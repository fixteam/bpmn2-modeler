/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc.
 *  All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 *
 * @author Bob Brodt
 ******************************************************************************/

package org.eclipse.bpmn2.modeler.core.merrimac.dialogs;

import org.eclipse.bpmn2.modeler.core.Activator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public abstract class AbstractObjectEditingDialog extends FormDialog {

	protected IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
	protected DiagramEditor editor;
	protected String title = "";
	protected EObject object;
	protected boolean cancel = false;
	protected boolean abortOnCancel = true;
	protected Transaction transaction;
	protected Composite dialogContent;
	
	public AbstractObjectEditingDialog(DiagramEditor editor, EObject object) {
		super(editor.getEditorSite().getShell());
		setHelpAvailable(false);
		setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.MAX | SWT.RESIZE
				| getDefaultOrientation());
		
		this.editor = editor;
		this.object = object;
	}

	@Override
	protected void createFormContent(IManagedForm mform) {
		super.createFormContent(mform); 
		final ScrolledForm form = mform.getForm();
		form.setExpandHorizontal(true);
		form.setExpandVertical(true);
		form.setText(null);

		Composite body = form.getBody();
		body.setBackground(form.getBackground());

		FormData data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		body.setLayoutData(data);
		body.setLayout(new FormLayout());
		
		dialogContent = createDialogContent(body);
		
		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		dialogContent.setLayoutData(data);
		
		form.setContent(body);
		getShell().pack();
	}
	
	abstract protected Composite createDialogContent(Composite parent);
	abstract protected String getPreferenceKey();
	
	protected String getTitle() {
		return title;
	}
	
	protected void addControlListener() {

		final String key = getPreferenceKey();
		Point p = getShell().getSize();
		int width = preferenceStore.getInt("dialog."+key+".width");
		if (width==0)
			width = p.x;
		int height = preferenceStore.getInt("dialog."+key+".height");
		if (height==0)
			height = p.y;
		getShell().setSize(width,height);
		
		p = getShell().getLocation();
		int x = preferenceStore.getInt("dialog."+key+".x");
		if (x==0)
			x = p.x;
		int y = preferenceStore.getInt("dialog."+key+".y");
		if (y==0)
			y = p.y;
		getShell().setLocation(x,y);

		getShell().addControlListener(new ControlListener() {
			public void controlMoved(ControlEvent e)
			{
				Point p = getShell().getLocation();
				preferenceStore.setValue("dialog."+key+".x", p.x);
				preferenceStore.setValue("dialog."+key+".y", p.y);
			}
			
			public void controlResized(ControlEvent e)
			{
				Point p = getShell().getSize();
				preferenceStore.setValue("dialog."+key+".width", p.x);
				preferenceStore.setValue("dialog."+key+".height", p.y);
			}
	
		});
		
        hookTransaction();
	}
	
	protected void aboutToOpen() {
		dialogContent.setData(object);
	}

	@Override
	public void create() {
		super.create();
		startTransaction();
	}
	
	@Override
	public int open() {
		if (getShell()==null)
			create();
		
		getShell().setText(getTitle());
		getShell().setSize(600,400);

		addControlListener();
		aboutToOpen();

		return super.open();
	}
	
	/**
	 * Return state of the "abortOnCancel transaction on cancel" flag
	 * 
	 * @return true if the current transaction will be aborted if the dialog is canceled.
	 */
	public boolean isAbortOnCancel() {
		return abortOnCancel;
	}

	/**
	 * Abort the currently active transaction if dialog is canceled either by clicking the "Cancel"
	 * button, pressing the ESCAPE key or closing the dialog with the Window Close button.
	 * 
	 * @param abortOnCancel - if true, abortOnCancel the current transaction if dialog is canceled,
	 * otherwise allow transaction to commit.
	 */
	public void setAbortOnCancel(boolean abort) {
		this.abortOnCancel = abort;
	}

	@Override
	protected void cancelPressed() {
		cancel = true;
		dialogContent.dispose();
		super.cancelPressed();
	}
	
	@Override
	protected void okPressed() {
		cancel = false;
		dialogContent.dispose();
		super.okPressed();
	}

	public boolean hasDoneChanges() {
		return transaction==null || !transaction.getChangeDescription().isEmpty();
	}
	
	protected void startTransaction() {
		if (transaction==null) {
			try {
				final InternalTransactionalEditingDomain transactionalDomain = (InternalTransactionalEditingDomain) editor
						.getEditingDomain();
				transaction = transactionalDomain.startTransaction(false, null);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void hookTransaction() {
		getShell().addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				if (transaction!=null && transaction.isActive()) {
					if (cancel) {
						transaction.rollback();
					}
					else {
						try {
							transaction.commit();
						}
						catch (RollbackException e) {
							ErrorDialog.openError(getShell(), "Error Commiting Model Changes",
									"An error occurred while trying to commit changes.", new Status(IStatus.ERROR,
											Activator.PLUGIN_ID, e.getMessage(), e));
						}
					}
				}
			}
		});
	}
	
	protected void rollbackTransaction() {
		if (transaction!=null) {
			transaction.rollback();
		}
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		button.setText("确定");
		Button button_1 = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		button_1.setText("取消");
	}
}

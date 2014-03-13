/*******************************************************************************
 * Copyright (c) 2011, 2012, 2013 Red Hat, Inc.
 * All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 	Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.modeler.ui.property.dialogs.DefaultSchemaImportDialog;
import org.eclipse.bpmn2.modeler.ui.property.dialogs.SchemaImportDialog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * This is a specialized Java Type Import dialog for importing BPMN2 Interface definitions.
 * It extends the default import dialog by adding a checkbox that allows the user to select
 * whether or not Process variables (a.k.a. BPMN2 "Property" elements) will be created.
 */
public class JbpmInterfaceImportDialog extends DefaultSchemaImportDialog {

	boolean createVariables = false;
	CheckboxTableViewer methodsTable;
	IMethod[] selectedMethods = new IMethod[0];
	
	public JbpmInterfaceImportDialog() {
		super(Display.getDefault().getActiveShell(), SchemaImportDialog.ALLOW_JAVA);
	}

	@Override
	public Control createDialogArea(Composite parent) {
		Composite contents = (Composite) super.createDialogArea(parent);

		Composite tableComposite = new Composite(contents, SWT.NONE);
		GridLayout layout = new GridLayout(3,false);
		layout.marginWidth = 0;
		tableComposite.setLayout(layout);
		tableComposite.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,1,1));

		Label label = new Label(tableComposite,SWT.NONE);
		label.setText( Messages.JbpmInterfaceImportDialog_Available_Methods);
		label.setLayoutData(new GridData(SWT.LEFT,SWT.TOP,true,false,1,1));
		
		final Button selectAllButton = new Button(tableComposite, SWT.PUSH);
		selectAllButton.setText(Messages.JbpmInterfaceImportDialog_Select_All);
		selectAllButton.setLayoutData(new GridData(SWT.RIGHT,SWT.TOP,false,false,1,1));
		
		final Button selectNoneButton = new Button(tableComposite, SWT.PUSH);
		selectNoneButton.setText(Messages.JbpmInterfaceImportDialog_Select_None);
		selectNoneButton.setLayoutData(new GridData(SWT.RIGHT,SWT.TOP,false,false,1,1));

		methodsTable = CheckboxTableViewer.newCheckList(tableComposite, SWT.CHECK | SWT.BORDER);
		methodsTable.getTable().setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,3,1));
		methodsTable.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return element.toString().replaceFirst("\\) \\[in .*", ")"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		});

		selectAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				methodsTable.setAllChecked(true);
			}
		});

		selectNoneButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				methodsTable.setAllChecked(false);
			}
		});

		final Button createVariablesbutton = new Button(contents, SWT.CHECK);
		createVariablesbutton.setText(Messages.JbpmImportDialog_Create_Process_Variables_Label);
		createVariablesbutton.setSelection(createVariables);
		createVariablesbutton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				createVariables = createVariablesbutton.getSelection();
			}
			
		});

		return contents;
	}
    
	protected void updateStatus(IStatus status) {
		super.updateStatus(status);
		if (status == Status.OK_STATUS) {
			// fill the Methods selection box
			if (methodsTable!=null) {
				IMethod methods[] = getAllMethods();
				methodsTable.getTable().removeAll();
				if (methods.length>0) {
					methodsTable.add(methods);
				}
			}
		}
    }
    
	public boolean isCreateVariables() {
		return createVariables;
	}

	public void setCreateVariables(boolean createVariables) {
		this.createVariables = createVariables;
	}
	
	public IType getIType() {
		Object result[] = getResult();
		if (result!=null && result.length == 1 && result[0] instanceof IType) {
			return (IType) result[0];
		}
		return null;
	}
	
	@Override
	protected void computeResult() {
		super.computeResult();
		
		if (methodsTable!=null) {
			Object[] checked = methodsTable.getCheckedElements();
			selectedMethods = new IMethod[checked.length];
			for (int i=0; i<checked.length; ++i)
				selectedMethods[i] = (IMethod) checked[i];
		}
		else
			selectedMethods = new IMethod[0];
	}

	public IMethod[] getAllMethods() {
		List<IMethod> methods = new ArrayList<IMethod>();
		IType type = getIType();
		if (type!=null) {
            try {
				for (IMethod method : type.getMethods()) {
					if (method.isConstructor()) {
						// don't create Operations for Constructors
						continue;
					}
					if (method.getElementName().contains("<")) { //$NON-NLS-1$
						continue;
					}
					if ((method.getFlags() & Flags.AccPublic) == 0) {
						continue;
					}
					if (method.getNumberOfParameters()==1)
						methods.add(method);
				}
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return methods.toArray(new IMethod[methods.size()]);
	}
	
	public IMethod[] getIMethods() {
		return selectedMethods;
	}
}

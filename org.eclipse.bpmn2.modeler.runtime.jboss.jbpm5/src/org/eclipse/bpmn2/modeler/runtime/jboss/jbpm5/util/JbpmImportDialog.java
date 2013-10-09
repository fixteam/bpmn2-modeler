package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.util;

import org.eclipse.bpmn2.modeler.ui.property.dialogs.DefaultSchemaImportDialog;
import org.eclipse.bpmn2.modeler.ui.property.dialogs.SchemaImportDialog;
import org.eclipse.jdt.core.IType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

/**
 * This is a specialized Java Type Import dialog for importing BPMN2 Interface definitions.
 * It extends the default import dialog by adding a checkbox that allows the user to select
 * whether or not Process variables (a.k.a. BPMN2 "Property" elements) will be created.
 */
public class JbpmImportDialog extends DefaultSchemaImportDialog {

	boolean createVariables = false;

	public JbpmImportDialog() {
		super(Display.getDefault().getActiveShell(), SchemaImportDialog.ALLOW_JAVA);
	}

	@Override
	public Control createDialogArea(Composite parent) {
		Composite contents = (Composite) super.createDialogArea(parent);
		
		final Button button = new Button(contents, SWT.CHECK);
		button.setText("Create Process Variables for imported Java Types");
		button.setSelection(createVariables);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				createVariables = button.getSelection();
			}
			
		});

		return contents;
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
}

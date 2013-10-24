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

package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property;

import org.eclipse.bpmn2.CallableElement;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.TextAndButtonObjectEditor;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerFactory;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.ExternalProcess;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Bob Brodt
 *
 */
public class JbpmCallActivityDetailComposite extends JbpmActivityDetailComposite {

	public JbpmCallActivityDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	public JbpmCallActivityDetailComposite(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	protected void bindReference(Composite parent, EObject object, EReference reference) {
		if ("calledElementRef".equals(reference.getName())) { //$NON-NLS-1$
			if (isModelObjectEnabled(object.eClass(), reference)) {
				if (parent==null)
					parent = getAttributesParent();
				
				String displayName = ModelUtil.getLabel(object, reference);
				ObjectEditor editor = new TextAndButtonObjectEditor(this,object,reference) {

					@Override
					protected void buttonClicked(int buttonId) {
						IInputValidator validator = new IInputValidator() {

							@Override
							public String isValid(String newText) {
								if (newText==null || newText.isEmpty())
									return Messages.JbpmCallActivityDetailComposite_Invalid_Empty;
								return null;
							}
							
						};
						
						String initialValue = ModelUtil.getDisplayName(object,feature);
						InputDialog dialog = new InputDialog(
								getShell(),
								Messages.JbpmCallActivityDetailComposite_Title,
								Messages.JbpmCallActivityDetailComposite_Message,
								initialValue,
								validator);
						if (dialog.open()==Window.OK){
							setValue(dialog.getValue());
						}
					}
					
					
					@Override
					protected boolean setValue(final Object result) {
						if (result != object.eGet(feature)) {
							TransactionalEditingDomain domain = getDiagramEditor().getEditingDomain();
							domain.getCommandStack().execute(new RecordingCommand(domain) {
								@Override
								protected void doExecute() {
									String id = result.toString();
									CallableElement ce = null;
									Definitions defs = ModelUtil.getDefinitions(object);
									for (RootElement re : defs.getRootElements()) {
										if (re instanceof ExternalProcess) {
											if (id.equals(re.getId())) {
												ce = (ExternalProcess) re;
												break;
											}
										}
									}
									if (ce==null) {
										ce = DroolsFactory.eINSTANCE.createExternalProcess();
										ce.setName(id);
										ce.setId(id);
										defs.getRootElements().add(ce);
									}
									
									object.eSet(feature, ce);
								}
							});
//							if (getDiagramEditor().getDiagnostics()!=null) {
//								ErrorUtils.showErrorMessage(getDiagramEditor().getDiagnostics().getMessage());
//							}
//							else {
//								ErrorUtils.showErrorMessage(null);
//								updateText();
//								return true;
//							}
							updateText();
							return true;
						}
						return false;
					}
				};
				editor.createControl(parent,displayName);
			}
		}
		else
			super.bindReference(parent, object, reference);
	}
}

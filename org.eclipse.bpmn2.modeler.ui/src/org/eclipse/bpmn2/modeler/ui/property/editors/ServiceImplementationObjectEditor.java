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
package org.eclipse.bpmn2.modeler.ui.property.editors;

import java.util.Hashtable;
import java.util.Map;

import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ComboObjectEditor;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ServiceImplementationObjectEditor extends ComboObjectEditor {

	public static String UNSPECIFIED_LABEL = Messages.ServiceImplementationObjectEditor_Unspecified_Label;
	public static String UNSPECIFIED_VALUE = "##unspecified"; //$NON-NLS-1$
	public static String WEBSERVICE_LABEL = Messages.ServiceImplementationObjectEditor_Web_Service_Label;
	public static String WEBSERVICE_VALUE = "##WebService"; //$NON-NLS-1$
	
	public ServiceImplementationObjectEditor(AbstractDetailComposite parent, EObject object, EStructuralFeature feature) {
		super(parent, object, feature);
	}

	public ServiceImplementationObjectEditor(AbstractDetailComposite parent, EObject object, EStructuralFeature feature,
			EClass featureEType) {
		super(parent, object, feature, featureEType);
	}
	
	protected boolean canEdit() {
		if (editButton==null)
			return true;
		Object value = object.eGet(feature);
		if (value instanceof String && ((String)value).startsWith("##")) //$NON-NLS-1$
			return false;
		return true;
	}
	
	protected boolean canCreateNew() {
		return true;
	}

	@Override
	protected boolean setValue(Object result) {
		if (ModelUtil.isStringWrapper(result)) {
			result = ModelUtil.getStringWrapperValue(result);
		}
		return super.setValue(result);
	}
	
	public Object getValue() {
		Object value = object.eGet(feature);
		if (UNSPECIFIED_VALUE.equals(value)) {
			value = UNSPECIFIED_LABEL;
		}
		else if (WEBSERVICE_VALUE.equals(value)) {
			value = WEBSERVICE_LABEL;
		}
		return value;
	}
	
	protected EObject createObject() throws Exception {
		Hashtable<String,Object> choices = getChoiceOfValues(object, feature);
		ImplementationEditingDialog dialog = new ImplementationEditingDialog(
				getDiagramEditor().getEditorSite().getShell(), 
				Messages.ServiceImplementationObjectEditor_Create_New_Title, 
				choices, null);
		if ( dialog.open() == Window.OK)
			return ModelUtil.createStringWrapper( dialog.getValue() );
		throw new OperationCanceledException(Messages.ServiceImplementationObjectEditor_Dialog_Cancelled);
	}
	
	protected EObject editObject(EObject value) throws Exception {
		Hashtable<String,Object> choices = getChoiceOfValues(object, feature);
		final String oldValue = ModelUtil.getStringWrapperValue(value);
		ImplementationEditingDialog dialog = new ImplementationEditingDialog(
				getDiagramEditor().getEditorSite().getShell(), 
				Messages.ServiceImplementationObjectEditor_Edit_Title, 
				choices, oldValue);
		if ( dialog.open() == Window.OK) {
			final String newValue = dialog.getValue();
			if (!newValue.equals(value)) {
				final Definitions definitions = ModelUtil.getDefinitions(object);
				if (definitions!=null) {
					TransactionalEditingDomain domain = getDiagramEditor().getEditingDomain();
					domain.getCommandStack().execute(new RecordingCommand(domain) {
						@Override
						protected void doExecute() {
							TreeIterator<EObject> iter = definitions.eAllContents();
							while (iter.hasNext()) {
								EObject o = iter.next();
								EStructuralFeature f = o.eClass().getEStructuralFeature("implementation"); //$NON-NLS-1$
								if (f!=null) {
									String implementation = (String)o.eGet(f);
									if (oldValue.equals(implementation)) {
										o.eSet(f, newValue);
									}
								}
							}
						}
					});
				}
	
				return ModelUtil.createStringWrapper( dialog.getValue() );
			}
		}
		throw new OperationCanceledException(Messages.ServiceImplementationObjectEditor_Dialog_Cancelled);
	}
	
	protected Hashtable<String,Object> getChoiceOfValues(EObject object, EStructuralFeature feature) {
		Hashtable<String, Object> choices = new Hashtable<String, Object>();
		choices.put(UNSPECIFIED_LABEL, ModelUtil.createStringWrapper(UNSPECIFIED_VALUE));
		choices.put(WEBSERVICE_LABEL, ModelUtil.createStringWrapper(WEBSERVICE_VALUE));
		Hashtable<String, Object> otherChoices = ModelUtil.getChoiceOfValues(object, feature);
		if (otherChoices!=null)
			choices.putAll(otherChoices);

		Definitions definitions = ModelUtil.getDefinitions(object);
		if (definitions!=null) {
			TreeIterator<EObject> iter = definitions.eAllContents();
			while (iter.hasNext()) {
				EObject o = iter.next();
				EStructuralFeature f = o.eClass().getEStructuralFeature("implementation"); //$NON-NLS-1$
				if (f!=null) {
					String implementation = (String)o.eGet(f);
					if (implementation!=null && !implementation.isEmpty() &&
							!implementation.startsWith("##")) { //$NON-NLS-1$
						if (!choices.containsKey(implementation)) {
							choices.put(implementation, ModelUtil.createStringWrapper(implementation));
						}
					}
				}
			}
		}
		return choices;
	}
	
	public class ImplementationEditingDialog extends InputDialog {
		public ImplementationEditingDialog(Shell shell, String title, final Map<String,Object> choices, final String uriString) {
			super(
					shell,
					title,
					Messages.ServiceImplementationObjectEditor_Implementation_Title,
					uriString,
					new IInputValidator() {

						@Override
						public String isValid(String newText) {
							if (newText==null || newText.isEmpty())
								return Messages.ServiceImplementationObjectEditor_Invalid_Empty;
							if (newText.equals(uriString))
								return null;
							if (choices.containsKey(newText) || choices.containsValue(newText))
								return NLS.bind(Messages.ServiceImplementationObjectEditor_Invalid_Duplicate,newText);
							URI uri = URI.createURI(newText);
							if (!(uri.hasAuthority() && uri.scheme()!=null)) {
								return Messages.ServiceImplementationObjectEditor_Invalid_URI;
							}
							return null;
						}
					}
				);
		}
	}
}

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

package org.eclipse.bpmn2.modeler.ui.property.editors;

import javax.xml.namespace.QName;

import org.eclipse.bpmn2.Import;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.TextAndButtonObjectEditor;
import org.eclipse.bpmn2.modeler.core.utils.NamespaceUtil;
import org.eclipse.bpmn2.modeler.ui.editor.BPMN2Editor;
import org.eclipse.bpmn2.modeler.ui.property.dialogs.SchemaSelectionDialog;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.wst.wsdl.Fault;
import org.eclipse.wst.wsdl.Input;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Output;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.xsd.XSDAttributeDeclaration;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDTypeDefinition;

/**
 * @author Bob Brodt
 * 
 */
public class SchemaObjectEditor extends TextAndButtonObjectEditor {

	protected Resource resource;
	protected Import imp;
	protected Button editButton;

	/**
	 * @param parent
	 * @param object
	 * @param feature
	 */
	public SchemaObjectEditor(AbstractDetailComposite parent, EObject object, EStructuralFeature feature) {
		super(parent, object, feature);
		resource = BPMN2Editor.getActiveEditor().getModelHandler().getResource();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.bpmn2.modeler.ui.property.editors.ObjectEditor#createControl
	 * (org.eclipse.swt.widgets.Composite, java.lang.String, int)
	 */
	@Override
	protected Control createControl(Composite composite, String label, int style) {
		super.createControl(composite, label, style);
		// the Text field should be editable
		text.setEditable(true);
		// and change the "Edit" button to a "Browse" to make it clear that
		// an XML type can be selected from the imports 
		defaultButton.setText(Messages.SchemaObjectEditor_Browse_Button);
		return text;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.ui.property.editors.TextAndButtonObjectEditor#buttonClicked()
	 */
	@Override
	protected void buttonClicked(int buttonId) {
		SchemaSelectionDialog dialog = new SchemaSelectionDialog(parent.getShell(), object);

		if (dialog.open() == Window.OK) {
			Object result = dialog.getResult()[0];
			String selectionPath = dialog.getSelectionPath();
			String value = ""; //$NON-NLS-1$
			String selectionType = ""; //$NON-NLS-1$

			// TODO: do we need these?
			if (result instanceof PortType) {
				PortType portType = (PortType)result;
				QName qname = portType.getQName();
				String prefix = NamespaceUtil.getPrefixForNamespace(resource, qname.getNamespaceURI());
				if (prefix==null)
					prefix = NamespaceUtil.addNamespace(resource, qname.getNamespaceURI());
				if (prefix!=null)
					value = prefix + ":"; //$NON-NLS-1$
				value += qname.getLocalPart();
				selectionType = Messages.SchemaObjectEditor_WSDL_Port;
			}
			if (result instanceof Operation) {
				selectionType = Messages.SchemaObjectEditor_WSDL_Operation;
			}
			if (result instanceof Input) {
				Input input = (Input)result;
				result = input.getMessage();
				selectionType = Messages.SchemaObjectEditor_WSDL_Input;
			}
			if (result instanceof Output) {
				Output output = (Output)result;
				result = output.getMessage();
				selectionType = Messages.SchemaObjectEditor_WSDL_Output;
			}
			if (result instanceof Fault) {
				Fault fault = (Fault)result;
				result = fault.getMessage();
				selectionType = Messages.SchemaObjectEditor_WSDL_Fault;
			}
			if (result instanceof Part) {
				Part part = (Part)result;
				result = part.getElementDeclaration();
				selectionType = Messages.SchemaObjectEditor_WSDL_Message_Part;
			}
			if (result instanceof Message) {
				Message message = (Message)result;
				QName qname = message.getQName();
				String prefix = NamespaceUtil.getPrefixForNamespace(resource, qname.getNamespaceURI());
				if (prefix==null)
					prefix = NamespaceUtil.addNamespace(resource, qname.getNamespaceURI());
				if (prefix!=null)
					value = prefix + ":"; //$NON-NLS-1$
				value += qname.getLocalPart();
				selectionType = Messages.SchemaObjectEditor_WSDL_Message;
			}
			if (result instanceof XSDAttributeDeclaration) {
				selectionType = Messages.SchemaObjectEditor_XML_Attribute;
			}
			
			if (result instanceof XSDElementDeclaration) {
				XSDElementDeclaration decl = (XSDElementDeclaration)result;
				XSDSchema schema = getContainingSchema(decl);
				String ns = schema.getTargetNamespace();
				if (ns==null) {
					XSDTypeDefinition type = decl.getTypeDefinition();
					if (type!=null) {
						ns = type.getSchema().getTargetNamespace();
					}
				}
				String prefix = NamespaceUtil.getPrefixForNamespace(resource, ns);
				if (prefix!=null)
					value = prefix + ":"; //$NON-NLS-1$
				value += selectionPath;
			}
			if (result instanceof XSDTypeDefinition) {
				XSDTypeDefinition type = (XSDTypeDefinition)result;
				XSDSchema schema = getContainingSchema(type);
				String ns = schema.getTargetNamespace();
				String prefix = NamespaceUtil.getPrefixForNamespace(resource, ns);
				if (prefix!=null)
					value = prefix + ":"; //$NON-NLS-1$
				value += selectionPath;
			}
			if (result instanceof XSDSchema) {
				XSDSchema schema = (XSDSchema)result;
				String prefix = NamespaceUtil.getPrefixForNamespace(resource, schema.getTargetNamespace());
				if (prefix!=null)
					value = prefix + ":"; //$NON-NLS-1$
				value += "schema"; //$NON-NLS-1$
			}
			if (result instanceof Process) {
				Process process = (Process)result;
				process.getSupportedInterfaceRefs();
			}
			if (result instanceof IType) {
				value = ((IType)result).getFullyQualifiedName('.');
			}
			if (value.isEmpty()) {
				MessageDialog.openWarning(parent.getShell(),
					Messages.SchemaObjectEditor_Invalid_Selection_Title,
					NLS.bind(Messages.SchemaObjectEditor_Invalid_Selection_Message,selectionType)
				);
			}
			else
				setValue(value);
		}
	}
	
	private XSDSchema getContainingSchema(EObject object) {
		EObject container = object.eContainer();
		if (container instanceof XSDSchema)
			return (XSDSchema) container;
		if (container!=null)
			return getContainingSchema(container);
		return null;
	}
}

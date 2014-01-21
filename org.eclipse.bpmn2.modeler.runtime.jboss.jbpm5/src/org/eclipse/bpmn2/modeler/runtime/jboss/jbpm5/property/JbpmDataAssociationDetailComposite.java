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

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.ItemAwareElement;
import org.eclipse.bpmn2.ReceiveTask;
import org.eclipse.bpmn2.SendTask;
import org.eclipse.bpmn2.ServiceTask;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskDescriptor;
import org.eclipse.bpmn2.modeler.ui.property.tasks.DataAssociationDetailComposite;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Bob Brodt
 *
 */
public class JbpmDataAssociationDetailComposite extends DataAssociationDetailComposite {

	/**
	 * @param section
	 */
	public JbpmDataAssociationDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	/**
	 * @param parent
	 * @param style
	 */
	public JbpmDataAssociationDetailComposite(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void createBindings(EObject be) {
		ExtendedPropertiesAdapter adapter = ExtendedPropertiesAdapter.adapt(be);
		if (adapter!=null) {
			// if the Activity that owns this DataInputAssociation or DataOutputAssociation is
			// a Custom Task, then make the "name" feature read-only. Custom Task I/O parameters
			// are defined in either the target runtime contributing plugin itself, or dynamically
			// by the target runtime plugin invoked during editor startup.
			if (JbpmIoParametersDetailComposite.isCustomTaskIOParameter((ItemAwareElement)be)) {
				EStructuralFeature f = be.eClass().getEStructuralFeature("name"); //$NON-NLS-1$
				adapter.setProperty(f, ExtendedPropertiesAdapter.UI_CAN_EDIT, Boolean.FALSE);
			}
		}
		
		setAllowedMapTypes(MapType.Property.getValue() | MapType.SingleAssignment.getValue());
		
		Activity activity = null;
		EObject o = be;
		while (o.eContainer()!=null) {
			o = o.eContainer();
			if (o instanceof Activity) {
				activity = (Activity)o;
				break;
			}
		}

		// TODO: for now we will show both "From" and "To", the DataInput or Output as well as the
		// process data object it is associated with, just in case the user needs to change the
		// DataInput/Output name or data type.
		// The data type is set to be whatever the Operation's InMessage and OutMessage are.
		// Does this need to be here?
		
//		boolean enabled = true;
//		if (activity instanceof ServiceTask) {
//			enabled = ((ServiceTask)activity).getOperationRef()==null;
//		}
//		else if (activity instanceof SendTask) {
//			enabled = ((SendTask)activity).getOperationRef()==null && ((SendTask)activity).getMessageRef()==null;
//		}
//		else if (activity instanceof ReceiveTask) {
//			enabled = ((ReceiveTask)activity).getOperationRef()==null && ((ReceiveTask)activity).getMessageRef()==null;
//		}
//		else if (CustomTaskDescriptor.getDescriptor(activity) != null && JbpmIoParametersDetailComposite.isCustomTaskIOParameter((ItemAwareElement)be)) {
//			enabled = false;
//		}
//
//		if (businessObject instanceof DataInput) {
//			setShowToGroup(enabled);
//		}
//		else {
//			setShowFromGroup(enabled);
//		}
		
		super.createBindings(be);
	}
}

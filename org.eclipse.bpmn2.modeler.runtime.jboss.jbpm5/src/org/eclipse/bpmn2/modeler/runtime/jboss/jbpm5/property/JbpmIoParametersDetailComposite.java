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
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property;

import java.util.List;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.DataOutput;
import org.eclipse.bpmn2.InputOutputSpecification;
import org.eclipse.bpmn2.ItemAwareElement;
import org.eclipse.bpmn2.ReceiveTask;
import org.eclipse.bpmn2.SendTask;
import org.eclipse.bpmn2.modeler.core.adapters.InsertionAdapter;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.ModelExtensionDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.ModelExtensionDescriptor.ModelExtensionAdapter;
import org.eclipse.bpmn2.modeler.core.runtime.ModelExtensionDescriptor.Property;
import org.eclipse.bpmn2.modeler.ui.property.tasks.IoParametersDetailComposite;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.widgets.Composite;

public class JbpmIoParametersDetailComposite extends IoParametersDetailComposite {

	public JbpmIoParametersDetailComposite(Composite parent, int style) {
		super(parent, style);
	}

	public JbpmIoParametersDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	@Override
	public void createBindings(final EObject be) {
		final EStructuralFeature ioSpecificationFeature = be.eClass().getEStructuralFeature("ioSpecification"); //$NON-NLS-1$
		if (ioSpecificationFeature != null) {
			// the control parameter must be an Activity or CallableElement (i.e. a Process or GlobalTask)
			InputOutputSpecification ioSpecification = (InputOutputSpecification)be.eGet(ioSpecificationFeature);
			if (ioSpecification==null) {
				ioSpecification = createModelObject(InputOutputSpecification.class);
				InsertionAdapter.add(be, ioSpecificationFeature, ioSpecification);
			}
			
			if (!(be instanceof ReceiveTask)) {
				EStructuralFeature dataInputsFeature = getFeature(ioSpecification, "dataInputs"); //$NON-NLS-1$
				if (isModelObjectEnabled(ioSpecification.eClass(),dataInputsFeature)) {
					dataInputsTable = new JbpmIoParametersListComposite(this, be, ioSpecification, dataInputsFeature);
					dataInputsTable.bindList(ioSpecification, dataInputsFeature);
					dataInputsTable.setTitle(Messages.JbpmIoParametersDetailComposite_Input_Mapping_Title);
				}
			}
			
			if (!(be instanceof SendTask)) {
				EStructuralFeature dataOutputsFeature = getFeature(ioSpecification, "dataOutputs"); //$NON-NLS-1$
				if (isModelObjectEnabled(ioSpecification.eClass(),dataOutputsFeature)) {
					dataOutputsTable = new JbpmIoParametersListComposite(this, be, ioSpecification, dataOutputsFeature);
					dataOutputsTable.bindList(ioSpecification, dataOutputsFeature);
					dataOutputsTable.setTitle(Messages.JbpmIoParametersDetailComposite_Output_Mapping_Title);
				}
			}
		}
	}
	
	public static boolean isCustomTask(EObject object) {
		ModelExtensionAdapter adapter = ModelExtensionDescriptor.getModelExtensionAdapter(object);
		if (adapter!=null && adapter.getDescriptor() instanceof CustomTaskDescriptor) {
			return true;
		}
		return false;
	}
	
	public static boolean isCustomTaskIOParameter(ItemAwareElement input) {
		String name = null;
		if (input instanceof DataInput)
			name = ((DataInput)input).getName();
		else if (input instanceof DataOutput)
			name = ((DataOutput)input).getName();
		else
			return false;
		
		Activity activity = findActivity(input);
		if (activity==null || name==null || name.isEmpty())
			return false;
		
		List<Property> props = null;
		ModelExtensionAdapter adapter = ModelExtensionDescriptor.getModelExtensionAdapter(activity);
		if (adapter!=null) {
			props = adapter.getProperties("ioSpecification/dataInputs/name"); //$NON-NLS-1$
			if (props!=null) {
				for (Property p : props) {
					Object propName = p.getFirstStringValue();
					if (propName!=null && name.equals(propName)) {
						return true;
					}
				}
			}
		}
		return false;
	}
		
	public static Activity findActivity(EObject be) {
		while (be!=null && !(be instanceof Activity)) {
			be = be.eContainer();
		}
		return (Activity)be;
	}

}

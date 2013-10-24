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
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.features;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.UserTask;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ElementParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.util.JbpmModelUtil;
import org.eclipse.bpmn2.modeler.ui.features.activity.task.UserTaskFeatureContainer;
import org.eclipse.bpmn2.modeler.ui.features.activity.task.UserTaskFeatureContainer.CreateUserTaskFeature;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

public class JbpmUserTaskFeatureContainer extends UserTaskFeatureContainer {

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateUserTaskFeature(fp) {
			public UserTask createBusinessObject(ICreateContext context) {
				UserTask userTask = super.createBusinessObject(context);
				
				for (DataInput input : userTask.getIoSpecification().getDataInputs()) {
					if ("Priority".equalsIgnoreCase(input.getName())) { //$NON-NLS-1$
						input.setItemSubjectRef(JbpmModelUtil.getDataType(context.getTargetContainer(), "Integer")); //$NON-NLS-1$
					}
					else if ("Skippable".equalsIgnoreCase(input.getName())) { //$NON-NLS-1$
						input.setItemSubjectRef(JbpmModelUtil.getDataType(context.getTargetContainer(), "Boolean")); //$NON-NLS-1$
					}
					else {
						input.setItemSubjectRef(JbpmModelUtil.getDataType(context.getTargetContainer(), "String")); //$NON-NLS-1$
					}
				}
				return userTask;
			}

		};
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new AddUserTaskFeature(fp) {

			@Override
			public PictogramElement add(IAddContext context) {
				PictogramElement pe = super.add(context);
				BaseElement be = BusinessObjectUtil.getFirstBaseElement(pe);
				ElementParameters ep = JbpmModelUtil.getElementParameters(be);
				getFeatureProvider().link(pe, ep);
				return pe;
			}
			
		};
	}

}

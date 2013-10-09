/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc. 
 * All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 *******************************************************************************/
package org.eclipse.bpmn2.modeler.runtime.example;

import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.runtime.example.SampleModel.SampleModelPackage;
import org.eclipse.bpmn2.modeler.ui.features.activity.task.TaskFeatureContainer;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;

public class SampleTaskFeatureContainer extends TaskFeatureContainer {

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateTaskFeature(fp) {

			@Override
			public Object[] create(ICreateContext context) {
				Object[] objects = super.create(context);
				
				if (objects.length>0 && objects[0] instanceof Task) {
					Task task = (Task)objects[0];
					Definitions defs = ModelUtil.getDefinitions(task);
					for (RootElement re : defs.getRootElements()) {
						System.out.println(re.getId());
					}
				}
				
				return objects;
			}

			@Override
			public Task createBusinessObject(ICreateContext context) {
				Task task = super.createBusinessObject(context);
				
				EStructuralFeature attr = SampleModelPackage.eINSTANCE.getDocumentRoot_elementId();
				task.eSet(attr, "task.id");
				
				attr = ModelUtil.createDynamicAttribute(SampleModelPackage.eINSTANCE, task, "execute", "EBoolean");
				task.eSet(attr, new Boolean(true));

				return task;
			}
			
		};
	}

}

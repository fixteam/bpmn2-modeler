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

import org.eclipse.bpmn2.Association;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.features.flow.AssociationFeatureContainer;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.mm.pictograms.Connection;

public class SampleAssociationFeatureContainer extends AssociationFeatureContainer {

	@Override
	public ICreateConnectionFeature getCreateConnectionFeature(IFeatureProvider fp) {
		return new CreateAssociationFeature(fp) {

			@Override
			public Connection create(ICreateConnectionContext context) {
				return super.create(context);
			}

			@Override
			public Association createBusinessObject(ICreateConnectionContext context) {
				Association assoc = super.createBusinessObject(context);
				if (assoc.getSourceRef() instanceof Task) {
					EStructuralFeature attr = ModelUtil.getAnyAttribute(assoc, "affectsTaskExecution");
					assoc.eSet(attr, true);
				}
				return assoc;
			}
			
		};
	}

}

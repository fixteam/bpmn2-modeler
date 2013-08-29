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
 * @author Ivar Meikas
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.features;


import java.io.IOException;

import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.SubProcess;
import org.eclipse.bpmn2.modeler.core.Activator;
import org.eclipse.bpmn2.modeler.core.ModelHandler;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

public abstract class AbstractCreateFlowElementFeature<T extends FlowElement> extends AbstractBpmn2CreateFeature<T> {
	
	public AbstractCreateFlowElementFeature(IFeatureProvider fp, String name, String description) {
		super(fp, name, description);
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		/*
		 * TODO: rethink this: it's causing all kinds of DI import problems
		 * also see AbstractAddActivityFeature
		 * 
		if (intoParticipant) {
			// don't allow flow elements to be added to a Pool if it is a "whitebox"
			// (i.e. if it has its own BPMNDiagram page.) Flow elements should be added
			// to the Process page instead.
			Participant participant = FeatureSupport.getTargetParticipant(context);
			if (FeatureSupport.hasBpmnDiagram(participant))
				return false;
		}
		else if (intoFlowElementContainer) {
			FlowElementsContainer flowElementsContainer = FeatureSupport.getTargetFlowElementsContainer(context);
			if (FeatureSupport.hasBpmnDiagram(flowElementsContainer))
				return false;
		}
		*/
		return FeatureSupport.isValidFlowElementTarget(context);
	}

	@Override
	public Object[] create(ICreateContext context) {
		T element = createBusinessObject(context);
		if (element!=null) {
			changesDone = true;
			try {
				ModelHandler handler = ModelHandler.getInstance(getDiagram());
				if (FeatureSupport.isTargetLane(context) && element instanceof FlowNode) {
					((FlowNode) element).getLanes().add(
							(Lane) getBusinessObjectForPictogramElement(context.getTargetContainer()));
				}
				handler.addFlowElement(getBusinessObjectForPictogramElement(context.getTargetContainer()), element);
			} catch (IOException e) {
				Activator.logError(e);
			}
			PictogramElement pe = null;
			pe = addGraphicalRepresentation(context, element);
			return new Object[] { element, pe };
		}
		else
			changesDone = false;
		return new Object[] { null };
	}
	
	protected abstract String getStencilImageId();
	
	@Override
	public String getCreateImageId() {
	    return getStencilImageId();
	}
	
	@Override
	public String getCreateLargeImageId() {
	    return getCreateImageId(); // FIXME
	}
}
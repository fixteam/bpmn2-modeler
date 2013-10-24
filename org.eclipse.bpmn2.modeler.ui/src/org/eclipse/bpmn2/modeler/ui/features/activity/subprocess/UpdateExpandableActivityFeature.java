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
 * @author Innar Made
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.ui.features.activity.subprocess;

import static org.eclipse.bpmn2.modeler.ui.features.activity.subprocess.SubProcessFeatureContainer.IS_EXPANDED;
import static org.eclipse.bpmn2.modeler.ui.features.activity.subprocess.SubProcessFeatureContainer.TRIGGERED_BY_EVENT;

import org.eclipse.bpmn2.SubProcess;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.AbstractUpdateFeature;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.Property;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.styles.LineStyle;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.osgi.util.NLS;

public class UpdateExpandableActivityFeature extends AbstractUpdateFeature {

	public UpdateExpandableActivityFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canUpdate(IUpdateContext context) {
		Object bo = getBusinessObjectForPictogramElement(context.getPictogramElement());
		return AbstractExpandableActivityFeatureContainer.isExpandableElement(bo);
	}

	@Override
	public IReason updateNeeded(IUpdateContext context) {
		PictogramElement pe = context.getPictogramElement();
		Property triggerProperty = Graphiti.getPeService().getProperty(pe,TRIGGERED_BY_EVENT);
		Property expandedProperty = Graphiti.getPeService().getProperty(pe,IS_EXPANDED);
		
		SubProcess subprocess = (SubProcess) getBusinessObjectForPictogramElement(pe);
		try {
			BPMNShape bpmnShape = DIUtils.findBPMNShape(subprocess);
			if (expandedProperty != null && Boolean.parseBoolean(expandedProperty.getValue()) != bpmnShape.isIsExpanded()) {
				return Reason.createTrueReason(Messages.UpdateExpandableActivityFeature_Expand_Changed);
			}
			
		} catch (Exception e) {
			throw new IllegalStateException(
				NLS.bind(Messages.UpdateExpandableActivityFeature_No_DI_Element,subprocess));
		}

		if (triggerProperty != null && Boolean.parseBoolean(triggerProperty.getValue()) != subprocess.isTriggeredByEvent()) {
			return Reason.createTrueReason(Messages.UpdateExpandableActivityFeature_Trigger_Changed);
		}
			
		return Reason.createFalseReason();
	}

	@Override
	public boolean update(IUpdateContext context) {
		PictogramElement pe = context.getPictogramElement();
		SubProcess subprocess = (SubProcess) getBusinessObjectForPictogramElement(pe);
		ContainerShape container = (ContainerShape) pe;
		boolean isExpanded = false;
		
		BPMNShape bpmnShape = DIUtils.findBPMNShape(subprocess);
		isExpanded = bpmnShape.isIsExpanded();
		Graphiti.getPeService().setPropertyValue(pe, TRIGGERED_BY_EVENT, Boolean.toString(subprocess.isTriggeredByEvent()));
		Graphiti.getPeService().setPropertyValue(pe, IS_EXPANDED, Boolean.toString(isExpanded));

		GraphicsAlgorithm rectangle = Graphiti.getPeService()
		        .getAllContainedPictogramElements(pe).iterator().next()
		        .getGraphicsAlgorithm();
		LineStyle lineStyle = subprocess.isTriggeredByEvent() ? LineStyle.DOT : LineStyle.SOLID;
		rectangle.setLineStyle(lineStyle);

		if(!isExpanded){
			FeatureSupport.setContainerChildrenVisible(container, false);
			GraphicsUtil.showActivityMarker(container, GraphicsUtil.ACTIVITY_MARKER_EXPAND);
		}else{
			FeatureSupport.setContainerChildrenVisible(container, true);
			GraphicsUtil.hideActivityMarker(container, GraphicsUtil.ACTIVITY_MARKER_EXPAND);
		}
		
		return true;
	}
}
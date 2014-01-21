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

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.SubProcess;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.features.activity.AbstractAddActivityFeature;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;

public class AddExpandableActivityFeature<T extends Activity>
	extends AbstractAddActivityFeature<T> {

	public AddExpandableActivityFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	protected void decorateShape(IAddContext context, ContainerShape containerShape, T businessObject) {
		boolean isTriggeredByEvent = false;
		boolean isExpanded = true;
		
		if (businessObject instanceof SubProcess) {
			SubProcess subprocess = (SubProcess) businessObject;
			isTriggeredByEvent = subprocess.isTriggeredByEvent();

			BPMNShape bpmnShape = DIUtils.findBPMNShape(subprocess);
			if (bpmnShape != null) {
				isExpanded = bpmnShape.isIsExpanded();
			}
		}
		peService.setPropertyValue(containerShape, TRIGGERED_BY_EVENT, Boolean.toString(isTriggeredByEvent));
		peService.setPropertyValue(containerShape, IS_EXPANDED, Boolean.toString(isExpanded));

		Shape textShape = peService.createShape(containerShape, false);
		MultiText text = gaService.createDefaultMultiText(getDiagram(), textShape, businessObject.getName());
		gaService.setLocationAndSize(text, 5, 5, context.getWidth() - 10, 15);
		StyleUtil.applyStyle(text, businessObject);
		text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		text.setVerticalAlignment(Orientation.ALIGNMENT_TOP);
//		text.setFont(gaService.manageFont(getDiagram(), GaServiceImpl.DEFAULT_FONT, 8, false, true));
		link(textShape, businessObject);
		
		if (!isExpanded){
			GraphicsUtil.showActivityMarker(containerShape, GraphicsUtil.ACTIVITY_MARKER_EXPAND);
		}
		else {
			GraphicsUtil.hideActivityMarker(containerShape, GraphicsUtil.ACTIVITY_MARKER_EXPAND);
		}
	}

	@Override
	public int getWidth() {
		if (preferences.isExpandedDefault())
			return GraphicsUtil.SUB_PROCEESS_DEFAULT_WIDTH;
		return GraphicsUtil.TASK_DEFAULT_WIDTH;
	}

	@Override
	public int getHeight() {
		if (preferences.isExpandedDefault())
			return GraphicsUtil.SUB_PROCESS_DEFAULT_HEIGHT;
		return GraphicsUtil.TASK_DEFAULT_HEIGHT;
	}
}
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
package org.eclipse.bpmn2.modeler.core.features.participant;

import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.di.DIImport;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2AddElementFeature;
import org.eclipse.bpmn2.modeler.core.features.DefaultPasteBPMNElementFeature;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.services.IPeService;

public class AddParticipantFeature extends AbstractBpmn2AddElementFeature<Participant> {

	public static final String MULTIPLICITY = "multiplicity"; //$NON-NLS-1$
	public static final int DEFAULT_POOL_WIDTH = 600;
	public static final int DEFAULT_POOL_HEIGHT = 150;

	public AddParticipantFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		boolean isParticipant = getBusinessObject(context) instanceof Participant;
		boolean addToDiagram = context.getTargetContainer() instanceof Diagram;
		return isParticipant && addToDiagram;
	}

	@Override
	public PictogramElement add(IAddContext context) {
		Participant businessObject = getBusinessObject(context);
		IGaService gaService = Graphiti.getGaService();
		IPeService peService = Graphiti.getPeService();
 
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		ContainerShape containerShape = peCreateService.createContainerShape(targetDiagram, true);

		int width = this.getWidth(context);
		int height = this.getHeight(context);

		Rectangle rect = gaService.createRectangle(containerShape);
		StyleUtil.applyStyle(rect, businessObject);
		gaService.setLocationAndSize(rect, context.getX(), context.getY(), width, height);

		boolean isImport = context.getProperty(DIImport.IMPORT_PROPERTY) != null;
		BPMNShape bpmnShape = createDIShape(containerShape, businessObject, !isImport);
		boolean horz = bpmnShape.isIsHorizontal();
		Object copiedBpmnShape = context.getProperty(DefaultPasteBPMNElementFeature.COPIED_BPMN_SHAPE);
		if (copiedBpmnShape instanceof BPMNShape) {
			horz = ((BPMNShape) copiedBpmnShape).isIsHorizontal();
		}
		FeatureSupport.setHorizontal(containerShape, horz);

		Shape lineShape = peCreateService.createShape(containerShape, false);
		Polyline line;
		if (horz)
			line = gaService.createPolyline(lineShape, new int[] { 30, 0, 30, height });
		else
			line = gaService.createPolyline(lineShape, new int[] { 0, 30, width, 30 });
		StyleUtil.applyStyle(line, businessObject);

		Shape textShape = peCreateService.createShape(containerShape, false);
		Text text = gaService.createText(textShape, businessObject.getName());
		StyleUtil.applyStyle(text, businessObject);
		text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		link(textShape, businessObject);

		peService.setPropertyValue(containerShape, MULTIPLICITY, Boolean.toString(businessObject.getParticipantMultiplicity()!=null));
		
		decorateShape(context, containerShape, businessObject);
		
		peCreateService.createChopboxAnchor(containerShape);
		AnchorUtil.addFixedPointAnchors(containerShape, rect);

		updatePictogramElement(containerShape);
		layoutPictogramElement(containerShape);
		return containerShape;
	}

	@Override
	public int getHeight() {
		return DEFAULT_POOL_HEIGHT;
	}

	@Override
	public int getWidth() {
		return DEFAULT_POOL_WIDTH;
	}
}

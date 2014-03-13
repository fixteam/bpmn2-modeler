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
package org.eclipse.bpmn2.modeler.ui.features.choreography;

import static org.eclipse.bpmn2.modeler.core.features.choreography.ChoreographyProperties.INITIATING_PARTICIPANT_REF;
import static org.eclipse.bpmn2.modeler.core.features.choreography.ChoreographyProperties.MESSAGE_REF_IDS;
import static org.eclipse.bpmn2.modeler.core.features.choreography.ChoreographyProperties.PARTICIPANT_REF_IDS;
import static org.eclipse.bpmn2.modeler.core.features.choreography.ChoreographyProperties.R;
import static org.eclipse.bpmn2.modeler.core.features.choreography.ChoreographyProperties.TEXT_H;
import static org.eclipse.bpmn2.modeler.ui.features.choreography.ChoreographyUtil.drawMultiplicityMarkers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.ChoreographyActivity;
import org.eclipse.bpmn2.ChoreographyTask;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.di.ParticipantBandKind;
import org.eclipse.bpmn2.modeler.core.Activator;
import org.eclipse.bpmn2.modeler.core.ModelHandler;
import org.eclipse.bpmn2.modeler.core.di.DIImport;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2AddElementFeature;
import org.eclipse.bpmn2.modeler.core.features.choreography.ChoreographyProperties;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.AbstractText;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;

public class AddChoreographyActivityFeature<T extends ChoreographyActivity>
	extends AbstractBpmn2AddElementFeature<T> {

	protected final IGaService gaService = Graphiti.getGaService();
	protected final IPeService peService = Graphiti.getPeService();

	public AddChoreographyActivityFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return context.getTargetContainer().equals(getDiagram());
	}

	@Override
	public PictogramElement add(IAddContext context) {
		T businessObject = getBusinessObject(context);

		int width = this.getWidth(context);
		int height = this.getHeight(context);

		ContainerShape containerShape = peService.createContainerShape(context.getTargetContainer(), true);
		RoundedRectangle containerRect = gaService.createRoundedRectangle(containerShape, R, R);
		gaService.setLocationAndSize(containerRect, context.getX(), context.getY(), width, height);
		StyleUtil.applyStyle(containerRect, businessObject);

		boolean isImport = context.getProperty(DIImport.IMPORT_PROPERTY) != null;
		if (isImport) {
			addedFromImport(businessObject, containerShape, context);
		}

		Shape nameShape = peService.createShape(containerShape, false);

		MultiText text = gaService.createDefaultMultiText(getDiagram(), nameShape);
		text.setValue(businessObject.getName());
		StyleUtil.applyStyle(text, businessObject);
		text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		setTextLocation(containerShape, text, width, height);
		peService.setPropertyValue(nameShape, ChoreographyProperties.CHOREOGRAPHY_NAME, Boolean.toString(true));
		GraphicsUtil.hideActivityMarker(containerShape, GraphicsUtil.ACTIVITY_MARKER_EXPAND);

		if (businessObject instanceof ChoreographyTask) {
			peService.setPropertyValue(containerShape, MESSAGE_REF_IDS,
					ChoreographyUtil.getMessageRefIds((ChoreographyTask) businessObject));
		}

		peService.createChopboxAnchor(containerShape);
		createDIShape(containerShape, businessObject, !isImport);
		
		decorateShape(context, containerShape, businessObject);
		
		AnchorUtil.addFixedPointAnchors(containerShape, containerRect);
		ChoreographyUtil.drawMessageLinks(getFeatureProvider(),containerShape);
		
		return containerShape;
	}

	protected void addedFromImport(T choreographyActivity, ContainerShape containerShape,
			IAddContext context) {

		List<Participant> participants = choreographyActivity.getParticipantRefs();
		List<BPMNShape> allShapes = ModelUtil.getAllObjectsOfType(choreographyActivity.eResource(), BPMNShape.class);
		List<BPMNShape> participantBandShapes = new ArrayList<BPMNShape>();
		BPMNShape choreoBpmnShape = null;

		for (BPMNShape bpmnShape : allShapes) {
			if (choreographyActivity.equals(bpmnShape.getBpmnElement())) {
				choreoBpmnShape = bpmnShape;
				break;
			}
		}

		for (BPMNShape bpmnShape : allShapes) {
			if (participants.contains(bpmnShape.getBpmnElement())
					&& choreoBpmnShape.equals(bpmnShape.getChoreographyActivityShape())) {
				participantBandShapes.add(bpmnShape);
			}
		}

		for (BPMNShape bpmnShape : participantBandShapes) {
			ParticipantBandKind bandKind = bpmnShape.getParticipantBandKind();
			ContainerShape createdShape = ChoreographyUtil.createParticipantBandContainerShape(bandKind,
					containerShape, bpmnShape, isShowNames());
			createDIShape(createdShape, bpmnShape.getBpmnElement(), bpmnShape, false);
			Participant p = (Participant) bpmnShape.getBpmnElement();
			if (p.getParticipantMultiplicity() != null && p.getParticipantMultiplicity().getMaximum() > 1) {
				drawMultiplicityMarkers(createdShape);
			}
		}

		peService.setPropertyValue(containerShape, PARTICIPANT_REF_IDS,
				ChoreographyUtil.getParticipantRefIds(choreographyActivity));
		Participant initiatingParticipant = choreographyActivity.getInitiatingParticipantRef();
		String id = initiatingParticipant == null ? "null" : initiatingParticipant.getId(); //$NON-NLS-1$
		peService.setPropertyValue(containerShape, INITIATING_PARTICIPANT_REF, id);
	}

	protected void setTextLocation(ContainerShape choreographyContainer, AbstractText text, int w, int h) {
		gaService.setLocationAndSize(text, 5, 5, w - 5, h);
	}

	protected boolean isShowNames() {
		return true;
	}

	@Override
	public int getHeight() {
		return GraphicsUtil.CHOREOGRAPHY_HEIGHT;
	}

	@Override
	public int getWidth() {
		return GraphicsUtil.CHOREOGRAPHY_WIDTH;
	}
}
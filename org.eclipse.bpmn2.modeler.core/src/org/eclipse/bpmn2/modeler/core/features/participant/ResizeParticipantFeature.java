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

import java.util.List;

import org.eclipse.bpmn2.ChoreographyActivity;
import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.context.impl.ResizeShapeContext;
import org.eclipse.graphiti.features.impl.DefaultResizeShapeFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;

public class ResizeParticipantFeature extends DefaultResizeShapeFeature {

	public static final String POOL_RESIZE_PROPERTY = "pool.resize"; //$NON-NLS-1$
	public static final String RESIZE_FIRST_LANE = "resize.first.lane"; //$NON-NLS-1$
	
	public ResizeParticipantFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canResizeShape(IResizeShapeContext context) {
		EObject container = context.getShape().eContainer();
		if (container instanceof PictogramElement) {
			PictogramElement containerElem = (PictogramElement) container;
			if (BusinessObjectUtil.containsElementOfType(containerElem, ChoreographyActivity.class)) {
				return false;
			}
		}
		return super.canResizeShape(context);
	}
	
	private void resizeLaneHeight(IResizeShapeContext context) {
		ContainerShape participantShape = (ContainerShape) context.getShape();
		GraphicsAlgorithm ga = participantShape.getGraphicsAlgorithm();
		
		ContainerShape laneToResize = null;
		GraphicsAlgorithm laneToResizeGA  = null;
		int width = 0;
		int height = 0;
		int x = 0;
		int y = 0;
		boolean resizeFirstLane = false;
		boolean resize = false;
		if (FeatureSupport.isHorizontal(participantShape)) {
			int dHeight = context.getHeight() - ga.getHeight();
			if (dHeight != 0) {
				resize = true;
				if (context.getY() != ga.getY()) {
					laneToResize = (ContainerShape) FeatureSupport.getFirstLaneInContainer(participantShape);
					resizeFirstLane = true;
				} else {
					laneToResize = (ContainerShape) FeatureSupport.getLastLaneInContainer(participantShape);
				}
				laneToResizeGA = laneToResize.getGraphicsAlgorithm();
				width = laneToResizeGA.getWidth();
				height = laneToResizeGA.getHeight() + dHeight;
				x = laneToResizeGA.getX();
				y = laneToResizeGA.getY();
			}
		} else {
			int dWidth = context.getWidth() - ga.getWidth();
			if (dWidth != 0) {
				resize = true;
				if (context.getX() != ga.getX()) {
					laneToResize = (ContainerShape) FeatureSupport.getFirstLaneInContainer(participantShape);
					resizeFirstLane = true;
				} else {
					laneToResize = (ContainerShape) FeatureSupport.getLastLaneInContainer(participantShape);
				}
				laneToResizeGA = laneToResize.getGraphicsAlgorithm();
				width = laneToResizeGA.getWidth() + dWidth;
				height = laneToResizeGA.getHeight();
				x = laneToResizeGA.getX();
				y = laneToResizeGA.getY();
			}
		}
		if (resize) {
			ResizeShapeContext newContext = new ResizeShapeContext(laneToResize);
			
			newContext.setLocation(x, y);
			newContext.setHeight(height);
			newContext.setWidth(width);
			
			newContext.putProperty(POOL_RESIZE_PROPERTY, true);
			newContext.putProperty(RESIZE_FIRST_LANE, resizeFirstLane);
			
			IResizeShapeFeature resizeFeature = getFeatureProvider().getResizeShapeFeature(newContext);
			if (resizeFeature.canResizeShape(newContext)) {
				resizeFeature.resizeShape(newContext);
			}
			if (FeatureSupport.isHorizontal(participantShape)) {
				((ResizeShapeContext) context).setHeight(ga.getHeight());
			} else {
				((ResizeShapeContext) context).setWidth(ga.getWidth());
			}
		}
	}
	
	private void resizeLaneWidth(IResizeShapeContext context) {
		ContainerShape participantShape = (ContainerShape) context.getShape();
		GraphicsAlgorithm ga = participantShape.getGraphicsAlgorithm();
		
		int dHeight = context.getHeight() - ga.getHeight();
		int dWidth = context.getWidth() - ga.getWidth();
		
		if ((dWidth != 0 && FeatureSupport.isHorizontal(participantShape)) ||
				(dHeight != 0 && !FeatureSupport.isHorizontal(participantShape))) {
			List<PictogramElement> childrenShapes = FeatureSupport.getChildsOfBusinessObjectType(participantShape, Lane.class);
			for (PictogramElement currentPicElem : childrenShapes) {
				if (currentPicElem instanceof ContainerShape) {
					ContainerShape currentContainerShape = (ContainerShape) currentPicElem; 
					GraphicsAlgorithm laneGA = currentContainerShape.getGraphicsAlgorithm();
					
					ResizeShapeContext newContext = new ResizeShapeContext(currentContainerShape);
					
					newContext.setLocation(laneGA.getX(), laneGA.getY());
					if (FeatureSupport.isHorizontal(participantShape)) {
						newContext.setWidth(laneGA.getWidth() + dWidth);
						newContext.setHeight(laneGA.getHeight());
					} else {
						newContext.setHeight(laneGA.getHeight() + dHeight);
						newContext.setWidth(laneGA.getWidth());
					}
					
					newContext.putProperty(POOL_RESIZE_PROPERTY, true);
					
					IResizeShapeFeature resizeFeature = getFeatureProvider().getResizeShapeFeature(newContext);
					if (resizeFeature.canResizeShape(newContext)) {
						resizeFeature.resizeShape(newContext);
					}
				}
			}
		}
	}
	
	@Override
	public void resizeShape(IResizeShapeContext context) {
		ContainerShape poolShape = (ContainerShape) context.getShape();
		GraphicsAlgorithm poolGa = poolShape.getGraphicsAlgorithm();
		int preX = poolGa.getX();
		int preY = poolGa.getY();

		if (BusinessObjectUtil.containsChildElementOfType(context.getPictogramElement(), Lane.class)) {
			resizeLaneHeight(context);
			resizeLaneWidth(context);
		}
		
		super.resizeShape(context);
		
		int deltaX = preX - context.getX();
		int deltaY = preY - context.getY();
		
		// Adjust location of children so that a resize up or left
		// leaves them in the same location relative to the diagram.
		// This allows the user to create (or remove) space between
		// the Pool's edge and the contained activities.
		for (Shape shape : poolShape.getChildren()) {
			if (shape instanceof ContainerShape ) {
				EObject bo = BusinessObjectUtil.getBusinessObjectForPictogramElement(shape);
				GraphicsAlgorithm ga = shape.getGraphicsAlgorithm();
				if (!(bo instanceof Lane)) {
					Graphiti.getLayoutService().setLocation(ga, ga.getX() + deltaX, ga.getY() + deltaY);
				}
			}
		}
	}
}

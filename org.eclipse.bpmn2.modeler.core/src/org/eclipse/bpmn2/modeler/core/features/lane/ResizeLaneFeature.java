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
package org.eclipse.bpmn2.modeler.core.features.lane;

import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.features.DefaultResizeBPMNShapeFeature;
import org.eclipse.bpmn2.modeler.core.features.participant.ResizeParticipantFeature;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.context.impl.ResizeShapeContext;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;

public class ResizeLaneFeature extends DefaultResizeBPMNShapeFeature {

	public static final String LANE_RESIZE_PROPERTY = "lane.resize"; //$NON-NLS-1$

	public ResizeLaneFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canResizeShape(IResizeShapeContext context) {
		boolean isLane = FeatureSupport.isLane(context.getPictogramElement());
		if (!isLane) {
			return false;
		}

		boolean isParentLane = FeatureSupport.isLane(((ContainerShape) context
				.getPictogramElement()).getContainer());
		if (!isParentLane) {
			return true;
		}

		if (context.getHeight() == -1 && context.getWidth() == -1) {
			return true;
		}

		GraphicsAlgorithm ga = ((ContainerShape) context.getPictogramElement())
				.getGraphicsAlgorithm();

		int i = compare(ga.getHeight(), ga.getWidth(), context.getHeight(),
				context.getWidth());

		Lane lane = (Lane) BusinessObjectUtil.getFirstElementOfType(
				context.getPictogramElement(), Lane.class);

		if (i < 0 && lane.getFlowNodeRefs().size() == 0) {
			return true;
		}

		if (i > 0) {
			return true;
		}

		return true;
	}

	private int compare(int heightBefore, int widthBefore, int heightAfter,
			int widthAfter) {
		if (heightAfter > heightBefore || widthAfter > widthBefore) {
			return 1;
		}
		if (heightAfter < heightBefore || widthAfter < widthBefore) {
			return -1;
		}
		return 0;
	}

	private void resizeHeight(IResizeShapeContext context) {
		ContainerShape laneContainerShape = (ContainerShape) context.getShape();
		GraphicsAlgorithm ga = laneContainerShape.getGraphicsAlgorithm();
		
		boolean isHorizontal = FeatureSupport.isHorizontal(laneContainerShape);
		
		if ((isHorizontal && ga.getHeight() != context.getHeight()) 
				|| (!isHorizontal && ga.getWidth() != context.getWidth())) {
			
			ContainerShape rootContainer = FeatureSupport.getRootContainer(laneContainerShape);
			
			boolean fetchFirstProp = false;
			Object fetchFirstProperty = context.getProperty(ResizeParticipantFeature.RESIZE_FIRST_LANE);
			if (fetchFirstProperty != null && ((Boolean) fetchFirstProperty).booleanValue()) {
				fetchFirstProp = true;
			} else {
				if ((isHorizontal && context.getY() != ga.getY()) ||
						(!isHorizontal && context.getX() != ga.getX())) {
					fetchFirstProp = true;
					if (laneContainerShape.equals(rootContainer)) {
						Graphiti.getGaService().setLocation(ga, context.getX(), context.getY());
					}
				}
			}
			
			ContainerShape lowestContainingLane = getLowestLane(laneContainerShape, fetchFirstProp);
			GraphicsAlgorithm lowestLaneGA = lowestContainingLane.getGraphicsAlgorithm();
			
			int width = 0;
			int height = 0;
			
			if (isHorizontal) {
				int dHeight = context.getHeight() - ga.getHeight();
				height = lowestLaneGA.getHeight() + dHeight;
				if (height < 100) {
					height = 100;
				}
				width = lowestLaneGA.getWidth();
			} else {
				int dWidth = context.getWidth() - ga.getWidth();
				width = lowestLaneGA.getWidth() + dWidth;
				if (width < 100) {
					width = 100;
				}
				height = lowestLaneGA.getHeight();
			}
			
			ResizeShapeContext newContext = new ResizeShapeContext(lowestContainingLane);
			
			newContext.setX(lowestLaneGA.getX());
			newContext.setY(lowestLaneGA.getY());
			newContext.setHeight(height);
			newContext.setWidth(width);
			
			super.resizeShape(newContext);
		}
	}

	private void resizeWidth(IResizeShapeContext context) {
		ContainerShape laneContainerShape = (ContainerShape) context.getShape();
		GraphicsAlgorithm ga = laneContainerShape.getGraphicsAlgorithm();
		
		boolean isHorizontal = FeatureSupport.isHorizontal(laneContainerShape);
		
		if ((isHorizontal && ga.getWidth() != context.getWidth()) 
				|| (!isHorizontal && ga.getHeight() != context.getHeight())) {
			
			
			int dWidth = 0;
			int dHeight = 0;
			if (isHorizontal) {
				dWidth = context.getWidth() - ga.getWidth();
			} else {
				dHeight = context.getHeight() - ga.getHeight();
			}
			
			Object poolResizeProperty = context.getProperty(ResizeParticipantFeature.POOL_RESIZE_PROPERTY);
			if (poolResizeProperty != null && ((Boolean) poolResizeProperty).booleanValue()) {
				if (isHorizontal) {
					Graphiti.getGaService().setWidth(ga, context.getWidth());
				} else {
					Graphiti.getGaService().setHeight(ga, context.getHeight());
				}
				for (PictogramElement currentChild : FeatureSupport.getChildsOfBusinessObjectType(laneContainerShape, Lane.class)) {
					if (currentChild instanceof ContainerShape) {
						ContainerShape currentContainer = (ContainerShape) currentChild;
						GraphicsAlgorithm currentGA = currentChild.getGraphicsAlgorithm();
						
						ResizeShapeContext newContext = new ResizeShapeContext(currentContainer);
						
						newContext.setX(currentGA.getX());
						newContext.setY(currentGA.getY());
						newContext.setHeight(currentGA.getHeight() + dHeight);
						newContext.setWidth(currentGA.getWidth() + dWidth);
						
						
						newContext.putProperty(ResizeParticipantFeature.POOL_RESIZE_PROPERTY, true);
						
						resizeShape(newContext);
					}
				}
			} else {
				ContainerShape rootContainer = FeatureSupport.getRootContainer(laneContainerShape);
				GraphicsAlgorithm rootGA = rootContainer.getGraphicsAlgorithm();
				
				if (FeatureSupport.isParticipant(rootContainer)) {
					ResizeShapeContext newContext = new ResizeShapeContext(rootContainer);

					newContext.setX(rootGA.getX());
					newContext.setY(rootGA.getY());
					newContext.setWidth(rootGA.getWidth() + dWidth);
					newContext.setHeight(rootGA.getHeight() + dHeight);

					IResizeShapeFeature resizeFeature = getFeatureProvider().getResizeShapeFeature(newContext);
					if (resizeFeature.canResizeShape(newContext)) {
						resizeFeature.resizeShape(newContext);
					}
				} else {
					ContainerShape container = null;
					Object rootIsLaneProperty = context.getProperty(LANE_RESIZE_PROPERTY);
					if (rootIsLaneProperty != null && ((Boolean) rootIsLaneProperty).booleanValue()) {
						Graphiti.getGaService().setWidth(ga, context.getWidth());
						Graphiti.getGaService().setHeight(ga, context.getHeight());
						container = laneContainerShape;
					} else {
						container = rootContainer;
						if (isHorizontal) {
							Graphiti.getGaService().setWidth(rootGA, rootGA.getWidth() + dWidth);
						} else {
							Graphiti.getGaService().setHeight(rootGA, rootGA.getHeight() + dHeight);
						}
						if (laneContainerShape.equals(rootContainer)) {
							Graphiti.getGaService().setLocation(ga, context.getX(), context.getY());
						}
					}
					for (PictogramElement currentChild : FeatureSupport.getChildsOfBusinessObjectType(container, Lane.class)) {
						if (currentChild instanceof ContainerShape) {
							ContainerShape currentContainer = (ContainerShape) currentChild;
							GraphicsAlgorithm currentGA = currentChild.getGraphicsAlgorithm();

							ResizeShapeContext newContext = new ResizeShapeContext(currentContainer);

							newContext.setX(currentGA.getX());
							newContext.setY(currentGA.getY());
							newContext.setWidth(currentGA.getWidth() + dWidth);
							newContext.setHeight(currentGA.getHeight() + dHeight);

							newContext.putProperty(LANE_RESIZE_PROPERTY, true);

							resizeShape(newContext);
						}
					}
				}
			}
		}
	}

	@Override
	public void resizeShape(IResizeShapeContext context) {
		ContainerShape laneShape = (ContainerShape) context.getShape();
		GraphicsAlgorithm laneGa = laneShape.getGraphicsAlgorithm();
		int preX = laneGa.getX();
		int preY = laneGa.getY();

		resizeHeight(context);
		resizeWidth(context);

		int deltaX = preX - context.getX();
		int deltaY = preY - context.getY();
		
		// Adjust location of children so that a resize up or left
		// leaves them in the same location relative to the diagram.
		// This allows the user to create (or remove) space between
		// the Lane's edge and the contained activities.
		for (PictogramElement pe : laneShape.getChildren()) {
			if (pe instanceof ContainerShape) {
				EObject bo = BusinessObjectUtil.getBusinessObjectForPictogramElement(pe);
				if (!(bo instanceof Lane)) {
					ContainerShape child = (ContainerShape) pe;
					GraphicsAlgorithm ga = child.getGraphicsAlgorithm();
					Graphiti.getLayoutService().setLocation(ga, ga.getX() + deltaX, ga.getY() + deltaY);
				}
			}
		}
		
		DIUtils.updateDIShape(laneShape);
		ContainerShape rootContainer = FeatureSupport.getRootContainer(laneShape);
		if (rootContainer!=laneShape) {
			DIUtils.updateDIShape(rootContainer);
		}
	}

	private ContainerShape getLowestLane(ContainerShape root, boolean fetchFirst) {
		ContainerShape result;
		if (fetchFirst) {
			result = (ContainerShape) FeatureSupport.getFirstLaneInContainer(root);
		} else {
			result = (ContainerShape) FeatureSupport.getLastLaneInContainer(root);
		}
		if (!result.equals(root)) {
			return getLowestLane(result, fetchFirst);
		}
		return result;
	}

}

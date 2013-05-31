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

import org.eclipse.bpmn2.di.BPMNEdge;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.dd.di.DiagramElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.impl.DefaultMoveShapeFeature;
import org.eclipse.graphiti.mm.algorithms.AbstractText;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.features.context.impl.MoveShapeContext;

public class DefaultMoveBPMNShapeFeature extends DefaultMoveShapeFeature {

	int preShapeX;
	int preShapeY;
	
	public DefaultMoveBPMNShapeFeature(IFeatureProvider fp) {
		super(fp);
	}

	public boolean canMoveShape(IMoveShapeContext context) {
		if (Graphiti.getPeService().getProperty(context.getShape(), RoutingNet.LANE)!=null) {
			return false;
		}
		ContainerShape targetContainer = context.getTargetContainer();
		if (Graphiti.getPeService().getProperty(targetContainer, RoutingNet.LANE)!=null) {
			int x = context.getX();
			int y = context.getY();
			ILocation loc = Graphiti.getPeService().getLocationRelativeToDiagram(targetContainer);
			((MoveShapeContext)context).setX(x + loc.getX());
			((MoveShapeContext)context).setY(y + loc.getY());
			((MoveShapeContext)context).setSourceContainer(targetContainer.getContainer());
			((MoveShapeContext)context).setTargetContainer(targetContainer.getContainer());
		}
		return context.getSourceContainer() != null
				&& context.getSourceContainer().equals(context.getTargetContainer());
	}

	@Override
	protected void preMoveShape(IMoveShapeContext context) {
		super.preMoveShape(context);
		preShapeX = 0;
		preShapeX = 0;
		
		if (context.getShape().getGraphicsAlgorithm() != null){
			preShapeX = context.getShape().getGraphicsAlgorithm().getX();
			preShapeY = context.getShape().getGraphicsAlgorithm().getY();
		}
	}

	@Override
	protected void postMoveShape(IMoveShapeContext context) {
		DIUtils.updateDIShape(context.getPictogramElement());

		Shape shape = context.getShape();
		// if this shape has a label, align the label so that it is centered below its owner
		for (EObject o : shape.getLink().getBusinessObjects()) {
			if (o instanceof Shape && Graphiti.getPeService().getPropertyValue((Shape)o, GraphicsUtil.LABEL_PROPERTY) != null) {
				// this is it!
				ContainerShape textContainerShape = (ContainerShape)o;
				GraphicsUtil.alignWithShape(
						(AbstractText) textContainerShape.getChildren().get(0).getGraphicsAlgorithm(), 
						textContainerShape,
						shape.getGraphicsAlgorithm().getWidth(),
						shape.getGraphicsAlgorithm().getHeight(),
						shape.getGraphicsAlgorithm().getX(),
						shape.getGraphicsAlgorithm().getY(),
						preShapeX,
						preShapeY
				);
				break;
			}
		}

		ConnectionFeatureContainer.updateConnections(getFeatureProvider(), shape);
		
		for (Connection connection : getDiagram().getConnections()) {
			if (GraphicsUtil.intersects(shape, connection)) {
				if (Graphiti.getPeService().getProperty(connection, RoutingNet.CONNECTION)!=null) {
				ConnectionFeatureContainer.updateConnection(getFeatureProvider(), connection);
				}
			}
		}

	}
}
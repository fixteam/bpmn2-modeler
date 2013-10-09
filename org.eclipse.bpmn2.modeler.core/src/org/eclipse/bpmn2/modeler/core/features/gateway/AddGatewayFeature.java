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
package org.eclipse.bpmn2.modeler.core.features.gateway;

import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.modeler.core.di.DIImport;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2AddElementFeature;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;

public class AddGatewayFeature<T extends Gateway>
	extends AbstractBpmn2AddElementFeature<T> {

	public AddGatewayFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return FeatureSupport.isValidFlowElementTarget(context);
	}

	@Override
	public PictogramElement add(IAddContext context) {
		T businessObject = getBusinessObject(context);
		IGaService gaService = Graphiti.getGaService();
		IPeService peService = Graphiti.getPeService();

		int width = this.getWidth(context);
		int height = this.getHeight(context);
		// for backward compatibility with older files that included
		// the label height in the figure height
		if (width!=height) {
			width = height = Math.min(width, height);
		}
		adjustLocation(context, width, height);
		
		int x = context.getX();
		int y = context.getY();
		
		// Create a container for the gateway-symbol
		final ContainerShape containerShape = peService.createContainerShape(context.getTargetContainer(), true);
		final Rectangle gatewayRect = gaService.createInvisibleRectangle(containerShape);
		gaService.setLocationAndSize(gatewayRect, x, y, width, height);

		Shape gatewayShape = peService.createShape(containerShape, false);
		Polygon gatewayPolygon = GraphicsUtil.createGateway(gatewayShape, width, height);
		StyleUtil.applyStyle(gatewayPolygon, businessObject);
		gaService.setLocationAndSize(gatewayPolygon, 0, 0, width, height);

		boolean isImport = context.getProperty(DIImport.IMPORT_PROPERTY) != null;
		createDIShape(containerShape, businessObject, !isImport);
		
		// hook for subclasses to inject extra code
		decorateShape(context, containerShape, businessObject);
		peService.createChopboxAnchor(containerShape);
		AnchorUtil.addFixedPointAnchors(containerShape, gatewayPolygon);
		
		splitConnection(context, containerShape);
		
		layoutPictogramElement(containerShape);
		
		// Use context for labeling! 
		this.prepareAddContext(context, containerShape, width, height);
		this.getFeatureProvider().getAddFeature(context).add(context);
		
		return containerShape;
	}

	@Override
	public int getHeight() {
		return GraphicsUtil.getGatewaySize(this.getDiagram()).getHeight();
	}

	@Override
	public int getWidth() {
		return GraphicsUtil.getGatewaySize(this.getDiagram()).getWidth();
	}
	
}
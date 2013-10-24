/*******************************************************************************
 * Copyright (c) 2011, 2012, 2013 Red Hat, Inc.
 * All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 	Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.features;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddPictogramElementFeature;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;

public abstract class AbstractBpmn2AddFeature<T extends BaseElement>
	extends AbstractAddPictogramElementFeature
	implements IBpmn2AddFeature<T> {
	
	public AbstractBpmn2AddFeature(IFeatureProvider fp) {
		super(fp);
	}

	/**
	 * Helper function to return the GraphicsAlgorithm for a ContainerShape created by
	 * one of the BPMN2 Modeler's Add features. This can be used by subclasses to decorate
	 * the figure on the canvas.
	 * 
	 * @param containerShape
	 * @return
	 */
	protected static GraphicsAlgorithmContainer getGraphicsAlgorithm(ContainerShape containerShape) {
		if (containerShape.getGraphicsAlgorithm() instanceof RoundedRectangle)
			return containerShape.getGraphicsAlgorithm();
		if (containerShape.getChildren().size()>0) {
			Shape shape = containerShape.getChildren().get(0);
			return shape.getGraphicsAlgorithm();
		}
		return null;
	}
	
	protected void decorateConnection(IAddConnectionContext context, Connection connection, T businessObject) {
	}

	protected void decorateShape(IAddContext context, ContainerShape containerShape, T businessObject) {
	}
}

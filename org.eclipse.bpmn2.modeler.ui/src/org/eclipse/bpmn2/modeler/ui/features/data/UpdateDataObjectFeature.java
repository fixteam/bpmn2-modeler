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
package org.eclipse.bpmn2.modeler.ui.features.data;

import java.util.Iterator;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.DataObject;
import org.eclipse.bpmn2.DataObjectReference;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.modeler.core.features.data.Properties;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.context.impl.UpdateContext;
import org.eclipse.graphiti.features.impl.AbstractUpdateFeature;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IPeService;

public class UpdateDataObjectFeature extends AbstractUpdateFeature {


	public UpdateDataObjectFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canUpdate(IUpdateContext context) {
		Object o = getBusinessObjectForPictogramElement(context.getPictogramElement());
		return o instanceof DataObject || o instanceof DataObjectReference;
	}

	@Override
	public IReason updateNeeded(IUpdateContext context) {
		IPeService peService = Graphiti.getPeService();
		ContainerShape container = (ContainerShape) context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(container);
		DataObject data = null;
		if (bo instanceof DataObject)
			data = (DataObject) bo;
		else if (bo instanceof DataObjectReference)
			data = ((DataObjectReference)bo).getDataObjectRef();
		
		boolean isCollection = Boolean.parseBoolean(peService.getPropertyValue(container,
				Properties.COLLECTION_PROPERTY));
		return data.isIsCollection() != isCollection ? Reason.createTrueReason() : Reason.createFalseReason();
	}

	@Override
	public boolean update(IUpdateContext context) {
		IPeService peService = Graphiti.getPeService();
		ContainerShape container = (ContainerShape) context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(container);
		DataObject data = null;
		if (bo instanceof DataObject)
			data = (DataObject) bo;
		else if (bo instanceof DataObjectReference)
			data = ((DataObjectReference)bo).getDataObjectRef();

		boolean drawCollectionMarker = data.isIsCollection();

		Iterator<Shape> iterator = peService.getAllContainedShapes(container).iterator();
		while (iterator.hasNext()) {
			Shape shape = iterator.next();
			String prop = peService.getPropertyValue(shape, Properties.HIDEABLE_PROPERTY);
			if (prop != null && new Boolean(prop)) {
				Polyline line = (Polyline) shape.getGraphicsAlgorithm();
				line.setLineVisible(drawCollectionMarker);
			}
		}

		peService.setPropertyValue(container, Properties.COLLECTION_PROPERTY,
				Boolean.toString(data.isIsCollection()));
		
		// Also update any DataObjectReferences
		if (bo instanceof DataObject) {
			Definitions definitions = ModelUtil.getDefinitions(data);
			TreeIterator<EObject> iter = definitions.eAllContents();
			while (iter.hasNext()) {
				EObject o = iter.next();
				if (o instanceof DataObjectReference) {
					for (PictogramElement pe : Graphiti.getLinkService().getPictogramElements(getDiagram(), o)) {
						if (pe instanceof ContainerShape) {
							UpdateContext newContext = new UpdateContext(pe);
							IUpdateFeature f = this.getFeatureProvider().getUpdateFeature(newContext);
							f.update(newContext);
						}
					}
				}
			}
		}

		return true;
	}
}

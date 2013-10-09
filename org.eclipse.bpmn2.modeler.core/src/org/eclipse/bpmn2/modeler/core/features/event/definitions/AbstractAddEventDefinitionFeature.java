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
package org.eclipse.bpmn2.modeler.core.features.event.definitions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.CancelEventDefinition;
import org.eclipse.bpmn2.CatchEvent;
import org.eclipse.bpmn2.ConditionalEventDefinition;
import org.eclipse.bpmn2.Event;
import org.eclipse.bpmn2.EventDefinition;
import org.eclipse.bpmn2.MessageEventDefinition;
import org.eclipse.bpmn2.SignalEventDefinition;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.SubProcess;
import org.eclipse.bpmn2.ThrowEvent;
import org.eclipse.bpmn2.TimerEventDefinition;
import org.eclipse.bpmn2.Transaction;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2AddFeature;
import org.eclipse.bpmn2.modeler.core.features.ContextConstants;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil.FillStyle;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.IExecutionInfo;
import org.eclipse.graphiti.features.IFeature;
import org.eclipse.graphiti.features.IFeatureAndContext;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;

public abstract class AbstractAddEventDefinitionFeature<T extends EventDefinition>
	extends AbstractBpmn2AddFeature<T> {


	public AbstractAddEventDefinitionFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		Object bo = getBusinessObjectForPictogramElement(context.getTargetContainer());
		Object ed = context.getNewObject();
		if (bo instanceof Event && ed instanceof EventDefinition) {
			List<EClass> allowedItems = FeatureSupport.getAllowedEventDefinitions((Event) bo);
			if (allowedItems.contains(((EventDefinition)ed).eClass()))
				return true;
		}
		return false;
	}

	@Override
	public PictogramElement add(IAddContext context) {
		ContainerShape containerShape = context.getTargetContainer();
		T businessObject = getBusinessObject(context);

		// hook for subclasses to inject extra code
		decorateShape(context, containerShape, businessObject);
		return null;
	}
	
	protected void decorateShape(IAddContext context, ContainerShape containerShape, T businessObject) {
		Event event = (Event) getBusinessObjectForPictogramElement(containerShape);
		draw(event, businessObject, containerShape);
	}

	public void draw(Event event, EventDefinition eventDef, ContainerShape container) {
		if (FeatureSupport.isLabelShape(container)) {
			// don't draw decorators on Labels
			return;
		}

		List<EventDefinition> eventDefinitions = ModelUtil.getEventDefinitions(event);
		int size = eventDefinitions.size();

		GraphicsUtil.deleteEventShape(container);
		if (size > 1) {
			Shape multipleShape = Graphiti.getPeService().createShape(container, false);
			drawForEvent(event, multipleShape);
			link(multipleShape, eventDefinitions.toArray(new EventDefinition[size]));
		} else {
			Shape addedShape = getDecorationAlgorithm(event).draw(container);
			if (addedShape==null) {
				addedShape = Graphiti.getPeService().createShape(container, false);
				GraphicsUtil.createEventNotAllowed(addedShape);
			}
			link(addedShape, eventDef);
		}
	}

	public abstract DecorationAlgorithm getDecorationAlgorithm(Event event);

	private void drawForEvent(Event event, Shape shape) {
		if(event instanceof CatchEvent && ((CatchEvent) event).isParallelMultiple()) {
			drawParallelMultiple(event, shape);
		} else {
			drawMultiple(event, shape);
		}
	}
	
	private void drawMultiple(Event event, Shape shape) {
		BaseElement be = BusinessObjectUtil.getFirstElementOfType(shape, BaseElement.class, true);
		Polygon pentagon = GraphicsUtil.createEventPentagon(shape);
		if (event instanceof ThrowEvent) {
			StyleUtil.setFillStyle(pentagon, FillStyle.FILL_STYLE_FOREGROUND);
		} else {
			StyleUtil.setFillStyle(pentagon, FillStyle.FILL_STYLE_BACKGROUND);
		}
		StyleUtil.applyStyle(pentagon, be);
	}
	
	private void drawParallelMultiple(Event event, Shape shape) {
		BaseElement be = BusinessObjectUtil.getFirstElementOfType(shape, BaseElement.class, true);
		Polygon cross = GraphicsUtil.createEventParallelMultiple(shape);
		StyleUtil.setFillStyle(cross, FillStyle.FILL_STYLE_BACKGROUND);
		StyleUtil.applyStyle(cross, be);
	}

	@Override
	public T getBusinessObject(IAddContext context) {
		Object businessObject = context.getProperty(ContextConstants.BUSINESS_OBJECT);
		if (businessObject instanceof EventDefinition)
			return (T)businessObject;
		return (T)context.getNewObject();
	}

	@Override
	public void putBusinessObject(IAddContext context, T businessObject) {
		context.putProperty(ContextConstants.BUSINESS_OBJECT, businessObject);
	}

	@Override
	public void postExecute(IExecutionInfo executionInfo) {
		List<PictogramElement> pes = new ArrayList<PictogramElement>();
		for (IFeatureAndContext fc : executionInfo.getExecutionList()) {
			IContext context = fc.getContext();
			IFeature feature = fc.getFeature();
			if (context instanceof AddContext) {
				AddContext ac = (AddContext)context;
				pes.add(ac.getTargetContainer());
			}
		}
		getDiagramEditor().setPictogramElementsForSelection(pes.toArray(new PictogramElement[pes.size()]));
	}
	
}
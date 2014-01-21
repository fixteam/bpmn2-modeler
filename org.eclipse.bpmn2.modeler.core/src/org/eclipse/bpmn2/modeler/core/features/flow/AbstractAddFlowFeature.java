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
package org.eclipse.bpmn2.modeler.core.features.flow;

import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.EventDefinition;
import org.eclipse.bpmn2.MessageEventDefinition;
import org.eclipse.bpmn2.modeler.core.di.DIImport;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2AddElementFeature;
import org.eclipse.bpmn2.modeler.core.features.label.UpdateLabelFeature;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.bpmn2.modeler.core.utils.Tuple;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.FixPointAnchor;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;

public abstract class AbstractAddFlowFeature<T extends BaseElement>
	extends AbstractBpmn2AddElementFeature<T> {

	public AbstractAddFlowFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		if (context instanceof IAddConnectionContext) {
			IAddConnectionContext acc = (IAddConnectionContext) context;
			if (acc.getSourceAnchor() != null) {
				Object obj = BusinessObjectUtil.getFirstElementOfType(
						acc.getSourceAnchor().getParent(), BaseElement.class);
				if (obj instanceof EndEvent) {
					List<EventDefinition> eventDefinitions = ((EndEvent) obj).getEventDefinitions();
					for (EventDefinition eventDefinition : eventDefinitions) {
						if (eventDefinition instanceof MessageEventDefinition) {
							return true;
						}
					}
					return false;
				}
			}
			return getBoClass().isAssignableFrom(getBusinessObject(context).getClass());
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.func.IAdd#add(org.eclipse.graphiti.features.context.IAddContext)
	 */
	@Override
	public PictogramElement add(IAddContext context) {
		IPeService peService = Graphiti.getPeService();
		IGaService gaService = Graphiti.getGaService();
 
		T businessObject = getBusinessObject(context);
		IAddConnectionContext addContext = (IAddConnectionContext) context;
		AnchorContainer sourceContainer = addContext.getSourceAnchor().getParent();
		AnchorContainer targetContainer = addContext.getTargetAnchor().getParent();

		Connection connection = peService.createFreeFormConnection(getDiagram());
		
		if (AnchorUtil.useAdHocAnchors(sourceContainer, connection)) {
			Point p = (Point)addContext.getProperty(AnchorUtil.CONNECTION_SOURCE_LOCATION);
			if (p!=null) {
				peService.setPropertyValue(connection, AnchorUtil.CONNECTION_SOURCE_LOCATION,
						AnchorUtil.pointToString(p));
			}
		}
		
		if (AnchorUtil.useAdHocAnchors(targetContainer, connection)) {
			// Fetch the source and target locations of the connection copied from the
			// CreateConnectionContext and copy them as String properties to the Connection
			// @see AbstractCreateFlowFeature.create()
			Point p = (Point)addContext.getProperty(AnchorUtil.CONNECTION_TARGET_LOCATION);
			if (p!=null) {
				peService.setPropertyValue(connection, AnchorUtil.CONNECTION_TARGET_LOCATION,
						AnchorUtil.pointToString(p));
			}
		}
		
		if (addContext.getProperty(AnchorUtil.CONNECTION_CREATED)!=null) {
			peService.setPropertyValue(connection, AnchorUtil.CONNECTION_CREATED, "true"); //$NON-NLS-1$
		}
		
		Anchor sourceAnchor = addContext.getSourceAnchor();
		Anchor targetAnchor = addContext.getTargetAnchor();
		Object importProp = context.getProperty(DIImport.IMPORT_PROPERTY);
		if (importProp != null && (Boolean) importProp) {
			connection.setStart(sourceAnchor);
			connection.setEnd(targetAnchor);
		} else {
			if (sourceAnchor==null || targetAnchor==null) {
				Tuple<FixPointAnchor, FixPointAnchor> anchors = AnchorUtil.getSourceAndTargetBoundaryAnchors(
					sourceContainer, targetContainer, null);
				sourceAnchor = anchors.getFirst();
				targetAnchor = anchors.getSecond();
			}
			else {
				Tuple<FixPointAnchor, FixPointAnchor> anchors = AnchorUtil.getSourceAndTargetBoundaryAnchors(
						sourceContainer, targetContainer, connection);
					sourceAnchor = anchors.getFirst();
					targetAnchor = anchors.getSecond();
			}

			connection.setStart(sourceAnchor);
			connection.setEnd(targetAnchor);
		}
		
		if (ModelUtil.hasName(businessObject)) {
			ConnectionDecorator labelDecorator = Graphiti.getPeService().createConnectionDecorator(connection, true, 0.5, true);
			Text text = gaService.createText(labelDecorator, ModelUtil.getName(businessObject));
			peService.setPropertyValue(labelDecorator, UpdateLabelFeature.TEXT_ELEMENT, Boolean.toString(true));
			StyleUtil.applyStyle(text, businessObject);
			peService.setPropertyValue(labelDecorator, GraphicsUtil.LABEL_PROPERTY, Boolean.toString(true));
			link(labelDecorator, businessObject);
		}

		createDIEdge(connection, businessObject);
		createConnectionLine(connection);
		
		decorateConnection(addContext, connection, businessObject);

		return connection;
	}
	
	@Override
	public int getHeight() {
		return -1;
	}

	@Override
	public int getWidth() {
		return -1;
	}

	protected abstract Class<? extends BaseElement> getBoClass();

	protected Polyline createConnectionLine(Connection connection) {
		BaseElement be = BusinessObjectUtil.getFirstBaseElement(connection);
		Polyline connectionLine = Graphiti.getGaService().createPolyline(connection);
		StyleUtil.applyStyle(connectionLine, be);

		return connectionLine;
	}
}
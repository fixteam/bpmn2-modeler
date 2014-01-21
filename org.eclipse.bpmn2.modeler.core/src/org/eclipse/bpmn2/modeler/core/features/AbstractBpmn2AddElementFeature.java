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

import java.io.IOException;

import org.eclipse.bpmn2.Association;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.MessageFlow;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNEdge;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.di.BpmnDiFactory;
import org.eclipse.bpmn2.modeler.core.Activator;
import org.eclipse.bpmn2.modeler.core.di.DIImport;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.features.flow.AbstractCreateFlowFeature;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.dd.dc.Bounds;
import org.eclipse.dd.dc.DcFactory;
import org.eclipse.dd.dc.Point;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.IExecutionInfo;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReconnectionFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ITargetContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.context.impl.CreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.ReconnectionContext;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.FixPointAnchor;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.ILayoutService;

public abstract class AbstractBpmn2AddElementFeature<T extends BaseElement>
	extends AbstractBpmn2AddFeature<T> {

	public AbstractBpmn2AddElementFeature(IFeatureProvider fp) {
		super(fp);
	}

	protected BPMNShape findDIShape(BaseElement elem) {
		try {
			return DIUtils.findBPMNShape(elem);
		} catch (Exception e) {
			Activator.logError(e);
		}
		return null;
	}
	
	protected BPMNShape createDIShape(Shape gShape, BaseElement elem, boolean applyDefaults) {
		return createDIShape(gShape, elem, findDIShape(elem), applyDefaults);
	}

	protected BPMNShape createDIShape(Shape shape, BaseElement elem, BPMNShape bpmnShape, boolean applyDefaults) {
		ILocation loc = Graphiti.getLayoutService().getLocationRelativeToDiagram(shape);
		if (bpmnShape == null) {
			int x = loc.getX();
			int y = loc.getY();
			int w = shape.getGraphicsAlgorithm().getWidth();
			int h = shape.getGraphicsAlgorithm().getHeight();
			bpmnShape = DIUtils.createDIShape(shape, elem, x, y, w, h, getFeatureProvider(), getDiagram());
		}
		else {
			link(shape, new Object[] { elem, bpmnShape });
		}
		if (applyDefaults && bpmnShape!=null)
			Bpmn2Preferences.getInstance(bpmnShape.eResource()).applyBPMNDIDefaults(bpmnShape, null);
		return bpmnShape;
	}

	protected BPMNEdge createDIEdge(Connection connection, BaseElement conElement) {
		try {
			BPMNEdge edge = DIUtils.findBPMNEdge(conElement);
			return createDIEdge(connection, conElement, edge);
		} catch (IOException e) {
			Activator.logError(e);
		}
		return null;
	}

	// TODO: move this to DIUtils
	protected BPMNEdge createDIEdge(Connection connection, BaseElement conElement, BPMNEdge edge) throws IOException {
		if (edge == null) {
			EList<EObject> businessObjects = Graphiti.getLinkService().getLinkForPictogramElement(getDiagram())
					.getBusinessObjects();
			for (EObject eObject : businessObjects) {
				if (eObject instanceof BPMNDiagram) {
					BPMNDiagram bpmnDiagram = (BPMNDiagram) eObject;

					edge = BpmnDiFactory.eINSTANCE.createBPMNEdge();
//					edge.setId(EcoreUtil.generateUUID());
					edge.setBpmnElement(conElement);

					if (conElement instanceof Association) {
						edge.setSourceElement(DIUtils.findDiagramElement(
								((Association) conElement).getSourceRef()));
						edge.setTargetElement(DIUtils.findDiagramElement(
								((Association) conElement).getTargetRef()));
					} else if (conElement instanceof MessageFlow) {
						edge.setSourceElement(DIUtils.findDiagramElement(
								(BaseElement) ((MessageFlow) conElement).getSourceRef()));
						edge.setTargetElement(DIUtils.findDiagramElement(
								(BaseElement) ((MessageFlow) conElement).getTargetRef()));
					} else if (conElement instanceof SequenceFlow) {
						edge.setSourceElement(DIUtils.findDiagramElement(
								((SequenceFlow) conElement).getSourceRef()));
						edge.setTargetElement(DIUtils.findDiagramElement(
								((SequenceFlow) conElement).getTargetRef()));
					}

					ILocation sourceLoc = Graphiti.getPeService().getLocationRelativeToDiagram(connection.getStart());
					ILocation targetLoc = Graphiti.getPeService().getLocationRelativeToDiagram(connection.getEnd());

					Point point = DcFactory.eINSTANCE.createPoint();
					point.setX(sourceLoc.getX());
					point.setY(sourceLoc.getY());
					edge.getWaypoint().add(point);

					point = DcFactory.eINSTANCE.createPoint();
					point.setX(targetLoc.getX());
					point.setY(targetLoc.getY());
					edge.getWaypoint().add(point);

					DIUtils.addShape(edge, bpmnDiagram);
					ModelUtil.setID(edge);
				}
			}
		}
		link(connection, new Object[] { conElement, edge });
		return edge;
	}
	
	protected void prepareAddContext(IAddContext context, PictogramElement labelOwner, int width, int height) {
		context.putProperty(ContextConstants.LABEL_CONTEXT, true);
		context.putProperty(ContextConstants.WIDTH, width);
		context.putProperty(ContextConstants.HEIGHT, height);
		context.putProperty(ContextConstants.BUSINESS_OBJECT, getBusinessObject(context));
		context.putProperty(ContextConstants.LABEL_OWNER, labelOwner);
	}
	
	protected void adjustLocation(IAddContext context, int width, int height) {
		if (context.getProperty(DIImport.IMPORT_PROPERTY) != null) {
			return;
		}
		
		int x = context.getX();
		int y = context.getY();
		((AddContext)context).setWidth(width);
		((AddContext)context).setHeight(height);
		
		y -= height/2;
		x -= width / 2;
		((AddContext)context).setY(y);
		((AddContext)context).setX(x);
	}

	protected void splitConnection(IAddContext context, ContainerShape containerShape) {
		if (context.getProperty(DIImport.IMPORT_PROPERTY) != null) {
			return;
		}
		
		Object newObject = getBusinessObject(context);
		Connection connection = context.getTargetConnection();
		if (connection!=null) {
			// determine how to split the line depending on where the new object was dropped:
			// the longer segment will remain the original connection, and a new connection
			// will be created for the shorter segment
			ILayoutService layoutService = Graphiti.getLayoutService();
			Anchor a0 = connection.getStart();
			Anchor a1 = connection.getEnd();
			double x0 = layoutService.getLocationRelativeToDiagram(a0).getX();
			double y0 = layoutService.getLocationRelativeToDiagram(a0).getY();
			double x1 = layoutService.getLocationRelativeToDiagram(a1).getX();
			double y1 = layoutService.getLocationRelativeToDiagram(a1).getY();
			double dx = x0 - context.getX();
			double dy = y0 - context.getY();
			double len0 = Math.sqrt(dx*dx + dy*dy);
			dx = context.getX() - x1;
			dy = context.getY() - y1;
			double len1 = Math.sqrt(dx*dx + dy*dy);

			AnchorContainer oldSourceContainer = connection.getStart().getParent();
			AnchorContainer oldTargetContainer = connection.getEnd().getParent();
			BaseElement baseElement = BusinessObjectUtil.getFirstElementOfType(connection, BaseElement.class);
			ILocation targetLocation = layoutService.getLocationRelativeToDiagram(containerShape);
			
			ReconnectionContext rc;
			FixPointAnchor anchor;
			
			if (newObject instanceof StartEvent || (len0 < len1 && !(newObject instanceof EndEvent))) {
				anchor = AnchorUtil.findNearestAnchor(containerShape, GraphicsUtil.getShapeCenter(oldTargetContainer));
				rc = new ReconnectionContext(connection, connection.getStart(), anchor, targetLocation);
				rc.setReconnectType(ReconnectionContext.RECONNECT_SOURCE);
				rc.setTargetPictogramElement(containerShape);
			}
			else {
				anchor = AnchorUtil.findNearestAnchor(oldTargetContainer, GraphicsUtil.getShapeCenter(containerShape));
				rc = new ReconnectionContext(connection, connection.getEnd(), anchor, targetLocation);
				rc.setReconnectType(ReconnectionContext.RECONNECT_TARGET);
				rc.setTargetPictogramElement(containerShape);
			}
			IReconnectionFeature rf = getFeatureProvider().getReconnectionFeature(rc);
			rf.reconnect(rc);
			
			if (!(newObject instanceof EndEvent) && !(newObject instanceof StartEvent)) {
				// connection = get create feature, create connection
				CreateConnectionContext ccc = new CreateConnectionContext();
				if (len0 < len1) {
					ccc.setSourcePictogramElement(oldSourceContainer);
					ccc.setTargetPictogramElement(containerShape);
					anchor = AnchorUtil.findNearestAnchor(oldSourceContainer, GraphicsUtil.getShapeCenter(containerShape));
					ccc.setSourceAnchor(anchor);
					anchor = AnchorUtil.findNearestAnchor(containerShape, GraphicsUtil.getShapeCenter(oldTargetContainer));
					ccc.setTargetAnchor(anchor);
				}
				else {
					ccc.setSourcePictogramElement(containerShape);
					ccc.setTargetPictogramElement(oldTargetContainer);
					anchor = AnchorUtil.findNearestAnchor(containerShape, GraphicsUtil.getShapeCenter(oldTargetContainer));
					ccc.setSourceAnchor(anchor);
					anchor = AnchorUtil.findNearestAnchor(oldTargetContainer, GraphicsUtil.getShapeCenter(containerShape));
					ccc.setTargetAnchor(anchor);
				}
				
				Connection newConnection = null;
				for (ICreateConnectionFeature cf : getFeatureProvider().getCreateConnectionFeatures()) {
					if (cf instanceof AbstractCreateFlowFeature) {
						AbstractCreateFlowFeature acf = (AbstractCreateFlowFeature) cf;
						if (acf.getBusinessObjectClass().isInstance(baseElement)) {
							newConnection = acf.create(ccc);
							DIUtils.updateDIEdge(newConnection);
							break;
						}
					}
				}
			}
			DIUtils.updateDIEdge(connection);
		}
	}
	
	protected int getHeight(IAddContext context) {
		Object copiedBpmnShape = context.getProperty(DefaultPasteBPMNElementFeature.COPIED_BPMN_SHAPE);
		if (copiedBpmnShape instanceof BPMNShape) {
			Bounds b = ((BPMNShape)copiedBpmnShape).getBounds();
			if (b!=null)
				return (int) b.getHeight();
		}
		return context.getHeight() > 0 ? context.getHeight() :
			(isHorizontal(context) ? getHeight() : getWidth());
	}
	
	protected int getWidth(IAddContext context) {
		Object copiedBpmnShape = context.getProperty(DefaultPasteBPMNElementFeature.COPIED_BPMN_SHAPE);
		if (copiedBpmnShape instanceof BPMNShape) {
			Bounds b = ((BPMNShape)copiedBpmnShape).getBounds();
			if (b!=null)
				return (int) b.getWidth();
		}
		return context.getWidth() > 0 ? context.getWidth() :
			(isHorizontal(context) ? getWidth() : getHeight());
	}

	protected boolean isHorizontal(ITargetContext context) {
		if (context.getProperty(DIImport.IMPORT_PROPERTY) == null) {
			// not importing - set isHorizontal to be the same as copied element or parent
			Object copiedBpmnShape = context.getProperty(DefaultPasteBPMNElementFeature.COPIED_BPMN_SHAPE);
			if (copiedBpmnShape instanceof BPMNShape) {
				return ((BPMNShape)copiedBpmnShape).isIsHorizontal();
			}
			
			if (FeatureSupport.isTargetParticipant(context)) {
				Participant targetParticipant = FeatureSupport.getTargetParticipant(context);
				BPMNShape participantShape = findDIShape(targetParticipant);
				if (participantShape!=null)
					return participantShape.isIsHorizontal();
			}
			else if (FeatureSupport.isTargetLane(context)) {
				Lane targetLane = FeatureSupport.getTargetLane(context);
				BPMNShape laneShape = findDIShape(targetLane);
				if (laneShape!=null)
					return laneShape.isIsHorizontal();
			}
		}
		return Bpmn2Preferences.getInstance(context.getTargetContainer()).isHorizontalDefault();
	}
	
	public abstract int getHeight();
	public abstract int getWidth();

	@Override
	public T getBusinessObject(IAddContext context) {
		Object businessObject = context.getProperty(ContextConstants.BUSINESS_OBJECT);
		if (businessObject instanceof BaseElement)
			return (T)businessObject;
		return (T)context.getNewObject();
	}

	@Override
	public void putBusinessObject(IAddContext context, T businessObject) {
		context.putProperty(ContextConstants.BUSINESS_OBJECT, businessObject);
	}

	@Override
	public void postExecute(IExecutionInfo executionInfo) {
	}
}
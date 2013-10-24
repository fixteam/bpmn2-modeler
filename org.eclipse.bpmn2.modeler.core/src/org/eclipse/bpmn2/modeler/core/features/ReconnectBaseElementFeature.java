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

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.DataAssociation;
import org.eclipse.bpmn2.di.BPMNEdge;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.Tuple;
import org.eclipse.dd.di.DiagramElement;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.context.impl.ReconnectionContext;
import org.eclipse.graphiti.features.impl.DefaultReconnectionFeature;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.FixPointAnchor;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;

public class ReconnectBaseElementFeature extends DefaultReconnectionFeature {

	protected boolean changesDone = false;
	
	public ReconnectBaseElementFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canReconnect(IReconnectionContext context) {
		// TODO Auto-generated method stub
		return super.canReconnect(context);
	}

	@Override
	public void preReconnect(IReconnectionContext context) {
		IPeService peService = Graphiti.getPeService();
		IGaService gaService = Graphiti.getGaService();
		Connection connection = context.getConnection();
		FixPointAnchor newAnchor = null;
		
		AnchorContainer source = connection.getStart().getParent();
		AnchorContainer target = connection.getEnd().getParent();
		Tuple<FixPointAnchor, FixPointAnchor> anchors = null;
		if (context.getReconnectType().equals(ReconnectionContext.RECONNECT_TARGET)) {
			target = (AnchorContainer) context.getTargetPictogramElement();
//				if (AnchorUtil.useAdHocAnchors(target, connection))
			if (true)
			{
				ILocation targetLoc = context.getTargetLocation();
				ILocation shapeLoc = peService.getLocationRelativeToDiagram((Shape)target);
				Point p = gaService.createPoint(targetLoc.getX() - shapeLoc.getX(), targetLoc.getY() - shapeLoc.getY());
				peService.setPropertyValue(connection, AnchorUtil.CONNECTION_TARGET_LOCATION,
						AnchorUtil.pointToString(p));
			}
			else {
				peService.setPropertyValue(connection, AnchorUtil.CONNECTION_TARGET_LOCATION, ""); //$NON-NLS-1$
			}
			BendpointConnectionRouter.setMovedBendpoint(connection, Integer.MAX_VALUE);

			peService.setPropertyValue(connection, AnchorUtil.CONNECTION_SOURCE_LOCATION, ""); //$NON-NLS-1$
			anchors = AnchorUtil.getSourceAndTargetBoundaryAnchors(source, target, connection);
			newAnchor = anchors.getSecond();
		}
		else {
			source = (AnchorContainer) context.getTargetPictogramElement();
//				if (AnchorUtil.useAdHocAnchors(source, connection))
			if (true)
			{
				ILocation sourceLoc = context.getTargetLocation();
				ILocation shapeLoc = peService.getLocationRelativeToDiagram((Shape)source);
				Point p = gaService.createPoint(sourceLoc.getX() - shapeLoc.getX(), sourceLoc.getY() - shapeLoc.getY());
				peService.setPropertyValue(connection, AnchorUtil.CONNECTION_SOURCE_LOCATION, AnchorUtil.pointToString(p));
			}
			else {
				peService.setPropertyValue(connection, AnchorUtil.CONNECTION_SOURCE_LOCATION, ""); //$NON-NLS-1$
			}
			BendpointConnectionRouter.setMovedBendpoint(connection, 0);
			peService.setPropertyValue(connection, AnchorUtil.CONNECTION_TARGET_LOCATION, ""); //$NON-NLS-1$
			anchors = AnchorUtil.getSourceAndTargetBoundaryAnchors(source, target, connection);
			newAnchor = anchors.getFirst();
		}

		if (newAnchor!=null)
			((ReconnectionContext)context).setNewAnchor(newAnchor);
		
		super.preReconnect(context);
	}

	@Override
	public void postReconnect(IReconnectionContext context) {
		super.postReconnect(context);

		BPMNEdge edge = BusinessObjectUtil.getFirstElementOfType(context.getConnection(), BPMNEdge.class);
		DiagramElement de = BusinessObjectUtil.getFirstElementOfType(context.getTargetPictogramElement(), DiagramElement.class);
		if (context.getReconnectType().equals(ReconnectionContext.RECONNECT_TARGET)) {
			edge.setTargetElement(de);
		}
		else {
			edge.setSourceElement(de);
		}
		
		Connection connection = context.getConnection();
		BaseElement flow = BusinessObjectUtil.getFirstElementOfType(connection, BaseElement.class);
		if (!(flow instanceof DataAssociation)) {
			BaseElement be = BusinessObjectUtil.getFirstElementOfType(context.getTargetPictogramElement(), BaseElement.class);
			if (context.getReconnectType().equals(ReconnectionContext.RECONNECT_TARGET)) {
				EStructuralFeature feature = flow.eClass().getEStructuralFeature("targetRef"); //$NON-NLS-1$
				if (feature!=null)
					flow.eSet(feature, be);
				AnchorUtil.deleteEmptyAdHocAnchors(connection.getEnd().getParent());
			}
			else {
				EStructuralFeature feature = flow.eClass().getEStructuralFeature("sourceRef"); //$NON-NLS-1$
				if (feature!=null && !feature.isMany())
					flow.eSet(feature, be);
				AnchorUtil.deleteEmptyAdHocAnchors(connection.getStart().getParent());
			}
		}
		
		FeatureSupport.updateConnection(getFeatureProvider(), connection, true);

		DIUtils.updateDIEdge(context.getConnection());
		
		changesDone = true;
	}

	@Override
	public boolean hasDoneChanges() {
		return changesDone;
	}
}
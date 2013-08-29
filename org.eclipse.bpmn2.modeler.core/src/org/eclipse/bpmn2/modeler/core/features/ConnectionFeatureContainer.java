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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IReconnectionFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.impl.LayoutContext;
import org.eclipse.graphiti.features.context.impl.UpdateContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.Shape;

public abstract class ConnectionFeatureContainer implements FeatureContainer {

	public abstract ICreateConnectionFeature getCreateConnectionFeature(IFeatureProvider fp);

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return null;
	}

	@Override
	public IUpdateFeature getUpdateFeature(IFeatureProvider fp) {
		return null;
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(IFeatureProvider fp) {
		return null;
	}

	@Override
	public ILayoutFeature getLayoutFeature(IFeatureProvider fp) {
		return new DefaultLayoutBPMNConnectionFeature(fp);
	}

	@Override
	public IMoveShapeFeature getMoveFeature(IFeatureProvider fp) {
		return null;
	}

	@Override
	public IResizeShapeFeature getResizeFeature(IFeatureProvider fp) {
		return null;
	}

	@Override
	public IDeleteFeature getDeleteFeature(IFeatureProvider context) {
		return null;
	}
	
	public IReconnectionFeature getReconnectionFeature(IFeatureProvider fp) {
		return null;
	}
	
	@Override
	public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
		return null;
	}

	public static void updateConnections(IFeatureProvider fp, AnchorContainer ac) {
		List<Connection> alreadyUpdated = new ArrayList<Connection>();
		if (ac instanceof ContainerShape) {
			for (Shape child : ((ContainerShape)ac).getChildren()) {
				if (child instanceof ContainerShape)
					updateConnections(fp, child, alreadyUpdated);
			}
		}
		updateConnections(fp, ac, alreadyUpdated);
	}
	
	private static void updateConnections(IFeatureProvider fp, AnchorContainer ac, List<Connection> alreadyUpdated) {
		for (int ai=0; ai<ac.getAnchors().size(); ++ai) {
			Anchor a = ac.getAnchors().get(ai);
			for (int ci=0; ci<a.getIncomingConnections().size(); ++ci) {
				Connection c = a.getIncomingConnections().get(ci);
				if (c instanceof FreeFormConnection) {
					if (!alreadyUpdated.contains(c)) {
						updateConnection(fp, c, true);
						alreadyUpdated.add(c);
					}
				}
			}
		}
		
		for (int ai=0; ai<ac.getAnchors().size(); ++ai) {
			Anchor a = ac.getAnchors().get(ai);
			for (int ci=0; ci<a.getOutgoingConnections().size(); ++ci) {
				Connection c = a.getOutgoingConnections().get(ci);
				if (c instanceof FreeFormConnection) {
					if (!alreadyUpdated.contains(c)) {
						updateConnection(fp, c, true);
						alreadyUpdated.add(c);
					}
				}
			}
		}
	}
	
	public static boolean updateConnection(IFeatureProvider fp, Connection connection, boolean force) {
		AbstractConnectionRouter.setForceRouting(connection, force);
		return updateConnection(fp,connection);
	}

	public static boolean updateConnection(IFeatureProvider fp, Connection connection) {
		boolean layoutChanged = false;
		LayoutContext layoutContext = new LayoutContext(connection);
		ILayoutFeature layoutFeature = fp.getLayoutFeature(layoutContext);
		if (layoutFeature!=null) {
			layoutFeature.layout(layoutContext);
			layoutChanged = layoutFeature.hasDoneChanges();
		}
		
		boolean updateChanged = false;
		UpdateContext updateContext = new UpdateContext(connection);
		IUpdateFeature updateFeature = fp.getUpdateFeature(updateContext);
		if (updateFeature!=null && updateFeature.updateNeeded(updateContext).toBoolean()) {
			updateFeature.update(updateContext);
			updateChanged = updateFeature.hasDoneChanges();
		}
		
		return layoutChanged || updateChanged;
	}
}
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
 * @author Bob Brodt
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.features;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNEdge;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.dd.di.DiagramElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;

/**
 * This class provides Graphiti features for the BPMNDiagram. Currently, it is only used
 * to contribute context menu actions for Connection Routing.
 */
public class BPMNDiagramFeatureContainer extends BaseElementFeatureContainer {

	public BPMNDiagramFeatureContainer() {
	}

	@Override
	public Object getApplyObject(IContext context) {
		if (context instanceof ICustomContext) {
			PictogramElement[] pes = ((ICustomContext) context).getPictogramElements();
			if (pes.length==1)
				return BusinessObjectUtil.getFirstElementOfType(pes[0], BPMNDiagram.class);
		}
		return null;
	}

	@Override
	public boolean canApplyTo(Object o) {
		return o instanceof BPMNDiagram;
	}

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return null;
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
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
		return null;
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
	public IDeleteFeature getDeleteFeature(IFeatureProvider fp) {
		return null;
	}

	@Override
	public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
		return new ICustomFeature[] {
				new EnableConnectionRoutingFeature(fp),
				new LayoutConnectionsFeature(fp)
			};
	}

	/**
	 * Context menu action to globally enable or disable the (still experimental)
	 * Manhattan Connection Router.
	 */
	public class EnableConnectionRoutingFeature extends AbstractCustomFeature {

		Bpmn2Preferences preferences;
		
		public EnableConnectionRoutingFeature(IFeatureProvider fp) {
			super(fp);
			Diagram diagram = fp.getDiagramTypeProvider().getDiagram();
			EObject bo = BusinessObjectUtil.getBusinessObjectForPictogramElement(diagram);
			preferences = Bpmn2Preferences.getInstance(bo);
		}
		
		@Override
		public boolean canExecute(ICustomContext context) {
			return true;
		}

		@Override
		public boolean isAvailable(IContext context) {
			return true;
		}

		@Override
		public String getName() {
			if (preferences.getEnableConnectionRouting())
//				return "Disable automatic Connection Routing";
				return "关闭自动连线";
//			return "Enable automatic Connection Routing";
			return "开启自动连线";
		}

		@Override
		public String getDescription() {
			return "";
		}

		@Override
		public void execute(ICustomContext context) {
			boolean enabled = preferences.getEnableConnectionRouting();
			preferences.setEnableConnectionRouting(!enabled);
		}
	}
	
	/**
	 * Context menu action to force automatic Connection Routing on all connections in
	 * the Diagram.
	 */
	public class LayoutConnectionsFeature extends AbstractCustomFeature {

		boolean hasDoneChanges = false;
		
		public LayoutConnectionsFeature(IFeatureProvider fp) {
			super(fp);
		}
		
		@Override
		public String getName() {
//			return "Re-route all Connections";
			return "重新连接所有线条(需开启自动连线)";
		}

		@Override
		public String getDescription() {
//			return "Re-route all connections in the current diagram using the selected routing strategy";
			return "重新连接所有流程图中的线条";
		}

		@Override
		public boolean canExecute(ICustomContext context) {
			PictogramElement[] pes = context.getPictogramElements();
			EObject businessObject = BusinessObjectUtil.getBusinessObjectForPictogramElement(pes[0]);
			return businessObject instanceof BPMNDiagram;
		}

		@Override
		public boolean hasDoneChanges() {
			return hasDoneChanges;
		}

		@Override
		public void execute(ICustomContext context) {
			PictogramElement[] pes = context.getPictogramElements();
			EObject businessObject = BusinessObjectUtil.getBusinessObjectForPictogramElement(pes[0]);
			BPMNDiagram bpmnDiagram = (BPMNDiagram)businessObject;
			Diagram diagram = getFeatureProvider().getDiagramTypeProvider().getDiagram();
			
			// NOTE: this only operates on the current BPMNDiagram tab. If, for example, the contents
			// of a SubProcess are contained in a different BPMNDiagram (i.e. a different tab of the
			// multi-page editor), those connections will NOT be affected.
			for (DiagramElement de : bpmnDiagram.getPlane().getPlaneElement()) {
				if (de instanceof BPMNEdge) {
					BaseElement be = ((BPMNEdge)de).getBpmnElement();
					for (PictogramElement pe : Graphiti.getLinkService().getPictogramElements(diagram, be)) {
						if (pe instanceof Connection) {
							// force the default routing to happen
							if (ConnectionFeatureContainer.updateConnection(getFeatureProvider(),
									(Connection)pe, true))
								hasDoneChanges = true;
						}
					}
				}
			}
		}
	}
}

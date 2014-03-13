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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.MessageFlow;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.modeler.core.Activator;
import org.eclipse.bpmn2.modeler.core.ModelHandler;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.context.impl.DeleteContext;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.Shape;

public class MoveFlowNodeFeature extends DefaultMoveBPMNShapeFeature {

	private final List<Algorithm> algorithms;
	private AlgorithmContainer algorithmContainer;
	protected IMoveShapeContext context;
	
	public MoveFlowNodeFeature(IFeatureProvider fp) {
		super(fp);
		algorithms = new ArrayList<MoveFlowNodeFeature.Algorithm>();
		algorithms.add(new FromLaneAlgorithm());
		algorithms.add(new ToLaneAlgorithm());
		algorithms.add(new FromParticipantAlgorithm());
		algorithms.add(new ToParticipantAlgorithm());
		algorithms.add(new FromFlowElementsContainerAlgorithm());
		algorithms.add(new ToFlowElementsContainerAlgorithm());
	}

	@Override
	public boolean canMoveShape(IMoveShapeContext context) {
		if (!(getBusinessObjectForPictogramElement(context.getShape()) instanceof FlowNode)) {
			return false;
		}

		try {
			ModelHandler handler = ModelHandler.getInstance(getDiagram());

			algorithmContainer = getAlgorithmContainer(context);

			if (algorithmContainer.isEmpty()) {
				return onMoveAlgorithmNotFound(context);
			}

			return algorithmContainer.isMoveAllowed(getSourceBo(context, handler), getTargetBo(context, handler));
		} catch (IOException e) {
			Activator.logError(e);
		}

		return false;
	}

	protected boolean onMoveAlgorithmNotFound(IMoveShapeContext context) {
		return super.canMoveShape(context);
	}

	@Override
	public void moveShape(IMoveShapeContext context) {
		this.context = context;
		super.moveShape(context);
	}

	@Override
	protected void postMoveShape(IMoveShapeContext context) {
		try {
			ModelHandler handler = ModelHandler.getInstance(getDiagram());
			Object[] nodes = getAllBusinessObjectsForPictogramElement(context.getShape());
			for (Object object : nodes) {
				if (object instanceof FlowNode && algorithmContainer!=null && !algorithmContainer.isEmpty()) {
					algorithmContainer.move(((FlowNode) object), getSourceBo(context, handler),
							getTargetBo(context, handler));
				}
			}
		} catch (Exception e) {
			Activator.logError(e);
		}
		super.postMoveShape(context);
	}

	private Object getSourceBo(IMoveShapeContext context, ModelHandler handler) {
		if (context.getSourceContainer().equals(getDiagram()))
			return handler.getFlowElementContainer(context.getSourceContainer());
		return getBusinessObjectForPictogramElement(context.getSourceContainer());
	}

	private Object getTargetBo(IMoveShapeContext context, ModelHandler handler) {
		if (context.getTargetContainer().equals(getDiagram()))
			return handler.getFlowElementContainer(context.getTargetContainer());
		return getBusinessObjectForPictogramElement(context.getTargetContainer());
	}

	private boolean isSourceParticipant(IMoveShapeContext context) {
		Object bo = getBusinessObjectForPictogramElement(context.getSourceContainer());
		return context.getSourceContainer().equals(getDiagram()) || (bo != null && bo instanceof Participant);
	}

	private boolean isSourceLane(IMoveShapeContext context) {
		Object bo = getBusinessObjectForPictogramElement(context.getSourceContainer());
		return bo != null && bo instanceof Lane;
	}

	class AlgorithmContainer {
		public Algorithm fromAlgorithm;
		public Algorithm toAlgorithm;

		public AlgorithmContainer(Algorithm fromAlgorithm, Algorithm toAlgorithm) {
			this.fromAlgorithm = fromAlgorithm;
			this.toAlgorithm = toAlgorithm;
		}

		boolean isMoveAllowed(Object source, Object target) {
			return fromAlgorithm.isMoveAllowed(source, target) && toAlgorithm.isMoveAllowed(source, target);
		}

		void move(FlowNode node, Object source, Object target) {
			fromAlgorithm.move(node, source, target);
			toAlgorithm.move(node, source, target);
			// If flow node was moved from one Pool to another, delete all
			// incoming and outgoing Sequence Flows; if flow node was connect
			// to another flow node by a Message Flow, and it is moved into the
			// same Pool as the other flow node, delete the Message Flows.
			List<Connection> connections = new ArrayList<Connection>();
			IFeatureProvider fp = MoveFlowNodeFeature.this.getFeatureProvider();
			Shape shape = context.getShape();
			for (Anchor a : shape.getAnchors()) {
				for (Connection c : a.getIncomingConnections()) {
					BaseElement cbo = BusinessObjectUtil.getFirstBaseElement(c);
					if (cbo instanceof SequenceFlow || cbo instanceof MessageFlow) {
						if (!isConnectionValid(cbo))
							connections.add(c);
					}
				}
				for (Connection c : a.getOutgoingConnections()) {
					BaseElement cbo = BusinessObjectUtil.getFirstBaseElement(c);
					if (cbo instanceof SequenceFlow || cbo instanceof MessageFlow) {
						if (!isConnectionValid(cbo))
							connections.add(c);
					}
				}
			}
			for (Connection c : connections) {
				DeleteContext dc = new DeleteContext(c);
				IDeleteFeature df = fp.getDeleteFeature(dc);
				df.delete(dc);
			}
		}

		boolean isEmpty() {
			return fromAlgorithm == null || toAlgorithm == null;
		}
		
		boolean isConnectionValid(BaseElement flow) {
			EStructuralFeature sourceRef = flow.eClass().getEStructuralFeature("sourceRef");
			EStructuralFeature targetRef = flow.eClass().getEStructuralFeature("targetRef");
			BaseElement source = (BaseElement) flow.eGet(sourceRef);
			BaseElement target = (BaseElement) flow.eGet(targetRef);
			EObject sourceContainer = source.eContainer();
			while (sourceContainer!=null) {
				if (sourceContainer instanceof FlowElementsContainer)
					break;
				sourceContainer = sourceContainer.eContainer();
			}
			if (sourceContainer==null)
				return true;
			EObject targetContainer = target.eContainer();
			while (targetContainer!=null) {
				if (targetContainer instanceof FlowElementsContainer)
					break;
				targetContainer = targetContainer.eContainer();
			}
			if (targetContainer==null)
				return true;
			if (flow instanceof SequenceFlow)
				return sourceContainer==targetContainer;
			if (flow instanceof MessageFlow)
				return sourceContainer!=targetContainer;
			return false;
		}
	}

	private AlgorithmContainer getAlgorithmContainer(IMoveShapeContext context) {
		Algorithm fromAlgorithm = null;
		Algorithm toAlgorithm = null;

		for (Algorithm a : algorithms) {
			if (a.canApplyTo(context)) {
				switch (a.getType()) {
				case Algorithm.TYPE_FROM:
					fromAlgorithm = a;
					break;
				case Algorithm.TYPE_TO:
					toAlgorithm = a;
					break;
				}
			}
		}

		return new AlgorithmContainer(fromAlgorithm, toAlgorithm);
	}

	interface Algorithm {

		int TYPE_FROM = 0;

		int TYPE_TO = 1;

		int getType();

		boolean canApplyTo(IMoveShapeContext context);

		boolean isMoveAllowed(Object source, Object target);

		void move(FlowNode node, Object source, Object target);
	}

	abstract class DefaultAlgorithm implements Algorithm {

		@Override
		public boolean isMoveAllowed(Object source, Object target) {
			return true;
		}

		@Override
		public void move(FlowNode node, Object source, Object target) {
			try {
				ModelHandler handler = ModelHandler.getInstance(getDiagram());
				handler.moveFlowNode(node, source, target);
			} catch (IOException e) {
				Activator.logError(e);
			}
		}
	}

	class FromLaneAlgorithm extends DefaultAlgorithm {

		@Override
		public int getType() {
			return TYPE_FROM;
		}

		@Override
		public boolean canApplyTo(IMoveShapeContext context) {
			return isSourceLane(context);
		}

		@Override
		public void move(FlowNode node, Object source, Object target) {
			Lane lane = (Lane) source;
			lane.getFlowNodeRefs().remove(node);
			node.getLanes().remove(lane);
		}
	}

	class ToLaneAlgorithm extends DefaultAlgorithm {

		@Override
		public int getType() {
			return TYPE_TO;
		}

		@Override
		public boolean canApplyTo(IMoveShapeContext context) {
			return FeatureSupport.isTargetLane(context);
		}

		@Override
		public boolean isMoveAllowed(Object source, Object target) {
			Lane lane = (Lane) target;
			return lane.getChildLaneSet() == null || lane.getChildLaneSet().getLanes().isEmpty();
		}

		@Override
		public void move(FlowNode node, Object source, Object target) {
			Lane lane = (Lane) target;
			lane.getFlowNodeRefs().add(node);
			node.getLanes().add(lane);
			super.move(node, source, target);
		}
	}

	class FromParticipantAlgorithm extends DefaultAlgorithm {

		@Override
		public int getType() {
			return TYPE_FROM;
		}

		@Override
		public boolean canApplyTo(IMoveShapeContext context) {
			return isSourceParticipant(context);
		}

		@Override
		public void move(FlowNode node, Object source, Object target) {
			// DO NOTHING HERE
		}
	}

	class ToParticipantAlgorithm extends DefaultAlgorithm {

		@Override
		public int getType() {
			return TYPE_TO;
		}

		@Override
		public boolean canApplyTo(IMoveShapeContext context) {
			return context.getTargetContainer().equals(getDiagram()) || FeatureSupport.isTargetParticipant(context);
		}

		@Override
		public boolean isMoveAllowed(Object source, Object target) {
			try {
				if (source==target)
					return true;
				if (target instanceof Participant) {
					Participant p = (Participant) target;
					if (p.equals(ModelHandler.getInstance(getDiagram()).getInternalParticipant())) {
						return true;
					}
					if (p.getProcessRef() == null) {
						return true;
					}
					if (p.getProcessRef().getLaneSets().isEmpty()) {
						return true;
					}
				}
				else if (target instanceof FlowElementsContainer) {
					FlowElementsContainer p = (FlowElementsContainer) target;
					if (p.getLaneSets().isEmpty()) {
						return true;
					}
				}
			} catch (Exception e) {
				Activator.logError(e);
			}
			return false;
		}
		
		// TODO: I have no idea what this was supposed to do.
		// This is preventing the move of a shape out of a FlowElementsContainer 
//		@Override
//		public void move(FlowNode node, Object source, Object target) {
//			try {
//				if (ModelHandler.getInstance(node).getInternalParticipant() == null){ // this is not a collaboration, don't move 
//					return;
//				}else{
//					super.move(node, source, target);
//				}
//			} catch (IOException e) {
//				Activator.logError(e);
//			}
//		}
	}

	class FromFlowElementsContainerAlgorithm extends DefaultAlgorithm {

		@Override
		public int getType() {
			return TYPE_FROM;
		}

		@Override
		public boolean canApplyTo(IMoveShapeContext context) {
			Object bo = getBusinessObjectForPictogramElement(context.getSourceContainer());
			return bo != null && bo instanceof FlowElementsContainer;
		}

		@Override
		public void move(FlowNode node, Object source, Object target) {
		}
	}

	class ToFlowElementsContainerAlgorithm extends DefaultAlgorithm {

		@Override
		public int getType() {
			return TYPE_TO;
		}

		@Override
		public boolean canApplyTo(IMoveShapeContext context) {
			Object bo = getBusinessObjectForPictogramElement(context.getTargetContainer());
			return bo != null && bo instanceof FlowElementsContainer;
		}
	}
}
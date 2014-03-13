package org.eclipse.bpmn2.modeler.core.features.data;

import org.eclipse.bpmn2.Choreography;
import org.eclipse.bpmn2.Collaboration;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.ItemAwareElement;
import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.modeler.core.features.DefaultMoveBPMNShapeFeature;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;

public class MoveDataFeature extends DefaultMoveBPMNShapeFeature {

	public MoveDataFeature(IFeatureProvider fp) {
		super(fp);
	}

	public boolean canMoveShape(IMoveShapeContext context) {
		EObject bo = BusinessObjectUtil.getBusinessObjectForPictogramElement(context.getShape());
		if (bo instanceof ItemAwareElement && bo instanceof FlowElement) {
			if (FeatureSupport.isValidDataTarget(context))
				return true;
		}
		return super.canMoveShape(context);
	}
	
	protected void postMoveShape(IMoveShapeContext context) {
		Shape shape = context.getShape();
		FlowElement dataElement = (FlowElement) BusinessObjectUtil.getBusinessObjectForPictogramElement(shape);
		ContainerShape sourceContainer = context.getSourceContainer();
		ContainerShape targetContainer = context.getTargetContainer();
		if (sourceContainer!=targetContainer) {
			EObject targetObject = BusinessObjectUtil.getBusinessObjectForPictogramElement(targetContainer);
			if (targetObject instanceof BPMNDiagram) {
				targetObject = ((BPMNDiagram)targetObject).getPlane().getBpmnElement();
			}
			if (targetObject instanceof Lane) {
				while (targetObject!=null) {
					targetObject = targetObject.eContainer();
					if (targetObject instanceof FlowElementsContainer)
						break;
				}
			}
			if (targetObject instanceof Collaboration && !(targetObject instanceof Choreography)) {
				Collaboration collaboration = (Collaboration)targetObject;
				for (Participant p : collaboration.getParticipants()) {
					if (p.getProcessRef()!=null) {
						targetObject = p.getProcessRef();
						break;
					}
				}

			}
			if (targetObject instanceof Participant) {
				targetObject = ((Participant)targetObject).getProcessRef();
			}
			if (targetObject instanceof FlowElementsContainer) {
				((FlowElementsContainer)targetObject).getFlowElements().add(dataElement);
			}
		}
		super.postMoveShape(context);
	}
}

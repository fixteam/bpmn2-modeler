package org.eclipse.bpmn2.modeler.ui.features.activity.subprocess;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.ui.features.activity.DeleteActivityFeature;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

public class DeleteExpandableActivityFeature extends DeleteActivityFeature {

	public DeleteExpandableActivityFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public void delete(final IDeleteContext context) {
		// Delete the BPMNDiagram if there is one
		PictogramElement pe = context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(pe);
		if (bo instanceof FlowElementsContainer) {
			BPMNDiagram bpmnDiagram = DIUtils.findBPMNDiagram((BaseElement)bo);
			if (bpmnDiagram != null) {
				DIUtils.deleteDiagram(getDiagramEditor(), bpmnDiagram);
			}
		}

		super.delete(context);
	}
}

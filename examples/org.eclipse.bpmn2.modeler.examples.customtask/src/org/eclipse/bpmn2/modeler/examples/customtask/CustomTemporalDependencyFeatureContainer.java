package org.eclipse.bpmn2.modeler.examples.customtask;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.BoundaryEvent;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.modeler.core.features.BaseElementConnectionFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.IFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.flow.AbstractAddFlowFeature;
import org.eclipse.bpmn2.modeler.core.features.flow.AbstractCreateFlowFeature;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyModelFactory;
import org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyModelPackage;
import org.eclipse.bpmn2.modeler.examples.customtask.MyModel.TemporalDependency;
import org.eclipse.bpmn2.modeler.ui.features.activity.task.CustomConnectionFeatureContainer;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.styles.LineStyle;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;

public class CustomTemporalDependencyFeatureContainer extends CustomConnectionFeatureContainer {

	public static String TEMPORAL_DEPENDENCY_ID = "org.eclipse.bpmn2.modeler.examples.timebpmn.temporalDependency";
	
	public CustomTemporalDependencyFeatureContainer() {
		setId(TEMPORAL_DEPENDENCY_ID);
	}

	@Override
	public String getId(EObject object) {
		if (object instanceof TemporalDependency) {
			return TEMPORAL_DEPENDENCY_ID;
		}

		return null;
	}

	@Override
	protected IFeatureContainer createFeatureContainer(IFeatureProvider fp) {
		return new TemporalDependencyFeatureContainer();
	}
	
	public class TemporalDependencyFeatureContainer extends BaseElementConnectionFeatureContainer {

		@Override
		public ICreateConnectionFeature getCreateConnectionFeature(IFeatureProvider fp) {
			return new CreateTemporalDependencyFeature(fp);
		}

		@Override
		public IAddFeature getAddFeature(IFeatureProvider fp) {
			return new AddTemporalDependencyFeature(fp);
		}
		
	}
	
	public class CreateTemporalDependencyFeature extends AbstractCreateFlowFeature<TemporalDependency, BoundaryEvent, BoundaryEvent> {

		public CreateTemporalDependencyFeature(IFeatureProvider fp, String name, String description) {
			super(fp, name, description);
		}

		public CreateTemporalDependencyFeature(IFeatureProvider fp) {
			this(fp,"Temporal Dependency", "Create Temporal Dependency");
		}

		@Override
		public TemporalDependency createBusinessObject(ICreateConnectionContext context) {
			TemporalDependency businessObject = MyModelFactory.eINSTANCE.createTemporalDependency();
			BoundaryEvent source = getSourceBo(context);
			BoundaryEvent target = getTargetBo(context);
			businessObject.setSourceRef(source);
			businessObject.setTargetRef(target);
			EObject container = source.eContainer();
			while (container!=null) {
				if (container instanceof FlowElementsContainer) {
					((FlowElementsContainer)container).getFlowElements().add(businessObject);
					break;
				}
				container = container.eContainer();
			}
			
			putBusinessObject(context, businessObject);
			changesDone = true;
			return businessObject;
		}

		@Override
		public EClass getBusinessObjectClass() {
			return MyModelPackage.eINSTANCE.getTemporalDependency();
		}

		@Override
		protected String getStencilImageId() {
			return null;
		}

		@Override
		protected Class<BoundaryEvent> getSourceClass() {
			return BoundaryEvent.class;
		}

		@Override
		protected Class<BoundaryEvent> getTargetClass() {
			return BoundaryEvent.class;
		}
	}
	

	public class AddTemporalDependencyFeature extends AbstractAddFlowFeature<TemporalDependency> {
		
		public AddTemporalDependencyFeature(IFeatureProvider fp) {
			super(fp);
		}

		@Override
		protected Class<? extends BaseElement> getBoClass() {
			return TemporalDependency.class;
		}

		@Override
		protected Polyline createConnectionLine(Connection connection) {
			IPeService peService = Graphiti.getPeService();
			IGaService gaService = Graphiti.getGaService();
			BaseElement be = BusinessObjectUtil.getFirstBaseElement(connection);

			Polyline connectionLine = super.createConnectionLine(connection);
			connectionLine.setLineStyle(LineStyle.SOLID);
			connectionLine.setLineWidth(2);

			int w = 5;
			int l = 15;
			
			ConnectionDecorator decorator = peService.createConnectionDecorator(connection, false,
					1.0, true);

			Polyline arrowhead = gaService.createPolygon(decorator, new int[] { -l, w, 0, 0, -l, -w, -l, w });
			StyleUtil.applyStyle(arrowhead, be);
			
			return connectionLine;
		}
	}
	
}

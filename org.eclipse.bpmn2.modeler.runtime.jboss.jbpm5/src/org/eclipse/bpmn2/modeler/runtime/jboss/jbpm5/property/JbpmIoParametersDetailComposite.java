package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.InputOutputSpecification;
import org.eclipse.bpmn2.modeler.core.adapters.InsertionAdapter;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.ListCompositeContentProvider;
import org.eclipse.bpmn2.modeler.core.runtime.ModelExtensionDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.ModelExtensionDescriptor.ModelExtensionAdapter;
import org.eclipse.bpmn2.modeler.core.runtime.ModelExtensionDescriptor.Property;
import org.eclipse.bpmn2.modeler.ui.property.tasks.IoParametersDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.tasks.IoParametersListComposite;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.widgets.Composite;

public class JbpmIoParametersDetailComposite extends IoParametersDetailComposite {

	public JbpmIoParametersDetailComposite(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	public JbpmIoParametersDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void createBindings(final EObject be) {
		final EStructuralFeature ioSpecificationFeature = be.eClass().getEStructuralFeature("ioSpecification");
		if (ioSpecificationFeature != null) {
			// the control parameter must be an Activity or CallableElement (i.e. a Process or GlobalTask)
			InputOutputSpecification ioSpecification = (InputOutputSpecification)be.eGet(ioSpecificationFeature);
			if (ioSpecification==null) {
				ioSpecification = (InputOutputSpecification) FACTORY.createInputOutputSpecification();
				InsertionAdapter.add(be, ioSpecificationFeature, ioSpecification);
			}
			
			EStructuralFeature dataInputsFeature = getFeature(ioSpecification, "dataInputs");
			if (isModelObjectEnabled(ioSpecification.eClass(),dataInputsFeature)) {
				dataInputsTable = new JbpmIoParametersListComposite(this, be, ioSpecification, dataInputsFeature);
				dataInputsTable.bindList(ioSpecification, dataInputsFeature);
				dataInputsTable.setTitle("Input Parameter Mapping");
			}
			
			EStructuralFeature dataOutputsFeature = getFeature(ioSpecification, "dataOutputs");
			if (isModelObjectEnabled(ioSpecification.eClass(),dataOutputsFeature)) {
				dataOutputsTable = new JbpmIoParametersListComposite(this, be, ioSpecification, dataOutputsFeature);
				dataOutputsTable.bindList(ioSpecification, dataOutputsFeature);
				dataOutputsTable.setTitle("Output Parameter Mapping");
			}
		}
	}	

	public class JbpmIoParametersListComposite extends IoParametersListComposite {

		public JbpmIoParametersListComposite(
				IoParametersDetailComposite detailComposite, EObject container,
				InputOutputSpecification ioSpecification,
				EStructuralFeature ioFeature) {
			super(detailComposite, container, ioSpecification, ioFeature);
			
		}

		@Override
		public ListCompositeContentProvider getContentProvider(EObject object, EStructuralFeature feature, EList<EObject>list) {
			if (contentProvider==null) {
				contentProvider = new ListCompositeContentProvider(this, object, feature, list) {
					@Override
					public Object[] getElements(Object inputElement) {
						
						Object elements[] = super.getElements(inputElement);
						List<Property> props = null;
						ModelExtensionAdapter adapter = ModelExtensionDescriptor.getModelExtensionAdapter(
								JbpmIoParametersDetailComposite.this.getBusinessObject());
						if (adapter!=null ) {
							if (JbpmIoParametersListComposite.this.isInput)
								props = adapter.getProperties("ioSpecification/dataInputs/name");
							else
								props = adapter.getProperties("ioSpecification/dataOutputs/name");
						
							List<Object> filtered = new ArrayList<Object>();
							for (Object e : elements) {
								boolean skip = false;
								EStructuralFeature f = ((EObject)e).eClass().getEStructuralFeature("name");
								if (f!=null) {
									Object elementName = (String) ((EObject)e).eGet(f);
									for (Property p : props) {
										Object propName = p.getFirstStringValue();
										if (elementName!=null && propName!=null && elementName.equals(propName)) {
											skip = true;
											break;
										}
									}
								}
								if (!skip)
									filtered.add(e);
							}
							return filtered.toArray();
						}
						return elements;
					}
				};
			}
			return contentProvider;
		}
		
	}
}

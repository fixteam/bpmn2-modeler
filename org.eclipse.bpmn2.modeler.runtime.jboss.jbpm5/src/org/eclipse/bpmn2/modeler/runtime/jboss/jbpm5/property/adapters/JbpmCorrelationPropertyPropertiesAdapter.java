package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.adapters;

import java.util.Hashtable;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.CorrelationProperty;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.util.JbpmModelUtil;
import org.eclipse.bpmn2.modeler.ui.adapters.properties.CorrelationPropertyPropertiesAdapter;
import org.eclipse.bpmn2.modeler.ui.adapters.properties.ItemDefinitionRefFeatureDescriptor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;

public class JbpmCorrelationPropertyPropertiesAdapter extends CorrelationPropertyPropertiesAdapter {

	public JbpmCorrelationPropertyPropertiesAdapter(AdapterFactory adapterFactory, CorrelationProperty object) {
		super(adapterFactory, object);

		EStructuralFeature feature = Bpmn2Package.eINSTANCE.getCorrelationProperty_Type();
		setProperty(feature, UI_IS_MULTI_CHOICE, Boolean.TRUE);
    	setFeatureDescriptor(feature, new ItemDefinitionRefFeatureDescriptor<CorrelationProperty>(adapterFactory, object, feature) {

    		@Override
    		public Hashtable<String, Object> getChoiceOfValues(Object context) {
				return JbpmModelUtil.collectAllDataTypes(adopt(context));
    		}
	
    	});
	}

}

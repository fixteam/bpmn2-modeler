package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.adapters;

import java.util.Hashtable;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.ResourceParameter;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.util.JbpmModelUtil;
import org.eclipse.bpmn2.modeler.ui.adapters.properties.ResourceParameterPropertiesAdapter;
import org.eclipse.bpmn2.modeler.ui.adapters.properties.ItemDefinitionRefFeatureDescriptor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;

public class JbpmResourceParameterPropertiesAdapter extends ResourceParameterPropertiesAdapter {

	public JbpmResourceParameterPropertiesAdapter(AdapterFactory adapterFactory, ResourceParameter object) {
		super(adapterFactory, object);

		EStructuralFeature feature = Bpmn2Package.eINSTANCE.getResourceParameter_Type();
		setProperty(feature, UI_IS_MULTI_CHOICE, Boolean.TRUE);
    	setFeatureDescriptor(feature, new ItemDefinitionRefFeatureDescriptor<ResourceParameter>(adapterFactory, object, feature) {

    		@Override
    		public Hashtable<String, Object> getChoiceOfValues(Object context) {
				return JbpmModelUtil.collectAllDataTypes(adopt(context));
    		}
	
    	});
	}

}

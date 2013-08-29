package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.adapters;

import org.eclipse.bpmn2.ItemDefinition;
import org.eclipse.bpmn2.modeler.core.adapters.ObjectDescriptor;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.adapters.properties.ItemDefinitionPropertiesAdapter;
import org.eclipse.emf.common.notify.AdapterFactory;

public class JbpmItemDefinitionPropertiesAdapter extends ItemDefinitionPropertiesAdapter {

	public JbpmItemDefinitionPropertiesAdapter(AdapterFactory adapterFactory, ItemDefinition object) {
		super(adapterFactory, object);
		setObjectDescriptor( new ObjectDescriptor<ItemDefinition>(adapterFactory, object) {

			@Override
			public boolean equals(Object obj) {
				ItemDefinition other = (ItemDefinition)obj;
				if (object.getItemKind().equals(other.getItemKind())) {
					String thisWrapper = ModelUtil.getStringWrapperValue(object.getStructureRef());
					String otherWrapper = ModelUtil.getStringWrapperValue(other.getStructureRef());
					if (thisWrapper==null) {
						if (otherWrapper==null)
							return true;
					}
					else
						return thisWrapper.equals(otherWrapper);
				}
				return false;
			}
		});
	}
	
}

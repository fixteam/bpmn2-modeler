package org.eclipse.bpmn2.modeler.examples.customtask;

import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyEventDefinition;
import org.eclipse.emf.common.notify.AdapterFactory;

public class MyEventDefinitionPropertiesAdapter extends ExtendedPropertiesAdapter<MyEventDefinition> {

	public MyEventDefinitionPropertiesAdapter(AdapterFactory adapterFactory, MyEventDefinition object) {
		super(adapterFactory, object);
	}

}

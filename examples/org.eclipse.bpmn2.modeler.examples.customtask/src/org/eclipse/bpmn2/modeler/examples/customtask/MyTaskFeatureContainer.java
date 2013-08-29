package org.eclipse.bpmn2.modeler.examples.customtask;

import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.features.activity.task.CustomTaskFeatureContainer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class MyTaskFeatureContainer extends CustomTaskFeatureContainer {

	private final static String TYPE_VALUE = "MyTask";
	private final static String CUSTOM_TASK_ID = "org.eclipse.bpmn2.modeler.examples.customtask.customTask1";

	public MyTaskFeatureContainer() {
	}

	@Override
	public String getId(EObject object) {
		// This is where we inspect the object to determine what its custom task ID should be.
		// In this case, the "type" attribute will have a value of "MyTask".
		// If found, return the CUSTOM_TASK_ID string.
		//
		// Note that the object inspection can be arbitrarily complex and may include several
		// object features. This simple case just demonstrates what needs to happen here.
		EStructuralFeature f = ModelUtil.getAnyAttribute(object, "type");
		if (f!=null) {
			Object id = object.eGet(f);
			if (TYPE_VALUE.equals(id))
				return CUSTOM_TASK_ID;
		}
			
		return null;
	}

}

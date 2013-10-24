/*******************************************************************************
 * Copyright (c) 2011, 2012, 2013 Red Hat, Inc.
 * All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 	Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.examples.customtask;

import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyEventDefinition;
import org.eclipse.emf.common.notify.AdapterFactory;

public class MyEventDefinitionPropertiesAdapter extends ExtendedPropertiesAdapter<MyEventDefinition> {

	public MyEventDefinitionPropertiesAdapter(AdapterFactory adapterFactory, MyEventDefinition object) {
		super(adapterFactory, object);
	}

}

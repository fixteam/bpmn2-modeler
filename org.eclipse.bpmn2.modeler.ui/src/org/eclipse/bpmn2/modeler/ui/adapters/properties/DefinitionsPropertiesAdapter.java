/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc.
 *  All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 *
 * @author Bob Brodt
 ******************************************************************************/

package org.eclipse.bpmn2.modeler.ui.adapters.properties;

import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.modeler.core.IBpmn2RuntimeExtension;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.adapters.ObjectDescriptor;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerFactory;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * @author Bob Brodt
 *
 */
public class DefinitionsPropertiesAdapter extends ExtendedPropertiesAdapter<Definitions> {

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public DefinitionsPropertiesAdapter(AdapterFactory adapterFactory, Definitions object) {
		super(adapterFactory, object);
    	
		setObjectDescriptor(new ObjectDescriptor<Definitions>(adapterFactory, object) {
			@Override
			public Definitions createObject(Resource resource, Object context) {
				Definitions definitions = Bpmn2ModelerFactory.create(Definitions.class);
				IBpmn2RuntimeExtension rte = TargetRuntime.getCurrentRuntime().getRuntimeExtension();
				definitions.setTypeLanguage(rte.getTypeLanguages()[0]);
				definitions.setExpressionLanguage(rte.getExpressionLanguages()[0]);
				return definitions;
			}
		});
	}

}

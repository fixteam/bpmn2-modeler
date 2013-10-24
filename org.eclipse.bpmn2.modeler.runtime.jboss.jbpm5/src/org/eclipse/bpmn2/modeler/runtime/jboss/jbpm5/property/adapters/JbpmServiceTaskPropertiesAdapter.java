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

package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.adapters;

import java.util.Hashtable;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.ServiceTask;
import org.eclipse.bpmn2.modeler.core.adapters.FeatureDescriptor;
import org.eclipse.bpmn2.modeler.ui.adapters.properties.ServiceTaskPropertiesAdapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author Bob Brodt
 * 
 * 
 * This class needs to go into the SwitchYard extension for the jBPM Target Runtime extension
 *
 */
public class JbpmServiceTaskPropertiesAdapter extends ServiceTaskPropertiesAdapter{

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public JbpmServiceTaskPropertiesAdapter(AdapterFactory adapterFactory, ServiceTask object) {
		super(adapterFactory, object);

    	EStructuralFeature feature = Bpmn2Package.eINSTANCE.getServiceTask_Implementation();
    	setProperty(feature, UI_IS_MULTI_CHOICE, Boolean.TRUE);
    	
    	setFeatureDescriptor(feature,
			new FeatureDescriptor<ServiceTask>(adapterFactory,object,feature) {

				@Override
				public Hashtable<String, Object> getChoiceOfValues(Object context) {
					if (choiceOfValues==null) {
						choiceOfValues = super.getChoiceOfValues(context);
						choiceOfValues.put("SwitchYard Service", "##SwitchYard"); //$NON-NLS-1$ //$NON-NLS-2$
					}
					return choiceOfValues;
				}
			}
    	);
	}

}

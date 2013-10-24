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
import org.eclipse.bpmn2.FormalExpression;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.modeler.core.adapters.FeatureDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.ui.adapters.properties.FormalExpressionPropertiesAdapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author Bob Brodt
 *
 */
public class JbpmFormalExpressionPropertiesAdapter extends FormalExpressionPropertiesAdapter {

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public JbpmFormalExpressionPropertiesAdapter(AdapterFactory adapterFactory, FormalExpression object) {
		super(adapterFactory, object);

    	final EStructuralFeature language = Bpmn2Package.eINSTANCE.getFormalExpression_Language();
    	setFeatureDescriptor(language,
    		new FeatureDescriptor<FormalExpression>(adapterFactory,object,language) {
				@Override
				public String getLabel(Object context) {
					FormalExpression object = adopt(context);
					if (object.eContainer() instanceof SequenceFlow)
						return Messages.JbpmFormalExpressionPropertiesAdapter_Condition_Language;
					return Messages.JbpmFormalExpressionPropertiesAdapter_Script_Language;
				}
	
				@Override
				public Hashtable<String, Object> getChoiceOfValues(Object context) {
					FormalExpression object = adopt(context);
					choiceOfValues = new Hashtable<String, Object>();
					TargetRuntime rt = TargetRuntime.getCurrentRuntime();
					String[] s = rt.getRuntimeExtension().getExpressionLanguages();
					for (int i=0; i<s.length; i+=2) {
						choiceOfValues.put(s[i+1], s[i]);
					}
					if (!(object.eContainer() instanceof SequenceFlow))
						choiceOfValues.remove(Messages.JbpmFormalExpressionPropertiesAdapter_Rule);
					return choiceOfValues;
				}
				
			}
    	);
	}

}

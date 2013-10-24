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

import java.util.Hashtable;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Message;
import org.eclipse.bpmn2.Operation;
import org.eclipse.bpmn2.SendTask;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author Bob Brodt
 *
 */
public class MessageRefFeatureDescriptor<T extends BaseElement> extends RootElementRefFeatureDescriptor<T> {

	/**
	 * @param adapterFactory
	 * @param object
	 * @param feature
	 */
	public MessageRefFeatureDescriptor(AdapterFactory adapterFactory, T object, EStructuralFeature feature) {
		super(adapterFactory, object, feature);
	}
	
	@Override
	public Hashtable<String, Object> getChoiceOfValues(Object context) {
		Hashtable<String, Object> choices = super.getChoiceOfValues(context);
		final T object = adopt(context);
		Operation operation = null;
		EStructuralFeature f = object.eClass().getEStructuralFeature("operationRef"); //$NON-NLS-1$
		if (f!=null && object.eGet(f)!=null) {
			operation = (Operation) object.eGet(f);
		}
		
		if (operation==null) {
			choices = super.getChoiceOfValues(context);
		}
		else {
			choices = new Hashtable<String,Object>();
			Message message = operation.getOutMessageRef();
			if (message!=null)
				choices.put(ModelUtil.getDisplayName(message), message);
			message = operation.getInMessageRef();
			if (message!=null)
				choices.put(ModelUtil.getDisplayName(message), message);
		}
		return choices;
	}
}

/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc. 
 * All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 *******************************************************************************/
package org.eclipse.bpmn2.modeler.ui.adapters.properties;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.ItemAwareElement;
import org.eclipse.bpmn2.ItemDefinition;
import org.eclipse.bpmn2.modeler.core.adapters.AdapterUtil;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class ItemAwareElementFeatureDescriptor<T extends ItemAwareElement> extends RootElementRefFeatureDescriptor<T> {
	public ItemAwareElementFeatureDescriptor(AdapterFactory adapterFactory, T object,EStructuralFeature feature) {
		super(adapterFactory, object, feature);
	}

	@Override
	public String getLabel(Object context) {
		return "Data Type";
	}

	@Override
	public String getDisplayName(Object context) {
		EObject object = this.object;
		if (context instanceof EObject)
			object = (EObject)context;
		ItemDefinition itemDefinition = null;
		if (object instanceof ItemDefinition)
			itemDefinition = (ItemDefinition) object;
		else if (object instanceof ItemAwareElement)
			itemDefinition = (ItemDefinition) object.eGet(feature);
		if (itemDefinition!=null) {
			ExtendedPropertiesAdapter<ItemDefinition> adapter =
					(ExtendedPropertiesAdapter<ItemDefinition>) AdapterUtil.adapt(itemDefinition, ExtendedPropertiesAdapter.class);
			return adapter.getFeatureDescriptor(Bpmn2Package.eINSTANCE.getItemDefinition_StructureRef()).getDisplayName(itemDefinition);
		}
		return super.getDisplayName(context);
	}
}
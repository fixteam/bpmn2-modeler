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

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Category;
import org.eclipse.bpmn2.CategoryValue;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.adapters.FeatureDescriptor;
import org.eclipse.bpmn2.modeler.core.adapters.ObjectDescriptor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author Bob Brodt
 *
 */
public class CategoryValuePropertiesAdapter extends ExtendedPropertiesAdapter<CategoryValue> {

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public CategoryValuePropertiesAdapter(AdapterFactory adapterFactory, CategoryValue object) {
		super(adapterFactory, object);

		EStructuralFeature ref = Bpmn2Package.eINSTANCE.getCategoryValue_Value();
//		setProperty(ref, UI_CAN_CREATE_NEW, Boolean.TRUE);
//		setProperty(ref, UI_IS_MULTI_CHOICE, Boolean.TRUE);
		setProperty(ref, UI_CAN_SET_NULL, Boolean.TRUE);
		// this is a read-only list
		setProperty(Bpmn2Package.eINSTANCE.getCategoryValue_CategorizedFlowElements(), UI_CAN_EDIT, Boolean.FALSE);
		
    	setFeatureDescriptor(ref,
			new FeatureDescriptor<CategoryValue>(adapterFactory,object,ref) {
				@Override
				public String getDisplayName(Object context) {
					CategoryValue categoryValue = adopt(context);
					return CategoryValuePropertiesAdapter.getDisplayName(categoryValue);
				}

				@Override
				public Object getValue() {
					return super.getValue();
				}

				@Override
				public void setValue(Object context, Object value) {
					CategoryValue categoryValue = adopt(context);
					if (value instanceof String) {
						int i = ((String) value).indexOf(":");
						if (i>=0)
							value = ((String) value).substring(i+1);
					}
					super.setValue(context, value);
				}
				
			}
    	
    	);
    	
		setObjectDescriptor(new ObjectDescriptor<CategoryValue>(adapterFactory, object) {
			@Override
			public String getDisplayName(Object context) {
				CategoryValue categoryValue = adopt(context);
				return CategoryValuePropertiesAdapter.getDisplayName(categoryValue);
			}
		});
	}
	
	private static String getDisplayName(CategoryValue categoryValue) {
		Category category = (Category) categoryValue.eContainer();
		String prefix = category==null ? "" : category.getName() + ":";
		String suffix = categoryValue.getValue();
		return prefix + suffix;
	}
}

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
import java.util.List;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.ItemDefinition;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.adapters.FeatureDescriptor;
import org.eclipse.bpmn2.modeler.core.adapters.ObjectDescriptor;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerFactory;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.core.utils.NamespaceUtil;
import org.eclipse.bpmn2.modeler.core.validation.SyntaxCheckerUtils;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.xsd.XSDElementDeclaration;

/**
 * @author Bob Brodt
 *
 */
public class ItemDefinitionPropertiesAdapter extends ExtendedPropertiesAdapter<ItemDefinition> {

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public ItemDefinitionPropertiesAdapter(AdapterFactory adapterFactory, ItemDefinition object) {
		super(adapterFactory, object);

		final EStructuralFeature ref = Bpmn2Package.eINSTANCE.getItemDefinition_StructureRef();
		setProperty(ref, UI_CAN_CREATE_NEW, Boolean.TRUE);
		setProperty(ref, UI_IS_MULTI_CHOICE, Boolean.TRUE);
		
    	setFeatureDescriptor(ref,
			new FeatureDescriptor<ItemDefinition>(adapterFactory,object,ref) {
				@Override
				public String getLabel(Object context) {
					return Messages.ItemDefinitionPropertiesAdapter_Structure;
				}

				@Override
				public String getDisplayName(Object context) {
					ItemDefinition itemDefinition = adopt(context);
					String value = ItemDefinitionPropertiesAdapter.getStructureName(itemDefinition);
					return value;
				}
				
	    		@Override
				public EObject createFeature(Resource resource, Object context, EClass eClass) {
					final ItemDefinition itemDefinition = adopt(context);
					EObject structureRef = ModelUtil.createStringWrapper(""); //$NON-NLS-1$
					itemDefinition.setStructureRef(structureRef);
					return structureRef;
	    		}

	    		@Override
	    		public Object getValue(Object context) {
					ItemDefinition itemDefinition = adopt(context);
					Object value = ItemDefinitionPropertiesAdapter.getStructureRef(itemDefinition);
					if (value==null || (ModelUtil.isStringWrapper(value) && ModelUtil.getStringWrapperValue(value).isEmpty())) {
						value = itemDefinition.getId();
					}
					return value;
	    		}

	    		@Override
	    		public void setValue(Object context, Object value) {
					ItemDefinition itemDefinition = adopt(context);
					if (value instanceof String) {
						if (itemDefinition.getStructureRef()==null) {
							String oldValue = ItemDefinitionPropertiesAdapter.getStructureName(itemDefinition);
							value = ((String) value).replace(oldValue, ""); //$NON-NLS-1$
						}
						value = SyntaxCheckerUtils.toNCName((String)value);
						value = ModelUtil.createStringWrapper((String)value);
					}
					super.setValue(context, value);
	    		}

				@Override
				public Hashtable<String, Object> getChoiceOfValues(Object context) {
					ItemDefinition itemDefinition = adopt(context);
					return ItemDefinitionPropertiesAdapter.getChoiceOfValues(itemDefinition);
				}
			}
    	);
    	
		setObjectDescriptor(new ObjectDescriptor<ItemDefinition>(adapterFactory, object) {
			@Override
			public String getDisplayName(Object context) {
				ItemDefinition itemDefinition = adopt(context);
				return ItemDefinitionPropertiesAdapter.getDisplayName(itemDefinition);
			}
			
			@Override
			public String getLabel(Object context) {
				return ItemDefinitionPropertiesAdapter.getLabel();
			}
			
			@Override
			public ItemDefinition createObject(Resource resource, Object context) {
				ItemDefinition itemDefinition = ItemDefinitionPropertiesAdapter.createItemDefinition(resource);
				return itemDefinition;
			}

			@Override
			public boolean equals(Object obj) {
				if (obj instanceof ItemDefinition) {
					return super.equals(obj);
				}
				else if (obj instanceof String) {
					String otherWrapper = (String) obj;
					Object thisStructure = object.getStructureRef();
					if (thisStructure==null) {
						if (otherWrapper.isEmpty())
							return true;
						return false;
					}
					if (ModelUtil.isStringWrapper(thisStructure)) {
						String thisWrapper = ModelUtil.getStringWrapperValue(object.getStructureRef());
						return thisWrapper.equals(otherWrapper);
					}
				}
				return true;
			}
		});
	}


	/*
	 * Methods for dealing with ItemDefinitions
	 */
	public static String getLabel() {
		return Messages.ItemDefinitionPropertiesAdapter_Data_Type;
	}

	public static ItemDefinition createItemDefinition(Resource resource) {
		ItemDefinition itemDefinition = Bpmn2ModelerFactory.eINSTANCE.createItemDefinition();
		ModelUtil.setID(itemDefinition, resource);
		Definitions defs = ModelUtil.getDefinitions(resource);
		if (defs!=null) {
			defs.getRootElements().add(itemDefinition);
		}

		return itemDefinition;
	}
	
	public static String getDisplayName(ItemDefinition itemDefinition) {
		String name = ""; //$NON-NLS-1$
		if (itemDefinition!=null) {
			name = getStructureName(itemDefinition);
			if (itemDefinition.isIsCollection())
				name += "[]"; //$NON-NLS-1$
		}
		return name;
	}
	
	public static String getStructureName(ItemDefinition itemDefinition) {
		Resource resource = ModelUtil.getResource(itemDefinition);
		String name = ""; //$NON-NLS-1$
		if (itemDefinition!=null) {
			Object value = itemDefinition.getStructureRef();
			if (value!=null) {
				if (value instanceof XSDElementDeclaration) {
					XSDElementDeclaration elem = (XSDElementDeclaration)value;
					name = elem.getQName();
				}
				else if (value instanceof Message) {
					Message message = (Message)value;
					name = NamespaceUtil.normalizeQName(resource,message.getQName());
				}
				else if (ModelUtil.isStringWrapper(value))
					name = ModelUtil.getStringWrapperValue(value);
			}
			if (name==null || name.isEmpty()) {
				name = ModelUtil.generateUndefinedID(itemDefinition.getId());
			}
		}
		return name;
	}
	
	public static Object getStructureRef(ItemDefinition itemDefinition) {
		Object value = null;
		if (itemDefinition!=null)
			value = itemDefinition.getStructureRef();
		if (ModelUtil.isStringWrapper(value) && ModelUtil.getStringWrapperValue(value).isEmpty())
			value = null;
		return value;
	}

	public static Hashtable<String, Object> getChoiceOfValues(EObject context) {
		// add all ItemDefinitions
		Hashtable<String,Object> choices = new Hashtable<String,Object>();
		if (context!=null) {
			String s;
			Definitions defs = ModelUtil.getDefinitions(context);
			List<ItemDefinition> itemDefs = ModelUtil.getAllRootElements(defs, ItemDefinition.class);
			for (ItemDefinition id : itemDefs) {
				s = getDisplayName(id);
				if (s==null || s.isEmpty())
					s = id.getId();
				choices.put(s,id);
			}
		}
		return choices;
	}
	
}

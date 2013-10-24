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

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Interface;
import org.eclipse.bpmn2.Operation;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.adapters.FeatureDescriptor;
import org.eclipse.bpmn2.modeler.core.adapters.InsertionAdapter;
import org.eclipse.bpmn2.modeler.core.adapters.ObjectDescriptor;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerFactory;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.core.utils.NamespaceUtil;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.wst.wsdl.PortType;

/**
 * @author Bob Brodt
 *
 */
public class InterfacePropertiesAdapter extends ExtendedPropertiesAdapter<Interface> {

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public InterfacePropertiesAdapter(AdapterFactory adapterFactory, Interface object) {
		super(adapterFactory, object);
		
    	EStructuralFeature ref = Bpmn2Package.eINSTANCE.getInterface_ImplementationRef();
    	setFeatureDescriptor(ref, new ImplementationRefFeatureDescriptor<Interface>(this, adapterFactory, object, ref));
    	
    	ref = Bpmn2Package.eINSTANCE.getInterface_Operations();
    	setFeatureDescriptor(ref, new FeatureDescriptor<Interface>(adapterFactory, object, ref) {

			@Override
			public EObject createFeature(Resource resource, Object context, EClass eclass) {
				Interface intf = adopt(context);
				Operation operation = Bpmn2ModelerFactory.create(Operation.class);
				ModelUtil.setID(operation, resource);
				operation.setName( ModelUtil.toDisplayName(operation.getId()) );
//				InsertionAdapter.add(intf, Bpmn2Package.eINSTANCE.getInterface_Operations(), operation);
				intf.getOperations().add(operation);
				return operation;
			}
    		
    	});
	}

	public static class ImplementationRefFeatureDescriptor<T extends BaseElement> extends FeatureDescriptor<T> {

		public ImplementationRefFeatureDescriptor(final ExtendedPropertiesAdapter<T> owner,
				AdapterFactory adapterFactory, final T object, final EStructuralFeature feature) {
			super(adapterFactory, object, feature);

	    	owner.setProperty(feature, UI_IS_MULTI_CHOICE, Boolean.FALSE);

			owner.setObjectDescriptor(new ObjectDescriptor<T>(adapterFactory, object) {
				@Override
				public String getDisplayName(Object context) {
					return owner.getFeatureDescriptor(feature).getDisplayName(context);
				}

				@Override
				public boolean equals(Object obj) {
					if (obj instanceof BaseElement) {
						BaseElement other = (BaseElement)obj;
						String otherName = ModelUtil.getName(other);
						Object otherValue = other.eGet(feature);
						if (ModelUtil.compare(otherName, ModelUtil.getName(object))) {
							if (ModelUtil.compare(otherValue, (object).eGet(feature)))
								return true;
						}
					}
					return false;
				}
			});

		}

		@Override
		public String getDisplayName(Object context) {
			final T object = adopt(context);
			Resource resource = ModelUtil.getResource(object);
			String text = null;
			EObject value = (EObject)object.eGet(feature);
			if (value!=null) {
				if (ModelUtil.isStringWrapper(value)) {
					text = ModelUtil.getStringWrapperValue( object.eGet(feature) ); // + type;
				}
				else if (value instanceof PortType) {
					PortType portType = (PortType)value;
					text = NamespaceUtil.normalizeQName(resource, portType.getQName());
				}
				else if (value instanceof org.eclipse.wst.wsdl.Operation) {
					org.eclipse.wst.wsdl.Operation operation = (org.eclipse.wst.wsdl.Operation)value;
					String name = operation.getName();
					String namespace = operation.getEnclosingDefinition().getTargetNamespace();
					String prefix = NamespaceUtil.getPrefixForNamespace(resource, namespace);
					if (prefix!=null && !prefix.isEmpty())
						text = prefix + ":" + name; //$NON-NLS-1$
					else
						text += name;
				}
				if (text==null)
					return ModelUtil.getDisplayName(object.eGet(feature));
				return text;
			}
			text = ModelUtil.getName(object);
			if (text==null || text.isEmpty())
				return object.getId();
			return text;
		}
		
		@Override
		public EObject createFeature(Resource resource, Object context, EClass eClass) {
			final T object = adopt(context);

			EObject impl = ModelUtil.createStringWrapper(""); //$NON-NLS-1$
			object.eSet(feature,impl);
			return impl;
		}

		@Override
		public Object getValue(Object context) {
			final T object = adopt(context);
			Object value = object.eGet(feature);
			if (value!=null) {
				if (ModelUtil.isStringWrapper(value))
					return value;
				return getDisplayName(context);
			}
			return ModelUtil.createStringWrapper(""); //$NON-NLS-1$
		}

		@Override
		public void setValue(Object context, Object value) {
			T object = adopt(context);
			Resource resource = ModelUtil.getResource(object);
			
			if (value instanceof PortType) {
				PortType portType = (PortType)value;
				value = NamespaceUtil.normalizeQName(resource, portType.getQName());;
			}
			else if (value instanceof Process) {
				Process process = (Process)value;
				if (process.getSupportedInterfaceRefs().size()>0)
					value = process.getSupportedInterfaceRefs().get(0).getImplementationRef();
			}

			if (value instanceof String) {
				value = ModelUtil.createStringWrapper((String)value);
			}
			else if (!ModelUtil.isStringWrapper(value)) {
				return;
			}
			super.setValue(object,value);
		}
	}

}

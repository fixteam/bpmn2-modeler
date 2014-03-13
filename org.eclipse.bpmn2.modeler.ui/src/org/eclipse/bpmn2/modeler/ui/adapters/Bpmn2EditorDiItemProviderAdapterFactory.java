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

package org.eclipse.bpmn2.modeler.ui.adapters;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.provider.BpmnDiItemProviderAdapterFactory;
import org.eclipse.bpmn2.di.util.BpmnDiSwitch;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.adapters.ObjectDescriptor;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.Messages;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author Bob Brodt
 *
 */
public class Bpmn2EditorDiItemProviderAdapterFactory extends BpmnDiItemProviderAdapterFactory {

	/**
	 * 
	 */
	public Bpmn2EditorDiItemProviderAdapterFactory() {
		super();
		supportedTypes.add(ExtendedPropertiesAdapter.class);
	}


	@Override
	public Adapter adaptNew(Notifier object, Object type) {
		if (type == ExtendedPropertiesAdapter.class && object instanceof EObject) {
			return bpmnDiModelSwitch.doSwitch((EObject) object);
		}
		return super.adaptNew(object, type);
	}
	
    protected BpmnDiSwitch<ExtendedPropertiesAdapter> bpmnDiModelSwitch = new BpmnDiExtendedPropertiesSwitch(this);
    
    public class BpmnDiExtendedPropertiesSwitch extends BpmnDiSwitch<ExtendedPropertiesAdapter> {

    	private AdapterFactory adapterFactory;
    	
    	public BpmnDiExtendedPropertiesSwitch(AdapterFactory adapterFactory) {
    		super();
    		this.adapterFactory = adapterFactory;
    	}
    	
        @SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public ExtendedPropertiesAdapter defaultCase(EObject object) {
        	ExtendedPropertiesAdapter adapter = getBpmnElementAdapter(object);
        	if (adapter!=null)
        		return adapter;
        	
        	adapter = new ExtendedPropertiesAdapter(adapterFactory,object);
        	adapter.setObjectDescriptor(new ObjectDescriptor(adapterFactory, object) {
				@Override
				public String getLabel(Object context) {
					EObject object = adopt(context);
		        	ExtendedPropertiesAdapter adapter = getBpmnElementAdapter(object);
		        	if (adapter!=null) {
		        		BaseElement bpmnElement = getBpmnElement(object);
		        		if (bpmnElement!=null)
		        			return adapter.getObjectDescriptor().getLabel(context);
		        	}
		        	
					if (ModelUtil.isStringWrapper(object)) {
						return Messages.CommonLabels_Data_Type;
					}
					return super.getLabel(context);
				}

				@Override
				public String getDisplayName(Object context) {
					EObject object = adopt(context);
		        	ExtendedPropertiesAdapter adapter = getBpmnElementAdapter(object);
		        	if (adapter!=null) {
		        		BaseElement bpmnElement = getBpmnElement(object);
		        		if (bpmnElement!=null)
		        			return adapter.getObjectDescriptor().getDisplayName(context);
		        	}
		        	
					if (ModelUtil.isStringWrapper(object)) {
						return ModelUtil.getStringWrapperValue(object);
					}
					return super.getDisplayName(context);
				}
        	});
        	return adapter;
		}
        
        private BaseElement getBpmnElement(EObject object) {
        	if (object instanceof BPMNDiagram) {
        		EObject plane = ((BPMNDiagram)object).getPlane();
        		if (plane!=null)
        			object = plane;
        	}
        	EStructuralFeature bpmnElementFeature = object.eClass().getEStructuralFeature("bpmnElement"); //$NON-NLS-1$
        	if (bpmnElementFeature!=null) {
        		EObject bpmnElement = (EObject)object.eGet(bpmnElementFeature);
        		if (bpmnElement instanceof BaseElement) {
        			object = bpmnElement;
        		}
        	}
        	if (object instanceof BaseElement)
        		return (BaseElement) object;
        	return null;
        }
        
        private ExtendedPropertiesAdapter getBpmnElementAdapter(EObject object) {
        	ExtendedPropertiesAdapter adapter = null;
        	if (object instanceof BPMNDiagram) {
        		EObject plane = ((BPMNDiagram)object).getPlane();
        		if (plane!=null)
        			object = plane;
        	}
        	EStructuralFeature bpmnElementFeature = object.eClass().getEStructuralFeature("bpmnElement"); //$NON-NLS-1$
        	if (bpmnElementFeature!=null) {
        		Object bpmnElement = object.eGet(bpmnElementFeature);
        		if (bpmnElement instanceof BaseElement) {
        			adapter = ExtendedPropertiesAdapter.adapt(bpmnElement);
        		}
        	}
			return adapter;
        }
    }
}

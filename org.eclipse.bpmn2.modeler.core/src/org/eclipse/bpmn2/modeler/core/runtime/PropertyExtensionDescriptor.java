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

package org.eclipse.bpmn2.modeler.core.runtime;

import java.lang.reflect.Constructor;

import org.eclipse.bpmn2.modeler.core.Activator;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * @author Bob Brodt
 *
 */
public class PropertyExtensionDescriptor extends BaseRuntimeDescriptor {
	
    protected String type;
    protected String adapterClassName;
	private final IConfigurationElement element;

	/**
	 * @param rt
	 */
	public PropertyExtensionDescriptor(TargetRuntime rt, IConfigurationElement element) {
		super(rt);
		this.element = element;
	}

	@SuppressWarnings("rawtypes")
	public Class getInstanceClass() {
	    if (type == null) {
	        return null;
	    }
		try {
			return Platform.getBundle(element.getContributor().getName()).loadClass(type);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public ExtendedPropertiesAdapter getAdapter(AdapterFactory adapterFactory, EObject object) {
        if (adapterClassName == null) {
            return null;
        }
		try {
			Constructor ctor = null;
			Class adapterClass = Platform.getBundle(element.getContributor().getName()).loadClass(adapterClassName);
			EClass eclass = null;
			if (object instanceof EClass) {
				eclass = (EClass)object;
				object = ExtendedPropertiesAdapter.getDummyObject(eclass);
			}
			else {
				eclass = object.eClass();
			}
			ctor = getConstructor(adapterClass, eclass);
			return (ExtendedPropertiesAdapter)ctor.newInstance(adapterFactory, object);
		} catch (Exception e) {
			Activator.logError(e);
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Constructor getConstructor(Class adapterClass, EClass eclass) {
		Constructor ctor = null;
		try {
			ctor = adapterClass.getConstructor(AdapterFactory.class, eclass.getInstanceClass());
		}
		catch (NoSuchMethodException e) {
			for (EClass superClass : eclass.getESuperTypes()) {
				ctor = getConstructor(adapterClass, superClass);
				if (ctor!=null)
					break;
			}
		}
		return ctor;
	}
}

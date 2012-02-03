/*******************************************************************************
 * Copyright (c) 2011 Red Hat, Inc.
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.bpmn2.Bpmn2Factory;
import org.eclipse.bpmn2.Choreography;
import org.eclipse.bpmn2.Collaboration;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.modeler.core.ModelHandler;
import org.eclipse.bpmn2.modeler.core.adapters.InsertionAdapter;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

/**
 * @author Bob Brodt
 *
 */
public class Bpmn2FeatureDescriptor extends Bpmn2ObjectDescriptor {

	protected EStructuralFeature feature;
	protected int multiline = 0; // -1 = false, +1 = true, 0 = unset
	protected Collection choiceOfValues; // for static lists
	
	public Bpmn2FeatureDescriptor(AdapterFactory adapterFactory, EObject object, EStructuralFeature feature) {
		super(adapterFactory, object);
		this.feature = feature;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLabel(Object context) {
		if (label==null) {
			IItemPropertyDescriptor propertyDescriptor = getPropertyDescriptor(feature);
			if (propertyDescriptor != null)
				label = propertyDescriptor.getDisplayName(object);
			else
				label = ModelUtil.toDisplayName(feature.getName());
		}
		return label;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText(Object context) {
		if (text==null) {
			String t = null;
			// derive text from feature's value: default behavior is
			// to use the "name" attribute if there is one;
			// if not, use the "id" attribute;
			// fallback is to use the feature's toString()
			EObject o = null;
			EStructuralFeature f = null;
			if (feature!=null) {
				Object value = object.eGet(feature); 
				if (value instanceof EObject) {
					o = (EObject)object.eGet(feature);
				}
				else if (value!=null)
					t = value.toString();
			}
			if (t==null && o!=null) {
				f = o.eClass().getEStructuralFeature("name");
				if (f!=null) {
					String name = (String)o.eGet(f);
					if (name!=null && !name.isEmpty())
						t = name;
				}
			}
			if (t==null && o!=null) {
				f = o.eClass().getEStructuralFeature("id");
				if (f!=null) {
					Object id = o.eGet(f);
					if (id!=null && !id.toString().isEmpty())
						t = id.toString();
				}
			}
			return t == null ? "" : t;
		}
		return text == null ? "" : text;
	}

	public void setChoiceOfValues(Collection choiceOfValues) {
		this.choiceOfValues = choiceOfValues;
	}
	
	public Collection getChoiceOfValues(Object context) {
		EObject object = context instanceof EObject ? (EObject)context : this.object;
		if (choiceOfValues==null) {
			try {
				IItemPropertyDescriptor propertyDescriptor = getPropertyDescriptor(feature);
				if (propertyDescriptor!=null) {
					return propertyDescriptor.getChoiceOfValues(object);
				}
			}
			catch (Exception e) {
				// ignore exceptions if we fail to resolve proxies;
				// e.g. and instance of a DynamicEObjectImpl with a bogus
				// URI is used for ItemDefinition.structureRef
				// fallback is to do our own search
			}
			return ModelUtil.getAllReachableObjects(object, feature);
		}
		return choiceOfValues;
	}

	public void setMultiLine(boolean multiline) {
		this.multiline = multiline ? 1 : -1;
	}
	
	public boolean isMultiLine(Object context) {
		if (multiline==0) {
			IItemPropertyDescriptor propertyDescriptor = getPropertyDescriptor(feature);
			if (propertyDescriptor!=null)
				multiline = propertyDescriptor.isMultiLine(object) ? 1 : -1;
		}
		return multiline == 1;
	}

	public EObject createObject() {
		return createObject(object);
	}
	
	public EObject createObject(Object context) {
		return createObject(context, null);
	}		
	
	public EObject createObject(Object context, EClass eclass) {
		EObject object = context instanceof EObject ? (EObject)context : this.object;
		try {
			if (eclass==null)
				eclass = (EClass)feature.getEType();
			
			ModelHandler mh = ModelHandler.getInstance(object);
			if (mh!=null)
				return mh.create(eclass);
			// object is not yet added to a Resource so use an insertion adapter
			// to add it later
			EObject value = Bpmn2Factory.eINSTANCE.create(eclass);
			InsertionAdapter.add(object, feature, value);
			return value;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// NOTE: getValue() and setValue() must be symmetrical; that is, setValue()
	// must be able to handle the object type returned by getValue(), although
	// setValue() may also know how to convert from other types, e.g. String,
	// Integer, etc.
	public Object getValue() {
		return getValue(object);
	}
	
	public Object getValue(Object context) {
		EObject object = context instanceof EObject ? (EObject)context : this.object;
		return object.eGet(feature);
	}
	
	public void setValue(Object value) {
		setValue(object,value);
	}
	
	public void setValue(Object context, final Object value) {
		EObject object = context instanceof EObject ? (EObject)context : this.object;
		
		if (object.eGet(feature) instanceof EObjectEList) {
			// the feature is a reference list - user must have meant to insert
			// the value into this list...
			final EObjectEList list = (EObjectEList)object.eGet(feature);
			TransactionalEditingDomain editingDomain = getEditingDomain(object);
			if (editingDomain == null) {
				list.add(value);
			} else {
				editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {
					@Override
					protected void doExecute() {
						list.add(value);
					}
				});
			}
		}
		else {
			IItemPropertyDescriptor propertyDescriptor = getPropertyDescriptor(object, feature);
			if (propertyDescriptor != null) {
				propertyDescriptor.setPropertyValue(object, value);
			}
			else {
				TransactionalEditingDomain editingDomain = getEditingDomain(object);
				if (editingDomain == null) {
					object.eSet(feature, value);
				} else {
					editingDomain.getCommandStack().execute(SetCommand.create(editingDomain, object, feature, value));
				}
			}
		}
		
		if (value instanceof RootElement && ((RootElement)value).eContainer()==null) {
			// stuff all root elements into Definitions.rootElements
			final Definitions definitions = ModelUtil.getDefinitions(object);
			if (definitions!=null) {
				TransactionalEditingDomain editingDomain = getEditingDomain(object);
				if (editingDomain == null) {
					definitions.getRootElements().add((RootElement)value);
				} else {
					editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {
						@Override
						protected void doExecute() {
							definitions.getRootElements().add((RootElement)value);
						}
					});
				}
			}
		}
	}

}

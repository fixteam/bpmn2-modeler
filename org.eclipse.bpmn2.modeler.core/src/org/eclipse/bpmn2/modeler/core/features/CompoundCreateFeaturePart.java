/*******************************************************************************
 * Copyright (c) 2011, 2012, 2013 Red Hat, Inc.
 * All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 	Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.features;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.modeler.core.runtime.ModelDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.runtime.ToolPaletteDescriptor;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.impl.CreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.CreateContext;
import org.eclipse.graphiti.features.context.impl.UpdateContext;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FixPointAnchor;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;

public class CompoundCreateFeaturePart<CONTEXT> {
	
	IFeature feature;
	List<CompoundCreateFeaturePart<CONTEXT>> children = new ArrayList<CompoundCreateFeaturePart<CONTEXT>>();
	Hashtable<String, String> properties = null;
	
	public CompoundCreateFeaturePart(IFeature feature) {
		this.feature = feature;
	}
	
	public boolean canCreate(IContext context) {
		if (feature instanceof ICreateFeature && context instanceof ICreateContext) {
			if (!((ICreateFeature)feature).canCreate((ICreateContext)context))
				return false;
		}
		else if (feature instanceof ICreateConnectionFeature && context instanceof ICreateConnectionContext) {
			if (!((ICreateConnectionFeature)feature).canCreate((ICreateConnectionContext)context))
				return false;
		}
		return true;
	}

	public List<Object> create(IContext context) {
		// Create the parent element.
		// For ICreateContext this will result in a BaseElement and a ContainerShape;
		// for ICreateConnectionContext we only get a Graphiti Connection element.
		List<Object> businessObjects = new ArrayList<Object>();
		if (feature instanceof ICreateFeature && context instanceof ICreateContext) {
			if (canCreate(context)) {
				Object created[] = ((ICreateFeature)feature).create((ICreateContext)context);
				for (Object o : created)
					businessObjects.add(o);
			}
		}
		else if (feature instanceof ICreateConnectionFeature && context instanceof ICreateConnectionContext) {
			if (canCreate(context)) {
				businessObjects.add(((ICreateConnectionFeature)feature).create((ICreateConnectionContext)context));
			}
		}

		BaseElement businessObject = null;
		ContainerShape targetContainer = null;
		Connection connection = null;
		for (Object o : businessObjects) {
			if (o instanceof ContainerShape && targetContainer==null) {
				targetContainer = (ContainerShape)o;
			}
			else if (o instanceof Connection && connection==null) {
				connection = (Connection)o;
			}
			else if (o instanceof BaseElement && businessObject==null) {
				businessObject = (BaseElement)o;
			}
		}
		if (connection!=null) {
			// we need the BaseElement that is linked to this connection
			businessObject = BusinessObjectUtil.getFirstBaseElement(connection);
		}
		// initialize any model features specified in the ToolPart definition
		applyBusinessObjectProperties(businessObject);
		
		// Now process the child features
		List<PictogramElement> createdPEs = new ArrayList<PictogramElement>();
		for (int i =0; i<children.size(); ++i) {
			CompoundCreateFeaturePart<CONTEXT> node = children.get(i);
			node.create(context, targetContainer, createdPEs, businessObjects);
		}
		return businessObjects;
	}

	public void create(IContext context, ContainerShape targetContainer, List<PictogramElement> pictogramElements, List<Object> businessObjects) {
		IContext childContext = null;
		String value;
		int x = 0;
		int y = 0;
		int width = 0;
		int height = 0;
		if (context instanceof ICreateContext) {
			ICreateContext cc = (ICreateContext)context;
			x = cc.getX();
			y = cc.getY();
			width = cc.getWidth();
			height = cc.getHeight();
		}
		PictogramElement source = null;
		PictogramElement target = null;
		int index = pictogramElements.size();
		
		// Construct a create context for either a shape or connection,
		// depending on the type of element to be created, and apply
		// any creation properties from the ToolPart definition.
		if (feature instanceof ICreateFeature) {
			CreateContext cc = new CreateContext();
			if (targetContainer==null)
				targetContainer = ((ICreateContext)context).getTargetContainer();
			cc.setTargetContainer(targetContainer);
			cc.setTargetConnection(((ICreateContext)context).getTargetConnection());
			value = this.getProperty("x"); //$NON-NLS-1$
			if (value!=null) {
				if (targetContainer instanceof Diagram)
					x += Integer.parseInt(value);
				else
					x = Integer.parseInt(value);
			}
			cc.setX(x);
			value = this.getProperty("y"); //$NON-NLS-1$
			if (value!=null) {
				if (targetContainer instanceof Diagram)
					y += Integer.parseInt(value);
				else
					y = Integer.parseInt(value);
			}
			cc.setY(y);
			value = this.getProperty("width"); //$NON-NLS-1$
			if (value!=null) {
				width = Integer.parseInt(value);
			}
			cc.setWidth(width);
			value = this.getProperty("height"); //$NON-NLS-1$
			if (value!=null) {
				height = Integer.parseInt(value);
			}
			cc.setHeight(height);
			
			childContext = cc;
		}
		else if (feature instanceof ICreateConnectionFeature) {
			CreateConnectionContext cc = new CreateConnectionContext();
			value = this.getProperty("source"); //$NON-NLS-1$
			if (value!=null) {
				for (PictogramElement pe : pictogramElements) {
					String id = Graphiti.getPeService().getPropertyValue(pe, ToolPaletteDescriptor.TOOLPART_ID);
					if (value.equals(id)) {
						source = pe;
						break;
					}
				}
			}
			else if (index-2>=0 && index-2<pictogramElements.size())
				source = pictogramElements.get(index-2);
			
			if (source==null)
				source = ((ICreateConnectionContext)context).getSourcePictogramElement();
			
			value = this.getProperty("target"); //$NON-NLS-1$
			if (value!=null) {
				for (PictogramElement pe : pictogramElements) {
					String id = Graphiti.getPeService().getPropertyValue(pe, ToolPaletteDescriptor.TOOLPART_ID);
					if (value.equals(id)) {
						target = pe;
						break;
					}
				}
			}
			else if (index-1>=0 && index-1<pictogramElements.size())
				target = pictogramElements.get(index-1);

			if (target==null)
				target = ((ICreateConnectionContext)context).getTargetPictogramElement();
			
			Point sp = AnchorUtil.getCenterPoint((Shape)source);
			Point tp = AnchorUtil.getCenterPoint((Shape)target);
			FixPointAnchor sourceAnchor = AnchorUtil.findNearestAnchor((Shape)source, tp);
			FixPointAnchor targetAnchor = AnchorUtil.findNearestAnchor((Shape)target, sp);
			cc.setSourcePictogramElement(source);
			cc.setTargetPictogramElement(target);
			cc.setSourceAnchor(sourceAnchor);
			cc.setTargetAnchor(targetAnchor);
			
			childContext = cc;
		}
		
		List<Object> result = null;
		
		result = create(childContext);
		PictogramElement pe = null;
		Connection cn = null;
		BaseElement be = null;
		for (Object o : result) {
			if (o instanceof ContainerShape) {
				pe = (ContainerShape)o;
			}
			else if (o instanceof Connection) {
				cn = (Connection)o;
			}
			else if (o instanceof BaseElement) {
				be = (BaseElement)o;
			}
		}
		
		PictogramElement updatePE = null;
		if (pe!=null) {
			pictogramElements.add(pe);
			value = this.getProperty(ToolPaletteDescriptor.TOOLPART_ID);
			if (value!=null) {
				Graphiti.getPeService().setPropertyValue(pe, ToolPaletteDescriptor.TOOLPART_ID, value);
			}
			updatePE = pe;
		}
		else if (cn!=null) {
			be = BusinessObjectUtil.getFirstBaseElement(cn);
			value = this.getProperty(ToolPaletteDescriptor.TOOLPART_ID);
			if (value!=null) {
				Graphiti.getPeService().setPropertyValue(cn, ToolPaletteDescriptor.TOOLPART_ID, value);
			}
			updatePE = cn;
		}

		// initialize any model features specified in the ToolPart definition
		applyBusinessObjectProperties(be);
		
		// Update the newly created pictogram element if needed.
		// This should be done within the same transaction so that a single
		// "Undo" can be used to delete all pictogram elements without having
		// to cycle through each transaction created by an Update.
		if (updatePE!=null) {
			UpdateContext updateContext = new UpdateContext(updatePE);
			IUpdateFeature updateFeature = feature.getFeatureProvider().getUpdateFeature(updateContext);
			if ( updateFeature.updateNeeded(updateContext).toBoolean() )
				updateFeature.update(updateContext);
		}
		
		businessObjects.add(result);
	}
	
	private void applyBusinessObjectProperties(BaseElement be) {
		if (be!=null && properties!=null) {
			ModelDescriptor md = TargetRuntime.getCurrentRuntime().getModelDescriptor();
			for (Entry<String, String> entry : properties.entrySet()) {
				if (entry.getKey().startsWith("$")) { //$NON-NLS-1$
					String featureName = entry.getKey().substring(1);
					EStructuralFeature feature = md.getFeature(be.eClass().getName(), featureName);
					String value = entry.getValue();
					if (value.startsWith("$")) { //$NON-NLS-1$
						String name = value.substring(1);
						EClassifier eClass = md.getClassifier(name);
						EFactory factory = eClass.getEPackage().getEFactoryInstance();
						EObject object = factory.create((EClass)eClass);
						be.eSet(feature, object);
					}
					else {
						md.setValueFromString(be, feature, value, true);
					}
				}
			}
		}

	}
	
	public boolean isAvailable(IContext context) {
		if (feature!=null && !feature.isAvailable(context))
			return false;
		for (CompoundCreateFeaturePart<CONTEXT> ft : children) {
			if (!ft.isAvailable(context))
				return false;
		}
		return true;
	}
	
	public CompoundCreateFeaturePart<CONTEXT> addChild(IFeature feature) {
		CompoundCreateFeaturePart<CONTEXT> node = new CompoundCreateFeaturePart<CONTEXT>(feature);
		children.add(node);
		return node;
	}

	public EClass getBusinessObjectClass() {
		EClass eClass = null;
		if (feature instanceof AbstractBpmn2CreateFeature) {
			eClass = ((AbstractBpmn2CreateFeature)feature).getBusinessObjectClass();
		}
		else if (feature instanceof AbstractBpmn2CreateConnectionFeature) {
			eClass = ((AbstractBpmn2CreateConnectionFeature)feature).getBusinessObjectClass();
		}
		if (eClass==null) {
			for (CompoundCreateFeaturePart<CONTEXT> child : children) {
				EClass ec = child.getBusinessObjectClass();
				if (ec!=null) {
					eClass = ec;
					break;
				}
			}
		}
		return eClass;
	}

	public String getCreateName() {
		String createName = null;
		if (feature!=null)
			createName = feature.getName();
		for (CompoundCreateFeaturePart<CONTEXT> child : children) {
			String cn = child.getCreateName();
			if (cn!=null)
				createName = cn;
		}
		return createName;
	}
	public IFeature getFeature() {
		return feature;
	}

	public void setFeature(IFeature feature) {
		this.feature = feature;
	}

	public List<CompoundCreateFeaturePart<CONTEXT>> getChildren() {
		return children;
	}

	public void setProperties(Hashtable<String, String> properties) {
		getProperties().putAll(properties);
	}
	
	public Hashtable<String, String> getProperties() {
		if (properties==null)
			properties = new Hashtable<String, String>();
		return properties;
	}
	
	public String getProperty(String name) {
		if (properties==null)
			return null;
		return properties.get(name);
	}
}

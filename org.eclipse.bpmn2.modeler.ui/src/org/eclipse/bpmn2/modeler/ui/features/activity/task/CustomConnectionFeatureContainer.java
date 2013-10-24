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
package org.eclipse.bpmn2.modeler.ui.features.activity.task;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2AddFeature;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2CreateConnectionFeature;
import org.eclipse.bpmn2.modeler.core.features.IConnectionFeatureContainer;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskImageProvider.IconSize;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.IExecutionInfo;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IReconnectionFeature;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IAreaContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;

public class CustomConnectionFeatureContainer extends CustomElementFeatureContainer implements IConnectionFeatureContainer {
	
	public String getDescription() {
		if (customTaskDescriptor!=null)
			return customTaskDescriptor.getDescription();
		return Messages.CustomConnectionFeatureContainer_Description;
	}
	
	@Override
	public ICreateConnectionFeature getCreateConnectionFeature(IFeatureProvider fp) {
		return new CreateCustomConnectionFeature(fp);
	}
	
	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new AddCustomConnectionFeature(fp);
	}
	
	/**
	 * @author bbrodt
	 *
	 * Base class for Custom Connection Feature construction. Custom Connections contributed to
	 * the editor MUST subclass this!
	 * 
	 * This class is intended for creation of BPMN2 objects that have custom model
	 * extensions. This is for any object considered to be a "connection", e.g.
	 * Sequence Flows, Associations, Message Flows and Conversation Links  
	 * 
	 * The creation process copies the modelObject ID string into the Graphiti create
	 * context during the construction phase, then migrates that ID into the created
	 * PictogramElement. This is necessary because the ID must be associated with the
	 * PE to allow our BPMNFeatureProvider to correctly identify the Custom Task.
	 */
	public class CreateCustomConnectionFeature extends AbstractBpmn2CreateConnectionFeature<BaseElement, EObject, EObject> {

		protected AbstractBpmn2CreateConnectionFeature createFeatureDelegate;
		
		public CreateCustomConnectionFeature(IFeatureProvider fp, String name, String description) {
			super(fp, name, description);
			IConnectionFeatureContainer fc = (IConnectionFeatureContainer)getFeatureContainer(fp);
			createFeatureDelegate = (AbstractBpmn2CreateConnectionFeature)fc.getCreateConnectionFeature(fp);
			Assert.isNotNull(createFeatureDelegate);
		}

		public CreateCustomConnectionFeature(IFeatureProvider fp) {
			this(fp, customTaskDescriptor.getName(), customTaskDescriptor.getDescription());
		}
		
		@Override
		public boolean isAvailable(IContext context) {
			// Custom Elements are not available to the Context Button Pad:
			// The Graphiti CreateConnectionCommand constructs a single ICreateConnectionContext, and reuses
			// it for all command/context pairs that are collected for display and user selection in a popup
			// menu (@see org.eclipse.graphiti.ui.internal.command.CreateConnectionCommand#execute()).
			// Each command/context pair is first tested with @link org.eclipse.graphiti.internal.command#canExecute()
			// which eventually calls our @link CreateCustomConnectionFeature#canCreate() where we insert the
			// Custom Element ID into the context properties.
			// Thus, all command/context pairs will contain this ID and will attempt to create a Custom Element
			// when executed.
			return false;
		}
		
		@Override
		protected PictogramElement addGraphicalRepresentation(IAreaContext context, Object newObject) {

			// create a new AddContext and copy our ID into it.
			IAddContext addContext = new AddContext(context, newObject);
			setId(addContext, getId());
			
			// create the PE and copy our ID into its properties.
			PictogramElement pe = getFeatureProvider().addIfPossible(addContext);
			Graphiti.getPeService().setPropertyValue(pe,CUSTOM_ELEMENT_ID,id);
			
			return pe;
		}

		@Override
		public boolean canCreate(ICreateConnectionContext context) {
			// copy our ID into the CreateContext - this is where it all starts!
			setId(context, id);
			return createFeatureDelegate.canCreate(context);
		}

		@Override
		public boolean canStartConnection(ICreateConnectionContext context) {
			setId(context, id);
			return createFeatureDelegate.canStartConnection(context);
		}

		@Override
		public BaseElement createBusinessObject(ICreateConnectionContext context) {
			BaseElement businessObject = createFeatureDelegate.createBusinessObject(context);
			customTaskDescriptor.populateObject(businessObject, false);
			return businessObject;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.bpmn2.modeler.core.features.AbstractCreateFlowElementFeature#getFlowElementClass()
		 */
		@Override
		public EClass getBusinessObjectClass() {
			return createFeatureDelegate.getBusinessObjectClass();
		}

		@Override
		public Connection create(ICreateConnectionContext context) {
			// Our Custom Task ID should have already been set in canCreate()
			// if not, we have a problem; in other words, canCreate() MUST have
			// been called by the framework before create()
			Assert.isNotNull(getId(context));
			return createFeatureDelegate.create(context);
		}

		@Override
		public String getCreateImageId() {
			String icon = customTaskDescriptor.getIcon();
			if (icon!=null) {
				String id = customTaskDescriptor.getImageId(icon, IconSize.SMALL);
				if (id!=null)
					return id;
			}
			return createFeatureDelegate.getCreateImageId();
		}

		@Override
		public String getCreateLargeImageId() {
			String icon = customTaskDescriptor.getIcon();
			if (icon!=null) {
				String id = customTaskDescriptor.getImageId(icon, IconSize.LARGE);
				if (id!=null)
					return id;
			}
			return createFeatureDelegate.getCreateLargeImageId();
		}

		@Override
		protected Class<EObject> getSourceClass() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected Class<EObject> getTargetClass() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public class AddCustomConnectionFeature extends AbstractBpmn2AddFeature<BaseElement> {

		protected AbstractBpmn2AddFeature<BaseElement> addFeatureDelegate;
		
		/**
		 * @param fp
		 */
		public AddCustomConnectionFeature(IFeatureProvider fp) {
			super(fp);
			addFeatureDelegate = (AbstractBpmn2AddFeature)getFeatureContainer(fp).getAddFeature(fp);
			Assert.isNotNull(addFeatureDelegate);
		}

		@Override
		public PictogramElement add(IAddContext context) {
			PictogramElement pe = addFeatureDelegate.add(context);
			// make sure everyone knows that this PE is a custom task
			if (pe!=null)
				Graphiti.getPeService().setPropertyValue(pe,CUSTOM_ELEMENT_ID,getId());

			return pe;
		}
		
		@Override
		public BaseElement getBusinessObject(IAddContext context) {
			// TODO Auto-generated method stub
			return addFeatureDelegate.getBusinessObject(context);
		}

		@Override
		public void putBusinessObject(IAddContext context, BaseElement businessObject) {
			addFeatureDelegate.putBusinessObject(context, businessObject);
		}

		@Override
		public void postExecute(IExecutionInfo executionInfo) {
			addFeatureDelegate.postExecute(executionInfo);
		}

		@Override
		public boolean canAdd(IAddContext context) {
			return addFeatureDelegate.canAdd(context);
		}
	}
	
	@Override
	public IUpdateFeature getUpdateFeature(IFeatureProvider fp) {
		return getFeatureContainer(fp).getUpdateFeature(fp);
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(IFeatureProvider fp) {
		return getFeatureContainer(fp).getDirectEditingFeature(fp);
	}
	
	@Override
	public ILayoutFeature getLayoutFeature(IFeatureProvider fp) {
		return getFeatureContainer(fp).getLayoutFeature(fp);
	}

	@Override
	public IRemoveFeature getRemoveFeature(IFeatureProvider fp) {
		return getFeatureContainer(fp).getRemoveFeature(fp);
	}

	@Override
	public IDeleteFeature getDeleteFeature(IFeatureProvider fp) {
		return getFeatureContainer(fp).getDeleteFeature(fp);
	}
	
	@Override
	public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
		return getFeatureContainer(fp).getCustomFeatures(fp);
	}

	@Override
	public IReconnectionFeature getReconnectionFeature(IFeatureProvider fp) {
		IConnectionFeatureContainer fc = (IConnectionFeatureContainer)getFeatureContainer(fp);
		return fc.getReconnectionFeature(fp);
	}

}

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
import org.eclipse.bpmn2.impl.TaskImpl;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2AddFeature;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2CreateFeature;
import org.eclipse.bpmn2.modeler.core.features.FeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.activity.task.ICustomTaskFeatureContainer;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskImageProvider;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskImageProvider.IconSize;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.diagram.BPMNFeatureProvider;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.IExecutionInfo;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IAreaContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.core.runtime.Assert;

public class CustomTaskFeatureContainer extends TaskFeatureContainer implements ICustomTaskFeatureContainer {
	
	protected String id;
	protected CustomTaskDescriptor customTaskDescriptor;
	protected FeatureContainer featureContainerDelegate = null;
	protected BPMNFeatureProvider fp;
	
	public CustomTaskFeatureContainer() {
	}
	
	public String getDescription() {
		if (customTaskDescriptor!=null)
			return customTaskDescriptor.getDescription();
		return "Custom Task";
	}

	protected FeatureContainer createFeatureContainer(IFeatureProvider fp) {
		EClass eClass = (EClass) ModelUtil.getEClassifierFromString(
				customTaskDescriptor.getRuntime().getModelDescriptor().getEPackage(), customTaskDescriptor.getType());
		return ((BPMNFeatureProvider)fp).getFeatureContainer(eClass.getInstanceClass());
	}
	
	protected FeatureContainer getFeatureContainer(IFeatureProvider fp) {
		if (featureContainerDelegate==null) {
			featureContainerDelegate = createFeatureContainer(fp);
		}
		return featureContainerDelegate;
	}
	
	/* (non-Javadoc)
	 * Determine if the context applies to this modelObject and return the Task object. Return null otherwise.
	 * @param context - the Graphiti context.
	 * 
	 * @see org.eclipse.bpmn2.modeler.core.features.BaseElementFeatureContainer#getApplyObject(org.eclipse.graphiti.features.context.IContext)
	 */
	@Override
	public Object getApplyObject(IContext context) {
		Object id = getId(context);
		if (id==null || !this.id.equals(id)) {
			return null;
		}

		return super.getApplyObject(context);
	}

	@Override
	public boolean canApplyTo(Object o) {
		boolean b1 =  o instanceof TaskImpl;
		boolean b2 = o.getClass().isAssignableFrom(TaskImpl.class);
		return b1 || b2;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.activity.task.ICustomTaskFeatureContainer#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Set this modelObject's ID in the given Graphiti context.
	 * 
	 * @param context - if this is a IPictogramElementContext, set the property
	 *                  in the contained PictogramElement's list of properties;
	 *                  otherwise set the Context's property 
	 * @param id - ID of this Custom Task
	 */
	public static void setId(IContext context, String id) {
		
		if (context instanceof IPictogramElementContext) {
			PictogramElement pe = ((IPictogramElementContext)context).getPictogramElement();
			Graphiti.getPeService().setPropertyValue(pe,CUSTOM_TASK_ID,id); 
		}
		else {
			context.putProperty(CUSTOM_TASK_ID, id);
		}
	}
	
	/**
	 * Returns the modelObject ID string from the given Graphiti context.
	 * 
	 * @param context
	 * @return - ID string for this modelObject.
	 */
	public static String getId(IContext context) {
		Object id = null;

		/**
		 * IAddContext can also mean that a file is dragged,
		 * therefore we have to check if we are really dragging a customTask
		 */
		if (context instanceof IAddContext && 
			((IAddContext)context).getNewObject() instanceof EObject ) {
			EObject object = (EObject) ((IAddContext)context).getNewObject();
			TargetRuntime rt = TargetRuntime.getCurrentRuntime();
			for (CustomTaskDescriptor ct : rt.getCustomTasks()) {
				id = ct.getFeatureContainer().getId(object);
				if (id!=null) {
					context.putProperty(CUSTOM_TASK_ID, id);
					return (String)id;
				}
			}
		}
		
		if (context instanceof IPictogramElementContext) {
			PictogramElement pe = ((IPictogramElementContext)context).getPictogramElement();
			id = Graphiti.getPeService().getPropertyValue(pe,CUSTOM_TASK_ID); 
		}
		else {
			id = context.getProperty(CUSTOM_TASK_ID);
		}
		return (String)id;
	}

	public String getId(EObject object) {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.ui.features.activity.task.ICustomTaskFeatureContainer#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.ui.features.activity.task.ICustomTaskFeatureContainer#setCustomTaskDescriptor(org.eclipse.bpmn2.modeler.core.preferences.TargetRuntime.CustomTaskDescriptor)
	 */
	@Override
	public void setCustomTaskDescriptor(CustomTaskDescriptor customTaskDescriptor) {
		this.customTaskDescriptor = customTaskDescriptor;
	}
	
	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateCustomTaskFeature(fp);
	}
	
	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new AddCustomTaskFeature(fp);
	}
	
	/**
	 * @author bbrodt
	 *
	 * Base class for Custom Task Feature construction. Custom Tasks contributed to
	 * the editor MUST subclass this!
	 * 
	 * The Task creation process copies the modelObject ID string into the Graphiti create
	 * context during the construction phase, then migrates that ID into the created
	 * PictogramElement. This is necessary because the ID must be associated with the
	 * PE in to allow our BPMNFeatureProvider to correctly identify the Custom Task.
	 */
	public class CreateCustomTaskFeature extends AbstractBpmn2CreateFeature<BaseElement> {

		protected AbstractBpmn2CreateFeature<BaseElement> createFeatureDelegate;
		
		public CreateCustomTaskFeature(IFeatureProvider fp, String name, String description) {
			super(fp, name, description);
			createFeatureDelegate = (AbstractBpmn2CreateFeature)getFeatureContainer(fp).getCreateFeature(fp);
			Assert.isNotNull(createFeatureDelegate);
		}

		public CreateCustomTaskFeature(IFeatureProvider fp) {
			super(fp, customTaskDescriptor.getName(), customTaskDescriptor.getDescription());
			createFeatureDelegate = (AbstractBpmn2CreateFeature)getFeatureContainer(fp).getCreateFeature(fp);
			Assert.isNotNull(createFeatureDelegate);
		}

		@Override
		protected PictogramElement addGraphicalRepresentation(IAreaContext context, Object newObject) {

			// create a new AddContext and copy our ID into it.
			IAddContext addContext = new AddContext(context, newObject);
			setId(addContext, getId());
			
			// create the PE and copy our ID into its properties.
			PictogramElement pe = getFeatureProvider().addIfPossible(addContext);
			Graphiti.getPeService().setPropertyValue(pe,CUSTOM_TASK_ID,id);
			
			return pe;
		}

		@Override
		public boolean canCreate(ICreateContext context) {
			// copy our ID into the CreateContext - this is where it all starts!
			setId(context, id);
			return createFeatureDelegate.canCreate(context);
		}

		@Override
		public BaseElement createBusinessObject(ICreateContext context) {
//			EObject target = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(context.getTargetContainer());
//			BaseElement businessObject = (BaseElement)customTaskDescriptor.createObject(target);
//			putBusinessObject(context, businessObject);
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
		public Object[] create(ICreateContext context) {
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
	}

	public class AddCustomTaskFeature extends AbstractBpmn2AddFeature<BaseElement> {

		protected AbstractBpmn2AddFeature<BaseElement> addFeatureDelegate;
		
		/**
		 * @param fp
		 */
		public AddCustomTaskFeature(IFeatureProvider fp) {
			super(fp);
			addFeatureDelegate = (AbstractBpmn2AddFeature)getFeatureContainer(fp).getAddFeature(fp);
			Assert.isNotNull(addFeatureDelegate);
		}

		@Override
		public PictogramElement add(IAddContext context) {
			PictogramElement pe = addFeatureDelegate.add(context);
			// make sure everyone knows that this PE is a custom task
			if (pe!=null)
				Graphiti.getPeService().setPropertyValue(pe,CUSTOM_TASK_ID,getId());
			
			// add an icon to the top-left corner if applicable, and if the implementing
			// addFeatureDelegate hasn't already done so.
			String icon = customTaskDescriptor.getIcon();
			if (icon!=null && pe instanceof ContainerShape) {
				boolean addImage = true;
				ContainerShape containerShape = (ContainerShape)pe;
				GraphicsAlgorithm ga = (GraphicsAlgorithm)AbstractBpmn2AddFeature.getGraphicsAlgorithm(containerShape);
				for (PictogramElement child : containerShape.getChildren()) {
					if (child.getGraphicsAlgorithm() instanceof Image) {
						addImage = false;
						break;
					}
				}
				if (ga!=null) {
					for (GraphicsAlgorithm g : ga.getGraphicsAlgorithmChildren()) {
						if (g instanceof Image) {
							addImage = false;
							break;
						}
					}
				}
				else
					addImage = false;
				
				if (addImage) {
					Image img = CustomTaskImageProvider.createImage(customTaskDescriptor, ga, icon, 24, 24);
					Graphiti.getGaService().setLocationAndSize(img, 2, 2, 24, 24);
				}
			}
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
	public IMoveShapeFeature getMoveFeature(IFeatureProvider fp) {
		return getFeatureContainer(fp).getMoveFeature(fp);
	}

	@Override
	public IResizeShapeFeature getResizeFeature(IFeatureProvider fp) {
		return getFeatureContainer(fp).getResizeFeature(fp);
	}

	@Override
	public IDeleteFeature getDeleteFeature(IFeatureProvider fp) {
		return getFeatureContainer(fp).getDeleteFeature(fp);
	}
	
	@Override
	public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
		return getFeatureContainer(fp).getCustomFeatures(fp);
	}

}

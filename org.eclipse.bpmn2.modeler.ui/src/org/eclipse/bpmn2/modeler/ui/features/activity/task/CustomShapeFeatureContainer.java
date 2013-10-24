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
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2CreateFeature;
import org.eclipse.bpmn2.modeler.core.features.IFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.IShapeFeatureContainer;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskImageProvider;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskImageProvider.IconSize;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.diagram.BPMNFeatureProvider;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EClass;
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
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;

public class CustomShapeFeatureContainer extends CustomElementFeatureContainer implements IShapeFeatureContainer {
	
	public String getDescription() {
		if (customTaskDescriptor!=null)
			return customTaskDescriptor.getDescription();
		return Messages.CustomShapeFeatureContainer_Description;
	}

	protected IFeatureContainer createFeatureContainer(IFeatureProvider fp) {
		EClass eClass = (EClass) ModelUtil.getEClassifierFromString(
				customTaskDescriptor.getRuntime().getModelDescriptor().getEPackage(), customTaskDescriptor.getType());
		return ((BPMNFeatureProvider)fp).getFeatureContainer(eClass.getInstanceClass());
	}
	
	protected IFeatureContainer getFeatureContainer(IFeatureProvider fp) {
		if (featureContainerDelegate==null) {
			featureContainerDelegate = createFeatureContainer(fp);
		}
		return featureContainerDelegate;
	}
	
	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateCustomShapeFeature(fp);
	}
	
	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new AddCustomShapeFeature(fp);
	}
	
	/**
	 * @author bbrodt
	 *
	 * Base class for Custom Shape Feature construction. Custom Shapes contributed to
	 * the editor MUST subclass this!
	 * 
	 * This class is intended for creation of BPMN2 objects that have custom model
	 * extensions. This is for any object considered to be a "shape", e.g. Activities,
	 * Data Objects, Gateways, Events, etc. 
	 * 
	 * The creation process copies the modelObject ID string into the Graphiti create
	 * context during the construction phase, then migrates that ID into the created
	 * PictogramElement. This is necessary because the ID must be associated with the
	 * PE to allow our BPMNFeatureProvider to correctly identify the Custom Task.
	 */
	public class CreateCustomShapeFeature extends AbstractBpmn2CreateFeature<BaseElement> {

		protected AbstractBpmn2CreateFeature<BaseElement> createFeatureDelegate;
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public CreateCustomShapeFeature(IFeatureProvider fp, String name, String description) {
			super(fp, name, description);
			IShapeFeatureContainer fc = (IShapeFeatureContainer)getFeatureContainer(fp);
			createFeatureDelegate = (AbstractBpmn2CreateFeature)fc.getCreateFeature(fp);
			Assert.isNotNull(createFeatureDelegate);
		}

		public CreateCustomShapeFeature(IFeatureProvider fp) {
			this(fp, customTaskDescriptor.getName(), customTaskDescriptor.getDescription());
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
		public boolean canCreate(ICreateContext context) {
			// copy our ID into the CreateContext - this is where it all starts!
			setId(context, id);
			return createFeatureDelegate.canCreate(context);
		}

		@Override
		public BaseElement createBusinessObject(ICreateContext context) {
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

	public class AddCustomShapeFeature extends AbstractBpmn2AddFeature<BaseElement> {

		protected AbstractBpmn2AddFeature<BaseElement> addFeatureDelegate;
		
		/**
		 * @param fp
		 */
		public AddCustomShapeFeature(IFeatureProvider fp) {
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
		IShapeFeatureContainer fc = (IShapeFeatureContainer)getFeatureContainer(fp);
		return fc.getMoveFeature(fp);
	}

	@Override
	public IResizeShapeFeature getResizeFeature(IFeatureProvider fp) {
		IShapeFeatureContainer fc = (IShapeFeatureContainer)getFeatureContainer(fp);
		return fc.getResizeFeature(fp);
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

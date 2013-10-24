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

package org.eclipse.bpmn2.modeler.core.features;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskImageProvider;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskImageProvider.IconSize;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.runtime.ToolPaletteDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.ToolPaletteDescriptor.ToolDescriptor;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.IExecutionInfo;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;

/**
 * A Create Feature class that can be used to create multiple objects.
 * @author Bob Brodt
 */
public class CompoundCreateFeature<CONTEXT extends IContext>
		extends AbstractCreateFeature
		implements IBpmn2CreateFeature<BaseElement, CONTEXT>,
		ICreateConnectionFeature {
	
	protected List<CompoundCreateFeaturePart<CONTEXT>> children = new ArrayList<CompoundCreateFeaturePart<CONTEXT>>();
	protected ToolDescriptor tool;
	
	public CompoundCreateFeature(IFeatureProvider fp, ToolDescriptor tool) {
		super(fp, tool.getName(), tool.getDescription());
		this.tool = tool;
	}
	
	public CompoundCreateFeature(IFeatureProvider fp) {
		super(fp, null, null);
	}
	
	public CompoundCreateFeaturePart<CONTEXT> addChild(IFeature feature) {
		CompoundCreateFeaturePart<CONTEXT> node = new CompoundCreateFeaturePart<CONTEXT>(feature);
		children.add(node);
		return node;
	}

	@Override
	public boolean canExecute(IContext context) {
		boolean ret = false;
		if (context instanceof ICreateContext)
			ret = canCreate((ICreateContext) context);
		else if (context instanceof ICreateConnectionContext)
			ret = canCreate((ICreateConnectionContext)context);
		return ret;
	}
	
	@Override
	public void execute(IContext context) {
		if (context instanceof ICreateContext)
			create((ICreateContext) context);
		else if (context instanceof ICreateConnectionContext)
			create((ICreateConnectionContext)context);
	}
	
	@Override
	public boolean canCreate(ICreateContext context) {
		for (CompoundCreateFeaturePart<CONTEXT> ft : children) {
			if (ft.canCreate(context)==false)
				return false;
		}
		return true;
	}

	@Override
	public boolean canCreate(ICreateConnectionContext context) {
		for (CompoundCreateFeaturePart<CONTEXT> ft : children) {
			if (ft.canCreate(context)==false)
				return false;
		}
		return true;
	}
	
	@Override
	public Object[] create(ICreateContext context) {
		List<Object> businessObjects = new ArrayList<Object>();
		List<PictogramElement> pictogramElements = new ArrayList<PictogramElement>();
		ContainerShape targetContainer = context.getTargetContainer();
		if (targetContainer==null)
			targetContainer = getDiagram();
		
		PictogramElement[] selection = getDiagramBehavior().getDiagramContainer().getSelectedPictogramElements();
		int index = 0;
		for (CompoundCreateFeaturePart<CONTEXT> ft : children) {
			String optional = ft.getProperty(ToolPaletteDescriptor.TOOLPART_OPTIONAL);
			if ("true".equals(optional)) { //$NON-NLS-1$
				if (index<selection.length) {
					boolean replace = true;
					PictogramElement pe = selection[index++];
					if (pe instanceof Diagram) {
						replace = false;
					}
					else if (ft.feature instanceof ICreateFeature) {
						if (!(pe instanceof ContainerShape))
							replace = false;
					}
					else if (ft.feature instanceof ICreateConnectionFeature) {
						if (!(pe instanceof Connection))
							replace = false;
					}
					
					if (replace) {
						Object bo = BusinessObjectUtil.getFirstBaseElement(pe);
						pictogramElements.add(pe);
						businessObjects.add(bo);
						String id = ft.getProperty(ToolPaletteDescriptor.TOOLPART_ID);
						if (id!=null) {
							Graphiti.getPeService().setPropertyValue(pe, ToolPaletteDescriptor.TOOLPART_ID, id);
						}
						continue;
					}
				}
			}
			ft.create(context, targetContainer, pictogramElements, businessObjects);
		}
		return businessObjects.toArray();
	}

	@Override
	public Connection create(ICreateConnectionContext context) {
		List<Object> businessObjects = new ArrayList<Object>();
		List<PictogramElement> pictogramElements = new ArrayList<PictogramElement>();
		ContainerShape targetContainer = getDiagram();
		for (CompoundCreateFeaturePart<CONTEXT> ft : children) {
			ft.create(context, targetContainer, pictogramElements, businessObjects);
		}
		if (businessObjects.size()>0) {
			Object o = businessObjects.get(0);
			if (o instanceof Connection)
				return (Connection)o;
		}
		return null;
	}
	
	@Override
	public String getCreateImageId() {
		String icon = tool.getIcon();
		if (icon!=null) {
			TargetRuntime rt = tool.getParent().getParent().getRuntime();
			return CustomTaskImageProvider.getImageId(rt, icon, IconSize.SMALL);
		}
		return null;
	}

	@Override
	public String getCreateLargeImageId() {
		String icon = tool.getIcon();
		if (icon!=null) {
			TargetRuntime rt = tool.getParent().getParent().getRuntime();
			return CustomTaskImageProvider.getImageId(rt, icon, IconSize.LARGE);
		}
		return null;
	}

	@Override
	public boolean isAvailable(IContext context) {
		for (CompoundCreateFeaturePart<CONTEXT> ft : children) {
			if (ft.isAvailable(context)==false)
				return false;
		}
		return true;
	}

	@Override
	public BaseElement createBusinessObject(CONTEXT context) {
		for (CompoundCreateFeaturePart<CONTEXT> ft : children) {
			if (ft.getFeature() instanceof IBpmn2CreateFeature) {
				IBpmn2CreateFeature f = (IBpmn2CreateFeature)ft.getFeature();
				BaseElement be = (BaseElement)f.createBusinessObject(context);
				if (be!=null)
					return be;
			}
		}
		return null;
	}

	@Override
	public BaseElement getBusinessObject(CONTEXT context) {
		for (CompoundCreateFeaturePart<CONTEXT> ft : children) {
			if (ft.getFeature() instanceof IBpmn2CreateFeature) {
				IBpmn2CreateFeature f = (IBpmn2CreateFeature)ft.getFeature();
				BaseElement be = (BaseElement)f.getBusinessObject(context);
				if (be!=null)
					return be;
			}
		}
		return null;
	}

	@Override
	public void putBusinessObject(CONTEXT context, BaseElement businessObject) {
		for (CompoundCreateFeaturePart<CONTEXT> ft : children) {
			if (ft.getFeature() instanceof IBpmn2CreateFeature) {
				IBpmn2CreateFeature f = (IBpmn2CreateFeature)ft.getFeature();
				f.putBusinessObject(context, businessObject);
			}
		}
	}

	@Override
	public EClass getBusinessObjectClass() {
		if (children.size()==1) {
			return children.get(0).getBusinessObjectClass();
		}
		return null;
	}

	@Override
	public void postExecute(IExecutionInfo executionInfo) {
		for (CompoundCreateFeaturePart<CONTEXT> ft : children) {
			if (ft.getFeature() instanceof IBpmn2CreateFeature) {
				IBpmn2CreateFeature f = (IBpmn2CreateFeature)ft.getFeature();
				f.postExecute(executionInfo);
			}
		}
	}

	public List<CompoundCreateFeaturePart<CONTEXT>> getChildren() {
		return children;
	}

	@Override
	public boolean canStartConnection(ICreateConnectionContext context) {
		for (CompoundCreateFeaturePart<CONTEXT> ft : children) {
			if (ft.getFeature() instanceof ICreateConnectionFeature) {
				ICreateConnectionFeature f = (ICreateConnectionFeature)ft.getFeature();
				if (!f.canStartConnection(context))
					return false;
			}
		}
		return true;
	}

	public void startConnecting() {
		for (CompoundCreateFeaturePart<CONTEXT> ft : children) {
			if (ft.getFeature() instanceof ICreateConnectionFeature) {
				ICreateConnectionFeature f = (ICreateConnectionFeature)ft.getFeature();
				f.startConnecting();
			}
		}
	}

	public void endConnecting() {
		for (CompoundCreateFeaturePart<CONTEXT> ft : children) {
			if (ft.getFeature() instanceof ICreateConnectionFeature) {
				ICreateConnectionFeature f = (ICreateConnectionFeature)ft.getFeature();
				f.endConnecting();
			}
		}
	}

	public void attachedToSource(ICreateConnectionContext context) {
		for (CompoundCreateFeaturePart<CONTEXT> ft : children) {
			if (ft.getFeature() instanceof ICreateConnectionFeature) {
				ICreateConnectionFeature f = (ICreateConnectionFeature)ft.getFeature();
				f.attachedToSource(context);
			}
		}
	}

	public void canceledAttaching(ICreateConnectionContext context) {
		for (CompoundCreateFeaturePart<CONTEXT> ft : children) {
			if (ft.getFeature() instanceof ICreateConnectionFeature) {
				ICreateConnectionFeature f = (ICreateConnectionFeature)ft.getFeature();
				f.canceledAttaching(context);
			}
		}
	}
}

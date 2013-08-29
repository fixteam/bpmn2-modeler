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

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.modeler.core.adapters.AdapterUtil;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.di.DIImport;
import org.eclipse.bpmn2.modeler.core.features.activity.task.ICustomTaskFeatureContainer;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ObjectEditingDialog;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.ModelEnablementDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.IExecutionInfo;
import org.eclipse.graphiti.features.IFeatureAndContext;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAreaContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.ui.editor.DiagramEditor;

/**
 * @author Bob Brodt
 *
 */
public abstract class AbstractBpmn2CreateFeature<T extends BaseElement>
		extends AbstractCreateFeature
		implements IBpmn2CreateFeature<T, ICreateContext> {

	protected boolean changesDone = true;

	/**
	 * @param fp
	 * @param name
	 * @param description
	 */
	public AbstractBpmn2CreateFeature(IFeatureProvider fp, String name, String description) {
		super(fp, name, description);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.features.impl.AbstractFeature#isAvailable(org.eclipse.graphiti.features.context.IContext)
	 */
	@Override
	public boolean isAvailable(IContext context) {
		return isModelObjectEnabled();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.features.impl.AbstractCreateFeature#getCreateDescription()
	 */
	@Override
	public String getCreateDescription() {
		return "Create " + ModelUtil.toDisplayName( getBusinessObjectClass().getName());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T createBusinessObject(ICreateContext context) {
		Shape shape = context.getTargetContainer();
		EObject container = BusinessObjectUtil.getBusinessObjectForPictogramElement(shape);
		Resource resource = container.eResource();
		EClass eclass = getBusinessObjectClass();
		ExtendedPropertiesAdapter adapter = (ExtendedPropertiesAdapter) AdapterUtil.adapt(eclass, ExtendedPropertiesAdapter.class);
		T businessObject = (T)adapter.getObjectDescriptor().createObject(resource,eclass);
		putBusinessObject(context, businessObject);
		String id = (String)context.getProperty(ICustomTaskFeatureContainer.CUSTOM_TASK_ID);
		if (id!=null) {
	    	TargetRuntime rt = TargetRuntime.getCurrentRuntime();
	    	CustomTaskDescriptor ctd = rt.getCustomTask(id);
	    	ctd.populateObject(businessObject, true);
		}
		changesDone = true;
		return businessObject;
	}
	
	@SuppressWarnings("unchecked")
	public T getBusinessObject(ICreateContext context) {
		return (T) context.getProperty(ContextConstants.BUSINESS_OBJECT);
	}
	
	public void putBusinessObject(ICreateContext context, T businessObject) {
		context.putProperty(ContextConstants.BUSINESS_OBJECT, businessObject);
	}

	public void postExecute(IExecutionInfo executionInfo) {
		for (IFeatureAndContext fc : executionInfo.getExecutionList()) {
			IContext context = fc.getContext();
			if (context instanceof ICreateContext) {
				ICreateContext cc = (ICreateContext)context;
				T businessObject = getBusinessObject(cc);
				Bpmn2Preferences prefs = (Bpmn2Preferences) ((DiagramEditor) getDiagramEditor()).getAdapter(Bpmn2Preferences.class);
				if (prefs!=null && prefs.getShowPopupConfigDialog(businessObject)) {
					ObjectEditingDialog dialog =
							new ObjectEditingDialog((DiagramEditor)getDiagramEditor(), businessObject);
					dialog.open();
				}
			}
		}
	}
	
	@Override
	protected PictogramElement addGraphicalRepresentation(IAreaContext context, Object newObject) {
		AddContext newContext = new AddContext(context, newObject);
		// copy properties into the new context
		Object value = context.getProperty(ICustomTaskFeatureContainer.CUSTOM_TASK_ID);
		newContext.putProperty(ICustomTaskFeatureContainer.CUSTOM_TASK_ID, value);
		value = context.getProperty(DIImport.IMPORT_PROPERTY);
		newContext.putProperty(DIImport.IMPORT_PROPERTY, value);
		value = context.getProperty(ContextConstants.BUSINESS_OBJECT);
		newContext.putProperty(ContextConstants.BUSINESS_OBJECT, value);
		return getFeatureProvider().addIfPossible(newContext);
	}
	
	protected boolean isModelObjectEnabled() {
		ModelEnablementDescriptor me = getModelEnablements();
		if (me!=null)
			return me.isEnabled(getBusinessObjectClass());
		return false;
	}
	
	protected boolean isModelObjectEnabled(EObject o) {
		ModelEnablementDescriptor me = getModelEnablements();
		if (me!=null)
			return me.isEnabled(o.eClass());
		return false;
	}
	
	protected ModelEnablementDescriptor getModelEnablements() {
		DiagramEditor editor = (DiagramEditor) getDiagramEditor();
		return (ModelEnablementDescriptor) editor.getAdapter(ModelEnablementDescriptor.class);
	}

	@Override
	public boolean hasDoneChanges() {
		return changesDone;
	}
}

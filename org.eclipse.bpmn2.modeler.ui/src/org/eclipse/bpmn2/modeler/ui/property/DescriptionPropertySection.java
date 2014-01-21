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

package org.eclipse.bpmn2.modeler.ui.property;

import org.eclipse.bpmn2.Group;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultPropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.FeatureListObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ObjectEditor;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.runtime.ModelExtensionDescriptor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;

/**
 * @author Bob Brodt
 * 
 */
public class DescriptionPropertySection extends DefaultPropertySection implements ITabbedPropertyConstants {

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.ui.property.AbstractBpmn2PropertySection#createSectionRoot()
	 */
	@Override
	protected AbstractDetailComposite createSectionRoot() {
		return new DescriptionDetailComposite(this);		
	}

	@Override
	public AbstractDetailComposite createSectionRoot(Composite parent, int style) {
		 return new DescriptionDetailComposite(parent, style);
	}

	public class DescriptionDetailComposite extends DefaultDetailComposite {

		/**
		 * @param section
		 */
		public DescriptionDetailComposite(AbstractBpmn2PropertySection section) {
			super(section);
		}
		
		public DescriptionDetailComposite(Composite parent, int style) {
			super(parent,style);
		}

		@Override
		protected void cleanBindings() {
			super.cleanBindings();
			descriptionText = null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.bpmn2.modeler.ui.property.AbstractBpmn2DetailComposite
		 * #createBindings(org.eclipse.emf.ecore.EObject)
		 */
		@Override
		public void createBindings(EObject object) {

			bindDescription(object);
			bindAttribute(object,"id"); //$NON-NLS-1$
			bindAttribute(object,"name"); //$NON-NLS-1$
			bindList(object, "documentation"); //$NON-NLS-1$
			if (!(object instanceof Group)) {
				EStructuralFeature reference = object.eClass().getEStructuralFeature("categoryValueRef");
				if (reference!=null) {
					if (isModelObjectEnabled(object.eClass(), reference)) {
						String displayName = getPropertiesProvider().getLabel(object, reference);
		
						ObjectEditor editor = new FeatureListObjectEditor(this,object,reference) {
							@Override
							protected boolean canEdit() {
								return !Bpmn2Preferences.getInstance(object).getPropagateGroupCategories();
							}
						};
						editor.createControl(getAttributesParent(),displayName);
					}
				}
			}
		}

		protected boolean isModelObjectEnabled(String className, String featureName) {
			if (featureName!=null && "name".equals(featureName)) //$NON-NLS-1$
					return true;
			return super.isModelObjectEnabled(className,featureName);
		}
		
		protected void bindDescription(EObject be) {
			// don't display the description text if disabled in preferences,
			// or if this is a popup configuration dialog.
			if (Bpmn2Preferences.getInstance(be).getShowDescriptions()) {
				String description = getDescription(be);
	
				if (description != null) {
					descriptionText = createDescription(this, description);
				}
			}
		}
		
		public String getDescription(EObject object) {
			String description = null;

			ExtendedPropertiesAdapter adapter = ExtendedPropertiesAdapter.adapt(object);
			if (adapter!=null) {
				// if this is a Custom Task, use the description provided by the <customTask> extension
				if (ModelExtensionDescriptor.getModelExtensionAdapter(object) != null)
					description = (String) adapter.getProperty(ExtendedPropertiesAdapter.CUSTOM_DESCRIPTION);
				if (description==null)
					description = (String) adapter.getProperty(ExtendedPropertiesAdapter.LONG_DESCRIPTION);
			}
			return description;
		}
	}
}

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
package org.eclipse.bpmn2.modeler.core.features;

import org.eclipse.bpmn2.modeler.core.IConstants;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ObjectEditingDialog;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.platform.IDiagramContainer;
import org.eclipse.graphiti.ui.editor.DiagramEditor;

public class ShowPropertiesFeature extends AbstractCustomFeature {

	public ShowPropertiesFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Show Properties";
	}

	@Override
	public String getDescription() {
		return "Display a Property configuration popup dialog for the selected item";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		EObject businessObject = BusinessObjectUtil.getBusinessObjectForPictogramElement(pes[0]);
		if (pes.length==1) {
			return Bpmn2Preferences.getInstance().hasPopupConfigDialog(businessObject);
		}
		return false;
	}

	@Override
	public boolean isAvailable(IContext context) {
		return true;
	}

	@Override
	public void execute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		DiagramEditor editor = (DiagramEditor)getDiagramBehavior().getDiagramContainer();
		editor.setPictogramElementForSelection(pes[0]);
		getDiagramBehavior().refresh();
		EObject businessObject = BusinessObjectUtil.getBusinessObjectForPictogramElement(pes[0]);
		ObjectEditingDialog dialog =
				new ObjectEditingDialog(editor, businessObject);
		dialog.open();
	}

	@Override
	public String getImageId() {
		return IConstants.ICON_PROPERTIES_16;
	}

}

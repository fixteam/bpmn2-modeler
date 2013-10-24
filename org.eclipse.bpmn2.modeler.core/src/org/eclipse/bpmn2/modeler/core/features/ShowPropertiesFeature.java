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
import org.eclipse.jface.window.Window;

public class ShowPropertiesFeature extends AbstractCustomFeature {

	protected boolean changesDone = false;
	
	public ShowPropertiesFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return Messages.ShowPropertiesFeature_Name;
	}

	@Override
	public String getDescription() {
		return Messages.ShowPropertiesFeature_Title;
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
		if (dialog.open() == Window.OK)
			changesDone = dialog.hasDoneChanges();
		else
			changesDone = false;
	}

	@Override
	public boolean hasDoneChanges() {
		return changesDone;
	}

	@Override
	public String getImageId() {
		return IConstants.ICON_PROPERTIES_16;
	}

}

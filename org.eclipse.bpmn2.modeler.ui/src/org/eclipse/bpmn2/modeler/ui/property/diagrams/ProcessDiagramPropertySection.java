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
package org.eclipse.bpmn2.modeler.ui.property.diagrams;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultPropertySection;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;

public class ProcessDiagramPropertySection extends DefaultPropertySection {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.bpmn2.modeler.ui.property.AbstractBpmn2PropertySection#
	 * createSectionRoot()
	 */
	@Override
	protected AbstractDetailComposite createSectionRoot() {
		return new ProcessDiagramDetailComposite(this);
	}

	@Override
	public AbstractDetailComposite createSectionRoot(Composite parent, int style) {
		return new ProcessDiagramDetailComposite(parent,style);
	}

	@Override
	protected EObject getBusinessObjectForSelection(ISelection selection) {
		EObject bo = super.getBusinessObjectForSelection(selection);
		if (bo instanceof BPMNDiagram) {
			BaseElement be = ((BPMNDiagram)bo).getPlane().getBpmnElement();
			if (be instanceof Process)
				return be;
		}
		else if (bo instanceof Process) {
			return bo;
		}
		
		return null;
	}
	
	@Override
	public boolean appliesTo(IWorkbenchPart part, ISelection selection) {
		if (super.appliesTo(part, selection))
			return true;
		EObject bo = getBusinessObjectForSelection(selection);
		return bo!=null;
	}
}

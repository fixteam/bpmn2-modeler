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
 * @author Innar Made
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.ui.property.tasks;

import org.eclipse.bpmn2.SubProcess;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultPropertySection;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;

public class IoParametersPropertySection extends DefaultPropertySection {

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.ui.property.AbstractBpmn2PropertySection#createSectionRoot()
	 */
	@Override
	protected AbstractDetailComposite createSectionRoot() {
		return new IoParametersDetailComposite(this);
	}

	@Override
	public AbstractDetailComposite createSectionRoot(Composite parent, int style) {
		return new IoParametersDetailComposite(parent,style);
	}
	
	@Override
	public boolean appliesTo(IWorkbenchPart part, ISelection selection) {
		if (super.appliesTo(part, selection)) {
			EObject object = getBusinessObjectForSelection(selection);
//			if (object instanceof ServiceTask) {
//				if (((ServiceTask)object).getOperationRef()!=null)
//					return false;
//			}
			return object!=null;
		}
		return false;
	}

	@Override
	protected EObject getBusinessObjectForSelection(ISelection selection) {
		EObject be = super.getBusinessObjectForSelection(selection);
		if (be instanceof SubProcess) {
			// Section 10, P211 of the BPMN 2.0 spec:
			// "Embedded SubProcesses MUST NOT define Data Inputs and Data Outputs directly,
			// however they MAY define them indirectly via MultiInstanceLoopCharacteristics."
			return null;
		}
		if (be!=null) {
			EStructuralFeature feature = be.eClass().getEStructuralFeature("ioSpecification"); //$NON-NLS-1$
			if (feature!=null && isModelObjectEnabled(be, feature))
				return be;
		}
		return null;
	}
}

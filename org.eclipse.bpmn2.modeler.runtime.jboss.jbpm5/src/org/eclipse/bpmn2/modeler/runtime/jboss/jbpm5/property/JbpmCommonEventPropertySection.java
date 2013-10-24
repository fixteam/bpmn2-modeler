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

package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property;

import org.eclipse.bpmn2.Event;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.events.CommonEventPropertySection;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Bob Brodt
 *
 */
public class JbpmCommonEventPropertySection extends CommonEventPropertySection {

	@Override
	protected AbstractDetailComposite createSectionRoot() {
		return new JbpmCommonEventDetailComposite(this);
	}

	@Override
	public AbstractDetailComposite createSectionRoot(Composite parent, int style) {
		return new JbpmCommonEventDetailComposite(parent,style);
	}
	
	
	@Override
	public boolean appliesTo(IWorkbenchPart part, ISelection selection) {
		if (super.appliesTo(part, selection)) {
			EObject be = getBusinessObjectForSelection(selection);
			if (be instanceof Event) {
				return isModelObjectEnabled(be, "isInterrupting") || //$NON-NLS-1$
						isModelObjectEnabled(be, "parallelMultiple") || //$NON-NLS-1$
						isModelObjectEnabled(be, "cancelActivity") || //$NON-NLS-1$
						isModelObjectEnabled(be, "eventDefinitions") || //$NON-NLS-1$
						isModelObjectEnabled(be, "dataInputs") || //$NON-NLS-1$
						isModelObjectEnabled(be, "dataOutputs") || //$NON-NLS-1$
						isModelObjectEnabled(be, "properties"); //$NON-NLS-1$
			}
		}
		return false;
	}
}

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
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultPropertySection;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsPackage;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.preferences.JbpmPreferencePage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Bob Brodt
 *
 */
public class SimulationPropertySection extends DefaultPropertySection {

	@Override
	public boolean appliesTo(IWorkbenchPart part, ISelection selection) {
		super.appliesTo(part,selection); // this sets the DiagramEditor as a side-effect
		
		// Only enable Simulation tab if enabled in User Preferences.
		if (!JbpmPreferencePage.isEnableSimulation())
			return false;
		
		// and all this other stuff...
		EObject object = BusinessObjectUtil.getBusinessObjectForSelection(selection);
		return object instanceof BPMNDiagram ||
				object instanceof Task ||
				object instanceof Event ||
				object instanceof SequenceFlow;
	}

	@Override
	protected AbstractDetailComposite createSectionRoot() {
		return new SimulationDetailComposite(this);
	}

	@Override
	public AbstractDetailComposite createSectionRoot(Composite parent, int style) {
		return new SimulationDetailComposite(parent,style);
	}

}

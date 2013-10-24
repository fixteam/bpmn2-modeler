/*******************************************************************************
 * Copyright (c) 2011, 2012, 2013 Red Hat, Inc.
 * All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 	Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property;

import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.InclusiveGateway;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractListComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.ListCompositeColumnProvider;
import org.eclipse.bpmn2.modeler.ui.property.gateways.GatewayDetailComposite;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.widgets.Composite;

public class JbpmGatewayDetailComposite extends GatewayDetailComposite {

	public JbpmGatewayDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	public JbpmGatewayDetailComposite(Composite parent, int style) {
		super(parent, style);
	}
	
	protected SequenceFlowsListComposite createSequenceFlowsListComposite(Composite parent) {
		
		return new SequenceFlowsListComposite(parent) {
			
			@Override
			public ListCompositeColumnProvider getColumnProvider(EObject object, EStructuralFeature feature) {
				if (columnProvider==null) {
					columnProvider = new GatewaySequenceFlowListColumnProvider(this, object);
				}
				return columnProvider;
			}

		};
	}

	private class GatewaySequenceFlowListColumnProvider extends  SequenceFlowListColumnProvider {

		public GatewaySequenceFlowListColumnProvider(AbstractListComposite list, EObject object) {
			super(list, false);
			// add 2 or 3 columns, depending on gateway type
			add(new SequenceFlowListColumn(object,1)); // identifier (from -> to)
			if (object instanceof ExclusiveGateway || object instanceof InclusiveGateway)
				add(new SequenceFlowListColumn(object,2)); // Condition (expression)
			if (object.eClass().getEStructuralFeature("default")!=null) { //$NON-NLS-1$
				add(new SequenceFlowListColumn(object,3)); // Is Default (boolean)
			}
		}
	}
}

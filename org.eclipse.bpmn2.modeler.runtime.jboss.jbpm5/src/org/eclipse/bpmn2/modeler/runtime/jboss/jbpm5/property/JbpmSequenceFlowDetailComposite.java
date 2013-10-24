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

import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.InclusiveGateway;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.ui.property.connectors.SequenceFlowDetailComposite;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Bob Brodt
 *
 */
public class JbpmSequenceFlowDetailComposite extends SequenceFlowDetailComposite {

	/**
	 * @param section
	 */
	public JbpmSequenceFlowDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	public JbpmSequenceFlowDetailComposite(Composite parent, int style) {
		super(parent, style);
	}
	
	@Override
	public void createBindings(EObject be) {
		bindAttribute(getAttributesParent(), be, "priority"); //$NON-NLS-1$
		super.createBindings(be);
	}
	
	@Override
	protected boolean isModelObjectEnabled(String className, String featureName) {
		if (super.isModelObjectEnabled(className, featureName)) {
			if ("conditionExpression".equals(featureName)) { //$NON-NLS-1$
				// Condition Expressions can only appear on SequenceFlows leaving
				// an Exclusive or Inclusive Gateway.
				FlowNode source = ((SequenceFlow)getBusinessObject()).getSourceRef();
				if (!(source instanceof ExclusiveGateway || source instanceof InclusiveGateway))
					return false;
			}
			return true;
		}
		return false;
	}
}

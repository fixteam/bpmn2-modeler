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

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.ui.property.connectors.SequenceFlowPropertySection;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Bob Brodt
 *
 */
public class JbpmSequenceFlowPropertySection extends SequenceFlowPropertySection {

	@Override
	public boolean appliesTo(IWorkbenchPart part, ISelection selection) {
		if (super.appliesTo(part, selection)) {
			EObject be = BusinessObjectUtil.getBusinessObjectForSelection(selection);
			if (be instanceof SequenceFlow) {
				// only show this tab if the sequence flow is attached to a Gateway
				if (((SequenceFlow) be).getSourceRef() instanceof Gateway) {
					Gateway gateway = (Gateway) ((SequenceFlow) be).getSourceRef();
					// hide this tab if the "condition expression" on the Sequence Flow
					// or the (possibly) attached Gateway's "default flow" feature is disabled
					boolean conditionEnabled = isModelObjectEnabled(
							Bpmn2Package.eINSTANCE.getSequenceFlow(),
							Bpmn2Package.eINSTANCE.getSequenceFlow_ConditionExpression());

					boolean defaultEnabled = true;
					EStructuralFeature defaultFeature = gateway.eClass().getEStructuralFeature("default"); //$NON-NLS-1$
					if (defaultFeature!=null) {
						if (!isModelObjectEnabled(gateway.eClass(), defaultFeature))
							defaultEnabled = false;
					}
					return conditionEnabled || defaultEnabled;
				}
			}
		}
		return false;
	}

	@Override
	protected AbstractDetailComposite createSectionRoot() {
		return new JbpmSequenceFlowDetailComposite(this);
	}

	@Override
	public AbstractDetailComposite createSectionRoot(Composite parent, int style) {
		return new JbpmSequenceFlowDetailComposite(parent,style);
	}
}

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
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.validation;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.DataObject;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.Message;
import org.eclipse.bpmn2.Property;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.GlobalType;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

public class ProcessVariableNameConstraint extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		EObject o1 = ctx.getTarget();
		String id1 = null;
		if (o1 instanceof GlobalType)
			id1 = ((GlobalType)o1).getIdentifier();
		else if (o1 instanceof Property)
			id1 = ((Property)o1).getId();
		else if (o1 instanceof Message)
			id1 = ((Message)o1).getId();
		else if (o1 instanceof DataObject)
			id1 = ((DataObject)o1).getId();

		Definitions definitions = ModelUtil.getDefinitions(o1);
		TreeIterator<EObject> iter = definitions.eAllContents();
		while (iter.hasNext()) {
			EObject o2 = iter.next();
			if (o2 instanceof BaseElement && o1!=o2) {
				String id2;
				if (o2 instanceof GlobalType)
					id2 = ((GlobalType)o2).getIdentifier();
				else
					id2 = ((BaseElement)o2).getId();
				if (id1!=null && id2!=null) {
					if (id1.equals(id2)) {
						String msg = NLS.bind(
								Messages.ProcessVariableNameConstraint_Duplicate_ID,
								ModelUtil.getLabel(o1)+" "+ModelUtil.getDisplayName(o1), //$NON-NLS-1$
								ModelUtil.getLabel(o2)+" "+ModelUtil.getDisplayName(o2)); //$NON-NLS-1$
						return ctx.createFailureStatus(msg);
					}
				}
			}
		}
		return ctx.createSuccessStatus();
	}

}

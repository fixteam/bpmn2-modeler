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

import java.util.Iterator;

import org.eclipse.bpmn2.BusinessRuleTask;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

public class BusinessRuleTaskConstraint extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		EObject eObj = ctx.getTarget();
		if (eObj instanceof BusinessRuleTask) {
			BusinessRuleTask bt = (BusinessRuleTask)eObj;
			
			Iterator<FeatureMap.Entry> biter = bt.getAnyAttribute().iterator();
			boolean foundRuleflowGroup = false;
			while (biter.hasNext()) {
				FeatureMap.Entry entry = biter.next();
				if (entry.getEStructuralFeature().getName().equals("ruleFlowGroup")) { //$NON-NLS-1$
					foundRuleflowGroup = true;
					String ruleflowGroup = (String) entry.getValue();
					if (ruleflowGroup==null || ruleflowGroup.isEmpty()) {
						return ctx.createFailureStatus(Messages.BusinessRuleTaskConstraint_No_Ruleflow_Group);
					}
				}
			}
			if (!foundRuleflowGroup) {
				return ctx.createFailureStatus(Messages.BusinessRuleTaskConstraint_No_Ruleflow_Group);
			}
		}	
		return ctx.createSuccessStatus();
	}

}

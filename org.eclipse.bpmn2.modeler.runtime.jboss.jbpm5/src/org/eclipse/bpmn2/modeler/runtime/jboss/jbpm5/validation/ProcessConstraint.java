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

import org.eclipse.bpmn2.Process;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

public class ProcessConstraint extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		EObject eObj = ctx.getTarget();
		if (eObj instanceof Process) {
			Process process = (Process)eObj;
			
			String name = null;
			Iterator<FeatureMap.Entry> iter = process.getAnyAttribute().iterator();
			while (iter.hasNext()) {
				FeatureMap.Entry entry = iter.next();
				if (entry.getEStructuralFeature().getName().equals("packageName")) { //$NON-NLS-1$
					name = (String) entry.getValue();
					if (name==null || name.isEmpty()) {
						ctx.addResult(entry.getEStructuralFeature());
						return ctx.createFailureStatus(Messages.ProcessConstraint_No_Package_Name);
					}
				}
			}
	
			name = process.getName();
			if (name==null || name.isEmpty()) {
				ctx.addResult(process.eClass().getEStructuralFeature("name")); //$NON-NLS-1$
				return ctx.createFailureStatus(Messages.ProcessConstraint_No_Process_Name);
			}
		}	
		return ctx.createSuccessStatus();
	}
}

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

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.bpmn2.CallActivity;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

public class CallActivityConstraint extends AbstractModelConstraint {
	private IDiagramProfile profile;
	private String uuid = "uuid"; //$NON-NLS-1$

	@Override
	public IStatus validate(IValidationContext ctx) {
		EObject eObj = ctx.getTarget();
		if (eObj instanceof CallActivity) {
			CallActivity ca = (CallActivity) eObj;
			if (ca.getCalledElementRef() == null) {
				ctx.createFailureStatus(Messages.CallActivityConstraint_No_Called_Element);
			} else {
				String[] packageAssetInfo = ServletUtil.findPackageAndAssetInfo(uuid, profile);
				String packageName = packageAssetInfo[0];
				List<String> allProcessesInPackage = ServletUtil.getAllProcessesInPackage(packageName, profile);
				boolean foundCalledElementProcess = false;
				for (String p : allProcessesInPackage) {
					String processContent = ServletUtil.getProcessSourceContent(packageName, p, profile);
					Pattern pattern = Pattern.compile("<\\S*process[\\s\\S]*id=\"" + ca.getCalledElementRef() + "\"", //$NON-NLS-1$ //$NON-NLS-2$
							Pattern.MULTILINE);
					Matcher m = pattern.matcher(processContent);
					if (m.find()) {
						foundCalledElementProcess = true;
						break;
					}
				}
				foundCalledElementProcess = true; // TODO: remove this
				if (!foundCalledElementProcess) {
					ctx.createFailureStatus(
						NLS.bind(
							Messages.CallActivityConstraint_No_Process,
							ca.getCalledElementRef()
						)
					);
				}
			}
		}
		return ctx.createSuccessStatus();
	}

}

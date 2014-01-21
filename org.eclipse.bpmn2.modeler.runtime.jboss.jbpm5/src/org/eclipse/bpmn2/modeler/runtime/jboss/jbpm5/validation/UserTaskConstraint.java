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
import java.util.List;

import org.eclipse.bpmn2.Assignment;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.DataInputAssociation;
import org.eclipse.bpmn2.ExtensionAttributeValue;
import org.eclipse.bpmn2.FormalExpression;
import org.eclipse.bpmn2.ItemAwareElement;
import org.eclipse.bpmn2.UserTask;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

public class UserTaskConstraint extends AbstractModelConstraint {
	private IDiagramProfile profile;
	private String uuid = "uuid"; //$NON-NLS-1$

	@Override
	public IStatus validate(IValidationContext ctx) {
		EObject eObj = ctx.getTarget();
		if (eObj instanceof UserTask) {
			UserTask ut = (UserTask)eObj;
			
			try {
				String taskName = null;
				if (ut.getIoSpecification()!=null) {
					Iterator<FeatureMap.Entry> utiter = ut.getAnyAttribute().iterator();
					for (DataInput di : ut.getIoSpecification().getDataInputs()) {
						if ("TaskName".equalsIgnoreCase(di.getName())) { //$NON-NLS-1$
							for (DataInputAssociation dia : ut.getDataInputAssociations()) {
								if (dia.getTargetRef() == di) {
									if (dia.getAssignment().size()!=0) {
										Assignment a = dia.getAssignment().get(0);
										if (a.getFrom() instanceof FormalExpression) {
											String body = ((FormalExpression)a.getFrom()).getBody();
											if (body!=null && !body.isEmpty()) {
												taskName = body;
												break;
											}
										}
									}
								}
							}
						}
					}
				}
				if (taskName==null) {
					return ctx.createFailureStatus(Messages.UserTaskConstraint_No_Name);
				}
				else {
					// TODO:
					if (taskName != null) {
						String[] packageAssetInfo = ServletUtil.findPackageAndAssetInfo(uuid, profile);
						String taskFormName = taskName + "-taskform"; //$NON-NLS-1$
						if (!ServletUtil.assetExistsInGuvnor(packageAssetInfo[0], taskFormName, profile)) {
							ctx.createFailureStatus(Messages.UserTaskConstraint_No_Form);
						}
					}
				}
	
				// simulation validation
				// TODO: fix this
				/*
				if (ut.getExtensionValues() != null && ut.getExtensionValues().size() > 0) {
					boolean foundStaffAvailability = false;
					for (ExtensionAttributeValue extattrval : ut.getExtensionValues()) {
						FeatureMap extensionElements = extattrval.getValue();
						if (extensionElements!=null) {
							@SuppressWarnings("unchecked")
							List<MetadataType> metadataTypeExtensions = (List<MetadataType>) extensionElements.get(
									DroolsPackage.Literals.DOCUMENT_ROOT__METADATA, true);
							if (metadataTypeExtensions != null && metadataTypeExtensions.size() > 0) {
								MetadataType metaType = metadataTypeExtensions.get(0);
								for (Object metaEntryObj : metaType.getMetaentry()) {
									MetaentryType entry = (MetaentryType) metaEntryObj;
									if (entry.getName() != null && entry.getName().equals("staffavailability")) {
										Float f = new Float(entry.getValue());
										if (f.floatValue() < 0) {
											ctx.createFailureStatus("User Task Simulation Parameter \"Staff Availability\" must be positive");
										}
										foundStaffAvailability = true;
									}
								}
							}
							if (!foundStaffAvailability) {
								return ctx.createFailureStatus("User Task Simulation Parameter \"Staff Availability\" is not defined");
							}
						}
					}
				}
				*/
			}
			catch (Exception e) {
				e.printStackTrace();
				return ctx.createFailureStatus(Messages.UserTaskConstraint_Internal_Error+e.getMessage());
			}	
		}
		return ctx.createSuccessStatus();
	}

}

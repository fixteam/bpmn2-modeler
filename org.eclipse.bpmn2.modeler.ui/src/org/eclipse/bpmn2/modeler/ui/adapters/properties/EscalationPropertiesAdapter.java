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

package org.eclipse.bpmn2.modeler.ui.adapters.properties;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Escalation;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author Bob Brodt
 *
 */
public class EscalationPropertiesAdapter extends RootElementPropertiesAdapter<Escalation> {

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public EscalationPropertiesAdapter(AdapterFactory adapterFactory, Escalation object) {
		super(adapterFactory, object);

		EStructuralFeature feature = Bpmn2Package.eINSTANCE.getEscalation_StructureRef();
		setProperty(feature, UI_IS_MULTI_CHOICE, Boolean.TRUE);
    	setFeatureDescriptor(feature, new ItemDefinitionRefFeatureDescriptor<Escalation>(adapterFactory, object, feature));
		
    	setObjectDescriptor(new RootElementObjectDescriptor<Escalation>(adapterFactory, object) {
			@Override
			public String getDisplayName(Object context) {
				final Escalation escalation = adopt(context);
				String text = ""; //$NON-NLS-1$
				if (escalation.getName()!=null) {
					text += escalation.getName();
				}
				else if (escalation.getEscalationCode()!=null) {
					text += Messages.EscalationPropertiesAdapter_Escalation_Code + escalation.getEscalationCode();
				}
				if (text.isEmpty())
					text = Messages.EscalationPropertiesAdapter_ID + escalation.getId();
				return text;
			}
    	});
	}

}

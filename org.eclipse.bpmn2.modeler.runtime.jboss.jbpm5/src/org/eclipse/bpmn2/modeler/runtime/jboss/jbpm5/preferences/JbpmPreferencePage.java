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
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.preferences;

import org.eclipse.bpmn2.modeler.core.Activator;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class JbpmPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	public final static String PREF_ENABLE_SIMULATION_PARAMS = "enable.simulation.params"; //$NON-NLS-1$
	public final static String PREF_ENABLE_SIMULATION_PARAMS_LABEL = Messages.JbpmPreferencePage_Enable_Simulation;

	public JbpmPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.JbpmPreferencePage_JBPM_Settings);
	}

	@Override
	public void init(IWorkbench workbench) {
	}

	@Override
	protected void createFieldEditors() {

		BooleanFieldEditor showAdvancedPropsTab = new BooleanFieldEditor(
				PREF_ENABLE_SIMULATION_PARAMS,
				PREF_ENABLE_SIMULATION_PARAMS_LABEL,
				getFieldEditorParent());
		addField(showAdvancedPropsTab);
	}

	public static boolean isEnableSimulation() {
		return Activator.getDefault().getPreferenceStore().getBoolean(PREF_ENABLE_SIMULATION_PARAMS);
	}
}

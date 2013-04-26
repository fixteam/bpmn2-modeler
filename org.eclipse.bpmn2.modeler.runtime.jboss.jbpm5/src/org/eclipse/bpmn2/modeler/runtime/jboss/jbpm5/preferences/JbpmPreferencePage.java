package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.preferences;

import org.eclipse.bpmn2.modeler.core.Activator;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class JbpmPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	public final static String PREF_ENABLE_SIMULATION_PARAMS = "enable.simulation.params";
	public final static String PREF_ENABLE_SIMULATION_PARAMS_LABEL = "Enable Simulation Parameters";

	public JbpmPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("jBPM Runtime-specific Settings");
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

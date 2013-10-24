package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.preferences;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.preferences.messages"; //$NON-NLS-1$
	public static String JbpmPreferencePage_Enable_Simulation;
	public static String JbpmPreferencePage_JBPM_Settings;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

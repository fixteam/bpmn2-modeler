package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.util;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.util.messages"; //$NON-NLS-1$
	public static String JbpmImportDialog_Create_Process_Variables_Label;
	public static String JbpmModelUtil_Duplicate_Import_Message;
	public static String JbpmModelUtil_Duplicate_Import_Title;
	public static String JbpmModelUtil_No_Process_Message;
	public static String JbpmModelUtil_No_Process_Title;
	public static String JbpmModelUtil_Scenario_Name;
	public static String JbpmModelUtil_Simulation;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

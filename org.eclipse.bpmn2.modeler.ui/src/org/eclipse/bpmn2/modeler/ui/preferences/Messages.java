package org.eclipse.bpmn2.modeler.ui.preferences;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.ui.preferences.messages"; //$NON-NLS-1$
	public static String ToolProfilePreferencePage_Default_Profile_Label;
	public static String ToolProfilePreferencePage_Export;
	public static String ToolProfilePreferencePage_Extension_Elements_Label;
	public static String ToolProfilePreferencePage_Import;
	public static String ToolProfilePreferencePage_Standard_Elements_Label;
	public static String ToolProfilePreferencePage_Unnamed;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

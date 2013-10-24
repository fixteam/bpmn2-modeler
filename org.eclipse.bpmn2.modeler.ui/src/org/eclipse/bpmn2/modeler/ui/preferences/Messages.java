package org.eclipse.bpmn2.modeler.ui.preferences;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.ui.preferences.messages"; //$NON-NLS-1$
	public static String ToolEnablementPropertyPage_Default_Profile_Label;
	public static String ToolEnablementPropertyPage_Export;
	public static String ToolEnablementPropertyPage_Extension_Elements_Label;
	public static String ToolEnablementPropertyPage_Import;
	public static String ToolEnablementPropertyPage_Initialize_Button;
	public static String ToolEnablementPropertyPage_Initialize_Label;
	public static String ToolEnablementPropertyPage_Overrid_Profile_Button;
	public static String ToolEnablementPropertyPage_Standard_Elements_Label;
	public static String ToolEnablementPropertyPage_Title;
	public static String ToolEnablementPropertyPage_Unnamed;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

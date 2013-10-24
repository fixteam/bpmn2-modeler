package org.eclipse.bpmn2.modeler.ui.property.diagrams;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.ui.property.diagrams.messages"; //$NON-NLS-1$
	public static String DefinitionsPropertyComposite_Change_Namespace_Title;
	public static String DefinitionsPropertyComposite_Create_Namespace_Title;
	public static String DefinitionsPropertyComposite_Invalid_Duplicate;
	public static String DefinitionsPropertyComposite_Namespace_Details_Title;
	public static String DefinitionsPropertyComposite_Namespace_Label;
	public static String DefinitionsPropertyComposite_Prefix_Label;
	public static String DefinitionsPropertyComposite_Prefix_Message;
	public static String ResourceRoleListComposite_Roles_Label;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

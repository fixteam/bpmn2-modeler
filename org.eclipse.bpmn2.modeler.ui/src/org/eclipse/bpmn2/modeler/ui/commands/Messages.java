package org.eclipse.bpmn2.modeler.ui.commands;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.ui.commands.messages"; //$NON-NLS-1$
	public static String CreateDiagramCommand_Title;
	public static String CreateDiagramCommand_Message;
	public static String CreateDiagramCommand_Choreography;
	public static String CreateDiagramCommand_Collaboration;
	public static String CreateDiagramCommand_Invalid_Duplicate;
	public static String CreateDiagramCommand_Invalid_Empty;
	public static String CreateDiagramCommand_Process;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

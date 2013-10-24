package org.eclipse.bpmn2.modeler.core;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.core.messages"; //$NON-NLS-1$
	public static String ModelHandler_20;
	public static String ModelHandler_21;
	public static String ModelHandler_Choreography;
	public static String ModelHandler_Choreography_Task;
	public static String ModelHandler_Collaboration;
	public static String ModelHandler_Collaboration_Diagram;
	public static String ModelHandler_Default;
	public static String ModelHandler_Initiating_Participant;
	public static String ModelHandler_Initiating_Pool;
	public static String ModelHandler_Initiating_Process;
	public static String ModelHandler_Lane_Set;
	public static String ModelHandler_Non_Initiating_Participant;
	public static String ModelHandler_Non_Initiating_Pool;
	public static String ModelHandler_Non_Initiating_Process;
	public static String ModelHandler_Process;
	public static String ModelHandler_Process_Diagram;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

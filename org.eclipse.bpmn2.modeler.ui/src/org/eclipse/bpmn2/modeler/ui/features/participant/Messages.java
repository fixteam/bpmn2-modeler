package org.eclipse.bpmn2.modeler.ui.features.participant;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.ui.features.participant.messages"; //$NON-NLS-1$
	public static String CreateParticipantFeature_Default_Pool_Name;
	public static String CreateParticipantFeature_Default_Process_Name;
	public static String CreateParticipantFeature_Description;
	public static String CreateParticipantFeature_Name;
	public static String RotatePoolFeature_Description;
	public static String RotatePoolFeature_Name;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

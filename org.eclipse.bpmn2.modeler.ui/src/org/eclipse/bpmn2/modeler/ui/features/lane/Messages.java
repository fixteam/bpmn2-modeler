package org.eclipse.bpmn2.modeler.ui.features.lane;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.ui.features.lane.messages"; //$NON-NLS-1$
	public static String CreateLaneFeature_Default_Name;
	public static String CreateLaneFeature_Description;
	public static String CreateLaneFeature_Name;
	public static String RotateLaneFeature_Description;
	public static String RotateLaneFeature_Name;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

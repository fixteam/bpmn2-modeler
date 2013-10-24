package org.eclipse.bpmn2.modeler.core.features.lane;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.core.features.lane.messages"; //$NON-NLS-1$
	public static String DirectEditLaneFeature_Invalid_Empty;
	public static String DirectEditLaneFeature_Invalid_Linebreak;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

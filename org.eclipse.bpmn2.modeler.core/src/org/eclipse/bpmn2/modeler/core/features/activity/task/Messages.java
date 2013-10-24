package org.eclipse.bpmn2.modeler.core.features.activity.task;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.core.features.activity.task.messages"; //$NON-NLS-1$
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

	public static String DirectEditTaskFeature_Invalid_Empty;
	public static String DirectEditTaskFeature_Invalid_Linebreak;
}

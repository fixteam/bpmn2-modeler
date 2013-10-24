package org.eclipse.bpmn2.modeler.ui.features.conversation;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.ui.features.conversation.messages"; //$NON-NLS-1$
	public static String ConversationLinkFeatureContainer_Description;
	public static String ConversationLinkFeatureContainer_Name;
	public static String CreateConversationFeature_Description;
	public static String CreateConversationFeature_Name;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

package org.eclipse.bpmn2.modeler.ui.views.outline;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.ui.views.outline.messages"; //$NON-NLS-1$
	public static String BPMN2EditorOutlinePage_Business_Model_Title;
	public static String BPMN2EditorOutlinePage_DI_Model_Title;
	public static String BPMN2EditorOutlinePage_Thumbnail_Title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

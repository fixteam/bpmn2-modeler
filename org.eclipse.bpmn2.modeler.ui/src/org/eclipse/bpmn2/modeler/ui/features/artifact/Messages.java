package org.eclipse.bpmn2.modeler.ui.features.artifact;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.ui.features.artifact.messages"; //$NON-NLS-1$
	public static String CreateTextAnnotationFeature_Description;
	public static String CreateTextAnnotationFeature_Name;
	public static String GroupFeatureContainer_Description;
	public static String GroupFeatureContainer_Name;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

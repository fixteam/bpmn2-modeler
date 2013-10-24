package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.features;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.features.messages"; //$NON-NLS-1$
	public static String JbpmCustomTaskFeatureContainer_Description;
	public static String JbpmCustomTaskFeatureContainer_Name;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

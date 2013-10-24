package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.messages"; //$NON-NLS-1$
	public static String Activator_Error;
	public static String JBPM5RuntimeExtension_Duplicate_Task_Message;
	public static String JBPM5RuntimeExtension_Duplicate_Task_Title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

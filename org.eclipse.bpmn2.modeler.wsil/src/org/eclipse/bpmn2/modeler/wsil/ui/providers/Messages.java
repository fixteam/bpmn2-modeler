package org.eclipse.bpmn2.modeler.wsil.ui.providers;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.wsil.ui.providers.messages"; //$NON-NLS-1$
	public static String WSILContentProvider_Not_A_WSIL;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

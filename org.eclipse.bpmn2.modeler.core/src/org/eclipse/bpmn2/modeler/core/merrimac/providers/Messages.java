package org.eclipse.bpmn2.modeler.core.merrimac.providers;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.core.merrimac.providers.messages"; //$NON-NLS-1$
	public static String TableCursor_Edit;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

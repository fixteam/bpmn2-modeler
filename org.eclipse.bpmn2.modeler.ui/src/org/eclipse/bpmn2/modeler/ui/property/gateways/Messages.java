package org.eclipse.bpmn2.modeler.ui.property.gateways;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.ui.property.gateways.messages"; //$NON-NLS-1$
	public static String GatewayDetailComposite_Condition_Header;
	public static String GatewayDetailComposite_Is_Default_Header;
	public static String GatewayDetailComposite_Sequence_Flow_Header;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

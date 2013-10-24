package org.eclipse.bpmn2.modeler.ui.property.providers;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.ui.property.providers.messages"; //$NON-NLS-1$
	public static String BPMN2ErrorTreeNode_Unnamed;
	public static String BPMN2InterfaceTreeNode_Unnamed;
	public static String BPMN2MessageTreeNode_Unnamed;
	public static String BPMN2OperationTreeNode_Unnamed;
	public static String BPMN2ProcessTreeNode_Unnamed;
	public static String ModelLabelProvider_Unknown;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

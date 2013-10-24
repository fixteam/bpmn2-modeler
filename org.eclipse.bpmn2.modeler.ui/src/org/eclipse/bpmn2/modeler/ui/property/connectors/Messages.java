package org.eclipse.bpmn2.modeler.ui.property.connectors;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.ui.property.connectors.messages"; //$NON-NLS-1$
	public static String SequenceFlowDetailComposite_Add_Button;
	public static String SequenceFlowDetailComposite_Condition_Expression_Title;
	public static String SequenceFlowDetailComposite_Default_Flow_Label;
	public static String SequenceFlowDetailComposite_Remove_Button;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

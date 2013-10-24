package org.eclipse.bpmn2.modeler.ui.property;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.ui.property.messages"; //$NON-NLS-1$
	public static String AdvancedDetailComposite_Add_Action;
	public static String AdvancedDetailComposite_Advanced_Button;
	public static String AdvancedDetailComposite_Details_Title;
	public static String AdvancedDetailComposite_Properties_Title;
	public static String AdvancedDetailComposite_Remove_Action;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

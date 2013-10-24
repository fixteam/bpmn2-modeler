package org.eclipse.bpmn2.modeler.core.model;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.core.model.messages"; //$NON-NLS-1$
	public static String Bpmn2ModelerResourceImpl_Illegal_QName;
	public static String Bpmn2ModelerResourceImpl_Invalid_Reference;
	public static String Bpmn2ModelerResourceSetImpl_Loading_Error_Message;
	public static String Bpmn2ModelerResourceSetImpl_Loading_Error;
	public static String Bpmn2ModelerResourceSetImpl_Loading_Resource_Not_Found;
	public static String Bpmn2ModelerResourceSetImpl_Loading_Title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

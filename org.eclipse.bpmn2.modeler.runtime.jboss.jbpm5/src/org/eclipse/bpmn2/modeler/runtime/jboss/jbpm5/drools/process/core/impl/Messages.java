package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.drools.process.core.impl;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.drools.process.core.impl.messages"; //$NON-NLS-1$
	public static String ParameterDefinitionImpl_Null_Name;
	public static String ParameterDefinitionImpl_Null_Parameter;
	public static String WorkImpl_Null_Parameter;
	public static String WorkImpl_Work_Item_Name;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

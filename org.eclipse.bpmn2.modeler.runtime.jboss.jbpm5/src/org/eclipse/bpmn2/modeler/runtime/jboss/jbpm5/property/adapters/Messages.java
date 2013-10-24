package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.adapters;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.adapters.messages"; //$NON-NLS-1$
	public static String JbpmFormalExpressionPropertiesAdapter_Condition_Language;
	public static String JbpmFormalExpressionPropertiesAdapter_Rule;
	public static String JbpmFormalExpressionPropertiesAdapter_Script_Language;
	public static String JbpmGlobalTypePropertiesAdapter_Data_Type_Label;
	public static String JbpmGlobalTypePropertiesAdapter_Label;
	public static String JbpmGlobalTypePropertiesAdapter_Name;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

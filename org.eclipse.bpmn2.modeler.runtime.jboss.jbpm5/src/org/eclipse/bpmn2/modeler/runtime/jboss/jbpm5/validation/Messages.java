package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.validation;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.validation.messages"; //$NON-NLS-1$
	public static String BusinessRuleTaskConstraint_No_Ruleflow_Group;
	public static String CallActivityConstraint_No_Called_Element;
	public static String CallActivityConstraint_No_Process;
	public static String ProcessConstraint_No_Package_Name;
	public static String ProcessConstraint_No_Process_Name;
	public static String ProcessVariableNameConstraint_Duplicate_ID;
	public static String UserTaskConstraint_Internal_Error;
	public static String UserTaskConstraint_No_Form;
	public static String UserTaskConstraint_No_Name;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

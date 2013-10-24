package org.eclipse.bpmn2.modeler.ui.adapters.properties;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.ui.adapters.properties.messages"; //$NON-NLS-1$
	public static String CallActivityPropertiesAdapter_Called_Activity;
	public static String DataAssociationPropertiesAdapter_Source;
	public static String DataAssociationPropertiesAdapter_Target;
	public static String ErrorPropertiesAdapter_Error_Code;
	public static String ErrorPropertiesAdapter_ID;
	public static String EscalationPropertiesAdapter_Escalation_Code;
	public static String EscalationPropertiesAdapter_ID;
	public static String FormalExpressionPropertiesAdapter_Constraint;
	public static String FormalExpressionPropertiesAdapter_Script;
	public static String FormalExpressionPropertiesAdapter_Script_Language;
	public static String ImportPropertiesAdapter_Import;
	public static String ItemAwareElementPropertiesAdapter_ID;
	public static String ItemDefinitionPropertiesAdapter_Data_Type;
	public static String ItemDefinitionPropertiesAdapter_Structure;
	public static String OperationPropertiesAdapter_Title;
	public static String ParticipantPropertiesAdapter_Multiplicity;
	public static String PropertyPropertiesAdapter_EventVar_Prefix;
	public static String PropertyPropertiesAdapter_LocalVar_Prefix;
	public static String PropertyPropertiesAdapter_ProcessVar_Prefix;
	public static String PropertyPropertiesAdapter_TaskVar_Prefix;
	public static String PropertyPropertiesAdapter_Variable;
	public static String SignalPropertiesAdapter_ID;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

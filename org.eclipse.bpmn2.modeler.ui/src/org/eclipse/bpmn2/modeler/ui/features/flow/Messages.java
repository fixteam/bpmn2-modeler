package org.eclipse.bpmn2.modeler.ui.features.flow;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.ui.features.flow.messages"; //$NON-NLS-1$
	public static String AssociationFeatureContainer_Name;
	public static String AssociationFeatureContainer_Description;
	public static String DataAssociationFeatureContainer_Name;
	public static String DataAssociationFeatureContainer_Description;
	public static String DataAssociationFeatureContainer_Commit_Error_Message;
	public static String DataAssociationFeatureContainer_Commit_Error_Title;
	public static String DataAssociationFeatureContainer_Invalid_Source;
	public static String DataAssociationFeatureContainer_Invalid_Target;
	public static String DataAssociationFeatureContainer_Mapped_To;
	public static String DataAssociationFeatureContainer_New_Input_For;
	public static String DataAssociationFeatureContainer_New_Output_For;
	public static String DataAssociationFeatureContainer_Reference_To;
	public static String DataAssociationFeatureContainer_Unmapped;
	public static String MessageFlowFeatureContainer_Name;
	public static String MessageFlowFeatureContainer_Description;
	public static String MessageFlowFeatureContainer_Decorator_Moved;
	public static String MessageFlowFeatureContainer_Ref_Changed;
	public static String SequenceFlowFeatureContainer_Name;
	public static String SequenceFlowFeatureContainer_Description;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

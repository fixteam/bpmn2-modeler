package org.eclipse.bpmn2.modeler.core.merrimac.dialogs;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.core.merrimac.dialogs.messages"; //$NON-NLS-1$
	public static String AbstractObjectEditingDialog_Commit_Error;
	public static String AbstractObjectEditingDialog_Commit_Error_Title;
	public static String FeatureEditingDialog_Create;
	public static String FeatureEditingDialog_Edit;
	public static String FeatureListObjectEditor_Title;
	public static String ModelSubclassSelectionDialog_Title;
	public static String ObjectEditingDialog_Create;
	public static String ObjectEditingDialog_Edit;
	public static String ObjectEditor_No_Description;
	public static String ObjectEditor_Set_Error_Message;
	public static String ReadonlyTextObjectEditor_Invalid_Feature;
	public static String ReadonlyTextObjectEditor_Title;
	public static String RefListEditingDialog_Add;
	public static String RefListEditingDialog_Add_All;
	public static String RefListEditingDialog_Move_Down;
	public static String RefListEditingDialog_Move_Up;
	public static String RefListEditingDialog_Remove;
	public static String RefListEditingDialog_Remove_All;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

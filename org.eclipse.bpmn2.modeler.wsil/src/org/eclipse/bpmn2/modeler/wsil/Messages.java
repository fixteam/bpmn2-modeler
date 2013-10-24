/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc.
 *  All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 *
 * @author Bob Brodt
 ******************************************************************************/

package org.eclipse.bpmn2.modeler.wsil;

import org.eclipse.osgi.util.NLS;

/**
 * @author Bob Brodt
 *
 */
public class Messages extends NLS {


	private static String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.wsil.messages"; //$NON-NLS-1$
	
	public static String SchemaImportDialog_8;
	public static String SchemaImportDialog_9;
	public static String SchemaImportDialog_15;
	public static String SchemaImportDialog_16;
	public static String SchemaImportDialog_26;
	
	public static String SchemaImportDialogWithWSIL_Message;

	public static String SchemaImportDialogWithWSIL_Title;

	public static String WSILPreferencePage_WSIL_1;
	public static String WSILPreferencePage_WSIL_2;
	public static String WSILPreferencePage_WSIL_Description;
	public static String WSILPreferencePage_WSIL_Add;
	public static String WSILPreferencePage_WSIL_EnterLocation;
	public static String WSILPreferencePage_WSIL_EnterDescription;
	public static String WSILPreferencePage_WSIL_Remove;
	public static String WSILPreferencePage_WSIL_MoveUp;
	public static String WSILPreferencePage_WSIL_MoveDown;
	public static String WSILPreferencePage_WSIL_OpenInBrowser;
	public static String WSILPreferencePage_WSIL_NameLimit;
	public static String WSILPreferencePage_WSIL_DocumentNotLoaded;
	public static String WSILPreferencePage_WSIL_Abstract;
	public static String WSILPreferencePage_WSIL_Location;
	public static String WSILPreferencePage_WSIL_Namespace;
	public static String WSILPreferencePage_WSIL_Index;

	private Messages() {
		// Do not instantiate
	}

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}

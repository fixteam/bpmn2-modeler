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
 * @author Innar Made
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.ui.preferences;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.preferences.ModelEnablementTreeEntry;
import org.eclipse.bpmn2.modeler.core.preferences.ModelEnablements;
import org.eclipse.bpmn2.modeler.core.preferences.ToolProfilesPreferencesHelper;
import org.eclipse.bpmn2.modeler.core.runtime.ModelEnablementDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil.Bpmn2DiagramType;
import org.eclipse.bpmn2.modeler.ui.Activator;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.prefs.BackingStoreException;

public class ToolProfilesPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	// Page ID must be the same as defined in plugin.xml
	public static String PAGE_ID = "org.eclipse.bpmn2.modeler.Profiles";
	
	private Bpmn2Preferences preferences;
	private TargetRuntime currentRuntime;
	private Bpmn2DiagramType currentDiagramType;
	private String currentProfile;
	private final List<ModelEnablementTreeEntry> bpmnEntries = new ArrayList<ModelEnablementTreeEntry>();
	private final List<ModelEnablementTreeEntry> extensionEntries = new ArrayList<ModelEnablementTreeEntry>();
	
	private Combo cboRuntimes;
	private Combo cboDiagramTypes;
	private Combo cboProfiles;
	private Button btnUseAsDefaultProfile;
	private Button btnCreateProfile;
	private Button btnDeleteProfile;
	private Button btnShowIds;
	private ModelEnablementTreeViewer bpmnTreeViewer;
	private Tree bpmnTree;
	private ModelEnablementTreeViewer extensionTreeViewer;
	private Tree extensionTree;

	// a list of ToolProfilesPreferencesHelpers, one for each permutation of Target Runtime, Diagram Type
	// and Tool Profiles defined in the Preferences. Helpers contain the Model Enablement list and are used
	// as factories for Model Enablement Tree Entries. 
	private Hashtable<TargetRuntime,
				Hashtable<Bpmn2DiagramType,
					Hashtable<String, ToolProfilesPreferencesHelper>>> helpers =
		new Hashtable<TargetRuntime,
				Hashtable<Bpmn2DiagramType,
					Hashtable<String,ToolProfilesPreferencesHelper>>>();

	/**
	 * Create the property page.
	 */
	public ToolProfilesPreferencePage() {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	@Override
	public void init(IWorkbench workbench) {
		preferences = Bpmn2Preferences.getInstance();
		ToolProfilesPreferencesHelper.setEnableIdAttribute(preferences.getShowIdAttribute());
	}

	private ToolProfilesPreferencesHelper getHelper(TargetRuntime rt, Bpmn2DiagramType diagramType, String profile) {
		Hashtable<Bpmn2DiagramType, Hashtable<String,ToolProfilesPreferencesHelper>> map1 = helpers.get(rt);
		
		if (map1==null) {
			map1 = new Hashtable<Bpmn2DiagramType, Hashtable<String,ToolProfilesPreferencesHelper>>();
			helpers.put(rt, map1);
		}
		
		Hashtable<String,ToolProfilesPreferencesHelper> map2 = map1.get(diagramType);
		if (map2==null) {
			map2 = new Hashtable<String,ToolProfilesPreferencesHelper>();
			map1.put(diagramType, map2);
		}
		
		ToolProfilesPreferencesHelper helper = map2.get(profile);
		if (helper==null) {
			helper = new ToolProfilesPreferencesHelper(rt, diagramType, profile);
			ModelEnablements me = preferences.getModelEnablements(rt, diagramType, profile);
			helper.setModelEnablements(me);
			map2.put(profile, helper);
		}
		else {
			ToolProfilesPreferencesHelper.setEnableIdAttribute(btnShowIds.getSelection());
			ModelEnablements me = helper.getModelEnablements();
			if (me==null) {
				me = preferences.getModelEnablements(rt, diagramType, profile);
			}
			helper.setModelEnablements(me);
		}

		return helper;
	}

	private boolean hasHelper(TargetRuntime rt, Bpmn2DiagramType diagramType, String profile) {
		Hashtable<Bpmn2DiagramType, Hashtable<String,ToolProfilesPreferencesHelper>> map1 = helpers.get(rt);
		
		if (map1==null) {
			map1 = new Hashtable<Bpmn2DiagramType, Hashtable<String,ToolProfilesPreferencesHelper>>();
			helpers.put(rt, map1);
		}
		
		Hashtable<String,ToolProfilesPreferencesHelper> map2 = map1.get(diagramType);
		if (map2==null) {
			map2 = new Hashtable<String,ToolProfilesPreferencesHelper>();
			map1.put(diagramType, map2);
		}
		
		ToolProfilesPreferencesHelper helper = map2.get(profile);
		if (helper==null) {
			return false;
		}


		return true;
	}
	
	/**
	 * Create contents of the property page.
	 * 
	 * @param parent
	 */
	@Override
	public Control createContents(final Composite parent) {
		
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(4, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false, 1, 1));

		currentRuntime = TargetRuntime.getCurrentRuntime();
		currentDiagramType = Bpmn2DiagramType.NONE;
		currentProfile = "";
		
		final Label lblRuntime = new Label(container, SWT.NONE);
		lblRuntime.setText("Target Runtime");
		lblRuntime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		cboRuntimes = new Combo(container, SWT.READ_ONLY);
		cboRuntimes.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		cboRuntimes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String s = cboRuntimes.getText();
				currentRuntime = (TargetRuntime) cboRuntimes.getData(s);
				fillProfilesCombo();
				fillModelEnablementTrees();
			}
		});
		fillRuntimesCombo();

		final Label lblDiagramType = new Label(container, SWT.NONE);
		lblDiagramType.setText("Diagram Type");
		lblDiagramType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));

		cboDiagramTypes = new Combo(container, SWT.READ_ONLY);
		cboDiagramTypes.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		cboDiagramTypes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String s = cboDiagramTypes.getText();
				currentDiagramType = (Bpmn2DiagramType) cboDiagramTypes.getData(s);
				fillProfilesCombo();
				fillModelEnablementTrees();
			}
		});
		fillDiagramTypesCombo();

		final Label lblProfile = new Label(container, SWT.NONE);
		lblProfile.setText("Tool Profile");
		lblProfile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 3, 1));

		cboProfiles = new Combo(container, SWT.READ_ONLY);
		cboProfiles.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cboProfiles.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				currentProfile = cboProfiles.getText();
				fillModelEnablementTrees();
			}
		});
		
		
		Composite buttonContainer = new Composite(container, SWT.NULL);
		buttonContainer.setLayout(new GridLayout(4, false));
		buttonContainer.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false, 4, 1));
		
		btnUseAsDefaultProfile = new Button(buttonContainer, SWT.CHECK);
		btnUseAsDefaultProfile.setText("Use as default profile");
		btnUseAsDefaultProfile.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		
		final Label lblFiller = new Label(buttonContainer, SWT.NONE);
		lblFiller.setText("");
		lblFiller.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		btnCreateProfile = new Button(buttonContainer, SWT.PUSH);
		btnCreateProfile.setText("Create Profile");
		btnCreateProfile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnCreateProfile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CreateProfileDialog dlg = new CreateProfileDialog(parent.getShell());
				if (dlg.open() == Window.OK) {
					currentProfile = dlg.getValue();
					preferences.createToolProfile(currentRuntime, currentDiagramType, currentProfile);
					preferences.setDefaultToolProfile(currentRuntime, currentDiagramType, currentProfile);
					if (dlg.getCopyProfile()!=null) {
						// make a copy of an existing Tool Profile: get the Model Enablements to be copied
						Bpmn2DiagramType saveDiagramType = currentDiagramType;
						currentDiagramType = dlg.getCopyDiagramType();
						currentProfile = dlg.getCopyProfile();
						ToolProfilesPreferencesHelper helper = getHelper(currentRuntime, currentDiagramType, currentProfile);
						ModelEnablements copyMe = helper.getModelEnablements();

						// create a helper for the new Tool Profile
						currentProfile = dlg.getValue();
						currentDiagramType = saveDiagramType;
						helper = getHelper(currentRuntime, currentDiagramType, currentProfile);
						
						// and copy the ModelEnablements into it
						helper.copyModelEnablements(copyMe);
						preferences.setModelEnablements(currentRuntime, currentDiagramType, currentProfile, helper.getModelEnablements());
					}					
					currentProfile = dlg.getValue();
					fillProfilesCombo();
					fillModelEnablementTrees();
				}
			}
		});
		
		btnDeleteProfile = new Button(buttonContainer, SWT.PUSH);
		btnDeleteProfile.setText("Delete Profile");
		btnDeleteProfile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnDeleteProfile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (MessageDialog.openQuestion(getShell(),
						"Confirm",
						"This operation can not be undone.\nAre you sure you want to delete this Tool Profile?")) {
					
					preferences.deleteToolProfile(currentRuntime, currentDiagramType, currentProfile);
					fillProfilesCombo();
					fillModelEnablementTrees();
				}
			}
		});

		fillProfilesCombo();
		
		btnShowIds = new Button(buttonContainer, SWT.CHECK);
		btnShowIds.setText("Show ID attributes (Advanced Behavior)");
		btnShowIds.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		btnShowIds.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ToolProfilesPreferencesHelper.setEnableIdAttribute(btnShowIds.getSelection());
				getHelper(currentRuntime, currentDiagramType, currentProfile);
				fillModelEnablementTrees();
			}
		});
		btnShowIds.setSelection(preferences.getShowIdAttribute());
		
		final Composite treesContainer = new Composite(container, SWT.NONE);
		treesContainer.setLayout(new GridLayout(2, false));
		treesContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));
		
		// Create Checkbox Tree Viwers for standard BPMN 2.0 elements and any extension elements
		ICheckStateListener checkStateListener = new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				boolean checked = event.getChecked();
				Object element = event.getElement();
				if (element instanceof ModelEnablementTreeEntry) {
					ModelEnablementTreeEntry entry = (ModelEnablementTreeEntry)element;
					ToolProfilesPreferencesHelper helper = getHelper(currentRuntime, currentDiagramType, currentProfile);
					helper.setEnabled(entry, checked);
				}
			}
		};
		
		bpmnTreeViewer = new ModelEnablementTreeViewer(treesContainer, Messages.ToolProfilePreferencePage_Standard_Elements_Label);
		bpmnTree = bpmnTreeViewer.getTree();
		bpmnTreeViewer.addCheckStateListener(checkStateListener);

		extensionTreeViewer = new ModelEnablementTreeViewer(treesContainer, Messages.ToolProfilePreferencePage_Extension_Elements_Label);
		extensionTree = extensionTreeViewer.getTree();
		extensionTreeViewer.addCheckStateListener(checkStateListener);

		// adjust height of the tree viewers to fill their container when dialog is resized
		// oddly enough, setting GridData.widthHint still causes the controls to fill available
		// horizontal space, but setting heightHint just keeps them the same height. Probably
		// because a GridLayout has a fixed number of columns, but variable number of rows.
		parent.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				GridData gd = (GridData) bpmnTree.getLayoutData();
				gd.heightHint = 1000;
				gd = (GridData) extensionTree.getLayoutData();
				gd.heightHint = 1000;
				treesContainer.layout();
			}
		});

		Composite importExportButtons = new Composite(container, SWT.NONE);
		importExportButtons.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 4, 1));
		importExportButtons.setLayout(new FillLayout());

		Button btnImportProfile = new Button(importExportButtons, SWT.NONE);
		btnImportProfile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), SWT.NULL);
				String path = dialog.open();
				if (path != null) {
					try {
						bpmnEntries.clear();
						extensionEntries.clear();
						getHelper(currentRuntime, currentDiagramType, currentProfile).importProfile(path);
						fillModelEnablementTrees();
					} catch (Exception e1) {
						Activator.showErrorWithLogging(e1);
					}
				}
			}
		});
		btnImportProfile.setText(Messages.ToolProfilePreferencePage_Import);

		Button btnExportProfile = new Button(importExportButtons, SWT.NONE);
		btnExportProfile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), SWT.SAVE);
				String path = dialog.open();
				if (path != null) {
					try {
						getHelper(currentRuntime, currentDiagramType, currentProfile).exportProfile(path);
					} catch (Exception e1) {
						Activator.showErrorWithLogging(e1);
					}
				}
			}
		});
		btnExportProfile.setText(Messages.ToolProfilePreferencePage_Export);

		fillModelEnablementTrees();
		
		bpmnTree.setEnabled(true);
		extensionTree.setEnabled(true);

		return container;
	}

	private void fillRuntimesCombo() {
		int i = 0;
		for (TargetRuntime r : TargetRuntime.getAllRuntimes()) {
			cboRuntimes.add(r.getName());
			cboRuntimes.setData(r.getName(), r);
			if (r == currentRuntime)
				cboRuntimes.select(i);
			++i;
		}
	}
	
	private void fillDiagramTypesCombo() {
		int i = 0;
		currentDiagramType = Bpmn2DiagramType.PROCESS;

		for (Bpmn2DiagramType t : Bpmn2DiagramType.values()) {
			cboDiagramTypes.add(t.toString());
			cboDiagramTypes.setData(t.toString(), t);
			if (t==currentDiagramType)
				cboDiagramTypes.select(i);
			++i;
		}
	}
	
	private void fillProfilesCombo() {
		if (cboProfiles!=null) {
			int i = 0;
			int iSelected = -1;
			currentProfile = preferences.getDefaultToolProfile(currentRuntime, currentDiagramType);
	
			cboProfiles.removeAll();
			for (String profile : preferences.getAllToolProfiles(currentRuntime, currentDiagramType)) {
				String text = profile;
				if (text==null || text.isEmpty())
					text = Messages.ToolProfilePreferencePage_Unnamed + (i+1);
				cboProfiles.add(text);
				if (iSelected<0 && (currentProfile!=null && currentProfile.equals(profile)))
					cboProfiles.select(iSelected = i);
				++i;
			}
			if (btnDeleteProfile!=null)
				btnDeleteProfile.setEnabled(cboProfiles.getItemCount()>0);

		}
	}
	
	private void fillModelEnablementTrees() {
		loadModelEnablements();
		bpmnTreeViewer.setInput(bpmnEntries);
		extensionTreeViewer.setInput(extensionEntries);

		if (TargetRuntime.DEFAULT_RUNTIME_ID.equals(currentRuntime.getId())) {
			extensionTreeViewer.setVisible(false);
		}
		else {
			extensionTreeViewer.setVisible(true);
		}
	}
	
	@Override
	protected void performDefaults() {
		super.performDefaults();
		String path = Bpmn2Preferences.getToolProfilePath(currentRuntime, currentDiagramType);
		preferences.setToDefault(path);
		path = Bpmn2Preferences.getModelEnablementsPath(currentRuntime, currentDiagramType, currentProfile);
		preferences.setToDefault(path);
		fillProfilesCombo();
		// force the helper's Model Enablements to be reloaded from default preferences
		ToolProfilesPreferencesHelper helper = getHelper(currentRuntime, currentDiagramType, currentProfile);
		helper.setModelEnablements(null);
		fillModelEnablementTrees();
	}

	private void loadModelEnablements() {
		ToolProfilesPreferencesHelper helper = getHelper(currentRuntime, currentDiagramType, currentProfile);

		loadModelEnablements(helper, bpmnEntries, null, null);
		ModelEnablementDescriptor med = currentRuntime.getModelEnablements(currentDiagramType, currentProfile);
		if (med!=null)
			loadModelEnablements(helper, extensionEntries, bpmnEntries, med);
	}
	
	private Object[] loadModelEnablements(ToolProfilesPreferencesHelper helper, List<ModelEnablementTreeEntry> entries, List<ModelEnablementTreeEntry> bpmnEntries, ModelEnablementDescriptor med) {
		entries.clear();
		if (med!=null)
			entries.addAll(helper.getAllExtensionElements(med, bpmnEntries));
		else
			entries.addAll(helper.getAllElements());

		ArrayList<ModelEnablementTreeEntry> enabled = new ArrayList<ModelEnablementTreeEntry>();
		for (ModelEnablementTreeEntry entry : entries) {
			if (entry.getEnabled()) {
				enabled.add(entry);
			}
			ArrayList<ModelEnablementTreeEntry> children = entry.getChildren();
			for (ModelEnablementTreeEntry t : children) {
				if (t.getEnabled()) {
					enabled.add(t);
				}
			}
		}
		return enabled.toArray();
	}

	@Override
	public boolean performOk() {
		setErrorMessage(null);
		try {
			for (TargetRuntime rt : TargetRuntime.getAllRuntimes()) {
				for (Bpmn2DiagramType diagramType : Bpmn2DiagramType.values()) {
					for (String profile : preferences.getAllToolProfiles(rt, diagramType)) {
						if (hasHelper(rt, diagramType, profile)) {
							ToolProfilesPreferencesHelper helper = getHelper(rt, diagramType, profile);
							preferences.setModelEnablements(rt, diagramType, profile, helper.getModelEnablements());
						}
					}
				}
			}

			preferences.setShowIdAttribute(btnShowIds.getSelection());
			if (btnUseAsDefaultProfile.getSelection())
				preferences.setDefaultToolProfile(currentRuntime, currentDiagramType, currentProfile);

			preferences.save();
		} catch (BackingStoreException e) {
			Activator.showErrorWithLogging(e);
		}
		return true;
	}
	
	private class CreateProfileDialog extends InputDialog {

		private String copySelection = null;
		
		public CreateProfileDialog(Shell parentShell) {
			super(parentShell,
					"Create New Profile",
					NLS.bind("Enter the name of the new Profile to create in Target Runtime \"{0}\"", currentRuntime.getName()),
					"",
					new IInputValidator() {

				@Override
				public String isValid(String newText) {
					if (newText==null || newText.isEmpty())
						return "Profile name can not be empty";
					for (String p : cboProfiles.getItems()) {
						if (newText.equals(p))
							return "Profile "+p+" is already defined";
					}
					return null;
				}
				
			});
		}

		public String getCopyProfile() {
			if (copySelection!=null && copySelection.contains("/")) {
				int i = copySelection.indexOf("/");
				return copySelection.substring(i+1);
			}
			return null;
		}

		public Bpmn2DiagramType getCopyDiagramType() {
			if (copySelection!=null && copySelection.contains("/")) {
				int i = copySelection.indexOf("/");
				String s = copySelection.substring(0,i);
				return Bpmn2DiagramType.fromString(s);
			}
			return null;
		}
		
		@Override
	    protected Control createDialogArea(Composite parent) {
			Composite composite = (Composite) super.createDialogArea(parent);

			Composite container = new Composite(composite, SWT.NULL);
			container.setLayout(new GridLayout(2, false));
			container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			
			final Button btnCopy = new Button(container, SWT.CHECK);
			btnCopy.setText("Copy settings from this profile:");
			btnCopy.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));

			final Combo cboCopy = new Combo(container, SWT.READ_ONLY);
			cboCopy.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
			cboCopy.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					copySelection = cboCopy.getText();
				}
			});
			
			for (Bpmn2DiagramType diagramType : Bpmn2DiagramType.values()) {
				for (String profile : preferences.getAllToolProfiles(currentRuntime, diagramType)) {
					String key = diagramType + "/" + profile;
					cboCopy.add(key);
				}
			}
			cboCopy.setEnabled(false);
			
			btnCopy.setEnabled(cboCopy.getItemCount()>0);
			btnCopy.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					cboCopy.setEnabled(btnCopy.getSelection());
					if (!btnCopy.getSelection())
						copySelection = null;
				}
			});
			
			return composite;
		}
	}
	
	/**
	 * The "Show ID Attribute" preference is shared with the Behavior page.
	 * 
	 * @return
	 */
	public boolean getShowIdAttribute() {
		if (btnShowIds!=null)
			return btnShowIds.getSelection();
		return preferences.getShowIdAttribute();
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible && btnShowIds!=null) {
			// copy the "Show ID Attribute" setting from the Behavior page if it is active
			Bpmn2EditorBehaviorPreferencePage page = (Bpmn2EditorBehaviorPreferencePage) Bpmn2HomePreferencePage.getPage(getContainer(), Bpmn2EditorBehaviorPreferencePage.PAGE_ID);
			if (page!=null)
				btnShowIds.setSelection(page.getShowIdAttribute());
		}
	}
}

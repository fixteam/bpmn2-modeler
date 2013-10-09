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
package org.eclipse.bpmn2.modeler.ui.wizards;

import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil.Bpmn2DiagramType;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

public class BPMN2DiagramWizardPage2 extends WizardPage {
	private Text containerText;

	private Text fileText;
	private Text targetNamespaceText;

	private ISelection selection;

	private IResource diagramContainer;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public BPMN2DiagramWizardPage2(ISelection selection) {
		super("wizardPage2");
		setTitle("BPMN2 Diagram File");
		setDescription("Select file name.");
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText("&Location:");

		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		containerText.setLayoutData(gd);
		containerText.setEditable(false);
		containerText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				targetNamespaceText.setText("");
				dialogChanged(true);
			}
		});

		Button button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("&File name:");

		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		fileText.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false, 2, 1));
		fileText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dialogChanged(false);
			}
		});

		label = new Label(container, SWT.NULL);
		label.setText("&Target Namespace:");

		targetNamespaceText = new Text(container, SWT.BORDER | SWT.SINGLE);
		targetNamespaceText.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false, 2, 1));
		targetNamespaceText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dialogChanged(false);
			}
		});
		
		updatePageDescription();
		updateFilename();
		dialogChanged(true);
		setControl(container);
	}

	private Bpmn2DiagramType getDiagramType() {
		BPMN2DiagramWizardPage1 page1 = (BPMN2DiagramWizardPage1)getWizard().getPage("wizardPage1");
		return page1.getDiagramType();
	}
		
	/**
	 * Tests if the current workbench selection is a suitable diagramContainer to use.
	 */

	private void updatePageDescription() {
		BPMN2DiagramWizardPage1 page1 = (BPMN2DiagramWizardPage1)getWizard().getPage("wizardPage1");
		String descriptionType = "Unknown Diagram Type";
		switch (page1.getDiagramType()) {
		case PROCESS:
			descriptionType = "Process Diagram";
			break;
		case COLLABORATION:
			descriptionType = "Collaboration Diagram";
			break;
		case CHOREOGRAPHY:
			descriptionType = "Choreography Diagram";
			break;
		default:
			break;
		}
		setDescription("Enter a file name for the new "+descriptionType);
	}
	
	private void updateFilename() {
		BPMN2DiagramWizardPage1 page1 = (BPMN2DiagramWizardPage1)getWizard().getPage("wizardPage1");
		String fileType = "unknown";
		String filename = fileType+".bpmn";
		switch (page1.getDiagramType()) {
		case PROCESS:
			fileType = "process";
			break;
		case COLLABORATION:
			fileType = "collaboration";
			break;
		case CHOREOGRAPHY:
			fileType = "choreography";
			break;
		default:
			return;
		}
		
		IContainer container = getFileContainer();
		if (container!=null) {
			String text = container.getFullPath().toString();
			if (text!=null && !text.equals(getContainerName()))
				containerText.setText(text);
			for (int i=1; ; ++i) {
				filename = fileType+"_" + i + ".bpmn";
				IResource file = container.findMember(filename);
				if (file==null) {
					break;
				}
			}
		}

		String oldFileText = fileText.getText();
		if (filename!=null && !filename.equals(oldFileText))
			fileText.setText(filename);
	}

	private IContainer getFileContainer() {
		if (selection != null && selection.isEmpty() == false && selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() == 1) {
				Object obj = ssel.getFirstElement();
				if (obj instanceof IAdaptable) {
					Object res = ((IAdaptable)obj).getAdapter(IResource.class);
					if (res!=null)
						obj = res;
				}
				if (obj instanceof Path) {
					obj = ResourcesPlugin.getWorkspace().getRoot().findMember((Path)obj);
				}
				if (obj instanceof IResource) {
					if (obj instanceof IContainer) {
						return (IContainer) obj;
					} else {
						return ((IResource) obj).getParent();
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			updatePageDescription();
			updateFilename();
		}
		super.setVisible(visible);
	}

	/**
	 * Uses the standard diagramContainer selection dialog to choose the new value for the diagramContainer field.
	 */

	private void handleBrowse() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace()
				.getRoot(), false, "Select Folder for the diagram");
		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				selection = new TreeSelection(new TreePath(result));
				containerText.setText(((Path) result[0]).toString());
			}
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged(boolean initialize) {
		boolean complete = false;
		if (validateContainer()) {
			diagramContainer = getFileContainer();
			if (initialize) {
				
				TargetRuntime rt = Bpmn2Preferences.getInstance(diagramContainer.getProject()).getRuntime();
				String targetNamespace = rt.getRuntimeExtension().getTargetNamespace(getDiagramType());
				if (targetNamespace==null)
					targetNamespace = "";
				
				if (rt!=TargetRuntime.getDefaultRuntime() && !targetNamespace.isEmpty()) {
					// Target Runtime will provide its own target namespace
					if (!targetNamespaceText.getText().equals(targetNamespace)) {
						targetNamespaceText.setText(targetNamespace);
						updateFilename();
					}
				}
				else {
					// The default "None" Target Runtime's target namespace may be edited by user.
					String text = targetNamespaceText.getText();
					if (text==null || text.isEmpty()) {
						targetNamespaceText.setText(targetNamespace);
						updateFilename();
					}
				}

			}
			if (validateFileName() && validateTargetNamespace()) {
				updateStatus(null);
				complete = true;
			}
		}
		setPageComplete(complete);
	}

	private boolean validateContainer() {
		IContainer container = getFileContainer();
		if (container==null) {
			setErrorMessage("Project and Folder must exist");
			return false;
		}
		if ((container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
			setErrorMessage("Folder must exist");
			return false;
		}
		if (!container.isAccessible()) {
			setErrorMessage("Project must be writable");
			return false;
		}
		return true;
	}
	
	private boolean validateFileName() {
		if (!validateContainer())
			return false;
		
		IContainer container = getFileContainer();
		String fileName = getFileName();
		if (fileName.length() == 0) {
			setErrorMessage("Name must be specified");
			return false;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
			setErrorMessage("Name must be valid");
			return false;
		}
		int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc != -1) {
			String ext = fileName.substring(dotLoc + 1);
			if (ext.equalsIgnoreCase("bpmn") == false && ext.equalsIgnoreCase("bpmn2") == false) {
				setErrorMessage("File extension must be \"bpmn\" or \"bpmn2\"");
				return false;
			}
		}
		else {
			setErrorMessage("Name is missing a file extension");
			return false;
		}
		IResource file = container.findMember(fileName);
		if (file!=null) {
			setErrorMessage("The file "+fileName+" already exists in this Project Folder");
			return false;
		}
		return true;
	}

	private boolean validateTargetNamespace() {
		String targetNamespace = targetNamespaceText.getText();
		if (targetNamespace==null || targetNamespace.isEmpty()) {
			setErrorMessage("Target Namespace must be specified");
			return false;
		}
		URI uri = URI.createURI(targetNamespace);
		if (!(uri.hasAuthority() &&uri.hasAbsolutePath())) {
			setErrorMessage("Target Namespace must be in the form 'http://absolute/path'");
			return false;
		}
		return true;
	}
	
	@Override
	public boolean isPageComplete() {
		return validateContainer() &&
				validateFileName() &&
				validateTargetNamespace();
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getContainerName() {
		return containerText.getText();
	}

	public String getFileName() {
		return fileText.getText();
	}

	public IResource getDiagramContainer() {
		return diagramContainer;
	}

	public String getTargetNamespace() {
		return targetNamespaceText.getText();
	}
}
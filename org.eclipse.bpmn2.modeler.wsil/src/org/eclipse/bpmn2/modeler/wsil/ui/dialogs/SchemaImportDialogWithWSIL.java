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

package org.eclipse.bpmn2.modeler.wsil.ui.dialogs;
import org.eclipse.bpel.wsil.model.inspection.Description;
import org.eclipse.bpel.wsil.model.inspection.Inspection;
import org.eclipse.bpel.wsil.model.inspection.Link;
import org.eclipse.bpel.wsil.model.inspection.Name;
import org.eclipse.bpel.wsil.model.inspection.Service;
import org.eclipse.bpel.wsil.model.inspection.TypeOfAbstract;
import org.eclipse.bpmn2.modeler.wsil.Messages;
import org.eclipse.bpmn2.modeler.ui.property.dialogs.DefaultSchemaImportDialog;
import org.eclipse.bpmn2.modeler.ui.property.providers.ModelLabelProvider;
import org.eclipse.bpmn2.modeler.wsil.ui.preferences.WSILPreferencePage;
import org.eclipse.bpmn2.modeler.wsil.ui.providers.WSILContentProvider;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.DrillDownComposite;

/**
 * Browse for complex/simple types available in the process and choose that
 * simple type.
 * 
 */

public class SchemaImportDialogWithWSIL extends DefaultSchemaImportDialog {

	// button id for browsing WSIL
	protected static final int BID_BROWSE_WSIL = IDialogConstants.CLIENT_ID + 5;
	// Browse button id


	// Import from WSIL constructs
	private Composite fWSILComposite;
	protected TreeViewer fWSILTreeViewer;
	protected Tree fWSILTree;
	protected Text filterText;

	String fBasePath = fPrefStore.getString(WSILPreferencePage.PREF_WSIL_URL);

	// The WSIL radio box is turned off if the WSIL document is not set in the
	// modelEnablement.
	Button fBtnWSIL;

	
	public SchemaImportDialogWithWSIL() {
		super(Display.getCurrent().getActiveShell(), -1);
	}

	protected void buttonPressed(int id, boolean checked) {

		if (id==BID_BROWSE_WSIL) {
			if (checked==false) {
				return;
			}
			if (fBasePath==null || fBasePath.isEmpty()) {
				MessageDialog.openInformation(getShell(), Messages.SchemaImportDialogWithWSIL_Title,
						Messages.SchemaImportDialogWithWSIL_Message);
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						fBtnWSIL.setSelection(false);
						fBtnResource.setSelection(true);
						buttonPressed(BID_BROWSE_WORKSPACE, true);
					}
				});
				return;
			}
		}
		
		setVisibleControl(fWSILComposite, fImportSource==BID_BROWSE_WSIL && fImportType != BID_IMPORT_JAVA);
		if (fImportType!=BID_IMPORT_JAVA) {
			setVisibleControl(fKindButtonComposite, true);
			setVisibleControl(fBrowseButton,true);
			fLocationLabel.setText(Messages.SchemaImportDialog_8);
			fBrowseButton.setText(fImportSource==BID_BROWSE_FILE || fImportSource==BID_BROWSE_WSIL ?
					Messages.SchemaImportDialog_9 : Messages.SchemaImportDialog_26);
		}
		super.buttonPressed(id, checked);
	}
	
	protected void createImportLocation(Composite parent) {

		super.createImportLocation(parent);

		// create WSIL UI widgets
		createWSILStructure(fKindGroup);

	}

	protected Object createWSILStructure(Composite parent) {
		
		// Add WSIL option to button bar
		fBtnWSIL = createRadioButton(fKindButtonComposite, Messages.SchemaImportDialog_15,
				BID_BROWSE_WSIL, fImportSource == BID_BROWSE_WSIL);

		// create WSIL section
        fWSILComposite = new Composite(parent, SWT.NONE);

        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        fWSILComposite.setLayout(layout);
        
		GridData data = new GridData();        
        data.grabExcessVerticalSpace = true;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.minimumHeight = 220;
        fWSILComposite.setLayoutData(data);
        
		Label location = new Label(fWSILComposite, SWT.NONE);
	    location.setText( Messages.SchemaImportDialog_16 );
	    
	    data = new GridData();
	    data.grabExcessHorizontalSpace = true;
	    data.horizontalAlignment = SWT.LEFT;
	    location.setLayoutData(data);
	    
	    filterText = new Text(fWSILComposite, SWT.BORDER);
	    data = new GridData(GridData.FILL_HORIZONTAL);
	    filterText.setLayoutData(data);
	    
    	filterText.addKeyListener(new KeyListener() {
    		
    		public void keyPressed(KeyEvent e) {
    			
    		}
    		
    		public void keyReleased(KeyEvent e) {
    			// set the value of the filter.
    			fFilter = filterText.getText().trim().toLowerCase();
    			    		
       			if (fFilter.length() > 0) {
       				/* for the time being, only filter 3 levels deep 
       				 * since link references within WSIL are rare at 
       				 * this time.  when adoption of WSIL directories
       				 * take off, this needs to be rehashed */ 
       				fWSILTreeViewer.expandToLevel(3);
       			}
       			fWSILTreeViewer.refresh();
       			e.doit = false;
			}	
    	});
	    
	    DrillDownComposite wsilTreeComposite = new DrillDownComposite(fWSILComposite, SWT.BORDER);
		
		layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        layout.verticalSpacing = 0;
        wsilTreeComposite.setLayout(layout);
        
        data = new GridData();        
        data.grabExcessVerticalSpace = true;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        wsilTreeComposite.setLayoutData(data);
	        
		//	Tree viewer for variable structure ...
		fWSILTree = new Tree(wsilTreeComposite, SWT.NONE );
		data = new GridData();        
        data.grabExcessVerticalSpace = true;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.minimumHeight = 200;
        fWSILTree.setLayoutData(data);
		  		
		fWSILTreeViewer = new TreeViewer( fWSILTree );
		fWSILTreeViewer.setContentProvider( new WSILContentProvider() );
		fWSILTreeViewer.setLabelProvider( new ModelLabelProvider() );
		
		Object wsilDoc = attemptLoad(URI.createURI(fBasePath),"wsil"); //$NON-NLS-1$
		fWSILTreeViewer.setInput ( 	wsilDoc ) ;
		if (wsilDoc == null || wsilDoc instanceof Throwable  ) {
//			fBtnWSIL.setEnabled(false);
			// that's always available.
			// delete fImportSource = BID_BROWSE_WORKSPACE; by Grid.Qian
			// because if not, the dialog always display the resource Control
			// regardless last time if user choose the resource button
				
			/*// that's always available.
			fImportSource = BID_BROWSE_WORKSPACE;*/
		}
		
		
		// set default tree expansion to the 2nd level
		fWSILTreeViewer.expandToLevel(2);
		fWSILTreeViewer.addFilter(new TreeFilter());
		fWSILTreeViewer.setComparator(new WSILViewerComparator());

		wsilTreeComposite.setChildTree(fWSILTreeViewer);

		fWSILTreeViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						IStructuredSelection sel = (IStructuredSelection) event.getSelection();
						if (sel.getFirstElement() instanceof Service) {
							Service serv = (Service) sel.getFirstElement();
							Description descr = serv.getDescription().get(0);
							attemptLoad(descr.getLocation());
						} else {
							markEmptySelection();
						}
					}
				});
		// end tree viewer for variable structure

		return fWSILComposite;
	}
	
	/**
	 * 
	 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
	 * @date May 4, 2007
	 * 
	 */
	public class TreeFilter extends ViewerFilter {

		/**
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
		 *      java.lang.Object, java.lang.Object)
		 */
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {

			if (fFilter == null || fFilter.length() == 0) {
				return true;
			}

			if (element instanceof Service) {
				String text = ""; //$NON-NLS-1$
				Service service = (Service) element;
				if (service.getName().size() > 0) {
					Name name = service.getName().get(0);
					text += name.getValue();
				}
				if (service.getAbstract().size() > 0) {
					TypeOfAbstract abst = service.getAbstract().get(0);
					text += abst.getValue();
				}
				return (text.toLowerCase().indexOf(fFilter) > -1);
			}

			return true;
		}
	}

	/**
	 * 
	 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
	 * @date May 10, 2007
	 * 
	 */
	public class WSILViewerComparator extends ViewerComparator {

		/**
		 * @see org.eclipse.jface.viewers.ViewerComparator#category(java.lang.Object)
		 */
		@Override
		public int category(Object element) {
			if (element instanceof Inspection)
				return 1;
			if (element instanceof Link)
				return 2;
			if (element instanceof Service)
				return 3;

			return 0;
		}
	}
}

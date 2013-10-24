/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc. 
 * All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 *******************************************************************************/
package org.eclipse.bpmn2.modeler.core;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.wst.sse.ui.StructuredTextEditor;

public class Bpmn2TabbedPropertySheetPage extends TabbedPropertySheetPage implements IAdaptable {

	DiagramEditor diagramEditor;
	private ISelection currentSelection;
	
	public Bpmn2TabbedPropertySheetPage(
			ITabbedPropertySheetPageContributor tabbedPropertySheetPageContributor) {
		super(tabbedPropertySheetPageContributor);
		diagramEditor = (DiagramEditor)tabbedPropertySheetPageContributor;
	}
	
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		currentSelection = selection;
		// Ignore selections from Source Viewer for now.
		// When there is better synchronization between Source Viewer and Design Editor
		// we can navigate from the selected IDOMNode to the BPMN2 model element and
		// modify the selection here...
		if (selection instanceof IStructuredSelection) {
			// ugly hack to disable selection while source viewer is active
			if (diagramEditor.getAdapter(StructuredTextEditor.class)!=null) {
				selection = new StructuredSelection(""); //$NON-NLS-1$
			}
			super.selectionChanged(part, selection);
		}
	}
	
	public DiagramEditor getDiagramEditor() {
		return diagramEditor;
	}

	@Override
	public Object getAdapter(Class adapter) {
		if (diagramEditor!=null)
			return diagramEditor.getAdapter(adapter);
		return null;
	}
}

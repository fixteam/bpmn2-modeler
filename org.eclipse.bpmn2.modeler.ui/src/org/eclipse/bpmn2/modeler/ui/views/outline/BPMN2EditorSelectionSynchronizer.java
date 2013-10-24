/*******************************************************************************
 * Copyright (c) 2011, 2012, 2013 Red Hat, Inc.
 * All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 	Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.ui.views.outline;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;

public class BPMN2EditorSelectionSynchronizer extends SelectionSynchronizer {
	
	protected EditPart convert(EditPartViewer viewer, EditPart part) {
		if (viewer instanceof BPMN2EditorOutlineTreeViewer) {
			BPMN2EditorOutlineTreeViewer ov = (BPMN2EditorOutlineTreeViewer)viewer;
			return ov.convert(part);
		}
		else if (viewer instanceof GraphicalViewer && part instanceof AbstractGraphicsTreeEditPart) {
			return BPMN2EditorOutlineTreeViewer.convert((GraphicalViewer)viewer, (AbstractGraphicsTreeEditPart)part);
		}
		return super.convert(viewer,part);
	}

}

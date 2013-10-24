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

package org.eclipse.bpmn2.modeler.runtime.example;

import org.eclipse.bpmn2.modeler.core.IBpmn2RuntimeExtension;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil.Bpmn2DiagramType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;

/**
 * @author Bob Brodt
 *
 */

public class SampleRuntimeExtension implements IBpmn2RuntimeExtension {
	
	public static final String RUNTIME_ID = "org.eclipse.bpmn2.modeler.runtime.example";
	
	private static final String targetNamespace = "http://org.eclipse.bpmn2.modeler.runtime.example";
	private static final String[] typeLanguages = new String[] {
		"http://www.w3.org/2001/XMLSchema", "XML Schema",
		"http://www.java.com/javaTypes", "Java",
	};
	private static final String[] expressionLanguages = new String[] {
		"http://www.w3.org/1999/XPath", "XPath",
		"http://www.mvel.org/2.0", "mvel",
		"http://www.java.com/java", "java",
	};

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.IBpmn2RuntimeExtension#getTargetNamespace(org.eclipse.bpmn2.modeler.core.utils.ModelUtil.Bpmn2DiagramType)
	 */
	@Override
	public String getTargetNamespace(Bpmn2DiagramType diagramType) {
		return targetNamespace;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.IBpmn2RuntimeExtension#initialize()
	 * @see org.eclipse.bpmn2.modeler.runtime.example.SampleImageProvider#registerAvailableImages()
	 */
	@Override
	public void initialize(DiagramEditor editor) {
		SampleImageProvider.registerAvailableImages();
	}

	@Override
	public String[] getTypeLanguages() {
		return typeLanguages;
	}

	@Override
	public String[] getExpressionLanguages() {
		return expressionLanguages;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.IBpmn2RuntimeExtension#isContentForRuntime(org.eclipse.bpmn2.modeler.core.IFile)
	 */
	@Override
	public boolean isContentForRuntime(IEditorInput input) {
		// IMPORTANT: The plugin is responsible for inspecting the file contents!
		// Unless you are absolutely sure that the file is targeted for this runtime
		// (by, e.g. looking at the targetNamespace or some other feature) then this
		// method must return FALSE.
		return false;
	}
}

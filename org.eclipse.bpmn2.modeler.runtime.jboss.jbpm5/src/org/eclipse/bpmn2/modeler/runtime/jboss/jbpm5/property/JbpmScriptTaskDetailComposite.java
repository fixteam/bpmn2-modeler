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

package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property;

import java.util.Hashtable;

import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ComboObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.TextObjectEditor;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Bob Brodt
 *
 */
public class JbpmScriptTaskDetailComposite extends JbpmTaskDetailComposite {

	ComboObjectEditor scriptFormatEditor;
	TextObjectEditor scriptEditor;
	
	/**
	 * @param section
	 */
	public JbpmScriptTaskDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	public JbpmScriptTaskDetailComposite(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void cleanBindings() {
		super.cleanBindings();
		scriptFormatEditor = null;
		scriptEditor = null;
	}

	@Override
	public void createBindings(EObject be) {
		scriptFormatEditor = new ComboObjectEditor(this,be,be.eClass().getEStructuralFeature("scriptFormat")) { //$NON-NLS-1$

			@Override
			protected Hashtable<String, Object> getChoiceOfValues(EObject object, EStructuralFeature feature) {
				Hashtable<String, Object> choiceOfValues = new Hashtable<String, Object>();
				choiceOfValues.put("Java", "http://www.java.com/java"); //$NON-NLS-1$ //$NON-NLS-2$
				choiceOfValues.put("MVEL", "http://www.mvel.org/2.0"); //$NON-NLS-1$ //$NON-NLS-2$
				return choiceOfValues;
			}
			
			@Override
			protected boolean canSetNull() {
				return true;
			}
			
		};
		scriptFormatEditor.createControl(getAttributesParent(),Messages.JbpmScriptTaskDetailComposite_Script_Language);

		scriptEditor = new TextObjectEditor(this,be,be.eClass().getEStructuralFeature("script")); //$NON-NLS-1$
		scriptEditor.createControl(getAttributesParent(),"Script"); //$NON-NLS-1$

		bindAttribute(be,Messages.JbpmScriptTaskDetailComposite_8);
	}
}

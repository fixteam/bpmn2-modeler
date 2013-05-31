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

import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractListComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractPropertiesProvider;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsPackage;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.util.JbpmModelUtil;
import org.eclipse.bpmn2.modeler.ui.property.ExtensionValueListComposite;
import org.eclipse.bpmn2.modeler.ui.property.diagrams.DefinitionsPropertyComposite;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jdt.core.IType;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Bob Brodt
 *
 */
public class JbpmDefinitionsPropertyComposite extends DefinitionsPropertyComposite {

	/**
	 * @param section
	 */
	public JbpmDefinitionsPropertyComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	public JbpmDefinitionsPropertyComposite(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public AbstractPropertiesProvider getPropertiesProvider(EObject object) {
		if (propertiesProvider==null) {
			propertiesProvider = new AbstractPropertiesProvider(object) {
				String[] properties = new String[] {
						"name",
						"imports",
						"rootElements#ItemDefinition",
						"rootElements#Resource",
						"rootElements#Message",
						"rootElements#Error",
						"rootElements#Signal",
						"rootElements#Escalation",
				};
				
				@Override
				public String[] getProperties() {
					return properties; 
				}
			};
		}
		return propertiesProvider;
	}

	@Override
	protected Composite bindFeature(EObject object, EStructuralFeature feature, EClass eItemClass) {
		if ("imports".equals(feature.getName())) {
			if (object instanceof Definitions) {
				Definitions definitions = (Definitions)object;
				for (RootElement re : definitions.getRootElements()) {
					if (re instanceof Process) {
						Process process = (Process)re;
						ExtensionValueListComposite importsTable = new ExtensionValueListComposite(
								this,  AbstractListComposite.READ_ONLY_STYLE)
						{
							@Override
							protected EObject addListItem(EObject object, EStructuralFeature feature) {
								IType type = JbpmModelUtil.showImportDialog(object);
								return JbpmModelUtil.addImport(type, object);
							}
	
						};
						importsTable.bindList(process, DroolsPackage.eINSTANCE.getDocumentRoot_ImportType());
						importsTable.setTitle("Imports");
						return importsTable;
					}
				}
			}
			return null;
		}
		else
			return super.bindFeature(object, feature, eItemClass);
	}
}

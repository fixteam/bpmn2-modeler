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

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractPropertiesProvider;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultListComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.ListCompositeColumnProvider;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.TableColumn;
import org.eclipse.bpmn2.modeler.ui.property.diagrams.DefinitionsPropertySection;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Bob Brodt
 *
 */
public class JbpmDefinitionsPropertySection extends DefinitionsPropertySection {

	@Override
	protected AbstractDetailComposite createSectionRoot() {
		return new JbpmDefinitionsPropertyComposite(this);
	}

	@Override
	public AbstractDetailComposite createSectionRoot(Composite parent, int style) {
		return new JbpmDefinitionsPropertyComposite(parent,style);
	}
	
	@Override
	public boolean appliesTo(IWorkbenchPart part, ISelection selection) {
		return super.appliesTo(part, selection);
	}
	
	public class JbpmMessageDetailComposite extends DefaultDetailComposite {

		private AbstractPropertiesProvider propertiesProvider;

		public JbpmMessageDetailComposite(Composite parent, int style) {
			super(parent, style);
		}

		/**
		 * @param section
		 */
		public JbpmMessageDetailComposite(AbstractBpmn2PropertySection section) {
			super(section);
		}

		@Override
		public AbstractPropertiesProvider getPropertiesProvider(EObject object) {
			if (propertiesProvider==null) {
				propertiesProvider = new AbstractPropertiesProvider(object) {
					String[] properties = new String[] {
							"id", //$NON-NLS-1$
							"itemRef" //$NON-NLS-1$
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
		protected void bindAttribute(Composite parent, EObject object, EAttribute attribute, String label) {
			if ("id".equals(attribute.getName())) //$NON-NLS-1$
				label = Messages.JbpmDefinitionsPropertySection_Name;
			super.bindAttribute(parent, object, attribute, label);
		}
		
		@Override
		protected boolean isModelObjectEnabled(String className, String featureName) {
			return true;
		}
	}
	
	public class JbpmMessageListComposite extends DefaultListComposite {

		public JbpmMessageListComposite(AbstractBpmn2PropertySection section, int style) {
			super(section, style);
			// TODO Auto-generated constructor stub
		}

		public JbpmMessageListComposite(AbstractBpmn2PropertySection section) {
			super(section, DEFAULT_STYLE);
		}

		public JbpmMessageListComposite(Composite parent, int style) {
			super(parent, DEFAULT_STYLE);
		}
		
		@Override
		public ListCompositeColumnProvider getColumnProvider(EObject object, EStructuralFeature feature) {
			if (columnProvider==null) {
				columnProvider = new ListCompositeColumnProvider(this);
				TableColumn tc = new TableColumn(object,Bpmn2Package.eINSTANCE.getBaseElement_Id());
				tc.setHeaderText(Messages.JbpmDefinitionsPropertySection_Name); 
				columnProvider.add(tc);
				columnProvider.add(new TableColumn(object,Bpmn2Package.eINSTANCE.getMessage_ItemRef()));
			}
			return columnProvider;
		}

		@Override
		protected boolean isModelObjectEnabled(String className, String featureName) {
			return true;
		}
	}

}

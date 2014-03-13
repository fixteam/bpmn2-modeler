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

package org.eclipse.bpmn2.modeler.ui.adapters.properties;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Documentation;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.adapters.FeatureDescriptor;
import org.eclipse.bpmn2.modeler.core.adapters.InsertionAdapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

/**
 * @author Bob Brodt
 *
 */
public class DocumentationPropertiesAdapter extends ExtendedPropertiesAdapter<Documentation> {

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public DocumentationPropertiesAdapter(AdapterFactory adapterFactory, Documentation object) {
		super(adapterFactory, object);

    	final EStructuralFeature textFeature = Bpmn2Package.eINSTANCE.getDocumentation_Text();
    	setFeatureDescriptor(textFeature,
			new FeatureDescriptor<Documentation>(adapterFactory,object,textFeature) {
    		
    			@Override
    			
    			public void setValue(Object context, final Object value) {
    				final Documentation documentation = adopt(context);
    				final String text = value==null ? "" : value.toString();
    				InsertionAdapter.executeIfNeeded(documentation);
    				TransactionalEditingDomain editingDomain = getEditingDomain(documentation);
    				final BaseElement owner = (BaseElement) documentation.eContainer();
					if (editingDomain == null) {
						documentation.setText(text);
					} else {
						editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {
							@Override
							protected void doExecute() {
								documentation.setText(text);
							}
						});
					}
    			}

				@Override
				public boolean isMultiLine(Object context) {
					// formal expression body is always a multiline text field
					return true;
				}
			}
    	);
	}

}

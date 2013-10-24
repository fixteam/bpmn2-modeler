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

import java.util.List;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Message;
import org.eclipse.bpmn2.MessageFlow;
import org.eclipse.bpmn2.ReceiveTask;
import org.eclipse.bpmn2.SendTask;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.adapters.ObjectDescriptor;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.features.choreography.ChoreographyUtil;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;

/**
 * @author Gary Brown
 *
 */
public class MessageFlowPropertiesAdapter extends ExtendedPropertiesAdapter<MessageFlow> {

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public MessageFlowPropertiesAdapter(AdapterFactory adapterFactory, MessageFlow object) {
		super(adapterFactory, object);

		EStructuralFeature ref = Bpmn2Package.eINSTANCE.getMessageFlow_MessageRef();
    	setFeatureDescriptor(ref, new RootElementRefFeatureDescriptor<MessageFlow>(adapterFactory,object,ref) {
    		
    		public void setValue(Object context, final Object value) {
    			if (value instanceof Message || value==null) {
	    			final MessageFlow object = adopt(context);
	    			final Message message = (Message)value; 
					final TransactionalEditingDomain editingDomain = getEditingDomain(object);
					if (editingDomain == null) {
						setMessageRef(object, message);
					} else {
						editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {
							@Override
							protected void doExecute() {
								setMessageRef(object, message);
							}
						});
					}
    			}
    		}
    		
    	});

    	setObjectDescriptor(new ObjectDescriptor<MessageFlow>(adapterFactory, object) {
			@Override
			public String getDisplayName(Object context) {
				final MessageFlow flow = adopt(context);
				String text = ""; //$NON-NLS-1$
				if (flow.getName()!=null)
					text = flow.getName();
				else {
					if (flow.getMessageRef()!=null) {
						text += ChoreographyUtil.getMessageFlowName(flow);
					}
					
					if (flow.getSourceRef() != null) {
						text += "(" + ModelUtil.getDisplayName(flow.getSourceRef())+"->"; //$NON-NLS-1$ //$NON-NLS-2$
						
						if (flow.getTargetRef() != null) {
							text += ModelUtil.getDisplayName(flow.getTargetRef());
						}
						text += ")"; //$NON-NLS-1$
					}
				}
				return text;
			}
    	});
	}
	
	private void setMessageRef(MessageFlow messageFlow, Message message) {
		ResourceSet resourceSet = messageFlow.eResource().getResourceSet();
		
		// first change the MessageRef on the SendTask
		messageFlow.setMessageRef(message);
		
		// If the source and/or target of this Message Flow are a SendTask
		// or ReceiveTask make sure the messageRef is the same as ours
		List<Connection> connections = DIUtils.getConnections(resourceSet, messageFlow);
		for (Connection connection : connections) {
			Object o = BusinessObjectUtil.getFirstBaseElement(connection.getEnd().getParent());
			if (o instanceof ReceiveTask) {
				((ReceiveTask)o).setMessageRef(message);
			}
			o = BusinessObjectUtil.getFirstBaseElement(connection.getStart().getParent());
			if (o instanceof SendTask) {
				((SendTask)o).setMessageRef(message);
			}
		}
	}

}

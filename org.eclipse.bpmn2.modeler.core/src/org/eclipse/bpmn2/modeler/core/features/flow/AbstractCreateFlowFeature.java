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
 * @author Ivar Meikas
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.features.flow;

import org.eclipse.bpmn2.Association;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.ChoreographyTask;
import org.eclipse.bpmn2.Collaboration;
import org.eclipse.bpmn2.Conversation;
import org.eclipse.bpmn2.ConversationLink;
import org.eclipse.bpmn2.ConversationNode;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.InteractionNode;
import org.eclipse.bpmn2.MessageFlow;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.SubChoreography;
import org.eclipse.bpmn2.SubProcess;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2CreateConnectionFeature;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;

public abstract class AbstractCreateFlowFeature<
		CONNECTION extends BaseElement,
		SOURCE extends EObject,
		TARGET extends EObject>
	extends AbstractBpmn2CreateConnectionFeature<CONNECTION, SOURCE, TARGET> {

	public AbstractCreateFlowFeature(IFeatureProvider fp, String name, String description) {
		super(fp, name, description);
	}

	@Override
	public boolean canCreate(ICreateConnectionContext context) {
		SOURCE source = getSourceBo(context);
		TARGET target = getTargetBo(context);
		return source != null && target != null;
	}

	@Override
	public Connection create(ICreateConnectionContext context) {
		Connection connection = null;
		CONNECTION businessObject = createBusinessObject(context);
		if (businessObject!=null) {
			AddConnectionContext addContext = createAddConnectionContext(context, businessObject);
			addContext.setNewObject(businessObject);
	
			IPeService peService = Graphiti.getPeService();
			IGaService gaService = Graphiti.getGaService();
			ILocation loc, shapeLoc;
			
			// the CreateConnectionContext contains the source and target locations - the actual
			// mouse locations where the connection was started and ended. These locations must
			// be passed to the AddConnectionContext so they can be added (as String properties)
			// to the Connection once it is created. These String properties are then decoded in
			// AnchorUtil.getSourceAndTargetBoundaryAnchors() to create Ad Hoc anchors if necessary.
			loc = context.getSourceLocation();
			if (loc==null)
				loc = peService.getLocationRelativeToDiagram(context.getSourceAnchor());
			shapeLoc = peService.getLocationRelativeToDiagram((Shape)context.getSourceAnchor().getParent());
			Point p = gaService.createPoint(
					loc.getX() - shapeLoc.getX(),
					loc.getY() - shapeLoc.getY());
			addContext.putProperty(AnchorUtil.CONNECTION_SOURCE_LOCATION, p);
			
			loc = context.getTargetLocation();
			if (loc==null)
				loc = peService.getLocationRelativeToDiagram(context.getTargetAnchor());
			shapeLoc = peService.getLocationRelativeToDiagram((Shape)context.getTargetAnchor().getParent());
			p = gaService.createPoint(
					loc.getX() - shapeLoc.getX(),
					loc.getY() - shapeLoc.getY());
			addContext.putProperty(AnchorUtil.CONNECTION_TARGET_LOCATION, p);
			addContext.putProperty(AnchorUtil.CONNECTION_CREATED, Boolean.TRUE);
	
			connection = (Connection) getFeatureProvider().addIfPossible(addContext);
			ModelUtil.setID(businessObject);
	
			FeatureSupport.updateConnection(getFeatureProvider(), connection);
	
			changesDone = true;
		}
		else
			changesDone = false;
		
		return connection;
	}

	@Override
	public CONNECTION createBusinessObject(ICreateConnectionContext context) {
		CONNECTION businessObject = super.createBusinessObject(context);
		SOURCE source = getSourceBo(context);
		TARGET target = getTargetBo(context);
		addConnectionToContainer(businessObject, source, target);

		return businessObject;
	}

	protected abstract String getStencilImageId();

	@Override
	public String getCreateImageId() {
		return getStencilImageId();
	}

	@Override
	public String getCreateLargeImageId() {
		return getStencilImageId();
	}
	
	/**
	 * Add a connection object to its correct container, depending on the type of connection.
	 * The container is determined by the connection's source object, which may be either a
	 * FlowNode or InteractionNode depending on the type of connection.
	 * 
	 * The container is determined as follows, based on connection type:
	 *   SequenceFlow - added to the list of FlowElements in a FlowElementsContainer,
	 *                  e.g. Process, SubProcess, etc.
	 *   Association - added to the list of Artifacts in a FlowElementsContainer
	 *   MessageFlow - added to the list of MessageFlows in a Collaboration
	 *   ConversationLink - added to the list of ConversationLinks in a Collaboration
	 *   
	 * This method does NOT handle DataAssociations which, technically appear like connections,
	 * but are really input/output mappings of an Activity's I/O Parameters. These are handled
	 * as a special case in
	 * @link org.eclipse.bpmn2.modeler.ui.features.flow.DataAssociationFeatureContainer#createBusinessObject()
	 * 
	 * @param connection - the connection to be added to a container element
	 * @param source
	 * @param target
	 * @return
	 */
	private boolean addConnectionToContainer(CONNECTION connection, SOURCE source, TARGET target) {
		if (connection instanceof SequenceFlow) {
			if (source instanceof Participant) {
				if (((Participant)source).getProcessRef()!=null) {
					((Participant)source).getProcessRef().getFlowElements().add((SequenceFlow)connection);
					return true;
				}
			}
			else if (source instanceof FlowElementsContainer) {
				((FlowElementsContainer)source).getFlowElements().add((SequenceFlow)connection);
				return true;
			}
		}
		else if (connection instanceof Association) {
			if (source instanceof Process) {
				((Process)source).getArtifacts().add((Association)connection);
				return true;
			}
			else if (source instanceof SubProcess) {
				((SubProcess)source).getArtifacts().add((Association)connection);
				return true;
			}
			else if (source instanceof SubChoreography) {
				((SubChoreography)source).getArtifacts().add((Association)connection);
				return true;
			}
			else if (source instanceof Collaboration) {
				((Collaboration)source).getArtifacts().add((Association)connection);
				return true;
			}
		}
		else if (connection instanceof MessageFlow) {
			if (source instanceof Process) {
				// find the Collaboration that owns this Process
				Definitions definitions = ModelUtil.getDefinitions(source);
				for (Collaboration c : ModelUtil.getAllRootElements(definitions, Collaboration.class)) {
					for (Participant p : c.getParticipants()) {
						if (p.getProcessRef() == source) {
							source = (SOURCE)c;
							break;
						}
					}
				}
			}
			if (source instanceof Collaboration) {
				((Collaboration)source).getMessageFlows().add((MessageFlow)connection);
				return true;
			}
			else if (source instanceof ChoreographyTask) {
				((ChoreographyTask)source).getMessageFlowRef().add((MessageFlow)connection);
			}
			else if (source instanceof ConversationNode) {
				((ConversationNode)source).getMessageFlowRefs().add((MessageFlow)connection);
			}
		}
		else if (connection instanceof ConversationLink) {
			if (source instanceof Process) {
				// find the Collaboration that owns this Process
				Definitions definitions = ModelUtil.getDefinitions(source);
				for (Collaboration c : ModelUtil.getAllRootElements(definitions, Collaboration.class)) {
					for (Participant p : c.getParticipants()) {
						if (p.getProcessRef() == source) {
							source = (SOURCE)c;
							break;
						}
					}
				}
			}
			if (source instanceof Collaboration) {
				((Collaboration)source).getConversationLinks().add((ConversationLink)connection);
				return true;
			}
			if (source instanceof Conversation && target instanceof Participant) {
				((Conversation)source).getParticipantRefs().add((Participant)target);
			}
			if (target instanceof Conversation && source instanceof Participant) {
				((Conversation)target).getParticipantRefs().add((Participant)source);
			}
		}

		if (source.eContainer() instanceof EObject) {
			return addConnectionToContainer(connection, (SOURCE)source.eContainer(), target);
		}
		
		return false;
	}
}
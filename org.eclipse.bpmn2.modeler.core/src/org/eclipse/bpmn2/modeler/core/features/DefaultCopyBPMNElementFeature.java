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
package org.eclipse.bpmn2.modeler.core.features;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.modeler.core.ModelHandler;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.util.Bpmn2Resource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dd.di.DiagramElement;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICopyContext;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.graphiti.ui.features.AbstractCopyFeature;

public class DefaultCopyBPMNElementFeature extends AbstractCopyFeature {

	public DefaultCopyBPMNElementFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canCopy(ICopyContext context) {
      /*  final PictogramElement[] pes = context.getPictogramElements();
        if (pes == null || pes.length == 0) {  // nothing selected
            return false;
        }
       
        // return true if all selected elements have linked BaseElements
        for (PictogramElement pe : pes) {
            final Object bo = BusinessObjectUtil.getFirstBaseElement(pe);
            if (!(bo instanceof BaseElement)) {
                return false;
            }
        }*/
        return true;
	}

	@Override
	public void copy(ICopyContext context) {
        PictogramElement[] pes = context.getPictogramElements();
        List<PictogramElement> copied = new ArrayList<PictogramElement>();
        for (int i = 0; i < pes.length; i++) {
            PictogramElement pe = pes[i];
            // only copy connections if both source and target Shapes
            // are also selected (i.e. don't copy a "dangling" connection)
            if (pe instanceof Connection) {
            	Connection connection = (Connection)pe;
            	PictogramElement source = connection.getStart().getParent();
            	PictogramElement target = connection.getEnd().getParent();
            	boolean containsSource = false;
            	boolean containsTarget = false;
            	for (PictogramElement p : pes) {
            		if (source==p)
            			containsSource = true;
            		else if (target==p)
            			containsTarget = true;
            	}
        		if (containsSource && containsTarget) {
        			copied.add(pe);
        		}
            }
            else {
    			copied.add(pe);
            }
        }
        
        // include all connections between the selected shapes, even if they
        // are not selected.
        copied.addAll(findAllConnections(copied));

        // remove PEs that are contained in FlowElementsContainers
        List<PictogramElement> ignored = new ArrayList<PictogramElement>();
        for (PictogramElement pe : copied) {
        	if (pe instanceof ContainerShape) {
        		for (PictogramElement childPe : ((ContainerShape) pe).getChildren()) {
        			if (copied.contains(childPe))
        				ignored.add(childPe);
        		}
        	}
        }
        copied.removeAll(ignored);
        
        // copy all PictogramElements to the clipboard
       /* ResourceSet resourceSet = new ResourceSetImpl();

		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml", new XMIResourceFactoryImpl());

		String path = ResourcesPlugin.getWorkspace().getRoot().getProject("fixflow-expand").getLocation().toString() + "/template/process_demo_2.bpmn";
		
		XMLResource resource = (XMLResource) resourceSet.getResource(URI.createFileURI(path), true);
		
		try {
			resource.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<PictogramElement> pictogramElements = new ArrayList<PictogramElement>();
		
		Definitions definitions = ModelUtil.getDefinitions(resource);
		Process process = (Process) definitions.getRootElements().get(0);
		final BPMNDiagram bpmnDiagram = definitions.getDiagrams().get(0);
		final Diagram diagram = Graphiti.getPeCreateService().createDiagram("BPMN2", bpmnDiagram.getName(), true);
		TransactionalEditingDomain domain = ((DiagramEditor) getDiagramEditor()).getEditingDomain();
		domain.getCommandStack().execute(new RecordingCommand(domain) {
			protected void doExecute() {
				getFeatureProvider().link(diagram, bpmnDiagram);
			}
		});
		
		for (BaseElement baseElement : ModelHandler.getAll(resource, FlowNode.class)) {
			pictogramElements.add(getFeatureProvider().getPictogramElementForBusinessObject(baseElement));
		}
		putToClipboard(pictogramElements.toArray());*/
        putToClipboard(copied.toArray());
	}
	
	public static List<Connection> findAllConnections(List<PictogramElement> shapes) {
        List<Connection> connections = new ArrayList<Connection>();
        for (PictogramElement pe : shapes) {
        	if (pe instanceof ContainerShape) {
        		ContainerShape shape = (ContainerShape)pe;
        		for (Anchor a : shape.getAnchors()) {
        			for (Connection c : a.getIncomingConnections()) {
        				if (	(shapes.contains(c.getStart().getParent()) ||
        						shapes.contains(c.getEnd().getParent())) &&
        						!shapes.contains(c) && !connections.contains(c)) {
        					connections.add(c);
        				}
        			}
        			for (Connection c : a.getOutgoingConnections()) {
        				if (	(shapes.contains(c.getStart().getParent()) ||
        						shapes.contains(c.getEnd().getParent())) &&
        						!shapes.contains(c) && !connections.contains(c)) {
        					connections.add(c);
        				}
        			}
        		}
        	}
        }
        return connections;
	}
}

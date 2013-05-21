package org.eclipse.bpmn2.modeler.core;

import org.eclipse.bpmn2.Bpmn2Factory;
import org.eclipse.bpmn2.ExtensionAttributeValue;
import org.eclipse.bpmn2.FormalExpression;
import org.eclipse.bpmn2.PotentialOwner;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.ResourceAssignmentExpression;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.UserTask;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNEdge;
import org.eclipse.bpmn2.di.BPMNPlane;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.di.BpmnDiFactory;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.dd.dc.Bounds;
import org.eclipse.dd.dc.DcFactory;
import org.eclipse.dd.dc.Point;
import org.eclipse.emf.ecore.EStructuralFeature.Internal;
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl.SimpleFeatureMapEntry;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;


import com.founder.fix.bpmn2extensions.fixflow.AssignPolicyType;
import com.founder.fix.bpmn2extensions.fixflow.FixFlowFactory;
import com.founder.fix.bpmn2extensions.fixflow.FixFlowPackage;
import com.founder.fix.bpmn2extensions.fixflow.SkipStrategy;
import com.founder.fix.bpmn2extensions.fixflow.TaskCommand;

public class FixModelHandler extends ModelHandler {

	@Override
	public BPMNDiagram createProcessDiagram(final String name) {

	
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(resource);
		final BPMNDiagram bpmnDiagram = BpmnDiFactory.eINSTANCE.createBPMNDiagram();

		if (domain != null) {
			domain.getCommandStack().execute(new RecordingCommand(domain) {
				@Override
				protected void doExecute() {
					BPMNPlane plane = BpmnDiFactory.eINSTANCE.createBPMNPlane();
					ModelUtil.setID(plane,resource);

					Process process = createProcess();
					process.setName(name+" Process");
					// the Process ID should be the same as the resource name
					String filename = resource.getURI().lastSegment();
					if (filename.contains("."))
						filename = filename.split("\\.")[0];
					process.setId( ModelUtil.generateID(process,resource,filename) );

					// create StartEvent
					StartEvent startEvent = create(StartEvent.class);
//					startEvent.setName("Start Event");
					process.getFlowElements().add(startEvent);
					
					// create SequenceFlow
					SequenceFlow flow = create(SequenceFlow.class);
					process.getFlowElements().add(flow);
					
					// create EndEvent
					UserTask endEvent = create(UserTask.class);
					endEvent.setName("提交任务");
//					endEvent.setName("End Event");
					process.getFlowElements().add(endEvent);
					startEvent.setName("启动");
					// hook 'em up
					startEvent.getOutgoing().add(flow);
					endEvent.getIncoming().add(flow);
					flow.setSourceRef(startEvent);
					flow.setTargetRef(endEvent);
					endEvent.getResources().add(createPotentialOwner());
					
					ExtensionAttributeValue extensionElement = Bpmn2Factory.eINSTANCE
					        .createExtensionAttributeValue();
					endEvent.getExtensionValues().add(extensionElement);
					FeatureMap.Entry extensionElementEntry = new SimpleFeatureMapEntry(
					        (org.eclipse.emf.ecore.EStructuralFeature.Internal) FixFlowPackage.Literals.DOCUMENT_ROOT__TASK_COMMAND, createTaskCommand());
					extensionElement.getValue().add(extensionElementEntry);

					
					extensionElementEntry = new SimpleFeatureMapEntry(
					        (org.eclipse.emf.ecore.EStructuralFeature.Internal) FixFlowPackage.Literals.DOCUMENT_ROOT__TASK_COMMAND, createTaskViewCommand());
					extensionElement.getValue().add(extensionElementEntry);
					FeatureMap.Entry extensionElementEntry2 = new SimpleFeatureMapEntry(
					        (org.eclipse.emf.ecore.EStructuralFeature.Internal) FixFlowPackage.Literals.DOCUMENT_ROOT__ASSIGN_POLICY_TYPE, createAssignPolicyType());
					extensionElement.getValue().add(extensionElementEntry2);
					
					
					FeatureMap.Entry extensionElementEntry3 = new SimpleFeatureMapEntry(
					        (org.eclipse.emf.ecore.EStructuralFeature.Internal) FixFlowPackage.Literals.DOCUMENT_ROOT__SKIP_STRATEGY, createSkipStrategy());
					extensionElement.getValue().add(extensionElementEntry3);
					
					
					

					// create DI shapes
					BPMNShape shape = BpmnDiFactory.eINSTANCE.createBPMNShape();
					ModelUtil.setID(shape,resource);

					// StartEvent shape
					shape.setBpmnElement(startEvent);
					Bounds bounds = DcFactory.eINSTANCE.createBounds();
					bounds.setX(100);
					bounds.setY(100);
					bounds.setWidth(GraphicsUtil.EVENT_SIZE);
					bounds.setHeight(GraphicsUtil.EVENT_SIZE);
					shape.setBounds(bounds);
					plane.getPlaneElement().add(shape);
					getPreferences().applyBPMNDIDefaults(shape, null);
					
					// SequenceFlow edge
					BPMNEdge edge = BpmnDiFactory.eINSTANCE.createBPMNEdge();
					edge.setBpmnElement(flow);
					edge.setSourceElement(shape);
					
					Point wp = DcFactory.eINSTANCE.createPoint();
					wp.setX(100+GraphicsUtil.EVENT_SIZE);
					wp.setY(100+GraphicsUtil.EVENT_SIZE/2);
					edge.getWaypoint().add(wp);
					
					wp = DcFactory.eINSTANCE.createPoint();
					wp.setX(500);
					wp.setY(100+GraphicsUtil.EVENT_SIZE/2);
					edge.getWaypoint().add(wp);
					
					plane.getPlaneElement().add(edge);

					// EndEvent shape
					shape = BpmnDiFactory.eINSTANCE.createBPMNShape();
					ModelUtil.setID(shape,resource);

					shape.setBpmnElement(endEvent);
					bounds = DcFactory.eINSTANCE.createBounds();
					bounds.setX(240);
					bounds.setY(93);
					bounds.setWidth(GraphicsUtil.TASK_DEFAULT_WIDTH);
					bounds.setHeight(GraphicsUtil.TASK_DEFAULT_HEIGHT);
					shape.setBounds(bounds);
					plane.getPlaneElement().add(shape);
					getPreferences().applyBPMNDIDefaults(shape, null);

					edge.setTargetElement(shape);
					
					// add to BPMNDiagram
					plane.setBpmnElement(process);
					bpmnDiagram.setPlane(plane);
					bpmnDiagram.setName(name+" 流程定义");
					getDefinitions().getDiagrams().add(bpmnDiagram);
				}

				
			});
		}
		return bpmnDiagram;
	}
	
	
	
	public SkipStrategy createSkipStrategy() {
		// TODO Auto-generated method stub
		SkipStrategy SkipStrategy=FixFlowFactory.eINSTANCE.createSkipStrategy();
		
		return SkipStrategy;
	}

	
	public PotentialOwner createPotentialOwner(){
		PotentialOwner potentialOwner = Bpmn2Factory.eINSTANCE.createPotentialOwner();
		
//		FeatureMap.Entry extensionAttributeEntry = new SimpleFeatureMapEntry((Internal) FixFlowPackage.Literals.DOCUMENT_ROOT__RESOURCE_TYPE, typecombo.getData(typecombo.getSelectionIndex()+"").toString());
		FeatureMap.Entry extensionAttributeEntry = new SimpleFeatureMapEntry((Internal) FixFlowPackage.Literals.DOCUMENT_ROOT__RESOURCE_TYPE, "user");
		potentialOwner.getAnyAttribute().add(extensionAttributeEntry);
		FeatureMap.Entry extensionAttributeEntry1 = new SimpleFeatureMapEntry((Internal) FixFlowPackage.Literals.DOCUMENT_ROOT__INCLUDE_EXCLUSION, "INCLUDE");
		potentialOwner.getAnyAttribute().add(extensionAttributeEntry1);
		FeatureMap.Entry extensionAttributeEntry2 = new SimpleFeatureMapEntry((Internal) FixFlowPackage.Literals.DOCUMENT_ROOT__IS_CONTAINS_SUB, "false");
		potentialOwner.getAnyAttribute().add(extensionAttributeEntry2);
		
		ResourceAssignmentExpression resourceAssignmentExpression = Bpmn2Factory.eINSTANCE.createResourceAssignmentExpression();
		/*FormalExpression formalExpression = ModelHandler.FACTORY.createFormalExpression();
		
		formalExpression.setBody(expcombo.getText());
		resourceAssignmentExpression.setExpression(formalExpression);*/
		
			FormalExpression formalExpression = Bpmn2Factory.eINSTANCE.createFormalExpression();
			
			
			formalExpression.setId("所有人");
			formalExpression.setBody("\"fixflow_allusers\"");
			resourceAssignmentExpression.setExpression(formalExpression);
		
	
		
		potentialOwner.setName("所有人");
		potentialOwner.setResourceAssignmentExpression(resourceAssignmentExpression);
		
		return potentialOwner;
	}
	
	public TaskCommand createTaskCommand(){
		
		TaskCommand taskCommand = FixFlowFactory.eINSTANCE.createTaskCommand();
		taskCommand.setId("Advance_1");
		taskCommand.setName("启动提交");
		taskCommand.setCommandType("startandsubmit");
		/*Expression expression = FixFlowFactory.eINSTANCE.createExpression();
		expression.setValue((DataVarTo)comboViewer.getElementAt(comboViewer.getCombo().getSelectionIndex()) == null ? "" : ((DataVarTo)comboViewer.getElementAt(comboViewer.getCombo().getSelectionIndex())).getValue());
		taskCommand.setExpression(expression);*/
		return taskCommand;
		
	}
	
	public TaskCommand createTaskViewCommand(){
		
		TaskCommand taskCommand = FixFlowFactory.eINSTANCE.createTaskCommand();
		taskCommand.setId("Advance_ProcessStatus");
		taskCommand.setName("流程状态");
		taskCommand.setCommandType("processStatus");
		/*Expression expression = FixFlowFactory.eINSTANCE.createExpression();
		expression.setValue((DataVarTo)comboViewer.getElementAt(comboViewer.getCombo().getSelectionIndex()) == null ? "" : ((DataVarTo)comboViewer.getElementAt(comboViewer.getCombo().getSelectionIndex())).getValue());
		taskCommand.setExpression(expression);*/
		return taskCommand;
		
	}
	
	public AssignPolicyType createAssignPolicyType(){
		
		
		AssignPolicyType assignPolicyType=FixFlowFactory.eINSTANCE.createAssignPolicyType();
		assignPolicyType.setId("potentialOwner");
		
	
		return assignPolicyType;
		
	}
	
	

}

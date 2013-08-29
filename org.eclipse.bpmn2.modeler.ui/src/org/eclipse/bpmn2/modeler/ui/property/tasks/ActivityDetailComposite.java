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


package org.eclipse.bpmn2.modeler.ui.property.tasks;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.CallableElement;
import org.eclipse.bpmn2.Choreography;
import org.eclipse.bpmn2.Collaboration;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.LoopCharacteristics;
import org.eclipse.bpmn2.MultiInstanceLoopCharacteristics;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.StandardLoopCharacteristics;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNPlane;
import org.eclipse.bpmn2.di.BpmnDiFactory;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractPropertiesProvider;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.PropertiesCompositeFactory;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ComboObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ObjectEditor;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class ActivityDetailComposite extends DefaultDetailComposite {

	protected Button noneButton;
	protected Button addStandardLoopButton;
	protected Button addMultiLoopButton;
	protected AbstractDetailComposite loopCharacteristicsComposite;
	
	public ActivityDetailComposite(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * @param section
	 */
	public ActivityDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	@Override
	public void cleanBindings() {
		super.cleanBindings();
		noneButton = null;
		addStandardLoopButton = null;
		addMultiLoopButton = null;
		loopCharacteristicsComposite = null;
	}
	
	@Override
	public AbstractPropertiesProvider getPropertiesProvider(EObject object) {
		if (propertiesProvider==null) {
			propertiesProvider = new AbstractPropertiesProvider(object) {
				String[] properties = new String[] {
						"anyAttribute",
						"calledElementRef", // only used in CallActivity
						"calledChoreographyRef", // only used in CallChoreography
						"calledCollaborationRef", // only used in CallConversation
						"implementation", // used by BusinessRuleTask, SendTask, ReceiveTask, UserTask and ServiceTask
						"operationRef", // SendTask, ReceiveTask, ServiceTask
						"messageRef", // SendTask, ReceiveTask
						"instantiate", // ReceiveTask
						"isForCompensation",
						"script", "scriptFormat", // ScriptTask
						"triggeredByEvent",
						"cancelRemainingInstances",
						"properties",
						"resources",
						"method",
						"ordering",
						"protocol",
						//"startQuantity", // these are "Advanced" features and should be used
						//"completionQuantity", // with caution, according to the BPMN 2.0 spec
						"completionCondition",
						"loopCharacteristics",
				};
				
				@Override
				public String[] getProperties() {
					return properties; 
				}
			};
		}
		return propertiesProvider;
	}

	protected void bindReference(Composite parent, EObject object, EReference reference) {
		if (!isModelObjectEnabled(object.eClass(), reference))
			return;
		
		if ("loopCharacteristics".equals(reference.getName())) {
			final Activity activity = (Activity) businessObject;
			LoopCharacteristics loopCharacteristics = (LoopCharacteristics) activity.getLoopCharacteristics();
				
			Composite composite = getAttributesParent();

			createLabel(composite, "Loop Characteristics:");
			
			Composite buttonComposite = toolkit.createComposite(composite);
			buttonComposite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
			FillLayout layout = new FillLayout();
			layout.marginWidth = 20;
			buttonComposite.setLayout(layout);
			
			noneButton = toolkit.createButton(buttonComposite, "None", SWT.RADIO);
			noneButton.setSelection(loopCharacteristics == null);
			noneButton.addSelectionListener(new SelectionAdapter() {
				
				public void widgetSelected(SelectionEvent e) {
					if (noneButton.getSelection()) {
						@SuppressWarnings("restriction")
						TransactionalEditingDomain domain = getDiagramEditor().getEditingDomain();
						domain.getCommandStack().execute(new RecordingCommand(domain) {
							@Override
							protected void doExecute() {
								if (activity.getLoopCharacteristics() !=null)
									activity.setLoopCharacteristics(null);
								setBusinessObject(activity);
							}
						});
					}
				}
			});
			
			addStandardLoopButton = toolkit.createButton(buttonComposite, "Standard", SWT.RADIO);
			addStandardLoopButton.setSelection(loopCharacteristics instanceof StandardLoopCharacteristics);
			addStandardLoopButton.addSelectionListener(new SelectionAdapter() {
				
				public void widgetSelected(SelectionEvent e) {
					if (addStandardLoopButton.getSelection()) {
						@SuppressWarnings("restriction")
						TransactionalEditingDomain domain = getDiagramEditor().getEditingDomain();
						domain.getCommandStack().execute(new RecordingCommand(domain) {
							@Override
							protected void doExecute() {
								StandardLoopCharacteristics loopChar = createModelObject(StandardLoopCharacteristics.class);
								activity.setLoopCharacteristics(loopChar);
								setBusinessObject(activity);
							}
						});
					}
				}
			});

			addMultiLoopButton = toolkit.createButton(buttonComposite, "Multi-Instance", SWT.RADIO);
			addMultiLoopButton.setSelection(loopCharacteristics instanceof MultiInstanceLoopCharacteristics);
			addMultiLoopButton.addSelectionListener(new SelectionAdapter() {
				
				public void widgetSelected(SelectionEvent e) {
					if (addMultiLoopButton.getSelection()) {
						@SuppressWarnings("restriction")
						TransactionalEditingDomain domain = getDiagramEditor().getEditingDomain();
						domain.getCommandStack().execute(new RecordingCommand(domain) {
							@Override
							protected void doExecute() {
								MultiInstanceLoopCharacteristics loopChar = createModelObject(MultiInstanceLoopCharacteristics.class);
								activity.setLoopCharacteristics(loopChar);
								setBusinessObject(activity);
							}
						});
					}
				}
			});
			
			if (loopCharacteristics != null) {
				loopCharacteristicsComposite = PropertiesCompositeFactory.createDetailComposite(
						loopCharacteristics.eClass().getInstanceClass(), composite, SWT.NONE);
				loopCharacteristicsComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
				loopCharacteristicsComposite.setBusinessObject(loopCharacteristics);
				loopCharacteristicsComposite.setTitle(loopCharacteristics instanceof StandardLoopCharacteristics ?
						"Standard Loop Characteristics" : "Multi-Instance Loop Characteristics");
			}
			else if (loopCharacteristicsComposite!=null) {
				loopCharacteristicsComposite.dispose();
				loopCharacteristicsComposite = null;
			}

		}
		else if ("calledElementRef".equals(reference.getName())) {
			// Handle CallActivity.calledElementRef
			//
			String displayName = getPropertiesProvider().getLabel(object, reference);
			ObjectEditor editor = new ComboObjectEditor(this,object,reference) {
				// handle creation of new target elements here:
				protected EObject createObject() throws Exception {
					CallableElement calledElement = (CallableElement)super.createObject();
					// create a new diagram for the CallableElement
					if (calledElement instanceof Process) {
						createNewDiagram(calledElement);
					}
					return calledElement;
				}
			};
			editor.createControl(parent,displayName);
		}
		else if ("calledChoreographyRef".equals(reference.getName())) {
			// Handle CallChoreography.calledChoreographyRef
			//
			// FIXME: This section should really be in a different detail composite class.
			// This detail composite is intended for Activity elements and their subclasses
			// but a CallChoreography is a ChoreographyActivity, not a subclass of Activity.
			// See the "static" initializers section of BPMN2Editor.
			// For now, this will have to do...
			String displayName = getPropertiesProvider().getLabel(object, reference);
			ObjectEditor editor = new ComboObjectEditor(this,object,reference) {
				// handle creation of new target elements here:
				protected EObject createObject() throws Exception {
					Choreography choreography = (Choreography)super.createObject();
					// create a new diagram for the Choreography
					createNewDiagram(choreography);
					return choreography;
				}
			};
			editor.createControl(parent,displayName);
		}
		else if ("calledCollaborationRef".equals(reference.getName())) {
			// Handle CallConversation.calledCollaborationRef
			//
			// FIXME: This section should really be in a different detail composite class.
			// This detail composite is intended for Activity elements and their subclasses
			// but a CallConversation is a ChoreographyNode, not a subclass of Activity.
			// See the "static" initializers section of BPMN2Editor.
			// For now, this will have to do...
			String displayName = getPropertiesProvider().getLabel(object, reference);
			ObjectEditor editor = new ComboObjectEditor(this,object,reference) {
				// handle creation of new target elements here:
				protected EObject createObject() throws Exception {
					Collaboration collaboration = (Collaboration)super.createObject();
					// create a new diagram for the Collaboration
					createNewDiagram(collaboration);
					return collaboration;
				}
			};
			editor.createControl(parent,displayName);
		}
		else
			super.bindReference(parent, object, reference);
		
		redrawPage();
	}
	
	private void createNewDiagram(final BaseElement bpmnElement) {
		final Definitions definitions = ModelUtil.getDefinitions(bpmnElement);
		final String name = ModelUtil.getName(bpmnElement);
		
		editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {
			@Override
			protected void doExecute() {
				BPMNPlane plane = BpmnDiFactory.eINSTANCE.createBPMNPlane();
				plane.setBpmnElement(bpmnElement);
				
				BPMNDiagram diagram = BpmnDiFactory.eINSTANCE.createBPMNDiagram();
				diagram.setPlane(plane);
				diagram.setName(name);
				definitions.getDiagrams().add(diagram);
				
				ModelUtil.setID(plane);
				ModelUtil.setID(diagram);
			}
		});
	}
}
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

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.CatchEvent;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.ThrowEvent;
import org.eclipse.bpmn2.UserTask;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ComboObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.IntObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ObjectEditor;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ControlParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.CostParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ElementParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.FloatingParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NormalDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.Parameter;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ParameterValue;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.PoissonDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ResourceParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.Scenario;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.TimeParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.UniformDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BPSimDataType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.util.JbpmModelUtil;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.util.JbpmModelUtil.DistributionType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;

/**
 * This Property Tab contains the Simulation parameters for each type of 
 * BPMN2 element selected. Simulation parameters are available for the
 * following BPMN2 elements:
 * 
 *   BPMNDiagram
 *   Task
 *   CatchEvent
 *   ThrowEvent
 *   SequenceFlow
 *   
 * The structure of the extension values in the BPMN2 Relationship container
 * is TBD
 * 
 * ScenarioParameters (for BPMNDiagram elements, there may be more than one of these for each diagram):
 *   baseCurrency [text]
 *   baseTimeUnit [milliseconds, seconds, minutes, hours, days, years]
 *   
 * ElementParameters (for each of the flow elements):
 *   Tasks:
 *     costPerTimeUnit
 *     distributionType [see below]
 *   SequenceFlows:
 *     probability
 *   Catch Events:
 *     timeUnit
 *     waitTime
 *   Throw Events:
 *     distributionType [see below]
 *  
 *  Different parameters are required for each of the distribution types
 *   distributionType [normal, uniform, random, poisson]
 *     [random, uniform]:
 *       processingTime(max)
 *       processingTime(min)
 *       timeUnit
 *     [normal]:
 *       processingTime(mean)
 *       standardDeviation
 *       timeUnit
 *     [poisson]:
 *       processingTime(mean)
 *       timeUnit
 */
public class SimulationDetailComposite extends DefaultDetailComposite {

	/**
	 * @param section
	 */
	public SimulationDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	public SimulationDetailComposite(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void cleanBindings() {
		super.cleanBindings();
	}

	@Override
	protected boolean isModelObjectEnabled(String className, String featureName) {
		return true;
	}

	@Override
	public void createBindings(EObject be) {
		setTitle( ModelUtil.getLabel(be.eClass())+Messages.SimulationDetailComposite_Title);
		if (be instanceof BPMNDiagram)
		{
			BPSimDataType pad = JbpmModelUtil.getBPSimData(be);
			Scenario scenario = pad.getScenario().get(0);
			scenario.getScenarioParameters().getBaseTimeUnit();
			bindAttribute(getAttributesParent(), scenario,
					BpsimPackage.eINSTANCE.getScenario_Name(),
					Messages.SimulationDetailComposite_Scenario);
			bindAttribute(getAttributesParent(), scenario.getScenarioParameters(),
					BpsimPackage.eINSTANCE.getScenarioParameters_BaseTimeUnit(),
					Messages.SimulationDetailComposite_Base_Time_Units);
			bindAttribute(getAttributesParent(), scenario.getScenarioParameters(),
					BpsimPackage.eINSTANCE.getScenarioParameters_BaseCurrencyUnit(),
					Messages.SimulationDetailComposite_Base_Currency_Units);
		}
		else if (be instanceof Task) {
			ElementParameters elementParams = JbpmModelUtil.getElementParameters((BaseElement)be);
			bindProcessingTime(elementParams);
			if (be instanceof UserTask) {
				ResourceParameters resourceParams = elementParams.getResourceParameters();
				Parameter param = resourceParams.getQuantity();
				FloatingParameterType quantity = (FloatingParameterType) param.getParameterValue().get(0);
				bindAttribute(getAttributesParent(), quantity,
						BpsimPackage.eINSTANCE.getFloatingParameterType_Value(),
						Messages.SimulationDetailComposite_Quantity);

				param = resourceParams.getAvailability();
				FloatingParameterType workingHours = (FloatingParameterType) param.getParameterValue().get(0);
				bindAttribute(getAttributesParent(), workingHours,
						BpsimPackage.eINSTANCE.getFloatingParameterType_Value(),
						Messages.SimulationDetailComposite_Availability);
			}
		}
		else if (be instanceof CatchEvent) {
			// TimeParameters = wait time and time unit
			ElementParameters elementParams = JbpmModelUtil.getElementParameters((BaseElement)be);
			TimeParameters timeParams = elementParams.getTimeParameters();
			Parameter param = timeParams.getWaitTime();
			FloatingParameterType waitTime = (FloatingParameterType) param.getParameterValue().get(0);
			bindAttribute(getAttributesParent(), waitTime,
					BpsimPackage.eINSTANCE.getFloatingParameterType_Value(),
					Messages.SimulationDetailComposite_Wait_Time);
		}
		else if (be instanceof ThrowEvent) {
			// TimeParameters = distribution type
			ElementParameters elementParams = JbpmModelUtil.getElementParameters((BaseElement)be);
			bindProcessingTime(elementParams);
		}
		else if (be instanceof SequenceFlow) {
			ElementParameters elementParams = JbpmModelUtil.getElementParameters((BaseElement)be);
			ControlParameters controlParams = elementParams.getControlParameters();
			Parameter param = controlParams.getProbability();
			FloatingParameterType probability = (FloatingParameterType) param.getParameterValue().get(0);
			bindAttribute(getAttributesParent(), probability,
					BpsimPackage.eINSTANCE.getFloatingParameterType_Value(),
					Messages.SimulationDetailComposite_Probability);
		}
	}
	
	private void bindProcessingTime(final ElementParameters elementParams) {
		Parameter param;
		ParameterValue value;
		ObjectEditor editor;
		final TimeParameters timeParams = elementParams.getTimeParameters();
		final CostParameters costParams = elementParams.getCostParameters();
		
		if (costParams!=null) {
			param = costParams.getUnitCost();
			final FloatingParameterType decimalValue = (FloatingParameterType)param.getParameterValue().get(0);
			editor = new IntObjectEditor(this,decimalValue,BpsimPackage.eINSTANCE.getFloatingParameterType_Value()) {

				@Override
				protected boolean setValue(final Object result) {
					TransactionalEditingDomain domain = getDiagramEditor().getEditingDomain();
					domain.getCommandStack().execute(new RecordingCommand(domain) {
						@Override
						protected void doExecute() {
							decimalValue.setValue(Double.valueOf((Double)result));
						}
					});
					return true;
				}
				
			};
			editor.createControl(getAttributesParent(),Messages.SimulationDetailComposite_Cost_Per_Time_Unit);
		}
		
		editor = new ComboObjectEditor(this,
				timeParams,
				BpsimPackage.eINSTANCE.getTimeParameters_ProcessingTime()) {
			
				Hashtable<String,Object> choices = null;

					@Override
					protected boolean canEdit() {
						return false;
					}

					@Override
					protected boolean canEditInline() {
						return false;
					}

					@Override
					protected boolean canCreateNew() {
						return false;
					}
					
					protected boolean canSetNull() {
						return false;
					}
					
					@Override
					protected boolean setValue(Object result) {
						final DistributionType dt = (DistributionType)result;
						TransactionalEditingDomain domain = getDiagramEditor().getEditingDomain();
						domain.getCommandStack().execute(new RecordingCommand(domain) {
							@Override
							protected void doExecute() {
								timeParams.setProcessingTime( JbpmModelUtil.createParameter(dt, 0.0, 0.0) ); 
							}
						});
						setBusinessObject( getBusinessObject() );
						return true;
					}

					@Override
					protected Hashtable<String, Object> getChoiceOfValues(EObject object, EStructuralFeature feature) {
						if (choices==null) {
							choices = new Hashtable<String,Object>();
							for (DistributionType dt : JbpmModelUtil.DistributionType.values()) {
								choices.put(dt.name(), dt);
							}
						}
						return choices;
					}

					protected void fillCombo() {
						super.fillCombo();
						Parameter param = timeParams.getProcessingTime();
						ParameterValue value = param.getParameterValue().get(0);
						StructuredSelection currentSelection = null;
						if (value instanceof UniformDistributionType) {
							currentSelection = new StructuredSelection(JbpmModelUtil.DistributionType.Uniform.name());
						}
						else if (value instanceof NormalDistributionType) {
							currentSelection = new StructuredSelection(JbpmModelUtil.DistributionType.Normal.name());
						}
						else if (value instanceof PoissonDistributionType) {
							currentSelection = new StructuredSelection(JbpmModelUtil.DistributionType.Poisson.name());
						}
						if (currentSelection!=null)
							comboViewer.setSelection(currentSelection);
					}
			
		};
		editor.createControl(getAttributesParent(),Messages.SimulationDetailComposite_Distribution_Type);
		
		param = timeParams.getProcessingTime();
		value = param.getParameterValue().get(0);
		if (value instanceof UniformDistributionType) {
			bindAttribute(getAttributesParent(), value,
					BpsimPackage.eINSTANCE.getUniformDistributionType_Min(),
					Messages.SimulationDetailComposite_Processing_Time_Min);
			bindAttribute(getAttributesParent(), value,
					BpsimPackage.eINSTANCE.getUniformDistributionType_Max(),
					Messages.SimulationDetailComposite_Processing_Time_Max);
		}
		else if (value instanceof NormalDistributionType) {
			bindAttribute(getAttributesParent(), value,
					BpsimPackage.eINSTANCE.getNormalDistributionType_Mean(),
					Messages.SimulationDetailComposite_Processing_Time_Mean);
			bindAttribute(getAttributesParent(), value,
					BpsimPackage.eINSTANCE.getNormalDistributionType_StandardDeviation(),
					Messages.SimulationDetailComposite_Standard_Deviation);
		}
		else if (value instanceof PoissonDistributionType) {
			bindAttribute(getAttributesParent(), value,
					BpsimPackage.eINSTANCE.getPoissonDistributionType_Mean(),
					Messages.SimulationDetailComposite_Processing_Time_Mean);
		}
	}
}

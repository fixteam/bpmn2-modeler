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

import java.util.List;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.Assignment;
import org.eclipse.bpmn2.CatchEvent;
import org.eclipse.bpmn2.DataAssociation;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.DataInputAssociation;
import org.eclipse.bpmn2.DataOutput;
import org.eclipse.bpmn2.DataOutputAssociation;
import org.eclipse.bpmn2.Expression;
import org.eclipse.bpmn2.FormalExpression;
import org.eclipse.bpmn2.InputOutputSpecification;
import org.eclipse.bpmn2.ItemAwareElement;
import org.eclipse.bpmn2.MultiInstanceLoopCharacteristics;
import org.eclipse.bpmn2.ThrowEvent;
import org.eclipse.bpmn2.modeler.core.adapters.InsertionAdapter;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractPropertiesProvider;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultListComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.ListCompositeColumnProvider;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.PropertiesCompositeFactory;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.TableColumn;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ComboObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ObjectEditor;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.property.data.ItemAwareElementDetailComposite;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.forms.widgets.Section;

/**
 * This class renders the property sheet tab for Data I/O Associations (a.k.a. parameter mappings)
 * defined in Activities and ThrowEvents.
 * 
 * The DataInput/OutputAssociation can be used to associate an ItemAwareElement
 * parameter with a DataInput/Output contained in an Activity. The source of such
 * a DataAssociation can be every ItemAwareElement accessible in the
 * current scope, e.g., a Data Object, a Property, or an Expression.
 * 
 * The execution of any Data Associations MUST follow these semantics:
 *  o If the Data Association specifies a �transformation� Expression,
 *    this expression is evaluated and the result is copied to the targetRef.
 *    This operation replaces completely the previous value of the targetRef parameter.
 *  o For each �assignment� parameter specified:
 *    o Evaluate the Assignment�s �from� expression and obtain the *source value*.
 *    o Evaluate the Assignment�s �to� expression and obtain the *target parameter*.
 *      The *target parameter* can be any parameter in the context or a sub-parameter of
 *      it (e.g., a DataObject or a sub-parameter of it).
 *    o Copy the *source value* to the *target parameter*.
 *  o If no �transformation� Expression nor any �assignment� elements are defined
 *    in the Data Association:
 *    o Copy the Data Association �sourceRef� value into the �targetRef.� Only one
 *      sourceRef parameter is allowed in this case.					
 */
public class DataAssociationDetailComposite extends ItemAwareElementDetailComposite {
	
	public enum MapType {
		None,
		Property,
		Transformation,
		Expression,
		Advanced
	};

	protected ItemAwareElement parameter;
	protected String parameterName;
	protected DataAssociation association;
	protected boolean isInput;
	protected boolean updatingWidgets;
	
	protected Group fromGroup;
	protected Group toGroup;
	protected Button mapPropertyButton;
	protected Button mapExpressionButton;
	protected Button mapTransformationButton;
	protected Button advancedMappingButton;
	// holds the Transformation expression details and Assignments table
	protected Composite transformationComposite;
	protected AbstractDetailComposite transformationDetailsComposite;
	protected Composite expressionComposite;
	protected AbstractDetailComposite expressionDetailsComposite;
	protected AssignmentListComposite assignmentsTable;
	// holds the Property details
	protected Composite propertyComposite;
	protected DefaultDetailComposite propertyDetailsComposite;
	protected boolean propertyWidgetsShowing = false;
	protected boolean expressionWidgetsShowing = false;
	protected boolean transformationWidgetsShowing = false;
	protected boolean advancedMappingWidgetsShowing = false;
	
	public class DataInputOutputDetailComposite extends ItemAwareElementDetailComposite {

		public DataInputOutputDetailComposite(Composite parent, int style) {
			super(parent, style);
		}
		
		public Composite getAttributesParent() {
			return this;
		}
	}
	
	public DataAssociationDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	public DataAssociationDetailComposite(Composite parent, int style) {
		super(parent, style);
	}
	
	@Override
	protected void cleanBindings() {
		super.cleanBindings();
		mapPropertyButton = null;
		mapExpressionButton = null;
		mapTransformationButton = null;
		advancedMappingButton = null;
		propertyComposite = null;
		propertyDetailsComposite = null;
		transformationComposite = null;
		transformationDetailsComposite = null;
		expressionComposite = null;
		expressionDetailsComposite = null;
		assignmentsTable = null;
		propertyWidgetsShowing = false;
		expressionWidgetsShowing = false;
		transformationWidgetsShowing = false;
		advancedMappingWidgetsShowing = false;
	}
	
	@Override
	public void createBindings(EObject be) {

		String sectionTitle = "";
		
		association = null;
		if (be instanceof DataInput) {
			isInput = true;
			parameterName = ((DataInput)be).getName();
		}
		else if (be instanceof DataOutput) {
			isInput = false;
			parameterName = ((DataOutput)be).getName();
		}
		else {
			createWidgets();
			return;
		}
		parameter = (ItemAwareElement)be;

		fromGroup = new Group(this, SWT.NONE);
		fromGroup.setText("From");
		fromGroup.setLayout(new GridLayout(3,false));
		fromGroup.setLayoutData(new GridData(SWT.FILL,SWT.TOP,true,false,3,1));

		toGroup = new Group(this, SWT.NONE);
		toGroup.setText("To");
		toGroup.setLayout(new GridLayout(3,false));
		toGroup.setLayoutData(new GridData(SWT.FILL,SWT.TOP,true,false,3,1));

		final Group group = isInput ? toGroup : fromGroup;

		Activity activity = null;
		List<? extends DataAssociation> associations = null;
		EObject container = ModelUtil.getContainer(be);
		if (container instanceof InputOutputSpecification || container instanceof MultiInstanceLoopCharacteristics) {
			EObject containerContainer = ModelUtil.getContainer(container);
			if (containerContainer instanceof Activity) {
				activity = (Activity)containerContainer;
				if (isInput)
					associations = activity.getDataInputAssociations();
				else
					associations = activity.getDataOutputAssociations();
			}
			else {
				super.createBindings(be);
				return;
			}
			DataInputOutputDetailComposite details = createDataInputOutputDetailComposite(be, group,SWT.NONE);
			details.setBusinessObject(be);
			sectionTitle = (isInput ? "Input" : "Output") +
				" Parameter Mapping Details";
		}
		else if (container instanceof ThrowEvent) {
			ThrowEvent throwEvent = (ThrowEvent)container;
			associations = throwEvent.getDataInputAssociation();
			if (associations.size()==0) {
				association = FACTORY.createDataInputAssociation();
				association.setTargetRef((ItemAwareElement) be);
				InsertionAdapter.add(throwEvent, PACKAGE.getThrowEvent_DataInputAssociation(), association);
			}
			DataInputOutputDetailComposite details = createDataInputOutputDetailComposite(be, group,SWT.NONE);
			details.setBusinessObject(be);
			sectionTitle = "Data Input Mapping Details";
		}
		else if (container instanceof CatchEvent) {
			CatchEvent catchEvent = (CatchEvent)container;
			associations = catchEvent.getDataOutputAssociation();
			if (associations.size()==0) {
				association = FACTORY.createDataOutputAssociation();
				association.getSourceRef().add((ItemAwareElement) be);
				InsertionAdapter.add(catchEvent, PACKAGE.getCatchEvent_DataOutputAssociation(), association);
			}
			DataInputOutputDetailComposite details = createDataInputOutputDetailComposite(be, group,SWT.NONE);
			details.setBusinessObject(be);
			sectionTitle = "Data Output Mapping Details";
		}
		else {
			super.createBindings(be);
			return;
		}
		
		
		// set section title
		if (getParent() instanceof Section) {
			((Section)getParent()).setText(sectionTitle);
		}

		if (associations!=null) {
			for (DataAssociation a : associations) {
				if (isInput) {
					if (a.getTargetRef() == be) {
						association = a;
						break;
					}
				}
				else
				{
					for (ItemAwareElement e : a.getSourceRef()) {
						if (e == be) {
							association = a;
							break;
						}
					}
					if (association!=null)
						break;
				}
			}
			if (association==null && activity!=null) {
				// create a new DataAssociation
				if (isInput) {
					association = FACTORY.createDataInputAssociation();
					association.setTargetRef((ItemAwareElement) be);
					InsertionAdapter.add(activity, PACKAGE.getActivity_DataInputAssociations(), association);
				}
				else {
					association = FACTORY.createDataOutputAssociation();
					association.getSourceRef().add((ItemAwareElement) be);
					InsertionAdapter.add(activity, PACKAGE.getActivity_DataOutputAssociations(), association);
				}
			}
		}
		createWidgets();
	}
	
	/**
	 * Create the composite which supports editing the DataInput/DataOutput details.
	 * 
	 * @param be the selected business object
	 * @param parent the parent composite
	 * @param style SWT style bits
	 * @return a new DataInputOutputDetailComposite.
	 */
	protected DataInputOutputDetailComposite createDataInputOutputDetailComposite(EObject be, Composite parent, int style) {
	    return new DataInputOutputDetailComposite(parent, style);
	}

	private MapType getMapType() {
		if (association!=null) {
			if (association.getAssignment().size()>1) {
				return MapType.Advanced;
			}
			if (association.getTransformation()!=null) {
				if (association.getAssignment().size()>0) {
					return MapType.Advanced;
				}
				return MapType.Transformation;
			}
			if (association.getAssignment().size()==1) {
				return MapType.Expression;
			}
			if (isInput) {
				if (association.getTargetRef()!=null) {
					return MapType.Property;
				}
			}
			else {
				if (association.getSourceRef().size()>0) {
					return MapType.Property;
				}
			}
		}
		return MapType.None;
	}
	
	private void redrawParent() {
		// this DetailComposite should be sitting in a SashForm created
		// by a ListComposite. layout this thing first
		layout();
		// and then search for the DetailComposite that contains the list 
		Composite parent = getParent();
		while (parent!=null) {
			parent = parent.getParent();
			if (parent instanceof AbstractDetailComposite) {
				parent.layout();
				break;
			}
		}
        if (getTabbedPropertySheetPage() != null) {
            getTabbedPropertySheetPage().resizeScrolledComposite();
        }
	}

	private void updateWidgets() {
		if (association!=null && !updatingWidgets) {
			updatingWidgets = true;

			switch (getMapType()) {
			case None:
				mapPropertyButton.setSelection(false);
				mapTransformationButton.setSelection(false);
				mapExpressionButton.setSelection(false);
				advancedMappingButton.setSelection(false);
				showPropertyWidgets(false);
				showTransformationWidgets(false);
				showExpressionWidgets(false);
				showAdvancedMappingWidgets(false);
				break;
			case Property:
				mapTransformationButton.setSelection(false);
				mapExpressionButton.setSelection(false);
				advancedMappingButton.setSelection(false);
				showTransformationWidgets(false);
				showExpressionWidgets(false);
				showAdvancedMappingWidgets(false);
				
				mapPropertyButton.setSelection(true);
				showPropertyWidgets(true);
				break;
			case Transformation:
				mapPropertyButton.setSelection(false);
				mapExpressionButton.setSelection(false);
				advancedMappingButton.setSelection(false);
				showPropertyWidgets(false);
				showExpressionWidgets(false);
				showAdvancedMappingWidgets(false);

				mapTransformationButton.setSelection(true);
				showTransformationWidgets(true);
				break;
			case Expression:
				mapPropertyButton.setSelection(false);
				mapTransformationButton.setSelection(false);
				advancedMappingButton.setSelection(false);
				showPropertyWidgets(false);
				showTransformationWidgets(false);
				showAdvancedMappingWidgets(false);

				mapExpressionButton.setSelection(true);
				showExpressionWidgets(true);
				break;
			case Advanced:
				mapPropertyButton.setSelection(false);
				mapTransformationButton.setSelection(false);
				mapExpressionButton.setSelection(false);
				showPropertyWidgets(false);
				showTransformationWidgets(false);
				showExpressionWidgets(false);
				
				advancedMappingButton.setSelection(true);
				showAdvancedMappingWidgets(true);
				break;
			}
			updatingWidgets = false;
		}
	}
	
	private void createWidgets() {
		
		if (association==null && !(businessObject instanceof DataInput || businessObject instanceof DataOutput)) {
			this.createLabel(this, "The I/O Parameter \""+parameterName+"\" can not have Mappings.");
			return;
		} else {
			EObject container = ModelUtil.getContainer(businessObject);
			if (container instanceof InputOutputSpecification) {
				EObject containerContainer = ModelUtil.getContainer(container);
				if (containerContainer instanceof Activity) {
					Activity activity = (Activity)containerContainer;

					if (association == null) {
						// if no DataAssociation was found for this Activity, create a new one
						// and add it to the Activity's DataInput/OutputAssociations list using
						// an InsertionAdapter.
						if (isInput) {
							DataInputAssociation diAssociation = modelHandler.create(DataInputAssociation.class);
							diAssociation.setTargetRef(parameter);
							association = diAssociation;
							InsertionAdapter.add(
									activity,
									PACKAGE.getActivity_DataInputAssociations(),
									association);
						}
						else {
							DataOutputAssociation doAssociation = modelHandler.create(DataOutputAssociation.class);
							doAssociation.getSourceRef().add(parameter);
							association = doAssociation;
							InsertionAdapter.add(
									activity,
									PACKAGE.getActivity_DataInputAssociations(),
									association);
						}
					}
				}
			}
		}

		Group group = !isInput ? toGroup : fromGroup;
		if (mapPropertyButton==null) {
			mapPropertyButton = toolkit.createButton(group, "Variable", SWT.RADIO);
			mapPropertyButton.setLayoutData(new GridData(SWT.LEFT,SWT.TOP,true,false,3,1));

			mapPropertyButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (mapPropertyButton.getSelection()) {
						showTransformationWidgets(false);
						showExpressionWidgets(false);
						showAdvancedMappingWidgets(false);

						showPropertyWidgets(true);
						redrawParent();
					}
				}
			});
		}
		
		if (mapTransformationButton==null) {
			mapTransformationButton = toolkit.createButton(group, "Transformation", SWT.RADIO);
			mapTransformationButton.setLayoutData(new GridData(SWT.LEFT,SWT.TOP,true,false,3,1));
			
			mapTransformationButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (mapTransformationButton.getSelection()) {
						showPropertyWidgets(false);
						showExpressionWidgets(false);
						showAdvancedMappingWidgets(false);

						showTransformationWidgets(true);
						redrawParent();
					}
				}
			});
		}
		
		if (mapExpressionButton==null) {
			mapExpressionButton = toolkit.createButton(group, "Expression", SWT.RADIO);
			mapExpressionButton.setLayoutData(new GridData(SWT.LEFT,SWT.TOP,true,false,3,1));
			
			mapExpressionButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (mapExpressionButton.getSelection()) {
						showPropertyWidgets(false);
						showTransformationWidgets(false);
						showAdvancedMappingWidgets(false);

						showExpressionWidgets(true);
						redrawParent();
					}
				}
			});
		}

		if (advancedMappingButton==null) {
			advancedMappingButton = toolkit.createButton(group, "Advanced Mapping", SWT.RADIO);
			advancedMappingButton.setLayoutData(new GridData(SWT.LEFT,SWT.TOP,true,false,3,1));
			
			advancedMappingButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (advancedMappingButton.getSelection()) {
						showPropertyWidgets(false);
						showTransformationWidgets(false);
						showExpressionWidgets(false);
						
						showAdvancedMappingWidgets(true);
						redrawParent();
					}
				}
			});
		}
		
		updateWidgets();
		
	}
	
	private void showPropertyWidgets(boolean show) {
		final Group group = !isInput ? toGroup : fromGroup;
		if (show != propertyWidgetsShowing) {
			if (show) {
				if (propertyComposite==null) {
					propertyComposite = toolkit.createComposite(group, SWT.NONE);
					GridLayout layout = new GridLayout(3,false);
					layout.verticalSpacing = 0;
					layout.marginHeight = 0;
					propertyComposite.setLayout(layout);
					propertyComposite.setLayoutData(new GridData(SWT.FILL,SWT.TOP,true,true,3,1));
				}
				else {
					propertyComposite.setVisible(true);
					((GridData)propertyComposite.getLayoutData()).exclude = false;
				}
				
				if (propertyDetailsComposite==null) {
					propertyDetailsComposite = new DefaultDetailComposite(propertyComposite,SWT.NONE) {

						@Override
						public Composite getAttributesParent() {
							return this;
						}

						@Override
						public AbstractPropertiesProvider getPropertiesProvider(EObject object) {
							if (propertiesProvider == null) {
								propertiesProvider = new AbstractPropertiesProvider(object) {
									@Override
									public String[] getProperties() {
										String[] properties = null;
										if (isInput) {
											properties = new String[] {
													"sourceRef"
											};
										} else {
											properties = new String[] {
													"targetRef"
											};
										}
										return properties; 
									}
								};
							}
							return propertiesProvider;
						}

						@Override
						protected void bindReference(Composite parent, EObject object, EReference reference) {
							String displayName = ModelUtil.getLabel(object, reference);
							ObjectEditor editor = new ComboObjectEditor(this,object,reference);
							editor.createControl(parent,displayName);
						}
						
						protected boolean isEmpty() {
							return false;
						}						
					};
					propertyDetailsComposite.setBusinessObject(association);
					propertyDetailsComposite.setTitle("Variables");
				}
			}
			else {
				if (propertyComposite!=null) {
					propertyComposite.setVisible(false);
					((GridData)propertyComposite.getLayoutData()).exclude = true;
				}
				
				// remove the Property reference
				boolean clear = false;
				if (isInput)
					clear = association.getSourceRef().size()>0;
				else
					clear = association.getTargetRef()!=null;
				
				if (!updatingWidgets && clear) {
					editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {
						@Override
						protected void doExecute() {
							if (isInput)
								association.getSourceRef().clear();
							else
								association.setTargetRef(null);
						}
					});
				}
			}
			propertyWidgetsShowing = show;
		}
	}

	private void showTransformationWidgets(boolean show) {
		Group group = !isInput ? toGroup : fromGroup;
		if (show != transformationWidgetsShowing) {
			if (show) {
				if (transformationComposite==null) {
					transformationComposite = toolkit.createComposite(group, SWT.NONE);
					transformationComposite.setLayout(new GridLayout(1,false));
					transformationComposite.setLayoutData(new GridData(SWT.FILL,SWT.TOP,true,true,3,1));
				}
				else {
					transformationComposite.setVisible(true);
					((GridData)transformationComposite.getLayoutData()).exclude = false;
				}
				
				// create a new Transformation FormalExpression
				FormalExpression transformation = association.getTransformation();
				if (!updatingWidgets && transformation==null) {
					transformation = FACTORY.createFormalExpression();
					InsertionAdapter.add(association, PACKAGE.getDataAssociation_Transformation(), transformation);
				}
				if (transformationDetailsComposite==null) {
					transformationDetailsComposite = PropertiesCompositeFactory.createDetailComposite(
							Expression.class, transformationComposite, SWT.NONE);
				}
				transformationDetailsComposite.setBusinessObject(transformation);
				transformationDetailsComposite.setTitle("Transformation");
	
			}
			else {
				if (transformationComposite!=null) {
					transformationComposite.setVisible(false);
					((GridData)transformationComposite.getLayoutData()).exclude = true;
				}
				
				// remove the Transformation and assignments
				if (!updatingWidgets && association.getTransformation()!=null) {
					editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {
						@Override
						protected void doExecute() {
							association.setTransformation(null);
						}
					});
				}
			}
			transformationWidgetsShowing = show;
		}
	}

	private void showExpressionWidgets(boolean show) {
		Group group = !isInput ? toGroup : fromGroup;
		if (show != expressionWidgetsShowing) {
			if (show) {
				if (expressionComposite==null) {
					expressionComposite = toolkit.createComposite(group, SWT.NONE);
					expressionComposite.setLayout(new GridLayout(1,false));
					expressionComposite.setLayoutData(new GridData(SWT.FILL,SWT.TOP,true,true,3,1));
				}
				else {
					expressionComposite.setVisible(true);
					((GridData)expressionComposite.getLayoutData()).exclude = false;
				}
				
				// create a new Transformation FormalExpression
				FormalExpression expression = null;
				Assignment assignment = null;
				if (association.getAssignment().size()==1) {
					assignment = (Assignment) association.getAssignment().get(0);
					if (isInput)
						expression = (FormalExpression) assignment.getFrom();
					else
						expression = (FormalExpression) assignment.getTo();
				}
				if (!updatingWidgets) {
					if (assignment==null) {
						assignment = FACTORY.createAssignment();
						FormalExpression paramExpression = FACTORY.createFormalExpression();
						paramExpression.setBody(parameter.getId());
						if (isInput)
							assignment.setTo(paramExpression);
						else
							assignment.setFrom(paramExpression);
						InsertionAdapter.add(association, PACKAGE.getDataAssociation_Assignment(), assignment);
					}
					if (expression==null) {
						expression = FACTORY.createFormalExpression();
						if (isInput)
							InsertionAdapter.add(assignment, PACKAGE.getAssignment_From(), expression);
						else
							InsertionAdapter.add(assignment, PACKAGE.getAssignment_To(), expression);
					}
				}
	
				if (expressionDetailsComposite==null) {
					expressionDetailsComposite = PropertiesCompositeFactory.createDetailComposite(
							Expression.class, expressionComposite, SWT.NONE);
				}
				expressionDetailsComposite.setBusinessObject(expression);//association.getexpression());
				expressionDetailsComposite.setTitle("Expression");
			}
			else {
				if (expressionComposite!=null) {
					expressionComposite.setVisible(false);
					((GridData)expressionComposite.getLayoutData()).exclude = true;
				}
				
				// remove the Transformation and assignments
				if (!updatingWidgets && association.getAssignment().size()==1) {
					editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {
						@Override
						protected void doExecute() {
							association.getAssignment().clear();
						}
					});
				}
			}
			expressionWidgetsShowing = show;
		}
	}

	private void showAdvancedMappingWidgets(boolean show) {
		Group group = !isInput ? toGroup : fromGroup;
		if (show != advancedMappingWidgetsShowing) {
			if (show) {
				if (transformationComposite==null) {
					transformationComposite = toolkit.createComposite(group, SWT.NONE);
					transformationComposite.setLayout(new GridLayout(1,false));
					transformationComposite.setLayoutData(new GridData(SWT.FILL,SWT.TOP,true,true,3,1));
				}
				else {
					transformationComposite.setVisible(true);
					((GridData)transformationComposite.getLayoutData()).exclude = false;
				}
				
				// create a new Transformation FormalExpression
				FormalExpression transformation = association.getTransformation();
				if (!updatingWidgets && transformation==null) {
					transformation = FACTORY.createFormalExpression();
					InsertionAdapter.add(association, PACKAGE.getDataAssociation_Transformation(), transformation);
				}
	
				if (transformationDetailsComposite==null) {
					transformationDetailsComposite = PropertiesCompositeFactory.createDetailComposite(
							Expression.class, transformationComposite, SWT.NONE);
				}
				transformationDetailsComposite.setBusinessObject(transformation);//association.getTransformation());
				transformationDetailsComposite.setTitle("Transformation");
				
				if (assignmentsTable!=null)
					assignmentsTable.dispose();
				assignmentsTable = new AssignmentListComposite(transformationComposite);
				assignmentsTable.setLayoutData(new GridData(SWT.FILL,SWT.TOP,true,false,1,1));
				assignmentsTable.bindList(association, association.eClass().getEStructuralFeature("assignment"));
				assignmentsTable.setTitle("Assignments");
			}
			else {
				if (transformationComposite!=null) {
					transformationComposite.setVisible(false);
					((GridData)transformationComposite.getLayoutData()).exclude = true;
				}
				
				if (assignmentsTable!=null) {
					assignmentsTable.setVisible(false);
					((GridData)assignmentsTable.getLayoutData()).exclude = true;
				}
				
				// remove the Transformation and assignments
				if (!updatingWidgets && (association.getAssignment().size()>0 || association.getTransformation()!=null)) {
					editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {
						@Override
						protected void doExecute() {
							association.getAssignment().clear();
							association.setTransformation(null);
						}
					});
				}
			}
			advancedMappingWidgetsShowing = show;
		}
	}

	public class AssignmentListComposite extends DefaultListComposite {

		public AssignmentListComposite(Composite parent) {
			super(parent, DEFAULT_STYLE);
		}
		
		@Override
		public ListCompositeColumnProvider getColumnProvider(EObject object, EStructuralFeature feature) {
			columnProvider = new ListCompositeColumnProvider(this);
			
			columnProvider.add(new AssignmentsTableColumn(object,PACKAGE.getAssignment_To()));
			columnProvider.add(new AssignmentsTableColumn(object,PACKAGE.getAssignment_From()));

			return columnProvider;
		}

		public class AssignmentsTableColumn extends TableColumn {

			public AssignmentsTableColumn(EObject o, EStructuralFeature f) {
				super(o, f);
			}

			public String getText(Object element) {
				Object value = ((EObject)element).eGet(feature);
				if (value==null) {
					return "";
				}
				if (value instanceof FormalExpression) {
					FormalExpression exp = (FormalExpression)value;
					String body = exp.getBody();
					if (body==null||body.isEmpty())
						body = "<empty>";
					return body;
				}
				return value.toString();
			}
			
		}

	}
}

/**
 */
package org.eclipse.bpmn2.modeler.examples.customtask.MyModel;

import org.eclipse.bpmn2.BoundaryEvent;
import org.eclipse.bpmn2.FlowElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Temporal Dependency</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.TemporalDependency#getSourceRef <em>Source Ref</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.TemporalDependency#getTargetRef <em>Target Ref</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.TemporalDependency#getLagTime <em>Lag Time</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyModelPackage#getTemporalDependency()
 * @model
 * @generated
 */
public interface TemporalDependency extends FlowElement {
	/**
	 * Returns the value of the '<em><b>Source Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Ref</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Ref</em>' reference.
	 * @see #setSourceRef(BoundaryEvent)
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyModelPackage#getTemporalDependency_SourceRef()
	 * @model
	 * @generated
	 */
	BoundaryEvent getSourceRef();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.TemporalDependency#getSourceRef <em>Source Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Ref</em>' reference.
	 * @see #getSourceRef()
	 * @generated
	 */
	void setSourceRef(BoundaryEvent value);

	/**
	 * Returns the value of the '<em><b>Target Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Ref</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Ref</em>' reference.
	 * @see #setTargetRef(BoundaryEvent)
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyModelPackage#getTemporalDependency_TargetRef()
	 * @model
	 * @generated
	 */
	BoundaryEvent getTargetRef();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.TemporalDependency#getTargetRef <em>Target Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Ref</em>' reference.
	 * @see #getTargetRef()
	 * @generated
	 */
	void setTargetRef(BoundaryEvent value);

	/**
	 * Returns the value of the '<em><b>Lag Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lag Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lag Time</em>' attribute.
	 * @see #setLagTime(String)
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyModelPackage#getTemporalDependency_LagTime()
	 * @model
	 * @generated
	 */
	String getLagTime();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.TemporalDependency#getLagTime <em>Lag Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lag Time</em>' attribute.
	 * @see #getLagTime()
	 * @generated
	 */
	void setLagTime(String value);

} // TemporalDependency

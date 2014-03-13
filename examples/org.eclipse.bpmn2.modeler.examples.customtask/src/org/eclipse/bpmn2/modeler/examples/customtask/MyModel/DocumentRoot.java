/**
 */
package org.eclipse.bpmn2.modeler.examples.customtask.MyModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.DocumentRoot#getTaskConfig <em>Task Config</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.DocumentRoot#getTemporalDependency <em>Temporal Dependency</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyModelPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Task Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Task Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Task Config</em>' containment reference.
	 * @see #setTaskConfig(TaskConfig)
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyModelPackage#getDocumentRoot_TaskConfig()
	 * @model containment="true" upper="-2"
	 *        extendedMetaData="name='taskConfig' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	TaskConfig getTaskConfig();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.DocumentRoot#getTaskConfig <em>Task Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Task Config</em>' containment reference.
	 * @see #getTaskConfig()
	 * @generated
	 */
	void setTaskConfig(TaskConfig value);

	/**
	 * Returns the value of the '<em><b>Temporal Dependency</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Temporal Dependency</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Temporal Dependency</em>' containment reference.
	 * @see #setTemporalDependency(TemporalDependency)
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyModelPackage#getDocumentRoot_TemporalDependency()
	 * @model containment="true" upper="-2"
	 *        extendedMetaData="name='temporalDependency' kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	TemporalDependency getTemporalDependency();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.DocumentRoot#getTemporalDependency <em>Temporal Dependency</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Temporal Dependency</em>' containment reference.
	 * @see #getTemporalDependency()
	 * @generated
	 */
	void setTemporalDependency(TemporalDependency value);

} // DocumentRoot

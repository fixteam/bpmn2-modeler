/**
 */
package org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl;

import org.eclipse.bpmn2.modeler.examples.customtask.MyModel.DocumentRoot;
import org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyModelPackage;
import org.eclipse.bpmn2.modeler.examples.customtask.MyModel.TaskConfig;
import org.eclipse.bpmn2.modeler.examples.customtask.MyModel.TemporalDependency;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.DocumentRootImpl#getTaskConfig <em>Task Config</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.DocumentRootImpl#getTemporalDependency <em>Temporal Dependency</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentRootImpl extends EObjectImpl implements DocumentRoot {
	/**
	 * The cached value of the '{@link #getTaskConfig() <em>Task Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTaskConfig()
	 * @generated
	 * @ordered
	 */
	protected TaskConfig taskConfig;

	/**
	 * The cached value of the '{@link #getTemporalDependency() <em>Temporal Dependency</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemporalDependency()
	 * @generated
	 * @ordered
	 */
	protected TemporalDependency temporalDependency;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MyModelPackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaskConfig getTaskConfig() {
		return taskConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTaskConfig(TaskConfig newTaskConfig, NotificationChain msgs) {
		TaskConfig oldTaskConfig = taskConfig;
		taskConfig = newTaskConfig;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MyModelPackage.DOCUMENT_ROOT__TASK_CONFIG, oldTaskConfig, newTaskConfig);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTaskConfig(TaskConfig newTaskConfig) {
		if (newTaskConfig != taskConfig) {
			NotificationChain msgs = null;
			if (taskConfig != null)
				msgs = ((InternalEObject)taskConfig).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MyModelPackage.DOCUMENT_ROOT__TASK_CONFIG, null, msgs);
			if (newTaskConfig != null)
				msgs = ((InternalEObject)newTaskConfig).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MyModelPackage.DOCUMENT_ROOT__TASK_CONFIG, null, msgs);
			msgs = basicSetTaskConfig(newTaskConfig, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MyModelPackage.DOCUMENT_ROOT__TASK_CONFIG, newTaskConfig, newTaskConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemporalDependency getTemporalDependency() {
		return temporalDependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTemporalDependency(TemporalDependency newTemporalDependency, NotificationChain msgs) {
		TemporalDependency oldTemporalDependency = temporalDependency;
		temporalDependency = newTemporalDependency;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MyModelPackage.DOCUMENT_ROOT__TEMPORAL_DEPENDENCY, oldTemporalDependency, newTemporalDependency);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTemporalDependency(TemporalDependency newTemporalDependency) {
		if (newTemporalDependency != temporalDependency) {
			NotificationChain msgs = null;
			if (temporalDependency != null)
				msgs = ((InternalEObject)temporalDependency).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MyModelPackage.DOCUMENT_ROOT__TEMPORAL_DEPENDENCY, null, msgs);
			if (newTemporalDependency != null)
				msgs = ((InternalEObject)newTemporalDependency).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MyModelPackage.DOCUMENT_ROOT__TEMPORAL_DEPENDENCY, null, msgs);
			msgs = basicSetTemporalDependency(newTemporalDependency, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MyModelPackage.DOCUMENT_ROOT__TEMPORAL_DEPENDENCY, newTemporalDependency, newTemporalDependency));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MyModelPackage.DOCUMENT_ROOT__TASK_CONFIG:
				return basicSetTaskConfig(null, msgs);
			case MyModelPackage.DOCUMENT_ROOT__TEMPORAL_DEPENDENCY:
				return basicSetTemporalDependency(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MyModelPackage.DOCUMENT_ROOT__TASK_CONFIG:
				return getTaskConfig();
			case MyModelPackage.DOCUMENT_ROOT__TEMPORAL_DEPENDENCY:
				return getTemporalDependency();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MyModelPackage.DOCUMENT_ROOT__TASK_CONFIG:
				setTaskConfig((TaskConfig)newValue);
				return;
			case MyModelPackage.DOCUMENT_ROOT__TEMPORAL_DEPENDENCY:
				setTemporalDependency((TemporalDependency)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MyModelPackage.DOCUMENT_ROOT__TASK_CONFIG:
				setTaskConfig((TaskConfig)null);
				return;
			case MyModelPackage.DOCUMENT_ROOT__TEMPORAL_DEPENDENCY:
				setTemporalDependency((TemporalDependency)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MyModelPackage.DOCUMENT_ROOT__TASK_CONFIG:
				return taskConfig != null;
			case MyModelPackage.DOCUMENT_ROOT__TEMPORAL_DEPENDENCY:
				return temporalDependency != null;
		}
		return super.eIsSet(featureID);
	}

} //DocumentRootImpl

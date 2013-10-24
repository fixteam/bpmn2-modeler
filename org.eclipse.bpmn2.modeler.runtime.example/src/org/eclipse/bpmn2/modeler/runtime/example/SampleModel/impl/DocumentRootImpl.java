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
package org.eclipse.bpmn2.modeler.runtime.example.SampleModel.impl;

import org.eclipse.bpmn2.modeler.runtime.example.SampleModel.DocumentRoot;
import org.eclipse.bpmn2.modeler.runtime.example.SampleModel.SampleModelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.example.SampleModel.impl.DocumentRootImpl#getelementId <em>Sample Custom Task Id</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.example.SampleModel.impl.DocumentRootImpl#getSampleCustomFlowValue <em>Sample Custom Flow Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentRootImpl extends org.eclipse.bpmn2.impl.DocumentRootImpl implements DocumentRoot {
	/**
	 * The default value of the '{@link #getelementId() <em>Sample Custom Task Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getelementId()
	 * @generated
	 * @ordered
	 */
	protected static final String SAMPLE_CUSTOM_TASK_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getelementId() <em>Sample Custom Task Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getelementId()
	 * @generated
	 * @ordered
	 */
	protected String elementId = SAMPLE_CUSTOM_TASK_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getSampleCustomFlowValue() <em>Sample Custom Flow Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSampleCustomFlowValue()
	 * @generated
	 * @ordered
	 */
	protected static final String SAMPLE_CUSTOM_FLOW_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSampleCustomFlowValue() <em>Sample Custom Flow Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSampleCustomFlowValue()
	 * @generated
	 * @ordered
	 */
	protected String sampleCustomFlowValue = SAMPLE_CUSTOM_FLOW_VALUE_EDEFAULT;

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
		return SampleModelPackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getelementId() {
		return elementId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setelementId(String newelementId) {
		String oldelementId = elementId;
		elementId = newelementId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SampleModelPackage.DOCUMENT_ROOT__SAMPLE_CUSTOM_TASK_ID, oldelementId, elementId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSampleCustomFlowValue() {
		return sampleCustomFlowValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSampleCustomFlowValue(String newSampleCustomFlowValue) {
		String oldSampleCustomFlowValue = sampleCustomFlowValue;
		sampleCustomFlowValue = newSampleCustomFlowValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SampleModelPackage.DOCUMENT_ROOT__SAMPLE_CUSTOM_FLOW_VALUE, oldSampleCustomFlowValue, sampleCustomFlowValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SampleModelPackage.DOCUMENT_ROOT__SAMPLE_CUSTOM_TASK_ID:
				return getelementId();
			case SampleModelPackage.DOCUMENT_ROOT__SAMPLE_CUSTOM_FLOW_VALUE:
				return getSampleCustomFlowValue();
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
			case SampleModelPackage.DOCUMENT_ROOT__SAMPLE_CUSTOM_TASK_ID:
				setelementId((String)newValue);
				return;
			case SampleModelPackage.DOCUMENT_ROOT__SAMPLE_CUSTOM_FLOW_VALUE:
				setSampleCustomFlowValue((String)newValue);
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
			case SampleModelPackage.DOCUMENT_ROOT__SAMPLE_CUSTOM_TASK_ID:
				setelementId(SAMPLE_CUSTOM_TASK_ID_EDEFAULT);
				return;
			case SampleModelPackage.DOCUMENT_ROOT__SAMPLE_CUSTOM_FLOW_VALUE:
				setSampleCustomFlowValue(SAMPLE_CUSTOM_FLOW_VALUE_EDEFAULT);
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
			case SampleModelPackage.DOCUMENT_ROOT__SAMPLE_CUSTOM_TASK_ID:
				return SAMPLE_CUSTOM_TASK_ID_EDEFAULT == null ? elementId != null : !SAMPLE_CUSTOM_TASK_ID_EDEFAULT.equals(elementId);
			case SampleModelPackage.DOCUMENT_ROOT__SAMPLE_CUSTOM_FLOW_VALUE:
				return SAMPLE_CUSTOM_FLOW_VALUE_EDEFAULT == null ? sampleCustomFlowValue != null : !SAMPLE_CUSTOM_FLOW_VALUE_EDEFAULT.equals(sampleCustomFlowValue);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (elementId: ");
		result.append(elementId);
		result.append(", sampleCustomFlowValue: ");
		result.append(sampleCustomFlowValue);
		result.append(')');
		return result.toString();
	}

} //DocumentRootImpl

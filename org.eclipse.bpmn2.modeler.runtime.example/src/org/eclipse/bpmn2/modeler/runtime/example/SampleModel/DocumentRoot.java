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
package org.eclipse.bpmn2.modeler.runtime.example.SampleModel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.example.SampleModel.DocumentRoot#getelementId <em>Sample Custom Task Id</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.example.SampleModel.DocumentRoot#getSampleCustomFlowValue <em>Sample Custom Flow Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpmn2.modeler.runtime.example.SampleModel.SampleModelPackage#getDocumentRoot()
 * @model extendedMetaData="kind='mixed' name='' namespace='##targetNamespace'"
 * @generated
 */
public interface DocumentRoot extends org.eclipse.bpmn2.DocumentRoot {
	/**
	 * Returns the value of the '<em><b>Sample Custom Task Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sample Custom Task Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sample Custom Task Id</em>' attribute.
	 * @see #setelementId(String)
	 * @see org.eclipse.bpmn2.modeler.runtime.example.SampleModel.SampleModelPackage#getDocumentRoot_elementId()
	 * @model extendedMetaData="kind='attribute' name='taskId' namespace='##targetNamespace'"
	 * @generated
	 */
	String getelementId();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.example.SampleModel.DocumentRoot#getelementId <em>Sample Custom Task Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sample Custom Task Id</em>' attribute.
	 * @see #getelementId()
	 * @generated
	 */
	void setelementId(String value);

	/**
	 * Returns the value of the '<em><b>Sample Custom Flow Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sample Custom Flow Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sample Custom Flow Value</em>' attribute.
	 * @see #setSampleCustomFlowValue(String)
	 * @see org.eclipse.bpmn2.modeler.runtime.example.SampleModel.SampleModelPackage#getDocumentRoot_SampleCustomFlowValue()
	 * @model extendedMetaData="kind='attribute' name='flowValue' namespace='##targetNamespace'"
	 * @generated
	 */
	String getSampleCustomFlowValue();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.example.SampleModel.DocumentRoot#getSampleCustomFlowValue <em>Sample Custom Flow Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sample Custom Flow Value</em>' attribute.
	 * @see #getSampleCustomFlowValue()
	 * @generated
	 */
	void setSampleCustomFlowValue(String value);

} // DocumentRoot

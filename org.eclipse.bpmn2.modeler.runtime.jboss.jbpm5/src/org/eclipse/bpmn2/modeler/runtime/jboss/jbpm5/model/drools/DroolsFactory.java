/**
 */
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsPackage
 * @generated
 */
public interface DroolsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DroolsFactory eINSTANCE = org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
	DocumentRoot createDocumentRoot();

	/**
	 * Returns a new object of class '<em>Global Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Global Type</em>'.
	 * @generated
	 */
	GlobalType createGlobalType();

	/**
	 * Returns a new object of class '<em>Import Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Import Type</em>'.
	 * @generated
	 */
	ImportType createImportType();

	/**
	 * Returns a new object of class '<em>Metadata Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metadata Type</em>'.
	 * @generated
	 */
	MetadataType createMetadataType();

	/**
	 * Returns a new object of class '<em>Metaentry Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metaentry Type</em>'.
	 * @generated
	 */
	MetaentryType createMetaentryType();

	/**
	 * Returns a new object of class '<em>On Entry Script Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>On Entry Script Type</em>'.
	 * @generated
	 */
	OnEntryScriptType createOnEntryScriptType();

	/**
	 * Returns a new object of class '<em>On Exit Script Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>On Exit Script Type</em>'.
	 * @generated
	 */
	OnExitScriptType createOnExitScriptType();

	/**
	 * Returns a new object of class '<em>BP Sim Data Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>BP Sim Data Type</em>'.
	 * @generated
	 */
	BPSimDataType createBPSimDataType();

	/**
	 * Returns a new object of class '<em>Callable Element Proxy</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Callable Element Proxy</em>'.
	 * @generated
	 */
	ExternalProcess createExternalProcess();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DroolsPackage getDroolsPackage();

} //DroolsFactory

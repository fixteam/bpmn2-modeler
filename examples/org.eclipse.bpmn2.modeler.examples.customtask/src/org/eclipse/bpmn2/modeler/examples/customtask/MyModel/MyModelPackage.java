/**
 */
package org.eclipse.bpmn2.modeler.examples.customtask.MyModel;

import org.eclipse.bpmn2.Bpmn2Package;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyModelFactory
 * @model kind="package"
 * @generated
 */
public interface MyModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "MyModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.eclipse.bpmn2.modeler.examples.customtask";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "mm";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MyModelPackage eINSTANCE = org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.MyModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.DocumentRootImpl
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.MyModelPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 0;

	/**
	 * The feature id for the '<em><b>Task Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TASK_CONFIG = 0;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.ParameterImpl
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.MyModelPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.TaskConfigImpl <em>Task Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.TaskConfigImpl
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.MyModelPackageImpl#getTaskConfig()
	 * @generated
	 */
	int TASK_CONFIG = 2;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_CONFIG__PARAMETERS = 0;

	/**
	 * The number of structural features of the '<em>Task Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_CONFIG_FEATURE_COUNT = 1;


	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.MyEventDefinitionImpl <em>My Event Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.MyEventDefinitionImpl
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.MyModelPackageImpl#getMyEventDefinition()
	 * @generated
	 */
	int MY_EVENT_DEFINITION = 3;

	/**
	 * The feature id for the '<em><b>Extension Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MY_EVENT_DEFINITION__EXTENSION_VALUES = Bpmn2Package.EVENT_DEFINITION__EXTENSION_VALUES;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MY_EVENT_DEFINITION__DOCUMENTATION = Bpmn2Package.EVENT_DEFINITION__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Extension Definitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MY_EVENT_DEFINITION__EXTENSION_DEFINITIONS = Bpmn2Package.EVENT_DEFINITION__EXTENSION_DEFINITIONS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MY_EVENT_DEFINITION__ID = Bpmn2Package.EVENT_DEFINITION__ID;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MY_EVENT_DEFINITION__ANY_ATTRIBUTE = Bpmn2Package.EVENT_DEFINITION__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MY_EVENT_DEFINITION__VALUE = Bpmn2Package.EVENT_DEFINITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>My Event Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MY_EVENT_DEFINITION_FEATURE_COUNT = Bpmn2Package.EVENT_DEFINITION_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.DocumentRoot#getTaskConfig <em>Task Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Task Config</em>'.
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.DocumentRoot#getTaskConfig()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_TaskConfig();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.Parameter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.Parameter#getName()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.Parameter#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.Parameter#getValue()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.TaskConfig <em>Task Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task Config</em>'.
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.TaskConfig
	 * @generated
	 */
	EClass getTaskConfig();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.TaskConfig#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.TaskConfig#getParameters()
	 * @see #getTaskConfig()
	 * @generated
	 */
	EReference getTaskConfig_Parameters();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyEventDefinition <em>My Event Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>My Event Definition</em>'.
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyEventDefinition
	 * @generated
	 */
	EClass getMyEventDefinition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyEventDefinition#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyEventDefinition#getValue()
	 * @see #getMyEventDefinition()
	 * @generated
	 */
	EAttribute getMyEventDefinition_Value();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MyModelFactory getMyModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.DocumentRootImpl
		 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.MyModelPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Task Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__TASK_CONFIG = eINSTANCE.getDocumentRoot_TaskConfig();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.ParameterImpl
		 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.MyModelPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__NAME = eINSTANCE.getParameter_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__VALUE = eINSTANCE.getParameter_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.TaskConfigImpl <em>Task Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.TaskConfigImpl
		 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.MyModelPackageImpl#getTaskConfig()
		 * @generated
		 */
		EClass TASK_CONFIG = eINSTANCE.getTaskConfig();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_CONFIG__PARAMETERS = eINSTANCE.getTaskConfig_Parameters();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.MyEventDefinitionImpl <em>My Event Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.MyEventDefinitionImpl
		 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl.MyModelPackageImpl#getMyEventDefinition()
		 * @generated
		 */
		EClass MY_EVENT_DEFINITION = eINSTANCE.getMyEventDefinition();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MY_EVENT_DEFINITION__VALUE = eINSTANCE.getMyEventDefinition_Value();

	}

} //MyModelPackage

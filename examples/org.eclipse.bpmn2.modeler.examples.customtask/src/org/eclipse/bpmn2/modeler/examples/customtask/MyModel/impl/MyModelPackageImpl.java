/**
 */
package org.eclipse.bpmn2.modeler.examples.customtask.MyModel.impl;

import org.eclipse.bpmn2.Bpmn2Package;

import org.eclipse.bpmn2.di.BpmnDiPackage;

import org.eclipse.bpmn2.modeler.examples.customtask.MyModel.DocumentRoot;
import org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyEventDefinition;
import org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyModelFactory;
import org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyModelPackage;
import org.eclipse.bpmn2.modeler.examples.customtask.MyModel.Parameter;
import org.eclipse.bpmn2.modeler.examples.customtask.MyModel.TaskConfig;

import org.eclipse.bpmn2.modeler.examples.customtask.MyModel.TemporalDependency;
import org.eclipse.dd.dc.DcPackage;

import org.eclipse.dd.di.DiPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MyModelPackageImpl extends EPackageImpl implements MyModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass taskConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass myEventDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass temporalDependencyEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.bpmn2.modeler.examples.customtask.MyModel.MyModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MyModelPackageImpl() {
		super(eNS_URI, MyModelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link MyModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MyModelPackage init() {
		if (isInited) return (MyModelPackage)EPackage.Registry.INSTANCE.getEPackage(MyModelPackage.eNS_URI);

		// Obtain or create and register package
		MyModelPackageImpl theMyModelPackage = (MyModelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof MyModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new MyModelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		Bpmn2Package.eINSTANCE.eClass();
		BpmnDiPackage.eINSTANCE.eClass();
		DiPackage.eINSTANCE.eClass();
		DcPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theMyModelPackage.createPackageContents();

		// Initialize created meta-data
		theMyModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMyModelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MyModelPackage.eNS_URI, theMyModelPackage);
		return theMyModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDocumentRoot() {
		return documentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_TaskConfig() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_TemporalDependency() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameter() {
		return parameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_Name() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_Value() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTaskConfig() {
		return taskConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTaskConfig_Parameters() {
		return (EReference)taskConfigEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMyEventDefinition() {
		return myEventDefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMyEventDefinition_Value() {
		return (EAttribute)myEventDefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTemporalDependency() {
		return temporalDependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemporalDependency_SourceRef() {
		return (EReference)temporalDependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemporalDependency_TargetRef() {
		return (EReference)temporalDependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTemporalDependency_LagTime() {
		return (EAttribute)temporalDependencyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MyModelFactory getMyModelFactory() {
		return (MyModelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__TASK_CONFIG);
		createEReference(documentRootEClass, DOCUMENT_ROOT__TEMPORAL_DEPENDENCY);

		parameterEClass = createEClass(PARAMETER);
		createEAttribute(parameterEClass, PARAMETER__NAME);
		createEAttribute(parameterEClass, PARAMETER__VALUE);

		taskConfigEClass = createEClass(TASK_CONFIG);
		createEReference(taskConfigEClass, TASK_CONFIG__PARAMETERS);

		myEventDefinitionEClass = createEClass(MY_EVENT_DEFINITION);
		createEAttribute(myEventDefinitionEClass, MY_EVENT_DEFINITION__VALUE);

		temporalDependencyEClass = createEClass(TEMPORAL_DEPENDENCY);
		createEReference(temporalDependencyEClass, TEMPORAL_DEPENDENCY__SOURCE_REF);
		createEReference(temporalDependencyEClass, TEMPORAL_DEPENDENCY__TARGET_REF);
		createEAttribute(temporalDependencyEClass, TEMPORAL_DEPENDENCY__LAG_TIME);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		Bpmn2Package theBpmn2Package = (Bpmn2Package)EPackage.Registry.INSTANCE.getEPackage(Bpmn2Package.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		myEventDefinitionEClass.getESuperTypes().add(theBpmn2Package.getEventDefinition());
		temporalDependencyEClass.getESuperTypes().add(theBpmn2Package.getFlowElement());

		// Initialize classes and features; add operations and parameters
		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDocumentRoot_TaskConfig(), this.getTaskConfig(), null, "taskConfig", null, 0, -2, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_TemporalDependency(), this.getTemporalDependency(), null, "temporalDependency", null, 0, -2, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getParameter_Name(), ecorePackage.getEString(), "name", null, 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameter_Value(), ecorePackage.getEString(), "value", null, 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(taskConfigEClass, TaskConfig.class, "TaskConfig", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTaskConfig_Parameters(), this.getParameter(), null, "parameters", null, 0, -1, TaskConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(myEventDefinitionEClass, MyEventDefinition.class, "MyEventDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMyEventDefinition_Value(), ecorePackage.getEString(), "value", null, 0, 1, MyEventDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(temporalDependencyEClass, TemporalDependency.class, "TemporalDependency", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTemporalDependency_SourceRef(), theBpmn2Package.getBoundaryEvent(), null, "sourceRef", null, 0, 1, TemporalDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTemporalDependency_TargetRef(), theBpmn2Package.getBoundaryEvent(), null, "targetRef", null, 0, 1, TemporalDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemporalDependency_LagTime(), ecorePackage.getEString(), "lagTime", null, 0, 1, TemporalDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
		addAnnotation
		  (documentRootEClass, 
		   source, 
		   new String[] {
			 "name", "",
			 "kind", "mixed"
		   });		
		addAnnotation
		  (getDocumentRoot_TaskConfig(), 
		   source, 
		   new String[] {
			 "name", "taskConfig",
			 "kind", "element",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_TemporalDependency(), 
		   source, 
		   new String[] {
			 "name", "temporalDependency",
			 "kind", "element",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTaskConfig_Parameters(), 
		   source, 
		   new String[] {
			 "name", "parameter",
			 "kind", "element",
			 "namespace", "##targetNamespace"
		   });
	}

} //MyModelPackageImpl

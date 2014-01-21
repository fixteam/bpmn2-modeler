/**
 */
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsFactory
 * @model kind="package"
 * @generated
 */
public interface DroolsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "drools";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.jboss.org/drools";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "drools";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DroolsPackage eINSTANCE = org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DocumentRootImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 0;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = Bpmn2Package.DOCUMENT_ROOT__MIXED;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = Bpmn2Package.DOCUMENT_ROOT__XMLNS_PREFIX_MAP;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = Bpmn2Package.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION;

	/**
	 * The feature id for the '<em><b>Activity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ACTIVITY = Bpmn2Package.DOCUMENT_ROOT__ACTIVITY;

	/**
	 * The feature id for the '<em><b>Ad Hoc Sub Process</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AD_HOC_SUB_PROCESS = Bpmn2Package.DOCUMENT_ROOT__AD_HOC_SUB_PROCESS;

	/**
	 * The feature id for the '<em><b>Flow Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FLOW_ELEMENT = Bpmn2Package.DOCUMENT_ROOT__FLOW_ELEMENT;

	/**
	 * The feature id for the '<em><b>Artifact</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ARTIFACT = Bpmn2Package.DOCUMENT_ROOT__ARTIFACT;

	/**
	 * The feature id for the '<em><b>MultipleAssignments</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ASSIGNMENT = Bpmn2Package.DOCUMENT_ROOT__ASSIGNMENT;

	/**
	 * The feature id for the '<em><b>Association</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ASSOCIATION = Bpmn2Package.DOCUMENT_ROOT__ASSOCIATION;

	/**
	 * The feature id for the '<em><b>Auditing</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUDITING = Bpmn2Package.DOCUMENT_ROOT__AUDITING;

	/**
	 * The feature id for the '<em><b>Base Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BASE_ELEMENT = Bpmn2Package.DOCUMENT_ROOT__BASE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Base Element With Mixed Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BASE_ELEMENT_WITH_MIXED_CONTENT = Bpmn2Package.DOCUMENT_ROOT__BASE_ELEMENT_WITH_MIXED_CONTENT;

	/**
	 * The feature id for the '<em><b>Boundary Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BOUNDARY_EVENT = Bpmn2Package.DOCUMENT_ROOT__BOUNDARY_EVENT;

	/**
	 * The feature id for the '<em><b>Business Rule Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BUSINESS_RULE_TASK = Bpmn2Package.DOCUMENT_ROOT__BUSINESS_RULE_TASK;

	/**
	 * The feature id for the '<em><b>Callable Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CALLABLE_ELEMENT = Bpmn2Package.DOCUMENT_ROOT__CALLABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Call Activity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CALL_ACTIVITY = Bpmn2Package.DOCUMENT_ROOT__CALL_ACTIVITY;

	/**
	 * The feature id for the '<em><b>Call Choreography</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CALL_CHOREOGRAPHY = Bpmn2Package.DOCUMENT_ROOT__CALL_CHOREOGRAPHY;

	/**
	 * The feature id for the '<em><b>Call Conversation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CALL_CONVERSATION = Bpmn2Package.DOCUMENT_ROOT__CALL_CONVERSATION;

	/**
	 * The feature id for the '<em><b>Conversation Node</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CONVERSATION_NODE = Bpmn2Package.DOCUMENT_ROOT__CONVERSATION_NODE;

	/**
	 * The feature id for the '<em><b>Cancel Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CANCEL_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__CANCEL_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Root Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ROOT_ELEMENT = Bpmn2Package.DOCUMENT_ROOT__ROOT_ELEMENT;

	/**
	 * The feature id for the '<em><b>Catch Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CATCH_EVENT = Bpmn2Package.DOCUMENT_ROOT__CATCH_EVENT;

	/**
	 * The feature id for the '<em><b>Category</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CATEGORY = Bpmn2Package.DOCUMENT_ROOT__CATEGORY;

	/**
	 * The feature id for the '<em><b>Category Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CATEGORY_VALUE = Bpmn2Package.DOCUMENT_ROOT__CATEGORY_VALUE;

	/**
	 * The feature id for the '<em><b>Choreography</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CHOREOGRAPHY = Bpmn2Package.DOCUMENT_ROOT__CHOREOGRAPHY;

	/**
	 * The feature id for the '<em><b>Collaboration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__COLLABORATION = Bpmn2Package.DOCUMENT_ROOT__COLLABORATION;

	/**
	 * The feature id for the '<em><b>Choreography Activity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CHOREOGRAPHY_ACTIVITY = Bpmn2Package.DOCUMENT_ROOT__CHOREOGRAPHY_ACTIVITY;

	/**
	 * The feature id for the '<em><b>Choreography Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CHOREOGRAPHY_TASK = Bpmn2Package.DOCUMENT_ROOT__CHOREOGRAPHY_TASK;

	/**
	 * The feature id for the '<em><b>Compensate Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__COMPENSATE_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__COMPENSATE_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Complex Behavior Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__COMPLEX_BEHAVIOR_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__COMPLEX_BEHAVIOR_DEFINITION;

	/**
	 * The feature id for the '<em><b>Complex Gateway</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__COMPLEX_GATEWAY = Bpmn2Package.DOCUMENT_ROOT__COMPLEX_GATEWAY;

	/**
	 * The feature id for the '<em><b>Conditional Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CONDITIONAL_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__CONDITIONAL_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Conversation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CONVERSATION = Bpmn2Package.DOCUMENT_ROOT__CONVERSATION;

	/**
	 * The feature id for the '<em><b>Conversation Association</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CONVERSATION_ASSOCIATION = Bpmn2Package.DOCUMENT_ROOT__CONVERSATION_ASSOCIATION;

	/**
	 * The feature id for the '<em><b>Conversation Link</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CONVERSATION_LINK = Bpmn2Package.DOCUMENT_ROOT__CONVERSATION_LINK;

	/**
	 * The feature id for the '<em><b>Correlation Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CORRELATION_KEY = Bpmn2Package.DOCUMENT_ROOT__CORRELATION_KEY;

	/**
	 * The feature id for the '<em><b>Correlation Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CORRELATION_PROPERTY = Bpmn2Package.DOCUMENT_ROOT__CORRELATION_PROPERTY;

	/**
	 * The feature id for the '<em><b>Correlation Property Binding</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CORRELATION_PROPERTY_BINDING = Bpmn2Package.DOCUMENT_ROOT__CORRELATION_PROPERTY_BINDING;

	/**
	 * The feature id for the '<em><b>Correlation Property Retrieval SingleAssignment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CORRELATION_PROPERTY_RETRIEVAL_EXPRESSION = Bpmn2Package.DOCUMENT_ROOT__CORRELATION_PROPERTY_RETRIEVAL_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Correlation Subscription</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CORRELATION_SUBSCRIPTION = Bpmn2Package.DOCUMENT_ROOT__CORRELATION_SUBSCRIPTION;

	/**
	 * The feature id for the '<em><b>Data Association</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_ASSOCIATION = Bpmn2Package.DOCUMENT_ROOT__DATA_ASSOCIATION;

	/**
	 * The feature id for the '<em><b>Data Input</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_INPUT = Bpmn2Package.DOCUMENT_ROOT__DATA_INPUT;

	/**
	 * The feature id for the '<em><b>Data Input Association</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_INPUT_ASSOCIATION = Bpmn2Package.DOCUMENT_ROOT__DATA_INPUT_ASSOCIATION;

	/**
	 * The feature id for the '<em><b>Data Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_OBJECT = Bpmn2Package.DOCUMENT_ROOT__DATA_OBJECT;

	/**
	 * The feature id for the '<em><b>Data Object Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_OBJECT_REFERENCE = Bpmn2Package.DOCUMENT_ROOT__DATA_OBJECT_REFERENCE;

	/**
	 * The feature id for the '<em><b>Data Output</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_OUTPUT = Bpmn2Package.DOCUMENT_ROOT__DATA_OUTPUT;

	/**
	 * The feature id for the '<em><b>Data Output Association</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_OUTPUT_ASSOCIATION = Bpmn2Package.DOCUMENT_ROOT__DATA_OUTPUT_ASSOCIATION;

	/**
	 * The feature id for the '<em><b>Data State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_STATE = Bpmn2Package.DOCUMENT_ROOT__DATA_STATE;

	/**
	 * The feature id for the '<em><b>Data Store</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_STORE = Bpmn2Package.DOCUMENT_ROOT__DATA_STORE;

	/**
	 * The feature id for the '<em><b>Data Store Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_STORE_REFERENCE = Bpmn2Package.DOCUMENT_ROOT__DATA_STORE_REFERENCE;

	/**
	 * The feature id for the '<em><b>Definitions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DEFINITIONS = Bpmn2Package.DOCUMENT_ROOT__DEFINITIONS;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DOCUMENTATION = Bpmn2Package.DOCUMENT_ROOT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>End Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__END_EVENT = Bpmn2Package.DOCUMENT_ROOT__END_EVENT;

	/**
	 * The feature id for the '<em><b>End Point</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__END_POINT = Bpmn2Package.DOCUMENT_ROOT__END_POINT;

	/**
	 * The feature id for the '<em><b>Error</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ERROR = Bpmn2Package.DOCUMENT_ROOT__ERROR;

	/**
	 * The feature id for the '<em><b>Error Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ERROR_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__ERROR_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Escalation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ESCALATION = Bpmn2Package.DOCUMENT_ROOT__ESCALATION;

	/**
	 * The feature id for the '<em><b>Escalation Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ESCALATION_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__ESCALATION_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EVENT = Bpmn2Package.DOCUMENT_ROOT__EVENT;

	/**
	 * The feature id for the '<em><b>Event Based Gateway</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EVENT_BASED_GATEWAY = Bpmn2Package.DOCUMENT_ROOT__EVENT_BASED_GATEWAY;

	/**
	 * The feature id for the '<em><b>Exclusive Gateway</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EXCLUSIVE_GATEWAY = Bpmn2Package.DOCUMENT_ROOT__EXCLUSIVE_GATEWAY;

	/**
	 * The feature id for the '<em><b>SingleAssignment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EXPRESSION = Bpmn2Package.DOCUMENT_ROOT__EXPRESSION;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EXTENSION = Bpmn2Package.DOCUMENT_ROOT__EXTENSION;

	/**
	 * The feature id for the '<em><b>Extension Elements</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EXTENSION_ELEMENTS = Bpmn2Package.DOCUMENT_ROOT__EXTENSION_ELEMENTS;

	/**
	 * The feature id for the '<em><b>Flow Node</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FLOW_NODE = Bpmn2Package.DOCUMENT_ROOT__FLOW_NODE;

	/**
	 * The feature id for the '<em><b>Formal SingleAssignment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FORMAL_EXPRESSION = Bpmn2Package.DOCUMENT_ROOT__FORMAL_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Gateway</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GATEWAY = Bpmn2Package.DOCUMENT_ROOT__GATEWAY;

	/**
	 * The feature id for the '<em><b>Global Business Rule Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GLOBAL_BUSINESS_RULE_TASK = Bpmn2Package.DOCUMENT_ROOT__GLOBAL_BUSINESS_RULE_TASK;

	/**
	 * The feature id for the '<em><b>Global Choreography Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GLOBAL_CHOREOGRAPHY_TASK = Bpmn2Package.DOCUMENT_ROOT__GLOBAL_CHOREOGRAPHY_TASK;

	/**
	 * The feature id for the '<em><b>Global Conversation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GLOBAL_CONVERSATION = Bpmn2Package.DOCUMENT_ROOT__GLOBAL_CONVERSATION;

	/**
	 * The feature id for the '<em><b>Global Manual Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GLOBAL_MANUAL_TASK = Bpmn2Package.DOCUMENT_ROOT__GLOBAL_MANUAL_TASK;

	/**
	 * The feature id for the '<em><b>Global Script Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GLOBAL_SCRIPT_TASK = Bpmn2Package.DOCUMENT_ROOT__GLOBAL_SCRIPT_TASK;

	/**
	 * The feature id for the '<em><b>Global Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GLOBAL_TASK = Bpmn2Package.DOCUMENT_ROOT__GLOBAL_TASK;

	/**
	 * The feature id for the '<em><b>Global User Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GLOBAL_USER_TASK = Bpmn2Package.DOCUMENT_ROOT__GLOBAL_USER_TASK;

	/**
	 * The feature id for the '<em><b>Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GROUP = Bpmn2Package.DOCUMENT_ROOT__GROUP;

	/**
	 * The feature id for the '<em><b>Human Performer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__HUMAN_PERFORMER = Bpmn2Package.DOCUMENT_ROOT__HUMAN_PERFORMER;

	/**
	 * The feature id for the '<em><b>Performer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PERFORMER = Bpmn2Package.DOCUMENT_ROOT__PERFORMER;

	/**
	 * The feature id for the '<em><b>Resource Role</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RESOURCE_ROLE = Bpmn2Package.DOCUMENT_ROOT__RESOURCE_ROLE;

	/**
	 * The feature id for the '<em><b>Implicit Throw Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__IMPLICIT_THROW_EVENT = Bpmn2Package.DOCUMENT_ROOT__IMPLICIT_THROW_EVENT;

	/**
	 * The feature id for the '<em><b>Import</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__IMPORT = Bpmn2Package.DOCUMENT_ROOT__IMPORT;

	/**
	 * The feature id for the '<em><b>Inclusive Gateway</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__INCLUSIVE_GATEWAY = Bpmn2Package.DOCUMENT_ROOT__INCLUSIVE_GATEWAY;

	/**
	 * The feature id for the '<em><b>Input Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__INPUT_SET = Bpmn2Package.DOCUMENT_ROOT__INPUT_SET;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__INTERFACE = Bpmn2Package.DOCUMENT_ROOT__INTERFACE;

	/**
	 * The feature id for the '<em><b>Intermediate Catch Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__INTERMEDIATE_CATCH_EVENT = Bpmn2Package.DOCUMENT_ROOT__INTERMEDIATE_CATCH_EVENT;

	/**
	 * The feature id for the '<em><b>Intermediate Throw Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__INTERMEDIATE_THROW_EVENT = Bpmn2Package.DOCUMENT_ROOT__INTERMEDIATE_THROW_EVENT;

	/**
	 * The feature id for the '<em><b>Io Binding</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__IO_BINDING = Bpmn2Package.DOCUMENT_ROOT__IO_BINDING;

	/**
	 * The feature id for the '<em><b>Io Specification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__IO_SPECIFICATION = Bpmn2Package.DOCUMENT_ROOT__IO_SPECIFICATION;

	/**
	 * The feature id for the '<em><b>Item Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ITEM_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__ITEM_DEFINITION;

	/**
	 * The feature id for the '<em><b>Lane</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__LANE = Bpmn2Package.DOCUMENT_ROOT__LANE;

	/**
	 * The feature id for the '<em><b>Lane Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__LANE_SET = Bpmn2Package.DOCUMENT_ROOT__LANE_SET;

	/**
	 * The feature id for the '<em><b>Link Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__LINK_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__LINK_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Loop Characteristics</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__LOOP_CHARACTERISTICS = Bpmn2Package.DOCUMENT_ROOT__LOOP_CHARACTERISTICS;

	/**
	 * The feature id for the '<em><b>Manual Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MANUAL_TASK = Bpmn2Package.DOCUMENT_ROOT__MANUAL_TASK;

	/**
	 * The feature id for the '<em><b>Message</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MESSAGE = Bpmn2Package.DOCUMENT_ROOT__MESSAGE;

	/**
	 * The feature id for the '<em><b>Message Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MESSAGE_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__MESSAGE_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Message Flow</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MESSAGE_FLOW = Bpmn2Package.DOCUMENT_ROOT__MESSAGE_FLOW;

	/**
	 * The feature id for the '<em><b>Message Flow Association</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MESSAGE_FLOW_ASSOCIATION = Bpmn2Package.DOCUMENT_ROOT__MESSAGE_FLOW_ASSOCIATION;

	/**
	 * The feature id for the '<em><b>Monitoring</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MONITORING = Bpmn2Package.DOCUMENT_ROOT__MONITORING;

	/**
	 * The feature id for the '<em><b>Multi Instance Loop Characteristics</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MULTI_INSTANCE_LOOP_CHARACTERISTICS = Bpmn2Package.DOCUMENT_ROOT__MULTI_INSTANCE_LOOP_CHARACTERISTICS;

	/**
	 * The feature id for the '<em><b>Operation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__OPERATION = Bpmn2Package.DOCUMENT_ROOT__OPERATION;

	/**
	 * The feature id for the '<em><b>Output Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__OUTPUT_SET = Bpmn2Package.DOCUMENT_ROOT__OUTPUT_SET;

	/**
	 * The feature id for the '<em><b>Parallel Gateway</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PARALLEL_GATEWAY = Bpmn2Package.DOCUMENT_ROOT__PARALLEL_GATEWAY;

	/**
	 * The feature id for the '<em><b>Participant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PARTICIPANT = Bpmn2Package.DOCUMENT_ROOT__PARTICIPANT;

	/**
	 * The feature id for the '<em><b>Participant Association</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PARTICIPANT_ASSOCIATION = Bpmn2Package.DOCUMENT_ROOT__PARTICIPANT_ASSOCIATION;

	/**
	 * The feature id for the '<em><b>Participant Multiplicity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PARTICIPANT_MULTIPLICITY = Bpmn2Package.DOCUMENT_ROOT__PARTICIPANT_MULTIPLICITY;

	/**
	 * The feature id for the '<em><b>Partner Entity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PARTNER_ENTITY = Bpmn2Package.DOCUMENT_ROOT__PARTNER_ENTITY;

	/**
	 * The feature id for the '<em><b>Partner Role</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PARTNER_ROLE = Bpmn2Package.DOCUMENT_ROOT__PARTNER_ROLE;

	/**
	 * The feature id for the '<em><b>Potential Owner</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__POTENTIAL_OWNER = Bpmn2Package.DOCUMENT_ROOT__POTENTIAL_OWNER;

	/**
	 * The feature id for the '<em><b>Process</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PROCESS = Bpmn2Package.DOCUMENT_ROOT__PROCESS;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PROPERTY = Bpmn2Package.DOCUMENT_ROOT__PROPERTY;

	/**
	 * The feature id for the '<em><b>Receive Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RECEIVE_TASK = Bpmn2Package.DOCUMENT_ROOT__RECEIVE_TASK;

	/**
	 * The feature id for the '<em><b>Relationship</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RELATIONSHIP = Bpmn2Package.DOCUMENT_ROOT__RELATIONSHIP;

	/**
	 * The feature id for the '<em><b>Rendering</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RENDERING = Bpmn2Package.DOCUMENT_ROOT__RENDERING;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RESOURCE = Bpmn2Package.DOCUMENT_ROOT__RESOURCE;

	/**
	 * The feature id for the '<em><b>Resource MultipleAssignments SingleAssignment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RESOURCE_ASSIGNMENT_EXPRESSION = Bpmn2Package.DOCUMENT_ROOT__RESOURCE_ASSIGNMENT_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Resource Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RESOURCE_PARAMETER = Bpmn2Package.DOCUMENT_ROOT__RESOURCE_PARAMETER;

	/**
	 * The feature id for the '<em><b>Resource Parameter Binding</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RESOURCE_PARAMETER_BINDING = Bpmn2Package.DOCUMENT_ROOT__RESOURCE_PARAMETER_BINDING;

	/**
	 * The feature id for the '<em><b>Script</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SCRIPT = Bpmn2Package.DOCUMENT_ROOT__SCRIPT;

	/**
	 * The feature id for the '<em><b>Script Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SCRIPT_TASK = Bpmn2Package.DOCUMENT_ROOT__SCRIPT_TASK;

	/**
	 * The feature id for the '<em><b>Send Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SEND_TASK = Bpmn2Package.DOCUMENT_ROOT__SEND_TASK;

	/**
	 * The feature id for the '<em><b>Sequence Flow</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SEQUENCE_FLOW = Bpmn2Package.DOCUMENT_ROOT__SEQUENCE_FLOW;

	/**
	 * The feature id for the '<em><b>Service Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SERVICE_TASK = Bpmn2Package.DOCUMENT_ROOT__SERVICE_TASK;

	/**
	 * The feature id for the '<em><b>Signal</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SIGNAL = Bpmn2Package.DOCUMENT_ROOT__SIGNAL;

	/**
	 * The feature id for the '<em><b>Signal Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SIGNAL_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__SIGNAL_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Standard Loop Characteristics</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__STANDARD_LOOP_CHARACTERISTICS = Bpmn2Package.DOCUMENT_ROOT__STANDARD_LOOP_CHARACTERISTICS;

	/**
	 * The feature id for the '<em><b>Start Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__START_EVENT = Bpmn2Package.DOCUMENT_ROOT__START_EVENT;

	/**
	 * The feature id for the '<em><b>Sub Choreography</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SUB_CHOREOGRAPHY = Bpmn2Package.DOCUMENT_ROOT__SUB_CHOREOGRAPHY;

	/**
	 * The feature id for the '<em><b>Sub Conversation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SUB_CONVERSATION = Bpmn2Package.DOCUMENT_ROOT__SUB_CONVERSATION;

	/**
	 * The feature id for the '<em><b>Sub Process</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SUB_PROCESS = Bpmn2Package.DOCUMENT_ROOT__SUB_PROCESS;

	/**
	 * The feature id for the '<em><b>Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TASK = Bpmn2Package.DOCUMENT_ROOT__TASK;

	/**
	 * The feature id for the '<em><b>Terminate Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TERMINATE_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__TERMINATE_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Text</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TEXT = Bpmn2Package.DOCUMENT_ROOT__TEXT;

	/**
	 * The feature id for the '<em><b>Text Annotation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TEXT_ANNOTATION = Bpmn2Package.DOCUMENT_ROOT__TEXT_ANNOTATION;

	/**
	 * The feature id for the '<em><b>Throw Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__THROW_EVENT = Bpmn2Package.DOCUMENT_ROOT__THROW_EVENT;

	/**
	 * The feature id for the '<em><b>Timer Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TIMER_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__TIMER_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Transaction</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TRANSACTION = Bpmn2Package.DOCUMENT_ROOT__TRANSACTION;

	/**
	 * The feature id for the '<em><b>User Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__USER_TASK = Bpmn2Package.DOCUMENT_ROOT__USER_TASK;

	/**
	 * The feature id for the '<em><b>Global</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GLOBAL = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Import Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__IMPORT_TYPE = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Metadata</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__METADATA = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Metaentry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__METAENTRY = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>On Entry Script</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ON_ENTRY_SCRIPT = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>On Exit Script</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ON_EXIT_SCRIPT = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Bpsim Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BPSIM_DATA = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Package Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PACKAGE_NAME = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PRIORITY = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Rule Flow Group</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RULE_FLOW_GROUP = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Task Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TASK_NAME = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__VERSION = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 11;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 12;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.GlobalTypeImpl <em>Global Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.GlobalTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getGlobalType()
	 * @generated
	 */
	int GLOBAL_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Extension Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_TYPE__EXTENSION_VALUES = Bpmn2Package.ITEM_AWARE_ELEMENT__EXTENSION_VALUES;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_TYPE__DOCUMENTATION = Bpmn2Package.ITEM_AWARE_ELEMENT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Extension Definitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_TYPE__EXTENSION_DEFINITIONS = Bpmn2Package.ITEM_AWARE_ELEMENT__EXTENSION_DEFINITIONS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_TYPE__ID = Bpmn2Package.ITEM_AWARE_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_TYPE__ANY_ATTRIBUTE = Bpmn2Package.ITEM_AWARE_ELEMENT__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Data State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_TYPE__DATA_STATE = Bpmn2Package.ITEM_AWARE_ELEMENT__DATA_STATE;

	/**
	 * The feature id for the '<em><b>Item Subject Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_TYPE__ITEM_SUBJECT_REF = Bpmn2Package.ITEM_AWARE_ELEMENT__ITEM_SUBJECT_REF;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_TYPE__IDENTIFIER = Bpmn2Package.ITEM_AWARE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_TYPE__TYPE = Bpmn2Package.ITEM_AWARE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Global Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_TYPE_FEATURE_COUNT = Bpmn2Package.ITEM_AWARE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.ImportTypeImpl <em>Import Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.ImportTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getImportType()
	 * @generated
	 */
	int IMPORT_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_TYPE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Import Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.MetadataTypeImpl <em>Metadata Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.MetadataTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getMetadataType()
	 * @generated
	 */
	int METADATA_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Metaentry</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_TYPE__METAENTRY = 0;

	/**
	 * The number of structural features of the '<em>Metadata Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.MetaentryTypeImpl <em>Metaentry Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.MetaentryTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getMetaentryType()
	 * @generated
	 */
	int METAENTRY_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAENTRY_TYPE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAENTRY_TYPE__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Metaentry Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAENTRY_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.OnEntryScriptTypeImpl <em>On Entry Script Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.OnEntryScriptTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getOnEntryScriptType()
	 * @generated
	 */
	int ON_ENTRY_SCRIPT_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Script</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ON_ENTRY_SCRIPT_TYPE__SCRIPT = 0;

	/**
	 * The feature id for the '<em><b>Script Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ON_ENTRY_SCRIPT_TYPE__SCRIPT_FORMAT = 1;

	/**
	 * The number of structural features of the '<em>On Entry Script Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ON_ENTRY_SCRIPT_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.OnExitScriptTypeImpl <em>On Exit Script Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.OnExitScriptTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getOnExitScriptType()
	 * @generated
	 */
	int ON_EXIT_SCRIPT_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Script</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ON_EXIT_SCRIPT_TYPE__SCRIPT = 0;

	/**
	 * The feature id for the '<em><b>Script Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ON_EXIT_SCRIPT_TYPE__SCRIPT_FORMAT = 1;

	/**
	 * The number of structural features of the '<em>On Exit Script Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ON_EXIT_SCRIPT_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.BPSimDataTypeImpl <em>BP Sim Data Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.BPSimDataTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getBPSimDataType()
	 * @generated
	 */
	int BP_SIM_DATA_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BP_SIM_DATA_TYPE__GROUP = BpsimPackage.BP_SIM_DATA_TYPE__GROUP;

	/**
	 * The feature id for the '<em><b>Scenario</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BP_SIM_DATA_TYPE__SCENARIO = BpsimPackage.BP_SIM_DATA_TYPE__SCENARIO;

	/**
	 * The number of structural features of the '<em>BP Sim Data Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BP_SIM_DATA_TYPE_FEATURE_COUNT = BpsimPackage.BP_SIM_DATA_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.ExternalProcessImpl <em>Callable Element Proxy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.ExternalProcessImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getExternalProcess()
	 * @generated
	 */
	int EXTERNAL_PROCESS = 8;

	/**
	 * The feature id for the '<em><b>Extension Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_PROCESS__EXTENSION_VALUES = Bpmn2Package.CALLABLE_ELEMENT__EXTENSION_VALUES;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_PROCESS__DOCUMENTATION = Bpmn2Package.CALLABLE_ELEMENT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>Extension Definitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_PROCESS__EXTENSION_DEFINITIONS = Bpmn2Package.CALLABLE_ELEMENT__EXTENSION_DEFINITIONS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_PROCESS__ID = Bpmn2Package.CALLABLE_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_PROCESS__ANY_ATTRIBUTE = Bpmn2Package.CALLABLE_ELEMENT__ANY_ATTRIBUTE;

	/**
	 * The feature id for the '<em><b>Supported Interface Refs</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_PROCESS__SUPPORTED_INTERFACE_REFS = Bpmn2Package.CALLABLE_ELEMENT__SUPPORTED_INTERFACE_REFS;

	/**
	 * The feature id for the '<em><b>Io Specification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_PROCESS__IO_SPECIFICATION = Bpmn2Package.CALLABLE_ELEMENT__IO_SPECIFICATION;

	/**
	 * The feature id for the '<em><b>Io Binding</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_PROCESS__IO_BINDING = Bpmn2Package.CALLABLE_ELEMENT__IO_BINDING;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_PROCESS__NAME = Bpmn2Package.CALLABLE_ELEMENT__NAME;

	/**
	 * The number of structural features of the '<em>Callable Element Proxy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_PROCESS_FEATURE_COUNT = Bpmn2Package.CALLABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '<em>Package Name Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getPackageNameType()
	 * @generated
	 */
	int PACKAGE_NAME_TYPE = 9;

	/**
	 * The meta object id for the '<em>Priority Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.math.BigInteger
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getPriorityType()
	 * @generated
	 */
	int PRIORITY_TYPE = 10;

	/**
	 * The meta object id for the '<em>Rule Flow Group Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getRuleFlowGroupType()
	 * @generated
	 */
	int RULE_FLOW_GROUP_TYPE = 11;

	/**
	 * The meta object id for the '<em>Task Name Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getTaskNameType()
	 * @generated
	 */
	int TASK_NAME_TYPE = 12;

	/**
	 * The meta object id for the '<em>Version Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getVersionType()
	 * @generated
	 */
	int VERSION_TYPE = 13;


	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getGlobal <em>Global</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Global</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getGlobal()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Global();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getImportType <em>Import Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Import Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getImportType()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ImportType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getMetadata <em>Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Metadata</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getMetadata()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Metadata();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getMetaentry <em>Metaentry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Metaentry</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getMetaentry()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Metaentry();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getOnEntryScript <em>On Entry Script</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>On Entry Script</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getOnEntryScript()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_OnEntryScript();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getOnExitScript <em>On Exit Script</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>On Exit Script</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getOnExitScript()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_OnExitScript();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getBpsimData <em>Bpsim Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Bpsim Data</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getBpsimData()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BpsimData();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getPackageName <em>Package Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package Name</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getPackageName()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_PackageName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Priority</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getPriority()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Priority();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getRuleFlowGroup <em>Rule Flow Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rule Flow Group</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getRuleFlowGroup()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_RuleFlowGroup();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getTaskName <em>Task Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Task Name</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getTaskName()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_TaskName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DocumentRoot#getVersion()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Version();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.GlobalType <em>Global Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Global Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.GlobalType
	 * @generated
	 */
	EClass getGlobalType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.GlobalType#getIdentifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Identifier</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.GlobalType#getIdentifier()
	 * @see #getGlobalType()
	 * @generated
	 */
	EAttribute getGlobalType_Identifier();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.GlobalType#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.GlobalType#getType()
	 * @see #getGlobalType()
	 * @generated
	 */
	EAttribute getGlobalType_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.ImportType <em>Import Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Import Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.ImportType
	 * @generated
	 */
	EClass getImportType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.ImportType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.ImportType#getName()
	 * @see #getImportType()
	 * @generated
	 */
	EAttribute getImportType_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.MetadataType <em>Metadata Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metadata Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.MetadataType
	 * @generated
	 */
	EClass getMetadataType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.MetadataType#getMetaentry <em>Metaentry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Metaentry</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.MetadataType#getMetaentry()
	 * @see #getMetadataType()
	 * @generated
	 */
	EReference getMetadataType_Metaentry();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.MetaentryType <em>Metaentry Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metaentry Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.MetaentryType
	 * @generated
	 */
	EClass getMetaentryType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.MetaentryType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.MetaentryType#getName()
	 * @see #getMetaentryType()
	 * @generated
	 */
	EAttribute getMetaentryType_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.MetaentryType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.MetaentryType#getValue()
	 * @see #getMetaentryType()
	 * @generated
	 */
	EAttribute getMetaentryType_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.OnEntryScriptType <em>On Entry Script Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>On Entry Script Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.OnEntryScriptType
	 * @generated
	 */
	EClass getOnEntryScriptType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.OnEntryScriptType#getScript <em>Script</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Script</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.OnEntryScriptType#getScript()
	 * @see #getOnEntryScriptType()
	 * @generated
	 */
	EAttribute getOnEntryScriptType_Script();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.OnEntryScriptType#getScriptFormat <em>Script Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Script Format</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.OnEntryScriptType#getScriptFormat()
	 * @see #getOnEntryScriptType()
	 * @generated
	 */
	EAttribute getOnEntryScriptType_ScriptFormat();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.OnExitScriptType <em>On Exit Script Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>On Exit Script Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.OnExitScriptType
	 * @generated
	 */
	EClass getOnExitScriptType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.OnExitScriptType#getScript <em>Script</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Script</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.OnExitScriptType#getScript()
	 * @see #getOnExitScriptType()
	 * @generated
	 */
	EAttribute getOnExitScriptType_Script();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.OnExitScriptType#getScriptFormat <em>Script Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Script Format</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.OnExitScriptType#getScriptFormat()
	 * @see #getOnExitScriptType()
	 * @generated
	 */
	EAttribute getOnExitScriptType_ScriptFormat();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.BPSimDataType <em>BP Sim Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>BP Sim Data Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.BPSimDataType
	 * @generated
	 */
	EClass getBPSimDataType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.ExternalProcess <em>Callable Element Proxy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Callable Element Proxy</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.ExternalProcess
	 * @generated
	 */
	EClass getExternalProcess();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Package Name Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Package Name Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='packageName_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'"
	 * @generated
	 */
	EDataType getPackageNameType();

	/**
	 * Returns the meta object for data type '{@link java.math.BigInteger <em>Priority Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Priority Type</em>'.
	 * @see java.math.BigInteger
	 * @model instanceClass="java.math.BigInteger"
	 *        extendedMetaData="name='priority_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#integer' minInclusive='1'"
	 * @generated
	 */
	EDataType getPriorityType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Rule Flow Group Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Rule Flow Group Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='ruleFlowGroup_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'"
	 * @generated
	 */
	EDataType getRuleFlowGroupType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Task Name Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Task Name Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='taskName_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'"
	 * @generated
	 */
	EDataType getTaskNameType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Version Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Version Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='version_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'"
	 * @generated
	 */
	EDataType getVersionType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DroolsFactory getDroolsFactory();

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
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DocumentRootImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Global</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__GLOBAL = eINSTANCE.getDocumentRoot_Global();

		/**
		 * The meta object literal for the '<em><b>Import Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__IMPORT_TYPE = eINSTANCE.getDocumentRoot_ImportType();

		/**
		 * The meta object literal for the '<em><b>Metadata</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__METADATA = eINSTANCE.getDocumentRoot_Metadata();

		/**
		 * The meta object literal for the '<em><b>Metaentry</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__METAENTRY = eINSTANCE.getDocumentRoot_Metaentry();

		/**
		 * The meta object literal for the '<em><b>On Entry Script</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ON_ENTRY_SCRIPT = eINSTANCE.getDocumentRoot_OnEntryScript();

		/**
		 * The meta object literal for the '<em><b>On Exit Script</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ON_EXIT_SCRIPT = eINSTANCE.getDocumentRoot_OnExitScript();

		/**
		 * The meta object literal for the '<em><b>Bpsim Data</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__BPSIM_DATA = eINSTANCE.getDocumentRoot_BpsimData();

		/**
		 * The meta object literal for the '<em><b>Package Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__PACKAGE_NAME = eINSTANCE.getDocumentRoot_PackageName();

		/**
		 * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__PRIORITY = eINSTANCE.getDocumentRoot_Priority();

		/**
		 * The meta object literal for the '<em><b>Rule Flow Group</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__RULE_FLOW_GROUP = eINSTANCE.getDocumentRoot_RuleFlowGroup();

		/**
		 * The meta object literal for the '<em><b>Task Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__TASK_NAME = eINSTANCE.getDocumentRoot_TaskName();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__VERSION = eINSTANCE.getDocumentRoot_Version();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.GlobalTypeImpl <em>Global Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.GlobalTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getGlobalType()
		 * @generated
		 */
		EClass GLOBAL_TYPE = eINSTANCE.getGlobalType();

		/**
		 * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GLOBAL_TYPE__IDENTIFIER = eINSTANCE.getGlobalType_Identifier();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GLOBAL_TYPE__TYPE = eINSTANCE.getGlobalType_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.ImportTypeImpl <em>Import Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.ImportTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getImportType()
		 * @generated
		 */
		EClass IMPORT_TYPE = eINSTANCE.getImportType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_TYPE__NAME = eINSTANCE.getImportType_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.MetadataTypeImpl <em>Metadata Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.MetadataTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getMetadataType()
		 * @generated
		 */
		EClass METADATA_TYPE = eINSTANCE.getMetadataType();

		/**
		 * The meta object literal for the '<em><b>Metaentry</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METADATA_TYPE__METAENTRY = eINSTANCE.getMetadataType_Metaentry();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.MetaentryTypeImpl <em>Metaentry Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.MetaentryTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getMetaentryType()
		 * @generated
		 */
		EClass METAENTRY_TYPE = eINSTANCE.getMetaentryType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METAENTRY_TYPE__NAME = eINSTANCE.getMetaentryType_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METAENTRY_TYPE__VALUE = eINSTANCE.getMetaentryType_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.OnEntryScriptTypeImpl <em>On Entry Script Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.OnEntryScriptTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getOnEntryScriptType()
		 * @generated
		 */
		EClass ON_ENTRY_SCRIPT_TYPE = eINSTANCE.getOnEntryScriptType();

		/**
		 * The meta object literal for the '<em><b>Script</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ON_ENTRY_SCRIPT_TYPE__SCRIPT = eINSTANCE.getOnEntryScriptType_Script();

		/**
		 * The meta object literal for the '<em><b>Script Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ON_ENTRY_SCRIPT_TYPE__SCRIPT_FORMAT = eINSTANCE.getOnEntryScriptType_ScriptFormat();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.OnExitScriptTypeImpl <em>On Exit Script Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.OnExitScriptTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getOnExitScriptType()
		 * @generated
		 */
		EClass ON_EXIT_SCRIPT_TYPE = eINSTANCE.getOnExitScriptType();

		/**
		 * The meta object literal for the '<em><b>Script</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ON_EXIT_SCRIPT_TYPE__SCRIPT = eINSTANCE.getOnExitScriptType_Script();

		/**
		 * The meta object literal for the '<em><b>Script Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ON_EXIT_SCRIPT_TYPE__SCRIPT_FORMAT = eINSTANCE.getOnExitScriptType_ScriptFormat();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.BPSimDataTypeImpl <em>BP Sim Data Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.BPSimDataTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getBPSimDataType()
		 * @generated
		 */
		EClass BP_SIM_DATA_TYPE = eINSTANCE.getBPSimDataType();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.ExternalProcessImpl <em>Callable Element Proxy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.ExternalProcessImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getExternalProcess()
		 * @generated
		 */
		EClass EXTERNAL_PROCESS = eINSTANCE.getExternalProcess();

		/**
		 * The meta object literal for the '<em>Package Name Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getPackageNameType()
		 * @generated
		 */
		EDataType PACKAGE_NAME_TYPE = eINSTANCE.getPackageNameType();

		/**
		 * The meta object literal for the '<em>Priority Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.math.BigInteger
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getPriorityType()
		 * @generated
		 */
		EDataType PRIORITY_TYPE = eINSTANCE.getPriorityType();

		/**
		 * The meta object literal for the '<em>Rule Flow Group Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getRuleFlowGroupType()
		 * @generated
		 */
		EDataType RULE_FLOW_GROUP_TYPE = eINSTANCE.getRuleFlowGroupType();

		/**
		 * The meta object literal for the '<em>Task Name Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getTaskNameType()
		 * @generated
		 */
		EDataType TASK_NAME_TYPE = eINSTANCE.getTaskNameType();

		/**
		 * The meta object literal for the '<em>Version Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.impl.DroolsPackageImpl#getVersionType()
		 * @generated
		 */
		EDataType VERSION_TYPE = eINSTANCE.getVersionType();

	}

} //DroolsPackage

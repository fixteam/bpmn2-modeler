/**
 */
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.util;

import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.*;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BPSimDataType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BetaDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BinomialDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BooleanParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.Calendar;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ConstantParameter;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ControlParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.CostParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.DateTimeParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.DistributionParameter;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.DocumentRoot;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.DurationParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ElementParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ElementParametersType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.EnumParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ErlangDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ExpressionParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.FloatingParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.GammaDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.LogNormalDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NegativeExponentialDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NormalDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NumericParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.Parameter;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ParameterValue;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.PoissonDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.PriorityParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.PropertyParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.PropertyType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ResourceParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.Scenario;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ScenarioParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ScenarioParametersType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.StringParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.TimeParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.TriangularDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.TruncatedNormalDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.UniformDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.UserDistributionDataPointType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.UserDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.VendorExtension;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.WeibullDistributionType;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BpsimPackage
 * @generated
 */
public class BpsimAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BpsimPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BpsimAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = BpsimPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BpsimSwitch<Adapter> modelSwitch =
		new BpsimSwitch<Adapter>() {
			@Override
			public Adapter caseBetaDistributionType(BetaDistributionType object) {
				return createBetaDistributionTypeAdapter();
			}
			@Override
			public Adapter caseBinomialDistributionType(BinomialDistributionType object) {
				return createBinomialDistributionTypeAdapter();
			}
			@Override
			public Adapter caseBooleanParameterType(BooleanParameterType object) {
				return createBooleanParameterTypeAdapter();
			}
			@Override
			public Adapter caseBPSimDataType(BPSimDataType object) {
				return createBPSimDataTypeAdapter();
			}
			@Override
			public Adapter caseCalendar(Calendar object) {
				return createCalendarAdapter();
			}
			@Override
			public Adapter caseConstantParameter(ConstantParameter object) {
				return createConstantParameterAdapter();
			}
			@Override
			public Adapter caseControlParameters(ControlParameters object) {
				return createControlParametersAdapter();
			}
			@Override
			public Adapter caseCostParameters(CostParameters object) {
				return createCostParametersAdapter();
			}
			@Override
			public Adapter caseDateTimeParameterType(DateTimeParameterType object) {
				return createDateTimeParameterTypeAdapter();
			}
			@Override
			public Adapter caseDistributionParameter(DistributionParameter object) {
				return createDistributionParameterAdapter();
			}
			@Override
			public Adapter caseDocumentRoot(DocumentRoot object) {
				return createDocumentRootAdapter();
			}
			@Override
			public Adapter caseDurationParameterType(DurationParameterType object) {
				return createDurationParameterTypeAdapter();
			}
			@Override
			public Adapter caseElementParameters(ElementParameters object) {
				return createElementParametersAdapter();
			}
			@Override
			public Adapter caseElementParametersType(ElementParametersType object) {
				return createElementParametersTypeAdapter();
			}
			@Override
			public Adapter caseEnumParameterType(EnumParameterType object) {
				return createEnumParameterTypeAdapter();
			}
			@Override
			public Adapter caseErlangDistributionType(ErlangDistributionType object) {
				return createErlangDistributionTypeAdapter();
			}
			@Override
			public Adapter caseExpressionParameterType(ExpressionParameterType object) {
				return createExpressionParameterTypeAdapter();
			}
			@Override
			public Adapter caseFloatingParameterType(FloatingParameterType object) {
				return createFloatingParameterTypeAdapter();
			}
			@Override
			public Adapter caseGammaDistributionType(GammaDistributionType object) {
				return createGammaDistributionTypeAdapter();
			}
			@Override
			public Adapter caseLogNormalDistributionType(LogNormalDistributionType object) {
				return createLogNormalDistributionTypeAdapter();
			}
			@Override
			public Adapter caseNegativeExponentialDistributionType(NegativeExponentialDistributionType object) {
				return createNegativeExponentialDistributionTypeAdapter();
			}
			@Override
			public Adapter caseNormalDistributionType(NormalDistributionType object) {
				return createNormalDistributionTypeAdapter();
			}
			@Override
			public Adapter caseNumericParameterType(NumericParameterType object) {
				return createNumericParameterTypeAdapter();
			}
			@Override
			public Adapter caseParameter(Parameter object) {
				return createParameterAdapter();
			}
			@Override
			public Adapter caseParameterValue(ParameterValue object) {
				return createParameterValueAdapter();
			}
			@Override
			public Adapter casePoissonDistributionType(PoissonDistributionType object) {
				return createPoissonDistributionTypeAdapter();
			}
			@Override
			public Adapter casePriorityParameters(PriorityParameters object) {
				return createPriorityParametersAdapter();
			}
			@Override
			public Adapter casePropertyParameters(PropertyParameters object) {
				return createPropertyParametersAdapter();
			}
			@Override
			public Adapter casePropertyType(PropertyType object) {
				return createPropertyTypeAdapter();
			}
			@Override
			public Adapter caseResourceParameters(ResourceParameters object) {
				return createResourceParametersAdapter();
			}
			@Override
			public Adapter caseScenario(Scenario object) {
				return createScenarioAdapter();
			}
			@Override
			public Adapter caseScenarioParameters(ScenarioParameters object) {
				return createScenarioParametersAdapter();
			}
			@Override
			public Adapter caseScenarioParametersType(ScenarioParametersType object) {
				return createScenarioParametersTypeAdapter();
			}
			@Override
			public Adapter caseStringParameterType(StringParameterType object) {
				return createStringParameterTypeAdapter();
			}
			@Override
			public Adapter caseTimeParameters(TimeParameters object) {
				return createTimeParametersAdapter();
			}
			@Override
			public Adapter caseTriangularDistributionType(TriangularDistributionType object) {
				return createTriangularDistributionTypeAdapter();
			}
			@Override
			public Adapter caseTruncatedNormalDistributionType(TruncatedNormalDistributionType object) {
				return createTruncatedNormalDistributionTypeAdapter();
			}
			@Override
			public Adapter caseUniformDistributionType(UniformDistributionType object) {
				return createUniformDistributionTypeAdapter();
			}
			@Override
			public Adapter caseUserDistributionDataPointType(UserDistributionDataPointType object) {
				return createUserDistributionDataPointTypeAdapter();
			}
			@Override
			public Adapter caseUserDistributionType(UserDistributionType object) {
				return createUserDistributionTypeAdapter();
			}
			@Override
			public Adapter caseVendorExtension(VendorExtension object) {
				return createVendorExtensionAdapter();
			}
			@Override
			public Adapter caseWeibullDistributionType(WeibullDistributionType object) {
				return createWeibullDistributionTypeAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BetaDistributionType <em>Beta Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BetaDistributionType
	 * @generated
	 */
	public Adapter createBetaDistributionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BinomialDistributionType <em>Binomial Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BinomialDistributionType
	 * @generated
	 */
	public Adapter createBinomialDistributionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BooleanParameterType <em>Boolean Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BooleanParameterType
	 * @generated
	 */
	public Adapter createBooleanParameterTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BPSimDataType <em>BP Sim Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.BPSimDataType
	 * @generated
	 */
	public Adapter createBPSimDataTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.Calendar <em>Calendar</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.Calendar
	 * @generated
	 */
	public Adapter createCalendarAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ConstantParameter <em>Constant Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ConstantParameter
	 * @generated
	 */
	public Adapter createConstantParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ControlParameters <em>Control Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ControlParameters
	 * @generated
	 */
	public Adapter createControlParametersAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.CostParameters <em>Cost Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.CostParameters
	 * @generated
	 */
	public Adapter createCostParametersAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.DateTimeParameterType <em>Date Time Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.DateTimeParameterType
	 * @generated
	 */
	public Adapter createDateTimeParameterTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.DistributionParameter <em>Distribution Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.DistributionParameter
	 * @generated
	 */
	public Adapter createDistributionParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.DocumentRoot
	 * @generated
	 */
	public Adapter createDocumentRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.DurationParameterType <em>Duration Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.DurationParameterType
	 * @generated
	 */
	public Adapter createDurationParameterTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ElementParameters <em>Element Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ElementParameters
	 * @generated
	 */
	public Adapter createElementParametersAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ElementParametersType <em>Element Parameters Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ElementParametersType
	 * @generated
	 */
	public Adapter createElementParametersTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.EnumParameterType <em>Enum Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.EnumParameterType
	 * @generated
	 */
	public Adapter createEnumParameterTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ErlangDistributionType <em>Erlang Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ErlangDistributionType
	 * @generated
	 */
	public Adapter createErlangDistributionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ExpressionParameterType <em>SingleAssignment Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ExpressionParameterType
	 * @generated
	 */
	public Adapter createExpressionParameterTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.FloatingParameterType <em>Floating Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.FloatingParameterType
	 * @generated
	 */
	public Adapter createFloatingParameterTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.GammaDistributionType <em>Gamma Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.GammaDistributionType
	 * @generated
	 */
	public Adapter createGammaDistributionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.LogNormalDistributionType <em>Log Normal Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.LogNormalDistributionType
	 * @generated
	 */
	public Adapter createLogNormalDistributionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NegativeExponentialDistributionType <em>Negative Exponential Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NegativeExponentialDistributionType
	 * @generated
	 */
	public Adapter createNegativeExponentialDistributionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NormalDistributionType <em>Normal Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NormalDistributionType
	 * @generated
	 */
	public Adapter createNormalDistributionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NumericParameterType <em>Numeric Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.NumericParameterType
	 * @generated
	 */
	public Adapter createNumericParameterTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.Parameter
	 * @generated
	 */
	public Adapter createParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ParameterValue <em>Parameter Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ParameterValue
	 * @generated
	 */
	public Adapter createParameterValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.PoissonDistributionType <em>Poisson Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.PoissonDistributionType
	 * @generated
	 */
	public Adapter createPoissonDistributionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.PriorityParameters <em>Priority Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.PriorityParameters
	 * @generated
	 */
	public Adapter createPriorityParametersAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.PropertyParameters <em>Property Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.PropertyParameters
	 * @generated
	 */
	public Adapter createPropertyParametersAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.PropertyType <em>Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.PropertyType
	 * @generated
	 */
	public Adapter createPropertyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ResourceParameters <em>Resource Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ResourceParameters
	 * @generated
	 */
	public Adapter createResourceParametersAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.Scenario <em>Scenario</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.Scenario
	 * @generated
	 */
	public Adapter createScenarioAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ScenarioParameters <em>Scenario Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ScenarioParameters
	 * @generated
	 */
	public Adapter createScenarioParametersAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ScenarioParametersType <em>Scenario Parameters Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.ScenarioParametersType
	 * @generated
	 */
	public Adapter createScenarioParametersTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.StringParameterType <em>String Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.StringParameterType
	 * @generated
	 */
	public Adapter createStringParameterTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.TimeParameters <em>Time Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.TimeParameters
	 * @generated
	 */
	public Adapter createTimeParametersAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.TriangularDistributionType <em>Triangular Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.TriangularDistributionType
	 * @generated
	 */
	public Adapter createTriangularDistributionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.TruncatedNormalDistributionType <em>Truncated Normal Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.TruncatedNormalDistributionType
	 * @generated
	 */
	public Adapter createTruncatedNormalDistributionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.UniformDistributionType <em>Uniform Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.UniformDistributionType
	 * @generated
	 */
	public Adapter createUniformDistributionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.UserDistributionDataPointType <em>User Distribution Data Point Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.UserDistributionDataPointType
	 * @generated
	 */
	public Adapter createUserDistributionDataPointTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.UserDistributionType <em>User Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.UserDistributionType
	 * @generated
	 */
	public Adapter createUserDistributionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.VendorExtension <em>Vendor Extension</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.VendorExtension
	 * @generated
	 */
	public Adapter createVendorExtensionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.WeibullDistributionType <em>Weibull Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.WeibullDistributionType
	 * @generated
	 */
	public Adapter createWeibullDistributionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //BpsimAdapterFactory

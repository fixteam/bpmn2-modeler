package org.eclipse.bpmn2.modeler.ui.features.gateway;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.ui.features.gateway.messages"; //$NON-NLS-1$
	public static String AppendGatewayFeature_Description;
	public static String AppendGatewayFeature_Name;
	public static String ComplexGatewayFeatureContainer_Description;
	public static String ComplexGatewayFeatureContainer_Name;
	public static String EventBasedGatewayFeatureContainer_Description;
	public static String EventBasedGatewayFeatureContainer_Name;
	public static String ExclusiveGatewayFeatureContainer_Description;
	public static String ExclusiveGatewayFeatureContainer_Name;
	public static String InclusiveGatewayFeatureContainer_Description;
	public static String InclusiveGatewayFeatureContainer_Name;
	public static String MorphGatewayFeature_Description;
	public static String MorphGatewayFeature_Name;
	public static String ParallelGatewayFeatureContainer_Description;
	public static String ParallelGatewayFeatureContainer_Name;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

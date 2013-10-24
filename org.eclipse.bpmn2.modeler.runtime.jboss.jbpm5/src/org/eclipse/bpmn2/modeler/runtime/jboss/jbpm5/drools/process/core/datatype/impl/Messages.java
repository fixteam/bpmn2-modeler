package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.drools.process.core.datatype.impl;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.drools.process.core.datatype.impl.messages"; //$NON-NLS-1$
	public static String InstanceDataTypeFactory_Cannot_Create;
	public static String NewInstanceDataTypeFactory_Cannot_Create;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

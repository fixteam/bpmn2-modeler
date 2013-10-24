/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc.
 *  All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 *
 * @author Bob Brodt
 ******************************************************************************/

package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.drools.process.core.datatype;

import java.util.Hashtable;

import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.drools.process.core.datatype.impl.NewInstanceDataTypeFactory;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.drools.process.core.datatype.impl.type.BooleanDataType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.drools.process.core.datatype.impl.type.EnumDataType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.drools.process.core.datatype.impl.type.FloatDataType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.drools.process.core.datatype.impl.type.IntegerDataType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.drools.process.core.datatype.impl.type.StringDataType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.drools.process.core.datatype.impl.type.UndefinedDataType;
import org.eclipse.core.runtime.Assert;

/**
 * @author Bob Brodt
 *
 */
public class DataTypeRegistry {

	public static Hashtable<String,DataTypeFactory> instance = null;

	public static DataTypeFactory getFactory(String type) {
		Assert.isTrue(type!=null && !type.isEmpty());
		
		if (instance==null) {
			instance = new Hashtable<String,DataTypeFactory>();
			instance.put("BooleanDataType", new NewInstanceDataTypeFactory(BooleanDataType.class)); //$NON-NLS-1$
			instance.put("EnumDataType", new NewInstanceDataTypeFactory(EnumDataType.class)); //$NON-NLS-1$
			instance.put("FloatDataType", new NewInstanceDataTypeFactory(FloatDataType.class)); //$NON-NLS-1$
			instance.put("IntegerDataType", new NewInstanceDataTypeFactory(IntegerDataType.class)); //$NON-NLS-1$
			instance.put("StringDataType", new NewInstanceDataTypeFactory(StringDataType.class)); //$NON-NLS-1$
			instance.put("UndefinedDataType", new NewInstanceDataTypeFactory(UndefinedDataType.class)); //$NON-NLS-1$
		}
		DataTypeFactory factory = instance.get(type);
		if (factory==null)
			factory = instance.get("UndefinedDataType"); //$NON-NLS-1$
		return factory;
	}
}

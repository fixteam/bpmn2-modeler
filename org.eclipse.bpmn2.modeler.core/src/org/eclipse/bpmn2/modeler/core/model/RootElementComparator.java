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
package org.eclipse.bpmn2.modeler.core.model;

import java.util.Comparator;

import org.eclipse.bpmn2.DataStore;
import org.eclipse.bpmn2.Error;
import org.eclipse.bpmn2.Escalation;
import org.eclipse.bpmn2.EventDefinition;
import org.eclipse.bpmn2.Interface;
import org.eclipse.bpmn2.ItemDefinition;
import org.eclipse.bpmn2.Message;
import org.eclipse.bpmn2.Resource;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.Signal;

public final class RootElementComparator implements Comparator<RootElement> {
	@Override
	public int compare(RootElement a, RootElement b) {
		int aOrder = getOrder(a);
		int bOrder = getOrder(b);
		return aOrder - bOrder;
	}
	
	private int getOrder(RootElement element) {
		if (element instanceof ItemDefinition)
			return 0;
		if (element instanceof Error)
			return 1;
		if (element instanceof Signal)
			return 2;
		if (element instanceof Escalation)
			return 3;
		if (element instanceof Resource)
			return 4;
		if (element instanceof Message)
			return 5;
		if (element instanceof EventDefinition)
			return 6;
		if (element instanceof DataStore)
			return 7;
		if (element instanceof Interface)
			return 8;
		
		return Integer.MAX_VALUE;
	}
}

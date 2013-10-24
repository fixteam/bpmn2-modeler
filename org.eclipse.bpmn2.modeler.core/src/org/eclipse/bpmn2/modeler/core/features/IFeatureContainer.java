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
package org.eclipse.bpmn2.modeler.core.features;

import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;

public interface IFeatureContainer {
	boolean isAvailable(IFeatureProvider fp);
	Object getApplyObject(IContext context);
	boolean canApplyTo(Object o);
	IAddFeature getAddFeature(IFeatureProvider fp);
	IUpdateFeature getUpdateFeature(IFeatureProvider fp);
	IDirectEditingFeature getDirectEditingFeature(IFeatureProvider fp);
	ILayoutFeature getLayoutFeature(IFeatureProvider fp);
	IRemoveFeature getRemoveFeature(IFeatureProvider fp);
	IDeleteFeature getDeleteFeature(IFeatureProvider fp);
	ICustomFeature[] getCustomFeatures(IFeatureProvider fp);

}

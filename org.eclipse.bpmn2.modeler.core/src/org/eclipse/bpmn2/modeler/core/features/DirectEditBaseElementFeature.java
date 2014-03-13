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
 * @author Ivar Meikas
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.features;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.impl.AbstractDirectEditingFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

public class DirectEditBaseElementFeature extends AbstractDirectEditingFeature {

	public DirectEditBaseElementFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public int getEditingType() {
		return TYPE_TEXT;
	}

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		BaseElement be = getBusinessObject(context);
		EStructuralFeature feature = be.eClass().getEStructuralFeature("name");
		if (feature!=null)
			return (String) be.eGet(feature);
		return "";
	}

	@Override
	public void setValue(String value, IDirectEditingContext context) {
		BaseElement be = getBusinessObject(context);
		EStructuralFeature feature = be.eClass().getEStructuralFeature("name");
		if (feature!=null) {
			be.eSet(feature, value);
			PictogramElement e = context.getPictogramElement();
			updatePictogramElement(((Shape) e).getContainer());
		}
	}

	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		PictogramElement pe = context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(pe);
		if (bo instanceof BaseElement && ((BaseElement)bo).eClass().getEStructuralFeature("name")!=null)
			return true;
		return false;
	}

	private BaseElement getBusinessObject(IDirectEditingContext context) {
		return (BaseElement) getBusinessObjectForPictogramElement(context.getPictogramElement());
	}
}

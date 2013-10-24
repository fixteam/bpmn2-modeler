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

package org.eclipse.bpmn2.modeler.runtime.example;

import java.util.List;

import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.modeler.core.features.IFeatureContainer;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.runtime.example.SampleImageProvider.IconSize;
import org.eclipse.bpmn2.modeler.ui.features.activity.task.CustomConnectionFeatureContainer;
import org.eclipse.bpmn2.modeler.ui.features.flow.SequenceFlowFeatureContainer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.mm.algorithms.styles.LineStyle;
import org.eclipse.graphiti.mm.pictograms.Connection;

/**
 * Example implementation of a Custom Connection feature container. The main things to consider
 * here are:
 * 
 * createFeatureContainer() - creates the Feature Container that is responsible for
 *   building the "custom connection". This can be a subclass of an existing Feature Container
 *   from the editor core, or a new one. Typically, this should be a subclass of the
 *   Feature Container for the type of bpmn2 element defined in the "type" attribute
 *   of this Custom Task extension point.
 * 
 * If your Feature Container extends one of the existing classes from editor core, you should
 * override the following methods:
 * 
 * getAddFeature() - this should override the Add Feature from the chosen Feature Container
 *   base class (see above). Typically you will want to override the decorateShape() method
 *   which allows you to customize the graphical representation of this Custom Connection figure.
 * getCreateFeature() - this MUST be overridden if you intend to add extension attributes to
 *   your business object (bpmn2 element) - see the code example below. You will also want to
 *   provide your own images for the tool palette by overriding getCreateImageId() and
 *   getCreateLargeImageId() in your Create Feature.
 * 
 * @author Bob Brodt
 */
public class SampleCustomSequenceFlowFeatureContainer extends CustomConnectionFeatureContainer {
	
	@Override
	protected IFeatureContainer createFeatureContainer(IFeatureProvider fp) {
		return new SequenceFlowFeatureContainer()
		{

			@Override
			public IAddFeature getAddFeature(IFeatureProvider fp) {
				return new AddSequenceFlowFeature(fp) {

					/* (non-Javadoc)
					 * @see org.eclipse.bpmn2.modeler.ui.features.flow.SequenceFlowFeatureContainer.AddSequenceFlowFeature#decorateConnection(org.eclipse.graphiti.features.context.IAddConnectionContext, org.eclipse.graphiti.mm.pictograms.Connection, org.eclipse.bpmn2.SequenceFlow)
					 * 
					 * This implementation of SequenceFlow's decorateConnection() changes the appearance of the connection
					 * to distinguish it from regular SequenceFlows.
					 */
					@Override
					protected void decorateConnection(IAddConnectionContext context, Connection connection,
							SequenceFlow businessObject) {
						super.decorateConnection(context, connection, businessObject);
						connection.getGraphicsAlgorithm().setLineWidth(3);
						connection.getGraphicsAlgorithm().setLineStyle(LineStyle.DASH);
					}

				};
			}

			@Override
			public ICreateConnectionFeature getCreateConnectionFeature(IFeatureProvider fp) {
				return new CreateSequenceFlowFeature(fp) {

					@Override
					public SequenceFlow createBusinessObject(ICreateConnectionContext context) {
						SequenceFlow businessObject = super.createBusinessObject(context);
						return businessObject;
					}

					@Override
					public String getCreateImageId() {
						return SampleImageProvider.getImageId(customTaskDescriptor, IconSize.SMALL);
					}

					@Override
					public String getCreateLargeImageId() {
						return SampleImageProvider.getImageId(customTaskDescriptor, IconSize.LARGE);
					}
				};
			}
		};
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.ui.features.activity.task.CustomTaskFeatureContainer#getId(org.eclipse.emf.ecore.EObject)
	 * 
	 * This method is called by the Feature Provider when it needs to find the Feature Container that will be handling the
	 * creation of a new object. @see org.eclipse.bpmn2.modeler.ui.diagram.BPMNFeatureProvider.getAddFeature(IAddContext).
	 * This method should inspect the object (which will be a bpmn2 element) and determine whether it is responsible for
	 * managing this object's lifecycle, typically by examining extension attributes, as shown in this example.
	 */
	public String getId(EObject object) {
		if (object==null)
			return null;
		List<EStructuralFeature> features = ModelUtil.getAnyAttributes(object);
		for (EStructuralFeature f : features) {
			if ("elementId".equals(f.getName())) {
				Object attrValue = object.eGet(f);
				if ("org.eclipse.bpmn2.modeler.runtime.example.flow".equals(attrValue))
					return attrValue.toString();
			}
		}
		return null;
	}
}

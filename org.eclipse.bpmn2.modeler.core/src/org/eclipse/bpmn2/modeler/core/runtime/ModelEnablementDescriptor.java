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

package org.eclipse.bpmn2.modeler.core.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.di.BpmnDiPackage;
import org.eclipse.bpmn2.modeler.core.features.IBpmn2CreateFeature;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.preferences.ModelEnablements;
import org.eclipse.bpmn2.modeler.core.preferences.ToolProfilesPreferencesHelper;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil.Bpmn2DiagramType;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IFeature;

/**
 * @author Bob Brodt
 *
 */
public class ModelEnablementDescriptor extends BaseRuntimeDescriptor {

	// Model Types that are enabled
	private ModelEnablements modelEnablements;
	// BPMN Diagram Type
	private Bpmn2DiagramType diagramType;
	// Tool Profile name
	private String profile;

	
	// require a TargetRuntime!
	private ModelEnablementDescriptor() {
	}
	
	public ModelEnablementDescriptor(TargetRuntime rt) {
		super(rt);
		modelEnablements = new ModelEnablements(rt, null, null);
//		modelEnablements.setEnabledAll(true);
	}

	public void setDiagramType(Bpmn2DiagramType type) {
		this.diagramType = type;
	}
	
	public Bpmn2DiagramType getDiagramType() {
		return diagramType;
	}
	
	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	public void setEnabled(EClass eClass, boolean enabled) {
		modelEnablements.setEnabled(eClass, enabled);
	}
	
	public void setEnabled(String className, boolean enabled) {
		modelEnablements.setEnabled(className, enabled);
	}
	
	public void setEnabled(String className, String featureName, boolean enabled) {
		modelEnablements.setEnabled(className,  featureName, enabled);
	}

	public void initializeFromTargetRuntime(TargetRuntime rt, Bpmn2DiagramType type, String profile) {
		ModelEnablementDescriptor med = rt.getModelEnablements(type, profile);
		Collection<String> enabledClasses = med.modelEnablements.getAllEnabledClasses();
		for (String c : enabledClasses) {
			Collection<String> enabledFeatures = med.modelEnablements.getAllEnabledFeatures(c);
			for (String f : enabledFeatures) {
				setEnabled(c, f, true);
			}
		}
	}

	public boolean isEnabled(String className, String featureName) {
		return modelEnablements.isEnabled(className, featureName);
	}
	
	public boolean isEnabled(EClass eClass, EStructuralFeature feature) {
		return modelEnablements.isEnabled(eClass, feature);
	}
	
	public boolean isEnabled(EClass eClass) {
		return modelEnablements.isEnabled(eClass);
	}

	public boolean isEnabled(String className) {
		return modelEnablements.isEnabled(className);
	}
	
	public boolean isEnabled(IFeature feature) {
		return modelEnablements.isEnabled(feature);
	}

	public Collection<String> getAllEnabled() {
		return modelEnablements.getAllEnabled();
	}
}

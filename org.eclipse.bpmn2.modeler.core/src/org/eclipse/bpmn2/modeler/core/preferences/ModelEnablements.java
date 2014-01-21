package org.eclipse.bpmn2.modeler.core.preferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.di.BpmnDiPackage;
import org.eclipse.bpmn2.modeler.core.features.IBpmn2CreateFeature;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil.Bpmn2DiagramType;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IFeature;

public class ModelEnablements {
	
	// Map of enabled EClasses and their enabled Features
	private Hashtable<String, HashSet<String>> classes = new Hashtable<String, HashSet<String>>();
	private TargetRuntime targetRuntime = null;
	private Bpmn2DiagramType diagramType;
	private String profile;
	private int enableIdAttribute = -1;
	
	// require a TargetRuntime!
	private ModelEnablements() {
	}

	public ModelEnablements(TargetRuntime targetRuntime,  Bpmn2DiagramType diagramType, String profile) {
		this.targetRuntime = targetRuntime;
		this.diagramType = diagramType;
		this.profile = profile;
	}

	public void setEnableIdAttribute(boolean enabled) {
		enableIdAttribute = enabled ? 1 : 0;
	}

	public boolean getEnableIdAttribute() {
		return enableIdAttribute == 1;
	}
	
	public void setEnabledAll(boolean enabled) {
		if (enabled) {
			classes.clear();
			final List<EClass> items = new ArrayList<EClass>();
			for (EClassifier eclassifier : Bpmn2Package.eINSTANCE.getEClassifiers() ) {
				if (eclassifier instanceof EClass) {
					items.add((EClass)eclassifier);
				}
			}
			setEnabled(items,true);
//			setEnabled(getSubClasses(i.getFlowElement()), true);
//			setEnabled(getSubClasses(i.getDataAssociation()), true);
//			setEnabled(getSubClasses(i.getRootElement()), true);
//			setEnabled(getSubClasses(i.getEventDefinition()), true);
//			setEnabled(getSubClasses(i.getLoopCharacteristics()), true);
//			setEnabled(getSubClasses(i.getExpression()), true);
//			setEnabled(i.getDefinitions(), true);
//			setEnabled(i.getOperation(), true);
//			setEnabled(i.getLane(), true);
//			setEnabled(i.getEscalation(), true);
//			setEnabled(getSubClasses(i.getPotentialOwner()), true);
//			setEnabled(i.getResourceAssignmentExpression(), true);
//			setEnabled(i.getInputSet(), true);
//			setEnabled(i.getOutputSet(), true);
//			setEnabled(i.getAssignment(), true);
//			setEnabled(i.getAssociation(), true);
//			setEnabled(i.getTextAnnotation(), true);
//			setEnabled(i.getMessageFlow(), true);
//			setEnabled(i.getConversationLink(), true);
//			setEnabled(i.getGroup(), true);
//			setEnabled(i.getConversation(), true);
		}
		else {
			classes.clear();
		}
	}
	
	public void copy(ModelEnablements me) {
		classes.clear();
		
		for (Entry<String, HashSet<String>> entry : me.classes.entrySet()) {
			String className = entry.getKey();
			HashSet<String> features = new HashSet<String>();
			features.addAll(entry.getValue());
			classes.put(className, features);
		}
	}
	
	private void setEnabled(List<EClass> eClasses, boolean enabled) {
		for (EClass c : eClasses) {
			setEnabled(c,enabled);
		}
	}
	
	public void setEnabled(String[] list) {
		setEnabledAll(false);
		for (String s : list) {
			int i = s.indexOf("."); //$NON-NLS-1$
			setEnabled(s.substring(0,i), s.substring(i+1), true);
		}
	}

	public void setEnabled(EClass eClass, boolean enabled) {
		if (isValid(eClass)) {
			if (enabled) {
				if (!classes.containsKey(eClass.getName()))
					setEnabled(eClass.getName(), true);
			}
			else {
				setEnabled(eClass.getName(), false);
			}
		}
	}
	
	/**
	 * Check if the given EClass is a candidate for enablement, by checking if its containing
	 * EPackage is either the one defined by the TargetRuntime, or one of the BPMN2 packages.
	 * 
	 * @param eClass - the EClass to check.
	 * @return true if the EClass is valid, false if not.
	 */
	private boolean isValid(EClass eClass) {
		if (eClass!=null && eClass.getInstanceClass() != EObject.class) {
			EPackage pkg = eClass.getEPackage();
			return	pkg==targetRuntime.getModelDescriptor().getEPackage() ||
					pkg==Bpmn2Package.eINSTANCE ||
					pkg==BpmnDiPackage.eINSTANCE;
		}
		return false;
	}
	
	/**
	 * Find an EClass by name. The EPackage defined by the TargetRuntime is searched first,
	 * then the BPMN2 packages.
	 * 
	 * @param className - name of an EClass.
	 * @return the EClass instance or null if not found.
	 */
	private EClass getEClass(String className) {
		// try the runtime package first
		EClass eClass = (EClass)targetRuntime.getModelDescriptor().getEPackage().getEClassifier(className);
		// then all BPMN2 packages
		if (eClass==null)
			eClass = (EClass)Bpmn2Package.eINSTANCE.getEClassifier(className);
		if (eClass==null)
			eClass = (EClass)BpmnDiPackage.eINSTANCE.getEClassifier(className);
		
		// TODO: do we need these?
//		if (eClass==null)
//			eClass = (EClass)DcPackage.eINSTANCE.getEClassifier(className);
//		if (eClass==null)
//			eClass = (EClass)DiPackage.eINSTANCE.getEClassifier(className);
		return eClass;
	}
	
	private void setEnabledSingle(EClass eClass, boolean enabled) {
		if (isValid(eClass)) {
			String className = eClass.getName();
			if (enabled) {
				if (classes.containsKey(className))
					return;
				HashSet<String> features = new HashSet<String>();
				classes.put(className, features);
			}
			else {
				if (!classes.containsKey(className))
					return;
				classes.remove(className);
			}
		}
	}
	
	/**
	 * Sets enablement for the given named element, which may be either just an EClass
	 * name or an EClass feature.
	 * 
	 * @param name - element name which may be in the form "eclass" or "eclass.feature" 
	 * @param enabled - if true add the EClass and/or EClass and Feature to our class map,
	 * if false, remove the element.
	 */
	public void setEnabled(String name, boolean enabled) {
		int i = name.indexOf("."); //$NON-NLS-1$
		if (i>0) {
			setEnabled(name.substring(0,i), name.substring(i+1), enabled);
			return;
		}
		// enable or disable just the class
		EClass eClass = getEClass(name);
		setEnabledSingle(eClass, enabled);
		
		if (enabled) {
			// and enable all of its contained and referenced types
			if (eClass!=null) {
				HashSet<String> features = classes.get(name);
				
				for (EAttribute a : eClass.getEAllAttributes()) {
					features.add(a.getName());
				}
				for (EReference a : eClass.getEAllContainments()) {
					features.add(a.getName());
//					setEnabledSingle(a.getEReferenceType(), true);
				}
				for (EReference a : eClass.getEAllReferences()) {
					features.add(a.getName());
//					setEnabledSingle(a.getEReferenceType(), true);
				}
			}
		}
		else {
			// remove any reference or containment list features
			// of this type for other elements 
			List<String> removed = new ArrayList<String>();
			for (Entry<String, HashSet<String>> entry : classes.entrySet()) {
				EClass ec = getEClass(entry.getKey());
				if (ec!=null) {
					HashSet<String> features = entry.getValue();
	
					for (EReference a : ec.getEAllContainments()) {
						// if this feature is a reference to the
						// class being disabled, remove it
						if (a.getEReferenceType() == eClass)
							removed.add(a.getName());
					}
					for (EReference a : ec.getEAllReferences()) {
						if (a.getEReferenceType() == eClass)
							removed.add(a.getName());
					}
					features.removeAll(removed);
				}
			}
		}
	}
	
	/**
	 * Sets enablement for the given EClass as well as all EClasses referenced by it.
	 * NOTE: Not sure if this is required/wanted for the UI.
	 * 
	 * @param eClass - the EClass which will be added to, or removed from our list of
	 * enabled classes, depending on the "enabled" flag. 
	 * @param enabled - if true, add the EClass and all of its referenced classes,
	 * if false, remove the EClass and all of its referenced classes.
	 */
	public void setEnabledAll(EClass eClass, boolean enabled) {

		// enable or disable the class
		setEnabledSingle(eClass,enabled);
		
		if (enabled) {
			// and enable all of its contained and referenced types
			if (eClass!=null) {
				HashSet<String> features = classes.get(eClass.getName());
				
				for (EAttribute a : eClass.getEAllAttributes()) {
					features.add(a.getName());
				}
				for (EReference a : eClass.getEAllContainments()) {
					features.add(a.getName());
					setEnabledSingle(a.getEReferenceType(), true);
				}
				for (EReference a : eClass.getEAllReferences()) {
					features.add(a.getName());
					setEnabledSingle(a.getEReferenceType(), true);
				}
			}
		}
		else {
			// remove any reference or containment list features
			// of this type for other elements 
			List<String> removed = new ArrayList<String>();
			for (Entry<String, HashSet<String>> entry : classes.entrySet()) {
				EClass ec = getEClass(entry.getKey());
				if (ec!=null) {
					HashSet<String> features = entry.getValue();
	
					for (EReference a : ec.getEAllContainments()) {
						// if this feature is a reference to the
						// class being disabled, remove it
						if (a.getEReferenceType() == eClass)
							removed.add(a.getName());
					}
					for (EReference a : ec.getEAllReferences()) {
						if (a.getEReferenceType() == eClass)
							removed.add(a.getName());
					}
					features.removeAll(removed);
				}
			}
		}
	}
	
	public void setEnabled(String className, String featureName, boolean enabled) {
		if ("all".equals(className)) { //$NON-NLS-1$
			// enable all model objects
			if (featureName==null)
				setEnabledAll(enabled);
			else {
				// enable feature for all classes
				for (Entry<String, HashSet<String>> entry : classes.entrySet()) {
					HashSet<String> features = entry.getValue();
					if (enabled)
						features.add(featureName);
					else
						features.remove(featureName);
				}
			}
		}
		else if (featureName!=null && !featureName.isEmpty()) {
			if ("all".equals(featureName)) { //$NON-NLS-1$
				if (enabled) {
					setEnabled(className,true);
				}
				else
				{
					if (classes.containsKey(className)) {
						classes.get(className).clear();
					}
				}
			}
			else if (enabled) {
				HashSet<String> features;
				if (classes.containsKey(className)) {
					features = classes.get(className);
				}
				else {
					features = new HashSet<String>();
					classes.put(className, features);
				}
				features.add(featureName);
			}
			else {
				if (classes.containsKey(className)) {
					classes.get(className).remove(featureName);
				}
			}
		}
		else
			setEnabled(className, enabled);
	}

	public boolean isEnabled(String className, String featureName) {
		if ("id".equals(featureName)) { //$NON-NLS-1$
			// this needs to happen very late in the lifecycle of this class because we don't want
			// to force loading of the Bpmn2Preferences (and setting up default preference values)
			// before all of the TargetRuntimes have been loaded by TargetRuntime.getAllRuntimes().
			// See Bpmn2Preferences#loadDefaults()
			if (enableIdAttribute== -1) {
				Bpmn2Preferences prefs = Bpmn2Preferences.getInstance(targetRuntime.getResource());
				setEnableIdAttribute(prefs.getShowIdAttribute());
			}
			if (!getEnableIdAttribute())
				return false;
		}
		if (className==null)
			return true;
		if (classes.containsKey(className)) { // && isOverride()) {
			if (featureName!=null && !featureName.isEmpty()) {
				HashSet<String> features = classes.get(className);
				return features.contains(featureName);
			}
			return true;
		}
		
		return false; //!isOverride();
	}
	
	public boolean isEnabled(EClass eClass, EStructuralFeature feature) {
		if (feature==null)
			return isEnabled(eClass);
		return isEnabled(eClass.getName(), feature.getName());
	}
	
	public boolean isEnabled(EClass eClass) {
		if (eClass==null)
			return false;
		return isEnabled(eClass.getName());
	}

	public boolean isEnabled(String className) {
		int i = className.indexOf("."); //$NON-NLS-1$
		if (i>0) {
			return isEnabled(className.substring(0,i), className.substring(i+1));
		}
		return isEnabled(className, null);
	}
	
	public boolean isEnabled(IFeature feature) {
		if (feature instanceof IBpmn2CreateFeature) {
			EClass eClass = ((IBpmn2CreateFeature)feature).getBusinessObjectClass();
			return isEnabled(eClass);
		}
		return false;
	}
	
	public int size() {
		return classes.size();
	}
	
	public List<String> getAllEnabled() {
		ArrayList<String> list = new ArrayList<String>();
		for (Entry<String, HashSet<String>> entry : classes.entrySet()) {
			String className = entry.getKey();
			list.add(className);
			HashSet<String> features = entry.getValue();
			for (String featureName : features) {
				list.add(className + "." + featureName); //$NON-NLS-1$
			}
		}
		return list;
	}
	
	public Collection<String> getAllEnabledClasses() {
		ArrayList<String> list = new ArrayList<String>();
		for (Entry<String, HashSet<String>> entry : classes.entrySet()) {
			String className = entry.getKey();
			list.add(className);
		}
		return list;
	}
	
	public Collection<String> getAllEnabledFeatures(String className) {
		if (classes.containsKey(className))
			return classes.get(className);
		return new ArrayList<String>();
	}
	
	public static List<EClass> getSubClasses(EClass parentClass) {

		List<EClass> classList = new ArrayList<EClass>();
		EList<EClassifier> classifiers = Bpmn2Package.eINSTANCE.getEClassifiers();

		for (EClassifier classifier : classifiers) {
			if (classifier instanceof EClass) {
				EClass clazz = (EClass) classifier;

				clazz.getEAllSuperTypes().contains(parentClass);
				if (parentClass.isSuperTypeOf(clazz) && !clazz.isAbstract()) {
					classList.add(clazz);
				}
			}
		}
		return classList;
	}

}

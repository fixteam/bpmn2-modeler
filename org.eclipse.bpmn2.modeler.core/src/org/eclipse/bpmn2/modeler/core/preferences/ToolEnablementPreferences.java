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
package org.eclipse.bpmn2.modeler.core.preferences;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.modeler.core.runtime.ModelEnablementDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.ModelExtensionDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.ModelExtensionDescriptor.Property;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

public class ToolEnablementPreferences {

	private final Preferences prefs;

	private static HashSet<EClass> elementSet = new HashSet<EClass>();


	static {
		Bpmn2Package i = Bpmn2Package.eINSTANCE;
		final List<EClass> items = new ArrayList<EClass>();
		for (EClassifier eclassifier : i.getEClassifiers() ) {
			if (eclassifier instanceof EClass && eclassifier!=i.getDocumentRoot()) {
				items.add((EClass)eclassifier);
			}
		}
		elementSet.addAll(items);
	}

	private ToolEnablementPreferences(Preferences prefs) {
		this.prefs = prefs;
	}

	public static ToolEnablementPreferences getPreferences(IProject project) {
		IEclipsePreferences rootNode = Platform.getPreferencesService().getRootNode();
		Preferences prefs = rootNode.node(ProjectScope.SCOPE).node(project.getName())
				.node("org.eclipse.bpmn2.modeler.tools"); //$NON-NLS-1$
		return new ToolEnablementPreferences(prefs);
	}

	public void setEnablements(ModelEnablementDescriptor me) {
		
		Collection<String> enablements = me.getAllEnabled();

		try {
			String keys[] = prefs.keys();
			for (String k : keys)
				prefs.remove(k);
			for (String s : enablements) {
				String className = null;
				String featureName = null;
				if (s.contains(".")) { //$NON-NLS-1$
					String[] a = s.split("\\."); //$NON-NLS-1$
					className = a[0];
					featureName = a[1];
				}
				else
					className = s;
				
				prefs.putBoolean(className, true);
				if (featureName!=null)
					prefs.putBoolean(className+"."+featureName, true); //$NON-NLS-1$
			}
		}
		catch (Exception e) {
		}
	}
	
	public List<ToolEnablement> getAllElements() {
		ArrayList<ToolEnablement> ret = new ArrayList<ToolEnablement>();

		for (EClass e : elementSet) {

			ToolEnablement tool = new ToolEnablement();
			tool.setTool(e);
			tool.setEnabled(isEnabled(e));
			ret.add(tool);

			HashSet<EStructuralFeature> possibleFeatures = new HashSet<EStructuralFeature>();

			ArrayList<ToolEnablement> children = new ArrayList<ToolEnablement>();

			for (EAttribute a : e.getEAllAttributes()) {
				// anyAttribute is always enabled to support
				// extension features.
				if (!"anyAttribute".equals(a.getName())) //$NON-NLS-1$
						possibleFeatures.add(a);
			}

			for (EReference a : e.getEAllContainments()) {
				possibleFeatures.add(a);
			}

			for (EReference a : e.getEAllReferences()) {
				possibleFeatures.add(a);
			}

			for (EStructuralFeature feature : possibleFeatures) {
				ToolEnablement toolEnablement = new ToolEnablement(feature, tool);
				toolEnablement.setEnabled(isEnabled(e, feature));
				children.add(toolEnablement);
			}
			sortTools(children);
			tool.setChildren(children);
		}
		sortTools(ret);
		return ret;
	}
	
	private ToolEnablement findOrCreateTool(List<ToolEnablement> tools, ENamedElement elem, ToolEnablement parent) {
		ToolEnablement t = findTool(tools,elem,parent);
		if (t!=null) {
			t.addFriend(parent);
			return t;
		}
		return new ToolEnablement(elem, parent);
	}
	
	private ToolEnablement findTool(List<ToolEnablement> tools, ENamedElement elem, ToolEnablement parent) {
		for (ToolEnablement tool : tools) {
			ToolEnablement thisParent = tool.getParent();
			if (thisParent!=null && parent!=null) {
				if (thisParent.getTool() == parent.getTool()) {
					for (ToolEnablement childTool : thisParent.getChildren()) {
						ENamedElement thisElem = childTool.getTool();
						if (thisElem == elem) {
							return childTool;
						}
					}
				}
			}
			else if (thisParent==parent) {
				if (tool.getTool() == elem)
					return tool;
			}
			else if (thisParent==null) {
				ToolEnablement t = findTool(tool.getChildren(), elem, parent);
				if (t!=null)
					return t;
			}
		}
		return null;
	}
	
	public List<ToolEnablement> getAllExtensionElements(ModelEnablementDescriptor me, List<ToolEnablement> bpmnTools) {
		
		// Fetch all of the <modelExtension> extension point elements defined
		// in the Target Runtime plugin.
		ArrayList<ToolEnablement> bpmnModelExtensions = new ArrayList<ToolEnablement>();
		
		ToolEnablement bpmnModelExtensionsRoot = new ToolEnablement();
		bpmnModelExtensionsRoot.setEnabled(true);
		bpmnModelExtensionsRoot.setName(Messages.ToolEnablementPreferences_BPMN_Extensions);

		TargetRuntime rt = me.getRuntime();
		for (ModelExtensionDescriptor mx : rt.getModelExtensions()) {
			if (mx.getProperties().size()>0) {
				// this <modelExtension> has at least one <property>
				// that can be enabled or disabled:
				// get the EClass which this <modelExtension> extends
				String className = mx.getType();
				EClassifier eclass = me.getClassifier(className);
				if (eclass instanceof EClass) {
					// and create a ToolEnablement for it
					ToolEnablement tool = new ToolEnablement(eclass, bpmnModelExtensionsRoot);
					// fetch its current enablement state
					tool.setEnabled(isEnabled((EClass)eclass));
					// and add it to our list
					bpmnModelExtensions.add(tool);
				}
			}
			
			// now fetch all of the <property> elements contained
			// in the <modelExtension> element. The result list
			// so far contains only EClass tools; the <property>
			// elements will become their children.
			for (ToolEnablement tool : bpmnModelExtensions) {
				for (ModelExtensionDescriptor me2 : rt.getModelExtensions()) {
					if (tool.getName().equals(me2.getType())) {
						ArrayList<ToolEnablement> children = new ArrayList<ToolEnablement>();
						for (Property p : me2.getProperties()) {
							EClass eclass = (EClass)tool.getTool();
							EStructuralFeature feature = me.getFeature(me2.getType(), p.name);
							if (feature==null)
								feature = me2.getFeature(eclass, p);
							if (feature instanceof EAttribute) {
								ToolEnablement child = findOrCreateTool(bpmnTools, feature, tool);
								// set enablement state of the feature:
								// the EClass is that of the parent tool.
								child.setEnabled(isEnabled(eclass, feature));
								children.add(child);
							}
						}
						// add the sorted list to the children of this tool parent
						sortTools(children);
						tool.setChildren(children);
					}
				}
			}
		}
		sortTools(bpmnModelExtensions);
		bpmnModelExtensionsRoot.setChildren(bpmnModelExtensions);
		
		ToolEnablement runtimeModelExtensionsRoot = new ToolEnablement();
		runtimeModelExtensionsRoot.setEnabled(true);
		runtimeModelExtensionsRoot.setName(Messages.ToolEnablementPreferences_Target_Extensions);

		
		ArrayList<ToolEnablement> runtimeModelExtensions = new ArrayList<ToolEnablement>();

		for (EClassifier ec : rt.getModelDescriptor().getEPackage().getEClassifiers()) {
			if (ec instanceof EClass) {
				EClass eclass = (EClass)ec;
				// skip over DocumentRoot - we'll assume that all of its features are
				// containers of, or references to EClasses which we'll process anyway.
				if (eclass.getName().equals("DocumentRoot")) //$NON-NLS-1$
					continue;
				
				ToolEnablement tool = new ToolEnablement(eclass, runtimeModelExtensionsRoot);
				// fetch its current enablement state
				tool.setEnabled(isEnabled((EClass)eclass));
				// and add it to our list
				runtimeModelExtensions.add(tool);
				
				HashSet<EStructuralFeature> possibleFeatures = new HashSet<EStructuralFeature>();

				ArrayList<ToolEnablement> children = new ArrayList<ToolEnablement>();

				for (EAttribute a : eclass.getEAllAttributes()) {
					possibleFeatures.add(a);
				}

				for (EReference a : eclass.getEAllContainments()) {
					possibleFeatures.add(a);
				}

				for (EReference a : eclass.getEAllReferences()) {
					possibleFeatures.add(a);
				}

				for (EStructuralFeature feature : possibleFeatures) {
					ToolEnablement toolEnablement = findOrCreateTool(bpmnTools, feature, tool);
					toolEnablement.setEnabled(isEnabled(eclass, feature));
					children.add(toolEnablement);
				}
				sortTools(children);
				tool.setChildren(children);
			}
		}
		sortTools(runtimeModelExtensions);
		runtimeModelExtensionsRoot.setChildren(runtimeModelExtensions);

		ArrayList<ToolEnablement> allExtensions = new ArrayList<ToolEnablement>();
//		allExtensions.addAll(bpmnModelExtensions);
//		allExtensions.addAll(runtimeModelExtensions);
		
		allExtensions.add(bpmnModelExtensionsRoot);
		allExtensions.add(runtimeModelExtensionsRoot);
		
		return allExtensions;
	}

	private void sortTools(ArrayList<ToolEnablement> ret) {
		Collections.sort(ret, new Comparator<ToolEnablement>() {

			@Override
			public int compare(ToolEnablement o1, ToolEnablement o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}

		});
	}

	public boolean isEnabled(EClass element) {
		return prefs.getBoolean(element.getName(), false);
	}

	public boolean isEnabled(String name) {
		return prefs.getBoolean(name, false);
	}

	public boolean isEnabled(String name, boolean b) {
		return prefs.getBoolean(name, b);
	}

	public boolean isEnabled(EClass c, ENamedElement element) {
		return prefs.getBoolean(c.getName() + "." + element.getName(), false); //$NON-NLS-1$
	}

	public void setEnabled(ToolEnablement tool, boolean enabled) {
		prefs.putBoolean(tool.getPreferenceName(), enabled);
	}

	public boolean isEnabled(ToolEnablement tool) {
		return prefs.getBoolean(tool.getPreferenceName(), false);
	}

	public void flush() throws BackingStoreException {
		prefs.flush();
	}

	public void importPreferences(String path) throws FileNotFoundException, IOException, BackingStoreException {
		Properties p = new Properties();
		p.load(new FileInputStream(path));

		for (Object k : p.keySet()) {
			Object object = p.get(k);
			if (k instanceof String && object instanceof String) {
				prefs.putBoolean((String) k, Boolean.parseBoolean((String) object));
			}
		}
		prefs.flush();
	}

	public void exportPreferences(String runtimeId, String type, String profile, String path) throws BackingStoreException, FileNotFoundException, IOException {
		FileWriter fw = new FileWriter(path);
		boolean writeXml = path.endsWith(".xml"); //$NON-NLS-1$

		List<String> keys = Arrays.asList(prefs.keys());
		Collections.sort(keys);

		if (writeXml) {
			fw.write("\t\t<modelEnablement"); //$NON-NLS-1$
			if (runtimeId!=null)
				fw.write(" runtimeId=\"" + runtimeId + "\""); //$NON-NLS-1$ //$NON-NLS-2$
			if (type!=null)
				fw.write(" type=\"" + type + "\""); //$NON-NLS-1$ //$NON-NLS-2$
			if (profile!=null)
				fw.write(" profile=\"" + profile + "\""); //$NON-NLS-1$ //$NON-NLS-2$
			fw.write(">\r\n"); //$NON-NLS-1$
			
			fw.write("\t\t\t<disable object=\"all\"/>\r\n"); //$NON-NLS-1$
		}
		
		for (String k : keys) {
			boolean enable = prefs.getBoolean(k, false);
			if (writeXml) {
				if (enable) {
					if (k.contains(".")) { //$NON-NLS-1$
						String a[] = k.split("\\."); //$NON-NLS-1$
						fw.write("\t\t\t<enable object=\""+ a[0] + "\" feature=\"" + a[1] + "\"/>\r\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
				}
			}
			else
				fw.write(k + "=" + enable + "\r\n"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (writeXml) {
			fw.write("\t</modelEnablement>\r\n"); //$NON-NLS-1$
		}
		
		fw.flush();
		fw.close();
	}
}

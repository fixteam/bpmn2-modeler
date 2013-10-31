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
package org.eclipse.bpmn2.modeler.core.preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.AdHocSubProcess;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.CallActivity;
import org.eclipse.bpmn2.CallChoreography;
import org.eclipse.bpmn2.CancelEventDefinition;
import org.eclipse.bpmn2.ChoreographyActivity;
import org.eclipse.bpmn2.Event;
import org.eclipse.bpmn2.EventDefinition;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.InteractionNode;
import org.eclipse.bpmn2.ItemAwareElement;
import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.Message;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.SubChoreography;
import org.eclipse.bpmn2.SubProcess;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.TerminateEventDefinition;
import org.eclipse.bpmn2.Transaction;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.Activator;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle.RoutingStyle;
import org.eclipse.bpmn2.modeler.core.runtime.ModelEnablementDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil.Bpmn2DiagramType;
import org.eclipse.core.internal.resources.ProjectPreferences;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.navigator.ResourceNavigator;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.founder.fix.bpmn2extensions.style.Style;
import com.founder.fix.designer.base.util.StyleConfigUtil;

@SuppressWarnings("restriction")
public class Bpmn2Preferences implements IPreferenceChangeListener, IPropertyChangeListener, IResourceChangeListener {
	public final static String PREF_TARGET_RUNTIME = "target.runtime"; //$NON-NLS-1$
	public final static String PREF_TARGET_RUNTIME_LABEL = Messages.Bpmn2Preferences_Target_Runtime;
	public final static String PREF_SHOW_ADVANCED_PROPERTIES = "show.advanced.properties"; //$NON-NLS-1$
	public final static String PREF_SHOW_ADVANCED_PROPERTIES_LABEL = Messages.Bpmn2Preferences_Show_Advanced_Properties;
	public final static String PREF_SHOW_DESCRIPTIONS = "show.descriptions"; //$NON-NLS-1$
	public final static String PREF_SHOW_DESCRIPTIONS_LABEL = Messages.Bpmn2Preferences_Show_Descriptions;
	public final static String PREF_TOOL_PROFILE = "tool.profile"; //$NON-NLS-1$
	public final static String PREF_MODEL_ENABLEMENT = "model.enablement"; //$NON-NLS-1$
	public final static String PREF_IS_HORIZONTAL = "is.horizontal"; //$NON-NLS-1$
	public final static String PREF_IS_HORIZONTAL_LABEL = Messages.Bpmn2Preferences_Horizontal;
	
	public final static String PREF_IS_EXPANDED = "is.expanded"; //$NON-NLS-1$
	public final static String PREF_IS_EXPANDED_LABEL = Messages.Bpmn2Preferences_Expand;
	public final static String PREF_IS_MESSAGE_VISIBLE = "is.message.visible"; //$NON-NLS-1$
	public final static String PREF_IS_MESSAGE_VISIBLE_LABEL = Messages.Bpmn2Preferences_Message_Visible;
	public final static String PREF_IS_MARKER_VISIBLE = "is.marker.visible"; //$NON-NLS-1$
	public final static String PREF_IS_MARKER_VISIBLE_LABEL = Messages.Bpmn2Preferences_Marker_Visible;
	
	public final static String PREF_SHAPE_STYLE = "shape.style"; //$NON-NLS-1$

	public final static String PREF_CONNECTION_TIMEOUT = "connection.timeout"; //$NON-NLS-1$
	public final static String PREF_CONNECTION_TIMEOUT_LABEL = Messages.Bpmn2Preferences_Timeout;

	public final static String PREF_POPUP_CONFIG_DIALOG = "popup.config.dialog"; //$NON-NLS-1$
	public final static String PREF_POPUP_CONFIG_DIALOG_LABEL = Messages.Bpmn2Preferences_Config_Dialog;
	
	public final static String PREF_POPUP_CONFIG_DIALOG_FOR_ACTIVITIES = "popup.config.dialog.for.activities"; //$NON-NLS-1$
	public final static String PREF_POPUP_CONFIG_DIALOG_FOR_ACTIVITIES_LABEL = Messages.Bpmn2Preferences_Activities;
	public final static String PREF_POPUP_CONFIG_DIALOG_FOR_GATEWAYS = "popup.config.dialog.for.gateways"; //$NON-NLS-1$
	public final static String PREF_POPUP_CONFIG_DIALOG_FOR_GATEWAYS_LABEL = Messages.Bpmn2Preferences_Gateways;
	public final static String PREF_POPUP_CONFIG_DIALOG_FOR_EVENTS = "popup.config.dialog.for.events"; //$NON-NLS-1$
	public final static String PREF_POPUP_CONFIG_DIALOG_FOR_EVENTS_LABEL = Messages.Bpmn2Preferences_Events;
	public final static String PREF_POPUP_CONFIG_DIALOG_FOR_EVENT_DEFS = "popup.config.dialog.for.event.defs"; //$NON-NLS-1$
	public final static String PREF_POPUP_CONFIG_DIALOG_FOR_EVENT_DEFS_LABEL = Messages.Bpmn2Preferences_Event_Definitions;
	public final static String PREF_POPUP_CONFIG_DIALOG_FOR_DATA_DEFS = "popup.config.dialog.for.data.defs"; //$NON-NLS-1$
	public final static String PREF_POPUP_CONFIG_DIALOG_FOR_DATA_DEFS_LABEL = Messages.Bpmn2Preferences_Data_Items;
	public final static String PREF_POPUP_CONFIG_DIALOG_FOR_CONTAINERS = "popup.config.dialog.for.containers"; //$NON-NLS-1$
	public final static String PREF_POPUP_CONFIG_DIALOG_FOR_CONTAINERS_LABEL = Messages.Bpmn2Preferences_Containers;

	public final static String PREF_SHOW_ID_ATTRIBUTE = "show.id.attribute"; //$NON-NLS-1$
	public final static String PREF_SHOW_ID_ATTRIBUTE_LABEL = Messages.Bpmn2Preferences_Show_ID_Attribute;
	public final static String PREF_CHECK_PROJECT_NATURE = "check.project.nature"; //$NON-NLS-1$
	public final static String PREF_CHECK_PROJECT_NATURE_LABEL = Messages.Bpmn2Preferences_Check_Project_Nature;
	public final static String PREF_SIMPLIFY_LISTS = "simplify.lists"; //$NON-NLS-1$
	public final static String PREF_SIMPLIFY_LISTS_LABEL = Messages.Bpmn2Preferences_Simplify_Lists;
	public final static String PREF_DO_CORE_VALIDATION = "do.core.validation"; //$NON-NLS-1$
	public final static String PREF_DO_CORE_VALIDATION_LABEL = Messages.Bpmn2Preferences_Do_Core_Validation;

	private static Hashtable<IProject,Bpmn2Preferences> instances = null;
	private static IProject activeProject;

	private IProject project;
	private static IPreferencesService preferenceService;
	private static IPreferenceStore preferenceStore;
	private boolean useProjectPreferences;
	private Preferences projectPreferences;
	private static Preferences instancePreferences;
	private static Preferences defaultPreferences;
	private boolean loaded;
	private boolean dirty;
	
	public enum BPMNDIAttributeDefault {
		USE_DI_VALUE,
		DEFAULT_TRUE,
		ALWAYS_TRUE,
		ALWAYS_FALSE
	};
	
	private TargetRuntime targetRuntime;
	private boolean showAdvancedPropertiesTab;
	private boolean showDescriptions;
	private boolean showIdAttribute;
	private boolean checkProjectNature;
	private boolean simplifyLists;
	private boolean doCoreValidation;
	private BPMNDIAttributeDefault isHorizontal;
	private BPMNDIAttributeDefault isExpanded;
	private BPMNDIAttributeDefault isMessageVisible;
	private BPMNDIAttributeDefault isMarkerVisible;
	private int connectionTimeout;
	private int popupConfigDialog;
	private boolean popupConfigDialogFor[] = new boolean[6];

	private HashMap<Class, ShapeStyle> shapeStyles = new HashMap<Class, ShapeStyle>();
	
	// TODO: stuff like colors, fonts, etc.

	private Bpmn2Preferences(IProject project) {
		this.project = project;
		if (preferenceService==null)
			preferenceService = Platform.getPreferencesService();
		if (instancePreferences==null)
			instancePreferences = preferenceService.getRootNode().node(InstanceScope.SCOPE).node(Activator.PLUGIN_ID);
		if (defaultPreferences==null)
			defaultPreferences = preferenceService.getRootNode().node(DefaultScope.SCOPE).node(Activator.PLUGIN_ID);
		if (preferenceStore==null)
			preferenceStore = Activator.getDefault().getPreferenceStore();

		projectPreferences = getProjectPreferences(project);
		
		preferenceStore.addPropertyChangeListener(this);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);

		loadDefaults();
		load();
	}
	
	private Preferences[] getAllPreferences() {
		if (projectPreferences!=null) {
			return new Preferences[] {projectPreferences, instancePreferences, defaultPreferences};
		}
		else {
			return new Preferences[] {instancePreferences, defaultPreferences};
		}
	}

	private Preferences getProjectPreferences(IProject project) {
		if (project != null) {
			projectPreferences = preferenceService.getRootNode().node(ProjectScope.SCOPE).node(project.getName()).node(Activator.PLUGIN_ID);
			((ProjectPreferences) projectPreferences).addPreferenceChangeListener(this);
			
			try {
				projectPreferences.sync();
			}
			catch (Exception e) {
				return null;
			}
		}
		return projectPreferences;
	}
	
	// various preference instance getters
	
	/**
	 * Return the Preferences for the currently active project. This should be used
	 * with caution: the active project is set by the BPMN2Editor, so this should only
	 * be used in a context that is known to have an active editor.
	 * 
	 * @return project preferences
	 */
	public static Bpmn2Preferences getInstance() {
		return getInstance(getActiveProject());
	}
	
	/**
	 * Return the Preferences for the project containing the EMF Resource
	 * 
	 * @param resource
	 * @return project preferences
	 */
	public static Bpmn2Preferences getInstance(EObject object) {
		return getInstance(object.eResource());
	}
	
	public static Bpmn2Preferences getInstance(Resource resource) {
		return getInstance(resource.getURI());
	}
	
	/**
	 * Return the Preferences for the project containing the EMF Resource specified
	 * by the resource URI. This must be a Platform URI.
	 * 
	 * @param resourceURI
	 * @return project preferences
	 */
	public static Bpmn2Preferences getInstance(URI resourceURI) {
		String filename = resourceURI.trimFragment().toPlatformString(true);
		if (filename==null) {
			return getInstance();
		}
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		if (root==null) {
			return getInstance();
		}
		IResource res = ResourcesPlugin.getWorkspace().getRoot().findMember(filename);
		if (res==null) {
			return getInstance();
		}
		IProject project = res.getProject();
		return getInstance(project);
			
	}
	
	/**
	 * Return the Preferences for the given project.
	 * 
	 * @param project
	 * @return project preferences
	 */
	public static Bpmn2Preferences getInstance(IProject project) {
		if (instances==null) {
			instances = new Hashtable<IProject,Bpmn2Preferences>();
		}
		Bpmn2Preferences pref;
		if (project==null)
			pref = new Bpmn2Preferences(null);
		else {
			pref = instances.get(project);
			if (pref==null) {
				pref = new Bpmn2Preferences(project);
				instances.put(project, pref);
			}
		}
		return pref;
	}
	
	public IPreferenceStore getPreferenceStore()
	{
		return preferenceStore;
	}
	
	public void useProjectPreferences() {
		Assert.isNotNull(projectPreferences);
		useProjectPreferences = true;
	}

	public Preferences getProjectPreferences()
	{
		return projectPreferences;
	}
	
	private void loadDefaults() {
		if (defaultPreferences.get(PREF_TARGET_RUNTIME, null)==null) {
			String rid = TargetRuntime.getFirstNonDefaultId();
			defaultPreferences.put(PREF_TARGET_RUNTIME, rid);
			defaultPreferences.putBoolean(PREF_SHOW_ADVANCED_PROPERTIES, false);
			defaultPreferences.putBoolean(PREF_CHECK_PROJECT_NATURE, true);
			defaultPreferences.putBoolean(PREF_SIMPLIFY_LISTS, true);
			defaultPreferences.putBoolean(PREF_SHOW_DESCRIPTIONS, true);
			defaultPreferences.put(PREF_IS_HORIZONTAL, BPMNDIAttributeDefault.DEFAULT_TRUE.name());
			defaultPreferences.put(PREF_IS_EXPANDED, BPMNDIAttributeDefault.ALWAYS_TRUE.name());
			defaultPreferences.put(PREF_IS_MESSAGE_VISIBLE, BPMNDIAttributeDefault.ALWAYS_TRUE.name());
			defaultPreferences.put(PREF_IS_MARKER_VISIBLE, BPMNDIAttributeDefault.DEFAULT_TRUE.name());

			defaultPreferences.putInt(PREF_POPUP_CONFIG_DIALOG, 0); // tri-state checkbox
			defaultPreferences.putBoolean(PREF_POPUP_CONFIG_DIALOG_FOR_ACTIVITIES, false);
			defaultPreferences.putBoolean(PREF_POPUP_CONFIG_DIALOG_FOR_GATEWAYS, false);
			defaultPreferences.putBoolean(PREF_POPUP_CONFIG_DIALOG_FOR_EVENTS, false);
			defaultPreferences.putBoolean(PREF_POPUP_CONFIG_DIALOG_FOR_EVENT_DEFS, false);
			defaultPreferences.putBoolean(PREF_POPUP_CONFIG_DIALOG_FOR_DATA_DEFS, false);
			defaultPreferences.putBoolean(PREF_POPUP_CONFIG_DIALOG_FOR_CONTAINERS, false);
			defaultPreferences.putBoolean(PREF_DO_CORE_VALIDATION, true);

			defaultPreferences.put(PREF_CONNECTION_TIMEOUT, "1000"); //$NON-NLS-1$
			
			loadDefaults(PREF_TOOL_PROFILE);
			loadDefaults(PREF_MODEL_ENABLEMENT);
			loadDefaults(PREF_SHAPE_STYLE);
		}
	}

	private void loadDefaults(String key) {
		if (key.equals(PREF_TOOL_PROFILE)) {
			for (TargetRuntime rt : TargetRuntime.getAllRuntimes()) {
				for (Bpmn2DiagramType diagramType : Bpmn2DiagramType.values()) {
					String defaultProfile = null;
					for (ModelEnablementDescriptor med : rt.getModelEnablements(diagramType)) {
						String path = getToolProfilePath(rt, diagramType);
						Preferences prefs = defaultPreferences.node(path);
						if (defaultProfile == null)
							prefs.putBoolean(defaultProfile = med.getProfile(), true);
						else
							prefs.putBoolean(med.getProfile(), false);
					}
				}
			}
			
		}
		else if (key.equals(PREF_MODEL_ENABLEMENT)) {
			for (TargetRuntime rt : TargetRuntime.getAllRuntimes()) {
				for (Bpmn2DiagramType diagramType : Bpmn2DiagramType.values()) {
					for (ModelEnablementDescriptor med : rt.getModelEnablements(diagramType)) {
						String path = getModelEnablementsPath(rt, diagramType, med.getProfile());
						Preferences prefs = defaultPreferences.node(path);
						for (String s : med.getAllEnabled()) {
							prefs.putBoolean(s, Boolean.TRUE);
						}
					}
				}
			}
		}
		else if (key.equals(PREF_SHAPE_STYLE)) {
			// Use ShapeStyles defined in the Default Target Runtime if an extension does not provide its own. 
			Map<Class, ShapeStyle> defaultShapeStyles = TargetRuntime.getDefaultRuntime().getShapeStyles();
			for (TargetRuntime rt : TargetRuntime.getAllRuntimes()) {
				String path = getShapeStylePath(rt);
				Preferences prefs = defaultPreferences.node(path);
				if (rt!=TargetRuntime.getDefaultRuntime()) {
					for (Entry<Class, ShapeStyle> entry : defaultShapeStyles.entrySet()) {
						String value = ShapeStyle.encode(entry.getValue());
						prefs.put(entry.getKey().getSimpleName(), value);
					}
				}
				for (Entry<Class, ShapeStyle> entry : rt.getShapeStyles().entrySet()) {
					String value = ShapeStyle.encode(entry.getValue());
					prefs.put(entry.getKey().getSimpleName(), value);
				}
			}
		}
	}
	
	public boolean shouldSaveToProjectPreference(String key) {
		if (projectPreferences!=null) {
			if (useProjectPreferences) {
				// if saving to project preferences, create the preference node
				projectPreferences.node(key);
				return true;
			}
			// otherwise only save to project preferences if the key already exists.
			try {
				return projectPreferences.nodeExists(key);
			}
			catch (BackingStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public void dispose() {
		if (projectPreferences instanceof ProjectPreferences)
			((ProjectPreferences)projectPreferences).removePreferenceChangeListener(this);
		preferenceStore.removePropertyChangeListener(this);
		if (project!=null)
			instances.remove(project);
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
	}
	
	public synchronized void reload() {
		loaded = false;
		load();
		dirty = false;
	}
	
	public void load() {
		
		if (!loaded) {
			// cache all preferences as Bpmn2Preferences instance variables for faster access
			String id = getString(PREF_TARGET_RUNTIME,TargetRuntime.getFirstNonDefaultId());
			if (id==null || id.isEmpty())
				id = TargetRuntime.getFirstNonDefaultId();
			targetRuntime = TargetRuntime.getRuntime(id);
			showAdvancedPropertiesTab = getBoolean(PREF_SHOW_ADVANCED_PROPERTIES, false);
			showDescriptions = getBoolean(PREF_SHOW_DESCRIPTIONS, false);
			showIdAttribute = getBoolean(PREF_SHOW_ID_ATTRIBUTE, false);
			checkProjectNature = getBoolean(PREF_CHECK_PROJECT_NATURE, false);
			simplifyLists = getBoolean(PREF_SIMPLIFY_LISTS, true);
			isHorizontal = getBPMNDIAttributeDefault(PREF_IS_HORIZONTAL, BPMNDIAttributeDefault.USE_DI_VALUE);
			isExpanded = getBPMNDIAttributeDefault(PREF_IS_EXPANDED, BPMNDIAttributeDefault.USE_DI_VALUE);
			isMessageVisible = getBPMNDIAttributeDefault(PREF_IS_MESSAGE_VISIBLE, BPMNDIAttributeDefault.USE_DI_VALUE);
			isMarkerVisible = getBPMNDIAttributeDefault(PREF_IS_MARKER_VISIBLE, BPMNDIAttributeDefault.USE_DI_VALUE);
			connectionTimeout = getInt(PREF_CONNECTION_TIMEOUT, 60000); //$NON-NLS-1$
			
			popupConfigDialog = getInt(PREF_POPUP_CONFIG_DIALOG, 0); // tri-state checkbox
			popupConfigDialogFor[0] = getBoolean(PREF_POPUP_CONFIG_DIALOG_FOR_ACTIVITIES, false);
			popupConfigDialogFor[1] = getBoolean(PREF_POPUP_CONFIG_DIALOG_FOR_GATEWAYS, false);
			popupConfigDialogFor[2] = getBoolean(PREF_POPUP_CONFIG_DIALOG_FOR_EVENTS, false);
			popupConfigDialogFor[3] = getBoolean(PREF_POPUP_CONFIG_DIALOG_FOR_EVENT_DEFS, false);
			popupConfigDialogFor[4] = getBoolean(PREF_POPUP_CONFIG_DIALOG_FOR_DATA_DEFS, false);
			popupConfigDialogFor[5] = getBoolean(PREF_POPUP_CONFIG_DIALOG_FOR_CONTAINERS, false);

			doCoreValidation = getBoolean(PREF_DO_CORE_VALIDATION, true);

			loaded = true;
		}
	}
	
	public synchronized void save() throws BackingStoreException {
		if (dirty) {
			setString(PREF_TARGET_RUNTIME,getRuntime().getId());
			setBoolean(PREF_SHOW_ADVANCED_PROPERTIES, showAdvancedPropertiesTab);
			setBoolean(PREF_SHOW_DESCRIPTIONS, showDescriptions);
			setBoolean(PREF_SHOW_ID_ATTRIBUTE, showIdAttribute);
			setBoolean(PREF_CHECK_PROJECT_NATURE, checkProjectNature);
			setBoolean(PREF_SIMPLIFY_LISTS, simplifyLists);
			setBPMNDIAttributeDefault(PREF_IS_HORIZONTAL, isHorizontal);

			setBPMNDIAttributeDefault(PREF_IS_EXPANDED, isExpanded);
			setBPMNDIAttributeDefault(PREF_IS_MESSAGE_VISIBLE, isMessageVisible);
			setBPMNDIAttributeDefault(PREF_IS_MARKER_VISIBLE, isMarkerVisible);
			
			setInt(PREF_CONNECTION_TIMEOUT, connectionTimeout);

			setInt(PREF_POPUP_CONFIG_DIALOG, popupConfigDialog);
			setBoolean(PREF_POPUP_CONFIG_DIALOG_FOR_ACTIVITIES, popupConfigDialogFor[0]);
			setBoolean(PREF_POPUP_CONFIG_DIALOG_FOR_GATEWAYS, popupConfigDialogFor[1]);
			setBoolean(PREF_POPUP_CONFIG_DIALOG_FOR_EVENTS, popupConfigDialogFor[2]);
			setBoolean(PREF_POPUP_CONFIG_DIALOG_FOR_EVENT_DEFS, popupConfigDialogFor[3]);
			setBoolean(PREF_POPUP_CONFIG_DIALOG_FOR_DATA_DEFS, popupConfigDialogFor[4]);
			setBoolean(PREF_POPUP_CONFIG_DIALOG_FOR_CONTAINERS, popupConfigDialogFor[5]);
			setBoolean(PREF_DO_CORE_VALIDATION, doCoreValidation);
		}
		
		for (Entry<Class, ShapeStyle> entry : shapeStyles.entrySet()) {
			setShapeStyle(entry.getKey(), entry.getValue());
		}
		
		if (projectPreferences!=null) {
			projectPreferences.flush();
		}

		dirty = false;
	}
	
	public String getShapeStyleId(EObject object) {
		return getShapeStyleId(getRuntime(), object);
	}
	
	public static String getShapeStyleId(TargetRuntime rt, EObject object) {
		try {
			Class clazz = Class.forName(object.eClass().getInstanceClassName());
			return getShapeStyleId(rt, clazz);
		} catch (ClassNotFoundException e) {
			return getShapeStyleId(rt, object.getClass());
		}
	}
	
	public static String getShapeStyleId(TargetRuntime rt, Class clazz) {
		return getShapeStylePath(rt) + "/" + clazz.getSimpleName(); //$NON-NLS-1$
	}
	
	public static String getShapeStylePath(TargetRuntime rt) {
		return PREF_SHAPE_STYLE + "/" + rt.getId(); //$NON-NLS-1$
	}

	public ShapeStyle getShapeStyle(EObject object) {
		Class clazz;
		try {
			clazz = Class.forName(object.eClass().getInstanceClassName());
			return getShapeStyle(clazz);
		} catch (ClassNotFoundException e) {
			return getShapeStyle(object.getClass());
		}
	}
	
/*	public ShapeStyle getShapeStyle(Class clazz) {
		ShapeStyle ss = shapeStyles.get(clazz);
		if (ss==null) {
			String key = getShapeStyleId(getRuntime(), clazz);
			String value = getString(key, "");
			ss = ShapeStyle.decode(value);
			shapeStyles.put(clazz, ss);
		}
		return ss;
	}*/
	
	/**
	 * 获取对应的shapeStyle
	 * 
	 * @param clazz
	 * @return
	 */
	public ShapeStyle getShapeStyle(Class clazz) {
		ShapeStyle ss = loadShapeStyles().get(clazz);
		return ss;
	}
	
	/**
	 * 返回该主题下所有的shapeStyle
	 * 
	 * @return
	 */
	public HashMap<Class, ShapeStyle> loadShapeStyles() {
		Map<Class, ShapeStyle> map = new HashMap<Class, ShapeStyle>();
		for (Style style : StyleConfigUtil.getCurrentStyle().getStyle()) {
			EClass eclass = (EClass) Bpmn2Package.eINSTANCE.getEClassifier(style.getObject());
			ShapeStyle ss = new ShapeStyle(style.getForeground(), style.getBackground(), style.getTextColor(), style.getFont());
			if(enableConnectionRouting) {
				ss.setRoutingStyle(RoutingStyle.Manhattan);
			}
			
			map.put(eclass.getInstanceClass(), ss);
		}
		return (HashMap<Class, ShapeStyle>) map;
	}
	
	public void setShapeStyle(Class clazz, ShapeStyle style) {
		if (style!=null && style.isDirty()) {
			String key = getShapeStyleId(getRuntime(),clazz);
			String value = ShapeStyle.encode(style);
			setString(key, value);
			shapeStyles.put(clazz, style);
			style.setDirty(false);
		}
	}
	
	public TargetRuntime getRuntime() {
		load();
		if (targetRuntime==null) {
			targetRuntime = TargetRuntime.getDefaultRuntime();
			Display.getDefault().asyncExec( new Runnable() {
				@Override
				public void run() {
					String id = getString(PREF_TARGET_RUNTIME,TargetRuntime.getFirstNonDefaultId());
					if (id==null || id.isEmpty())
						id = TargetRuntime.getFirstNonDefaultId();

					targetRuntime = TargetRuntime.getDefaultRuntime();
					MessageDialog.openError(
						Display.getDefault().getActiveShell(),
						Messages.Bpmn2Preferences_No_Runtime_Plugin_Title,
						NLS.bind(
							Messages.Bpmn2Preferences_No_Runtime_Plugin_Message,
							id,
							targetRuntime.getDescription()
						)
					);
				}
				
			});
					
		}
		return targetRuntime;
	}

	public void setRuntime(TargetRuntime rt) {
		Assert.isTrue(rt!=null);
		setString(PREF_TARGET_RUNTIME, rt.getId());
		targetRuntime = rt;
	}
	
	public boolean getShowAdvancedPropertiesTab() {
		return showAdvancedPropertiesTab;
	}
	
	public void setShowAdvancedPropertiesTab(boolean show) {
		setBoolean(PREF_SHOW_ADVANCED_PROPERTIES, show);
		showAdvancedPropertiesTab = show;
	}
	
	public boolean getShowDescriptions() {
		return showDescriptions;
	}
	
	public void setShowDescriptions(boolean show) {
		setBoolean(PREF_SHOW_DESCRIPTIONS, show);
		showDescriptions = show;
	}
	
	public boolean getShowIdAttribute() {
		return showIdAttribute;
	}
	
	public void setShowIdAttribute(boolean show) {
		setBoolean(PREF_SHOW_ID_ATTRIBUTE, show);
		showIdAttribute = show;
	}
	
	public boolean getCheckProjectNature() {
		return checkProjectNature;
	}
	
	public void setCheckProjectNature(boolean show) {
		setBoolean(PREF_CHECK_PROJECT_NATURE, show);
		checkProjectNature = show;
	}
	
	public boolean getSimplifyLists() {
		return simplifyLists;
	}
	
	public void setSimplifyLists(boolean simplify) {
		setBoolean(PREF_SIMPLIFY_LISTS,simplify);
		simplifyLists = simplify;
	}

	public String getDefaultToolProfile(Bpmn2DiagramType diagramType) {
		return getDefaultToolProfile(getRuntime(), diagramType);
	}
	
	public void setDefaultToolProfile(Bpmn2DiagramType diagramType, String profile) {
		setDefaultToolProfile(getRuntime(), diagramType, profile);
	}

	public static String getToolProfilePath(TargetRuntime rt, Bpmn2DiagramType diagramType) {
		return PREF_TOOL_PROFILE + "/" + rt.getId() + "/" + diagramType;
	}
	
	public String getDefaultToolProfile(TargetRuntime rt, Bpmn2DiagramType diagramType) {
		try {
			Preferences prefs = null;
			String path = getToolProfilePath(rt, diagramType);
			if (projectPreferences!=null && projectPreferences.nodeExists(path))
				prefs = projectPreferences.node(path);
			else if (instancePreferences.nodeExists(path))
				prefs = instancePreferences.node(path);
			else if (defaultPreferences.nodeExists(path))
				prefs = defaultPreferences.node(path);

			if (prefs!=null) {
				for (String p : prefs.keys()) {
					if (prefs.getBoolean(p, false))
						return p;
				}
			}
		}
		catch (BackingStoreException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public boolean setDefaultToolProfile(TargetRuntime rt, Bpmn2DiagramType diagramType, String profile) {
		boolean result = false;
		if (profile!=null && !profile.isEmpty()) {
			try {
				Preferences prefs = null;
				String path = getToolProfilePath(rt, diagramType);
				String keys[] = null;
				if (projectPreferences!=null && useProjectPreferences) {
					if (!projectPreferences.nodeExists(path))
						keys = defaultPreferences.node(path).keys();
					prefs = projectPreferences.node(path);
				}
				else {
					if (!instancePreferences.nodeExists(path))
						keys = defaultPreferences.node(path).keys();
					prefs = instancePreferences.node(path);
				}
	
				if (keys==null)
					keys = prefs.keys();

				for (String p : keys) {
					if (profile.equals(p)) {
						prefs.putBoolean(p, true);
						result = true;
					}
					else {
						prefs.putBoolean(p, false);
					}
				}
			}
			catch (BackingStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public boolean createToolProfile(TargetRuntime rt, Bpmn2DiagramType diagramType, String profile) {
		boolean result = false;
		if (profile!=null && !profile.isEmpty()) {
			try {
				Preferences prefs = null;
				String path = getToolProfilePath(rt, diagramType);
				String keys[] = null;
				boolean populate = false;
				if (projectPreferences!=null && useProjectPreferences) {
					if (!projectPreferences.nodeExists(path)) {
						populate = true;
						keys = defaultPreferences.node(path).keys();
					}
					prefs = projectPreferences.node(path);
				}
				else {
					if (!instancePreferences.nodeExists(path)) {
						keys = defaultPreferences.node(path).keys();
						populate = true;
					}
					prefs = instancePreferences.node(path);
				}
	
				if (keys==null)
					keys = prefs.keys();

				result = true;
				for (String p : keys) {
					if (profile.equals(p)) {
						result = false;
					}
					if (populate)
						prefs.putBoolean(p, false);
				}
				if (result) {
					prefs.putBoolean(profile, true);
				}
			}
			catch (BackingStoreException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public boolean deleteToolProfile(TargetRuntime rt, Bpmn2DiagramType diagramType, String profile) {
		boolean result = false;
		if (profile!=null && !profile.isEmpty()) {
			try {
				Preferences prefs = null;
				String path = getToolProfilePath(rt, diagramType);
				String keys[] = null;
				boolean populate = false;
				if (projectPreferences!=null && useProjectPreferences) {
					if (!projectPreferences.nodeExists(path)) {
						keys = defaultPreferences.node(path).keys();
						populate = true;
					}
					prefs = projectPreferences.node(path);
				}
				else {
					if (!instancePreferences.nodeExists(path)) {
						keys = defaultPreferences.node(path).keys();
						populate = true;
					}
					prefs = instancePreferences.node(path);
				}
	
				if (keys==null)
					keys = prefs.keys();
	
				for (String p : keys) {
					if (populate)
						prefs.putBoolean(p, false);
					if (profile.equals(p)) {
						result = true;
						prefs.remove(p);
					}
				}
				if (result && prefs.keys().length>0) {
					prefs.putBoolean(prefs.keys()[0], true);
				}
			}
			catch (BackingStoreException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public String[] getAllToolProfiles(TargetRuntime rt, Bpmn2DiagramType diagramType) {
		List<String> profiles = new ArrayList<String>();
		try {
			Preferences prefs = null;
			String path = getToolProfilePath(rt, diagramType);
			if (projectPreferences!=null && projectPreferences.nodeExists(path))
				prefs = projectPreferences.node(path);
			else if (instancePreferences.nodeExists(path))
				prefs = instancePreferences.node(path);
			else if (defaultPreferences.nodeExists(path))
				prefs = defaultPreferences.node(path);
		
			if (prefs!=null) {
				int i = 1;
				for (String p : prefs.keys()) {
					if (prefs.getBoolean(p, false))
						profiles.add(0, p);
					else {
						profiles.add(p);
					}
				}
			}
		}
		catch (BackingStoreException e) {
			e.printStackTrace();
		}
		
		return profiles.toArray(new String[profiles.size()]);
	}
	
	public static String getModelEnablementsPath(TargetRuntime rt, Bpmn2DiagramType diagramType, String profile) {
		return PREF_MODEL_ENABLEMENT + "/" + rt.getId() + "/" + diagramType + "/" + profile;
	}

	public ModelEnablements getModelEnablements(Bpmn2DiagramType diagramType, String profile) {
		return getModelEnablements(getRuntime(), diagramType, profile);
	}
	
	public ModelEnablements getModelEnablements(TargetRuntime rt, Bpmn2DiagramType diagramType, String profile) {
		ModelEnablements me = new ModelEnablements(rt);
		if (profile!=null && !profile.isEmpty()) {
			try {
				Preferences prefs = null;
				String path = getModelEnablementsPath(rt, diagramType, profile);
				if (projectPreferences!=null && projectPreferences.nodeExists(path))
					prefs = projectPreferences.node(path);
				else if (instancePreferences.nodeExists(path))
					prefs = instancePreferences.node(path);
				else if (defaultPreferences.nodeExists(path))
					prefs = defaultPreferences.node(path);
				
				if (prefs!=null) {
					me.setEnabledAll(false);
					for (String k : prefs.keys()) {
						if (prefs.getBoolean(k, false))
							me.setEnabled(k, true);
					}
				}
			}
			catch (BackingStoreException e) {
				e.printStackTrace();
			}
		}
		return me;
	}
	
	public boolean setModelEnablements(TargetRuntime rt, Bpmn2DiagramType diagramType, String profile, ModelEnablements me) {
		if (me!=null && me.size()>0) {
			try {
				Preferences prefs = null;
				String path = getModelEnablementsPath(rt, diagramType, profile);
				if (projectPreferences!=null && useProjectPreferences) {
					prefs = projectPreferences.node(path);
					prefs.removeNode();
					prefs = projectPreferences.node(path);
				}
				else {
					prefs = instancePreferences.node(path);
					prefs.removeNode();
					prefs = instancePreferences.node(path);
				}
			
				for (String s : me.getAllEnabled()) {
					prefs.putBoolean(s, true);
				}
				return true;
			}
			catch (BackingStoreException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean getShowPopupConfigDialog(Object context) {
		if (popupConfigDialog!=0) {
			if (context instanceof Task || context instanceof ChoreographyActivity) {
				return popupConfigDialogFor[0];
			}
			if (context instanceof Gateway) {
				return popupConfigDialogFor[1];
			}
			if (context instanceof Event) {
				return popupConfigDialogFor[2];
			}
			if (context instanceof EventDefinition) {
				if (context instanceof CancelEventDefinition || context instanceof TerminateEventDefinition)
					return false; // these have no additional attributes
				return popupConfigDialogFor[3];
			}
			if (context instanceof ItemAwareElement || context instanceof Message) {
				return popupConfigDialogFor[4];
			}
			if (context instanceof InteractionNode || context instanceof FlowElementsContainer) {
				return popupConfigDialogFor[5];
			}
		}
		return false;
	}
	
	public boolean hasPopupConfigDialog(Object context) {
		if (context instanceof Activity) {
			return true;
		}
		if (context instanceof Gateway) {
			return true;
		}
		if (context instanceof Event) {
			return true;
		}
		if (context instanceof EventDefinition) {
			if (context instanceof CancelEventDefinition || context instanceof TerminateEventDefinition)
				return false; // these have no additional attributes
			return true;
		}
		if (context instanceof ItemAwareElement || context instanceof Message) {
			return true;
		}
		if (context instanceof InteractionNode
				|| context instanceof FlowElementsContainer
				|| context instanceof CallChoreography) {
			return true;
		}
		return false;
	}
	
	public void setShowPopupConfigDialog(Object context, boolean value) {
		setInt(PREF_POPUP_CONFIG_DIALOG,  value ? 1 : 0);
		popupConfigDialog = value ? 1 : 0;
	}
	
	public boolean getDoCoreValidation() {
		return doCoreValidation;
	}
	
	public void setDoCoreValidation(boolean enable) {
		setBoolean(PREF_DO_CORE_VALIDATION,enable);
		doCoreValidation = enable;
	}

	public boolean isHorizontalDefault() {
		return isHorizontal==BPMNDIAttributeDefault.ALWAYS_TRUE ||
				isHorizontal==BPMNDIAttributeDefault.DEFAULT_TRUE;
	}

	public BPMNDIAttributeDefault getIsHorizontal() {
		return isHorizontal;
	}
	
	public void setIsHorizontal(BPMNDIAttributeDefault value) {
		setBPMNDIAttributeDefault(PREF_IS_HORIZONTAL, value);
		this.isHorizontal = value;
	}

	public boolean isExpandedDefault() {
		return isExpanded==BPMNDIAttributeDefault.ALWAYS_TRUE ||
				isExpanded==BPMNDIAttributeDefault.DEFAULT_TRUE;
	}

	public BPMNDIAttributeDefault getIsExpanded() {
		return isExpanded;
	}

	public void setIsExpanded(BPMNDIAttributeDefault value) {
		setBPMNDIAttributeDefault(PREF_IS_EXPANDED, value);
		this.isExpanded = value;
	}

	public BPMNDIAttributeDefault getIsMessageVisible() {
		return isMessageVisible;
	}

	public void setIsMessageVisible(BPMNDIAttributeDefault value) {
		setBPMNDIAttributeDefault(PREF_IS_MESSAGE_VISIBLE, value);
		this.isMessageVisible = value;
	}

	public BPMNDIAttributeDefault getIsMarkerVisible() {
		return isMarkerVisible;
	}

	public void setIsMarkerVisible(BPMNDIAttributeDefault value) {
		setBPMNDIAttributeDefault(PREF_IS_MARKER_VISIBLE, value);
		this.isMarkerVisible = value;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}
	
	public void setConnectionTimeout(int value) {
		setInt(PREF_CONNECTION_TIMEOUT, value);
		connectionTimeout = value;
	}
	
	// this is temporary until the connection routing has been proven reliable
	static boolean enableConnectionRouting = true;
	public boolean getEnableConnectionRouting() {
		return enableConnectionRouting;
	}
	
	public void setEnableConnectionRouting(boolean enable) {
		this.enableConnectionRouting = enable;
	}
	
	@Override
	public void preferenceChange(PreferenceChangeEvent event) {
		reload();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		reload();
	}

	// preference/property getters and setters
	public boolean getBoolean(String key, boolean defaultValue) {
		String value = preferenceService.get(key, null, getAllPreferences());
		if (value!=null)
			return Boolean.parseBoolean(value);
		return false;
	}
	
	public void setBoolean(String key, boolean value) {
		if (shouldSaveToProjectPreference(key))
			projectPreferences.putBoolean(key, value);
		else
			instancePreferences.putBoolean(key, value);
		dirty = true;
	}

	public int getInt(String key, int defaultValue) {
		String value = preferenceService.get(key, null, getAllPreferences());
		if (value!=null)
			return Integer.parseInt(value);
		return -1;
	}
	
	public void setInt(String key, int value) {
		if (shouldSaveToProjectPreference(key))
			projectPreferences.putInt(key, value);
		else
			instancePreferences.putInt(key, value);
		dirty = true;
	}
	
	public String getString(String key, String defaultValue) {
		try {
			Preferences prefs = null;
			String path = null;
			int i = key.lastIndexOf("/");
			if (i>0) {
				path = key.substring(0, i);
				key = key.substring(i+1);
				if (projectPreferences!=null && projectPreferences.nodeExists(path))
					prefs = projectPreferences.node(path);
				else if (instancePreferences.nodeExists(path))
					prefs = instancePreferences.node(path);
				else if (defaultPreferences.nodeExists(path))
					prefs = defaultPreferences.node(path);
			}
			else {
				if (projectPreferences!=null)
					prefs = projectPreferences;
				else if (instancePreferences.get(key, null)!=null)
					prefs = instancePreferences;
				else if (defaultPreferences.get(key,null)!=null)
					prefs = defaultPreferences;
			}
			
			if (prefs!=null) {
				String value = prefs.get(key, defaultValue);
				return value;
			}
		}
		catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return defaultValue;
	}
	
	public void setString(String key, String value) {
		Preferences prefs = null;
		String path = null;
		int i = key.lastIndexOf("/");
		if (i>0) {
			path = key.substring(0, i);
			key = key.substring(i+1);
			if (shouldSaveToProjectPreference(key))
				prefs = projectPreferences.node(path);
			else
				prefs = instancePreferences.node(path);
		}
		else {
			if (shouldSaveToProjectPreference(key))
				prefs = projectPreferences;
			else
				prefs = instancePreferences;
		}		
		prefs.put(key, value);
		dirty = true;

	}

	public BPMNDIAttributeDefault getBPMNDIAttributeDefault(String key, BPMNDIAttributeDefault defaultValue) {
		BPMNDIAttributeDefault value = null;
		if (shouldSaveToProjectPreference(key))
			value = BPMNDIAttributeDefault.valueOf(projectPreferences.get(key, defaultValue.name()));
		else if (preferenceStore.contains(key))
			value = BPMNDIAttributeDefault.valueOf(preferenceStore.getString(key));
		else
			value = defaultValue;
		return value;
	}
	
	public void setBPMNDIAttributeDefault(String key, BPMNDIAttributeDefault value) {
		if (shouldSaveToProjectPreference(key))
			projectPreferences.put(key, value.name());
		else
			instancePreferences.put(key, value.name());
		dirty = true;
	}

	public static String[] getBPMNDIAttributeDefaultChoices() {
		BPMNDIAttributeDefault[] values = BPMNDIAttributeDefault.values();
		String[] choices = new String[values.length];
		int i = 0;
		for (BPMNDIAttributeDefault v : values) {
			String text = Messages.Bpmn2Preferences_None;
			switch (v) {
			case USE_DI_VALUE:
				text = Messages.Bpmn2Preferences_False_if_not_set;
				break;
			case DEFAULT_TRUE:
				text = Messages.Bpmn2Preferences_True_if_not_set;
				break;
			case ALWAYS_TRUE:
				text = Messages.Bpmn2Preferences_Always_true;
				break;
			case ALWAYS_FALSE:
				text = Messages.Bpmn2Preferences_Always_False;
				break;
			}
			choices[i++] = text;
		}
		return choices;
	}
	
	public static String[][] getBPMNDIAttributeDefaultChoicesAndValues() {
		String[] choices = getBPMNDIAttributeDefaultChoices();
		BPMNDIAttributeDefault[] values = BPMNDIAttributeDefault.values();
		String[][] choicesAndValues = new String[choices.length][2];
		int i = 0;
		for (BPMNDIAttributeDefault v : values) {
			choicesAndValues[i][0] = choices[i];
			choicesAndValues[i][1] = v.name();
			++i;
		}
		return choicesAndValues;
	}
	
	/**
	 * Applies preference defaults to a BPMNShape object. The <code>attribs</code> map should contain
	 * only those attributes that are set on the BPMNShape object (as read from the bpmn XML file).
	 * This is used to determine the appropriate default values for certain optional attributes, e.g.
	 * isHorizontal, isExpanded, etc.
	 * 
	 * @param bpmnShape - the BPMNShape object whose attributes are to be set
	 * @param attribs - map of BPMN DI attributes currently set on the BPMNShape object. May be null.
	 * @see getIsHorizontal(), getIsExpanded(), getIsMessageVisible() and getIsMarkerVisible()
	 */
	public void applyBPMNDIDefaults(BPMNShape bpmnShape, Map<String,String>attribs) {
		boolean isHorizontalSet = false;
		boolean isExpandedSet = false;
		boolean isMessageVisibleSet = false;
		boolean isMarkerVisibleSet = false;
		boolean choreographyActivityShapeSet = false;
		
		if (attribs != null) {
			for (Entry<String, String> entry : attribs.entrySet()) {
				String name = entry.getKey();
				if ("isHorizontal".equals(name)) { //$NON-NLS-1$
					isHorizontalSet = true;
				}
				if ("isExpanded".equals(name)) { //$NON-NLS-1$
					isExpandedSet = true;
				}
				if ("isMessageVisible".equals(name)) { //$NON-NLS-1$
					isMessageVisibleSet = true;
				}
				if ("isMarkerVisible".equals(name)) { //$NON-NLS-1$
					isMarkerVisibleSet = true;
				}
				if ("choreographyActivityShape".equals(name)) { //$NON-NLS-1$
					choreographyActivityShapeSet = true;
				}
			}
		}
		
		BaseElement be = bpmnShape.getBpmnElement();
		
		// isHorizontal only applies to Pools and Lanes, not Participant bands
		if (!isHorizontalSet) {
			if ((be instanceof Participant && !choreographyActivityShapeSet) || be instanceof Lane) {
				boolean horz = isHorizontalDefault();
				bpmnShape.setIsHorizontal(horz);
			}
		}
		else {
			if ((be instanceof Participant && !choreographyActivityShapeSet) || be instanceof Lane) {
				BPMNDIAttributeDefault df = getIsHorizontal();
				switch(df) {
				case ALWAYS_TRUE:
					bpmnShape.setIsHorizontal(true);
					break;
				case ALWAYS_FALSE:
					bpmnShape.setIsHorizontal(false);
					break;
				}

			}
		}
		
		// isExpanded only applies to activity containers (SubProcess, AdHocSubProcess, etc.)
		if (!isExpandedSet) {
			if (be instanceof  SubProcess ||
					be instanceof AdHocSubProcess ||
					be instanceof Transaction ||
					be instanceof SubChoreography ||
					be instanceof CallActivity ||
					be instanceof CallChoreography) {
				boolean value = false;
				BPMNDIAttributeDefault df = getIsExpanded();
				switch(df) {
				case ALWAYS_TRUE:
				case DEFAULT_TRUE:
					value = true;
					break;
				case ALWAYS_FALSE:
				case USE_DI_VALUE:
					value = false;
				}
				bpmnShape.setIsExpanded(value);
			}
		}
		else {
			if (be instanceof  SubProcess ||
					be instanceof AdHocSubProcess ||
					be instanceof Transaction ||
					be instanceof SubChoreography ||
					be instanceof CallActivity ||
					be instanceof CallChoreography) {
				BPMNDIAttributeDefault df = getIsExpanded();
				switch(df) {
				case ALWAYS_TRUE:
					bpmnShape.setIsExpanded(true);
					break;
				case ALWAYS_FALSE:
					bpmnShape.setIsExpanded(false);
					break;
				}
			}
		}
		
		// isMessageVisible only applies to Participant Bands
		if (!isMessageVisibleSet) {
			if (be instanceof Participant && choreographyActivityShapeSet) {
				boolean value = false;
				BPMNDIAttributeDefault df = getIsMessageVisible();
				switch(df) {
				case ALWAYS_TRUE:
				case DEFAULT_TRUE:
					value = true;
					break;
				case ALWAYS_FALSE:
				case USE_DI_VALUE:
					value = false;
				}
				bpmnShape.setIsMessageVisible(value);
			}
		}
		else {
			if (be instanceof Participant && choreographyActivityShapeSet) {
				BPMNDIAttributeDefault df = getIsMessageVisible();
				switch(df) {
				case ALWAYS_TRUE:
					bpmnShape.setIsMessageVisible(true);
					break;
				case ALWAYS_FALSE:
					bpmnShape.setIsMessageVisible(false);
					break;
				}
			}
		}
		
		// isMarkerVisible only applies to ExclusiveGateway
		if (!isMarkerVisibleSet) {
			if (be instanceof ExclusiveGateway) {
				BPMNDIAttributeDefault df = getIsMarkerVisible();
				switch(df) {
				case ALWAYS_TRUE:
				case DEFAULT_TRUE:
					bpmnShape.setIsMarkerVisible(true);
					break;
				case ALWAYS_FALSE:
				case USE_DI_VALUE:
					bpmnShape.setIsMarkerVisible(false);
					break;
				}
			}
		}
		else {
			if (be instanceof ExclusiveGateway) {
				BPMNDIAttributeDefault df = getIsMarkerVisible();
				switch(df) {
				case ALWAYS_TRUE:
					bpmnShape.setIsMarkerVisible(true);
					break;
				case ALWAYS_FALSE:
					bpmnShape.setIsMarkerVisible(false);
					break;
				}
			}
		}
	}

	public void setToDefault(String key) {
		try {
			if (key.startsWith(PREF_TOOL_PROFILE)) {
				Preferences prefs = instancePreferences.node(key);
				prefs.removeNode();
			}
			else if (key.startsWith(PREF_MODEL_ENABLEMENT)) {
				Preferences prefs = instancePreferences.node(key);
				prefs.removeNode();
			}
			else if (key.startsWith(PREF_SHAPE_STYLE)) {
				shapeStyles.clear();
				Preferences prefs = instancePreferences.node(key);
				prefs.removeNode();
			}
			else if (key.startsWith(PREF_TARGET_RUNTIME)) {
				if (shouldSaveToProjectPreference(PREF_TARGET_RUNTIME)) {
					projectPreferences.put(PREF_TARGET_RUNTIME, TargetRuntime.DEFAULT_RUNTIME_ID);
				}
			}
			else {
				instancePreferences.put(key, defaultPreferences.get(key,""));
			}
		}
		catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// TODO: use CNF for indigo & future - keep ResourceNavigator for backward compatibility
	public static IProject getActiveProject() {
		if (activeProject!=null)
			return activeProject;
		
		IWorkbench workbench = PlatformUI.getWorkbench(); 
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		if (window!=null) {
			IWorkbenchPage page = window.getActivePage();
			if (page!=null) {
				IViewPart[] parts = page.getViews();
		
				for (int i = 0; i < parts.length; i++) {
					if (parts[i] instanceof ResourceNavigator) {
						ResourceNavigator navigator = (ResourceNavigator) parts[i];
						StructuredSelection sel = (StructuredSelection) navigator.getTreeViewer().getSelection();
						IResource resource = (IResource) sel.getFirstElement();
						if (resource!=null) {
							activeProject = resource.getProject();
							break;
						}
					}
				}
			}
		}
		return activeProject;
	}

	public static void setActiveProject(IProject project) {
		activeProject = project;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		int type = event.getType();
		if (type==IResourceChangeEvent.PRE_CLOSE) {
			try {
				save();
			} catch (Exception e) {
				e.printStackTrace();
			}
			dispose();
		}
		if (type==IResourceChangeEvent.PRE_DELETE)
			dispose();
	}
}

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
 * @author Innar Made
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.ui.diagram;

import java.lang.reflect.Constructor;

import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.ui.Activator;
import org.eclipse.bpmn2.modeler.ui.editor.BPMN2Editor;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;

public class MainBPMNDiagramTypeProvider extends AbstractDiagramTypeProvider {
	private IToolBehaviorProvider[] toolBehaviorProviders;

	public MainBPMNDiagramTypeProvider() {
		super();
		setFeatureProvider(new BPMNFeatureProvider(this));
	}

	@Override
	public IToolBehaviorProvider[] getAvailableToolBehaviorProviders() {
		if (toolBehaviorProviders == null) {
			BPMN2Editor editor = (BPMN2Editor)getDiagramEditor();
			TargetRuntime rt = editor.getTargetRuntime();
			IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(
					Activator.UI_EXTENSION_ID);
			BPMNToolBehaviorProvider provider = null;
			try {
				for (IConfigurationElement e : config) {
					if (e.getName().equals("toolProvider")) { //$NON-NLS-1$
						String id = e.getAttribute("id"); //$NON-NLS-1$
						String runtimeId = e.getAttribute("runtimeId"); //$NON-NLS-1$
						if (rt!=null && rt.getId().equals(runtimeId)) {
							String className = e.getAttribute("class"); //$NON-NLS-1$
							ClassLoader cl = rt.getRuntimeExtension().getClass().getClassLoader();
							Constructor ctor = null;
							Class providerClass = Class.forName(className, true, cl);
							ctor = providerClass.getConstructor(IDiagramTypeProvider.class);
							provider = (BPMNToolBehaviorProvider)ctor.newInstance(this);
							break;
						}
					}
				}
			}
			catch (Exception ex) {
				Activator.logError(ex);
			}
			
			if (provider==null)
				provider = new BPMNToolBehaviorProvider(this);
			toolBehaviorProviders = new IToolBehaviorProvider[] { provider };
		}
		return toolBehaviorProviders;
	}
}

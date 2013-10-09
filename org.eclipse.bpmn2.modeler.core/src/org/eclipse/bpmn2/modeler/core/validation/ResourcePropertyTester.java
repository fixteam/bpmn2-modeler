package org.eclipse.bpmn2.modeler.core.validation;

import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.runtime.ToolPaletteDescriptor;
import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EObject;

public class ResourcePropertyTester extends PropertyTester {

	public ResourcePropertyTester() {
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof EObject) {
			EObject object = (EObject) receiver;
			Bpmn2Preferences prefs = Bpmn2Preferences.getInstance(object);
			TargetRuntime rt = null;
			if (prefs != null)
				rt = prefs.getRuntime();

			if ("targetRuntimeId".equals(property)) {
				if (rt != null) {
					return rt.getId().equals(expectedValue);
				}
			}
			else if ("toolPaletteProfile".equals(property)) {
				if (rt != null) {
					ToolPaletteDescriptor tpd = rt.getToolPalette(object);
					if (tpd != null) {
						String profile = tpd.getProfile();
						if (profile != null)
							return profile.equals(expectedValue);
						else if (expectedValue instanceof String)
							return expectedValue==null || ((String)expectedValue).isEmpty();
					}
				}
			}
			if ("doCoreValidation".equals(property)) {
				if (prefs!=null) {
					String value = Boolean.toString( prefs.getDoCoreValidation() );
					expectedValue = expectedValue.toString();
					return value.equals(expectedValue);
				}
			}
		}
		return false;
	}

}

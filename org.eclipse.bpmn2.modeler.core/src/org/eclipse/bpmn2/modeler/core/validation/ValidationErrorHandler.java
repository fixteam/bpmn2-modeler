package org.eclipse.bpmn2.modeler.core.validation;

import org.eclipse.core.runtime.IStatus;

public interface ValidationErrorHandler {
	
	public void reportError(IStatus s);
	
}

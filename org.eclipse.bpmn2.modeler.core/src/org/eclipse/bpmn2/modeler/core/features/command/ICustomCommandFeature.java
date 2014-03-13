package org.eclipse.bpmn2.modeler.core.features.command;

public interface ICustomCommandFeature {

	public final static String COMMAND_HINT = "command.hint"; 
	boolean isAvailable(String hint);
}

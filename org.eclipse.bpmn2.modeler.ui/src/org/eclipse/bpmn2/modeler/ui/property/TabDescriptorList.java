package org.eclipse.bpmn2.modeler.ui.property;

import java.util.ArrayList;

import org.eclipse.bpmn2.modeler.core.runtime.Bpmn2TabDescriptor;

public class TabDescriptorList extends ArrayList<Bpmn2TabDescriptor> {
	private static final long serialVersionUID = -296768469891312674L;

	@Override
	public Bpmn2TabDescriptor[] toArray() {
		return this.toArray(new Bpmn2TabDescriptor[this.size()]);
	}
}
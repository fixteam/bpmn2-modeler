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

import java.util.ArrayList;

import org.eclipse.bpmn2.modeler.core.AbstractPropertyChangeListenerProvider;
import org.eclipse.emf.ecore.ENamedElement;

public class ToolEnablement extends AbstractPropertyChangeListenerProvider {

	private String name;
	private ENamedElement tool;
	private Boolean enabled;
	private ToolEnablement parent;
	private ArrayList<ToolEnablement> children;
	// "friends" are references to this ToolEnablement.
	private ArrayList<ToolEnablement> friends;
	private static ArrayList<ToolEnablement> EMPTY_LIST = new ArrayList<ToolEnablement>();

	public ToolEnablement() {
	}

	public ToolEnablement(ENamedElement tool, ToolEnablement parent) {
		setTool(tool);
		this.parent = parent;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		if (tool!=null)
			return tool.getName();
		return name==null ? "" : name; //$NON-NLS-1$
	}

	public String getPreferenceName() {
		if (parent == null || parent.getTool()==null) {
			return getName();
		} else {
			return parent.getPreferenceName() + "." + getName(); //$NON-NLS-1$
		}
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setSubtreeEnabled(Boolean enabled) {
		setEnabled(enabled);
		for (ToolEnablement child : getChildren()) {
			child.setSubtreeEnabled(enabled);
		}
	}

	public int getSubtreeSize() {
		return getSubtreeSize(this);
	}
	
	private static int getSubtreeSize(ToolEnablement parent) {
		int size = 0;
		for (ToolEnablement child : parent.getChildren()) {
			++size;
			size += getSubtreeSize(child);
		}
		return size;
	}
	
	public int getSubtreeEnabledCount() {
		return getSubtreeEnabledCount(this);
	}
	
	private static int getSubtreeEnabledCount(ToolEnablement parent) {
		int count = 0;
		for (ToolEnablement child : parent.getChildren()) {
			if (child.getEnabled())
				++count;
			count += getSubtreeEnabledCount(child);
		}
		return count;
	}
	
	public void setTool(ENamedElement tool) {
		if (tool!=null)
			this.name = tool.getName();
		this.tool = tool;
	}

	public ENamedElement getTool() {
		return tool;
	}

	public void setChildren(ArrayList<ToolEnablement> children) {
		this.children = children;
	}

	public ArrayList<ToolEnablement> getChildren() {
		if (children==null)
			return EMPTY_LIST;
		return children;
	}

	public void setParent(ToolEnablement parent) {
		this.parent = parent;
	}

	public ToolEnablement getParent() {
		return parent;
	}

	@Override
	public String toString() {
		return "ToolEnablement [tool=" + getName() + ", enabled=" + enabled + ", children=" + children + ", parent=" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ (parent == null ? "null" : parent.getName()) + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public boolean hasChildren() {
		return children != null && children.size() > 0;

	}
	
	public boolean hasFriends() {
		return friends!=null && friends.size()>0;
	}
	
	public ArrayList<ToolEnablement> getFriends() {
		if (friends==null)
			return EMPTY_LIST;
		return friends;
	}
	
	public void addFriend(ToolEnablement friend) {
		if (friend!=null) {
			if (friends==null)
				friends = new ArrayList<ToolEnablement>();
			if (!friends.contains(friend))
				friends.add(friend);
		}
	}
}
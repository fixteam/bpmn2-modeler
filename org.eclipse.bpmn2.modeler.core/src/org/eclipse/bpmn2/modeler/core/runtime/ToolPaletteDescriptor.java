package org.eclipse.bpmn2.modeler.core.runtime;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;

import org.eclipse.core.runtime.IConfigurationElement;

public class ToolPaletteDescriptor extends BaseRuntimeDescriptor {

	public final static String TOOLPART_ID = "ToolPartID";
	public final static String TOOLPART_OPTIONAL = "ToolPartOptional";
	
	// The Drawers
	public static class CategoryDescriptor {
		private ToolPaletteDescriptor parent;
		private String id;
		// these are used to sort the categories
		private String before;
		private String after;
		private String name;
		private String description;
		private String icon;
		private String fromPalette;
		private List<ToolDescriptor> tools = new ArrayList<ToolDescriptor>();
		
		public CategoryDescriptor(ToolPaletteDescriptor parent, String id, String name, String description, String icon) {
			this.parent = parent;
			this.id = id;
			this.name = name;
			this.description = description;
			this.icon = icon;
		}
		
		public ToolDescriptor addTool(String id, String name, String description, String icon, String object) {
			ToolDescriptor tool = new ToolDescriptor(this, id, name, description, icon, object);
			tools.add(tool);
			return tool;
		}
		
		public ToolDescriptor addTool(String id, String name, String description, String icon) {
			ToolDescriptor tool = new ToolDescriptor(this, id, name, description, icon);
			tools.add(tool);
			return tool;
		}
		
		public List<ToolDescriptor> getTools() {
			return tools;
		}

		public String getId() {
			return id;
		}

		public void setBefore(String before) {
			this.before = before;
		}

		public String getBefore() {
			return before;
		}

		public void setAfter(String after) {
			this.after = after;
		}

		public String getAfter() {
			return after;
		}
		
		public String getName() {
			return name;
		}
		
		public String getDescription() {
			return description;
		}

		public String getIcon() {
			return icon;
		}
		
		public String getFromPalette() {
			return fromPalette;
		}

		public void setFromPalette(String fromPalette) {
			this.fromPalette = fromPalette;
		}

		public ToolPaletteDescriptor getParent() {
			return parent;
		}
	};
	
	// The Tools
	public static class ToolDescriptor {
		private CategoryDescriptor parent;
		private String id;
		private String name;
		private String description;
		private String icon;
		private String fromPalette;
		private List<ToolPart> toolParts = new ArrayList<ToolPart>() {
			@Override
			public boolean add(ToolPart tp) {
				if (tp.getName().isEmpty())
					System.out.println("");
				return super.add(tp);
			}
		};
		
		public ToolDescriptor(CategoryDescriptor parent, String id, String name, String description, String icon) {
			this.parent = parent;
			this.id = id;
			this.name = name;
			this.description = description;
			this.icon = icon;
		}
		
		public ToolDescriptor(CategoryDescriptor parent, String id, String name, String description, String icon, String object) {
			this(parent, id, name, description, icon);
			parseToolObjectString(object);
		}
		
		public ToolPart parseToolObjectString(String object) {
			ToolPart parentToolPart = null;
			ToolPart toolPart = null;
			String toolPartName = "";
			Stack<ToolPart> toolPartStack = new Stack<ToolPart>();
			ToolPart result = null;
			char chars[] = object.toCharArray();
			for (int i=0; i<chars.length; ++i) {
				char c = chars[i];
				if (c=='+') {
					if (!"".equals(toolPartName)) {
						toolPart = new ToolPart(this, toolPartName);
						if (parentToolPart==null) {
							toolParts.add(toolPart);
						}
						else {
							parentToolPart.children.add(toolPart);
						}
						parentToolPart = toolPart;
						if (result==null)
							result = toolPart;
					}
					else if (parentToolPart==null)
						parentToolPart = toolPart;
					toolPartStack.push(parentToolPart);
					toolPartName = "";
				}
				else if (c=='-') {
					if (!"".equals(toolPartName))
						parentToolPart.children.add( new ToolPart(this, toolPartName) );
					parentToolPart = toolPartStack.pop();
					toolPartName = "";
				}
				else if (c==',') {
					if (!"".equals(toolPartName)) {
						toolPart = new ToolPart(this, toolPartName);
						if (parentToolPart==null) {
							toolParts.add(toolPart);
						}
						else {
							parentToolPart.children.add(toolPart);
						}
						if (result==null)
							result = toolPart;
						toolPartName = "";
					}
				}
				else if (c=='[') {
					toolPart = new ToolPart(this, toolPartName);
					if (parentToolPart==null) {
						toolParts.add(toolPart);
					}
					else {
						parentToolPart.children.add(toolPart);
					}
					if (result==null)
						result = toolPart;
					toolPartName = "";
					
					// data for preceding object type follows:
					// [name=value] or [name1=value1,name2=value2]
					// are valid
					++i;
					do {
						String prop = "";
						while (i<chars.length) {
							c = chars[i++];
							if (c=='\\')
								c = chars[i++];
							else if (c=='=')
								break;
							prop += c;
						}
						String value = "";
						boolean quote = false;
						while (i<chars.length) {
							c = chars[i++];
							if (c=='\'') {
								quote = !quote;
								continue;
							}
							if (c=='\\')
								c = chars[i++];
							else if (!quote && (c==',' || c==']'))
								break;
							value += c;
						}
						toolPart.putProperty(prop,value);
					} while (i<chars.length && c!=']');
					if (c==']') {
						--i;
					}
				}
				else if ("".equals(toolPartName)) {
					if (Character.isJavaIdentifierStart(c))
						toolPartName += c;
				}
				else if (Character.isJavaIdentifierPart(c)) {
					toolPartName += c;
				}
				
				if (i==chars.length-1 && !toolPartName.isEmpty()) {
					toolPart = new ToolPart(this, toolPartName);
					if (parentToolPart==null) {
						toolParts.add(toolPart);
					}
					else {
						parentToolPart.children.add(toolPart);
					}
					if (result==null)
						result = toolPart;
				}
			}
			
			return result;
		}
		
		public ToolDescriptor(CategoryDescriptor parent, String name, String description, String icon) {
			this.parent = parent;
			this.name = name;
			this.description = description;
			this.icon = icon;
		}
		
		public List<ToolPart> getToolParts() {
			return toolParts;
		}
		
		public String getId() {
			return id;
		}
		
		public String getName() {
			return name;
		}
		
		public String getDescription() {
			return description;
		}
		
		public String getIcon() {
			return icon;
		}
		
		public String getFromPalette() {
			return fromPalette;
		}

		public void setFromPalette(String fromPalette) {
			this.fromPalette = fromPalette;
		}

		public CategoryDescriptor getParent() {
			return parent;
		}
	};
	
	public static class ToolPart {
		private ToolDescriptor parent;
		private String name;
		private List<ToolPart> children = new ArrayList<ToolPart>();
		private Hashtable<String, String> properties = null;
		
		public ToolPart(ToolDescriptor parent, String name) {
			this.parent = parent;
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		public List<ToolPart> getChildren() {
			return children;
		}
		
		public void putProperty(String name, String value) {
			getProperties().put(name, value);
		}
		
		public Hashtable<String, String> getProperties() {
			if (properties==null)
				properties = new Hashtable<String, String>();
			return properties;
		}
		
		public String getProperty(String name) {
			if (properties==null)
				return null;
			return properties.get(name);
		}
		
		public boolean hasProperties() {
			return properties!=null && properties.size()>0;
		}
		
		public ToolDescriptor getParent() {
			return parent;
		}
	}
	
	String id; // unique ID
	String type; // the diagram type for which this toolPalette is to be used
	String profile; // the model enablement profile for which this toolPalette is to be used
	// the list of categories in the toolPalette
	List<CategoryDescriptor> categories = new ArrayList<CategoryDescriptor>();
	
	public ToolPaletteDescriptor() {
		super();
	}

	protected void create(IConfigurationElement e) {
		id = e.getAttribute("id");
		type = e.getAttribute("type");
		profile = e.getAttribute("profile");
		for (IConfigurationElement c : e.getChildren()) {
			if (c.getName().equals("category")) {
				String cid = c.getAttribute("id");
				String name = c.getAttribute("name");
				String description = c.getAttribute("description");
				String icon = c.getAttribute("icon");
				CategoryDescriptor category = addCategory(cid, name, description, icon);
				cid = c.getAttribute("before");
				if (cid!=null)
					category.setBefore(cid);
				cid = c.getAttribute("after");
				if (cid!=null)
					category.setAfter(cid);
				cid = c.getAttribute("fromPalette");
				if (cid!=null)
					category.setFromPalette(cid);
				for (IConfigurationElement t : c.getChildren()) {
					if (t.getName().equals("tool")) {
						String tid = t.getAttribute("id");
						name = t.getAttribute("name");
						description = t.getAttribute("description");
						icon = t.getAttribute("icon");
						String object = t.getAttribute("object");
						ToolDescriptor tool = null;
						
						if (object!=null && !object.isEmpty()) {
							tool = category.addTool(tid, name, description, icon, object);
						}
						else {
							tool = category.addTool(tid, name, description, icon);
							for (IConfigurationElement tc : t.getChildren()) {
								if ("object".equals(tc.getName())) {
									String id = tc.getAttribute("id");
									String type = tc.getAttribute("type");
									String optional = tc.getAttribute("optional");
									ToolPart tp = tool.parseToolObjectString(type);
									if (id!=null && !id.isEmpty())
										tp.getProperties().put(TOOLPART_ID, id);
									if ("true".equals(optional))
										tp.getProperties().put(TOOLPART_OPTIONAL, optional);
								}
							}
						}
						tid = c.getAttribute("fromPalette");
						if (tid!=null)
							tool.setFromPalette(cid);
					}
				}
			}
		}
	}
	
	protected CategoryDescriptor addCategory(String id, String name, String description, String icon) {
		CategoryDescriptor category = null;
		for (CategoryDescriptor cd : categories) {
			if (cd.getId().equals(id)) {
				category = cd;
				break;
			}
		}
		if (category==null) {
			category = new CategoryDescriptor(this, id, name, description, icon);
			categories.add(category);
		}
		return category;
	}

	public void sortCategories() {
		// order the categories depending on "before" abd "after" attributes
		List<CategoryDescriptor> sorted = new ArrayList<CategoryDescriptor>();
		sorted.addAll(categories);
		boolean changed = false;
		for (CategoryDescriptor movedCategory : categories) {
			String before = movedCategory.getBefore();
			if (before!=null) {
				for (CategoryDescriptor cd : sorted) {
					if (cd.getId().equals(before)) {
						sorted.remove(movedCategory);
						int i = sorted.indexOf(cd);
						sorted.add(i,movedCategory);
						changed = true;
						break;
					}
				}				
			}
			String after = movedCategory.getAfter();
			if (after!=null) {
				for (CategoryDescriptor cd : sorted) {
					if (cd.getId().equals(after)) {
						sorted.remove(movedCategory);
						int i = sorted.indexOf(cd);
						if (i+1 < sorted.size())
							sorted.add(i+1,movedCategory);
						else
							sorted.add(movedCategory);
						changed = true;
						break;
					}
				}				
			}
		}
		if (changed) {
			categories.clear();
			categories.addAll(sorted);
		}
	}

	public String getId() {
		return id;
	}
	
	public String getProfile() {
		return profile;
	}

	public String getType() {
		return type;
	}
	
	public List<CategoryDescriptor> getCategories() {
		return categories;
	}
}

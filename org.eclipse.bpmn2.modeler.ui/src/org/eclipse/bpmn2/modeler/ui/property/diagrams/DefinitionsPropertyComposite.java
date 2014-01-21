/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc. 
 * All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 *******************************************************************************/
package org.eclipse.bpmn2.modeler.ui.property.diagrams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.Import;
import org.eclipse.bpmn2.impl.DefinitionsImpl;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractListComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractPropertiesProvider;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultListComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.ListCompositeColumnProvider;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.ListCompositeContentProvider;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.TableColumn;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.TextAndButtonObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.TextObjectEditor;
import org.eclipse.bpmn2.modeler.core.utils.ImportUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.core.utils.NamespaceUtil;
import org.eclipse.bpmn2.modeler.ui.property.dialogs.NamespacesEditingDialog;
import org.eclipse.bpmn2.modeler.ui.property.dialogs.SchemaImportDialog;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class DefinitionsPropertyComposite extends DefaultDetailComposite  {

	public DefinitionsPropertyComposite(Composite parent, int style) {
		super(parent, style);
	}

	private NamespaceListComposite namespacesTable;
	
	/**
	 * @param section
	 */
	public DefinitionsPropertyComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	@Override
	public AbstractPropertiesProvider getPropertiesProvider(EObject object) {
		if (propertiesProvider==null) {
			propertiesProvider = new AbstractPropertiesProvider(object) {
				String[] properties = new String[] {
						"imports", //$NON-NLS-1$
						"name", //$NON-NLS-1$
						"targetNamespace", //$NON-NLS-1$
						"typeLanguage", //$NON-NLS-1$
						"expressionLanguage", //$NON-NLS-1$
						"exporter", //$NON-NLS-1$
						"exporterVersion", //$NON-NLS-1$
						"rootElements#ItemDefinition", //$NON-NLS-1$
						"relationships", //$NON-NLS-1$
						"rootElements#PartnerEntity", //$NON-NLS-1$
						"rootElements#PartnerRole", //$NON-NLS-1$
						"rootElements#EndPoint", //$NON-NLS-1$
						"rootElements#Resource", //$NON-NLS-1$
						"rootElements#DataStore", //$NON-NLS-1$
						"rootElements#Message", //$NON-NLS-1$
						"rootElements#Error", //$NON-NLS-1$
						"rootElements#Escalation", //$NON-NLS-1$
						"rootElements#Signal", //$NON-NLS-1$
						"rootElements#CorrelationProperty", //$NON-NLS-1$
						"rootElements#Category", //$NON-NLS-1$
				};
				
				@Override
				public String[] getProperties() {
					return properties; 
				}
			};
		}
		return propertiesProvider;
	}

	@Override
	public void cleanBindings() {
		super.cleanBindings();
		namespacesTable = null;
	}

	@Override
	public void createBindings(EObject be) {
		super.createBindings(be);
		
		namespacesTable = new NamespaceListComposite(getPropertySection());
		DefinitionsImpl definitions = (DefinitionsImpl)getBusinessObject();
		DocumentRoot root = (DocumentRoot) definitions.eContainer();
		namespacesTable.bindList(root, Bpmn2Package.eINSTANCE.getDocumentRoot_XMLNSPrefixMap());
		namespacesTable.setTitle("Namespaces"); //$NON-NLS-1$
	}

	@Override
	protected AbstractListComposite bindList(EObject object, EStructuralFeature feature, EClass listItemClass) {
		if ("imports".equals(feature.getName())) { //$NON-NLS-1$
			ImportListComposite importsTable = new ImportListComposite(getPropertySection());
			EStructuralFeature importsFeature = object.eClass().getEStructuralFeature("imports"); //$NON-NLS-1$
			importsTable.bindList(object, importsFeature);
			return importsTable;
		}
		else if ("relationships".equals(feature.getName())) { //$NON-NLS-1$
			DefaultListComposite table = new DefaultListComposite(getPropertySection());
			table.bindList(getBusinessObject(), feature);
			return table;
		}
		else {
			return super.bindList(object, feature, listItemClass);
		}
	}

	public class NamespaceListComposite extends DefaultListComposite {
		
		public NamespaceListComposite(AbstractBpmn2PropertySection section) {
			super(section, ADD_BUTTON | REMOVE_BUTTON | EDIT_BUTTON);
		}

		
		@Override
		protected EObject addListItem(EObject object, EStructuralFeature feature) {
			DocumentRoot root = (DocumentRoot)object;
			Map<String,String> map = root.getXMLNSPrefixMap();
			NamespacesEditingDialog dialog = new NamespacesEditingDialog(getShell(),
				Messages.DefinitionsPropertyComposite_Create_Namespace_Title, map, "",""); //$NON-NLS-1$ //$NON-NLS-2$
			if (dialog.open() == Window.OK) {
				map.put(dialog.getPrefix(), dialog.getNamespace());
			}
			return null;
		}

		@Override
		protected Object removeListItem(EObject object, EStructuralFeature feature, int index) {
			DocumentRoot root = (DocumentRoot)object;
			Map<String,String> map = root.getXMLNSPrefixMap();
			for ( Map.Entry<String, String> entry : map.entrySet() ) {
				if (index-- == 0) {
					map.remove( entry.getKey() );
					break;
				}
			}
			return null;
		}

		@Override
		public AbstractDetailComposite createDetailComposite(Class eClass, final Composite parent, int style) {
			detailSection.setText(Messages.DefinitionsPropertyComposite_Namespace_Details_Title);
			AbstractDetailComposite composite = new DefaultDetailComposite(parent, SWT.NONE) {
				
				@Override
				protected void bindAttribute(Composite parent, EObject object, EAttribute attribute, String label) {
					if (attribute.getName().equals("key")) { //$NON-NLS-1$
						ObjectEditor editor = new TextAndButtonObjectEditor(this,businessObject,attribute) {

							@Override
							protected void buttonClicked(int buttonId) {
								Map.Entry<String, String> entry = (Map.Entry<String, String>)object;
								DocumentRoot root = (DocumentRoot)object.eContainer();
								Map<String, String> map = (Map<String, String>)root.getXMLNSPrefixMap();
								NamespacesEditingDialog dialog = new NamespacesEditingDialog(getShell(), Messages.DefinitionsPropertyComposite_Change_Namespace_Title, map, entry.getKey(),null);
								if (dialog.open() == Window.OK) {
									setValue(dialog.getPrefix());
								}
							}
							
							@Override
							protected boolean setValue(final Object result) {
								// we can't just change the key because the map that contains it
								// needs to be updated, so remove old key, then add new.
								if (result instanceof String && !((String)result).isEmpty() ) {
									final Map.Entry<String, String> entry = (Map.Entry<String, String>)object;
									final String oldKey = entry.getKey();
									TransactionalEditingDomain domain = getDiagramEditor().getEditingDomain();
									domain.getCommandStack().execute(new RecordingCommand(domain) {
										@Override
										protected void doExecute() {
											DocumentRoot root = (DocumentRoot)object.eContainer();
											Map<String, String> map = (Map<String, String>)root.getXMLNSPrefixMap();
											String value = map.remove(oldKey);
											map.put((String)result, value);
										}
									});
									return true;
								}
								text.setText(ModelUtil.getDisplayName(object, feature));
								return false;
							}
						};
						editor.createControl(parent,"Prefix"); //$NON-NLS-1$
					}
					else {
						ObjectEditor editor = new TextObjectEditor(this,businessObject,attribute);
						editor.createControl(parent,Messages.DefinitionsPropertyComposite_Namespace_Label);
					}
				}
			};
			return composite;
		}

		@Override
		public ListCompositeContentProvider getContentProvider(EObject object, EStructuralFeature feature, EList<EObject>list) {
			if (contentProvider==null) {
				contentProvider = new ListCompositeContentProvider(this, object, feature, list) {

					@Override
					public Object[] getElements(Object inputElement) {
						List<Object> elements = new ArrayList<Object>();
						EcoreEMap<String,String> map = (EcoreEMap<String,String>)inputElement;
						for ( Map.Entry<String, String> entry : map.entrySet() ) {
							elements.add(entry);
						}
						return elements.toArray(new EObject[elements.size()]);
					}

				};
			}
			return contentProvider;
		}
		
		@Override
		protected int createColumnProvider(EObject theobject, EStructuralFeature thefeature) {
			if (columnProvider==null) {
				columnProvider = getColumnProvider(theobject, thefeature);
			}
			return columnProvider.getColumns().size();
		}
		
		@Override
		public ListCompositeColumnProvider getColumnProvider(EObject object, EStructuralFeature feature) {
			if (columnProvider==null) {
				columnProvider = new ListCompositeColumnProvider(this);
				columnProvider.add(new NamespacesTableColumn(object, 0));
				columnProvider.add(new NamespacesTableColumn(object, 1));
			}
			return columnProvider;
		}
		
		public class NamespacesTableColumn extends TableColumn {
			
			int columnIndex;
			
			public NamespacesTableColumn(EObject object, int columnIndex) {
				super(object,(EStructuralFeature)null);
				this.columnIndex = columnIndex;
			}

			@Override
			public String getProperty() {
				return getHeaderText();
			}

			@Override
			public String getHeaderText() {
				if (columnIndex==0)
					return Messages.DefinitionsPropertyComposite_Prefix_Label;
				return Messages.DefinitionsPropertyComposite_Namespace_Label;
			}

			@Override
			public String getText(Object element) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>)element;
				if (columnIndex==0)
					return entry.getKey();
				return entry.getValue();
			}
		}
	}
	
	public class ImportListComposite extends DefaultListComposite {

		/**
		 * @param parent
		 * @param style
		 */
		public ImportListComposite(AbstractBpmn2PropertySection section) {
			super(section);
		}

		@Override
		public ListCompositeColumnProvider getColumnProvider(EObject object, EStructuralFeature feature) {
			if (columnProvider==null) {
				columnProvider = new ListCompositeColumnProvider(this);
				
				// add a namespace prefix column that does NOT come from the Import object
				TableColumn tableColumn = new TableColumn(object,(EStructuralFeature)null) {
					@Override
					public String getHeaderText() {
						return Messages.DefinitionsPropertyComposite_Prefix_Label;
					}
	
					@Override
					public String getText(Object element) {
						Import imp = (Import)element;
						String prefix = NamespaceUtil.getPrefixForNamespace(imp.eResource(), imp.getNamespace());
						if (prefix!=null)
							return prefix;
						return ""; //$NON-NLS-1$
					}

					@Override
					public CellEditor createCellEditor (Composite parent) {
						CellEditor ce = null;
						// TODO: create a dialog cell editor for NS prefix
						return ce;
					}
				};
				columnProvider.add(tableColumn);
				// add remaining columns
				columnProvider.add(new TableColumn(object,PACKAGE.getImport_Namespace())).setEditable(false);
				columnProvider.add(new TableColumn(object,PACKAGE.getImport_Location())).setEditable(false);
				columnProvider.add(new TableColumn(object,PACKAGE.getImport_ImportType())).setEditable(false);
			}
			return columnProvider;
		}


		@Override
		protected EObject editListItem(EObject object, EStructuralFeature feature) {
			return super.editListItem(object, feature);
		}


		@Override
		protected Object removeListItem(EObject object, EStructuralFeature feature, int index) {
			EList<Import> list = (EList<Import>)object.eGet(feature);
			Import imp = list.get(index);
			ImportUtil.removeImport(imp);
//			return super.removeListItem(object, feature, index);
			return null;
		}

		@Override
		protected EObject addListItem(EObject object, EStructuralFeature feature) {
			SchemaImportDialog dialog = new SchemaImportDialog(getShell());
			if (dialog.open() == Window.OK) {
				Object result[] = dialog.getResult();
				if (result.length == 1) {
					ImportUtil importer = new ImportUtil();
					return importer.addImport(object, result[0]);
				}
			}
			return null;
		}
	}
	
	public class ImportDetailComposite extends DefaultDetailComposite {

		private Text text;
		private Button button;
		
		public ImportDetailComposite(Composite parent, int style) {
			super(parent, style);
		}

		/**
		 * @param section
		 */
		public ImportDetailComposite(AbstractBpmn2PropertySection section) {
			super(section);
		}
		
		@Override
		public void createBindings(EObject be) {
			final Import imp = (Import)be;
			
			Composite composite = getAttributesParent();
			TextObjectEditor editor;
			String label;
			EStructuralFeature feature;
			
			feature = null;
			label = Messages.DefinitionsPropertyComposite_Prefix_Label;
			editor = new TextAndButtonObjectEditor(this,be,feature) {

				@Override
				protected void buttonClicked(int buttonId) {
					IInputValidator validator = new IInputValidator() {

						@Override
						public String isValid(String newText) {
							String ns = NamespaceUtil.getNamespaceForPrefix(imp.eResource(), newText);
							if (ns==null)
								return null;
							return NLS.bind(
								Messages.DefinitionsPropertyComposite_Invalid_Duplicate,
								newText,ns);
						}
						
					};
					String initialValue = getText();
					InputDialog dialog = new InputDialog(
							getShell(),
							Messages.DefinitionsPropertyComposite_Prefix_Label,
							Messages.DefinitionsPropertyComposite_Prefix_Message,
							initialValue,
							validator);
					if (dialog.open()==Window.OK){
						setValue(dialog.getValue());
					}
				}
				
				protected boolean setValue(final Object value) {
					// remove old prefix
					String prefix = text.getText();
					NamespaceUtil.removeNamespaceForPrefix(imp.eResource(), prefix);
					// and add new
					NamespaceUtil.addNamespace(imp.eResource(), (String)value, imp.getNamespace());
					setText((String) value);
					return true;
				}
				
				protected String getText() {
					return getNamespacePrefix();
				}
			};
			editor.createControl(composite,label);
			
			feature = Bpmn2Package.eINSTANCE.getImport_Namespace();
			label = ModelUtil.getLabel(be, feature);
			editor = new TextObjectEditor(this,be, feature);
			editor.createControl(composite,label);
			editor.setEditable(false);

			feature = Bpmn2Package.eINSTANCE.getImport_Location();
			label = ModelUtil.getLabel(be, feature);
			editor = new TextObjectEditor(this,be, feature);
			editor.createControl(composite,label);
			editor.setEditable(false);

			feature = Bpmn2Package.eINSTANCE.getImport_ImportType();
			label = ModelUtil.getLabel(be, feature);
			editor = new TextObjectEditor(this,be, feature);
			editor.createControl(composite,label);
			editor.setEditable(false);
		}
		
		private String getNamespacePrefix() {
			Import imp = (Import)businessObject;
			String prefix = NamespaceUtil.getPrefixForNamespace(imp.eResource(), imp.getNamespace());
			if (prefix==null)
				prefix = ""; //$NON-NLS-1$
			return prefix;
		}
	}
}

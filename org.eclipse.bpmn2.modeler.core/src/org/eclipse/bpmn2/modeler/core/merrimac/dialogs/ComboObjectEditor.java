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

package org.eclipse.bpmn2.modeler.core.merrimac.dialogs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map.Entry;

import org.eclipse.bpmn2.modeler.core.Activator;
import org.eclipse.bpmn2.modeler.core.IConstants;
import org.eclipse.bpmn2.modeler.core.adapters.AdapterRegistry;
import org.eclipse.bpmn2.modeler.core.adapters.AdapterUtil;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author Bob Brodt
 *
 */
public class ComboObjectEditor extends MultivalueObjectEditor {

	protected ComboViewer comboViewer;
	private Composite buttons = null;
	private boolean keyPressed = false;
	private Button editButton = null;
	private Button createButton = null;
	
	/**
	 * @param parent
	 * @param object
	 * @param feature
	 */
	public ComboObjectEditor(AbstractDetailComposite parent, EObject object, EStructuralFeature feature) {
		this(parent, object, feature, null);
	}

	public ComboObjectEditor(AbstractDetailComposite parent, EObject object, EStructuralFeature feature, EClass featureEType) {
		super(parent, object, feature, featureEType);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.ui.property.editors.ObjectEditor#createControl(org.eclipse.swt.widgets.Composite, java.lang.String, int)
	 */
	@Override
	protected Control createControl(Composite composite, String label, int style) {
		if (label==null)
			label = ModelUtil.getLabel(object,feature);
		createLabel(composite, label);

		boolean canEdit = canEdit();
		boolean canEditInline = canEditInline();
		boolean canCreateNew = canCreateNew();
		
		if (style == SWT.READ_ONLY) {
			canEdit = false;
			canEditInline = false;
			canCreateNew = false;
		}
		
		if (!canEditInline)
			style |= SWT.READ_ONLY;
		comboViewer = createComboViewer(composite,
				AdapterRegistry.getLabelProvider(), style);
		Combo combo = comboViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, (canEdit || canCreateNew) ? 1 : 2, 1));
		combo.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				comboViewer = null;
			}
			
		});
		
		if (canEditInline) {
			combo.addKeyListener( new KeyListener() {

				@Override
				public void keyPressed(KeyEvent e) {
					keyPressed = true;
				}

				@Override
				public void keyReleased(KeyEvent e) {
				}
				
			});
			combo.addFocusListener( new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (keyPressed) {
						keyPressed = false;
						String text = comboViewer.getCombo().getText();
						comboViewer.setSelection(new StructuredSelection(text));
					}
				}
				
			});
		}

		buttons = null;
		if (canEdit || canCreateNew) {
			buttons =  getToolkit().createComposite(composite);
			buttons.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			buttons.setLayout(new FillLayout(SWT.HORIZONTAL));

			if (canCreateNew) {
				createButton = getToolkit().createButton(buttons, null, SWT.PUSH);
				createButton.setImage( Activator.getDefault().getImage(IConstants.ICON_ADD_20));
				createButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						// create a new target object
						try {
							EObject value = createObject();
							setValue(value);
							fillCombo();
						}
						catch (OperationCanceledException ex1) {
						}
						catch (Exception ex2) {
							Activator.logError(ex2);
						}
					}
				});
			}
			if (canEdit) {
				editButton = getToolkit().createButton(buttons, null, SWT.PUSH);
				editButton.setImage( Activator.getDefault().getImage(IConstants.ICON_EDIT_20));
				editButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						ISelection selection = comboViewer.getSelection();
						if (selection instanceof StructuredSelection) {
							String firstElement = (String) ((StructuredSelection) selection).getFirstElement();
							if ((firstElement != null && firstElement.isEmpty())) {
								// nothing to edit
								firstElement = null;
							}
							if (firstElement != null && comboViewer.getData(firstElement) instanceof EObject) {
								EObject value = (EObject) comboViewer.getData(firstElement);
								try {
									value = editObject(value);
									setValue(value);
									fillCombo();
								}
								catch (OperationCanceledException ex1) {
								}
								catch (Exception ex2) {
									Activator.logError(ex2);
								}
							}
						}
					}
				});
			}
		}

		fillCombo();

		comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (!isWidgetUpdating) {
					ISelection selection = comboViewer.getSelection();
					if (selection instanceof StructuredSelection) {
						String firstElement = (String) ((StructuredSelection) selection).getFirstElement();
						if(firstElement!=null && comboViewer.getData(firstElement)!=null)
							setValue(comboViewer.getData(firstElement));
						else {
							if (firstElement!=null && firstElement.isEmpty())
								firstElement = null;
							if (firstElement==null)
								firstElement = comboViewer.getCombo().getText();
							setValue(firstElement);
							fillCombo();
						}
						if (editButton!=null)
							editButton.setEnabled(firstElement!=null && !firstElement.isEmpty());
					}
				}
			}
		});
		
		return combo;
	}
	
	protected boolean canEdit() {
		return ModelUtil.canEdit(object,feature);
	}
	
	protected boolean canEditInline() {
		return ModelUtil.canEditInline(object,feature);
	}
	
	protected boolean canCreateNew() {
		return ModelUtil.canCreateNew(object,feature);
	}
	
	protected boolean canSetNull() {
		return ModelUtil.canSetNull(object,feature);
	}
	
	protected EObject createObject() throws Exception {
		FeatureEditingDialog dialog = new FeatureEditingDialog(getDiagramEditor(), object, feature, null);
		dialog.setFeatureEType(featureEType);
		if ( dialog.open() == Window.OK)
			return dialog.getNewObject();
		throw new OperationCanceledException("Dialog Cancelled");
	}
	
	protected EObject editObject(EObject value) throws Exception {
		FeatureEditingDialog dialog = new FeatureEditingDialog(getDiagramEditor(),
				object, feature, value);
		dialog.setFeatureEType(featureEType);
		if ( dialog.open() == Window.OK)
			return dialog.getNewObject();
		throw new OperationCanceledException("Dialog Cancelled");
	}

	@Override
	protected boolean setValue(Object result) {
		keyPressed = false;
		return super.setValue(result);
	}

	@Override
	public Object getValue() {
		Object v =  object.eGet(feature);
		// hack to deal with List features: use the first element in the list to
		// determine which item to select as active in the combobox
		if (v instanceof EObjectEList) {
			EObjectEList list = (EObjectEList)v;
			if (list.size()>0)
				v = list.get(0);
		}
		return v;
	}
	
	protected void fillCombo() {
		try {
			isWidgetUpdating = true;
			if (comboViewer!=null) {
				Object oldValue =  getValue();
		
				while (comboViewer.getElementAt(0) != null)
					comboViewer.remove(comboViewer.getElementAt(0));
				
				Hashtable<String,Object> choices = getChoiceOfValues(object, feature);
				if (canSetNull()) {
					// selecting this one will set the target's value to null
					comboViewer.add("");
				}
				
				// add all other possible selections
				ExtendedPropertiesAdapter adapter = (ExtendedPropertiesAdapter) AdapterUtil.adapt(oldValue, ExtendedPropertiesAdapter.class);
				if (ModelUtil.isStringWrapper(oldValue))
					oldValue = ModelUtil.getStringWrapperValue(oldValue);
	
				ArrayList<String> list = new ArrayList<String>(choices.keySet());
				Collections.sort(list);
				StructuredSelection currentSelection = null;
				for (String key : list) {
					comboViewer.add(key);
					Object newValue = choices.get(key); 
					if (newValue!=null) {
						comboViewer.setData(key, newValue);
						if (currentSelection==null) {
							String oldValueString = oldValue==null ? null : oldValue.toString();
							if (newValue.equals(oldValue) || key.equals(oldValue) || key.equals(oldValueString)) {
								currentSelection = new StructuredSelection(key);
							}
							else if (adapter!=null) {
								if (adapter.getObjectDescriptor().equals(newValue)) {
									currentSelection = new StructuredSelection(key);
								}
							}
						}
					}
				}
				if (currentSelection!=null)
					comboViewer.setSelection(currentSelection);
				if (editButton!=null)
					editButton.setEnabled(currentSelection!=null);
			}
		}
		finally {
			isWidgetUpdating = false;
		}
	}
	
	private boolean itemsChanged() {
		if (comboViewer==null)
			return false;

		Object newValue =  getValue();
		ExtendedPropertiesAdapter adapter = (ExtendedPropertiesAdapter) AdapterUtil.adapt(newValue, ExtendedPropertiesAdapter.class);
		if (ModelUtil.isStringWrapper(newValue))
			newValue = ModelUtil.getStringWrapperValue(newValue);

		isWidgetUpdating = false;
		Hashtable<String,Object> oldChoices = new Hashtable<String,Object>();
		int index = 0;
		while (comboViewer.getElementAt(index) != null) {
			String key = (String)comboViewer.getElementAt(index);
			if (!key.isEmpty()) {
				Object value = comboViewer.getData(key);
				oldChoices.put(key, value);
			}
			++index;
		}
		isWidgetUpdating = false;
		
		Hashtable<String,Object> newChoices = getChoiceOfValues(object, feature);

		if (oldChoices.size()!=newChoices.size())
			return true;
		
		StructuredSelection oldSelection = (StructuredSelection)comboViewer.getSelection();
		Object oldValue = oldSelection.getFirstElement();
		if (oldValue instanceof String)
			oldValue = comboViewer.getData((String)oldValue);
		if (ModelUtil.isStringWrapper(oldValue))
			oldValue = ModelUtil.getStringWrapperValue(oldValue);
		if (newValue==null) {
			if (oldValue!=null)
				return true;
		}
		else if (!newValue.equals(oldValue))
			return true;

		for (Entry<String, Object> entry : newChoices.entrySet()) {
			oldValue = oldChoices.get(entry.getKey());
			if (ModelUtil.isStringWrapper(oldValue))
				oldValue = ModelUtil.getStringWrapperValue(oldValue);
			newValue = entry.getValue();
			if (ModelUtil.isStringWrapper(newValue))
				newValue = ModelUtil.getStringWrapperValue(newValue);
			if (oldValue!=newValue) {
				if (oldValue!=null && newValue!=null) {
					if (!oldValue.equals(newValue))
						return true;
				}
				else
					return true;
			}
		}
		return false;
	}

	private ComboViewer createComboViewer(Composite parent, AdapterFactoryLabelProvider labelProvider, int style) {
		ComboViewer comboViewer = new ComboViewer(parent, style);
		comboViewer.setLabelProvider(labelProvider);
		return comboViewer;
	}
	
	@Override
	public void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		if (notification.getEventType() == -1 || itemsChanged()) {
			fillCombo();
		}
	}
	
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		comboViewer.getCombo().setVisible(visible);
		GridData data = (GridData)comboViewer.getCombo().getLayoutData();
		data.exclude = !visible;
		if (buttons!=null) {
			buttons.setVisible(visible);
			data = (GridData)buttons.getLayoutData();
			data.exclude = !visible;
		}
	}
	
	public void dispose() {
		super.dispose();
		if (comboViewer!=null && !comboViewer.getCombo().isDisposed()) {
			comboViewer.getCombo().dispose();
			comboViewer = null;
		}
		if (editButton!=null && !editButton.isDisposed()) {
			editButton.dispose();
			editButton = null;
		}
		if (createButton!=null && !createButton.isDisposed()) {
			createButton.dispose();
			createButton = null;
		}
		if (buttons!=null && !buttons.isDisposed()) {
			buttons.dispose();
			buttons = null;
		}
	}
	
	public Control getControl() {
		return comboViewer.getCombo();
	}
}

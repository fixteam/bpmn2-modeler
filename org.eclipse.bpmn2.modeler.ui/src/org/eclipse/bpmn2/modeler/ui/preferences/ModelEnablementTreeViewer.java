package org.eclipse.bpmn2.modeler.ui.preferences;

import java.util.List;

import org.eclipse.bpmn2.modeler.core.preferences.ModelEnablementTreeEntry;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.IElementComparer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;

public class ModelEnablementTreeViewer extends Composite {
	
	private Label label;
	private Tree tree;
	private CheckboxTreeViewer treeViewer;
	
	public ModelEnablementTreeViewer(Composite parent, String name) {
		
		super(parent, SWT.BORDER);
		
		setLayout(new GridLayout(1, false));
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		label = new Label(this, SWT.NONE);
		label.setText(name);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));

		treeViewer = new CheckboxTreeViewer(this, SWT.BORDER);
		tree = treeViewer.getTree();

		GridData data = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
		data.heightHint = 200;
		data.widthHint = 50;
		tree.setLayoutData(data);
		treeViewer.setCheckStateProvider(new ICheckStateProvider() {
			@Override
			public boolean isChecked(Object element) {
				if (element instanceof ModelEnablementTreeEntry) {
					ModelEnablementTreeEntry entry = (ModelEnablementTreeEntry)element;
					if (entry.getChildren().size()>0) {
						for (ModelEnablementTreeEntry child : entry.getChildren()) {
							if (child.getEnabled())
								return true;
						}
						return false;
					}
					return entry.getEnabled();
				}
				return false;
			}

			@Override
			public boolean isGrayed(Object element) {
				if (element instanceof ModelEnablementTreeEntry) {
					ModelEnablementTreeEntry entry = (ModelEnablementTreeEntry)element;
					int countEnabled = 0;
					for (ModelEnablementTreeEntry child : entry.getChildren()) {
						if (child.getEnabled())
							++countEnabled;
					}
					return countEnabled>0 && countEnabled != entry.getChildren().size();
				}
				return false;
			}
			
		});
		
		treeViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				boolean checked = event.getChecked();
				Object element = event.getElement();
				if (element instanceof ModelEnablementTreeEntry) {
					ModelEnablementTreeEntry entry = (ModelEnablementTreeEntry)element;
					updateDescendents(entry, checked);
					updateAncestors(entry.getParent(), checked);
				}
			}
			
			void updateDescendents(ModelEnablementTreeEntry entry, boolean checked) {
				for (ModelEnablementTreeEntry child : entry.getChildren()) {
					updateDescendents(child,checked);
				}
				entry.setSubtreeEnabled(checked);
				treeViewer.setSubtreeChecked(entry, checked);
				
				treeViewer.setChecked(entry, checked);
				treeViewer.setGrayed(entry, false);
				for (ModelEnablementTreeEntry friend : entry.getFriends()) {
					updateAncestors(friend, checked);
					if (friend.getParent()!=null)
						updateAncestors(friend.getParent(), checked);
				}
				for (ModelEnablementTreeEntry child : entry.getChildren()) {
					for (ModelEnablementTreeEntry friend : child.getFriends()) {
						if (child.getParent()!=null)
							updateAncestors(child.getParent(), checked);
						updateAncestors(friend, checked);
					}
				}
			}
			
			void updateAncestors(ModelEnablementTreeEntry entry, boolean checked) {
				while (entry!=null) {
					int enabled = entry.getSubtreeEnabledCount();
					int size = entry.getSubtreeEnabledCount();
					if (enabled==0) {
						treeViewer.setChecked(entry, false);
						entry.setEnabled(false);
						checked = true;
					}
					else if (enabled==size) {
						treeViewer.setChecked(entry, true);
						treeViewer.setGrayed(entry, false);
						entry.setEnabled(true);
					}
					else {
						treeViewer.setGrayChecked(entry, true);
						entry.setEnabled(true);
					}
					
					for (ModelEnablementTreeEntry friend : entry.getFriends()) {
						updateAncestors(friend, checked);
					}
					refreshSiblings(entry);
					entry = entry.getParent();
				}
			}

			private void refreshSiblings(Object element) {
				Control kids[] = getParent().getChildren();
				for (Control k : kids) {
					if (k instanceof ModelEnablementTreeViewer) {
						if (k.isVisible())
							((ModelEnablementTreeViewer)k).refresh(element);
					}
				}
			}
		});
		
		treeViewer.setComparer(new IElementComparer() {

			@Override
			public boolean equals(Object a, Object b) {
				return a == b;
			}

			@Override
			public int hashCode(Object element) {
				return System.identityHashCode(element);
			}
		});
		treeViewer.setUseHashlookup(true);
	}

	public DataBindingContext setInput(List<ModelEnablementTreeEntry> entries) {
		if (treeViewer==null || entries==null)
			return null;
		
		DataBindingContext bindingContext = new DataBindingContext();
		//
		treeViewer.setContentProvider(new ITreeContentProvider() {

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}

			@Override
			public void dispose() {
			}

			@Override
			public boolean hasChildren(Object element) {
				if (element instanceof ModelEnablementTreeEntry) {
					return !((ModelEnablementTreeEntry) element).getChildren().isEmpty();
				}
				return false;
			}

			@Override
			public Object getParent(Object element) {
				if (element instanceof ModelEnablementTreeEntry) {
					return ((ModelEnablementTreeEntry) element).getParent();
				}
				return null;
			}

			@Override
			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof WritableList) {
					return ((WritableList) inputElement).toArray();
				}
				return null;
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof ModelEnablementTreeEntry) {
					return ((ModelEnablementTreeEntry) parentElement).getChildren().toArray();
				}
				return null;
			}
		});

		treeViewer.setLabelProvider(new ILabelProvider() {
			@Override
			public void removeListener(ILabelProviderListener listener) {
			}

			@Override
			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			@Override
			public void dispose() {

			}

			@Override
			public void addListener(ILabelProviderListener listener) {
			}

			@Override
			public Image getImage(Object element) {
				return null;
			}

			@Override
			public String getText(Object element) {
				if (element instanceof ModelEnablementTreeEntry) {
					return ((ModelEnablementTreeEntry) element).getName();
				}
				return null;
			}
		});
		WritableList writableList = new WritableList(entries, ModelEnablementTreeEntry.class);
		treeViewer.setInput(writableList);
		//
		return bindingContext;
	}

	public void addCheckStateListener(ICheckStateListener listener) {
		treeViewer.addCheckStateListener(listener);
	}
	
	public void refresh() {
		treeViewer.refresh();
	}

	public void refresh(Object element) {
		treeViewer.refresh(element);
	}

	public Tree getTree() {
		return tree;
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (getParent().getLayout() instanceof GridLayout) {
			GridData data = (GridData)getLayoutData();
			data.exclude = !visible;

			getParent().layout();
		}
	}
}
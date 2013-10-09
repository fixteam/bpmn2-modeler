package org.eclipse.bpmn2.modeler.core.di;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.modeler.core.Activator;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.ListDialog;

public class ImportDiagnostics implements IStructuredContentProvider, ILabelProvider {
	private static class ImportDiagnostic {
		public EObject element;
		public String message;
		public int severity;
		public ImportDiagnostic(int severity, EObject element, String message) {
			this.severity = severity;
			this.element = element;
			this.message = message;
		}
	}
	private Resource resource;
	private TargetRuntime runtime;
	private List<ImportDiagnostics.ImportDiagnostic> entries;
	
	public ImportDiagnostics(Resource resource) {
		this.resource = resource;
		runtime = TargetRuntime.getCurrentRuntime();
	}
	
	public void add(int severity, EObject element, String message) {
		if (entries==null)
			entries = new ArrayList<ImportDiagnostics.ImportDiagnostic>();
		entries.add(new ImportDiagnostic(severity, element, message));
	}
	
	public List<ImportDiagnostic> get(EObject element) {
		if (entries!=null) {
			List<ImportDiagnostic> list = new ArrayList<ImportDiagnostic>();
			for (ImportDiagnostic d : entries) {
				if (d.element == element)
					list.add(d);
			}
			if (!list.isEmpty())
				return list;
		}
		return null;
	}
	
	public void report() {
		if (entries!=null) {
			ListDialog dlg = new ListDialog(Display.getDefault().getActiveShell());
			dlg.setContentProvider(this);
			dlg.setLabelProvider(this);
			for (ImportDiagnostic d : entries) {
				Activator.logStatus(new Status(d.severity, Activator.PLUGIN_ID, getText(d)));
			}
			dlg.setInput(entries);
			dlg.setMessage(
					"The following errors were detected while trying to load this file.\n"+
					"Please see the Error Log for more information."
			);
			dlg.setAddCancelButton(false);
			dlg.setTitle("BPMN2 Modeler - Import Errors");
			dlg.open();
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image getImage(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText(Object element) {
		ImportDiagnostic d = (ImportDiagnostic)element;
		String text = "";
		String id = "";
		if (d.element!=null)
			text = getText(d.element);
		else
			return d.message;
		
		text += ": " + d.message;
		return text;
	}

	public String getText(EObject element) {
		String text = "";
		String type = "";
		String id = "";
		String name = "";
		String customTaskId = "";
		if (runtime!=null) {
			for (CustomTaskDescriptor tc : runtime.getCustomTasks()) {
				customTaskId = tc.getFeatureContainer().getId(element);
				if (customTaskId==null)
					customTaskId = "";
			}
		}
		
		type = element.eClass().getName();
		
		EStructuralFeature f = element.eClass().getEStructuralFeature("id");
		if (f!=null) {
			id = (String)element.eGet(f);
			if (id==null)
				id = "";
		}
		if (id.isEmpty())
			id = "unknown";
		
		f = element.eClass().getEStructuralFeature("name");
		if (f!=null) {
			name = (String)element.eGet(f);
			if (name==null)
				name = "";
		}
		if (!customTaskId.isEmpty())
			text = type + " Custom Task " + customTaskId;
		else
			text = type;
		
		if (name.isEmpty())
			text += " id=\"" + id + "\"";
		else
			text += " \"" + name + "\"";

		return text;
	}
	
	@Override
	public Object[] getElements(Object inputElement) {
		return entries.toArray();
	}
}
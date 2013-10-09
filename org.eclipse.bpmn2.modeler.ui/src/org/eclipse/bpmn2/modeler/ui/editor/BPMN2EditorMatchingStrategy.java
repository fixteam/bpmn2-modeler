package org.eclipse.bpmn2.modeler.ui.editor;

import org.eclipse.bpmn2.modeler.ui.Bpmn2DiagramEditorInput;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.ui.editor.DiagramEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPart;

public class BPMN2EditorMatchingStrategy implements IEditorMatchingStrategy {

	public BPMN2EditorMatchingStrategy() {
	}

	@Override
	public boolean matches(IEditorReference editorRef, IEditorInput input) {
		IWorkbenchPart part = editorRef.getPart(true);
		if (part instanceof BPMN2MultiPageEditor) {
			BPMN2Editor editor = ((BPMN2MultiPageEditor) part).getDesignEditor();
			URI editorUri = editor.getModelUri();
			if (input instanceof Bpmn2DiagramEditorInput) {
				URI inputUri = ((Bpmn2DiagramEditorInput) input).getModelUri();
				if (editorUri.equals(inputUri)) {
					return true;
				}
			}
			else if (input instanceof URIEditorInput) {
				final URIEditorInput uriInput = (URIEditorInput) input;
				URI inputUri = uriInput.getURI();
				if (editorUri.equals(inputUri)) {
					return true;
				}
			}
			else {
				IFile inputFile = getFile(input);
				if (inputFile != null) {
					// check whether the given input comes with a file which is
					// already opened in the diagram editor.
					IFile editorFile = getFile(editor.getEditorInput());
					if (inputFile.equals(editorFile)) {
						return true;
					}
				}
			}

		}
		return false;
	}

	private IFile getFile(Object input) {
		IFile file = null;
		if (input instanceof IAdaptable) {
			file = (IFile) ((IAdaptable) input).getAdapter(IFile.class);
		}
		return file;
	}
}

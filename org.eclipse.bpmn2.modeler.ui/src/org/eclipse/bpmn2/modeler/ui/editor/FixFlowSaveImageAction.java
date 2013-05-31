package org.eclipse.bpmn2.modeler.ui.editor;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.graphiti.features.ISaveImageFeature;
import org.eclipse.graphiti.features.context.ISaveImageContext;
import org.eclipse.graphiti.platform.IDiagramBehavior;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.graphiti.ui.internal.Messages;
import org.eclipse.graphiti.ui.internal.action.SaveImageAction;
import org.eclipse.graphiti.ui.internal.services.GraphitiUiInternal;
import org.eclipse.graphiti.ui.platform.IConfigurationProvider;

public class FixFlowSaveImageAction extends SaveImageAction {

	private ISaveImageFeature saveImageFeature;

	private ISaveImageContext context;

	private DiagramEditor graphicsEditor;
	
	public static final String ACTION_ID = "export_diagram_action"; //$NON-NLS-1$
	
	public static final String ACTION_DEFINITION_ID = "org.eclipse.graphiti.ui.internal.action.SaveImageAction"; //$NON-NLS-1$
	
	public FixFlowSaveImageAction(IDiagramBehavior diagramBehavior, IConfigurationProvider configurationProvider) {
		super(diagramBehavior, configurationProvider);
		this.saveImageFeature = saveImageFeature;
		this.context = context;
		this.graphicsEditor = graphicsEditor;
		setText("\u5BFC\u51FA\u56FE\u50CF...");
		setToolTipText("\u5BFC\u51FA\u56FE\u50CF...");
		setId(ACTION_ID);
		setActionDefinitionId(ACTION_DEFINITION_ID);
	}
	
	@Override
	public boolean isEnabled() {
		return saveImageFeature.canSave(context);
	}

	@Override
	public void run() {
		saveImageFeature.preSave(context);

		// get viewer and start save-image-dialog
		GraphicalViewer viewer = (GraphicalViewer) graphicsEditor.getAdapter(GraphicalViewer.class);
		FixFlowUiService fixFlowUiService = new FixFlowUiService();
		fixFlowUiService.startSaveAsImageDialog(viewer);

		saveImageFeature.postSave(context);
	}
}

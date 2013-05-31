package org.eclipse.bpmn2.modeler.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentAction;
import org.eclipse.gef.ui.actions.DirectEditAction;
import org.eclipse.gef.ui.actions.MatchHeightAction;
import org.eclipse.gef.ui.actions.MatchWidthAction;
import org.eclipse.gef.ui.actions.ToggleGridAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ISaveImageFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.editor.DefaultPersistencyBehavior;
import org.eclipse.graphiti.ui.editor.DefaultUpdateBehavior;
import org.eclipse.graphiti.ui.editor.DiagramBehavior;
import org.eclipse.graphiti.ui.editor.DiagramEditorContextMenuProvider;
import org.eclipse.graphiti.ui.editor.IDiagramContainerUI;
import org.eclipse.graphiti.ui.internal.action.CopyAction;
import org.eclipse.graphiti.ui.internal.action.DeleteAction;
import org.eclipse.graphiti.ui.internal.action.FeatureExecutionHandler;
import org.eclipse.graphiti.ui.internal.action.PasteAction;
import org.eclipse.graphiti.ui.internal.action.RemoveAction;
import org.eclipse.graphiti.ui.internal.action.SaveImageAction;
import org.eclipse.graphiti.ui.internal.action.ToggleContextButtonPadAction;
import org.eclipse.graphiti.ui.internal.action.UpdateAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.IHandlerService;

public class BPMN2EditorDiagramBehavior extends DiagramBehavior {
	private IDiagramContainerUI diagramContainer;
	private IWorkbenchPart parentPart;

	BPMN2Editor bpmn2Editor;
	
	public BPMN2EditorDiagramBehavior(BPMN2Editor bpmn2Editor) {
		super(bpmn2Editor);
		this.bpmn2Editor = bpmn2Editor;
		setParentPart((IWorkbenchPart) bpmn2Editor);
		initDefaultBehaviors();
	}
	
	@Override
	protected DefaultUpdateBehavior createUpdateBehavior() {
		return new BPMN2EditorUpdateBehavior(this);
	}
	
    @Override
    protected DefaultPersistencyBehavior createPersistencyBehavior() {
    	return new BPMN2PersistencyBehavior(this);
    }
	
	@Override
	protected PictogramElement[] getPictogramElementsForSelection() {
		// filter out invisible elements when setting selection
		PictogramElement[] pictogramElements = super.getPictogramElementsForSelection();
		if (pictogramElements==null)
			return null;
		ArrayList<PictogramElement> visibleList = new ArrayList<PictogramElement>();
		for (PictogramElement pe : pictogramElements) {
			if (pe.isVisible())
				visibleList.add(pe);
		}
		return visibleList.toArray(new PictogramElement[visibleList.size()]);
	}


	@Override
	protected ContextMenuProvider createContextMenuProvider() {
		return new DiagramEditorContextMenuProvider(getDiagramContainer().getGraphicalViewer(),
				getDiagramContainer().getActionRegistry(),
				getConfigurationProvider()) {
			@Override
			public void buildContextMenu(IMenuManager manager) {
				super.buildContextMenu(manager);
				IAction action = getDiagramContainer().getActionRegistry().getAction("show.or.hide.source.view");
				action.setText(action.getText());
				manager.add(action);

				int pageIndex = bpmn2Editor.getMultipageEditor().getActivePage();
				int lastPage = bpmn2Editor.getMultipageEditor().getDesignPageCount();
				if (pageIndex > 0 && pageIndex < lastPage) {
					action = getDiagramContainer().getActionRegistry().getAction("delete.page");
					action.setText(action.getText());
					action.setEnabled(action.isEnabled());
					manager.add(action);
				}
			}
		};
	}

	/*@Override
	protected void initActionRegistry(ZoomManager zoomManager) {
		if (parentPart == null)
		{
			return;
		}
		final ActionRegistry actionRegistry = diagramContainer.getActionRegistry();
		@SuppressWarnings("unchecked")
		final List<String> selectionActions = diagramContainer.getSelectionActions();

		// register predefined actions (e.g. update, remove, delete, ...)
		IAction action = new UpdateAction(parentPart, getConfigurationProvider());
		actionRegistry.registerAction(action);
		selectionActions.add(action.getId());

		action = new RemoveAction(parentPart, getConfigurationProvider());
		actionRegistry.registerAction(action);
		selectionActions.add(action.getId());

		action = new DeleteAction(parentPart, getConfigurationProvider());
		actionRegistry.registerAction(action);
		selectionActions.add(action.getId());

		action = new CopyAction(parentPart, getConfigurationProvider());
		actionRegistry.registerAction(action);
		selectionActions.add(action.getId());

		action = new PasteAction(parentPart, getConfigurationProvider());
		actionRegistry.registerAction(action);
		selectionActions.add(action.getId());

		IFeatureProvider fp = getConfigurationProvider().getDiagramTypeProvider().getFeatureProvider();
		if (fp != null) {
			ISaveImageFeature sf = fp.getSaveImageFeature();

			if (sf != null) {
				action = new SaveImageAction(this, getConfigurationProvider());
				actionRegistry.registerAction(action);
				selectionActions.add(action.getId());
			}
		}

		registerAction(new ZoomInAction(zoomManager));
		registerAction(new ZoomOutAction(zoomManager));
		registerAction(new DirectEditAction(parentPart));

		registerAction(new AlignmentAction(parentPart, PositionConstants.LEFT));
		registerAction(new AlignmentAction(parentPart, PositionConstants.RIGHT));
		registerAction(new AlignmentAction(parentPart, PositionConstants.TOP));
		registerAction(new AlignmentAction(parentPart, PositionConstants.BOTTOM));
		registerAction(new AlignmentAction(parentPart, PositionConstants.CENTER));
		registerAction(new AlignmentAction(parentPart, PositionConstants.MIDDLE));
		registerAction(new MatchWidthAction(parentPart));
		registerAction(new MatchHeightAction(parentPart));
		IAction showGrid = new ToggleGridAction(diagramContainer.getGraphicalViewer());
		diagramContainer.getActionRegistry().registerAction(showGrid);

		// Bug 323351: Add button to toggle a flag if the context pad buttons
		// shall be shown or not
		IAction toggleContextButtonPad = new ToggleContextButtonPadAction(this);
		toggleContextButtonPad.setChecked(false);
		actionRegistry.registerAction(toggleContextButtonPad);
		// End bug 323351

		IHandlerService hs = (IHandlerService) parentPart.getSite()
				.getService(IHandlerService.class);
		hs.activateHandler(FeatureExecutionHandler.COMMAND_ID, new FeatureExecutionHandler(getConfigurationProvider()));
	}*/
}
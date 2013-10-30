package org.eclipse.bpmn2.modeler.ui.editor;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ISaveImageFeature;
import org.eclipse.graphiti.features.context.ISaveImageContext;
import org.eclipse.graphiti.features.context.impl.SaveImageContext;
import org.eclipse.graphiti.internal.command.FeatureCommandWithContext;
import org.eclipse.graphiti.internal.command.GenericFeatureCommandWithContext;
import org.eclipse.graphiti.internal.command.ICommand;
import org.eclipse.graphiti.platform.IDiagramBehavior;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.graphiti.ui.internal.Messages;
import org.eclipse.graphiti.ui.internal.action.SaveImageAction;
import org.eclipse.graphiti.ui.internal.command.GefCommandWrapper;
import org.eclipse.graphiti.ui.internal.services.GraphitiUiInternal;
import org.eclipse.graphiti.ui.platform.IConfigurationProvider;

public class FixFlowSaveImageAction extends SaveImageAction {
        private IDiagramBehavior diagramBehavior;
        private IConfigurationProvider configurationProvider;
        
        public static final String TOOL_TIP = Messages.SaveImageAction_1_xmsg;
        
        public static final String TEXT = Messages.SaveImageAction_0_xmsg;
        
        public static final String ACTION_ID = "export_diagram_action"; //$NON-NLS-1$
        
        public static final String ACTION_DEFINITION_ID = "org.eclipse.graphiti.ui.internal.action.SaveImageAction"; //$NON-NLS-1$

        public FixFlowSaveImageAction(IDiagramBehavior diagramBehavior, IConfigurationProvider configurationProvider) {
                super(diagramBehavior, configurationProvider);
                this.diagramBehavior = diagramBehavior;
                this.configurationProvider = configurationProvider;
                
                setText("\u5BFC\u51FA\u56FE\u50CF...");
                setToolTipText("\u5BFC\u51FA\u56FE\u50CF...");
                setId(ACTION_ID);
                setActionDefinitionId(ACTION_DEFINITION_ID);
        }
        
        @Override
        public boolean isEnabled() {
                IFeatureProvider featureProvider = getFeatureProvider();
                if (featureProvider == null) {
                        return false;
                }
                ISaveImageFeature feature = featureProvider.getSaveImageFeature();
                ISaveImageContext context = createSaveImageContext();
                if (feature == null || !feature.canSave(context)) {
                        return false;
                }

                if (((IAdaptable) diagramBehavior.getDiagramContainer()).getAdapter(GraphicalViewer.class) == null) {
                        return false;
                }

                return true;
        }

        @Override
        public void run() {
                ISaveImageContext context = createSaveImageContext();
                IFeatureProvider featureProvider = getFeatureProvider();
                ISaveImageFeature feature = featureProvider.getSaveImageFeature();
                if (feature != null) {
                        FeatureCommandWithContext command = new GenericFeatureCommandWithContext(feature, context);
                        executeOnCommandStack(command);
                }
        }

        private ISaveImageContext createSaveImageContext() {
                SaveImageContext context = new SaveImageContext();
                return context;
        }

        private IFeatureProvider getFeatureProvider() {
                return configurationProvider.getDiagramTypeProvider().getFeatureProvider();
        }

        private void executeOnCommandStack(ICommand command) {
                CommandStack commandStack = configurationProvider.getDiagramBehavior().getEditDomain().getCommandStack();
                GefCommandWrapper wrapperCommand = new GefCommandWrapper(command, configurationProvider.getDiagramBehavior()
                                .getEditingDomain());
                commandStack.execute(wrapperCommand);
        }
}
/**
 * 对应graphiti0.92版本
 */
/*package org.eclipse.bpmn2.modeler.ui.editor;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.graphiti.features.ISaveImageFeature;
import org.eclipse.graphiti.features.context.ISaveImageContext;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.graphiti.ui.internal.Messages;
import org.eclipse.graphiti.ui.internal.action.SaveImageAction;
import org.eclipse.graphiti.ui.internal.services.GraphitiUiInternal;

public class FixFlowSaveImageAction extends SaveImageAction {

	private ISaveImageFeature saveImageFeature;

	private ISaveImageContext context;

	private DiagramEditor graphicsEditor;
	
	public static final String ACTION_ID = "export_diagram_action"; //$NON-NLS-1$
	
	public static final String ACTION_DEFINITION_ID = "org.eclipse.graphiti.ui.internal.action.SaveImageAction"; //$NON-NLS-1$

	public FixFlowSaveImageAction(ISaveImageFeature saveImageFeature, ISaveImageContext context, DiagramEditor graphicsEditor) {
		super(saveImageFeature, context, graphicsEditor);
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
*/
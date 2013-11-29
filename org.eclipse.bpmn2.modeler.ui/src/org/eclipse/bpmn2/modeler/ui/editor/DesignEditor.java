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
package org.eclipse.bpmn2.modeler.ui.editor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Choreography;
import org.eclipse.bpmn2.Collaboration;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNEdge;
import org.eclipse.bpmn2.di.BPMNPlane;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.Activator;
import org.eclipse.bpmn2.util.Bpmn2Resource;
import org.eclipse.dd.di.DiagramElement;
import org.eclipse.dd.di.Plane;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.context.impl.AreaContext;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.contentoutline.ContentOutline;

import com.founder.fix.designer.base.util.ProjectUtil;
import com.founder.fix.designer.base.util.TemplateUtil;

public class DesignEditor extends BPMN2Editor {
	protected ResourceSetListener resourceSetListener = null;
	private BPMNDiagram bpmnDiagramDeleted = null;
	// the container that holds the tabFolder
	protected Composite container;
	protected CTabFolder tabFolder;
	private int defaultTabHeight;

	public DesignEditor(BPMN2MultiPageEditor bpmn2MultiPageEditor, BPMN2MultiPageEditor mpe) {
		super(mpe);
	}

	public void deleteBpmnDiagram(BPMNDiagram bpmnDiagram) {
		this.bpmnDiagramDeleted = bpmnDiagram;
	}

	public void dispose() {
		if (bpmnDiagramDeleted == null) {
			getEditingDomain().removeResourceSetListener(resourceSetListener);
			resourceSetListener = null;
			super.dispose();
		} else {
			bpmnDiagramDeleted = null;
		}
	}

	@Override
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		if (resourceSetListener == null) {
			resourceSetListener = new AddRemoveDiagramListener();
			getEditingDomain().addResourceSetListener(resourceSetListener);
		}
	}

	@Override
	protected void setPartName(String partName) {
		// IEditorInput input = getEditorInput();
		// if (input instanceof Bpmn2DiagramEditorInput) {
		// URI uri = ((Bpmn2DiagramEditorInput)input).getModelUri();
		// partName = URI.decode(uri.trimFileExtension().lastSegment());
		// }
		// else if (input instanceof DiagramEditorInput) {
		// URI uri = ((DiagramEditorInput)input).getUri();
		// partName = URI.decode(uri.trimFileExtension().lastSegment());
		// }
		super.setPartName(URI.decode(partName));
	}

	private boolean inSelectionChanged = false;

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		// is the selected EObject in our resource?
		if (!inSelectionChanged) {
			try {
				inSelectionChanged = true;
				EObject object = BusinessObjectUtil.getBusinessObjectForSelection(selection);
				if (object != null && object.eResource() == bpmnResource) {
					BPMNDiagram newBpmnDiagram = null;
					boolean showSelection = true;
					if (object instanceof BaseElement) {
						// If the selection came from the ContentOutline then
						// navigate to
						// diagram page corresponds to this
						// flowElementsContainer if one exists
						if (part instanceof ContentOutline) {
							newBpmnDiagram = DIUtils.findBPMNDiagram((BaseElement) object, true);
							Object o = DIUtils.findBPMNDiagram((BaseElement) object, false);
							if (o == newBpmnDiagram)
								showSelection = false;
						}
					} else if (object instanceof BPMNDiagram) {
						newBpmnDiagram = (BPMNDiagram) object;
						showSelection = false;
					}
					if (newBpmnDiagram != null && getBpmnDiagram() != newBpmnDiagram) {
						multipageEditor.showDesignPage(newBpmnDiagram);
						final BPMNDiagram d = newBpmnDiagram;
						if (showSelection) {
							Display.getDefault().asyncExec(new Runnable() {
								public void run() {
									showDesignPage(d);
									Object sel = BusinessObjectUtil.getPictogramElementForSelection(selection);
									if (sel instanceof PictogramElement)
										DesignEditor.super.selectPictogramElements(new PictogramElement[] { (PictogramElement) sel });
								}
							});
						}
					}
				}
				DesignEditor.super.selectionChanged(part, selection);

			} catch (Exception e) {
			} finally {
				inSelectionChanged = false;
			}
		}
	}

	public void pageChange(final BPMNDiagram bpmnDiagram) {
		setBpmnDiagram(bpmnDiagram);
		reloadTabs();
		tabFolder.setSelection(0);
		tabFolder.getItem(0).getControl().setVisible(true);
		tabFolder.getItem(0).setData(bpmnDiagram);
		updatePalette();
	}

	public void selectBpmnDiagram(BPMNDiagram bpmnDiagram) {
		Diagram diagram = DIUtils.findDiagram(DesignEditor.this.getDiagramBehavior(), bpmnDiagram);
		if (diagram != null) {
			selectPictogramElements(new PictogramElement[] { (PictogramElement) diagram });
			getDiagramBehavior().refreshContent();
		}
	}

	public void showDesignPage(final BPMNDiagram bpmnDiagram) {
		CTabItem current = tabFolder.getSelection();
		if (current != null && current.getData() == bpmnDiagram) {
			current.getControl().setVisible(true);
			return;
		}
		showDesignPageInternal(bpmnDiagram);
	}

	private void showDesignPageInternal(BPMNDiagram bpmnDiagram) {
		for (CTabItem item : tabFolder.getItems()) {
			if (item.getData() == bpmnDiagram) {
				setBpmnDiagram(bpmnDiagram);
				tabFolder.setSelection(item);
				item.getControl().setVisible(true);
			}
		}
	}

	protected void addDesignPage(final BPMNDiagram bpmnDiagram) {
		setBpmnDiagram((BPMNDiagram) tabFolder.getItem(0).getData());
		reloadTabs();
		showDesignPage(bpmnDiagram);
	}

	protected void removeDesignPage(final BPMNDiagram bpmnDiagram) {
		CTabItem currentItem = tabFolder.getSelection();
		BPMNDiagram currentBpmnDiagram = (BPMNDiagram) currentItem.getData();
		setBpmnDiagram((BPMNDiagram) tabFolder.getItem(0).getData());
		reloadTabs();
		showDesignPage(currentBpmnDiagram);
	}

	private void reloadTabs() {
		List<BPMNDiagram> bpmnDiagrams = new ArrayList<BPMNDiagram>();
		BaseElement bpmnElement = bpmnDiagram.getPlane().getBpmnElement();

		getSubDiagrams(bpmnElement, bpmnDiagrams);

		tabFolder.setLayoutDeferred(true);
		for (int i = tabFolder.getItemCount() - 1; i > 0; --i) {
			tabFolder.getItem(i).setControl(null);
			tabFolder.getItem(i).dispose();
		}

		if (bpmnDiagrams.size() > 0) {
			for (BPMNDiagram bd : bpmnDiagrams) {
				CTabItem item = new CTabItem(tabFolder, SWT.NONE);
				item.setControl(container);
				BaseElement be = bd.getPlane().getBpmnElement();
				item.setText(ModelUtil.getDisplayName(be));
				item.setData(bd);
			}

		}
		tabFolder.setLayoutDeferred(false);

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				updateTabs();
			}
		});
	}

	private void getSubDiagrams(BaseElement bpmnElement, List<BPMNDiagram> bpmnDiagrams) {

		List<FlowElement> flowElements = null;
		if (bpmnElement instanceof FlowElementsContainer) {
			flowElements = ((FlowElementsContainer) bpmnElement).getFlowElements();
		} else if (bpmnElement instanceof Collaboration) {
			flowElements = new ArrayList<FlowElement>();
			for (Participant p : ((Collaboration) bpmnElement).getParticipants()) {
				if (p.getProcessRef() != null) {
					flowElements.addAll(p.getProcessRef().getFlowElements());
				}
			}
		} else if (bpmnElement instanceof Choreography) {
			flowElements = ((Choreography) bpmnElement).getFlowElements();
		}

		if (flowElements != null) {
			BPMNDiagram mainBpmnDiagram = ModelUtil.getDefinitions(bpmnResource).getDiagrams().get(0);
			BPMNDiagram activeBpmnDiagram = getBpmnDiagram();
			for (FlowElement fe : flowElements) {
				BPMNDiagram bd = DIUtils.findBPMNDiagram(fe);
				if (bd != null && !bpmnDiagrams.contains(bd) && bd != activeBpmnDiagram && bd != mainBpmnDiagram)
					bpmnDiagrams.add(bd);
				getSubDiagrams(fe, bpmnDiagrams);
			}
		}
	}

	public void createPartControl(Composite parent) {
		if (getGraphicalViewer() == null) {
			tabFolder = new CTabFolder(parent, SWT.BOTTOM);
			tabFolder.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					int pageIndex = tabFolder.indexOf((CTabItem) e.item);
					CTabItem item = tabFolder.getItem(pageIndex);
					BPMNDiagram bpmnDiagram = (BPMNDiagram) item.getData();
					showDesignPageInternal(bpmnDiagram);
					selectBpmnDiagram(bpmnDiagram);
				}
			});
			tabFolder.addTraverseListener(new TraverseListener() {
				// see https://bugs.eclipse.org/bugs/show_bug.cgi?id=199499 :
				// Switching tabs by Ctrl+PageUp/PageDown must not be caught on
				// the inner tab set
				public void keyTraversed(TraverseEvent e) {
					switch (e.detail) {
					case SWT.TRAVERSE_PAGE_NEXT:
					case SWT.TRAVERSE_PAGE_PREVIOUS:
						int detail = e.detail;
						e.doit = true;
						e.detail = SWT.TRAVERSE_NONE;
						Control control = tabFolder.getParent();
						control.traverse(detail, new Event());
					}
				}
			});
			defaultTabHeight = tabFolder.getTabHeight();

			container = new Composite(tabFolder, SWT.NONE);
			container.setLayout(new FillLayout());
			CTabItem item = new CTabItem(tabFolder, SWT.NONE, 0);
			item.setText(Messages.DesignEditor_Diagram_Tab);
			item.setControl(container);
			item.setData(getBpmnDiagram());

			super.createPartControl(container);

			// create additional editor tabs for BPMNDiagrams in the parent
			// MultiPageEditor
			final List<BPMNDiagram> bpmnDiagrams = getModelHandler().getAll(BPMNDiagram.class);
			for (int i = 1; i < bpmnDiagrams.size(); ++i) {
				BPMNDiagram bpmnDiagram = bpmnDiagrams.get(i);
				if (bpmnDiagram.getPlane().getBpmnElement() instanceof RootElement)
					multipageEditor.addDesignPage(bpmnDiagram);
			}
		}
	}

	public void updateTabs() {
		// if (!tabFolder.isLayoutDeferred()) {
		if (tabFolder.getItemCount() == 1) {
			tabFolder.setTabHeight(0);
		} else
			tabFolder.setTabHeight(defaultTabHeight);
		// }
		tabFolder.layout();
	}

	@Override
	protected void createActions() {
		super.createActions();
		ActionRegistry registry = getActionRegistry();
		IAction action = new WorkbenchPartAction(multipageEditor.getDesignEditor()) {

			@Override
			protected void init() {
				super.init();
				setId("show.or.hide.source.view"); //$NON-NLS-1$
			}

			@Override
			public String getText() {
				// return multipageEditor.getSourceViewer() == null ?
				// Messages.DesignEditor_Show_Source_View_Action :
				// Messages.DesignEditor_Hide_Source_View_Action;
				return multipageEditor.getSourceViewer() == null ? "显示源码" : "隐藏源码";
			}

			@Override
			protected boolean calculateEnabled() {
				return true;
			}

			public void run() {
				if (multipageEditor.getSourceViewer() == null) {
					multipageEditor.createSourceViewer();
				} else {
					multipageEditor.removeSourceViewer();
				}
			}
		};
		registry.registerAction(action);

		action = new WorkbenchPartAction(multipageEditor.getDesignEditor()) {

			@Override
			protected void init() {
				super.init();
				setId("delete.page"); //$NON-NLS-1$
			}

			@Override
			public String getText() {
				int pageIndex = multipageEditor.getActivePage();
				return NLS.bind(Messages.DesignEditor_Delete_Diagram_Action, multipageEditor.getTabItem(pageIndex).getText());
			}

			@Override
			public boolean isEnabled() {
				return calculateEnabled();
			}

			@Override
			protected boolean calculateEnabled() {
				BPMNDiagram bpmnDiagram = getBpmnDiagram();
				BPMNPlane plane = bpmnDiagram.getPlane();
				BaseElement process = plane.getBpmnElement();
				List<Participant> participants = getModelHandler().getAll(Participant.class);
				for (Participant p : participants) {
					if (p.getProcessRef() == process)
						return false;
				}
				return true;
			}

			public void run() {
				int pageIndex = multipageEditor.getActivePage();
				boolean result = MessageDialog.openQuestion(getSite().getShell(), Messages.DesignEditor_Delete_Page_Title,
						NLS.bind(Messages.DesignEditor_DeletePage_Message, multipageEditor.getTabItem(pageIndex).getText()));
				if (result) {
					final BPMNDiagram bpmnDiagram = getBpmnDiagram();
					TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(bpmnDiagram);
					// removeDesignPage(bpmnDiagram);

					if (domain != null) {
						domain.getCommandStack().execute(new RecordingCommand(domain) {
							@Override
							protected void doExecute() {
								BPMNPlane plane = bpmnDiagram.getPlane();
								BaseElement process = plane.getBpmnElement();
								DIUtils.deleteDiagram(DesignEditor.this.getDiagramBehavior(), bpmnDiagram);
								EcoreUtil.delete(process);
							}
						});
					}
				}
			}
		};
		registry.registerAction(action);

		// 自己添加右键菜单
		action = new WorkbenchPartAction(multipageEditor.getDesignEditor()) {

			@Override
			protected void init() {
				super.init();
				setId("add.to.model"); //$NON-NLS-1$
			}

			@Override
			public String getText() {
				return "创建到模板...";
			}

			@Override
			protected boolean calculateEnabled() {
				return true;
			}

			public void run() {
				FixFlowCreateModelDialog fixFlowCreateModelDialog = new FixFlowCreateModelDialog(null);
				fixFlowCreateModelDialog.open();

				PictogramElement pictogramElement[] = getDiagramTypeProvider().getDiagramEditor().getSelectedPictogramElements();
				final HashSet<EObject> set = new HashSet<EObject>();
				for (PictogramElement pictogramElement2 : pictogramElement) {
					EObject eObject = BusinessObjectUtil.getBusinessObjectForPictogramElement(pictogramElement2);
					if (set.contains(eObject)) {
						continue;
					} else {
						set.add(eObject);
					}
				}

				String id = fixFlowCreateModelDialog.getMap().get("id").toString();
				String name = fixFlowCreateModelDialog.getMap().get("name").toString();
				String newFilePath = fixFlowCreateModelDialog.getMap().get("newFilePath").toString();

				ResourceSet resourceSet = new ResourceSetImpl();
				resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml", new XMIResourceFactoryImpl());

				Bpmn2Resource resource = (Bpmn2Resource) resourceSet.getResource(URI.createFileURI(newFilePath), true);

				DocumentRoot documentRoot = (DocumentRoot) resource.getContents().get(0);
				Definitions definitions = documentRoot.getDefinitions();
				final Process process = (Process) definitions.getRootElements().get(0);
				process.setId(id);
				process.setName(name);

				final Plane plane = definitions.getDiagrams().get(0).getPlane();

				TransactionalEditingDomain domain = getEditingDomain();
				domain.getCommandStack().execute(new RecordingCommand(domain) {

					@Override
					protected void doExecute() {
						for (Object object : set.toArray()) {
							if (object instanceof FlowNode) {
								FlowNode flowNode = (FlowNode) object;
								DiagramElement diagramElement = DIUtils.findBPMNShape(flowNode);
								DiagramElement newDiagramElement = EcoreUtil.copy(diagramElement);
								if (diagramElement != null) {
									plane.getPlaneElement().add(newDiagramElement);
								}
								FlowNode newFlowNode = EcoreUtil.copy(flowNode);
								process.getFlowElements().add(newFlowNode);
							}
							if (object instanceof SequenceFlow) {
								SequenceFlow sequenceFlow = (SequenceFlow) object;
								BPMNEdge bpmnEdge = DIUtils.findBPMNEdge(sequenceFlow);
								BPMNEdge newDpmnEdge = EcoreUtil.copy(bpmnEdge);

								FlowNode sourceFlowNode = sequenceFlow.getSourceRef();
								FlowNode targetFlowNode = sequenceFlow.getTargetRef();

								if (newDpmnEdge != null && set.contains(sourceFlowNode) && set.contains(targetFlowNode)) {
									plane.getPlaneElement().add(newDpmnEdge);

									SequenceFlow newSequenceFlow = EcoreUtil.copy(sequenceFlow);
									if (set.contains(sourceFlowNode) && set.contains(targetFlowNode)) {
										FlowNode newSourceFlowNode = EcoreUtil.copy(sourceFlowNode);
										FlowNode newTargetFlowNode = EcoreUtil.copy(targetFlowNode);
										newSequenceFlow.setSourceRef(newSourceFlowNode);
										newSequenceFlow.setTargetRef(newTargetFlowNode);
									}
									process.getFlowElements().add(newSequenceFlow);
								}
							}
						}
					}
				});

				try {
					resource.save(null);
				} catch (IOException e) {
					e.printStackTrace();
				}

				ProjectUtil.refreshProject("fixflow-repository");
			}
		};

		registry.registerAction(action);

		action = new WorkbenchPartAction(multipageEditor.getDesignEditor()) {

			@Override
			protected void init() {
				super.init();
				setId("paste.from.model"); //$NON-NLS-1$
			}

			@Override
			public String getText() {
				return "从模板中贴回";
			}

			@Override
			protected boolean calculateEnabled() {
				return true;
			}

			public void run() {
				if (TemplateUtil.getModelId() == null) {
					MessageDialog.openInformation(null, "提示", "请先在模板视图中复制模板");
				}
				Definitions templatedefinitions = TemplateUtil.pasteNodeTemplate();

				final Process templateProcess = (Process) templatedefinitions.getRootElements().get(0);
				
				final Plane templatePlane = ((BPMNDiagram)templatedefinitions.getDiagrams().get(0)).getPlane();
				
				final Diagram diagram = getDiagramTypeProvider().getFeatureProvider().getDiagramTypeProvider().getDiagram();

				DocumentRoot documentRoot = (DocumentRoot) getResource().getContents().get(0);
				Definitions definitions = documentRoot.getDefinitions();
				final Plane plane = definitions.getDiagrams().get(0).getPlane();
				
				final Process process = (Process) definitions.getRootElements().get(0);
				
				TransactionalEditingDomain domain = getEditingDomain();
				domain.getCommandStack().execute(new RecordingCommand(domain) {

					@Override
					protected void doExecute() {
						process.getFlowElements().addAll(EcoreUtil.copyAll(templateProcess.getFlowElements()));
						plane.getPlaneElement().addAll(EcoreUtil.copyAll(templatePlane.getPlaneElement()));
						
						for (FlowElement flowElement : templateProcess.getFlowElements()) {
							if (flowElement instanceof FlowNode) {
								BPMNShape bpmnShape = DIUtils.findBPMNShape(flowElement);
								AddContext ac = new AddContext(new AreaContext(), flowElement);
								ac.setTargetContainer(diagram);
								ac.setNewObject(flowElement);
								if (null != bpmnShape) {
									float x1 = bpmnShape.getBounds().getX();
									float y1 = bpmnShape.getBounds().getY();
									int x = (int) x1;
									int y = (int) y1;
									ac.setLocation(x, y);
								}
								IAddFeature af = getDiagramTypeProvider().getFeatureProvider().getAddFeature(ac);
								af.add(ac);
							}
							if (flowElement instanceof SequenceFlow) {
								BPMNEdge bpmnEdge = DIUtils.findBPMNEdge(flowElement);
								BPMNDiagram bpmnDiagram = (BPMNDiagram) bpmnEdge.eContainer().eContainer();
								Anchor startAnchor = null;
								Anchor endAnchor = null;
								List<Connection> connections = diagram.getConnections();
								for (Connection connection : connections) {
									SequenceFlow sequenceFlow = (SequenceFlow) BusinessObjectUtil.getBusinessObjectForPictogramElement(connection);
									if(connection!=null && flowElement.getId().equals(sequenceFlow.getId())) {
										startAnchor = connection.getStart();
										endAnchor = connection.getEnd();
									}
								}
								if (null != bpmnEdge) {
									AddConnectionContext ac = new AddConnectionContext(startAnchor, endAnchor);
									ac.setTargetContainer(diagram);
									ac.setNewObject(flowElement);

									IAddFeature af = getDiagramTypeProvider().getFeatureProvider().getAddFeature(ac);
									af.add(ac);
								}
							}

						}
					}
				});
			}
		};

		registry.registerAction(action);
	}

	public class AddRemoveDiagramListener implements ResourceSetListener {
		@Override
		public NotificationFilter getFilter() {
			return null;
		}

		@Override
		public Command transactionAboutToCommit(ResourceSetChangeEvent event) throws RollbackException {
			return null;
		}

		@Override
		public void resourceSetChanged(ResourceSetChangeEvent event) {
			boolean debug = Activator.getDefault().isDebugging();

			for (Notification n : event.getNotifications()) {
				int et = n.getEventType();
				Object notifier = n.getNotifier();
				Object newValue = n.getNewValue();
				Object oldValue = n.getOldValue();
				Object feature = n.getFeature();

				if (debug) {
					if (et == Notification.ADD || et == Notification.REMOVE || et == Notification.SET) {
						System.out.print("event: " + et + "\t"); //$NON-NLS-1$ //$NON-NLS-2$
						if (notifier instanceof EObject) {
							System.out.print("notifier: $" + ((EObject) notifier).eClass().getName()); //$NON-NLS-1$
						} else
							System.out.print("notifier: " + notifier); //$NON-NLS-1$
					}
				}

				if (et == Notification.ADD) {
					if (debug) {
						if (newValue instanceof EObject) {
							System.out.println("\t\tvalue:    " + ((EObject) newValue).eClass().getName()); //$NON-NLS-1$
						} else
							System.out.println("\t\tvalue:    " + newValue); //$NON-NLS-1$
					}

					if (notifier instanceof Definitions && newValue instanceof BPMNDiagram && feature == Bpmn2Package.eINSTANCE.getDefinitions_Diagrams()) {
						final BPMNDiagram bpmnDiagram = (BPMNDiagram) newValue;
						BaseElement bpmnElement = bpmnDiagram.getPlane().getBpmnElement();
						if (bpmnElement instanceof RootElement)
							multipageEditor.addDesignPage(bpmnDiagram);
						else
							addDesignPage(bpmnDiagram);
						break;
					}
				} else if (et == Notification.REMOVE) {
					if (debug) {
						if (oldValue instanceof EObject) {
							System.out.println("\t\tvalue:    " + ((EObject) oldValue).eClass().getName()); //$NON-NLS-1$
						} else
							System.out.println("\t\tvalue:    " + oldValue); //$NON-NLS-1$
					}

					if (notifier instanceof Definitions && oldValue instanceof BPMNDiagram && feature == Bpmn2Package.eINSTANCE.getDefinitions_Diagrams()) {
						final BPMNDiagram bpmnDiagram = (BPMNDiagram) oldValue;
						BaseElement bpmnElement = bpmnDiagram.getPlane().getBpmnElement();
						if (bpmnElement instanceof RootElement)
							multipageEditor.removeDesignPage(bpmnDiagram);
						else
							removeDesignPage(bpmnDiagram);
						break;
					}
				} else if (et == Notification.SET) {
					// check if we need to change the tab names
					if (n.getFeature() instanceof EStructuralFeature && ((EStructuralFeature) n.getFeature()).getName().equals("name")) { //$NON-NLS-1$
						for (int i = 1; i < tabFolder.getItemCount(); ++i) {
							CTabItem item = tabFolder.getItem(i);
							BPMNDiagram bpmnDiagram = (BPMNDiagram) item.getData();
							if (bpmnDiagram != null) {
								if (bpmnDiagram == notifier || bpmnDiagram.getPlane().getBpmnElement() == notifier) {
									String text = n.getNewStringValue();
									if (text == null || text.isEmpty())
										text = "Unnamed"; //$NON-NLS-1$
									item.setText(text);
								}
							}
						}
						for (int i = 0; i < multipageEditor.getPageCount(); ++i) {
							BPMNDiagram bpmnDiagram = multipageEditor.getBpmnDiagram(i);
							if (bpmnDiagram == notifier) {
								CTabItem item = multipageEditor.getTabItem(i);
								String text = n.getNewStringValue();
								if (text == null || text.isEmpty())
									text = "Unnamed"; //$NON-NLS-1$
								item.setText(text);
							}
						}
					}
				}
			}
		}

		@Override
		public boolean isAggregatePrecommitListener() {
			return false;
		}

		@Override
		public boolean isPrecommitOnly() {
			return false;
		}

		@Override
		public boolean isPostcommitOnly() {
			return true;
		}
	}
}

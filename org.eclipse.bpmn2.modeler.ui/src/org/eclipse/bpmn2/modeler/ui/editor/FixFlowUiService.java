/*package org.eclipse.bpmn2.modeler.ui.editor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.graphiti.internal.util.T;
import org.eclipse.graphiti.ui.internal.editor.GFFigureCanvas;
import org.eclipse.graphiti.ui.internal.services.GraphitiUiInternal;
import org.eclipse.graphiti.ui.internal.services.impl.UiService;
import org.eclipse.graphiti.ui.internal.util.ui.print.DefaultPrintPreferences;
import org.eclipse.graphiti.ui.internal.util.ui.print.ExportDiagramDialog;
import org.eclipse.graphiti.ui.internal.util.ui.print.IDiagramsExporter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class FixFlowUiService extends UiService {
	private IFigure _allFigure;
	private Image _imageAll;

	private IRunnableWithProgress getExportOp(final String METHOD, final Shell shell, final IFigure _allFigure, final String file, final Image im, final IDiagramsExporter exporter) {
		IRunnableWithProgress operation;
		operation = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) {
				try {
					exporter.export(im, _allFigure, file, new DefaultPrintPreferences().getDoublePreference(DefaultPrintPreferences.SCALE_FACTOR));
				} catch (Exception e) {
					handleException(shell, e);
				}
			}

		};
		return operation;
	}

	private void handleException(final Shell shell, Exception e) {
		String message = "Can not export diagram: "; //$NON-NLS-1$
		MessageDialog.openError(shell, "Can not export diagram", message + e.getMessage()); //$NON-NLS-1$
		e.printStackTrace();
	}

	*//**
	 * Returns an IRunnableWithProgress, which saves the given contents to a
	 * File with the given filename.
	 * 
	 * @param shell
	 * 
	 * @param filename
	 *            The name of the file, where to save the contents.
	 * @param contents
	 *            The contents to save into the file.
	 * @throws Exception
	 *             On any errors that occur.
	 *//*
	private IRunnableWithProgress getSaveToFileOp(final Shell shell, final String filename, final byte contents[]) throws Exception {
		IRunnableWithProgress operation = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) {

				
				
				FileOutputStream outputStream = null;
				try {
					outputStream = new FileOutputStream(filename);
					outputStream.write(contents);
				} catch (Exception e) {
					handleException(shell, e);
				} finally {
					try {
						outputStream.close();
					} catch (Exception x) {
						T.racer().error("close output stream failed", x); //$NON-NLS-1$
					}
				}
			}
		};

		return operation;
	}

	private void initImageAll(double upperBoundPixels, GraphicalViewer graphicalViewer) {
		{
			int width = _allFigure.getBounds().width;
			int height = _allFigure.getBounds().height;

			// check whether the dimensions of the image to be created would
			// be small enough to prevent runtime exceptions
			if (width <= upperBoundPixels && height <= upperBoundPixels) {
				_imageAll = new Image(Display.getDefault(), width, height);
				GC gc = new GC(_imageAll);
				SWTGraphics graphics = new SWTGraphics(gc);

				 move all figures into the positive region 
				EditPart contents = graphicalViewer.getContents();
				if (contents instanceof GraphicalEditPart) {
					IFigure contentsFigure = ((GraphicalEditPart) contents).getFigure();
					Rectangle contentBounds = contentsFigure.getBounds();
					graphics.translate(-contentBounds.x, -contentBounds.y);
				}

				_allFigure.paint(graphics);

				if (gc != null)
					gc.dispose();
				if (graphics != null)
					graphics.dispose();
			} else {
				_imageAll = null;
			}
		}
	}
	


	public byte[] startSaveAsImageDialog(GraphicalViewer graphicalViewer, String language , String processKey,String shortfilename) {
		final String METHOD = "startSaveAsImageDialog(graphicalViewer)"; //$NON-NLS-1$

		// check extension point for exporters
		Map<String, Boolean> diagramExporterTypes = new HashMap<String, Boolean>();
		diagramExporterTypes.put("PNG", false);

		// configure dialog with exporters and open dialog
		final Shell shell = GraphitiUiInternal.getWorkbenchService().getShell();
		final ExportDiagramDialog saveAsImageDialog = new ExportDiagramDialog(shell, graphicalViewer);
		saveAsImageDialog.addExporters(diagramExporterTypes);
		
		org.eclipse.swt.widgets.Control control = graphicalViewer.getControl();
		if (control instanceof GFFigureCanvas) {
			GFFigureCanvas canvas = (GFFigureCanvas) control;
			canvas.regainSpace();
		}

		EditPart rootEditPart = graphicalViewer.getRootEditPart();
		if (!(rootEditPart instanceof GraphicalEditPart))
			return null;

		// determine _allFigure
		GraphicalEditPart graphicalRootEditPart = (GraphicalEditPart) rootEditPart;
		IFigure rootFigure = ((LayerManager) graphicalRootEditPart).getLayer(LayerConstants.PRINTABLE_LAYERS);
		if (rootFigure == null)
			return null;

		_allFigure = rootFigure;
		
		initImageAll(10000.0d, graphicalViewer);

		String filePath = ResourcesPlugin.getWorkspace().getRoot().getProject("fixflow-expand").getLocation().toString() + "/fixflowdiagram/"+language+"/"+processKey+"/";
		
		
		String filename = filePath + shortfilename + ".PNG";
	
		byte image[] = null;
		
		File file = new File(filePath);
		
		if(!file.exists())
			file.mkdirs();
		
		
		if (filename != null) {
			try {
				// add extension to filename (if none exists)
				IPath path = new Path(filename);
				if (path.getFileExtension() == null)
					filename = filename + "." + "PNG"; //$NON-NLS-1$

		
				final Image im = _imageAll;
				String imageExtension = saveAsImageDialog.getFileExtension();
				IRunnableWithProgress operation;
				// if the exporter is non-standard, i.e. registered via
				// extension point, we need to call the registered exporter.
//				if (diagramExporterTypes.containsKey(imageExtension)) {
					final IDiagramsExporter exporter = ExtensionManager.getSingleton().getDiagramExporterForType("PNG");
					Assert.isNotNull(exporter);
					operation = getExportOp(METHOD, shell, _allFigure, file, im, exporter);
					new ProgressMonitorDialog(shell).run(false, false, operation);

				int imageFormat = saveAsImageDialog.getImageFormat();
				image = createImage(im, 5);
				operation = getSaveToFileOp(shell, filename, image);
				
				new ProgressMonitorDialog(shell).run(false, false, operation);
				
				
				try  
		        {   
		            File f = new File(filename);   
		            f.canRead();   
		            f.canWrite();   
		            BufferedImage src = ImageIO.read(f);   
		            ImageIO.write(src, "PNG", new File(filename));   
		        }   
		        catch (Exception e)   
		        {   
		            e.printStackTrace();   
		        }   
				
			} catch (Exception e) {
				String message = "Cannot save image: "; //$NON-NLS-1$
				MessageDialog.openError(shell, "Cannot save image", message + e.getMessage()); //$NON-NLS-1$
				T.racer()
						.error(METHOD, message + "\nDetails: " + GraphitiUiInternal.getTraceService().getStacktrace(e)); //$NON-NLS-1$
				e.printStackTrace();
			}
		}
		return image;
	}

	@Override
	public byte[] createImage(Image image, int format) throws Exception {
		return super.createImage(image, format);
	}

	@Override
	public ImageData create8BitIndexedPaletteImage(Image image) throws Exception {
		return super.create8BitIndexedPaletteImage(image);
	}

	@Override
	public void startSaveAsImageDialog(GraphicalViewer graphicalViewer) {
		super.startSaveAsImageDialog(graphicalViewer);
	}
}
*/
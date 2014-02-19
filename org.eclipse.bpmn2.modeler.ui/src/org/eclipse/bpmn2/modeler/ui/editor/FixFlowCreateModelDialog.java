package org.eclipse.bpmn2.modeler.ui.editor;

import java.util.HashMap;
import java.util.List;

import org.eclipse.bpmn2.FlowElement;
import org.eclipse.dd.di.DiagramElement;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.fixflow.designer.base.util.FixFlowConfigUtil;
import org.fixflow.designer.base.util.TemplateUtil;

public class FixFlowCreateModelDialog extends TitleAreaDialog {
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private HashMap<String, Object> map;

	/**
	 * Create the dialog.
	 * @param parentShell
	 * @wbp.parser.constructor
	 */
	public FixFlowCreateModelDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.BORDER | SWT.CLOSE | SWT.RESIZE | SWT.PRIMARY_MODAL);
	}
	
	public FixFlowCreateModelDialog(Shell parentShell, List<FlowElement> flowElements, List<DiagramElement> diagramElements) {
		super(parentShell);
		setHelpAvailable(false);
		setShellStyle(SWT.BORDER | SWT.CLOSE | SWT.RESIZE | SWT.PRIMARY_MODAL);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("创建选中元素到模板");
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		GridLayout gl_container = new GridLayout(2, false);
		gl_container.verticalSpacing = 10;
		gl_container.marginRight = 15;
		gl_container.marginLeft = 15;
		gl_container.marginHeight = 15;
		container.setLayout(gl_container);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label idLabel = new Label(container, SWT.NONE);
		idLabel.setText("编号");
		
		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText("名称");
		
		text_1 = new Text(container, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label imageLabel = new Label(container, SWT.NONE);
		imageLabel.setText("图片地址");
		
		text_2 = new Text(container, SWT.BORDER);
		text_2.setText(FixFlowConfigUtil.getResourcePath("fixflow-repository-node-template"));
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label descriptionLabel = new Label(container, SWT.NONE);
		descriptionLabel.setText("描述");
		
		text_3 = new Text(container, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		GridData gd_text_3 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text_3.heightHint = 60;
		text_3.setLayoutData(gd_text_3);

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		button.setText("确定");
		Button button_1 = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		button_1.setText("取消");
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 370);
	}

	@Override
	protected void okPressed() {
		HashMap<String, Object> map = TemplateUtil.createNodeTemplate(text.getText(), text_1.getText(), text_3.getText(), text_2.getText());
		setMap(map);
		super.okPressed();
	}

	public HashMap<String, Object> getMap() {
		return map;
	}

	public void setMap(HashMap<String, Object> map) {
		this.map = map;
	}

}

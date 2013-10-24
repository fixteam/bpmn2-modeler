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

package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.customeditor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.drools.process.core.ParameterDefinition;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.drools.process.core.Work;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.drools.process.core.WorkDefinition;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.drools.process.core.WorkEditor;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.drools.process.core.datatype.DataType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.drools.process.core.impl.WorkImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Custom Work editor that can handle work definitions that only have
 * String parameters.
 */
public class SampleCustomEditor extends EditBeanDialog<Work> implements WorkEditor {

    private WorkDefinition workDefinition;
    private Map<String, Text> texts = new HashMap<String, Text>();
    
    public SampleCustomEditor(Shell parentShell) {
        super(parentShell, "Custom Work Editor"); //$NON-NLS-1$
        setBlockOnOpen(true);
    }
    
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        composite.setLayout(gridLayout);
        
        Work work = (Work) getValue();
        
        Label nameLabel = new Label(composite, SWT.NONE);
        nameLabel.setText("Name: "); //$NON-NLS-1$
        nameLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
        
        Text nameText = new Text(composite, SWT.NONE);
        nameText.setEditable(false);
        nameText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
        String name = work.getName();
        nameText.setText(name == null ? "" : name); //$NON-NLS-1$
        
        Set<ParameterDefinition> parameters = workDefinition.getParameters();
        for (ParameterDefinition param: parameters) {
            Label label = new Label(composite, SWT.NONE);
            label.setText(param.getName() + ": "); //$NON-NLS-1$
            label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
            
            Text text = new Text(composite, SWT.BORDER);
            text.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
            texts.put(param.getName(), text);
            Object value = work.getParameter(param.getName());
            text.setText(value == null ? "" : value.toString()); //$NON-NLS-1$
        }
        
        return composite;
    }
    
    protected Work updateValue(Work value) {
        Work work = new WorkImpl();
        work.setName(((Work) value).getName());
        for (Map.Entry<String, Text> entry: texts.entrySet()) {
            String text = entry.getValue().getText();
            ParameterDefinition pd = value.getParameterDefinition(entry.getKey());
            DataType type = pd.getType();
            try {
            	type.readValue(text);
            }
            catch (Exception e) {
            	throw new IllegalArgumentException(
            			"The value \""+text+"\" "+ //$NON-NLS-1$ //$NON-NLS-2$
            			"for parameter \""+entry.getKey()+"\" "+ //$NON-NLS-1$ //$NON-NLS-2$
            			"does not conform to the "+pd.getType().getStringType()+" "+ //$NON-NLS-1$ //$NON-NLS-2$
            			"data type."); //$NON-NLS-1$
            }
            work.setParameter(entry.getKey(), "".equals(text) ? null : text); //$NON-NLS-1$
        }
        work.setParameterDefinitions(((Work) value).getParameterDefinitions());
        return work;
    }
        
    public Work getWork() {
        return (Work) getValue();
    }

    public void setWork(Work work) {
        setValue(work);
    }

    public void setWorkDefinition(WorkDefinition workDefinition) {
        this.workDefinition = workDefinition;
    }

    public boolean show() {
        int result = open();
        return result == OK;
    }
}

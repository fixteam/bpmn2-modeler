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
package org.eclipse.bpmn2.modeler.ui;
import java.util.Enumeration;

import org.apache.xerces.parsers.SAXParser;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XNIException;
import org.eclipse.bpmn2.modeler.core.IBpmn2RuntimeExtension;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil.Bpmn2DiagramType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.xml.sax.InputSource;


public class DefaultBpmn2RuntimeExtension implements IBpmn2RuntimeExtension {

	private static final String targetNamespace = "http://sample.bpmn2.org/bpmn2/sample"; //$NON-NLS-1$
	private static final String[] typeLanguages = new String[] {
		"http://www.w3.org/2001/XMLSchema", "XML Schema", //$NON-NLS-1$ //$NON-NLS-2$
	};
	private static final String[] expressionLanguages = new String[] {
		"http://www.w3.org/1999/XPath", "XPath", //$NON-NLS-1$ //$NON-NLS-2$
	};
	
	public DefaultBpmn2RuntimeExtension() {
	}

	@Override
	public boolean isContentForRuntime(IEditorInput input) {
		return false;
	}

	@Override
	public String getTargetNamespace(Bpmn2DiagramType diagramType){
		String type = ""; //$NON-NLS-1$
		switch (diagramType) {
		case PROCESS:
			type = "/process"; //$NON-NLS-1$
			break;
		case COLLABORATION:
			type = "/collaboration"; //$NON-NLS-1$
			break;
		case CHOREOGRAPHY:
			type = "/choreography"; //$NON-NLS-1$
			break;
		}
		return targetNamespace + type;
	}

	@Override
	public String[] getTypeLanguages() {
		return typeLanguages;
	}

	@Override
	public String[] getExpressionLanguages() {
		return expressionLanguages;
	}

	@Override
	public void initialize(DiagramEditor editor) {
	}


	/**
	 * A simple XML parser that checks if the root element of an xml document contains any
	 * namespace definitions matching the given namespace URI.
	 * 
	 * @author bbrodt
	 */
	public static class RootElementParser extends SAXParser {
		private String namespace;
		private boolean result = false;
		
		/**
		 * @param namespace - the namespace URI to scan for.
		 */
		public RootElementParser(String namespace) {
			this.namespace = namespace;
		}
		
		public boolean getResult() {
			return result;
		}
		
		public void parse(InputSource source) {
			result = false;
			try {
				super.parse(source);
			} catch (AcceptedException e) {
				result = true;
			} catch (Exception e) {
			}
		}
		
		@Override
		public void startElement(QName qName, XMLAttributes attributes, Augmentations augmentations)
				throws XNIException {

			super.startElement(qName, attributes, augmentations);

			// search the "definitions" for a namespace that matches the required namespace
			if ("definitions".equals(qName.localpart)) { //$NON-NLS-1$
				String namespace = attributes.getValue("targetNamespace"); //$NON-NLS-1$
				if (this.namespace.equals(namespace))
					throw new AcceptedException(qName.localpart);
				Enumeration<?> e = fNamespaceContext.getAllPrefixes();
				while (e.hasMoreElements()) {
					String prefix = (String)e.nextElement();
					namespace = fNamespaceContext.getURI(prefix);
					if (this.namespace.equals(namespace))
						throw new AcceptedException(qName.localpart);
				}
				throw new RejectedException();
			} else {
				throw new RejectedException();
			}
		}
	}

	public static class AcceptedException extends RuntimeException {
		public String acceptedRootElement;

		public AcceptedException(String acceptedRootElement) {
			this.acceptedRootElement = acceptedRootElement;
		}

		private static final long serialVersionUID = 1L;
	}

	public static class RejectedException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
}

package org.eclipse.bpmn2.modeler.core.merrimac.clad;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.swt.widgets.Composite;

public interface IPropertiesCompositeFactory {
	AbstractDetailComposite createDetailComposite(Class eClass, AbstractBpmn2PropertySection section);
	AbstractDetailComposite createDetailComposite(Class eClass, Composite parent, int style);

	AbstractListComposite createListComposite(Class eClass, AbstractBpmn2PropertySection section);
	AbstractListComposite createListComposite(Class eClass, Composite parent, int style);
	
	AbstractDialogComposite createDialogComposite(EClass eClass, Composite parent, int style);
	
}

/**
 * Copyright (c) 2007, 2013 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Obeo - initial API and implementation
 * 
 */
package org.eclipse.sirius.diagram;

import org.eclipse.sirius.viewpoint.DNavigationLink;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>DDiagram Link</b></em>'. <!-- end-user-doc -->
 * 
 * <!-- begin-model-doc --> A link that references a ViewPoint. <!--
 * end-model-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.sirius.diagram.DDiagramLink#getTarget <em>Target</em>}
 * </li>
 * <li>{@link org.eclipse.sirius.diagram.DDiagramLink#getNode <em>Node</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.sirius.diagram.DiagramPackage#getDDiagramLink()
 * @model
 * @generated
 */
public interface DDiagramLink extends DNavigationLink {
    /**
     * Returns the value of the '<em><b>Target</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The
     * referenced ViewPoint. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Target</em>' reference.
     * @see #setTarget(DDiagram)
     * @see org.eclipse.sirius.diagram.DiagramPackage#getDDiagramLink_Target()
     * @model required="true"
     * @generated
     */
    DDiagram getTarget();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.diagram.DDiagramLink#getTarget <em>Target</em>}
     * ' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Target</em>' reference.
     * @see #getTarget()
     * @generated
     */
    void setTarget(DDiagram value);

    /**
     * Returns the value of the '<em><b>Node</b></em>' reference. <!--
     * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The
     * referenced node. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Node</em>' reference.
     * @see #setNode(EdgeTarget)
     * @see org.eclipse.sirius.diagram.DiagramPackage#getDDiagramLink_Node()
     * @model
     * @generated
     */
    EdgeTarget getNode();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.diagram.DDiagramLink#getNode <em>Node</em>}'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Node</em>' reference.
     * @see #getNode()
     * @generated
     */
    void setNode(EdgeTarget value);

} // DDiagramLink
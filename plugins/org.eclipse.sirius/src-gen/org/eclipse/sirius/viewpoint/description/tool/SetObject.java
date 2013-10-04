/*******************************************************************************
 * Copyright (c) 2007-2013 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.viewpoint.description.tool;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Set Object</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>
 * {@link org.eclipse.sirius.viewpoint.description.tool.SetObject#getFeatureName
 * <em>Feature Name</em>}</li>
 * <li>{@link org.eclipse.sirius.viewpoint.description.tool.SetObject#getObject
 * <em>Object</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.sirius.viewpoint.description.tool.ToolPackage#getSetObject()
 * @model
 * @generated
 */
public interface SetObject extends ContainerModelOperation {
    /**
     * Returns the value of the '<em><b>Feature Name</b></em>' attribute. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Feature Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc --> <!-- begin-model-doc --> The name of the feature to
     * set. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Feature Name</em>' attribute.
     * @see #setFeatureName(String)
     * @see org.eclipse.sirius.viewpoint.description.tool.ToolPackage#getSetObject_FeatureName()
     * @model dataType="org.eclipse.sirius.description.FeatureName"
     *        required="true"
     * @generated
     */
    String getFeatureName();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.viewpoint.description.tool.SetObject#getFeatureName
     * <em>Feature Name</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Feature Name</em>' attribute.
     * @see #getFeatureName()
     * @generated
     */
    void setFeatureName(String value);

    /**
     * Returns the value of the '<em><b>Object</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Object</em>' reference isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Object</em>' reference.
     * @see #setObject(EObject)
     * @see org.eclipse.sirius.viewpoint.description.tool.ToolPackage#getSetObject_Object()
     * @model
     * @generated
     */
    EObject getObject();

    /**
     * Sets the value of the '
     * {@link org.eclipse.sirius.viewpoint.description.tool.SetObject#getObject
     * <em>Object</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @param value
     *            the new value of the '<em>Object</em>' reference.
     * @see #getObject()
     * @generated
     */
    void setObject(EObject value);

} // SetObject
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
package org.eclipse.sirius.viewpoint;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>DValidable</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> Represents an element that can be validated. <!--
 * end-model-doc -->
 *
 *
 * @see org.eclipse.sirius.viewpoint.ViewpointPackage#getDValidable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface DValidable extends EObject {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * Validate the element. Return <code>false</code> if the element is not
     * valid. <!-- end-model-doc -->
     * 
     * @model
     * @generated
     */
    boolean validate();

} // DValidable

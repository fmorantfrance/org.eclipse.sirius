/*******************************************************************************
 * Copyright (c) 2010 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.tools.internal.actions.repair;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.eclipse.sirius.diagram.tools.api.migration.DiagramCrossReferencer;
import org.eclipse.sirius.viewpoint.AbstractDNode;
import org.eclipse.sirius.viewpoint.ArrangeConstraint;
import org.eclipse.sirius.viewpoint.description.DiagramElementMapping;

/**
 * Diagram element state for {@link AbstractDNode}.
 * 
 * @author dlecan
 * @param <D>
 *            Type of diagram element.
 */
public abstract class AbstractAbstractDNodeDiagramElementState<D extends AbstractDNode> extends AbstractDiagramElementState<D> {

    /**
     * Store the arrange constraints as pin status.
     */
    private List<ArrangeConstraint> arrangeConstraints;

    /**
     * Constructor.
     * 
     * @param id
     *            Identifier
     * @param crossReferencer
     *            the cross-referencer
     */
    public AbstractAbstractDNodeDiagramElementState(Identifier id, DiagramCrossReferencer crossReferencer) {
        super(id, crossReferencer);
        this.arrangeConstraints = Lists.newArrayList();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.tools.internal.actions.migration.state.AbstractDiagramElementState#storeElementState(org.eclipse.emf.ecore.EObject,
     *      org.eclipse.sirius.viewpoint.description.DiagramElementMapping,
     *      org.eclipse.sirius.viewpoint.DDiagramElement)
     */
    @Override
    public void storeElementState(EObject target, DiagramElementMapping mapping, D element) {
        super.storeElementState(target, mapping, element);

        Iterable<ArrangeConstraint> existingArrangeConstraints = Iterables.filter(element.getArrangeConstraints(), ArrangeConstraint.class);
        if (!Iterables.isEmpty(existingArrangeConstraints)) {
            Iterables.addAll(arrangeConstraints, existingArrangeConstraints);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.sirius.diagram.tools.internal.actions.migration.state.AbstractDiagramElementState#restoreElementState(org.eclipse.sirius.viewpoint.DDiagramElement)
     */
    @Override
    public void restoreElementState(D element) {
        super.restoreElementState(element);

        if (!arrangeConstraints.isEmpty()) {
            element.getArrangeConstraints().addAll(arrangeConstraints);
        }
    }
}
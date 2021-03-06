/*******************************************************************************
 * Copyright (c) 2012 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.ui.business.internal.bracket.locators;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.ui.internal.figures.ResizableLabelLocator;
import org.eclipse.sirius.diagram.ui.business.internal.bracket.BracketConnectionQuery;

/**
 * Bracket specific
 * {@link org.eclipse.gmf.runtime.diagram.ui.figures.LabelLocator} to have the
 * center label the middle of the bracket segment as reference point.
 * 
 * @author <a href="mailto:esteban.dugueperoux@obeo.fr">Esteban Dugueperoux</a>
 */
@SuppressWarnings("restriction")
public class BracketResizableLabelLocator extends ResizableLabelLocator {

    /**
     * Constructor for figure who are located and sized.
     * 
     * @param parent
     *            the parent figure
     * @param bounds
     *            the bounds
     * @param alignment
     *            the alignment
     */
    public BracketResizableLabelLocator(IFigure parent, Rectangle bounds, int alignment) {
        super(parent, bounds, alignment);
    }

    /**
     * Overridden to return the middle of the bracket segment as reference
     * point.
     * 
     * {@inheritDoc}
     */
    @Override
    protected Point getReferencePoint() {
        Point referencePoint = null;
        if (parent instanceof Connection) {
            PointList pointList = ((Connection) parent).getPoints();
            if (pointList.size() == 6) {
                Point originPoint = pointList.getPoint(BracketConnectionQuery.ORIGIN_POINT_INDEX);
                Point targetPoint = pointList.getPoint(BracketConnectionQuery.TARGET_POINT_INDEX);
                referencePoint = new Rectangle(originPoint, targetPoint).getCenter();
            } else {
                referencePoint = pointList.getMidpoint();
            }
        } else {
            referencePoint = parent.getBounds().getLocation();
        }
        return referencePoint;
    }
}

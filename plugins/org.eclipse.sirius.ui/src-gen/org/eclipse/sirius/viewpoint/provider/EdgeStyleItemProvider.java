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
package org.eclipse.sirius.viewpoint.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.viewpoint.EdgeStyle;
import org.eclipse.sirius.viewpoint.LineStyle;
import org.eclipse.sirius.viewpoint.ViewpointFactory;
import org.eclipse.sirius.viewpoint.ViewpointPackage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.sirius.viewpoint.EdgeStyle} object. <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class EdgeStyleItemProvider extends StyleItemProvider implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright = "Copyright (c) 2007, 2013 THALES GLOBAL SERVICES.\nAll rights reserved. This program and the accompanying materials\nare made available under the terms of the Eclipse Public License v1.0\nwhich accompanies this distribution, and is available at\nhttp://www.eclipse.org/legal/epl-v10.html\n\nContributors:\n   Obeo - initial API and implementation\n";

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public EdgeStyleItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            addLineStylePropertyDescriptor(object);
            addSourceArrowPropertyDescriptor(object);
            addTargetArrowPropertyDescriptor(object);
            addFoldingStylePropertyDescriptor(object);
            addSizePropertyDescriptor(object);
            addRoutingStylePropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Line Style feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addLineStylePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_EdgeStyle_lineStyle_feature"), getString("_UI_EdgeStyle_lineStyle_description"), ViewpointPackage.Literals.EDGE_STYLE__LINE_STYLE, true, false, false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, getString("_UI_GeneralPropertyCategory"), null));
    }

    /**
     * This adds a property descriptor for the Source Arrow feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addSourceArrowPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_EdgeStyle_sourceArrow_feature"), getString("_UI_PropertyDescriptor_description", "_UI_EdgeStyle_sourceArrow_feature", "_UI_EdgeStyle_type"),
                ViewpointPackage.Literals.EDGE_STYLE__SOURCE_ARROW, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Target Arrow feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addTargetArrowPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_EdgeStyle_targetArrow_feature"), getString("_UI_PropertyDescriptor_description", "_UI_EdgeStyle_targetArrow_feature", "_UI_EdgeStyle_type"),
                ViewpointPackage.Literals.EDGE_STYLE__TARGET_ARROW, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Folding Style feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addFoldingStylePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_EdgeStyle_foldingStyle_feature"), getString("_UI_PropertyDescriptor_description", "_UI_EdgeStyle_foldingStyle_feature", "_UI_EdgeStyle_type"),
                ViewpointPackage.Literals.EDGE_STYLE__FOLDING_STYLE, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Size feature. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addSizePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(), getString("_UI_EdgeStyle_size_feature"),
                getString("_UI_PropertyDescriptor_description", "_UI_EdgeStyle_size_feature", "_UI_EdgeStyle_type"), ViewpointPackage.Literals.EDGE_STYLE__SIZE, true, false, false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Routing Style feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addRoutingStylePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_EdgeStyle_routingStyle_feature"), getString("_UI_PropertyDescriptor_description", "_UI_EdgeStyle_routingStyle_feature", "_UI_EdgeStyle_type"),
                ViewpointPackage.Literals.EDGE_STYLE__ROUTING_STYLE, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to
     * deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand},
     * {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in
     * {@link #createCommand}. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(ViewpointPackage.Literals.EDGE_STYLE__STROKE_COLOR);
            childrenFeatures.add(ViewpointPackage.Literals.EDGE_STYLE__BEGIN_LABEL_STYLE);
            childrenFeatures.add(ViewpointPackage.Literals.EDGE_STYLE__CENTER_LABEL_STYLE);
            childrenFeatures.add(ViewpointPackage.Literals.EDGE_STYLE__END_LABEL_STYLE);
        }
        return childrenFeatures;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper
        // feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns EdgeStyle.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/EdgeStyle"));
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getText(Object object) {
        LineStyle labelValue = ((EdgeStyle) object).getLineStyle();
        String label = labelValue == null ? null : labelValue.toString();
        return label == null || label.length() == 0 ? getString("_UI_EdgeStyle_type") : getString("_UI_EdgeStyle_type") + " " + label;
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to
     * update any cached children and by creating a viewer notification, which
     * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(EdgeStyle.class)) {
        case ViewpointPackage.EDGE_STYLE__LINE_STYLE:
        case ViewpointPackage.EDGE_STYLE__SOURCE_ARROW:
        case ViewpointPackage.EDGE_STYLE__TARGET_ARROW:
        case ViewpointPackage.EDGE_STYLE__FOLDING_STYLE:
        case ViewpointPackage.EDGE_STYLE__SIZE:
        case ViewpointPackage.EDGE_STYLE__ROUTING_STYLE:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
            return;
        case ViewpointPackage.EDGE_STYLE__STROKE_COLOR:
        case ViewpointPackage.EDGE_STYLE__BEGIN_LABEL_STYLE:
        case ViewpointPackage.EDGE_STYLE__CENTER_LABEL_STYLE:
        case ViewpointPackage.EDGE_STYLE__END_LABEL_STYLE:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
            return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
     * describing the children that can be created under this object. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add(createChildParameter(ViewpointPackage.Literals.EDGE_STYLE__STROKE_COLOR, ViewpointFactory.eINSTANCE.createRGBValues()));

        newChildDescriptors.add(createChildParameter(ViewpointPackage.Literals.EDGE_STYLE__BEGIN_LABEL_STYLE, ViewpointFactory.eINSTANCE.createBeginLabelStyle()));

        newChildDescriptors.add(createChildParameter(ViewpointPackage.Literals.EDGE_STYLE__CENTER_LABEL_STYLE, ViewpointFactory.eINSTANCE.createCenterLabelStyle()));

        newChildDescriptors.add(createChildParameter(ViewpointPackage.Literals.EDGE_STYLE__END_LABEL_STYLE, ViewpointFactory.eINSTANCE.createEndLabelStyle()));
    }

}
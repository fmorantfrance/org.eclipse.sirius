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
package org.eclipse.sirius.diagram.description.tool.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.business.api.query.IdentifiedElementQuery;
import org.eclipse.sirius.common.tools.api.util.StringUtil;
import org.eclipse.sirius.diagram.description.tool.ReconnectEdgeDescription;
import org.eclipse.sirius.diagram.description.tool.ToolFactory;
import org.eclipse.sirius.diagram.description.tool.ToolPackage;
import org.eclipse.sirius.diagram.ui.provider.DiagramUIPlugin;
import org.eclipse.sirius.viewpoint.description.tool.provider.MappingBasedToolDescriptionItemProvider;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.sirius.diagram.description.tool.ReconnectEdgeDescription}
 * object. <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class ReconnectEdgeDescriptionItemProvider extends MappingBasedToolDescriptionItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ReconnectEdgeDescriptionItemProvider(AdapterFactory adapterFactory) {
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

            addReconnectionKindPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Reconnection Kind feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addReconnectionKindPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_ReconnectEdgeDescription_reconnectionKind_feature"),
                getString("_UI_PropertyDescriptor_description", "_UI_ReconnectEdgeDescription_reconnectionKind_feature", "_UI_ReconnectEdgeDescription_type"),
                ToolPackage.Literals.RECONNECT_EDGE_DESCRIPTION__RECONNECTION_KIND, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, getString("_UI_GeneralPropertyCategory"), null));
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
            childrenFeatures.add(ToolPackage.Literals.RECONNECT_EDGE_DESCRIPTION__SOURCE);
            childrenFeatures.add(ToolPackage.Literals.RECONNECT_EDGE_DESCRIPTION__TARGET);
            childrenFeatures.add(ToolPackage.Literals.RECONNECT_EDGE_DESCRIPTION__SOURCE_VIEW);
            childrenFeatures.add(ToolPackage.Literals.RECONNECT_EDGE_DESCRIPTION__TARGET_VIEW);
            childrenFeatures.add(ToolPackage.Literals.RECONNECT_EDGE_DESCRIPTION__ELEMENT);
            childrenFeatures.add(ToolPackage.Literals.RECONNECT_EDGE_DESCRIPTION__INITIAL_OPERATION);
            childrenFeatures.add(ToolPackage.Literals.RECONNECT_EDGE_DESCRIPTION__EDGE_VIEW);
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
     * This returns ReconnectEdgeDescription.gif. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/ReconnectEdgeDescription"));
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @not-generated
     */
    @Override
    public String getText(Object object) {
        String label = new IdentifiedElementQuery((ReconnectEdgeDescription) object).getLabel();
        return StringUtil.isEmpty(label) ? getString("_UI_ReconnectEdgeDescription_type") : getString("_UI_ReconnectEdgeDescription_type") + " " + label;
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

        switch (notification.getFeatureID(ReconnectEdgeDescription.class)) {
        case ToolPackage.RECONNECT_EDGE_DESCRIPTION__RECONNECTION_KIND:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
            return;
        case ToolPackage.RECONNECT_EDGE_DESCRIPTION__SOURCE:
        case ToolPackage.RECONNECT_EDGE_DESCRIPTION__TARGET:
        case ToolPackage.RECONNECT_EDGE_DESCRIPTION__SOURCE_VIEW:
        case ToolPackage.RECONNECT_EDGE_DESCRIPTION__TARGET_VIEW:
        case ToolPackage.RECONNECT_EDGE_DESCRIPTION__ELEMENT:
        case ToolPackage.RECONNECT_EDGE_DESCRIPTION__INITIAL_OPERATION:
        case ToolPackage.RECONNECT_EDGE_DESCRIPTION__EDGE_VIEW:
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

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.RECONNECT_EDGE_DESCRIPTION__SOURCE, ToolFactory.eINSTANCE.createSourceEdgeCreationVariable()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.RECONNECT_EDGE_DESCRIPTION__TARGET, ToolFactory.eINSTANCE.createTargetEdgeCreationVariable()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.RECONNECT_EDGE_DESCRIPTION__SOURCE_VIEW, ToolFactory.eINSTANCE.createSourceEdgeViewCreationVariable()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.RECONNECT_EDGE_DESCRIPTION__TARGET_VIEW, ToolFactory.eINSTANCE.createTargetEdgeViewCreationVariable()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.RECONNECT_EDGE_DESCRIPTION__ELEMENT,
                org.eclipse.sirius.viewpoint.description.tool.ToolFactory.eINSTANCE.createElementSelectVariable()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.RECONNECT_EDGE_DESCRIPTION__INITIAL_OPERATION,
                org.eclipse.sirius.viewpoint.description.tool.ToolFactory.eINSTANCE.createInitialOperation()));

        newChildDescriptors.add(createChildParameter(ToolPackage.Literals.RECONNECT_EDGE_DESCRIPTION__EDGE_VIEW,
                org.eclipse.sirius.viewpoint.description.tool.ToolFactory.eINSTANCE.createElementSelectVariable()));
    }

    /**
     * This returns the label text for
     * {@link org.eclipse.emf.edit.command.CreateChildCommand}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
        Object childFeature = feature;
        Object childObject = child;

        boolean qualify = childFeature == ToolPackage.Literals.RECONNECT_EDGE_DESCRIPTION__ELEMENT || childFeature == ToolPackage.Literals.RECONNECT_EDGE_DESCRIPTION__EDGE_VIEW;

        if (qualify) {
            return getString("_UI_CreateChild_text2", new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
        }
        return super.getCreateChildText(owner, feature, child, selection);
    }

    /**
     * Return the resource locator for this item provider's resources. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return DiagramUIPlugin.INSTANCE;
    }

}

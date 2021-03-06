/*******************************************************************************
 * Copyright (c) 2010, 2014 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.tests.swtbot.tree;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import org.eclipse.sirius.tests.swtbot.support.api.business.UILocalSession;
import org.eclipse.sirius.tests.swtbot.support.api.business.UIResource;
import org.eclipse.sirius.tests.swtbot.support.api.editor.SWTBotSiriusHelper;
import org.eclipse.sirius.tests.swtbot.support.api.editor.SWTBotVSMEditor;
import org.eclipse.sirius.tests.swtbot.support.utils.SWTBotUtils;
import org.eclipse.sirius.tree.DTree;
import org.eclipse.sirius.ui.tools.api.color.VisualBindingManager;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Test;

import org.eclipse.sirius.tests.swtbot.Activator;

/**
 * Test Style description item. Test automatic refresh and manual refresh. Test
 * undo/redo after each test type Test opening and closing model or editor to
 * verify that all changes are effective
 * 
 * @author jdupont
 */
public class TreeItemStyleDescriptionTest extends AbstractTreeSiriusSWTBotGefTestCase {

    /**
     * Sirius Specific Model.
     */
    private static final String VSM = "ecore.odesign";

    /**
     * 
     */
    private static final String ODESIGN = "platform:/resource/DesignerTestProject/" + VSM;

    /**
     * Test repository.
     */
    private static final String DATA_UNIT_DIR = "data/unit/tree/";

    /**
     * Session file.
     */
    private static final String SESSION_FILE = "tree.aird";

    /**
     * UML File.
     */
    private static final String ECORE_FILE = "tree.ecore";

    /**
     * File directory.
     */
    private static final String FILE_DIR = "/";

    /**
     * Sirius Group.
     */
    private static final String GROUP = "Ecore Editing Workbench V4.6";

    /**
     * Properties view tab color.
     */
    private static final String COLOR = "Color";

    /**
     * Properties view tab Label.
     */
    private static final String LABEL = "Label";

    /**
     * Representation name.
     */
    private static final String REPRESENTATION_NAME = "Tree";

    /**
     * Semantic model instance.
     */
    private static final String REPRESENTATION_INSTANCE_NAME = "new Tree";

    /**
     * Contextual menu undo.
     */
    private static final String UNDO = "Undo Refresh representation";

    /**
     * Contextual menu redo.
     */
    private static final String REDO = "Redo Refresh representation";

    /**
     * Constant value for 'new EClass 1'
     */
    private static final String NEWECLASS1 = "new EClass 1";

    /**
     * Current editor.
     */
    protected SWTBotEditor editor;

    /**
     * Session.
     */
    private UIResource sessionAirdResource;

    /**
     * Local Session.
     */
    private UILocalSession localSession;

    /**
     * Blue color value RGB.
     */
    private Color blue = VisualBindingManager.getDefault().getColorFromName(MAPCOLORVALUE.get(2));

    /**
     * Light green color valur RGB.
     */
    private Color lightGreen = VisualBindingManager.getDefault().getColorFromName(MAPCOLORVALUE.get(13));

    /**
     * The label color value.
     */
    private Color labelColor;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onSetUpBeforeClosingWelcomePage() throws Exception {
        copyFileToTestProject(Activator.PLUGIN_ID, DATA_UNIT_DIR, VSM, SESSION_FILE, ECORE_FILE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onSetUpAfterOpeningDesignerPerspective() throws Exception {
        sessionAirdResource = new UIResource(designerProject, FILE_DIR, SESSION_FILE);
        localSession = designerPerspective.openSessionFromFile(sessionAirdResource);

    }

    /**
     * Test automatic refresh when tree editor is open when modify odesign.
     */
    @Test
    public void testAutomaticRefreshWithEditorOpen() {
        disableAutoRefresh();
        enableAutoRefresh();
        disableRefreshOnOpeningRepresentation();
        enableRefreshOnOpeningRepresentation();

        // Open editor
        editor = openRepresentation(localSession.getOpenedSession(), REPRESENTATION_NAME, REPRESENTATION_INSTANCE_NAME, DTree.class);

        // the session is already open and opening odesign
        SWTBotVSMEditor odesignEditor = openViewpointSpecificationModel(VSM);

        modifyVSM(odesignEditor);

        bot.sleep(500);

        // Test editor refresh
        refreshEditorTest();

        VisualBindingManager.getDefault().getColorFromName(MAPCOLORVALUE.get(13));

        // Test undo and redo on refreh
        undoRedoRefresh();

        if (odesignEditor.isDirty()) {
            closeAndSaveViewpointSpecificationModel(VSM);
        } else {
            odesignEditor.close();
        }

    }

    /**
     * Test automatic refresh when session is open before modify odesign.
     */
    @Test
    public void testAutomaticRefreshWithSessionOpen() {
        disableAutoRefresh();
        enableAutoRefresh();
        disableRefreshOnOpeningRepresentation();
        enableRefreshOnOpeningRepresentation();

        // the session is already open and opening odesign
        SWTBotVSMEditor odesignEditor = openViewpointSpecificationModel(VSM);

        // Modify VSM
        modifyVSM(odesignEditor);

        // Open editor
        editor = openRepresentation(localSession.getOpenedSession(), REPRESENTATION_NAME, REPRESENTATION_INSTANCE_NAME, DTree.class);

        // Test editor refresh
        refreshEditorTest();

        // Test undo and redo on refreh
        undoRedoRefresh();

        // close session
        localSession.close(true);
    }

    /**
     * Test automatic refresh when session is close during modify odesign.
     */
    @Test
    public void testAutomaticRefreshWithSessionClose() {
        disableAutoRefresh();
        enableAutoRefresh();
        disableRefreshOnOpeningRepresentation();
        enableRefreshOnOpeningRepresentation();

        // the session is already open and opening odesign
        SWTBotVSMEditor odesignEditor = openViewpointSpecificationModel(VSM);

        // the session is close and the odesign is open
        if (localSession.isDirty()) {
            localSession.close(true);
        } else {
            localSession.closeNoDirty();
        }

        // Modify VSM
        modifyVSM(odesignEditor);

        // Wait for the refresh of the vsm is over.
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Open session
        localSession = designerPerspective.openSessionFromFile(sessionAirdResource);

        // Open editor
        editor = openRepresentation(localSession.getOpenedSession(), REPRESENTATION_NAME, REPRESENTATION_INSTANCE_NAME, DTree.class);

        // Test editor refresh
        refreshEditorTest();

        // Test undo and redo on refreh
        undoRedoRefresh();

        localSession.close(true);
    }

    /**
     * Test manual refresh.
     */
    @Test
    public void testStyleDescriptionManualRefresh() {
        disableAutoRefresh();
        disableRefreshOnOpeningRepresentation();

        // open Odesign
        SWTBotVSMEditor odesignEditor = openViewpointSpecificationModel(VSM);

        modifyVSM(odesignEditor);

        // Open editor
        editor = openRepresentation(localSession.getOpenedSession(), REPRESENTATION_NAME, REPRESENTATION_INSTANCE_NAME, DTree.class);

        editor.save();
        SWTBotUtils.waitProgressMonitorClose("Progress Information");

        TreeItem widgetNewEclass1 = null;

        if (editor.bot().tree().getTreeItem(NEWECLASS1).widget instanceof TreeItem) {
            widgetNewEclass1 = editor.bot().tree().getTreeItem(NEWECLASS1).widget;
        }

        assertNotNull("The tree item for the class is null", widgetNewEclass1);

        checkTreeItemStyle(widgetNewEclass1);

        // Manual refreh with click context menu
        editor.bot().tree().getTreeItem(NEWECLASS1).contextMenu(REFRESH_TREE).click();
        SWTBotUtils.waitProgressMonitorClose("Progress Information");

        refreshEditorTest();

        localSession.closeNoDirty();
    }

    /**
     * Undo and redo on refresh action.
     */
    private void undoRedoRefresh() {
        TreeItem widgetNewEclass1 = null;
        String treeItemName = null;

        if (editor.bot().tree().getAllItems().length > 0) {
            treeItemName = editor.bot().tree().getAllItems()[0].getText();
        }
        if (treeItemName != null) {
            widgetNewEclass1 = editor.bot().tree().getTreeItem(treeItemName).widget;
        }

        labelColor = getLabelColor(widgetNewEclass1);
        assertThat(labelColor, equalTo(blue));
        assertThat(getWidgetLabelColor(widgetNewEclass1), equalTo(blue));

        SWTBotUtils.waitAllUiEvents();

        // Undo
        bot.menu("Edit").menu(UNDO).click();
        SWTBotUtils.waitAllUiEvents();
        bot.waitUntil(new CheckTreeItemColor(widgetNewEclass1, lightGreen));
        labelColor = getLabelColor(widgetNewEclass1);
        assertThat(labelColor, equalTo(lightGreen));
        assertThat(getWidgetLabelColor(widgetNewEclass1), equalTo(lightGreen));

        // ReDo
        bot.menu("Edit").menu(REDO).click();
        SWTBotUtils.waitAllUiEvents();
        bot.waitUntil(new CheckTreeItemColor(widgetNewEclass1, blue));
        labelColor = getLabelColor(widgetNewEclass1);
        assertThat(labelColor, equalTo(blue));
        assertThat(getWidgetLabelColor(widgetNewEclass1), equalTo(blue));
    }

    /**
     * Check the tree item style.
     * 
     * @param widgetNewEclass1
     */
    private void checkTreeItemStyle(TreeItem widgetNewEclass1) {
        boolean showIcon = false;
        int labelSize = 0;
        String labelExpression = null;
        // String labelAlignment = null;
        String iconPath = null;
        String labelFormat = null;
        Color colorBackground = null;
        Color labelColor = null;

        // Retrieve show icon, label size, label expression, label alignment,
        // label format, color background, label color of 'new EClass 1'
        if (widgetNewEclass1 != null) {
            showIcon = isShowIcon(widgetNewEclass1);
            labelSize = getLabelSize(widgetNewEclass1);
            labelExpression = getWidgetLabelExpression(widgetNewEclass1);
            iconPath = getIconPath(widgetNewEclass1);
            // labelAlignment = getLabelAlignment(widgetNewEclass1);
            labelFormat = getLabelFormat(widgetNewEclass1);
            colorBackground = getLabelBackgroundColor(widgetNewEclass1);
            labelColor = getLabelColor(widgetNewEclass1);
        }

        Color lightYellow = VisualBindingManager.getDefault().getColorFromName(MAPCOLORVALUE.get(17));
        Color lightGreen = VisualBindingManager.getDefault().getColorFromName(MAPCOLORVALUE.get(13));

        assertThat(showIcon, equalTo(false));
        assertThat(labelSize, equalTo(8));
        assertThat(labelExpression, equalTo(NEWECLASS1));
        // assertThat(labelAlignment, equalTo("CENTER"));
        assertThat(labelFormat, equalTo("bold"));
        assertThat(iconPath, anyOf(equalTo(""), nullValue()));
        assertThat(colorBackground, equalTo(lightYellow));
        assertThat(labelColor, equalTo(lightGreen));

        assertThat(SWTBotUtils.getWidgetFormat(widgetNewEclass1).getName(), equalTo("bold"));
        assertThat(getWidgetBackgroundColor(widgetNewEclass1), equalTo(lightYellow));
        assertThat(getWidgetLabelColor(widgetNewEclass1), equalTo(lightGreen));
        assertNull(getWidgetImage(widgetNewEclass1));
        assertThat(getWidgetSize(widgetNewEclass1), equalTo(8));
    }

    /**
     * Modify all fields in properties view of tree item style description in
     * viewpoint specific model (.odesign).
     * 
     * @param odesignEditor
     *            the odesign editor.
     */
    private void modifyVSM(SWTBotVSMEditor odesignEditor) {
        // expand the tree : Tree Item Style Description 8
        SWTBotTree tree = odesignEditor.bot().tree();
        tree.expandNode(ODESIGN).expandNode(GROUP).expandNode("Design").expandNode("Tree").expandNode("Class").expandNode("feature:name").select();

        // accesses to property view
        bot.viewByTitle(PROPERTIES).setFocus();

        // accesses to tab Label
        SWTBotSiriusHelper.selectPropertyTabItem(LABEL);
        changeAndTestPropertyTabLabel();
        changeAndTestPropertyTabLabel(8, 12, "feature:name", "Test<%name%>", 0, 1);
        SWTBotUtils.waitAllUiEvents();
        // Save odesign
        saveViewpointSpecificationModel(VSM);
        SWTBotUtils.waitAllUiEvents();

        // accesses to tab Color
        SWTBotSiriusHelper.selectPropertyTabItem(COLOR);
        changeAndTestPropertyTabColor(17, 1, 13, 2);

        // Save odesign
        saveViewpointSpecificationModel(VSM);
        SWTBotUtils.waitAllUiEvents();
        SWTBotUtils.waitProgressMonitorClose("Progress Information");
    }

    /**
     * Verify that editor changes made in the odesign.
     */
    private void refreshEditorTest() {

        editor.show();

        editor.save();
        SWTBotUtils.waitProgressMonitorClose("Progress Information");

        TreeItem widgetNewEclass1 = null;
        String treeItemName = null;

        if (editor.bot().tree().getAllItems().length > 0) {
            treeItemName = editor.bot().tree().getAllItems()[0].getText();
        }
        if (treeItemName != null) {
            widgetNewEclass1 = editor.bot().tree().getTreeItem(treeItemName).widget;
        }

        boolean showIcon = false;
        int labelSize = 0;
        String labelExpression = null;
        // String labelAlignment = null;
        String iconPath = null;
        String labelFormat = null;
        Color colorBackground = null;
        Color labelColor = null;

        // Retrieve show icon, label size, label expression, label alignment,
        // label format, color background, label color of 'new EClass 1'
        if (widgetNewEclass1 != null) {
            showIcon = isShowIcon(widgetNewEclass1);
            iconPath = getIconPath(widgetNewEclass1);
            labelSize = getLabelSize(widgetNewEclass1);
            labelExpression = getWidgetLabelExpression(widgetNewEclass1);
            // labelAlignment = getLabelAlignment(widgetNewEclass1);
            labelFormat = getLabelFormat(widgetNewEclass1);
            colorBackground = getWidgetBackgroundColor(widgetNewEclass1);
            labelColor = getWidgetLabelColor(widgetNewEclass1);
        }

        Color black = VisualBindingManager.getDefault().getColorFromName(MAPCOLORVALUE.get(1));
        Color blue = VisualBindingManager.getDefault().getColorFromName(MAPCOLORVALUE.get(2));

        assertThat(showIcon, equalTo(true));
        assertThat(iconPath, anyOf(equalTo(""), nullValue()));
        assertThat(labelSize, equalTo(12));
        assertThat(labelExpression, equalTo("Testnew EClass 1"));
        // assertThat(labelAlignment, equalTo("RIGHT"));
        assertThat(labelFormat, equalTo("bold_italic"));
        assertThat(colorBackground, equalTo(black));
        assertThat(labelColor, equalTo(blue));
        assertThat(SWTBotUtils.getWidgetFormat(widgetNewEclass1).getName(), equalTo("bold_italic"));
        assertThat(getWidgetSize(widgetNewEclass1), equalTo(12));
        assertThat(getWidgetBackgroundColor(widgetNewEclass1), equalTo(black));
        assertThat(getWidgetLabelColor(widgetNewEclass1), equalTo(blue));
        assertNotNull(getWidgetImage(widgetNewEclass1));
        assertThat(getWidgetSize(widgetNewEclass1), equalTo(12));
    }

    /**
     * Retrieve, test, change and test all fields in General tab.
     */
    private void changeAndTestPropertyTabLabel() {
        // Retrieve show icon check
        assertFalse(bot.viewByTitle(PROPERTIES).bot().checkBox(2).isChecked());
        // Check show icon
        bot.viewByTitle(PROPERTIES).bot().checkBox(2).click();
        assertTrue(bot.viewByTitle(PROPERTIES).bot().checkBox(2).isChecked());
    }

    /**
     * Retrieve, test, change and test all fields in Label tab.
     * 
     * @param valueLabelSize
     *            index of spinner for label size
     * @param newValueLabelSize
     *            index of spinner for new label size
     * @param valueLabelExpression
     *            the value of label expression
     * @param newValueLabelExpression
     *            the value of new label expression
     * @param valueLabelAlignement
     *            the index of radio button for label alignment
     * @param newValueLabelAlignement
     *            the index of radio button for new label alignment
     * @param valueLabelFormat
     *            the index of radio button for label format
     * @param newValueLabelFormat
     *            the index of radio button for new label format
     */
    private void changeAndTestPropertyTabLabel(int valueLabelSize, int newValueLabelSize, String valueLabelExpression, String newValueLabelExpression, /*
                                                                                                                                                        * int
                                                                                                                                                        * valueLabelAlignement
                                                                                                                                                        * ,
                                                                                                                                                        * int
                                                                                                                                                        * newValueLabelAlignement
                                                                                                                                                        * ,
                                                                                                                                                        */int valueLabelFormat, int newValueLabelFormat) {
        // Retrieve label format
        assertTrue(bot.viewByTitle(PROPERTIES).bot().checkBox(valueLabelFormat).isChecked());
        // Change label format
        bot.viewByTitle(PROPERTIES).bot().checkBox(newValueLabelFormat).click();
        assertTrue(bot.viewByTitle(PROPERTIES).bot().checkBox(newValueLabelFormat).isChecked());
        // Retrieve label expression
        assertThat(bot.viewByTitle(PROPERTIES).bot().text(0).getText(), equalTo(valueLabelExpression));
        // Retrieve label size
        assertThat(bot.viewByTitle(PROPERTIES).bot().spinner(0).getSelection(), equalTo(valueLabelSize));
        // Change label size
        bot.viewByTitle(PROPERTIES).bot().spinner(0).setFocus();
        bot.viewByTitle(PROPERTIES).bot().spinner(0).setSelection(newValueLabelSize);
        assertThat(bot.viewByTitle(PROPERTIES).bot().spinner(0).getSelection(), equalTo(newValueLabelSize));
        SWTBotUtils.pressEnterKey(bot.viewByTitle(PROPERTIES).bot().spinner(0).widget);

        // Change label expression
        bot.viewByTitle(PROPERTIES).bot().text(0).setFocus();
        bot.viewByTitle(PROPERTIES).bot().text(0).setText(newValueLabelExpression);
        assertThat(bot.viewByTitle(PROPERTIES).bot().text(0).getText(), equalTo(newValueLabelExpression));
        SWTBotUtils.pressEnterKey(bot.viewByTitle(PROPERTIES).bot().spinner(0).widget);
    }

    /**
     * Retrieve, test, change and test all fields in Color tab.
     * 
     * @param backgroundValue
     *            index for the background color
     * @param newBackgroundValue
     *            index for the new background color
     * @param labelColorValue
     *            index for the label color value
     * @param newLabelColorValue
     *            index for the new label color value
     */
    private void changeAndTestPropertyTabColor(int backgroundValue, int newBackgroundValue, int labelColorValue, int newLabelColorValue) {
        // Retrieve color for Background Color and verify equal to light_yellow
        String backgroundColor = bot.viewByTitle(PROPERTIES).bot().ccomboBox(1).selection();
        assertThat(backgroundColor, equalTo(MAPCOLORVALUE.get(backgroundValue)));
        // Choose black color for Background Color
        bot.viewByTitle(PROPERTIES).bot().ccomboBox(1).setSelection(newBackgroundValue);
        String newBackgroundColor = bot.viewByTitle(PROPERTIES).bot().ccomboBox(1).selection();
        assertThat(newBackgroundColor, equalTo(MAPCOLORVALUE.get(newBackgroundValue)));
        // Retrieve color for Label Color
        String labelColor = bot.viewByTitle(PROPERTIES).bot().ccomboBox(0).selection();
        assertThat(labelColor, equalTo(MAPCOLORVALUE.get(labelColorValue)));
        // Choose blue color for Label Color
        bot.viewByTitle(PROPERTIES).bot().ccomboBox(0).setSelection(newLabelColorValue);
        String newLabelColor = bot.viewByTitle(PROPERTIES).bot().ccomboBox(0).selection();
        assertThat(newLabelColor, equalTo(MAPCOLORVALUE.get(newLabelColorValue)));
    }

}

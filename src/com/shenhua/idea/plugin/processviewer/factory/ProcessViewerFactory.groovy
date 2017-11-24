package com.shenhua.idea.plugin.processviewer.factory

import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory
import com.shenhua.idea.plugin.processviewer.core.DevicesModel
import com.shenhua.idea.plugin.processviewer.core.DevicesModelAdapter
import com.shenhua.idea.plugin.processviewer.etc.Constans
import org.jetbrains.annotations.NotNull;

import javax.swing.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.ItemEvent
import java.awt.event.ItemListener

/**
 * Created by shenhua on 2017-11-20-0020.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class ProcessViewerFactory implements ToolWindowFactory {

    private JPanel mPanel
    private JToolBar mLeftToolbar
    private JToolBar mConnectToolbar
    private JComboBox mDevicesComboBox
    private JTextField mFilterTextField
    private JTable mTable

    @Override
    void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // add content window
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance()
        Content content = contentFactory.createContent(mPanel, "", false)
        toolWindow.getContentManager().addContent(content)

        // add left toolbar
        ActionGroup leftGroup = ActionManager.instance.getAction(Constans.LEFT_GROUP_ID) as ActionGroup
        ActionToolbar leftToolbar = ActionManager.instance.createActionToolbar(Constans.LEFT_TOOLBAR_ID, leftGroup, false)
        mLeftToolbar.add(leftToolbar.getComponent())

        // add top toolbar
        ActionGroup topGroup = ActionManager.instance.getAction(Constans.TOP_GROUP_ID) as ActionGroup
        ActionToolbar topToolbar = ActionManager.instance.createActionToolbar(Constans.TOP_TOOLBAR_ID, topGroup, false)
        mConnectToolbar.add(topToolbar.getComponent())

        // add listener


        DevicesModel devicesModel = new DevicesModel();
        devicesModel.toGetDevices()
        DevicesModelAdapter model = new DevicesModelAdapter(devicesModel.getDevices())
        mDevicesComboBox.model = model
    }
}
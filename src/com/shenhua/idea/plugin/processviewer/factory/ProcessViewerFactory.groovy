package com.shenhua.idea.plugin.processviewer.factory

import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionToolbar
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import com.shenhua.idea.plugin.processviewer.bean.Device
import com.shenhua.idea.plugin.processviewer.bean.Process
import com.shenhua.idea.plugin.processviewer.callback.OnDevicesCallback
import com.shenhua.idea.plugin.processviewer.callback.OnProcessCallback
import com.shenhua.idea.plugin.processviewer.cmd.UserMouseAdapter
import com.shenhua.idea.plugin.processviewer.core.DeviceServerImpl
import com.shenhua.idea.plugin.processviewer.core.DevicesModel
import com.shenhua.idea.plugin.processviewer.core.DevicesModelAdapter
import com.shenhua.idea.plugin.processviewer.etc.Constans
import org.jetbrains.annotations.NotNull

import javax.swing.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

/**
 * Created by shenhua on 2017-11-20-0020.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class ProcessViewerFactory implements ToolWindowFactory, OnDevicesCallback, OnProcessCallback {

    private JPanel mPanel
    private JToolBar mLeftToolbar
    private JToolBar mConnectToolbar
    private JComboBox mDevicesComboBox
    private JTextField mFilterTextField
    private JTable mTable

    private int mCurrentDevice = 0
    private ArrayList<Device> mDevices = new ArrayList<>()
    private DeviceServerImpl mServer
    private DevicesModelAdapter mDevicesModelAdapter

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

        // connect the data model
        DevicesModel.get().onDevicesCallback = this

        // obtainDatas
        mServer = new DeviceServerImpl()
        mServer.getDevice(project, this)

    }

    def addDevicesComboBoxListener() {
        if (mDevicesComboBox.getActionListeners() > 0) {
            return
        }
        mDevicesComboBox.addActionListener(new ActionListener() {
            @Override
            void actionPerformed(ActionEvent e) {
                if (mCurrentDevice == mDevicesComboBox.getSelectedIndex()) {
                    return
                }
                mCurrentDevice = mDevicesComboBox.getSelectedIndex()
                Device device = mDevices.get(mCurrentDevice)
                println(Constans.TAG + "current devices:" + device.id)
            }
        })
    }

    def addTableListener() {
        if (mTable.getMouseListeners() > 0) {
            return
        }
        mTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
        mTable.addMouseListener(new UserMouseAdapter() {
            @Override
            void onSingleClicked(MouseEvent e) {
                println("sigle click: ${mTable.getSelectedRow()},${mTable.getSelectedColumn()}")
            }

            @Override
            void onDoubleClicked(MouseEvent e) {
                println("double click: ${mTable.getSelectedRow()}")
            }
        })
    }

    @Override
    void onObtainDevices(ArrayList<Device> devices) {
        if (mDevicesModelAdapter == null) {
            mDevicesModelAdapter = new DevicesModelAdapter(devices)
        }
        if (devices != null) {
            mDevices.clear()
            mDevices.addAll(devices)
        }
        println("---------------------- onObtainDevices")
        mDevicesComboBox.model = mDevicesModelAdapter
        addDevicesComboBoxListener()
    }

    @Override
    void onObtainProcess(ArrayList<Process> processes) {
        addTableListener()
    }
}
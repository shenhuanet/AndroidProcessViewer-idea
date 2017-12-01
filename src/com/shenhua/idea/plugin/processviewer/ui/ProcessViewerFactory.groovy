package com.shenhua.idea.plugin.processviewer.ui

import com.android.ddmlib.IDevice
import com.android.tools.idea.ddms.DeviceContext
import com.intellij.execution.ExecutionManager
import com.intellij.execution.ui.RunnerLayoutUi
import com.intellij.execution.ui.layout.PlaceInGrid
import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionToolbar
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBLoadingPanel
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import com.intellij.util.ui.UIUtil
import com.shenhua.idea.plugin.processviewer.bean.Device
import com.shenhua.idea.plugin.processviewer.callback.OnDevicesCallback
import com.shenhua.idea.plugin.processviewer.cmd.AdbHelper
import com.shenhua.idea.plugin.processviewer.cmd.UserMouseAdapter
import com.shenhua.idea.plugin.processviewer.core.DeviceServer
import com.shenhua.idea.plugin.processviewer.core.DeviceServerImpl
import com.shenhua.idea.plugin.processviewer.core.DevicesModelAdapter
import com.shenhua.idea.plugin.processviewer.etc.ComboBoxRenderer
import com.shenhua.idea.plugin.processviewer.etc.Constans
import org.jetbrains.annotations.NotNull

import javax.swing.*
import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseEvent

/**
 * Created by shenhua on 2017-11-20-0020.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class ProcessViewerFactory implements ToolWindowFactory, Disposable {

    private static final String TAG = "ProcessViewer"
    private JPanel mPanel1
    private JToolBar mLeftToolbar
    private JToolBar mConnectToolbar
    private JComboBox mDevicesComboBox
    private JTextField mFilterTextField
    private JTable mTable
    private DeviceContext mDeviceContext

    private int mCurrentDevice = 0
    private ArrayList<Device> mDevices = new ArrayList<>()
    private DeviceServerImpl mServer
    private DevicesModelAdapter mDevicesModelAdapter

    @Override
    void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ExecutionManager.getInstance(project).getContentManager()
        RunnerLayoutUi layoutUi = RunnerLayoutUi.Factory.getInstance(project).create(TAG, TAG, TAG, project)
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance()
        // content
        println(mPanel1 == null)
        Content content = layoutUi.createContent(TAG, mPanel1, TAG, null, null)
        content.setDisposer(this)
        layoutUi.addContent(content, 0, PlaceInGrid.center, false)
        // loading
        final JBLoadingPanel loadingPanel = new JBLoadingPanel(new BorderLayout(), project)
        loadingPanel.add(layoutUi.getComponent(), BorderLayout.CENTER)
        Content loadingContent = contentFactory.createContent(loadingPanel, "", true)
        toolWindow.getContentManager().addContent(loadingContent)

        AdbHelper adbHelper = new AdbHelper()
        adbHelper.loadingAdb(project, loadingPanel)
        createContent()
        setupDevices(project)
    }

    /**
     * to setup the DeviceServer for work
     * @param project
     */
    void setupDevices(@NotNull Project project) {
        ApplicationManager.getApplication().invokeLater({
            DeviceServer server = new DeviceServerImpl(project, mDeviceContext, new OnDevicesCallback() {
                @Override
                void onObtainDevices(ArrayList<IDevice> devices) {
                    UIUtil.invokeLaterIfNeeded({
                        if (!project.isDisposed()) {
                            obtainDevices(devices)
                        }
                    })
                }

                @Override
                void onDeviceSelected() {
                    println("xxxxx")
                }
            })
            server.start()
        }, project.getDisposed())
    }

    /**
     * update mDevicesComboBox to show the devices.
     * @param devices IDevice
     */
    private void obtainDevices(ArrayList<IDevice> devices) {
        mDevicesComboBox.removeAllItems()
        devices.each {
            mDevicesComboBox.addItem(it)
        }
    }

    /**
     * create the main content when inneed.
     */
    private void createContent() {
        mDeviceContext = new DeviceContext()
        ApplicationManager.getApplication().invokeLater({
            // add left toolbar
            ActionGroup leftGroup = ActionManager.instance.getAction(Constans.LEFT_GROUP_ID) as ActionGroup
            ActionToolbar leftToolbar = ActionManager.instance.createActionToolbar(Constans.LEFT_TOOLBAR_ID, leftGroup, false)
            mLeftToolbar.add(leftToolbar.getComponent())

            // add top toolbar
            ActionGroup topGroup = ActionManager.instance.getAction(Constans.TOP_GROUP_ID) as ActionGroup
            ActionToolbar topToolbar = ActionManager.instance.createActionToolbar(Constans.TOP_TOOLBAR_ID, topGroup, false)
            mConnectToolbar.add(topToolbar.getComponent())

            // config mDevicesComboBox
            mDevicesComboBox.setRenderer(new ComboBoxRenderer())
            mDevicesComboBox.addActionListener(new ActionListener() {
                @Override
                void actionPerformed(ActionEvent e) {
                    Object sel = mDevicesComboBox.getSelectedItem()
                    IDevice device = (sel instanceof IDevice) ? (IDevice) sel : null
                    mDeviceContext.fireDeviceSelected(device)
                }
            })
        })
    }

    def addDevicesComboBoxListener() {
        if (mDevicesComboBox.getActionListeners().length > 0) {
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

//    @Override
//    void onObtainDevices(ArrayList<Device> devices) {
////        if (mDevicesModelAdapter == null) {
//            mDevicesModelAdapter = new DevicesModelAdapter(devices)
////        }
//        if (devices != null) {
//            mDevices.clear()
//            mDevices.addAll(devices)
//        }
//        println("---------------------- onObtainDevices")
//        mDevicesComboBox.model = mDevicesModelAdapter
//        addDevicesComboBoxListener()
//    }
//
//    @Override
//    void onObtainProcess(ArrayList<Process> processes) {
//        addTableListener()
//    }
    @Override
    void dispose() {
        println("${TAG} dispose")
    }
}
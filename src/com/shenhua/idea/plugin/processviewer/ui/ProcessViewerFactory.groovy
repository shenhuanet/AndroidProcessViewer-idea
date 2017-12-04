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
import com.shenhua.idea.plugin.processviewer.actions.RefreshAction
import com.shenhua.idea.plugin.processviewer.bean.Device
import com.shenhua.idea.plugin.processviewer.bean.Process
import com.shenhua.idea.plugin.processviewer.callback.OnDevicesCallback
import com.shenhua.idea.plugin.processviewer.callback.OnProcessCallback
import com.shenhua.idea.plugin.processviewer.cmd.AdbHelper
import com.shenhua.idea.plugin.processviewer.cmd.UserMouseAdapter
import com.shenhua.idea.plugin.processviewer.core.DeviceServer
import com.shenhua.idea.plugin.processviewer.core.DeviceServerImpl
import com.shenhua.idea.plugin.processviewer.core.DevicesModelAdapter
import com.shenhua.idea.plugin.processviewer.etc.ComboBoxRenderer
import com.shenhua.idea.plugin.processviewer.etc.Constans
import com.shenhua.idea.plugin.processviewer.etc.ProcessTableModel
import org.jetbrains.annotations.NotNull

import javax.swing.*
import javax.swing.table.DefaultTableModel
import javax.swing.table.TableModel
import javax.swing.table.TableRowSorter
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseEvent

/**
 * Created by shenhua on 2017-11-20-0020.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class ProcessViewerFactory implements ToolWindowFactory, Disposable, OnProcessCallback {

    private static final String TAG = "ProcessViewer"
    private Project mProject
    private JPanel mPanel
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
//    private RefreshAction mRefreshAction
    private TableRowSorter<TableModel> sorter
    private OnProcessCallback mProcessCallback

    @Override
    void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        mProject = project
        ExecutionManager.getInstance(project).getContentManager()
        RunnerLayoutUi layoutUi = RunnerLayoutUi.Factory.getInstance(project).create(TAG, TAG, TAG, project)
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance()
        // content
        Content content = layoutUi.createContent(TAG, mPanel, TAG, null, null)
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
        setupDevices()
    }

    /**
     * to setup the DeviceServer for work
     */
    void setupDevices() {
        ApplicationManager.getApplication().invokeLater({
            DeviceServer server = new DeviceServerImpl(mProject, mDeviceContext, new OnDevicesCallback() {
                @Override
                void onObtainDevices(ArrayList<IDevice> devices) {
                    UIUtil.invokeLaterIfNeeded({
                        if (!mProject.isDisposed()) {
                            obtainDevices(devices)
                        }
                    })
                }

                @Override
                void onDeviceSelected(IDevice device) {
                    AdbHelper adbHelper = new AdbHelper()
                    ArrayList<Process> processes = adbHelper.getProcess(mProject, device.serialNumber)
                    println(processes.size())
                    onObtainProcess(processes)
                }
            })
            server.start()
        }, mProject.getDisposed())
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
            RefreshAction refreshAction = ActionManager.instance.getAction(Constans.ACTION_ID_REFRESH)
            refreshAction.processCallback = this

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

            // config table
            mTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
            mTable.getTableHeader().setPreferredSize(new Dimension(-1, 24))
            mTable.addMouseListener(new UserMouseAdapter() {
                @Override
                void onSingleClicked(MouseEvent e) {
                    println("single click: x(${mTable.getSelectedColumn()}) y(${mTable.getSelectedRow()})")
                }

                @Override
                void onDoubleClicked(MouseEvent e) {
                    println("double click: x(${mTable.getSelectedRow()})")
                }
            })
        })
    }

    @Override
    void dispose() {
        println("${TAG} dispose")
    }

    @Override
    void onObtainProcess(ArrayList<Process> processes) {
        DefaultTableModel model = new ProcessTableModel()
        Object[][] datas = new Object[processes.size()][7]
        for (int i = 0; i < processes.size(); i++) {
            datas[i][0] = processes.get(i).user
            datas[i][1] = processes.get(i).pid
            datas[i][2] = processes.get(i).ppid
            datas[i][3] = processes.get(i).vSize
            datas[i][4] = processes.get(i).rss
            datas[i][5] = processes.get(i).wChan
            datas[i][6] = processes.get(i).name
        }
        model.setDataVector(datas, Constans.COLUMN_NAME)
        sorter = new TableRowSorter<TableModel>(model)
        mTable.setModel(model)
        mTable.setRowSorter(sorter)
        setTableSize()
    }

    private void setTableSize() {
        mTable.getColumnModel().getColumn(0).setPreferredWidth(50)
        mTable.getColumnModel().getColumn(1).setPreferredWidth(50)
        mTable.getColumnModel().getColumn(2).setPreferredWidth(50)
        mTable.getColumnModel().getColumn(3).setPreferredWidth(50)
        mTable.getColumnModel().getColumn(4).setPreferredWidth(50)
        mTable.getColumnModel().getColumn(5).setPreferredWidth(50)
    }
}
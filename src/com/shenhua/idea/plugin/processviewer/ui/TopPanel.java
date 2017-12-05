package com.shenhua.idea.plugin.processviewer.ui;

import com.android.ddmlib.IDevice;
import com.android.tools.idea.ddms.DeviceContext;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.util.ui.UIUtil;
import com.shenhua.idea.plugin.processviewer.actions.RefreshAction;
import com.shenhua.idea.plugin.processviewer.bean.Process;
import com.shenhua.idea.plugin.processviewer.callback.OnDevicesCallback;
import com.shenhua.idea.plugin.processviewer.callback.OnProcessCallback;
import com.shenhua.idea.plugin.processviewer.cmd.AdbHelper;
import com.shenhua.idea.plugin.processviewer.core.DeviceServer;
import com.shenhua.idea.plugin.processviewer.core.DeviceServerImpl;
import com.shenhua.idea.plugin.processviewer.etc.ComboBoxRenderer;
import com.shenhua.idea.plugin.processviewer.etc.Constans;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.BadLocationException;
import java.util.ArrayList;

/**
 * Created by shenhua on 2017-12-05-0005.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class TopPanel implements OnProcessCallback {

    private DeviceContext mDeviceContext;
    private JPanel mPanel;
    private JComboBox<IDevice> mDevicesComboBox;
    private JTextField mFilterTextField;
    private TablePanel mTablePanel;
    private Project mProject;

    TopPanel(Project project, TablePanel tablePanel) {
        this.mProject = project;
        this.mTablePanel = tablePanel;
    }

    void init(ActionToolbar toolbar) {
        configPanel(toolbar);
        setFilter();
    }

    private void setFilter() {
        mFilterTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    setTableFilter(e.getDocument().getText(0, e.getDocument().getLength()));
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    setTableFilter(e.getDocument().getText(0, e.getDocument().getLength()));
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    private void setTableFilter(String text) {
        TableRowSorter<TableModel> sorter = mTablePanel.getSorter();
        if (sorter != null) {
            if (text.length() == 0) {
                sorter.setRowFilter(null);
            } else {
                try {
                    sorter.setRowFilter(RowFilter.regexFilter(text));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void configPanel(ActionToolbar toolbar) {
        mDeviceContext = new DeviceContext();
        mDevicesComboBox.setRenderer(new ComboBoxRenderer(toolbar));
        mDevicesComboBox.addActionListener(e -> {
            Object sel = mDevicesComboBox.getSelectedItem();
            IDevice device = (sel instanceof IDevice) ? (IDevice) sel : null;
            mDeviceContext.fireDeviceSelected(device);
        });
        ApplicationManager.getApplication().invokeLater(() -> {
            DeviceServer server = new DeviceServerImpl(mProject, mDeviceContext, new OnDevicesCallback() {
                @Override
                public void onObtainDevices(ArrayList<IDevice> devices) {
                    UIUtil.invokeLaterIfNeeded(() -> {
                        if (!mProject.isDisposed()) {
                            obtainDevices(devices);
                        }
                    });
                }

                @Override
                public void onDeviceSelected(IDevice device) {
                    deviceSelected(mProject, device);
                }
            });
            server.start();
        }, mProject.getDisposed());
    }

    private void deviceSelected(Project project, IDevice device) {
        AdbHelper adbHelper = new AdbHelper();
        ArrayList<Process> processes = adbHelper.getProcess(project, device.getSerialNumber());
        onObtainProcess(processes);
        RefreshAction refreshAction = (RefreshAction) ActionManager.getInstance().getAction(Constans.ACTION_ID_REFRESH);
        refreshAction.processCallback = this;
        refreshAction.setSerialNumber(device.getSerialNumber());
    }

    /**
     * update mDevicesComboBox to show the devices.
     *
     * @param devices IDevice
     */
    private void obtainDevices(ArrayList<IDevice> devices) {
        mDevicesComboBox.removeAllItems();
        for (IDevice device : devices) {
            mDevicesComboBox.addItem(device);
        }
    }

    @Override
    public void onObtainProcess(ArrayList<Process> processes) {
        if (processes == null || processes.size() <= 0) {
            return;
        }
        mTablePanel.onObtainProcess(processes);
    }

    JPanel getmPanel() {
        return mPanel;
    }

}

package com.shenhua.idea.plugin.processviewer.ui;

import com.shenhua.idea.plugin.processviewer.bean.Process;
import com.shenhua.idea.plugin.processviewer.callback.OnProcessCallback;
import com.shenhua.idea.plugin.processviewer.cmd.UserMouseAdapter;
import com.shenhua.idea.plugin.processviewer.etc.Constans;
import com.shenhua.idea.plugin.processviewer.etc.ProcessTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * 表格布局
 * Created by shenhua on 2017-12-05-0005.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class TablePanel implements OnProcessCallback {

    private JPanel mPanel;
    private JTable mTable;
    private TableRowSorter<TableModel> sorter;

    TablePanel() {
        configTable();
    }

    private void configTable() {
        // config table
        mTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mTable.getTableHeader().setPreferredSize(new Dimension(-1, 24));
        mTable.addMouseListener(new UserMouseAdapter() {
            @Override
            public void onSingleClicked(MouseEvent e) {
//                    println("single click: x(${mTable.getSelectedColumn()}) y(${mTable.getSelectedRow()})");
            }

            @Override
            public void onDoubleClicked(MouseEvent e) {
//                    println("double click: x(${mTable.getSelectedRow()})")
            }
        });
    }

    JComponent getPanel() {
        return mPanel;
    }

    @Override
    public void onObtainProcess(ArrayList<Process> processes) {
        DefaultTableModel model = new ProcessTableModel();
        Object[][] datas = new Object[processes.size()][7];
        for (int i = 0; i < processes.size(); i++) {
            datas[i][0] = processes.get(i).getUser();
            datas[i][1] = processes.get(i).getPid();
            datas[i][2] = processes.get(i).getPpid();
            datas[i][3] = processes.get(i).getvSize();
            datas[i][4] = processes.get(i).getRss();
            datas[i][5] = processes.get(i).getwChan();
            datas[i][6] = processes.get(i).getName();
        }
        model.setDataVector(datas, Constans.COLUMN_NAME);
        mTable.setModel(model);
        sorter = new TableRowSorter<>(model);
        mTable.setRowSorter(sorter);
        setTableSize();
    }

    private void setTableSize() {
        try {
            for (int i = 0; i < 6; i++) {
                mTable.getColumnModel().getColumn(i).setMinWidth(Constans.COLUMN_WIDTH);
                mTable.getColumnModel().getColumn(i).setMaxWidth(Constans.COLUMN_WIDTH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    TableRowSorter<TableModel> getSorter() {
        return sorter;
    }
}

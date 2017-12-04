package com.shenhua.idea.plugin.processviewer.etc

import javax.swing.table.DefaultTableModel

/**
 * Created by shenhua on 2017-12-04-0004.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class ProcessTableModel extends DefaultTableModel {

    @Override
    boolean isCellEditable(int row, int column) {
        false
    }
}

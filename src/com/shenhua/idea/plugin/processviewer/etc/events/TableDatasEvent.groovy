package com.shenhua.idea.plugin.processviewer.etc.events

/**
 * Created by shenhua on 2017-12-06-0006.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class TableDatasEvent {

    // 0显示,1不显示,2清空
    int type

    TableDatasEvent(int type) {
        this.type = type
    }
}

package com.shenhua.idea.plugin.processviewer.etc.events

/**
 * Created by shenhua on 2017-12-06-0006.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class StopProcessEvent {

    String pid

    StopProcessEvent(String pid) {
        this.pid = pid
    }
}

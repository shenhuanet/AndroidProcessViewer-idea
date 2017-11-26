package com.shenhua.idea.plugin.processviewer.bean

/**
 * Created by shenhua on 2017-11-25-0025.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class Process {

    String user
    String pid
    String ppid
    String vSize
    String rss
    String wChan
    String name

    @Override
    String toString() {
        "ppid:${ppid} name:${name}"
    }
}

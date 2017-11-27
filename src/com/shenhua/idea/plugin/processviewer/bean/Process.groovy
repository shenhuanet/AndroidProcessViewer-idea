package com.shenhua.idea.plugin.processviewer.bean

/**
 * Created by shenhua on 2017-11-25-0025.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class Process {

    String user // 0
    String pid // 1
    String ppid // 2
    String vSize // 3
    String rss // 4
    String wChan // 5
    String name // 8

    @Override
    String toString() {
        "ppid:${ppid} name:${name}"
    }
}

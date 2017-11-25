package com.shenhua.idea.plugin.processviewer.bean

/**
 * Created by shenhua on 2017-11-24-0024.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class Device {

    String DisplayName
    String name
    boolean isOnline

    Device(String displayName, boolean isOnline) {
        DisplayName = displayName
        this.isOnline = isOnline
    }

    @Override
    String toString() {
        String suf = ""
        if (!isOnline) {
            suf = " - (offline)"
        }
        return getDisplayName() + suf
    }
}

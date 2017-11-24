package com.shenhua.idea.plugin.processviewer.core

import com.shenhua.idea.plugin.processviewer.bean.Device

/**
 * Created by shenhua on 2017-11-24-0024.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class DevicesModel {

    ArrayList<Device> devices = new ArrayList<>()

    void toGetDevices() {
        devices.add(new Device("aaaa", true))
        devices.add(new Device("bbbbbbb", false))
        devices.add(new Device("cc", false))
        devices.add(new Device("ddddd", true))
    }

    ArrayList<Device> getDevices() {
        return devices
    }
}

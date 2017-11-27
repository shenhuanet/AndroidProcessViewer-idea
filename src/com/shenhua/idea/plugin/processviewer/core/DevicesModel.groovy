package com.shenhua.idea.plugin.processviewer.core

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.shenhua.idea.plugin.processviewer.bean.Device
import com.shenhua.idea.plugin.processviewer.callback.OnDevicesCallback
import com.shenhua.idea.plugin.processviewer.cmd.AdbHelper
import com.shenhua.idea.plugin.processviewer.cmd.CommandLine
import com.shenhua.idea.plugin.processviewer.cmd.DeviceAdbParser

/**
 * Created by shenhua on 2017-11-24-0024.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class DevicesModel {

    private static DevicesModel sModel = null
    private ArrayList<Device> devices
    private ArrayList<Process> processes
    private OnDevicesCallback mOnDevicesCallback

    synchronized static DevicesModel get() {
        if (sModel == null) {
            synchronized (DevicesModel.class) {
                sModel = new DevicesModel()
            }
        }
        sModel
    }

    private DevicesModel(){}

    OnDevicesCallback getOnDevicesCallback() {
        return mOnDevicesCallback
    }

    void setOnDevicesCallback(OnDevicesCallback mOnDevicesCallback) {
        this.mOnDevicesCallback = mOnDevicesCallback
    }

}

package com.shenhua.idea.plugin.processviewer.core

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.shenhua.idea.plugin.processviewer.bean.Device
import com.shenhua.idea.plugin.processviewer.cmd.AdbHelper
import com.shenhua.idea.plugin.processviewer.cmd.CommandLine
import com.shenhua.idea.plugin.processviewer.cmd.DeviceAdbParser

/**
 * Created by shenhua on 2017-11-24-0024.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class DevicesModel {

    void toGetDevices(Project project) {
        toGetDevices(project, null)
    }

    void toGetDevices(Project project, Callback callback) {
        CommandLine commandline = new CommandLine()
        DeviceAdbParser parser = new DeviceAdbParser()
        ApplicationManager.getApplication().executeOnPooledThread({
            AdbHelper adbHelper = new AdbHelper(project, commandline, parser)
            ArrayList<Device> devices = adbHelper.getDevices()
            if (callback != null) {
                callback.onObtainDevices(devices)
            }
        })
    }

    interface Callback {
        void onObtainDevices(ArrayList<Device> devices)
    }
}

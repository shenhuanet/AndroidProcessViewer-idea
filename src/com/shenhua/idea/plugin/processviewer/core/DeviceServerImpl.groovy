package com.shenhua.idea.plugin.processviewer.core

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.shenhua.idea.plugin.processviewer.bean.Device
import com.shenhua.idea.plugin.processviewer.callback.OnDevicesCallback
import com.shenhua.idea.plugin.processviewer.callback.OnKillProcessCallback
import com.shenhua.idea.plugin.processviewer.callback.OnProcessCallback
import com.shenhua.idea.plugin.processviewer.cmd.AdbHelper

/**
 * Created by shenhua on 2017/11/27.
 * Email shenhuanet@126.com
 *
 * @author shenhua
 */
class DeviceServerImpl implements DeviceServer {

    @Override
    void getDevice(Project project, OnDevicesCallback callback) {
        ApplicationManager.getApplication().executeOnPooledThread({
            AdbHelper adbHelper = new AdbHelper()
            ArrayList<Device> devices = adbHelper.getDevices(project)
            callback.onObtainDevices(devices)
        })
    }

    @Override
    void getProcess(Project project, String serId, OnProcessCallback callback) {

    }

    @Override
    void stopProcess(String serId, String pid, OnKillProcessCallback callback) {

    }
}

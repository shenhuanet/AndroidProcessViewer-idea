package com.shenhua.idea.plugin.processviewer.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.shenhua.idea.plugin.processviewer.bean.Device
import com.shenhua.idea.plugin.processviewer.cmd.AdbHelper
import com.shenhua.idea.plugin.processviewer.cmd.CommandLine
import com.shenhua.idea.plugin.processviewer.cmd.DeviceAdbParser
import com.shenhua.idea.plugin.processviewer.core.DeviceServerImpl
import com.shenhua.idea.plugin.processviewer.core.DevicesModel
import com.shenhua.idea.plugin.processviewer.etc.Constans
import com.shenhua.idea.plugin.processviewer.factory.ProcessViewerFactory

/**
 * Created by shenhua on 2017-11-21-0021.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class RefreshDevicesAction extends AnAction {

    @Override
    void actionPerformed(AnActionEvent anActionEvent) {
//        if (!AdbHelper.isInstalled()) {
//            println("Android sdk not install.")
//            return
//        }
        new DeviceServerImpl().getDevice(anActionEvent.project, DevicesModel.get().onDevicesCallback)
    }
}
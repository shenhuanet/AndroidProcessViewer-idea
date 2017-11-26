package com.shenhua.idea.plugin.processviewer.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.shenhua.idea.plugin.processviewer.bean.Device
import com.shenhua.idea.plugin.processviewer.cmd.AdbHelper
import com.shenhua.idea.plugin.processviewer.cmd.CommandLine
import com.shenhua.idea.plugin.processviewer.cmd.DeviceAdbParser

/**
 * Created by shenhua on 2017-11-21-0021.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class RefreshDevicesAction extends AnAction {

    ArrayList<Device> devices = new LinkedList<Device>()

    @Override
    void actionPerformed(AnActionEvent anActionEvent) {
//        if (!AdbHelper.isInstalled()) {
//            println("Android sdk not install.")
//            return
//        }
        CommandLine commandline = new CommandLine()
        DeviceAdbParser parser = new DeviceAdbParser()
        ApplicationManager.getApplication().executeOnPooledThread({
            AdbHelper adbHelper = new AdbHelper(anActionEvent.project, commandline, parser)
            devices = adbHelper.getDevices()
            devices.forEach {
                println(it)
            }
        })
    }
}

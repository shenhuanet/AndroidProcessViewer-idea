package com.shenhua.idea.plugin.processviewer.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.shenhua.idea.plugin.processviewer.bean.Process
import com.shenhua.idea.plugin.processviewer.cmd.AdbHelper
import com.shenhua.idea.plugin.processviewer.cmd.CommandLine
import com.shenhua.idea.plugin.processviewer.cmd.DeviceAdbParser
import com.shenhua.idea.plugin.processviewer.etc.Constans;

/**
 * Created by shenhua on 2017-11-20-0020.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class RefreshAction extends AnAction {

    @Override
    void actionPerformed(AnActionEvent e) {
        CommandLine commandline = new CommandLine()
        DeviceAdbParser parser = new DeviceAdbParser()
        ApplicationManager.getApplication().executeOnPooledThread({
            AdbHelper adbHelper = new AdbHelper(e.project, commandline, parser)
            ArrayList<Process> processes = adbHelper.getProcess("N2F4C15C08046582")
            processes.forEach {
                println(it.name)
            }
        })
    }
}

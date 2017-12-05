package com.shenhua.idea.plugin.processviewer.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.shenhua.idea.plugin.processviewer.bean.Process
import com.shenhua.idea.plugin.processviewer.callback.OnProcessCallback
import com.shenhua.idea.plugin.processviewer.cmd.AdbHelper

/**
 * Created by shenhua on 2017-11-20-0020.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class RefreshAction extends AnAction {

    public OnProcessCallback processCallback
    public volatile String serialNumber

    void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber
    }

    @Override
    void actionPerformed(AnActionEvent e) {
        ApplicationManager.getApplication().executeOnPooledThread({
            AdbHelper adbHelper = new AdbHelper()
            ArrayList<Process> processes = adbHelper.getProcess(e.project, serialNumber)
            if (processCallback != null) {
                processCallback.onObtainProcess(processes)
            }
        })
    }

    @Override
    void update(AnActionEvent e) {
        super.update(e)
        e.getPresentation().enabled = serialNumber != null
    }
}

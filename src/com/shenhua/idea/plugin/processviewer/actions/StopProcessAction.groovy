package com.shenhua.idea.plugin.processviewer.actions

import com.google.common.eventbus.Subscribe
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.shenhua.idea.plugin.processviewer.cmd.AdbHelper
import com.shenhua.idea.plugin.processviewer.etc.BusProvider
import com.shenhua.idea.plugin.processviewer.etc.events.RefreshEvent
import com.shenhua.idea.plugin.processviewer.etc.events.StopProcessEvent

/**
 * Created by shenhua on 2017-11-20-0020.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class StopProcessAction extends AnAction {

    public volatile boolean isRoot
    public volatile String serialNumber

    StopProcessAction() {
        BusProvider.get().register(this);
    }

    private volatile String pid

    @Subscribe
    void setPid(StopProcessEvent event) {
        this.pid = event.pid
    }

    @Subscribe
    void setSerialNumber(RefreshEvent event) {
        this.serialNumber = event.serialNumber
    }

    @Override
    void actionPerformed(AnActionEvent anActionEvent) {
        if (serialNumber == null) {
            return
        }
        ApplicationManager.getApplication().executeOnPooledThread({
            AdbHelper adbHelper = new AdbHelper()
            adbHelper.killProcess(anActionEvent.project, serialNumber, pid)
        })
    }

    @Override
    void update(AnActionEvent e) {
        super.update(e)
        e.presentation.enabled = (isRoot && pid != null)
    }
}

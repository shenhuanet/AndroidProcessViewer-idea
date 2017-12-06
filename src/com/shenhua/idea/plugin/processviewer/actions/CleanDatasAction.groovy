package com.shenhua.idea.plugin.processviewer.actions

import com.google.common.eventbus.Subscribe
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.shenhua.idea.plugin.processviewer.etc.BusProvider
import com.shenhua.idea.plugin.processviewer.etc.Constans
import com.shenhua.idea.plugin.processviewer.etc.events.StopProcessEvent
import com.shenhua.idea.plugin.processviewer.etc.events.TableDatasEvent

/**
 * Created by shenhua on 2017-11-21-0021.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class CleanDatasAction extends AnAction {

    volatile boolean show

    CleanDatasAction() {
        BusProvider.get().register(this)
    }

    @Subscribe
    void update(TableDatasEvent event) {
        // 不对清空事件进行订阅
        if (event.type == Constans.CLEAN_CLEAN) {
            return
        }
        this.show = event.type == Constans.CLEAN_SHOW
    }

    @Override
    void actionPerformed(AnActionEvent anActionEvent) {
        BusProvider.get().post(new TableDatasEvent(Constans.CLEAN_CLEAN))
        BusProvider.get().post(new StopProcessEvent(null))
    }

    @Override
    void update(AnActionEvent e) {
        super.update(e)
        e.presentation.enabled = show
    }
}

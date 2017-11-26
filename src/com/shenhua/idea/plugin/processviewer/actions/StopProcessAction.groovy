package com.shenhua.idea.plugin.processviewer.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * Created by shenhua on 2017-11-20-0020.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class StopProcessAction extends AnAction {

    @Override
    void actionPerformed(AnActionEvent anActionEvent) {
        println(anActionEvent.project.basePath)
    }
}

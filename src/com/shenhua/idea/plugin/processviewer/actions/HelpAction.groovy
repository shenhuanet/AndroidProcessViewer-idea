package com.shenhua.idea.plugin.processviewer.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.util.messages.impl.Message

/**
 * Created by shenhua on 2017-11-20-0020.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class HelpAction extends AnAction {

    @Override
    void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT)
        Messages.showMessageDialog(project, "Hello", "Message", Messages.getInformationIcon())
    }
}

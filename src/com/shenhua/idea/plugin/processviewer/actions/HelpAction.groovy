package com.shenhua.idea.plugin.processviewer.actions

import com.android.tools.idea.actions.BrowserHelpAction
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

/**
 * Created by shenhua on 2017-11-20-0020.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class HelpAction extends BrowserHelpAction {

    HelpAction() {
        super("Help", "https://github.com/shenhuanet/AndroidProcessViewer-idea");
    }

}

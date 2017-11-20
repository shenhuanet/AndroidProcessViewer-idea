package com.shenhua.idea.plugin.processviewer.factory

import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory
import com.shenhua.idea.plugin.processviewer.etc.Constans
import org.jetbrains.annotations.NotNull;

import javax.swing.*
import javax.swing.border.EmptyBorder
import javax.swing.border.LineBorder;

/**
 * Created by shenhua on 2017-11-20-0020.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class ProcessViewerFactory implements ToolWindowFactory {

    private JPanel mPanel
    private JToolBar mLeftToolbar

    @Override
    void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance()
        Content content = contentFactory.createContent(mPanel, "", false)
        toolWindow.getContentManager().addContent(content)

        ActionGroup actionGroup = ActionManager.instance.getAction(Constans.LEFT_GROUP_ID) as ActionGroup
        ActionToolbar leftToolbar = ActionManager.instance.createActionToolbar(Constans.LEFT_TOOLBAR_ID, actionGroup, false)
        mLeftToolbar.add(leftToolbar.getComponent())

    }
}
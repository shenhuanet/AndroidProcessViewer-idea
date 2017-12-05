package com.shenhua.idea.plugin.processviewer.ui

import com.intellij.execution.ExecutionManager
import com.intellij.execution.ui.RunnerLayoutUi
import com.intellij.execution.ui.layout.PlaceInGrid
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.AbstractProjectComponent
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.openapi.wm.ex.ToolWindowManagerEx
import com.intellij.openapi.wm.ex.ToolWindowManagerListener
import com.intellij.ui.components.JBLoadingPanel
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import com.shenhua.idea.plugin.processviewer.cmd.AdbHelper
import com.shenhua.idea.plugin.processviewer.etc.Constans
import org.jetbrains.annotations.NotNull

import javax.swing.JComponent
import java.awt.BorderLayout

/**
 * 工程组件
 * Created by shenhua on 2017-12-05-0005.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class ContentComponent extends AbstractProjectComponent {

    boolean needLoading

    ContentComponent(Project project) {
        super(project)
    }

    static ContentComponent get(@NotNull Project project) {
        return project.getComponent(ContentComponent.class)
    }

    void init(ToolWindow toolwindow, RunnerLayoutUi layoutUi) {
        toolwindow.setToHideOnEmptyContent(true)
        Content content = layoutUi.createContent(Constans.WINDOW_ID, createContentPanel(), Constans.WINDOW_ID, null, null)
        content.setDisposer(project)
        layoutUi.addContent(content, 0, PlaceInGrid.center, false)
        ((ToolWindowManagerEx) ToolWindowManager.getInstance(myProject)).addToolWindowManagerListener(getToolWindowListener())
        if (needLoading) {
            JBLoadingPanel loadingPanel = new JBLoadingPanel(new BorderLayout(), project)
            loadingPanel.add(layoutUi.getComponent(), BorderLayout.CENTER)
            Content loadingContent = ContentFactory.SERVICE.getInstance().createContent(loadingPanel, "", true)
            toolwindow.getContentManager().addContent(loadingContent)
        } else {
            toolwindow.getContentManager().addContent(content)
        }
        AdbHelper adbHelper = new AdbHelper()
        adbHelper.loadingAdb(project)
    }

    private ToolWindowManagerListener getToolWindowListener() {
        new ToolWindowManagerListener() {
            @Override
            void toolWindowRegistered(@NotNull String s) {
            }

            @Override
            void stateChanged() {
                ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow(Constans.WINDOW_ID)
                if (toolWindow != null && toolWindow.isVisible() && toolWindow.getContentManager().getContentCount() == 0) {
                    ExecutionManager.getInstance(project).getContentManager()
                    RunnerLayoutUi layoutUi = RunnerLayoutUi.Factory.getInstance(project)
                            .create(Constans.WINDOW_ID, Constans.WINDOW_ID, Constans.WINDOW_ID, project)
                    init(toolWindow, layoutUi)
                }
            }
        }
    }

    private synchronized JComponent createContentPanel() {
        new ContentPanel(project)
    }

    Project getProject() {
        myProject
    }

}

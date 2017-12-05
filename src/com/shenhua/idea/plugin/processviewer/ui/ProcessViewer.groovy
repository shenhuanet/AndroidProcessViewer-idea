package com.shenhua.idea.plugin.processviewer.ui

import com.intellij.execution.ExecutionManager
import com.intellij.execution.ui.RunnerLayoutUi
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import org.jetbrains.annotations.NotNull

/**
 * 主内容窗口
 * Created by shenhua on 2017-12-05-0005.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class ProcessViewer implements ToolWindowFactory, Disposable {

    private static final String TAG = "ProcessViewer"

    @Override
    void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ExecutionManager.getInstance(project).getContentManager()
        RunnerLayoutUi layoutUi = RunnerLayoutUi.Factory.getInstance(project).create(TAG, TAG, TAG, project)
        ContentComponent component = ContentComponent.get(project)
        component.init(toolWindow, layoutUi)
    }

    @Override
    void dispose() {
        println("-----  dispose")
    }
}

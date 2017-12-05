package com.shenhua.idea.plugin.processviewer.ui;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.shenhua.idea.plugin.processviewer.etc.Constans;

import javax.swing.*;
import java.awt.*;

/**
 * 内容容器
 * Created by shenhua on 2017-12-05-0005.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class ContentPanel extends JPanel {

    private TopPanel mTopPanel;
    private TablePanel mTablePanel;
    private ActionToolbar mToolbar;

    public ContentPanel(Project project) {
        super(new BorderLayout());
        mTablePanel = new TablePanel();
        mTopPanel = new TopPanel(project, mTablePanel);
        ApplicationManager.getApplication().invokeLater(() -> {
            setToolbar();
            setTopbar();
            setTable();
        });
    }

    private void setToolbar() {
        SimpleToolWindowPanel contentPanel = new SimpleToolWindowPanel(false, true);
        ActionGroup leftGroup = (ActionGroup) ActionManager.getInstance().getAction(Constans.LEFT_GROUP_ID);
        mToolbar = ActionManager.getInstance().createActionToolbar(Constans.LEFT_TOOLBAR_ID, leftGroup, false);
        contentPanel.setToolbar(mToolbar.getComponent());
        add(contentPanel, BorderLayout.WEST);
    }

    private void setTopbar() {
        mTopPanel.init(mToolbar);
        add(mTopPanel.getmPanel(), BorderLayout.NORTH);
    }

    private void setTable() {
        add(mTablePanel.getPanel(), BorderLayout.CENTER);
    }
}

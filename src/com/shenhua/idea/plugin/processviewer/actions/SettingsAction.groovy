package com.shenhua.idea.plugin.processviewer.actions

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.util.IconLoader

/**
 * Created by shenhua on 2017-11-21-0021.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class SettingsAction extends AnAction {
    @Override
    void actionPerformed(AnActionEvent anActionEvent) {
        Notification notification = new Notification("Meassge",
                IconLoader.getIcon("/general/information.png"),
                "Meassge",
                null,
                "Settings now is unavailable, please continue to pay attention to this plug-in, Thanks",
                NotificationType.INFORMATION,
                null)
        notification.notify(anActionEvent.project)
    }
}

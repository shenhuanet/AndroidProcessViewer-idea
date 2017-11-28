package com.shenhua.idea.plugin.processviewer.cmd

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * Created by shenhua on 2017-11-28-0028.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class UserMouseAdapter extends MouseAdapter {

    boolean flag

    /**
     * SingleClick
     *
     * @param e MouseEvent
     */
    void onSingleClicked(MouseEvent e) {

    }

    /**
     * DoubleClick
     *
     * @param e MouseEvent
     */
    void onDoubleClicked(MouseEvent e) {

    }

    @Override
    void mouseClicked(MouseEvent e) {
        if (!flag) {
            flag = true;
            ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
            service.schedule({
                if (flag) {
                    onSingleClicked(e);
                    flag = false;
                }
            }, 200, TimeUnit.MILLISECONDS);
        } else {
            onDoubleClicked(e);
            flag = false;
        }
    }
}

package com.shenhua.idea.plugin.processviewer.core

import com.intellij.openapi.project.Project
import com.shenhua.idea.plugin.processviewer.callback.OnDevicesCallback
import com.shenhua.idea.plugin.processviewer.callback.OnKillProcessCallback
import com.shenhua.idea.plugin.processviewer.callback.OnProcessCallback

/**
 * Created by shenhua on 2017-11-27-0027.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
interface DeviceServer {

    void getDevice(Project project, OnDevicesCallback callback)

    void getProcess(Project project, String serId, OnProcessCallback callback)

    void stopProcess(String serId, String pid, OnKillProcessCallback callback)

}
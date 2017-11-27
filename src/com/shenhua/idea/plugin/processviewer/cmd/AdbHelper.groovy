package com.shenhua.idea.plugin.processviewer.cmd

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.shenhua.idea.plugin.processviewer.bean.Device
import com.shenhua.idea.plugin.processviewer.bean.Process
import com.shenhua.idea.plugin.processviewer.etc.Constans
import org.apache.http.util.TextUtils
import org.jetbrains.android.sdk.AndroidSdkUtils

/**
 * Created by shenhua on 2017/11/25.
 * Email shenhuanet@126.com
 *
 * @author shenhua
 */
class AdbHelper {

    static boolean isInstalled() {
        // TODO ClassCastException: com.android.tools.idea.sdk.AndroidSdks cannot be cast to com.android.tools.idea.sdk.AndroidSdks
        AndroidSdkUtils.isAndroidSdkAvailable()
    }

    ArrayList<Device> getDevices(Project project) {
        CommandLine commandLine = new CommandLine()
        DeviceAdbParser adbParser = new DeviceAdbParser()
        String adbOutput = commandLine.executeCommand(getAdbCommand(project), "devices", "-l")
        adbParser.parseGetDevicesOutput(adbOutput)
    }

    ArrayList<Process> getProcess(Project project, String deviceId) {
        CommandLine commandLine = new CommandLine()
        DeviceAdbParser adbParser = new DeviceAdbParser()
        String adbOutput = commandLine.executeShellCommand(getAdbCommand(project) + " -s " + deviceId + " shell " + "ps")
        adbParser.parseProcessList(adbOutput)
    }

    private synchronized String getAdbCommand(Project project) {
//        File adbFile = AndroidSdkUtils.getAdb(project)
//        if (adbFile != null) {
//            println(Constans.TAG + "adb file not null.")
//            return "${adbFile.getAbsolutePath()}adb"
//        }
        return "/Users/shenhua/Library/Android/sdk/platform-tools/adb"
    }
}

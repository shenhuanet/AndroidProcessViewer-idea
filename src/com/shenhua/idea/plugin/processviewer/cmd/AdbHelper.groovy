package com.shenhua.idea.plugin.processviewer.cmd

import com.android.ddmlib.AndroidDebugBridge
import com.android.tools.idea.ddms.adb.AdbService
import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBLoadingPanel
import com.shenhua.idea.plugin.processviewer.bean.Device
import com.shenhua.idea.plugin.processviewer.bean.Process
import org.jetbrains.android.sdk.AndroidSdkUtils
import org.jetbrains.annotations.NotNull

/**
 * Created by shenhua on 2017/11/25.
 * Email shenhuanet@126.com
 *
 * @author shenhua
 */
class AdbHelper {

    AdbHelper() {
    }

    synchronized void loadingAdb(@NotNull Project project, JBLoadingPanel loadingPanel) {
        if (loadingPanel == null) {
            return
        }
        File adb = AndroidSdkUtils.getAdb(project)
        if (adb == null) {
            println("Adb file is null")
        } else {
            loadingPanel.setLoadingText("Initializing ADB")
            loadingPanel.startLoading()
            println("Adb file is " + adb.getAbsolutePath())
            ListenableFuture<AndroidDebugBridge> future = AdbService.getInstance().getDebugBridge(adb)
            Futures.addCallback(future, new FutureCallback<AndroidDebugBridge>() {
                @Override
                void onSuccess(AndroidDebugBridge androidDebugBridge) {
                    println("Successfully obtained debug bridge")
                    loadingPanel.stopLoading()
                }

                @Override
                void onFailure(Throwable throwable) {
                    loadingPanel.stopLoading()
                }
            })
        }
    }

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
//        return "/Users/shenhua/Library/Android/sdk/platform-tools/adb"
        "adb"
    }
}

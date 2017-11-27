package com.shenhua.idea.plugin.processviewer.cmd

import com.intellij.openapi.project.Project
import com.shenhua.idea.plugin.processviewer.bean.Device
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

    static final String TCPIP_PORT = "5555"
    private CommandLine commandLine
    private DeviceAdbParser adbParser
    private Project project

    AdbHelper(Project project, CommandLine commandLine, DeviceAdbParser adbParser) {
        this.project = project
        this.commandLine = commandLine
        this.adbParser = adbParser
    }

    static boolean isInstalled() {
        // TODO ClassCastException: com.android.tools.idea.sdk.AndroidSdks cannot be cast to com.android.tools.idea.sdk.AndroidSdks
        AndroidSdkUtils.isAndroidSdkAvailable()
    }

    ArrayList<Device> getDevices() {
        String adbDevicesOutput = commandLine.executeCommand(getAdbCommand(), "devices", "-l")
        adbParser.parseGetDevicesOutput(adbDevicesOutput)
    }

    private void enableTCPCommand() {
        if (!checkTCPCommandExecuted()) {
            String enableTCPCommand = getCommand("tcpip " + TCPIP_PORT)
            commandLine.executeCommand(enableTCPCommand)
        }
    }

    private boolean checkTCPCommandExecuted() {
        String getPropCommand = getCommand("adb shell getprop | grep adb")
        String getPropOutput = commandLine.executeCommand(getPropCommand)
        String adbTcpPort = adbParser.parseAdbServiceTcpPort(getPropOutput)
        TCPIP_PORT == (adbTcpPort)
    }

    private boolean connectDevice(String deviceIp) {
        String enableTCPCommand = getCommand("tcpip 5555")
        commandLine.executeCommand(enableTCPCommand)
        String connectDeviceCommand = getCommand("connect " + deviceIp)
        String connectOutput = commandLine.executeCommand(connectDeviceCommand)
        connectOutput.contains("connected")
    }

    private String getAdbPath() {
        String adbPath = ""
        File adbFile = AndroidSdkUtils.getAdb(project)
        if (adbFile != null) {
            println(Constans.TAG + "adb file not null.")
            adbPath = adbFile.getAbsolutePath()
        }
        println("${Constans.TAG}adbPath: ${adbPath}")
        adbPath
    }

    private String getAdbCommand() {
        TextUtils.isEmpty(getAdbPath()) ?
                "adb" :
                "${getAdbPath()}adb"
    }

    private String getCommand(String... command) {
        TextUtils.isEmpty(getAdbPath()) ?
                "adb ${command}" :
                "${getAdbPath()}adb ${command}"
    }
}

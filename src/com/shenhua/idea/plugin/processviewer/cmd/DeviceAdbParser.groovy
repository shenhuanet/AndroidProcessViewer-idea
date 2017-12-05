package com.shenhua.idea.plugin.processviewer.cmd


import com.shenhua.idea.plugin.processviewer.bean.Process

/**
 * Created by shenhua on 2017/11/25.
 * Email shenhuanet@126.com
 *
 * @author shenhua
 */
class DeviceAdbParser {

    static final String MODEL_INDICATOR = "model:"
    static final String DEVICE_INDICATOR = "device:"
    static final String DEVICE_DEVICE_INDICATOR = "device "
    static final String DEVICE_USB_INDICATOR = "usb:"
    static final String PRODUCT_INDICATOR = "product:"

    static final String DOT_SEPARATOR = "."
    static final String END_DEVICE_IP_INDICATOR = "/"
    static final String START_DEVICE_IP_INDICATOR = "inet"
    static final String ERROR_PARSING_DEVICE_IP_KEY = "Object"
    static final String DAEMON_INDICATOR = "daemon"
    static final String START_TCPIP_PORT_INDICATOR = "[service.adb.tcp.port]: ["
    static final String DEVICE_NOT_FOUND = "error: device '(null)' not found"

    static String parseDeviceName(String line) {
        int start = line.indexOf(DEVICE_INDICATOR) + DEVICE_INDICATOR.length()
        line.substring(start, line.length())
    }

    static String parseProduct(String line) {
        int start = line.indexOf(PRODUCT_INDICATOR) + PRODUCT_INDICATOR.length()
        int end = line.indexOf(MODEL_INDICATOR) - 1
        if (end < 0) {
            end = line.length()
        }
        line.substring(start, end).trim()
    }

    static String parseModel(String line) {
        int start = line.indexOf(MODEL_INDICATOR) + MODEL_INDICATOR.length()
        int end = line.indexOf(DEVICE_INDICATOR) - 1
        if (end < 0) {
            end = line.length()
        }
        line.substring(start, end).trim()
    }

    static String parseUsb(String line) {
        if (!line.contains(DEVICE_USB_INDICATOR)) {
            return ""
        }
        int start = line.indexOf(DEVICE_USB_INDICATOR) + DEVICE_USB_INDICATOR.length()
        int end = line.indexOf(PRODUCT_INDICATOR) - 1
        if (end < 0) {
            end = line.length()
        }
        line.substring(start, end).trim()
    }

    static String parseId(String line) {
        int end = line.indexOf(DEVICE_DEVICE_INDICATOR) - 1
        if (end < 0) {
            end = line.length()
        }
        line.substring(0, end).trim()
    }

    static ArrayList<Process> parseProcessList(String result) {
        String[] lines = result.split("\n\n")
        ArrayList<Process> processes = new ArrayList<>();
        String line
        String[] pre
        for (int i = 1; i < lines.length; i++) {
            if (lines[i].contains(".")) {
                line = lines[i].replaceAll("\\s+", " ")
                pre = line.split(" ")
                Process process = new Process("user": pre[0], "pid": pre[1], "ppid": pre[2], "vSize": pre[3],
                        "rss": pre[4], "wChan": pre[5], "name": pre[8])
                processes.add(process)
            }
        }
        processes
    }
}

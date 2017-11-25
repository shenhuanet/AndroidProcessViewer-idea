package com.shenhua.idea.plugin.processviewer.bean

/**
 * Created by shenhua on 2017-11-24-0024.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class Device {

    String deviceName
    String modelName
    String productName
    String ip
    boolean isOnline

    Device(String deviceName, boolean isOnline) {
        this.deviceName = deviceName
        this.isOnline = isOnline
    }

    @Override
    boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        Device device = obj as Device
        return ip.equals(device.ip)
    }

    @Override
    int hashCode() {
        return ip.hashCode()
    }

    @Override
    String toString() {
        String suf = ""
        if (!isOnline) {
            suf = " - (offline)"
        }
        return getDeviceName() + " [" + getIp() + "] " + suf
    }
}

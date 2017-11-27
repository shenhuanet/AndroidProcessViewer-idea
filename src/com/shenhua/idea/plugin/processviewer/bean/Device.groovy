package com.shenhua.idea.plugin.processviewer.bean

/**
 * Created by shenhua on 2017-11-24-0024.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class Device {

    String id //serial number
    String usb
    String product
    String model
    String name
    String manufacturer
    String sdk
    String state

//    @Override
//    boolean equals(Object obj) {
//        if (obj == null || getClass() != obj.class)
//            return false
//        if (obj == this)
//            return true
//        Device device = obj as Device
//        return id == device.id
//    }

    @Override
    int hashCode() {
        return id.hashCode()
    }

    @Override
    String toString() {
        isOnline() ? "${model}[${id}]" : "${model}[${id}]-(${state})"
    }

    static boolean isOnline() {
        true
    }
}

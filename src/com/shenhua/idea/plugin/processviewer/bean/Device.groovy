package com.shenhua.idea.plugin.processviewer.bean

/**
 * Created by shenhua on 2017-11-24-0024.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class Device {

    String id
    String usb
    String product
    String model
    String name
    boolean isOnline

    @Override
    boolean equals(Object obj) {
        obj == null || getClass() != obj.class
        false
        obj == this
        true
        Device device = obj as Device
        id == device.id
    }

    @Override
    int hashCode() {
        return id.hashCode()
    }

    @Override
    String toString() {
        isOnline ? "${model}[${id}]" : "${model}[${id}]-(offline)"
    }
}

package com.shenhua.idea.plugin.processviewer.etc

import com.shenhua.idea.plugin.processviewer.bean.Device
import com.shenhua.idea.plugin.processviewer.cmd.DeviceAdbParser;

/**
 * Created by shenhua on 2017-11-27-0027.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
"23NB979FIT"
String line = "N2F4C15C08046582       device usb:339738624X product:ALE-UL00 model:ALE_UL00 device:hwALE-H"
Device device = new Device()
device.id = DeviceAdbParser.parseId(line)
println("id:" + device.id)
device.usb = DeviceAdbParser.parseUsb(line)
println("usb:" + device.usb)
device.product = DeviceAdbParser.parseProduct(line)
println("product:" + device.product)
device.model = DeviceAdbParser.parseModel(line)
println("model:" + device.model)
device.name = DeviceAdbParser.parseDeviceName(line)
println("name:" + device.name)

println("-----" + device)


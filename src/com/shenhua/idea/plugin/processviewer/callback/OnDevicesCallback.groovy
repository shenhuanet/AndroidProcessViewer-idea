package com.shenhua.idea.plugin.processviewer.callback

import com.android.ddmlib.IDevice

/**
 * Created by shenhua on 2017/11/27.
 * Email shenhuanet@126.com
 *
 * @author shenhua
 */
interface OnDevicesCallback {

    /**
     * Called when all available devices changes.
     *
     * @param devices the connected devices.
     */
    void onObtainDevices(ArrayList<IDevice> devices)

    /**
     * Call when the ComboBox list selected.
     */
    void onDeviceSelected(IDevice device)
}
package com.shenhua.idea.plugin.processviewer.etc

import com.android.ddmlib.IDevice
import com.android.tools.idea.ddms.DevicePropertyUtil
import com.intellij.openapi.actionSystem.ActionToolbar
import com.intellij.ui.ColoredListCellRenderer
import com.intellij.ui.SimpleTextAttributes
import com.shenhua.idea.plugin.processviewer.etc.events.RefreshEvent
import com.shenhua.idea.plugin.processviewer.etc.events.StopProcessEvent
import org.jetbrains.annotations.NotNull

import javax.swing.JList

/**
 * Created by shenhua on 2017-12-01-0001.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class ComboBoxRenderer extends ColoredListCellRenderer<IDevice> {

    private ActionToolbar mToolbar

    ComboBoxRenderer(ActionToolbar mToolbar) {
        this.mToolbar = mToolbar
    }

    @Override
    protected void customizeCellRenderer(
            @NotNull JList<? extends IDevice> jList, IDevice device, int index, boolean selected, boolean hasFocus) {
        if (device == null) {
            BusProvider.get().post(new RefreshEvent(null))
            BusProvider.get().post(new StopProcessEvent(null))
            append("No Connected Device", SimpleTextAttributes.ERROR_ATTRIBUTES)
        } else {
            String name
            if (device.isEmulator()) {
                String avdName = device.getAvdName()
                if (avdName == null) {
                    avdName = "unknown"
                }
                name = " Emulator ${avdName}"
            } else {
                name = " ${DevicePropertyUtil.getModel(device, "")} "
            }
            append(device.getSerialNumber(), SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES)
            append(" ${DevicePropertyUtil.getManufacturer(device, "")} ")
            append(name, SimpleTextAttributes.REGULAR_ATTRIBUTES)

            IDevice.DeviceState deviceState = device.getState()
            if (deviceState != IDevice.DeviceState.ONLINE) {
                append("[${device.getState()}]", SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES)
            }
            if (deviceState != IDevice.DeviceState.DISCONNECTED && deviceState != IDevice.DeviceState.OFFLINE) {
                append(DevicePropertyUtil.getBuild(device), SimpleTextAttributes.GRAY_ATTRIBUTES)
            }
        }
    }
}

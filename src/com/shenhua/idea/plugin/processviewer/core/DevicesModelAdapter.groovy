package com.shenhua.idea.plugin.processviewer.core

import com.shenhua.idea.plugin.processviewer.bean.Device

import javax.swing.ComboBoxModel
import javax.swing.event.ListDataListener

/**
 * Created by shenhua on 2017-11-24-0024.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class DevicesModelAdapter implements ComboBoxModel<Device> {

    private ArrayList<Device> devices
    private Device device

    DevicesModelAdapter(ArrayList<Device> devices) {
        this.devices = devices
    }

    @Override
    void setSelectedItem(Object anItem) {
        device = anItem as Device
    }

    @Override
    Object getSelectedItem() {
        device == null ? devices.get(0) : device
    }

    @Override
    int getSize() {
        devices == null ? 0 : devices.size()
    }

    @Override
    Device getElementAt(int index) {
        devices.get(index)
    }

    @Override
    void addListDataListener(ListDataListener l) {
    }

    @Override
    void removeListDataListener(ListDataListener l) {
    }
}

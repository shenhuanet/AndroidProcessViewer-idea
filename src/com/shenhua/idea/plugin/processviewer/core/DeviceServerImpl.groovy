package com.shenhua.idea.plugin.processviewer.core

import com.android.ddmlib.AndroidDebugBridge
import com.android.ddmlib.Client
import com.android.ddmlib.IDevice
import com.android.tools.idea.ddms.DeviceContext
import com.android.tools.idea.ddms.DeviceContext.DeviceSelectionListener
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.util.ui.UIUtil
import com.shenhua.idea.plugin.processviewer.callback.OnDevicesCallback
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

/**
 * Created by shenhua on 2017/11/27.
 * Email shenhuanet@126.com
 *
 * @author shenhua
 */
class DeviceServerImpl implements DeviceServer, Disposable, AndroidDebugBridge.IClientChangeListener,
        AndroidDebugBridge.IDeviceChangeListener, AndroidDebugBridge.IDebugBridgeChangeListener {

    private Project mProject
    private DeviceContext mDeviceContext
    private OnDevicesCallback mDevicesCallback
    private DeviceSelectionListener mDeviceSelectionListener
    private AndroidDebugBridge mBridge

    DeviceServerImpl(Project mProject, DeviceContext mDeviceContext, OnDevicesCallback mDevicesCallback) {
        this.mProject = mProject
        this.mDeviceContext = mDeviceContext
        this.mDevicesCallback = mDevicesCallback
    }

    @Override
    void start() {
        // DeviceContext DeviceSelectionListener
        mDeviceSelectionListener = new DeviceSelectionListener() {
            @Override
            void deviceSelected(@Nullable IDevice iDevice) {
                notifyDeviceUpdated(iDevice)
            }

            @Override
            void deviceChanged(@NotNull IDevice iDevice, int i) {
                if ((i & IDevice.CHANGE_STATE) == IDevice.CHANGE_STATE) {
                    notifyDeviceUpdated(iDevice);
                }
            }

            @Override
            void clientSelected(@Nullable Client client) {

            }
        }
        mDeviceContext.addListener(mDeviceSelectionListener, this)
        AndroidDebugBridge.addClientChangeListener(this)
        AndroidDebugBridge.addDeviceChangeListener(this)
        AndroidDebugBridge.addDebugBridgeChangeListener(this)
    }

    private void updateDevice() {
        if (mBridge == null) {
            return;
        }
        ArrayList<IDevice> devices = new ArrayList<>()
        devices.addAll(Arrays.asList(mBridge.getDevices()))
        mDevicesCallback.onObtainDevices(devices)
    }

    private void notifyDeviceUpdated(IDevice device) {
        UIUtil.invokeAndWaitIfNeeded({
            if (mProject.isDisposed()) {
                return
            }
            if (device != null && mDevicesCallback != null) {
                mDevicesCallback.onDeviceSelected()
            }
        });
    }

    @Override
    void dispose() {
        AndroidDebugBridge.removeClientChangeListener(this)
        if (mBridge != null) {
            AndroidDebugBridge.removeDeviceChangeListener(this)
            AndroidDebugBridge.removeDebugBridgeChangeListener(this)
        }
    }

    @Override
    void clientChanged(Client client, int i) {

    }

    @Override
    void bridgeChanged(AndroidDebugBridge androidDebugBridge) {
        mBridge = androidDebugBridge
        updateDevice()
    }

    @Override
    void deviceConnected(IDevice iDevice) {
        updateDevice()
    }

    @Override
    void deviceDisconnected(IDevice iDevice) {
        updateDevice()
    }

    @Override
    void deviceChanged(IDevice iDevice, int i) {
        if ((i & IDevice.CHANGE_STATE) != 0) {
            updateDevice()
        }
        if (iDevice != null) {
            mDeviceSelectionListener.deviceChanged(iDevice, i)
        }
    }
}

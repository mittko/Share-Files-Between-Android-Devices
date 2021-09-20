/*
package com.wifi.movecopydata.sharewifiles3.reveivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import DeviceReceiver;

import java.lang.ref.WeakReference;

import static Konstants.DEVICE_IP;
import static Konstants.DEVICE_NAME;
import static Konstants.DEVICE_RECEIVER_PORT;
import static Konstants.SEND_ACTION;

public class PeerReceiver extends BroadcastReceiver {

    private WeakReference<Context> weakReference;
    public PeerReceiver(Context context) {
        this.weakReference = new WeakReference<>(context);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action != null) {
            if (action.equals(SEND_ACTION)) {
                String device = intent.getStringExtra(DEVICE_NAME);
                String ip = intent.getStringExtra(DEVICE_IP);
                String  port = intent.getStringExtra(DEVICE_RECEIVER_PORT);
                DeviceReceiver deviceReceiver =
                        (DeviceReceiver)weakReference.get();
                deviceReceiver.addDevice(device, ip,port);
            }
        }
    }
}
*/

/*
package com.wifi.movecopydata.sharewifiles3.reveivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.wifi.movecopydata.sharewifiles3.mcst.MulticastListener;
import MyConsole;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

import static Konstants.LISTENING_PORT;
import static Konstants.MULTICAST_GROUP_ADDRESS;

public class WifiConnectionReceiver extends BroadcastReceiver {
    private MulticastSocket multicastSocketListener;
    private MulticastListener multicastListener = null;
    private InetAddress inetAddress = null;
    private WifiManager.MulticastLock multicastLock;
    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if(action != null && action.equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            boolean connected = info != null &&  info.isConnected()
                    && info.getType() == ConnectivityManager.TYPE_WIFI;
            if(connected) {
                joinToGroup(context);
            }
        }
    }
    private void joinToGroup(Context context) {
        try {
            if(inetAddress == null) {
                lockMulticastLock(context);
                inetAddress = InetAddress.getByName(MULTICAST_GROUP_ADDRESS);
                multicastSocketListener = new MulticastSocket(LISTENING_PORT);
                multicastSocketListener.joinGroup(inetAddress);
                multicastSocketListener.setSoTimeout(3000);// ????
                multicastListener =
                        new MulticastListener(multicastSocketListener,
                                context.getApplicationContext());
                multicastListener.start();
           //     MyDialog.showDialog("JOINED",context);
            }
        } catch (IOException e) {
            MyConsole.println("from WificonnectionReceiver(JoinToGroup) " + e.getMessage());
        }
    }

    public void leaveFromGroup() {
        try {
            if( inetAddress != null) {
                multicastSocketListener.leaveGroup(inetAddress);
                multicastSocketListener.disconnect();
                multicastSocketListener.close();
                unlockMulticast();
                multicastListener.interrupt();
           //     MyDialog.showDialog("LEAVE", context);
            }
        } catch (IOException e) {
            MyConsole.println("from WificonnectionReceiver "+e.getMessage());
        }

    }

    private void lockMulticastLock(Context context) {
        // Acquire multicast lock
        WifiManager wifi = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        multicastLock = wifi.createMulticastLock("multicastLock");
        multicastLock.setReferenceCounted(true);
        multicastLock.acquire();
    }
    private void unlockMulticast() {
        // Once your finish using it, release multicast lock
        if (multicastLock != null) {
            multicastLock.release();
            multicastLock = null;
        }
    }
}
*/

package com.sendshare.movecopydata.wififiletransfer.threads;

import android.content.Context;

import com.sendshare.movecopydata.wififiletransfer.activities.FileTransferActivity2;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyConsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SayHelloThread extends Thread {
    private String netIp;
    private int port;
    private WeakReference<Context> contextWeakReference;
    public SayHelloThread(String netIp, int port, Context context) {
        this.netIp = netIp;
        this.port = port;
        this.contextWeakReference = new WeakReference<>(context);
    }
    @Override
    public void run() {
        super.run();
         int timeout = 1000;
            for(int i = 1;!isInterrupted() && i <= 255;i++) {
                BufferedReader bufferedReader = null;
                Socket socket1 = null;
                try {
                  
                    // If you want to let the socket abort after a certain amount of time you have to do this:
                    InetSocketAddress inetAddress = new InetSocketAddress(netIp+"." + i, port);
                    socket1 = new Socket();
                    socket1.connect(inetAddress, timeout);
                    // DO NOT REMOVE THIS TEXT !!!
                    // Otherways the socket will hang before you set the soTimeout.
                    // The connect method will throw an SocketTimeoutException if the remote host is not reachable.

                 //   MyConsole.println("host = "+socket1.getInetAddress());

                    if(socket1.getInputStream() != null) {
                        bufferedReader =
                                new BufferedReader(new InputStreamReader(socket1.getInputStream()));
                        String address = bufferedReader.readLine();
                        String deviceName = bufferedReader.readLine();
                        if (address != null) {
                                ((FileTransferActivity2)contextWeakReference.get())
                                        .addPeerFromAnotherThread(address,deviceName);
                        }
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                       MyConsole.println(e.getMessage());
                    }
                } catch (IOException e) {
                    MyConsole.println("fuck off " + e.getMessage());
                } finally {
                    try {
                        if (bufferedReader != null)
                            bufferedReader.close();
                        if (socket1 != null)
                            socket1.close();
                    } catch (IOException e) {
                        MyConsole.println(e.getMessage());
                    }
                }
            }
    }


}

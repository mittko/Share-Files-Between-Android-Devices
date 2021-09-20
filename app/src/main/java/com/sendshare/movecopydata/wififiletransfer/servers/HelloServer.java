package com.sendshare.movecopydata.wififiletransfer.servers;

import android.content.Context;
import android.os.Build;

import com.sendshare.movecopydata.wififiletransfer.utilities.Konstants;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyConsole;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyUtility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HelloServer extends Thread {

    private static ServerSocket serverSocket;
    private Context context;
    public static int getServerPort() {
        return serverSocket.getLocalPort();
    }
    public HelloServer(Context context) {
        try {
            serverSocket = new ServerSocket(Konstants.HELLO_SERVER_PORT);//(0);
            this.context = context;
        } catch (IOException e) {
            MyConsole.println("from HelloServer "+e.getMessage());
        }
    }
    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {
            try {
                Socket socket = serverSocket.accept();
                synchronized (this) {
                    if(socket != null && socket.getOutputStream() != null)
                    sayHello(socket.getOutputStream());
                }
            } catch (IOException e) {
                MyConsole.println("from HelloServer "+e.getMessage());
            }

        }
    }

    @Override
    public void interrupt() {
        //  interruptThreads();

        // За да прекъсна нишката трябва да затворя сокета
        try {
            if(serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
            MyConsole.println(e.getMessage());
        }
        super.interrupt();
    }




    private void sayHello(OutputStream outputStream)  {
        PrintWriter printWriter = new PrintWriter(outputStream,true);
        //   BufferedWriter bufferedWriter = new BufferedWriter(printWriter);
        printWriter.write(MyUtility.getWifi_IPAddress(context)+":"+ Konstants.SERVER_PORT+"\n");
        printWriter.write( Build.BRAND + " " + Build.MODEL+"\n");
        printWriter.flush();
        //  //  bufferedWriter.flush();
        //    printWriter.close();
    }
}

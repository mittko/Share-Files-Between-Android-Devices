package com.sendshare.movecopydata.wififiletransfer.servers;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.widget.LinearLayout;

import com.sendshare.movecopydata.wififiletransfer.activities.DataReceiverActivity;
import com.sendshare.movecopydata.wififiletransfer.threads.FileReceiverThreadDefaultStorage;
import com.sendshare.movecopydata.wififiletransfer.threads.FileReceiverThreadSelectedStorage;
import com.sendshare.movecopydata.wififiletransfer.ui.LinearCustomView;
import com.sendshare.movecopydata.wififiletransfer.utilities.FileManager;
import com.sendshare.movecopydata.wififiletransfer.utilities.Konstants;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyConsole;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyUtility;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MyServer extends Thread {

    private static ServerSocket serverSocket;


 //   private Context context;
    private ArrayList<Thread> threads = new ArrayList<>();

    private WeakReference<Context> weakReference;

    public static int getServerPort() {
        return serverSocket.getLocalPort();
    }
    public MyServer(Context context1) {
        this.weakReference = new WeakReference<>(context1);
        try {
            serverSocket = new ServerSocket(Konstants.SERVER_PORT);//(0);
        } catch (IOException e) {
           MyConsole.println("from MyServer "+e.getMessage());
        }
    }

    private int addedView = 0;
    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {
            try {
                Socket socket = null;
                synchronized (this) {
                    socket = serverSocket.accept();

                    LinearCustomView linearCustomView = addCustomView("fak you");
                    ((DataReceiverActivity)weakReference.get()).addViewFromAnotherThread(linearCustomView, ++addedView);

                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                        FileReceiverThreadDefaultStorage
                                fileReceiverThreadDefaultStorage =
                                new FileReceiverThreadDefaultStorage(weakReference, socket,linearCustomView,addedView);
                        fileReceiverThreadDefaultStorage.start();
                        threads.add(fileReceiverThreadDefaultStorage);

                    } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Uri persistableUri = FileManager.getUserUri(weakReference.get());// (ApplicationContext works !!);
                        if (persistableUri == null || persistableUri.toString().equals("default")) {
                            FileReceiverThreadDefaultStorage
                                    fileReceiverThreadDefaultStorage =
                                    new FileReceiverThreadDefaultStorage(weakReference, socket,linearCustomView,addedView);
                            fileReceiverThreadDefaultStorage.start();
                            threads.add(fileReceiverThreadDefaultStorage);

                        } else {
                            FileReceiverThreadSelectedStorage fileReceiverThreadSelectedStorage =
                                    new FileReceiverThreadSelectedStorage(weakReference, socket, persistableUri,
                                            linearCustomView,addedView);
                            fileReceiverThreadSelectedStorage.start();
                            threads.add(fileReceiverThreadSelectedStorage);
                        }

                    }
                }

            } catch (IOException e) {
                MyConsole.println("from MySever "+e.getMessage());
            }
        }
    }
    @Override
    public void interrupt() {
        interruptThreads();
     //   cancelTasks();

        // За да прекъсна нишката трябва да затворя сокета
        try {
            if(serverSocket != null)
            serverSocket.close();
        } catch (IOException e) {
          MyConsole.println(e.getMessage());
        }
        super.interrupt();
    }
    public boolean tasksCompleted() {
        boolean completed = true;
        for(Thread thread : threads) {
            if(thread instanceof FileReceiverThreadDefaultStorage) {
                FileReceiverThreadDefaultStorage fileReceiverThreadDefaultStorage =
                        (FileReceiverThreadDefaultStorage) thread;
                if(!fileReceiverThreadDefaultStorage.taskCompleted()) {
                    completed = false;
                    break;
                }
            } else if(thread instanceof FileReceiverThreadSelectedStorage) {
                FileReceiverThreadSelectedStorage fileReceiverThreadSdCard =
                        (FileReceiverThreadSelectedStorage) thread;
                if(!fileReceiverThreadSdCard.taskCompleted()) {
                    completed = false;
                    break;
                }
            }
        }
        return completed;
    }

    private void interruptThreads() {
        for(Thread thread : threads) {
            thread.interrupt();
        }
        threads.clear();
    }
    private LinearCustomView addCustomView(String fileName) {
        int viewHeight =
                (int)( MyUtility.getHeightInPixels((DataReceiverActivity)weakReference.get())/12);
        int viewWidth =
                (int)(MyUtility.getWidthInPixels((DataReceiverActivity)weakReference.get()));
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(viewWidth,
                        viewHeight);
        final LinearCustomView linearCustomView = new LinearCustomView((DataReceiverActivity)weakReference.get(),viewWidth,viewHeight);
       /* customView.setWIDTH(viewWidth);
        customView.setHEIGHT(viewHeight);*/
        linearCustomView.setLayoutParams(layoutParams);
        return linearCustomView;
    }

}

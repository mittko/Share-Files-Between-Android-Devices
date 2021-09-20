/*
package com.sendshare.movecopydata.wififiletransfer.tasks;

import android.os.AsyncTask;

import com.sendshare.movecopydata.wififiletransfer.interfaces.VisualizeDataTransfer;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyConsole;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FileTransferTask extends AsyncTask<Void,Double,Void>  {
    private String hostIp;
    private int port;
    private String filePath;
    private VisualizeDataTransfer visualizeDataTransfer;

    public FileTransferTask(String hostIp, int port,
                            String filePath, VisualizeDataTransfer visualizeDataTransfer) {
        this.hostIp = hostIp;
        this.port = port;
        this.filePath = filePath;
        this.visualizeDataTransfer = visualizeDataTransfer;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        Socket socket = null;
        // socket.setSoTimeout() wrong result in program !!!!
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            socket = new Socket(this.hostIp,this.port);

                File file = new File(this.filePath);
           //     String mimeType = FileManager.getMimeType(this.filePath);
           //     MyConsole.println("MIME  ->"+mimeType);

            //    MyConsole.println("AAAAAAAAAAAAAAAAAAAAAAAa");
           //     MyConsole.println("FILE PATH = " + this.filePath);
                inputStream = new FileInputStream(file);
          //      MyConsole.println("BBBBBBBBBBBBBBBBBBBBBBBBBBbb");
                outputStream = socket.getOutputStream();// new FileOutputStream(file1);//
            //    MyConsole.println("CCCCCCCCCCCCCCCCCCCCc");

                byte[] byteFromString = this.filePath.getBytes();
            //    MyConsole.println("LENGHT = "+byteFromString.length);
                outputStream.write(byteFromString.length);
                outputStream.write(byteFromString);
                outputStream.flush();
            //    MyConsole.println("DDDDDDDDDDDDDD");
                int count;
                long total = 0;
                long lengthOfFile = file.length();
                byte[] bytes = new byte[1000*1024];//[1000*1024];
                while ((count = inputStream.read(bytes)) > 0) {
               //     MyConsole.println("EEEEEEEEEEEEEEEEE");
                    outputStream.write(bytes, 0, count);
                //    MyConsole.println("FFFFFFFFFFFFFFFFFF");
                    total += count;
                    publishProgress(   ( (double)total  / (double)lengthOfFile));


                    if(isCancelled()) break;// if Task is cancelled though dialog button
                }
                outputStream.flush();

        } catch (IOException e) {
               MyConsole.println("from FileTransferTask " + e.getMessage());
               visualizeDataTransfer.handleConnectionError("failed to connect to " + hostIp);
        } finally {
            try {
                if(outputStream != null)
                    outputStream.close();
                if(inputStream != null)
                    inputStream.close();
                if(socket != null)
                    socket.close();
            } catch (IOException e) {
                MyConsole.println("from FileTransferTask " + e.getMessage());
            }

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        visualizeDataTransfer = null;
    }

    @Override
    protected void onProgressUpdate(Double... values) {
        super.onProgressUpdate(values);
        visualizeDataTransfer.visualize(values[0]);
    }
}
*/

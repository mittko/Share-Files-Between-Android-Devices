/*
package com.sendshare.movecopydata.wififiletransfer.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.sendshare.movecopydata.wififiletransfer.interfaces.VisualizeDataTransfer;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyConsole;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class FileTransferTask2 extends AsyncTask<Void,Double,Void>  {
    private String hostIp;
    private int port;
    private String filePath;
    private VisualizeDataTransfer visualizeDataTransfer1;
    private  VisualizeDataTransfer visualizeDataTransfer2;
    private long ALL_BYTES_TO_SEND;

    public FileTransferTask2(Context context,String hostIp, int port,
                             String filePath,long allBytesToSend,  VisualizeDataTransfer visualizeDataTransfer1,
                             VisualizeDataTransfer visualizeDataTransfer2) {
        this.hostIp = hostIp;
        this.port = port;
        this.filePath = filePath;
        ALL_BYTES_TO_SEND = allBytesToSend;
        this.visualizeDataTransfer1 = visualizeDataTransfer1;
        this.visualizeDataTransfer2 = visualizeDataTransfer2;
    }

    long total = 0;
    long fileSize = 0;
    @Override
    protected Void doInBackground(Void... voids) {
        Socket socket = null;
        // socket.setSoTimeout() wrong result in program !!!!
        InputStream inputStream = null;
        DataOutputStream dataOutputStream = null;
        try {
            socket = new Socket(this.hostIp,this.port);

                File file = new File(this.filePath);
           //     String mimeType = FileManager.getMimeType(this.filePath);
           //     MyConsole.println("MIME  ->"+mimeType);

                inputStream = (new FileInputStream(file));

                dataOutputStream = new DataOutputStream((socket.getOutputStream()));

                fileSize = file.length();

                sendFileInformation(dataOutputStream, filePath, fileSize);

                int count;

                byte[] bytes = new byte[getBufferSize(fileSize)];

                while ((count = inputStream.read(bytes)) > 0) {
                   dataOutputStream.write(bytes, 0, count);
                   total += count;
               // only for one file     publishProgress(   ( (double)total  / (double)lengthOfFile));

                    publishProgress((double) count, (double) ALL_BYTES_TO_SEND, // for all files
                                (double) total, (double) fileSize);// for one file

                    if(isCancelled()) break;// if Task is cancelled though dialog button
                }
                dataOutputStream.flush();
                // END OF SPEED

        } catch (IOException e) {
               MyConsole.println("from FileTransferTask " + e.getMessage());
               visualizeDataTransfer2.handleConnectionError("failed to connect to " + hostIp);
        } catch (Exception e) {
               MyConsole.println("from FileTransferTask " + e.getMessage());
        } finally {
            try {
                if(dataOutputStream != null)
                    dataOutputStream.close();
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

    private void sendFileInformation(DataOutputStream dataOutputStream
                                     ,String filePath, long fileSize) throws Exception {
        byte[] byteFromString = filePath.getBytes();
        dataOutputStream.write(byteFromString.length);
        dataOutputStream.write(byteFromString);
        dataOutputStream.writeLong(fileSize);
        dataOutputStream.flush();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        visualizeDataTransfer1 = null;
        visualizeDataTransfer2 = null;
    }

    @Override
    protected void onProgressUpdate(Double... values) {
        super.onProgressUpdate(values);
        if(visualizeDataTransfer1 != null) {
            visualizeDataTransfer1.visualize(values[0], values[1]);
        }
        if(visualizeDataTransfer2 != null) {
            visualizeDataTransfer2.visualize(values[2], values[3]);
        }
    }

    private int getBufferSize(long fileLength) {
        int MAX_BUFFER_SIZE = 1024 * 1024;
         return fileLength > MAX_BUFFER_SIZE ? MAX_BUFFER_SIZE : (int)fileLength;
    }

    public boolean taskCompleted() {
        return  total >= fileSize;
    }

}
*/

/*
package com.sendshare.movecopydata.wififiletransfer.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.sendshare.movecopydata.wififiletransfer.activities.DataReceiverActivity;
import com.sendshare.movecopydata.wififiletransfer.interfaces.VisualizeDataTransfer;
import com.sendshare.movecopydata.wififiletransfer.utilities.FileManager;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyConsole;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyUtility;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.net.URISyntaxException;

import static com.sendshare.movecopydata.wififiletransfer.utilities.Konstants.APP_FOLDER;

public class FileReceiverTaskExternalStorage extends AsyncTask<Void, Object, String> {

    private WeakReference<Context> weakReference;
    private Socket socket;
    private File dir;
    private OutputStream outputStream = null;
    private byte[] bytes1 = null;

    private VisualizeDataTransfer visualizeDataTransfer1;
    private int addedView;
    private DataInputStream dataInputStream;
    private String shrinkName = "";
    private String pathToFile = null;
    public FileReceiverTaskExternalStorage(WeakReference<Context> weakReference, Socket socket,
                                           VisualizeDataTransfer visualizeDataTransfer1, int addedView) {
        this.weakReference = weakReference;
        this.socket = socket;
        File root = Environment.getExternalStorageDirectory();
        dir = new File(root.getAbsolutePath() + File.separator +  APP_FOLDER);
        if(!dir.exists()) {
            dir.mkdir();
        }
        this.visualizeDataTransfer1 = visualizeDataTransfer1;
        this.addedView = addedView;
    }

    long total = 0;
    long sizeOfFile = 0;
    @Override
    protected String doInBackground(Void... voids) {
        try {
            dataInputStream = new DataInputStream((socket.getInputStream()));
            int bytesFromStringLength = dataInputStream.read();
            byte[] bytes = new byte[bytesFromStringLength];
            int count = dataInputStream.read(bytes);
            String filePath = new String(bytes,0,count);
            int index = filePath.lastIndexOf("/");
            sizeOfFile = dataInputStream.readLong();


            if(index != -1) {
                String name = filePath.substring(index + 1);
                shrinkName = MyUtility.shrinkString(name);
                File file = new File(dir, name);
                pathToFile = file.getAbsolutePath();
                if(file.exists()) {
                    file.delete();
                    file = new File(dir, MyUtility.getRandomString()+name);
                    pathToFile = file.getAbsolutePath();
                }
                outputStream = new FileOutputStream(file);
                int count1;
                total = 0;
                bytes1 = new byte[getBufferSize(sizeOfFile)];
                while ((count1 = dataInputStream.read(bytes1)) > 0) {
                    outputStream.write(bytes1, 0, count1);
                    total += count1;
                    publishProgress( (double)total, (double)sizeOfFile, shrinkName);

                    if(isCancelled()) break;
                }
                outputStream.flush();

                // refresh media in db
                FileManager.refreshMediaStoreDB(weakReference.get(),file.getAbsolutePath());
            } else {
                MyConsole.println("Wrong file path !");
            }
        } catch (IOException e) {
            MyConsole.println(e.getMessage()+"");
        } finally {
            try {
                if(dataInputStream != null) {
                    dataInputStream.close();
                }
                if(outputStream != null) {
                    outputStream.close();
                }
                if(socket != null) {
                    socket.close();
                }
            }catch (IOException e) {
                MyConsole.println(e.getMessage()+"");
            }
        }

        return pathToFile;
    }

    @Override
    protected void onPostExecute(String filePath) {
        super.onPostExecute(filePath);
        visualizeDataTransfer1 = null;
        try {
          //  Uri.fromFile(file) or Uri.parse(pathToFile);
            ((DataReceiverActivity)weakReference.get()).showDrawable(addedView, filePath);
        } catch (URISyntaxException e) {
            MyConsole.println("FUCK OFF EXTERNAL STORAGE" + e.getMessage());
        }
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
        visualizeDataTransfer1.showFileName((String)values[2]);
        visualizeDataTransfer1.visualize((double)values[0],(double)values[1]);
    }

    private int getBufferSize(long fileLength) {
        int MAX_BUFFER_SIZE = 1024 * 1024;
        return fileLength > MAX_BUFFER_SIZE ? MAX_BUFFER_SIZE : (int)fileLength;
    }

    public boolean taskCompleted() {
        return total >= sizeOfFile;
    }
}*/

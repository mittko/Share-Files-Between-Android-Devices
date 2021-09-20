package com.sendshare.movecopydata.wififiletransfer.threads;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.sendshare.movecopydata.wififiletransfer.activities.DataReceiverActivity;
import com.sendshare.movecopydata.wififiletransfer.interfaces.VisualizeDataTransfer;
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

public class FileReceiverThreadDefaultStorage extends Thread {

        private WeakReference<Context> weakReference;
        private Socket socket;
        private File dir;
        private OutputStream outputStream = null;
       // private Uri uri;
        private VisualizeDataTransfer visualizeDataTransfer1;
        private int addedView;
        private DataInputStream dataInputStream;
        private String pathToFile = null;
        public FileReceiverThreadDefaultStorage(WeakReference<Context> weakReference, Socket socket,
                                                VisualizeDataTransfer visualizeDataTransfer1, int addedView) {
            this.weakReference = weakReference;
            this.socket = socket;
       
            File root = ContextCompat.getExternalFilesDirs(this.weakReference.get(),null)[0];
            String path = root.getAbsolutePath();
            int index = path.toUpperCase().indexOf("/Android".toUpperCase());

            dir = new File( path.substring(0,index+1) + File.separator +  APP_FOLDER);
            if(!dir.exists()) {
                dir.mkdirs();
            }
            this.visualizeDataTransfer1 = visualizeDataTransfer1;
            this.addedView = addedView;
        }

        long total = 0;
        long sizeOfFile = 0;

        @Override
        public void run() {
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
                    String shrinkName = MyUtility.shrinkString(name);
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
                    byte[] bytes1 = new byte[getBufferSize(sizeOfFile)];
                    while ((count1 = dataInputStream.read(bytes1)) > 0) {
                        outputStream.write(bytes1, 0, count1);
                        total += count1;
                        onProgressUpdate( (double)total, (double)sizeOfFile, shrinkName);
                        if(isInterrupted()) break;
                    }
                    outputStream.flush();
                    // refresh media in db NO
              //      FileManager.refreshMediaStoreDB(weakReference.get(),file.getAbsolutePath());
                    // no need for FileManager.refreshMediaStoreDB(context.get(), path);
                    // just pass right path to file !!!!
                    onPostExecute(pathToFile);
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
        }
        public boolean taskCompleted() {
        return total >= sizeOfFile;
    }

        protected void onPostExecute(final String filePath) {
            ( (DataReceiverActivity)weakReference.get()).runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            visualizeDataTransfer1 = null;
                            try {
                                ((DataReceiverActivity)weakReference.get()).showDrawable(addedView, filePath);
                            } catch (URISyntaxException | IOException e) {
                                MyConsole.println("FUCK OFF EXTERNAL STORAGE" + e.getMessage());
                            }
                        }
                    }
            );
        }
        protected void onProgressUpdate(final Object... values) {
            ((DataReceiverActivity)weakReference.get()).runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            visualizeDataTransfer1.showFileName((String)values[2]);
                            visualizeDataTransfer1.visualize((double)values[0],(double)values[1]);
                        }
                    }
            );
        }

        private int getBufferSize(long fileLength) {
            int MAX_BUFFER_SIZE = 1024 * 1024;
            return fileLength > MAX_BUFFER_SIZE ? MAX_BUFFER_SIZE : (int)fileLength;
        }

    }


/*
package com.sendshare.movecopydata.wififiletransfer.tasks;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.documentfile.provider.DocumentFile;

import com.sendshare.movecopydata.wififiletransfer.activities.DataReceiverActivity;
import com.sendshare.movecopydata.wififiletransfer.interfaces.VisualizeDataTransfer;
import com.sendshare.movecopydata.wififiletransfer.utilities.FileManager;
import com.sendshare.movecopydata.wififiletransfer.utilities.FileUtilsUpdated;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyConsole;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyUtility;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.net.URISyntaxException;

import static com.sendshare.movecopydata.wififiletransfer.utilities.Konstants.APP_FOLDER;

public class FileReceiverTaskUserDirectory extends AsyncTask<Void, Object, Void> {
    private WeakReference<Context> context;
    private Socket socket;
    private DocumentFile pickedDir;
    private DocumentFile directoryToSave;
    private int addedView;
    private VisualizeDataTransfer visualizeDataTransfer;
    private Uri fileUri;
    public FileReceiverTaskUserDirectory(WeakReference<Context> weakReference, Socket socket, Uri persistableUri,
                                         VisualizeDataTransfer visualizeDataTransfer, int addedView) {
        this.context = weakReference;
        this.socket = socket;
        this.visualizeDataTransfer = visualizeDataTransfer;
        this.addedView = addedView;
        createDirectoryToWriteFile(persistableUri);
    }
    private void createDirectoryToWriteFile(Uri persistableUri) {
        try {
            pickedDir = DocumentFile.fromTreeUri(context.get(), persistableUri);
            if(pickedDir != null) {
                directoryToSave = pickedDir.findFile(APP_FOLDER);
                if(directoryToSave == null || !directoryToSave.exists()) {
                    directoryToSave = pickedDir.createDirectory(APP_FOLDER);
                }
            }
        } catch (IllegalArgumentException e) {
            MyConsole.println("from FileReceiverThreadUserDirectory "+e.getMessage());
            //     MyDialog.showDialog(e.getMessage()+"",context);
        }
    }


    long total = 0;
    long fileLength = 0;
    private void writeToFile(InputStream inputStream) {

       */
/* Old solution !!!  DocumentFile pickedDir;
        try {
             pickedDir = DocumentFile.fromTreeUri(context, uri);
        } catch (IllegalArgumentException e) {
            MyConsole.println("from FileReceiverThreadUserDirectory "+e.getMessage());
       //     MyDialog.showDialog(e.getMessage()+"",context);
            return;
        }*//*

        DataInputStream dataInputStream = new DataInputStream((inputStream));
        // Create a new file and write into it
        if(pickedDir != null) {
            int count1;
            OutputStream outputStream = null;
            //  String extension = "";
            String stringFromBytes;
            String name;
            try {

                total = 0;
                int bytesFromStringLength = dataInputStream.read();
                byte[] bytes1 = new byte[bytesFromStringLength];
                count1 = dataInputStream.read(bytes1);
                stringFromBytes = new String(bytes1,0,count1);

                // read file sie in bytes
                fileLength = dataInputStream.readLong();


                int index = stringFromBytes.lastIndexOf("/");
                if(index != -1) {
                    name = stringFromBytes.substring(index + 1);
                    String shrinkName = MyUtility.shrinkString(name);

                    String mimeType = FileManager.getMimeType(stringFromBytes);

                 */
/*  Old solution !!! DocumentFile directoryToSave = pickedDir.findFile(APP_FOLDER);
                    if(directoryToSave == null || !directoryToSave.exists()) {
                            directoryToSave = pickedDir.createDirectory(APP_FOLDER);
                    }*//*

                    if(directoryToSave != null) {

                        DocumentFile newFile = directoryToSave.createFile(mimeType, name);

                        if(newFile != null && newFile.exists()) {
                            newFile.delete();
                            newFile =  directoryToSave.createFile(mimeType, MyUtility.getRandomString() + name);
                        }

                        if (newFile != null) {
                            //          MyConsole.println("PATH "+newFile.getUri().toString());
                            byte[] bytes = new byte[getBufferSize(fileLength)];

                            fileUri = newFile.getUri();
                            outputStream =
                                    context.get().getContentResolver().openOutputStream(fileUri);

                            if(outputStream != null) {
                                int count;
                                while ((count = dataInputStream.read(bytes)) > 0) {
                                    outputStream.write(bytes, 0, count);

                                    total += count;
                                    publishProgress((double) total, (double) fileLength, shrinkName);

                                    if (isCancelled()) break;
                                }
                                outputStream.flush();
                                //    refresh media in db
                               */
/* FileManager.refreshMediaStoreDB(context.get(),
                                        pickedDir.getUri().toString());*//*

                                FileManager.refreshMediaStoreDB(context.get(),
                                        directoryToSave.getUri().toString());
                            }
                          }
                        } else {
                            MyConsole.println("Can not be created file on SD Card!");
                        }
                    } else {
                        MyConsole.println("Can not be created directory on SD Card!");
                    }


            } catch (IOException e) {
                MyConsole.println("from FileReceiverThreadUserDirectory "+ e.getMessage());
            }finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if(dataInputStream != null){
                        dataInputStream.close();
                    }
                    inputStream.close();
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    MyConsole.println("from FileReceiverThreadUserDirectory "+e.getMessage());
                }
            }
        } else {
            MyConsole.println("Directory not selected !");
        }
        return;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
         //   long start = System.currentTimeMillis();
            writeToFile(socket.getInputStream());
         //   long end = System.currentTimeMillis();
         //   MyConsole.println("Elapsed Time = " + ((end-start)/1000.0));
            // Elapsed Time = 8.561  Elapsed Time = 589.083
            // Elapsed Time = 590.284 1.8GB (<- buffered)  Elapsed Time = 588.413
            // with Buffer Elapsed Time = 859.491 mor slowly ???
            */
/*     Version Code 6 ->  writeToFile(sdcardUri,socket.getInputStream());*//*

        } catch (IOException e) {
            MyConsole.println(e.getMessage()+"");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        visualizeDataTransfer = null;
        try {
            final String path = new FileUtilsUpdated(context.get()).getPath(fileUri);
            ((DataReceiverActivity)context.get()).showDrawable(addedView, path);
        } catch (URISyntaxException e) {
            MyConsole.println("FUCK OFF USER DIRECTORY" + e.getMessage());
        }
    }



    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
        visualizeDataTransfer.showFileName((String)values[2]);
        visualizeDataTransfer.visualize((Double)values[0],(Double)values[1]);
    }

    private int getBufferSize(long fileLength) {
        // url = file path or whatever suitable URL you want.
        int MAX_BUFFER_SIZE = 1024 * 1024;
        return fileLength > MAX_BUFFER_SIZE ? MAX_BUFFER_SIZE : (int)fileLength;
    }

    public boolean taskCompleted() {
        return total >= fileLength;
    }
}
*/

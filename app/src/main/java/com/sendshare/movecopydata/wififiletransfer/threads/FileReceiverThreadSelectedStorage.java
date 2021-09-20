package com.sendshare.movecopydata.wififiletransfer.threads;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;

import androidx.annotation.RequiresApi;
import androidx.documentfile.provider.DocumentFile;

import com.sendshare.movecopydata.wififiletransfer.activities.DataReceiverActivity;
import com.sendshare.movecopydata.wififiletransfer.interfaces.VisualizeDataTransfer;
import com.sendshare.movecopydata.wififiletransfer.utilities.FileManager;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyConsole;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyUtility;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.net.URISyntaxException;

import static com.sendshare.movecopydata.wififiletransfer.utilities.Konstants.APP_FOLDER;

public class FileReceiverThreadSelectedStorage extends Thread { // CUSTOM DIRECTORY

        private WeakReference<Context> context;
        private Socket socket;
        private DocumentFile pickedDir;
        private DocumentFile directoryToSave;
        private int addedView;
        private VisualizeDataTransfer visualizeDataTransfer;

        public FileReceiverThreadSelectedStorage(WeakReference<Context> weakReference, Socket socket, Uri persistableUri,
                                                 VisualizeDataTransfer visualizeDataTransfer, int addedView) {
            this.context = weakReference;
            this.socket = socket;
            this.visualizeDataTransfer = visualizeDataTransfer;
            this.addedView = addedView;
            createDirectoryToWriteFile(persistableUri);
        }

        long total = 0;
        long fileLength = 0;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            Looper.prepare();
            try {
                //   long start = System.currentTimeMillis();
                writeToFile(socket.getInputStream());
            } catch (IOException e) {
                MyConsole.println(e.getMessage()+"");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        public boolean taskCompleted() {
        return total >= fileLength;
    }
        protected void onProgressUpdate(final Object... values) {
            ((DataReceiverActivity)context.get()).runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            visualizeDataTransfer.showFileName((String)values[2]);
                            visualizeDataTransfer.visualize((Double)values[0],(Double)values[1]);
                        }
                    }
            );
        }


        private void createDirectoryToWriteFile(Uri persistableUri) {
        try {
            pickedDir = DocumentFile.fromTreeUri(context.get(), persistableUri);

            if(pickedDir != null) {
                directoryToSave = pickedDir.findFile(APP_FOLDER);
                if(directoryToSave == null || !directoryToSave.exists()) {
                    directoryToSave = pickedDir.createDirectory(APP_FOLDER);
                } else {
                    MyConsole.println("Directory to save == null");
                }
            } else {
                MyConsole.println("PICKED DIR IS NULL");
            }
        } catch (IllegalArgumentException e) {
            MyConsole.println("from FileReceiverThreadUserDirectory "+e.getMessage());
            //     MyDialog.showDialog(e.getMessage()+"",context);
        }
    }
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void writeToFile(InputStream inputStream) throws NoSuchFieldException, IOException, IllegalAccessException {

       DataInputStream dataInputStream = new DataInputStream((inputStream));
        String name = null;
        // Create a new file and write into it
        if(pickedDir != null) {
            int count1;
            OutputStream outputStream = null;
            //  String extension = "";
            String stringFromBytes;
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
                    if(directoryToSave != null) {
                        DocumentFile newFile = directoryToSave.createFile(mimeType, name);
                        if(newFile != null && newFile.exists()) {
                            newFile.delete();
                            name = MyUtility.getRandomString() + name;
                            newFile =  directoryToSave.createFile(mimeType, name);
                        }
                        if (newFile != null) {
                            byte[] bytes = new byte[getBufferSize(fileLength)];
                            outputStream =
                                    context.get().getContentResolver().openOutputStream(newFile.getUri());
                            if(outputStream != null) {
                                int count;
                                while ((count = dataInputStream.read(bytes)) > 0) {
                                    outputStream.write(bytes, 0, count);
                                    total += count;
                                    onProgressUpdate((double) total, (double) fileLength, shrinkName);
                                    if (isInterrupted()) break;
                                }
                                outputStream.flush();
                                // refresh media in db NO
                                // no need for FileManager.refreshMediaStoreDB(context.get(), path);
                                // just pass right path to file !!!!
                                String path = FileManager.getRealPath(pickedDir.getUri(),context.get());
                                String finalPath = path+File.separator + APP_FOLDER + File.separator + name;
                                onPostExecute(finalPath);
                        //        MyConsole.println("PATH = " + (path+File.separator+APP_FOLDER+File.separator+name));
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
                    dataInputStream.close();
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
    }

    protected void onPostExecute(final String filePath) {
        ( (DataReceiverActivity)context.get()).runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ((DataReceiverActivity)context.get()).showDrawable(addedView, filePath);
                        } catch (URISyntaxException | IOException e) {
                            MyConsole.println("FUCK OFF EXTERNAL STORAGE" + e.getMessage());
                        }
                    }
                }
        );
    }
        private int getBufferSize(long fileLength) {
            // url = file path or whatever suitable URL you want.
            int MAX_BUFFER_SIZE = 1024 * 1024;
            return fileLength > MAX_BUFFER_SIZE ? MAX_BUFFER_SIZE : (int)fileLength;
        }


    }

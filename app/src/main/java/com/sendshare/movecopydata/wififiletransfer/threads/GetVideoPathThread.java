package com.sendshare.movecopydata.wififiletransfer.threads;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.sendshare.movecopydata.wififiletransfer.utilities.MyConsole;

import java.io.File;
import java.util.ArrayList;

public class GetVideoPathThread extends Thread  {
    private ArrayList<String> videos = new ArrayList<>();
    private String[] proj = {
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME};

    private Context context;
    private String name;
    private int addedView;
    Uri uri;
    public GetVideoPathThread(String name,
            int addedView,Context context) {
        this.name = name;
        this.addedView = addedView;
        this.context = context;
    }



    @Override
    public void run() {
        videos.clear();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        MyConsole.println("NAME = " + name);
   try {
     Cursor videocursor =
             context.getContentResolver().query(
                     uri,
                     proj,
                     MediaStore.Video.Media.DISPLAY_NAME+" =?", new String[]{name},
                     null);
     //context.get().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
     //        proj, null, null, null);
     if (videocursor != null) {
         videocursor.moveToFirst();
         do {
             if (videocursor.getCount() > 0) {
                 int index = videocursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);//(MediaStore.Video.Media.DATA);
                 //  Log.e(" video =>", videocursor.getString(index));
                 try {

                            /* This happens when the user deletes an image file from an file manager,
                                    and if the file manager does not movecopydata proper broadcasts to notify
                            the media scanner, then the thumbnail info of this image would still exist
                            in the provider.*/

                     String filePath = videocursor.getString(index);

                     File file = new File(filePath);

                     Log.e("filepath " , filePath);
                     if (file.exists() && file.length() > 0) {
                         videos.add(filePath);
                     }
                 } catch (IndexOutOfBoundsException e) {
                     MyConsole.println("from GetVideoPathThread " + e.getMessage());
                 }
             } else {
                 Log.e("Cursor","IS NULL");
             }
         } while (videocursor.moveToNext());
         videocursor.close();
      }
     } catch (SecurityException e) {
      Log.e("Sec","urity Exception");
    }
        onPostExecute(videos);
    }


    protected void onPostExecute(ArrayList<String> strings) {


    }
}


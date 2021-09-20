/*
package com.sendshare.movecopydata.wififiletransfer.media;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.sendshare.movecopydata.wififiletransfer.activities.MainActivity;
import com.sendshare.movecopydata.wififiletransfer.interfaces.ListRefresher;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyConsole;
import com.wifi.mitko.sharewifiles3.R;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class GetVideoFiles extends AsyncTask<Void,Void,ArrayList<String>>  {
    private ArrayList<String> videos = new ArrayList<>();
    private String[] proj = { MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.SIZE };

    private WeakReference<Context> context;
    private int from;
    private ListRefresher listRefresher;
    public GetVideoFiles(
            int from,WeakReference<Context> context, ListRefresher listRefresher) {
        this.from = from;
        this.context = context;
        this.listRefresher = listRefresher;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ProgressBar progressBar =
                ( (MainActivity)context.get()).findViewById(R.id.progressbar);
        if(progressBar != null) {
          //  MyConsole.println("VISIBLE");
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected ArrayList<String> doInBackground(Void... voids) {
        videos.clear();

        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        uri = uri.buildUpon().appendQueryParameter("limit",
               from+",20").build();
 try {
     Cursor videocursor =
             (context.get()).getContentResolver().query(
                     uri,
                     proj,
                     null, null,
                     MediaStore.Video.Media.DATE_MODIFIED + " desc");
     //context.get().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
     //        proj, null, null, null);
     if (videocursor != null) {
         videocursor.moveToFirst();
         do {
             if (videocursor.getCount() > 0) {
                 int index = videocursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);//(MediaStore.Video.Media.DATA);
                 //  Log.e(" video =>", videocursor.getString(index));
                 try {
                         */
/* This happens when the user deletes an image file from an file manager,
                                    and if the file manager does not movecopydata proper broadcasts to notify
                            the media scanner, then the thumbnail info of this image would still exist
                            in the provider.*//*

                     String filePath = videocursor.getString(index);
                     File file = new File(filePath);
                     //      MyConsole.println("filepath " + filePath);
                     if (file.exists() && file.length() > 0) {
                         videos.add(filePath);
                     }
                 } catch (IndexOutOfBoundsException e) {
                     MyConsole.println("from GetVideoFiles " + e.getMessage());
                 }
             }
         } while (videocursor.moveToNext());
         videocursor.close();
      }
     } catch (SecurityException e) {
      Log.e("Sec","urity Exception");
    }
        return videos;
    }


    @Override
    protected void onPostExecute(ArrayList<String> videos) {
        super.onPostExecute(videos);
        listRefresher.updateList(videos);
        ProgressBar progressBar =
                ( (MainActivity)context.get()).findViewById(R.id.progressbar);
        if(progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }



}
*/

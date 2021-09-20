/*
package com.sendshare.movecopydata.wififiletransfer.media;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ProgressBar;

import com.sendshare.movecopydata.wififiletransfer.activities.MainActivity;
import com.sendshare.movecopydata.wififiletransfer.interfaces.ListRefresher;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyConsole;
import com.wifi.mitko.sharewifiles3.R;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class GetAudioFiles extends AsyncTask<Void,Void, ArrayList<String>> {
        private ArrayList<String> audios = new ArrayList<>();
        private String[] proj = { MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.SIZE ,
            //    MediaStore.Audio.Albums.ALBUM_ART
        };
        private ListRefresher listRefresher;
        private WeakReference<Context> weakReference;
        private int from;

        public GetAudioFiles(int from, WeakReference<Context> weakReference,ListRefresher listRefresher) {
            this.weakReference = weakReference;
            this.from = from;
            this.listRefresher = listRefresher;
        }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ProgressBar progressBar =
                ( (MainActivity)weakReference.get()).findViewById(R.id.progressbar);
        if(progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected ArrayList<String> doInBackground(Void... voids) {
        audios.clear();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        uri = uri.buildUpon().appendQueryParameter("limit",from+",20").build();
        Cursor videocursor = weakReference.get().getContentResolver().query(
                uri,
                proj, null, null,
                MediaStore.Audio.Media.DATE_MODIFIED+" desc");
        if(videocursor != null) {
            videocursor.moveToFirst();
            do {
                if(videocursor.getCount() > 0) {
                    int index = videocursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                    //   Log.e(" video =>", videocursor.getString(index));
                    try {
                           */
/* This happens when the user deletes an image file from an file manager,
                                    and if the file manager does not movecopydata proper broadcasts to notify
                            the media scanner, then the thumbnail info of this image would still exist
                            in the provider.*//*

                           String filePath = videocursor.getString(index);
                           File file = new File(filePath);
                           if(file.exists() && file.length() > 0) {
                               audios.add(filePath);
                           }
                    }catch (IndexOutOfBoundsException e) {
                        MyConsole.println("from GetAudioFiles "+e.getMessage());
                    }
                }
            } while (videocursor.moveToNext());
            videocursor.close();
        }
        return audios;
    }

    @Override
    protected void onPostExecute(ArrayList<String> audios) {
        super.onPostExecute(audios);
        listRefresher.updateList(audios);
        ProgressBar progressBar =
                ( (MainActivity)weakReference.get()).findViewById(R.id.progressbar);
        if(progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }
}
*/

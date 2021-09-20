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

public class GetPicturesFiles extends AsyncTask<Void,Void, ArrayList<String>> {
    private ArrayList<String> pictures = new ArrayList<>();
    private String[] proj = { MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE };
    private WeakReference<Context> weakReference;
    private int from;
    private ListRefresher listRefresher;
    public GetPicturesFiles(int from, WeakReference<Context> weakReference,ListRefresher listRefresher) {
        this.weakReference = weakReference;
        this.from = from;
        this.listRefresher = listRefresher;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ProgressBar progressBar =
                ((MainActivity)weakReference.get()).findViewById(R.id.progressbar);
        if(progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected ArrayList<String> doInBackground(Void... voids) {
        pictures.clear();
         Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        uri = uri.buildUpon().appendQueryParameter("limit",from+",20").build();
        Cursor videocursor = weakReference.get().getContentResolver().query(uri,
                proj,
                null, null,
                MediaStore.Images.Media.DATE_MODIFIED+" desc");

            //       MediaStore.Images.ImageColumns.DATE_TAKEN+" desc");
           //     weakReference.get().getContentResolver().query (MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
           //     proj, null, null, null);
        if(videocursor != null) {
            videocursor.moveToFirst();
            do {
                if (videocursor.getCount() > 0) {
                    int index = videocursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //      MyConsole.println(" video =>" + videocursor.getString(index));
                        try {
                            String filePath = videocursor.getString(index);
                           */
/* This happens when the user deletes an image file from an file manager,
                                    and if the file manager does not movecopydata proper broadcasts to notify
                            the media scanner, then the thumbnail info of this image would still exist
                            in the provider.*//*

                            File file = new File(filePath);
                            if(file.exists() && file.length() > 0) {
                                pictures.add(filePath);
                            }
                        } catch (IndexOutOfBoundsException e) {
                            MyConsole.println("from GetPicturesFiles "+e.getMessage());
                        }
                  }
                }
                while (videocursor.moveToNext()) ;
                videocursor.close();
            }

        return pictures;
    }

    @Override
    protected void onPostExecute(ArrayList<String> pictures) {
        super.onPostExecute(pictures);
        listRefresher.updateList(pictures);
        ProgressBar progressBar =
                ( (MainActivity)weakReference.get()).findViewById(R.id.progressbar);
        if(progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }
}
*/

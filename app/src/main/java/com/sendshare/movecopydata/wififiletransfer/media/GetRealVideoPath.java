package com.sendshare.movecopydata.wififiletransfer.media;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.sendshare.movecopydata.wififiletransfer.utilities.MyConsole;

import java.lang.ref.WeakReference;

public class GetRealVideoPath extends AsyncTask<Void,Void, String> {

    private WeakReference<Context> contextWeakReference;
    private String fileName;

    public GetRealVideoPath(Context context, String fileName) {
        this.contextWeakReference = new WeakReference<>(context);
        this.fileName = fileName;
    }
    @Override
    public String doInBackground(Void... voids) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String absolutePathOfImage = null;
        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME };

        cursor = this.contextWeakReference.get().getContentResolver().query(uri, projection, null,
                null, null);
        if(cursor == null) return null;
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            MyConsole.println("absolute path cursor = " + absolutePathOfImage);
            int lastIndex1 = absolutePathOfImage.lastIndexOf("/");
            if(lastIndex1 != -1) {
               String name = absolutePathOfImage.substring(lastIndex1+1);
               MyConsole.println("name = " + name + " fileName = " + fileName);
               if(name.equals(fileName)) {
                   return absolutePathOfImage;
               }
            }
        }
        cursor.close();
        return null;
    }


}

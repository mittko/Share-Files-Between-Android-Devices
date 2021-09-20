/*
package com.sendshare.movecopydata.wififiletransfer.media;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;

import com.sendshare.movecopydata.wififiletransfer.activities.MainActivity;
import com.sendshare.movecopydata.wififiletransfer.interfaces.ListRefresher;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyConsole;
import com.wifi.mitko.sharewifiles3.R;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class GetDocumentFiles2 extends AsyncTask<Void,Void, ArrayList<String>> {
    private ArrayList<String> documents = new ArrayList<>();
    private WeakReference<Context> weakReference;
    private int from;
    private String pdf = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf");
    private String doc = MimeTypeMap.getSingleton().getMimeTypeFromExtension("doc");
    private String docx = MimeTypeMap.getSingleton().getMimeTypeFromExtension("docx");
    private String xls = MimeTypeMap.getSingleton().getMimeTypeFromExtension("xls");
    private String xlsx = MimeTypeMap.getSingleton().getMimeTypeFromExtension("xlsx");
    private String ppt = MimeTypeMap.getSingleton().getMimeTypeFromExtension("ppt");
    private String pptx = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pptx");
    private String txt = MimeTypeMap.getSingleton().getMimeTypeFromExtension("txt");
    private String rtx = MimeTypeMap.getSingleton().getMimeTypeFromExtension("rtx");
    private String rtf = MimeTypeMap.getSingleton().getMimeTypeFromExtension("rtf");
    private String html = MimeTypeMap.getSingleton().getMimeTypeFromExtension("html");

    private ListRefresher listRefresher;
     public GetDocumentFiles2(int from, WeakReference<Context> weakReference,ListRefresher listRefresher) {
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

        Uri uri = MediaStore.Files.getContentUri("external");
        uri = uri.buildUpon().appendQueryParameter("limit",from+",20").build();
        //Column
        String[] column = {MediaStore.Files.FileColumns.DATA};
        //Where
        String where = MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                +" OR " +MediaStore.Files.FileColumns.MIME_TYPE + "=?";
        //args
        String[] args = new String[]{pdf,doc,docx,xls,xlsx,ppt,pptx,txt,rtx,rtf,html};
        Cursor videocursor =
                weakReference.get().getContentResolver().
                        query(uri, column, where, args, MediaStore.Files.FileColumns.DATE_MODIFIED+" desc");//   "_id DESC");
        if(videocursor != null) {
            videocursor.moveToFirst();
            do {
                if(videocursor.getCount() > 0) {
                    int index = 0;
                    try {
                       */
/* This happens when the user deletes an image file from an file manager,
                                    and if the file manager does not movecopydata proper broadcasts to notify
                            the media scanner, then the thumbnail info of this image would still exist
                            in the provider.*//*

                        String filePath = videocursor.getString(index);
                        File file = new File(filePath);
                        if(file.exists() && file.length() > 0) {
                            documents.add(filePath);
                        }
                    }catch (IndexOutOfBoundsException e) {
                        MyConsole.println("from GetDocumentFiles "+e.getMessage());
                    }
                }
            }while (videocursor.moveToNext());
            videocursor.close();
        }
        return documents;
    }

    @Override
    protected void onPostExecute(ArrayList<String> documents) {
        super.onPostExecute(documents);
        listRefresher.updateList(documents);
        ProgressBar progressBar =
                ( (MainActivity)weakReference.get()).findViewById(R.id.progressbar);
        if(progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }
}
*/

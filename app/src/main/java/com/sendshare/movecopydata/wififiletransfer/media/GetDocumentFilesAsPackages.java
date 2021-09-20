package com.sendshare.movecopydata.wififiletransfer.media;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import com.sendshare.movecopydata.wififiletransfer.interfaces.ListRefresher3;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

public class GetDocumentFilesAsPackages extends AsyncTask<Void,Void, HashMap<String, ArrayList<String>>> {

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
    String[] args = new String[]{pdf,doc,docx,xls,xlsx,ppt,pptx,txt,rtx,rtf,html};

    private WeakReference<Context> contextWeakReference;
    private ListRefresher3 listRefresher3;
    private HashMap<String, ArrayList<String>> map =
            new HashMap<>();

    public GetDocumentFilesAsPackages(Context context, ListRefresher3 listRefresher3) {
        this.contextWeakReference = new WeakReference<>(context);;
        this.listRefresher3 = listRefresher3;
    }
    @Override
    protected HashMap<String, ArrayList<String>> doInBackground(Void... voids) {
        Uri uri;
        int column_index_data, column_index_folder_name;
        String absolutePathOfImage = null;
        uri = MediaStore.Files.getContentUri("external");

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME };

        Cursor cursor =
                contextWeakReference.get().getContentResolver().
                        query(uri, projection, where, args, MediaStore.Files.FileColumns.DATE_MODIFIED+" desc");//   "_id DESC");

        if(cursor == null) return null;
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
      /*  column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
       */ while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            int lastIndex1 = absolutePathOfImage.lastIndexOf("/");
            if(lastIndex1 != -1) {
                int lastIndex2 = absolutePathOfImage.lastIndexOf("/", lastIndex1-1);;
                if(lastIndex2 != -1) {
                    String folder = absolutePathOfImage.substring(lastIndex2 + 1, lastIndex1);
                    ArrayList<String> paths = map.get(folder);
                    if(paths == null) {
                        paths = new ArrayList<>();
                        map.put(folder,paths);
                    }
                    paths.add(absolutePathOfImage);
                }
            }
           /* for(Map.Entry<String, ArrayList<String>> m : map.entrySet()) {
                Log.e(" folder ", m.getKey());
                for(String p : m.getValue()) {
                    Log.e("path = " , p);
                }
            }*/
        }
        cursor.close();
        return map;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(HashMap<String, ArrayList<String>> stringArrayListHashMap) {
        super.onPostExecute(stringArrayListHashMap);
        /*ListRefresher3 listRefresher =
                (ListRefresher3) this.contextWeakReference.get();*/
        listRefresher3.updateList(map);
    }
}

package com.sendshare.movecopydata.wififiletransfer.utilities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;

import com.sendshare.movecopydata.wififiletransfer.activities.DataReceiverActivity;

import java.io.File;

import static android.content.Context.MODE_PRIVATE;

public class FileManager {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String userDirectory = "userDirectory";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static String getRealPath(Uri uri, Context context) {

        File[] storage = ContextCompat.getExternalFilesDirs(context.getApplicationContext(),null);
        String phoneStorage = storage[0].getAbsolutePath();
        String phoneStorageSecondary = "";
        if(storage.length > 1) {
            phoneStorageSecondary = storage[1].getAbsolutePath();
        }
        String selectedStorage = "";


        String segment = "";
        String finalDir = "";

        if(uri != null) {
            DocumentFile pickedDir = DocumentFile.fromTreeUri(context, uri);
            if(pickedDir != null) {
                //
                String lastPathSegment  = pickedDir.getUri().getLastPathSegment();
                if (lastPathSegment != null) {
                    int index2 = lastPathSegment.indexOf(":");
                    if (index2 != -1) {
                        String parentDir = lastPathSegment.substring(0,index2);
                    //    MyConsole.println("parent = " + parentDir + " phoneStorage = " + phoneStorage);
                        if(parentDir.contains("primary")) {
                            selectedStorage = phoneStorage;
                        } else  {
                            selectedStorage = phoneStorageSecondary;
                        }
                        segment = lastPathSegment.substring(index2 + 1);
                    }
                }
            }
        }
        int index = selectedStorage.toUpperCase().indexOf("/Android".toUpperCase());
        if(index != -1) {
            finalDir = selectedStorage.substring(0,index+1);
            //  Log.e("dir = ",dir);
        }
        //  Log.e("realPath =",realPath);
        return finalDir+segment;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean verifyStoragePermissions(Context context) {
      // Check if we have write permission
      int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
      if(permission != PackageManager.PERMISSION_GRANTED) {
          ActivityCompat.requestPermissions(
                      (AppCompatActivity)context,
                      PERMISSIONS_STORAGE,
                      REQUEST_EXTERNAL_STORAGE);
          return false;
      }
      return true;
   //   MyConsole.println("NEEDED ONLY FROM MARSHMALLOW AND ABOVE BELOW PERMISSION IS GRANTED AUTOMATICALLY");
  }
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
    //    MyConsole.println("MIME = "+type);
        return type;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void grandPermission(Context context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            ( (AppCompatActivity)context).startActivityForResult(intent,25);
            MyConsole.println("ACTION_OPEN_DOCUMENT_TREE");
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("*/*");
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            ( (AppCompatActivity)context).startActivityForResult(intent,25);
            MyConsole.println("ACTION_OPEN_DOCUMENT");
        } else {
           // MyConsole.println("ACTION_GET_CONTENT");
        }
    }
    public static void saveUserUri(Uri uri,Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("some tag",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(userDirectory,uri.toString());
        editor.apply();
     }
    public static Uri getUserUri(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("some tag",MODE_PRIVATE);
        return Uri.parse(sharedPreferences.getString(userDirectory,"default"));
    }

    public static void scanFile(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            MyConsole.println("SEND BROADCAST");
            Intent mediaScanIntent = new Intent(context, DataReceiverActivity.class);
            mediaScanIntent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File file = new File(uri.getPath());
          //  MyConsole.println("absolute file = "+file.getAbsolutePath());
            mediaScanIntent.setData(uri);
            ((DataReceiverActivity)context).sendBroadcast(mediaScanIntent);
        }
        else
        {
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,uri));
        }
    }
    public static void refreshMediaStoreDB(Context context, String file) {
        MyConsole.println("onScan Begin " + file);
        MediaScannerConnection.scanFile(context,
                new String[]{file},
                null
                , new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                               MyConsole.println("onScanCompleted 222 " + path +" " +uri.getPath());

                    }
                });
    }
    public static void showDialog(final String msg, final Context context, final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    /* public static String getMimeTypeFromUri(Uri uri, Context context) {
           return context.getContentResolver().getType(uri);
        }*/
    public static boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                        try {
                            ActivityCompat
                                    .requestPermissions(
                                            (Activity) context,
                                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        } catch (SecurityException e) {

                        }
                    Log.e("fuck ","you");
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static boolean checkIfSDCardRoot(Uri uri) {
        return isExternalStorageDocument(uri) && isRootUri(uri) && !isInternalStorage(uri);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static boolean isRootUri(Uri uri) {
        String docId = DocumentsContract.getTreeDocumentId(uri);
        return docId.endsWith(":");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean isInternalStorage(Uri uri) {
        return isExternalStorageDocument(uri) && DocumentsContract.getTreeDocumentId(uri).contains("primary");
    }
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static void refreshMediaStoreDB(Context context, String[] paths) {
        // !!!  android.app.ServiceConnectionLeaked: Activity MainActivity has leaked ServiceConnection
        MediaScannerConnection.scanFile(context.getApplicationContext(),
                paths,
                null
                , new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                                MyConsole.println("onScanCompleted 222 " + path +" " +uri.getPath());
                    }
                });
    }

    //  Version Code 6 ->   @TargetApi(Build.VERSION_CODES.KITKAT)
   /* public static boolean checkPersistedUriPermissions(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// !!!!! NOT VERIFIED ATENTION
            List<UriPermission> list = context.getContentResolver().getPersistedUriPermissions();
            for (int i = 0; i < list.size(); i++) {
                // da se proveri korektnostta
                //     Log.e("tag",list.get(i).getUri().toString());
                if (list.get(i).isWritePermission()) {// && list.get(i).getUri() == myUri
                    //        Log.e("tag",list.get(i).getUri().getPath());
                    return true;
                }
            }
            return false;
        }
        return true;
    }*/
    /* Version code 6 -> public static void saveStorageDirectory(int storage,Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("storagePreferrences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("storageDirectory",storage);
        editor.apply();
    }
    public static int getStorageDirectory(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("storagePreferrences", MODE_PRIVATE);
        return sharedPreferences.getInt("storageDirectory",MODE_PRIVATE);
    }
*/

}

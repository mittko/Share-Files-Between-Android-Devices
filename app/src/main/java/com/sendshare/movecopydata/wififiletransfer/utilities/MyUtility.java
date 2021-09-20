package com.sendshare.movecopydata.wififiletransfer.utilities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.DisplayMetrics;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;

import java.nio.ByteOrder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class MyUtility {

    public static HashMap<String, Drawable>
    filesToSend = new HashMap<>();
    public static String getNetworkPAddress(Context context) {
        WifiManager wifiManager = (WifiManager) (context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE));
        int integerIP = wifiManager.getDhcpInfo().ipAddress;
        //   MyConsole.println(integerIP + "");
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            integerIP = Integer.reverseBytes(integerIP);
        }
        return ((integerIP >> 24) & 0xff) + "." +
                ((integerIP >> 16) & 0xff) + "." +
                ((integerIP >> 8) & 0xff);
    }
    public static String getWifi_IPAddress(Context context) {
        WifiManager wifiManager = (WifiManager) (context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE));
        int integerIP = wifiManager.getDhcpInfo().ipAddress;
     //   MyConsole.println(integerIP + "");
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            integerIP = Integer.reverseBytes(integerIP);
        }
        return ((integerIP >> 24) & 0xff) + "." +
                ((integerIP >> 16) & 0xff) + "." +
                ((integerIP >> 8) & 0xff) + "." + (integerIP & 0xff);
    }
    public static float getWidthInPixels(AppCompatActivity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
    public static float getHeightInPixels(AppCompatActivity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public  static int dpToPx(int dp, Context context) {
        float density = context.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

  public static boolean checkIsWifiOn(Context context) {
      ConnectivityManager connectivityManager
               = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
      return networkInfo != null
              && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
  }

  public static String getRandomString() {
      Calendar now = Calendar.getInstance();
      int minute = now.get(Calendar.MINUTE);
      int second = now.get(Calendar.SECOND);
      int millis = now.get(Calendar.MILLISECOND);
      return String.format(Locale.getDefault(),"%d%d%d",minute,second,millis);
  }

    public static String getHumanReadableBytes(double bytes) {
        String cnt_size;

        double size_kb = bytes / 1024d;
        double size_mb = size_kb / 1024d;
        double size_gb = size_mb / 1024d ;

        if (size_gb >= 1){
            cnt_size = String.format(Locale.getDefault(),"%.2f",size_gb) + " GB";
        }else if(size_mb >= 1){
            cnt_size = String.format(Locale.getDefault(),"%.2f",size_mb) + " MB";
        }else{
            cnt_size = String.format(Locale.getDefault(),"%.2f",size_kb) + " KB";
        }
        return cnt_size;
    }

    public static String shrinkString(String txt) {
        StringBuilder sb = new StringBuilder();
        int lastIndex = txt.lastIndexOf(".");
        String name = txt.substring(0,lastIndex);
        String ext = txt.substring(lastIndex);
        System.out.println(name);
        if(name.length() > 6) {
            sb.append(name.charAt(0));
            sb.append(name.charAt(1));
            sb.append(name.charAt(2));
            sb.append("...");
            sb.append(name.charAt(name.length()-3));
            sb.append(name.charAt(name.length()-2));
            sb.append(name.charAt(name.length()-1));
            sb.append(ext);
            return sb.toString();
        }
        return txt;
    }
}

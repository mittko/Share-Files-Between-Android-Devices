package com.sendshare.movecopydata.wififiletransfer.application;

import android.app.Application;


public class MyApplication extends Application {
 //  private RefWatcher refWatcher;

 /*   public static RefWatcher getRefWather(Context context) {
        MyApplication  myApplication = (MyApplication)context.getApplicationContext();
        return myApplication.refWatcher;
    }*/
    @Override
    public void onCreate() {
        super.onCreate();
   //     refWatcher = LeakCanary.install(this);
    }
}

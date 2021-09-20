package com.sendshare.movecopydata.wififiletransfer.media;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.sendshare.movecopydata.wififiletransfer.activities.MainActivity;
import com.sendshare.movecopydata.wififiletransfer.adapters.ApkInfo;
import com.sendshare.movecopydata.wififiletransfer.interfaces.ListRefresher2;
import com.wifi.mitko.sharewifiles3.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class GetApkFiles extends AsyncTask<Void,Void, ArrayList<ApkInfo>> {

    private WeakReference<Context> context;
    private ArrayList<ApkInfo> apkInfos = new ArrayList<>();
    private ListRefresher2 listRefresher;
    public GetApkFiles(WeakReference<Context> context, ListRefresher2 listRefresher) {
        this.context = context;
        this.listRefresher = listRefresher;
    }

    @Override
    protected ArrayList<ApkInfo> doInBackground(Void... voids) {
        List<PackageInfo> packList = context.get().getPackageManager().getInstalledPackages(0);

        for (int i=0; i < packList.size(); i++)
        {
            PackageInfo packInfo = packList.get(i);
            if (  (packInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
            {
                String appName = packInfo.applicationInfo.loadLabel(context.get().getPackageManager()).toString();
            //    Log.e("App № " + Integer.toString(i), appName + " "  + packInfo.applicationInfo.sourceDir);
                String apkFilePath = packInfo.applicationInfo.sourceDir;
                // da se zarejda drawable v otdelna nishka !!!!!
                Drawable  drawable = packInfo.applicationInfo.loadIcon(context.get().getPackageManager());
                apkInfos.add(new ApkInfo(appName,apkFilePath,drawable,false));
                //    packInfo.applicationInfo.
            }
        }
        return apkInfos;
    }
//da se poprawi memory leak !!!!!!!!!!!!!!!!!!!!!11
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
    protected void onPostExecute(ArrayList<ApkInfo> apkInfos) {
        super.onPostExecute(apkInfos);
        listRefresher.updateList(apkInfos);
        ProgressBar progressBar =
                ( (MainActivity)context.get()).findViewById(R.id.progressbar);
        if(progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }
}

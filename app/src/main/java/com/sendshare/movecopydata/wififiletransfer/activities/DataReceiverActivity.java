package com.sendshare.movecopydata.wififiletransfer.activities;

import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdView;
import com.sendshare.movecopydata.wififiletransfer.modules.GlideApp;
import com.sendshare.movecopydata.wififiletransfer.servers.HelloServer;
import com.sendshare.movecopydata.wififiletransfer.servers.MyServer;
import com.sendshare.movecopydata.wififiletransfer.singletons.AdViewBanner;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyConsole;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyUtility;
import com.wifi.mitko.sharewifiles3.R;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class DataReceiverActivity extends AppCompatActivity {

    private HelloServer helloServer;
    private MyServer myServer;
    private AdView adView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (helloServer == null) {
            helloServer = new HelloServer(getApplicationContext());
            helloServer.start();
        }
        if (myServer == null) {
            myServer = new MyServer(this);
            myServer.start();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_receiver);

        linearLayout =
                findViewById(R.id.data_receiver_id);
        TextView textView = linearLayout.findViewById(R.id.deviceNameId);
        textView.setText(Build.BRAND + " " + Build.MODEL);

        initActionBar();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    protected void onResume() {
        super.onResume();
    //    createRealAds();
    }
    @Override
    protected void onStop() {
        destroyAds();
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        if (myServer != null) {
            myServer.interrupt();
        }
        if (helloServer != null) {
            helloServer.interrupt();
        }
        destroyAds();
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        if (myServer.tasksCompleted()) {
            MyUtility.filesToSend.clear();
            super.onBackPressed();
        } else {
            final AlertDialog alertDialog =
                    new AlertDialog.Builder(this, R.style.InputDialogStyle).create();
            alertDialog.setTitle("");
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setMessage("Files sending will be cancelled. Do you want to stop it ?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"OK",(DialogInterface.OnClickListener)null);
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"CANCEL",(DialogInterface.OnClickListener)null);
            alertDialog.show();
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MyUtility.filesToSend.clear();
                            alertDialog.dismiss();
                            DataReceiverActivity.super.onBackPressed();
                        }
                    });
        }

    }
    public void showDrawable(int viewId,  final String  pathStorage) throws URISyntaxException, IOException {
        View view = findViewById(viewId);
        final ImageView imageView = view.findViewById(R.id.transfer_image_view);
        if(pathStorage != null) {
            final File file = new File(pathStorage);
            if (file.exists()) {
                if(pathStorage.endsWith(".apk")) {
                    PackageManager pm = getPackageManager();
                    PackageInfo info = pm.getPackageArchiveInfo(pathStorage, 0);
                    if(info != null) {
                        // the secret are these two lines....
                        info.applicationInfo.sourceDir = pathStorage;
                        info.applicationInfo.publicSourceDir = pathStorage;
                        Drawable drawable = info.applicationInfo.loadIcon(pm);
                        //         imageView.setImageDrawable(drawable);
                        GlideApp.with(DataReceiverActivity.this).load(drawable).into(imageView);
                    } else {
                        GlideApp.with(DataReceiverActivity.this).load(R.mipmap.ic_launcher_round).into(imageView);
                    }
                } else {
                    if(pathStorage.endsWith(".mp3")) {
                        coverAudioThumb(pathStorage, imageView);
                    } else {
                        GlideApp.with(DataReceiverActivity.this)
                                .load(pathStorage).placeholder(R.mipmap.share_blue_launcher).into(imageView);
                    }
                }
            } else {
                // placeholder    GlideApp.with(DataReceiverActivity.this).load(path);
                MyConsole.println("FILE NOT EXIST !!!");
            }
        }
    }
    public void addViewFromAnotherThread(final View view, final int viewId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(linearLayout != null) {
                    View imageAndTextView = addViewDynamically(viewId);

                    linearLayout.addView(view,1);
                    linearLayout.addView(imageAndTextView,1);

                    ImageView imageView = imageAndTextView.findViewById(R.id.transfer_image_view);
                    Glide.with(DataReceiverActivity.this).load(R.mipmap.share_blue_launcher).into(imageView);
                }
            }
        });
    }

    private View addViewDynamically(int id) {
        View view = LayoutInflater.from(this).inflate(R.layout.just_transfer_row, null);
        view.setId(id);
        return view;
    }
    private void createRealAds() {
        LinearLayout parent = findViewById(R.id.ads_parent2);
        AdView adView = AdViewBanner.getInstance()
                .createRealAdView2(getApplicationContext(),
                        getString(R.string.real_banner2_unitId));
        parent.removeAllViews(); ;// IMPORTANT !!!! because onResume() called twice
        parent.addView(adView);
    }
    private void createTestAds() {
        LinearLayout parent = findViewById(R.id.ads_parent2);
        AdView adView = AdViewBanner.getInstance().
                createTestAdView2(getApplicationContext(),getString(R.string.test_banner_unitId));
        parent.addView(adView);
    }
    private void destroyAds() {
        LinearLayout adsParent = findViewById(R.id.ads_parent2);
        if(adsParent != null)
            adsParent.removeAllViews();
        if(adView != null) {
            adView.destroy();
            adView = null;
        }
    }
    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //    actionBar.setDisplayHomeAsUpEnabled(true);
            //     actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
            ActionBar.LayoutParams layoutParams =
                    new ActionBar.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                    );
            View view = LayoutInflater.from(this).inflate(R.layout.ads_container2, null);// LayoutInflater.from(this).inflate(R.layout.custom_actionbar2,null);
            actionBar.setCustomView(view, layoutParams);
            Toolbar parent = (Toolbar) view.getParent();
            parent.setPadding(0, 0, 0, 0);//for tab otherwise give space in tab
            parent.setContentInsetsAbsolute(0, 0);
        }
    }
    private void coverAudioThumb(String path, ImageView imageView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            GlideApp.with(this).load(R.mipmap.share_blue_launcher).into(imageView);
        } else {
            Bitmap bitmap = null;
            MediaMetadataRetriever mr = new MediaMetadataRetriever();
            mr.setDataSource(path);
            byte[] byte1 = mr.getEmbeddedPicture();
            mr.release();
            if (byte1 != null) {
                bitmap = BitmapFactory.decodeByteArray(byte1, 0, byte1.length);
                GlideApp.with(this).load(bitmap).placeholder(R.mipmap.share_blue_launcher).into(imageView);
            } else {
                GlideApp.with(this).load(R.mipmap.share_blue_launcher).into(imageView);
            }
        }
    }


}
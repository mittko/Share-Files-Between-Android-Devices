package com.sendshare.movecopydata.wififiletransfer.activities;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.ads.AdView;
import com.sendshare.movecopydata.wififiletransfer.modules.GlideApp;
import com.sendshare.movecopydata.wififiletransfer.singletons.AdViewBanner;
import com.sendshare.movecopydata.wififiletransfer.threads.FileTransferThread;
import com.sendshare.movecopydata.wififiletransfer.threads.SayHelloThread;
import com.sendshare.movecopydata.wififiletransfer.ui.LinearCustomView;
import com.sendshare.movecopydata.wififiletransfer.utilities.Konstants;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyUtility;
import com.wifi.mitko.sharewifiles3.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileTransferActivity2 extends AppCompatActivity {


    private ArrayList<Thread> fileTransferThreads = new ArrayList<>();

    private AdView adView;

    private long ALL_BYTES_TO_SEND = 0;
    private HashMap<String, String> peersMap = new HashMap<>();
    SayHelloThread sayHelloThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_transfer);

        initActionBar();

        findPeers();
        /*String targetIp = getIntent().getStringExtra(Konstants.DEVICE_IP);
        int targetPort = getIntent().getIntExtra(Konstants.DEVICE_RECEIVER_PORT,0);*/

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onStop() {
        destroyAds();
        super.onStop();
    }
    @Override
    protected void onResume() {
        super.onResume();
      //  createRealAds();
    }
    @Override
    protected void onDestroy() {
        destroyThreads();
        destroyAds();
        super.onDestroy();

    }
    @Override
    public void onBackPressed() {
        boolean threadsInterrupted = true;
        for( Thread thread : fileTransferThreads) {
            FileTransferThread fileTransferThread = (FileTransferThread)thread;
            if(!fileTransferThread.taskCompleted()) {
                threadsInterrupted = false;
                break;
            }
        }
        if (threadsInterrupted) {
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
                            FileTransferActivity2.super.onBackPressed();
                        }
                    });
        }

    }

    public void addPeerFromAnotherThread(final String address,final String deviceName) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                peersMap.put(deviceName, address);
                TextView deviceView = new TextView(FileTransferActivity2.this);
                deviceView.setGravity(Gravity.CENTER);
                deviceView.setTypeface(null, Typeface.BOLD);
                deviceView.setTextAppearance(FileTransferActivity2.this, android.R.style.TextAppearance_Large);
                deviceView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.clickable_row,null));
                deviceView.setText(deviceName);
                deviceView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startFileTransferring((TextView)view);
                    }
                });
                LinearLayout peersLayout =
                        findViewById(R.id.peers_container);
                LinearLayout.LayoutParams layoutParams =
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                MyUtility.dpToPx(100,FileTransferActivity2.this));
                deviceView.setLayoutParams(layoutParams);
                peersLayout.addView(deviceView);
            }
        });
    }
    public void startFileTransferring(TextView view) {

        if(sayHelloThread != null) {
            sayHelloThread.interrupt();
        }

        String deviceName = view.getText().toString();
        String address = peersMap.get(deviceName);
         if(address != null) {
        int splitIndex = address.indexOf(":");
        String ip = address.substring(0,splitIndex);
        int port = Integer.parseInt(address.substring(splitIndex+1));

        // SWAP LAYOUTS
        RelativeLayout relativeLayout = findViewById(R.id.peers_parent_layout);
        relativeLayout.setVisibility(View.GONE);

        ScrollView filesToBeSendParentLayout =
                findViewById(R.id.files_to_be_send_parent);
        filesToBeSendParentLayout.setVisibility(View.VISIBLE);

        LinearLayout filesToBeSendLayout = findViewById(R.id.fileToBeSend);
       // END OF SWAP
        for(Map.Entry<String, Drawable>  keySet : MyUtility.filesToSend.entrySet()) {
            String str = keySet.getKey();
            long bytesToSend = new File(str).length();
            ALL_BYTES_TO_SEND += bytesToSend;
        }

            int v = 0;
            for(Map.Entry<String, Drawable> keySet  : MyUtility.filesToSend.entrySet()) {
                String filepath = keySet.getKey();
                LinearCustomView linearCustomView = addCustomView(filepath);
                View whatIsTransferredView = addViewDynamically(filepath,keySet.getValue());
                filesToBeSendLayout.addView(whatIsTransferredView);
                filesToBeSendLayout.addView(linearCustomView);
                FileTransferThread fileTransferThread =
                        new FileTransferThread(this,ip, port,
                                filepath,ALL_BYTES_TO_SEND,
                                null/*sharedCircleCustomView*/ , linearCustomView);
                fileTransferThread.start();
                fileTransferThreads.add(fileTransferThread);
            }
        }
    }
    public void stopOrStartHelloThread(View view) {
        Button button = (Button)view;
        ProgressBar progressBar = findViewById(R.id.searchDevicesProgressBar);
        if(button.getText().toString().equals("stop searching devices")) {
            if(sayHelloThread != null) {
                sayHelloThread.interrupt();
            }
            button.setText(getResources().getString(R.string.start_search_devices));
            if(progressBar != null) progressBar.setVisibility(View.GONE);
        } else {
            LinearLayout linearLayout =
                    findViewById(R.id.peers_container);
            linearLayout.removeAllViews();
            findPeers();
            button.setText(getResources().getString(R.string.stop_search_devices));
            if(progressBar != null) progressBar.setVisibility(View.VISIBLE);
        }

    }

    private void findPeers() {
        String netAddress = MyUtility.getNetworkPAddress(this);
        sayHelloThread = new SayHelloThread(netAddress, Konstants.HELLO_SERVER_PORT, this);
        sayHelloThread.start();
    }
    private View addViewDynamically(String filepath, Drawable drawable) {
        View whatIsTransferredView = LayoutInflater.from(this).inflate(R.layout.just_transfer_row,null);
        ImageView imageView = whatIsTransferredView.findViewById(R.id.transfer_image_view);
        if(filepath.endsWith(".mp3")) {
                coverAudioThumb(filepath, imageView);
            } else {
                if(drawable != null) {
                    GlideApp.with(this).load(drawable).placeholder(R.mipmap.share_blue_launcher).into(imageView);
                } else {
                    GlideApp.with(this).load(filepath).placeholder(R.mipmap.share_blue_launcher).into(imageView);
                }
        }

        TextView textView = whatIsTransferredView.findViewById(R.id.transfer_text_view);
        textView.setText(getFileName(filepath));
        return whatIsTransferredView;
    }
    private LinearCustomView addCustomView(String fileName) {
        int viewHeight =
                (int)( MyUtility.getHeightInPixels(this)/12);
        int viewWidth =
                (int)(MyUtility.getWidthInPixels(this));
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(viewWidth,
                        viewHeight);
        final LinearCustomView linearCustomView = new LinearCustomView(this,viewWidth,viewHeight);
        //  customView.setWIDTH(viewWidth);
        //   customView.setHEIGHT(viewHeight);
        linearCustomView.setLayoutParams(layoutParams);
        return linearCustomView;
    }
    private void destroyAds() {
        LinearLayout adsParent = findViewById(R.id.ads_parent3);
        if(adsParent != null)
            adsParent.removeAllViews();
        if(adView != null) {
            adView.destroy();
            adView = null;
        }
    }
    private void createTestAds() {
        LinearLayout parent = findViewById(R.id.ads_parent3);
        AdView adView = AdViewBanner.getInstance().
                createTestAdView3(getApplicationContext(),getString(R.string.test_banner_unitId));
        parent.removeAllViews();
        parent.addView(adView);

    }
     private void createRealAds() {
         LinearLayout parent = findViewById(R.id.ads_parent3);
         AdView adView = AdViewBanner.getInstance()
                 .createRealAdView3(getApplicationContext(),
                         getString(R.string.real_banner3_unitId));
         parent.removeAllViews();// IMPORTANT !!!! because onResume() called twice
         parent.addView(adView);
     }
    private void destroyThreads() {
        for(Thread thread : fileTransferThreads) {
            thread.interrupt();
        }
        fileTransferThreads.clear();
        if(sayHelloThread != null) {
            sayHelloThread.interrupt();
        }
    }
    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            ActionBar.LayoutParams layoutParams =
                    new ActionBar.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                    );
            View view = LayoutInflater.from(this).inflate(R.layout.ads_container3,null);
            actionBar.setCustomView(view,layoutParams);
            Toolbar parent =(Toolbar) view.getParent();
            parent.setPadding(0,0,0,0);//for tab otherwise give space in tab
            parent.setContentInsetsAbsolute(0,0);
        }
    }
    private String getFileName(String filepath) {
        int index = filepath.lastIndexOf("/");
        if(index != -1) {
            return filepath.substring(index+1);
        } else {
            return filepath;
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

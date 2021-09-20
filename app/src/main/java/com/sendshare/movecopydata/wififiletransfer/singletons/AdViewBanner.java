package com.sendshare.movecopydata.wififiletransfer.singletons;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AdViewBanner {
    private static final AdViewBanner ourInstance = new AdViewBanner();

    public static AdViewBanner getInstance() {
        return ourInstance;
    }

    private AdView realAdView;
    private AdView realAdView2;
    private AdView realAdView3;

    private AdView testAdView;
    private AdView testAdView2;
    private AdView testAdView3;

    public AdView createRealAdView2(Context context, String unitId) {
        if(realAdView2 == null) {
            realAdView2 = new AdView(context);//(new WeakReference<Context>(getContext()).get());
            realAdView2.setAdSize(AdSize.SMART_BANNER);
            realAdView2.setAdUnitId(unitId);
            AdRequest adRequest = new AdRequest.Builder().build(); // real device
            realAdView2.loadAd(adRequest);
        }
        return realAdView2;
    }
    public AdView createRealAdView(Context context, String unitId) {
        if(realAdView == null) {
            realAdView = new AdView(context);//(new WeakReference<Context>(getContext()).get());
            realAdView.setAdSize(AdSize.SMART_BANNER);
            realAdView.setAdUnitId(unitId);
            AdRequest adRequest = new AdRequest.Builder().build(); // real device
            realAdView.loadAd(adRequest);
        }
        return realAdView;
    }
    public AdView createRealAdView3(Context context, String unitId) {
        if(realAdView3 == null) {
            realAdView3 = new AdView(context);//(new WeakReference<Context>(getContext()).get());
            realAdView3.setAdSize(AdSize.SMART_BANNER);
            realAdView3.setAdUnitId(unitId);
            AdRequest adRequest = new AdRequest.Builder().build(); // real device
            realAdView3.loadAd(adRequest);
        }
        return realAdView3;
    }

    public AdView createTestAdView(Context context, String unitId) {
        if(testAdView == null) {
            testAdView = new AdView(context);//(new WeakReference<Context>(getContext()).get());
            testAdView.setAdSize(AdSize.SMART_BANNER);
            testAdView.setAdUnitId(unitId);
            AdRequest adRequest =
                    new AdRequest.Builder()
                            .build();
            testAdView.loadAd(adRequest);
        }
        return  testAdView;
    }
    public AdView createTestAdView2(Context context, String unitId) {
        if(testAdView2 == null) {
            testAdView2 = new AdView(context);//(new WeakReference<Context>(getContext()).get());
            testAdView2.setAdSize(AdSize.SMART_BANNER);
            testAdView2.setAdUnitId(unitId);
            AdRequest adRequest =
                    new AdRequest.Builder()
                            .build();
            testAdView2.loadAd(adRequest);
        }
        return  testAdView2;
    }
    public AdView createTestAdView3(Context context, String unitId) {
        if(testAdView3 == null) {
            testAdView3 = new AdView(context);//(new WeakReference<Context>(getContext()).get());
            testAdView3.setAdSize(AdSize.SMART_BANNER);
            testAdView3.setAdUnitId(unitId);
            AdRequest adRequest =
                    new AdRequest.Builder()
                            .build();
            testAdView3.loadAd(adRequest);
        }
        return  testAdView3;
    }
    private AdViewBanner() { }

}

package com.azsdk.ads;

import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by prashant.chovatiya on 3/5/2018.
 */

public class AdsLoader {

    public static void loadBannerAds(Context context, RelativeLayout layout, String strBnrId) {
        try {
            AdView mAdView = new AdView(context);
            mAdView.setAdSize(AdSize.BANNER);
            if (strBnrId != null && strBnrId.length() > 0) {

            } else {
                strBnrId = AdsDisplayUtil.getStrAdsBnrId();
            }
            Log.i("=bnr=", "=load banner===strBnrId ==" + strBnrId);

            mAdView.setAdUnitId(strBnrId);

            // Add the AdView to the view hierarchy. The view will have no size
            // until the ad is loaded.
            //RelativeLayout layout = (RelativeLayout) findViewById(R.id.test);
            layout.addView(mAdView);

            // Create an ad request. Check logcat output for the hashed device ID to
            // get test ads on a physical device.
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice("33BE2250B43518CCDA7DE426D04EE232")
                    .build();
            //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            //.addTestDevice(deviceid).build();

            // Start loading the ad in the background.
            mAdView.loadAd(adRequest);
            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    Log.i("Ads", "onAdLoaded");
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // Code to be executed when an ad request fails.
                    Log.i("Ads", "onAdFailedToLoad");
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                    Log.i("Ads", "onAdOpened");
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                    Log.i("Ads", "onAdLeftApplication");
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when when the user is about to return
                    // to the app after tapping on an ad.
                    Log.i("Ads", "onAdClosed");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static InterstitialAd interstitialAd;
    static boolean isIntShow = false;

    public static void loadIntAds(Context context, String strIndId) {
        try {
            {

                if (strIndId != null && strIndId.length() > 0) {

                } else {
                    strIndId = AdsDisplayUtil.getStrAdsIntId();
                }


                if (interstitialAd == null) {
                    interstitialAd = new InterstitialAd(context);

                    interstitialAd.setAdUnitId(strIndId);
                    interstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            Log.i("Ads-int", "onAdLoaded");
                            if(isIntShow)
                            {

                            }
                            else
                            {
                                isIntShow = true;
                                if (interstitialAd !=null && interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                }
                            }

                        }

                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            Log.i("Ads-int", "onAdFailedToLoad" + errorCode);
                        }

                        @Override
                        public void onAdClosed() {
                            // Proceed to the next level.
                            Log.i("Ads-int", "onAdClosed");
                            requestLoadInt();
                        }

                    });
                }


                if (interstitialAd !=null && interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.--reloading...");
                    requestLoadInt();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void requestLoadInt() {
        Log.d("===", "======requestLoadInt===");

        if (interstitialAd != null) {
            AdRequest adRequest = new AdRequest.Builder()
                    .setRequestAgent("android_studio:ad_template").build();
            interstitialAd.loadAd(adRequest);
        }
    }

    public static void setIntAdsNull()
    {
        interstitialAd = null;
        isIntShow = false;
    }
}

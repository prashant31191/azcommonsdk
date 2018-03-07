# azsdk - The Location tracker & Admob Ads Loader

This sdk uses for the Track user location and Admob Ads Loading


# How to use AzSDK ?

*SDK Installation

-Add below code in project level build.gradle file

    repositories {
        maven {
            url  "https://dl.bintray.com/prashant31191/maven"
            }
    }

-Add below code in app level build.gradle file

    compile 'com.prashant31191.azsdk:azsdk:1.0.0'
    //or
    implementation 'com.prashant31191.azsdk:azsdk:1.0.0'

# For Location

-Add Event bus for tracking locations

-Add below code in app level build.gradle file

    compile 'org.greenrobot:eventbus:3.1.1'
    //or
    implementation 'org.greenrobot:eventbus:3.1.1'



-Add below code in your activity or fragments...

    @Override
    public void onStart() {
        try {
            super.onStart();
            EventBus.getDefault().unregister(this);
            EventBus.getDefault().register(this);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        try {
            super.onStop();
            EventBus.getDefault().unregister(this);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


-For the getting Sucess location and some other detail of the user..

    int counter = 0;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResponseModel(ResponseModel responseModel) {
        Log.i("==onResponseModel==","=====Call--1--==");
        counter = counter + 1;
        if(responseModel !=null) {
            tvData.append(counter + " #  OK \n"+
                    "Lat : "+responseModel.getLocationLatLong().getLatitude()+
                    "Lng : "+responseModel.getLocationLatLong().getLongitude()+
                    "macAdd : "+responseModel.getMacAdressId()+
                    "\n\n"
            );
            if (responseModel.getLocationLatLong().hasSpeed()) {
                String speed = String.format(Locale.ENGLISH, "%.0f", responseModel.getLocationLatLong().getSpeed() * 3.6) + "km/h";
                SpannableString s = new SpannableString(speed);
                s.setSpan(new RelativeSizeSpan(0.25f), s.length()-4, s.length(), 0);
                tvData.append("===Speed=1=="+s);
            }
            else if(responseModel.getLocationLatLong() !=null)
            {
                CLocation cLocation = new CLocation(responseModel.getLocationLatLong());
                updateSpeed(cLocation);
            }
        }
    };


-If Location is off then gives error response from event bus

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorModel(ErrorModel errorModel) {
        Log.i("==onErrorModel==","=====Call--3--==");
        counter = counter + 1;
        if(errorModel !=null) {
            tvData.append(counter + " #  Error \n"+
                    "Error : "+errorModel.getException().getMessage()+
                    "Code : "+errorModel.getStatusCode()+
                    "\n\n"

            );
        }
    };



# For Ads SDK

-When click open Full screen ads
-For Interstitial Ads

    fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            //Interstitial Ads
                 AdsLoader.loadIntAds(AdsSampleActivity.this,"Add_Here_Interstitial_Ads_Unit_Id");
            }
        });

-For the loading banner

    RelativeLayout rlAds; //It's your relative id use in your layout

-set this code in your oncreate or on resume of activity or fragment

    AdsLoader.loadBannerAds(AdsSampleActivity.this,rlAds,"Add_Here_Banner_Ads_Unit_Id");







# Thank you

-For more detail - https://github.com/prashant31191/azcommonsdk
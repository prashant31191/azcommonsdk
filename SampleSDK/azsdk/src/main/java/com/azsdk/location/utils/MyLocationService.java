package com.azsdk.location.utils;

/**
 * Created by AzTeam.
 */
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;


public class MyLocationService extends Service
{
    private static final String TAG = "==MyLocationService==";
    private Context mContext;
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 0f;
    private String advertisingId = "";
    private String macAdressId = "";
    private String sha256MacAdressId = "";



    private class LocationListener implements android.location.LocationListener
    {
        Location mLastLocation;

        public LocationListener(String provider)
        {
         //   Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location)
        {
            try {
             //   Log.e(TAG, "onLocationChanged: " + location);
                mLastLocation.set(location);

                ResponseModel responseModel = new ResponseModel();

                responseModel.locationLatLong = location;
                responseModel.advertisingId = advertisingId;

                // get mac adress from your device
                responseModel.macAdressId = macAdressId;
                // get Mac Adress Id encrypt to SHA256
                responseModel.sha256MacAdressId = sha256MacAdressId;

                responseModel.wifiSSID = MacAdressId.getWifiSSIDN(mContext);

                LocationInfoModel mLocationInfoModel = new LocationInfoModel();
                mLocationInfoModel.setResponseModel(responseModel);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Log.e(TAG, "onProviderDisabled: " + provider);
            // on error
            ErrorModel errorModel = new ErrorModel();
            errorModel.exception = new Exception("Location Provider Disabled");

            LocationInfoModel mLocationInfoModel = new LocationInfoModel();
            mLocationInfoModel.setErrorModel(errorModel);

        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Log.e(TAG, "onProviderEnabled: " + provider);
            // on sucess
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
       // Log.e(TAG, "onStartCommand");
        mContext = getApplicationContext();
        getAdvertisingId();

        macAdressId = MacAdressId.getMacAddr();
        sha256MacAdressId = MacAdressId.encryptSHA256(macAdressId);

        statusCheck();

        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate()
    {
       // Log.e(TAG, "onCreate");
        mContext = getApplicationContext();

        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy()
    {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    private void getAdvertisingId()
    {

        // for the get Advertising Id
        new Thread(new Runnable() {
            public void run() {
                try {
                    AdvertisingIdClient.AdInfo adInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
                    // get AdvertisingId
                    advertisingId = adInfo.getId();
                   // Log.i("===", "====advertisingId==="+advertisingId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.i("GPS","Off");

            /*Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);*/
        }
    }

}
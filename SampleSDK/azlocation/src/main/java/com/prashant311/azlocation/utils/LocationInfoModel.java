package com.prashant311.azlocation.utils;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by AzTeam.
 */

public class LocationInfoModel {
    // ErrorModel errorModel;
    //ResponseModel responseModel;

    public void setErrorModel(ErrorModel errorModel) {
        Log.i("==setErrorModel====", errorModel.getException().getMessage());
        EventBus.getDefault().post(errorModel);

        /* this.errorModel = errorModel;

        if(CommonObjects.getActivityCommon() !=null)
        {
            AppInfoListener listener = (AppInfoListener) CommonObjects.getActivityCommon();
            listener.onFailure(errorModel);
        }*/
    }

    public void setResponseModel(ResponseModel responseModel) {
        // this.responseModel = responseModel;
        Log.i("==setResponseModel====", responseModel.getMacAdressId());
        EventBus.getDefault().post(responseModel);

       /* if(CommonObjects.getActivityCommon() !=null)
        {
            AppInfoListener listener = (AppInfoListener) CommonObjects.getActivityCommon();
            listener.onSuccess(responseModel);
        }*/
    }


}

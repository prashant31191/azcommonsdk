package com.azsamplesdk.ads;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azsamplesdk.R;
import com.azsamplesdk.location.CLocation;
import com.azsdk.ads.AdsDisplayUtil;
import com.azsdk.ads.AdsLoader;
import com.azsdk.location.utils.ErrorModel;
import com.azsdk.location.utils.MyLocationService;
import com.azsdk.location.utils.ResponseModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Formatter;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

import static android.widget.Toast.LENGTH_SHORT;

public class AdsSampleActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tvData)
    TextView tvData;
    @BindView(R.id.rlAds)
    RelativeLayout rlAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_sample);
        ButterKnife.bind(this);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvData.setText("Please wait...");

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                AdsDisplayUtil.openBnrIntAdsScreen(AdsSampleActivity.this, "", "");
            }
        });


        //When click open Full screen ads
        //For Interstitial Ads

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                //Load Interstitial Ads
                AdsLoader.loadIntAds(AdsSampleActivity.this, "Add_Here_Interstitial_Ads_Unit_Id");
            }
        });


        // code write in on create
        AdsLoader.setIntAdsNull();

    }

    @OnClick(R.id.tvData)
    public void clicktvData(TextView view) {
        view.setText("Clear--1");

        AdsLoader.loadBannerAds(AdsSampleActivity.this, rlAds, "");
    }

    @OnLongClick(R.id.tvData)
    public boolean longClicktvData(TextView view) {
        view.setText("-longClicktvData-Clear--1");
        Toast.makeText(this, "-Please wait-", LENGTH_SHORT).show();
        AdsLoader.loadIntAds(AdsSampleActivity.this, "");
        return true;
    }

/*
    @OnLongClick(R2.id.hello)
    boolean sayGetOffMe() {
        Toast.makeText(this, "Let go of me!", LENGTH_SHORT).show();
        return true;
    }*/


}

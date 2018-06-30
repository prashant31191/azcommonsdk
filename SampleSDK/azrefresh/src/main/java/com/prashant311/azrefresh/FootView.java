package com.prashant311.azrefresh;


import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class FootView extends FrameLayout implements FooterListener {

    //    private final ImageView mHeaderImageView;
//    private final AnimationDrawable mFrameAnimation;
//    private final int mNumberOfFrames;
    private final TextView mFoot;

    public FootView(Context context) {
        super(context);
        float density = context.getResources().getDisplayMetrics().density;
        mFoot = new TextView(context);
        mFoot.setBackgroundColor(Color.RED);
        mFoot.setText("Loading...");
        mFoot.setGravity(Gravity.CENTER);
        addView(mFoot, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (density * 60)));

//        LayoutInflater.from(context).inflate(R.layout.layout_header_loading, this, true);
//        mHeaderImageView = (ImageView) findViewById(R.id.pull_to_refresh_image);
//
//        mFrameAnimation = (AnimationDrawable) mHeaderImageView.getBackground();
//        mNumberOfFrames = mFrameAnimation.getNumberOfFrames();
    }

    @Override
    public void onLoadBefore(int scrollY) {

    }

    @Override
    public void onLoadAfter(int scrollY) {

    }

    @Override
    public void onLoadReady(int scrollY) {

    }

    @Override
    public void onLoading(int scrollY) {
    }

    @Override
    public void onLoadComplete(int scrollY, boolean isLoadSuccess) {

    }

    @Override
    public void onLoadCancel(int scrollY) {

    }
}

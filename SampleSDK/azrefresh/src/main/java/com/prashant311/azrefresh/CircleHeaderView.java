package com.prashant311.azrefresh;


import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class CircleHeaderView extends FrameLayout implements HeaderListener {

    public TouchCircleView getmHeader() {
        return mHeader;
    }

    //    private final ImageView mHeaderImageView;
//    private final AnimationDrawable mFrameAnimation;
//    private final int mNumberOfFrames;
    private final TouchCircleView mHeader;

    public CircleHeaderView(Context context) {
        super(context);
        float density = context.getResources().getDisplayMetrics().density;
        mHeader = new TouchCircleView(context);

        addView(mHeader, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (density * 150)));

//        LayoutInflater.from(context).inflate(R.layout.layout_header_loading, this, true);
//        mHeaderImageView = (ImageView) findViewById(R.id.pull_to_refresh_image);
//
//        mFrameAnimation = (AnimationDrawable) mHeaderImageView.getBackground();
//        mNumberOfFrames = mFrameAnimation.getNumberOfFrames();
    }

    /**
     * Pull down to refresh
     *
     * @param scrollY
     */
    @Override
    public void onRefreshBefore(int scrollY, int headerHeight) {
        Log.e("TAG", "onRefreshBefore: " + scrollY);
        mHeader.handleOffset(-scrollY);
//        mHeaderImageView.setBackgroundDrawable(mFrameAnimation.getFrame(current));

    }

    /**
     * Release refresh
     *
     * @param scrollY
     */
    @Override
    public void onRefreshAfter(int scrollY, int headerHeight) {
//        mHeaderImageView.setBackgroundDrawable(mFrameAnimation.getFrame(mNumberOfFrames - 1));
    }

    /**
     * Ready to refresh
     *
     * @param scrollY
     */
    @Override
    public void onRefreshReady(int scrollY, int headerHeight) {
        mHeader.setRefresh(true);
    }

    /**
     * Refreshing
     *
     * @param scrollY
     */
    @Override
    public void onRefreshing(int scrollY, int headerHeight) {
        mHeader.setRefresh(true);
    }

    /**
     * this method will callback not only once.
     */
    @Override
    public void onRefreshComplete(int scrollY, int headerHeight, boolean isRefreshSuccess) {
        if (isRefreshSuccess) {
            mHeader.setRefreshSuccess();
        } else {
            mHeader.setRefreshError();
        }

    }

    /**
     * Cancel refresh
     *
     * @param scrollY
     */
    @Override
    public void onRefreshCancel(int scrollY, int headerHeight) {

    }

    @Override
    public int getRefreshHeight() {
        return (int) (getMeasuredHeight()*0.8f);
    }

}
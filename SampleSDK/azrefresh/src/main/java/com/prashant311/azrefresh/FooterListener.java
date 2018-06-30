package com.prashant311.azrefresh;


public interface FooterListener {

    void onLoadBefore(int scrollY);

    void onLoadAfter(int scrollY);

    void onLoadReady(int scrollY);

    void onLoading(int scrollY);

    void onLoadComplete(int scrollY, boolean isLoadSuccess);

    void onLoadCancel(int scrollY);
}

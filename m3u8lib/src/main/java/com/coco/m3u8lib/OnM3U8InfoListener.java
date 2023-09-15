package com.coco.m3u8lib;


import com.coco.m3u8lib.BaseListener;
import com.coco.m3u8lib.bean.M3U8;


public interface OnM3U8InfoListener extends BaseListener {

    @Override
    void onStart();

    void onSuccess(M3U8 m3U8);

    @Override
    void onError(Throwable errorMsg);
}

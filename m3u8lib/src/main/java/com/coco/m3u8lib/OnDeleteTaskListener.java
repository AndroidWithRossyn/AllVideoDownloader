package com.coco.m3u8lib;


public interface OnDeleteTaskListener extends BaseListener {
    void onStart();

    void onSuccess();

    void onFail();
}

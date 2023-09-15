package com.coco.m3u8lib;

public interface BaseListener {
    void onStart();

    void onError(Throwable errorMsg);
}

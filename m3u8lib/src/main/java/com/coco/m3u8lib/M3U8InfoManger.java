package com.coco.m3u8lib;


import java.io.IOException;

import com.coco.m3u8lib.bean.M3U8;
import com.coco.m3u8lib.utils.MUtils;


public class M3U8InfoManger {
    private static M3U8InfoManger mM3U8InfoManger;
    private OnM3U8InfoListener onM3U8InfoListener;

    private M3U8InfoManger() {
    }

    public static M3U8InfoManger getInstance() {
        synchronized (M3U8InfoManger.class) {
            if (mM3U8InfoManger == null) {
                mM3U8InfoManger = new M3U8InfoManger();
            }
        }
        return mM3U8InfoManger;
    }


    public synchronized void getM3U8Info(final String url, OnM3U8InfoListener onM3U8InfoListener) {
        this.onM3U8InfoListener = onM3U8InfoListener;
        onM3U8InfoListener.onStart();
        new Thread() {
            @Override
            public void run() {
                try {
                    M3U8 m3u8 = MUtils.parseIndex(url);
                    handlerSuccess(m3u8);
                } catch (IOException e) {
                    handlerError(e);
                }
            }
        }.start();

    }


    private void handlerError(Throwable e) {
        onM3U8InfoListener.onError(e);
    }


    private void handlerSuccess(M3U8 m3u8) {
        onM3U8InfoListener.onSuccess(m3u8);
    }
}

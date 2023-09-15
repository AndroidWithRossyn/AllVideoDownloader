package com.yxcorp.gifshow.util;
import android.content.Context;

public abstract class CPU {
    static {
        System.loadLibrary("core");
    }

    public static native int getCheckRes(Context context, int i);

    public static native String getClock(Context context, byte[] bArr, int i);

    public static native String getMagic(Context context, int i);

    public static synchronized String getMagicData(Context context, int i) {
        String magic;
        Class<CPU> cls = CPU.class;
        synchronized (cls) {
            synchronized (cls) {
                magic = getMagic(context, i);
            }
        }
        return magic;
    }

    public static synchronized String getClockData(Context context, byte[] bArr, int i) {
        String clock;
        Class<CPU> cls = CPU.class;
        synchronized (cls) {
            synchronized (cls) {
                clock = getClock(context, bArr, i);
            }
        }
        return clock;
    }

    public static synchronized int getCheckResData(Context context, int i) {
        int checkRes;
        Class<CPU> cls = CPU.class;
        synchronized (cls) {
            synchronized (cls) {
                checkRes = getCheckRes(context, i);
            }
        }
        return checkRes;
    }
}

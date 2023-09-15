package com.coco.m3u8lib.bean;

import com.coco.m3u8lib.utils.MUtils;


public class M3U8Task {

    private String url;
    private int state = M3U8TaskState.DEFAULT;
    private long speed;
    private float progress;
    private com.coco.m3u8lib.bean.M3U8 m3U8;

    private M3U8Task(){}

    public M3U8Task(String url){
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof M3U8Task){
            M3U8Task m3U8Task = (M3U8Task)obj;
            if (url != null && url.equals(m3U8Task.getUrl()))return true;
        }
        return false;
    }

    public String getFormatSpeed() {
        if (speed == 0)return "";
        return MUtils.formatFileSize(speed) + "/s";
    }

    public String getFormatTotalSize() {
        if (m3U8 == null)return "";
        return m3U8.getFormatFileSize();
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public com.coco.m3u8lib.bean.M3U8 getM3U8() {
        return m3U8;
    }

    public void setM3U8(M3U8 m3U8) {
        this.m3U8 = m3U8;
    }

}

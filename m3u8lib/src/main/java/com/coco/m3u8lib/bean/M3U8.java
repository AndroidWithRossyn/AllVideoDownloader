package com.coco.m3u8lib.bean;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.coco.m3u8lib.bean.M3U8Ts;
import com.coco.m3u8lib.utils.MUtils;

public class M3U8 {
    private String basePath;
    private String m3u8FilePath;
    private String dirFilePath;
    private long fileSize;
    private long totalTime;
    private List<M3U8Ts> tsList = new ArrayList<M3U8Ts>();

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getM3u8FilePath() {
        return m3u8FilePath;
    }

    public void setM3u8FilePath(String m3u8FilePath) {
        this.m3u8FilePath = m3u8FilePath;
    }

    public String getDirFilePath() {
        return dirFilePath;
    }

    public void setDirFilePath(String dirFilePath) {
        this.dirFilePath = dirFilePath;
    }

    public long getFileSize() {
        fileSize = 0;
        for (M3U8Ts m3U8Ts : tsList){
            fileSize = fileSize + m3U8Ts.getFileSize();
        }
        return fileSize;
    }

    public String getFormatFileSize() {
        fileSize = getFileSize();
        if (fileSize == 0)return "";
        return MUtils.formatFileSize(fileSize);
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public List<M3U8Ts> getTsList() {
        return tsList;
    }

    public void setTsList(List<M3U8Ts> tsList) {
        this.tsList = tsList;
    }

    public void addTs(M3U8Ts ts) {
        this.tsList.add(ts);
    }

    public long getTotalTime(){
        totalTime = 0;
        for (M3U8Ts m3U8Ts : tsList){
            totalTime = totalTime + (int)(m3U8Ts.getSeconds() * 1000);
        }
        return totalTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("basePath: " + basePath);
        sb.append("\nm3u8FilePath: " + m3u8FilePath);
        sb.append("\ndirFilePath: " + dirFilePath);
        sb.append("\nfileSize: " + getFileSize());
        sb.append("\nfileFormatSize: " + MUtils.formatFileSize(fileSize));
        sb.append("\ntotalTime: " + totalTime);

        for (M3U8Ts ts : tsList) {
            sb.append("\nts: " + ts);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof M3U8){
            M3U8 m3U8 = (M3U8)obj;
            if (basePath != null && basePath.equals(m3U8.basePath))return true;
        }
        return false;
    }
}

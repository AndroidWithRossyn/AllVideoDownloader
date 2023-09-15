package com.coco.m3u8lib.bean;
import androidx.annotation.NonNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.coco.m3u8lib.utils.MD5Utils;

public class M3U8Ts implements Comparable<M3U8Ts> {
    private String url;
    private long fileSize;
    private float seconds;
    private String mp4URL;

    private String fileName;

    public M3U8Ts(String url, float seconds) {
        this.url = url;
        this.seconds = seconds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getSeconds() {
        return seconds;
    }

    public void setSeconds(float seconds) {
        this.seconds = seconds;
    }
    public String getMp4URL() {
        return mp4URL;
    }

    public void setMp4URL(String mp4URL) {
        this.mp4URL = mp4URL;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String obtainEncodeTsFileName(){
        if (url == null)return "error.ts";
        return MD5Utils.encode(url).concat(".ts");
    }

    public String obtainFullUrl(String hostUrl){
        if (url == null) {
            return null;
        }
        if (url.startsWith("http")) {
            return url;
        }else if (url.startsWith("//")) {
            return "http:".concat(url);
        }else {
            URL _url= null;
            try {
                _url = new URL(hostUrl);
                String _mainUrl=_url.getProtocol()+"://" +_url.getAuthority();
                String RemainString= hostUrl.replace(_mainUrl,"");
                if( RemainString !=null & RemainString !="")
                {
                    if(url.contains(RemainString))
                    {
                        return _mainUrl.concat(url);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            return hostUrl.concat(url);
        }
    }
    @Override
    public String toString() {
        return url + " (" + seconds + "sec)";
    }

    public long getLongDate() {
        try {
            return Long.parseLong(url.substring(0, url.lastIndexOf(".")));
        }catch (NumberFormatException e){
            return 0;
        }
    }

    @Override
    public int compareTo(@NonNull M3U8Ts o) {
        return url.compareTo(o.url);
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}

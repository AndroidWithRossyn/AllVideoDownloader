package com.video.music.downloader.statusandgallery.model.downloader.story;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CandidatesModel implements Serializable {

    @SerializedName("width")
    private int width;
    @SerializedName("height")
    private int height;
    @SerializedName("url")
    private String url;
    @SerializedName("scans_profile")
    private String scans_profile;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getScans_profile() {
        return scans_profile;
    }

    public void setScans_profile(String scans_profile) {
        this.scans_profile = scans_profile;
    }
}

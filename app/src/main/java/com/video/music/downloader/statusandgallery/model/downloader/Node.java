package com.video.music.downloader.statusandgallery.model.downloader;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Node implements Serializable {


    @SerializedName("display_url")
    private String display_url;

    @SerializedName("display_resources")
    private List<DisplayResource> display_resources;

    @SerializedName("is_video")
    private boolean is_video;

    @SerializedName("video_url")
    private String video_url;

    public String getDisplay_url() {
        return display_url;
    }

    public void setDisplay_url(String display_url) {
        this.display_url = display_url;
    }

    public List<DisplayResource> getDisplay_resources() {
        return display_resources;
    }

    public void setDisplay_resources(List<DisplayResource> display_resources) {
        this.display_resources = display_resources;
    }

    public boolean isIs_video() {
        return is_video;
    }

    public void setIs_video(boolean is_video) {
        this.is_video = is_video;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }
}

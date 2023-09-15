package com.video.music.downloader.statusandgallery.model.downloader.story;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class StoryModel implements Serializable {

    @SerializedName("tray")
    private ArrayList<TrayModel> tray;

    @SerializedName("status")
    private String status;

    public ArrayList<TrayModel> getTray() {
        return tray;
    }

    public void setTray(ArrayList<TrayModel> tray) {
        this.tray = tray;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

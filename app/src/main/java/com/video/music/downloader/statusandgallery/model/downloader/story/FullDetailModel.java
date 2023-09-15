package com.video.music.downloader.statusandgallery.model.downloader.story;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FullDetailModel implements Serializable {

    @SerializedName("user_detail")
    private UserDetailModel user_detail;

    @SerializedName("reel_feed")
    private ReelFeedModel reel_feed;

    public UserDetailModel getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(UserDetailModel user_detail) {
        this.user_detail = user_detail;
    }

    public ReelFeedModel getReel_feed() {
        return reel_feed;
    }

    public void setReel_feed(ReelFeedModel reel_feed) {
        this.reel_feed = reel_feed;
    }
}

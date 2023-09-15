package com.video.music.downloader.statusandgallery.model.downloader.FBStoryModel;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MediaDataModel implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("__typename")
    private String __typename;

    @SerializedName("playable_url_quality_hd")
    private String playable_url_quality_hd;

    @SerializedName("previewImage")
    private JsonObject previewImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String get__typename() {
        return __typename;
    }

    public void set__typename(String __typename) {
        this.__typename = __typename;
    }

    public String getPlayable_url_quality_hd() {
        return playable_url_quality_hd;
    }

    public void setPlayable_url_quality_hd(String playable_url_quality_hd) {
        this.playable_url_quality_hd = playable_url_quality_hd;
    }

    public JsonObject getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(JsonObject previewImage) {
        this.previewImage = previewImage;
    }
}

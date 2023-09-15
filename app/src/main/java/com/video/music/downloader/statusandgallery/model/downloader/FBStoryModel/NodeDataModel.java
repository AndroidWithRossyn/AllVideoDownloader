package com.video.music.downloader.statusandgallery.model.downloader.FBStoryModel;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class NodeDataModel  implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("story_bucket_owner")
    private JsonObject story_bucket_owner;

    @SerializedName("owner")
    private JsonObject owner;

    @SerializedName("attachments")
    private ArrayList<MediaModel> attachmentsList;

    public JsonObject getOwner() {
        return owner;
    }

    public void setOwner(JsonObject owner) {
        this.owner = owner;
    }

    public JsonObject getStory_bucket_owner() {
        return story_bucket_owner;
    }

    public void setStory_bucket_owner(JsonObject story_bucket_owner) {
        this.story_bucket_owner = story_bucket_owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<MediaModel> getAttachmentsList() {
        return attachmentsList;
    }

    public void setAttachmentsList(ArrayList<MediaModel> attachmentsList) {
        this.attachmentsList = attachmentsList;
    }
}
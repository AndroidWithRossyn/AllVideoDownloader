package com.video.music.downloader.statusandgallery.model.downloader.story;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("pk")
    private long pk;
    @SerializedName("username")
    private String username;
    @SerializedName("full_name")
    private String full_name;
    @SerializedName("is_private")
    private boolean is_private;
    @SerializedName("profile_pic_url")
    private String profile_pic_url;
    @SerializedName("profile_pic_id")
    private String profile_pic_id;
    @SerializedName("is_verified")
    private boolean is_verified;
    @SerializedName("media_count")
    private int media_count;
    @SerializedName("follower_count")
    private int follower_count;
    @SerializedName("following_count")
    private int following_count;
    @SerializedName("biography")
    private String  biography;
    @SerializedName("total_igtv_videos")
    private String  total_igtv_videos;
    @SerializedName("hd_profile_pic_url_info")
    private HDProfileModel  hdProfileModel;
    @SerializedName("mutual_followers_count")
    private int  mutual_followers_count;
    @SerializedName("profile_context")
    private String  profile_context;


    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public boolean isIs_private() {
        return is_private;
    }

    public void setIs_private(boolean is_private) {
        this.is_private = is_private;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }

    public String getProfile_pic_id() {
        return profile_pic_id;
    }

    public void setProfile_pic_id(String profile_pic_id) {
        this.profile_pic_id = profile_pic_id;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public int getMedia_count() {
        return media_count;
    }

    public void setMedia_count(int media_count) {
        this.media_count = media_count;
    }

    public int getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(int follower_count) {
        this.follower_count = follower_count;
    }

    public int getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(int following_count) {
        this.following_count = following_count;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getTotal_igtv_videos() {
        return total_igtv_videos;
    }

    public void setTotal_igtv_videos(String total_igtv_videos) {
        this.total_igtv_videos = total_igtv_videos;
    }

    public HDProfileModel getHdProfileModel() {
        return hdProfileModel;
    }

    public void setHdProfileModel(HDProfileModel hdProfileModel) {
        this.hdProfileModel = hdProfileModel;
    }

    public int getMutual_followers_count() {
        return mutual_followers_count;
    }

    public void setMutual_followers_count(int mutual_followers_count) {
        this.mutual_followers_count = mutual_followers_count;
    }

    public String getProfile_context() {
        return profile_context;
    }

    public void setProfile_context(String profile_context) {
        this.profile_context = profile_context;
    }
}

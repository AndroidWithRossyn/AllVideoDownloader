package com.video.music.downloader.videoplayer.data.model.youtube;


import android.os.Parcel;
import android.os.Parcelable;

public class Snippet implements Parcelable {
    private String title;
    private String channelTitle;
    private ThumbNail thumbnails;

    protected Snippet(Parcel in) {
        title = in.readString();
        channelTitle = in.readString();
    }

    public static final Creator<Snippet> CREATOR = new Creator<Snippet>() {
        @Override
        public Snippet createFromParcel(Parcel in) {
            return new Snippet(in);
        }

        @Override
        public Snippet[] newArray(int size) {
            return new Snippet[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public ThumbNail getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(ThumbNail thumbnails) {
        this.thumbnails = thumbnails;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(channelTitle);
    }
}

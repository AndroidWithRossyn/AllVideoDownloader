package com.video.music.downloader.videoplayer.data.model.youtube;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoID implements Parcelable {
    private String videoId;

    protected VideoID(Parcel in) {
        videoId = in.readString();
    }

    public static final Creator<VideoID> CREATOR = new Creator<VideoID>() {
        @Override
        public VideoID createFromParcel(Parcel in) {
            return new VideoID(in);
        }

        @Override
        public VideoID[] newArray(int size) {
            return new VideoID[size];
        }
    };

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(videoId);
    }
}

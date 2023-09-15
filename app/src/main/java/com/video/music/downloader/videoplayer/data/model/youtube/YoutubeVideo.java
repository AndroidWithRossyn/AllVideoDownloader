package com.video.music.downloader.videoplayer.data.model.youtube;

import android.os.Parcel;
import android.os.Parcelable;

public class YoutubeVideo implements Parcelable {
    private VideoID id;
    private Snippet snippet;

    protected YoutubeVideo(Parcel in) {
        id = in.readParcelable(VideoID.class.getClassLoader());
        snippet = in.readParcelable(Snippet.class.getClassLoader());
    }

    public static final Creator<YoutubeVideo> CREATOR = new Creator<YoutubeVideo>() {
        @Override
        public YoutubeVideo createFromParcel(Parcel in) {
            return new YoutubeVideo(in);
        }

        @Override
        public YoutubeVideo[] newArray(int size) {
            return new YoutubeVideo[size];
        }
    };

    public VideoID getId() {
        return id;
    }

    public void setId(VideoID id) {
        this.id = id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(id, i);
        parcel.writeParcelable(snippet, i);
    }
}

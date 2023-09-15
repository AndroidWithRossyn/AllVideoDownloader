package com.video.music.downloader.videoplayer.data.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {
    private long song_id;
    private String title;
    private String artist;
    private Uri coverUri;

    public Song(long song_id, String title, String artist, Uri coverUri) {
        this.song_id = song_id;
        this.title = title;
        this.artist = artist;
        this.coverUri = coverUri;
    }

    protected Song(Parcel in) {
        song_id = in.readLong();
        title = in.readString();
        artist = in.readString();
        coverUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public long getId() {
            return song_id;
        }

    public void setId(long song_id) {
            this.song_id = song_id;
        }

    public String getTitle() {
            return title;
        }

    public void setTitle(String title) {
            this.title = title;
        }

    public String getArtist() {
            return artist;
        }

    public void setArtist(String artist) {
            this.artist = artist;
        }

    public Uri getCoverUri() {
        return coverUri;
    }

    public void setCoverUri(Uri coverUri) {
        this.coverUri = coverUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(song_id);
        parcel.writeString(title);
        parcel.writeString(artist);
        parcel.writeParcelable(coverUri, i);
    }
}

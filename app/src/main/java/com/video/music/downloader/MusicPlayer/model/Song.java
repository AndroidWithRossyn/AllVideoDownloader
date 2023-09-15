package com.video.music.downloader.MusicPlayer.model;

import android.net.Uri;

public class Song {
    //member variables
    long id;
    Uri uri;
    String name;
    int duration;
    int size;
    long albumId;
    Uri albumartUri;

    //constructor


    public Song(long id, Uri uri, String name, int duration, int size, long albumId, Uri albumartUri) {
        this.id = id;
        this.uri = uri;
        this.name = name;
        this.duration = duration;
        this.size = size;
        this.albumId = albumId;
        this.albumartUri = albumartUri;
    }

    public Song() {

    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public void setAlbumartUri(Uri albumartUri) {
        this.albumartUri = albumartUri;
    }
    //getters

    public long getId() {
        return id;
    }

    public Uri getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getSize() {
        return size;
    }

    public long getAlbumId() {
        return albumId;
    }

    public Uri getAlbumartUri() {
        return albumartUri;
    }
}

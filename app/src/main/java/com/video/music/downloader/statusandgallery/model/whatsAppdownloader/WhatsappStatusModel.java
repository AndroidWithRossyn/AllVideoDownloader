package com.video.music.downloader.statusandgallery.model.whatsAppdownloader;

import android.net.Uri;


public class WhatsappStatusModel {
    private String name;
    private Uri uri;
    private String path;
    private String filename;
    public WhatsappStatusModel(String name, Uri uri, String path, String filename ) {
        this.name = name;
        this.uri = uri;
        this.path = path;
        this.filename = filename;
    }
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}

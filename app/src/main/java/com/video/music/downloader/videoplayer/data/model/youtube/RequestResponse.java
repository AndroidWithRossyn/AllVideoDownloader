package com.video.music.downloader.videoplayer.data.model.youtube;

import java.util.ArrayList;

public class RequestResponse {
    private ArrayList<YoutubeVideo> items;

    public ArrayList<YoutubeVideo> getItems() {
        return items;
    }

    public void setItems(ArrayList<YoutubeVideo> items) {
        this.items = items;
    }
}

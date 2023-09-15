package com.video.music.downloader.statusandgallery.model.downloader;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EdgeSidecarToChildren implements Serializable {

    @SerializedName("edges")
    private List<Edge> edges;

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
}

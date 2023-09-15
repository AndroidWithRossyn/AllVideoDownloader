package com.video.music.downloader.statusandgallery.model.downloader.FBStoryModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class EdgesModel implements Serializable {
    @SerializedName("edges")
    private ArrayList<NodeModel> edgeModel;

    public ArrayList<NodeModel> getEdgeModel() {
        return edgeModel;
    }

    public void setEdgeModel(ArrayList<NodeModel> edgeModel) {
        this.edgeModel = edgeModel;
    }
}

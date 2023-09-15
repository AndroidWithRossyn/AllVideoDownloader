package com.video.music.downloader.statusandgallery.model.downloader.story;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ImageVersionModel implements Serializable {

    @SerializedName("candidates")
    private ArrayList<CandidatesModel> candidates;

    public ArrayList<CandidatesModel> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<CandidatesModel> candidates) {
        this.candidates = candidates;
    }
}

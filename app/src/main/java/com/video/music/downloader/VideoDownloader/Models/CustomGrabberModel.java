package com.video.music.downloader.VideoDownloader.Models;

public class CustomGrabberModel {
    private String VideoUrl;
    private file_type _file_type;
    private Boolean IsM3u8;

    public CustomGrabberModel()
    {
        this.IsM3u8=false;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public file_type get_file_type() {
        return _file_type;
    }

    public void set_file_type(file_type _file_type) {
        this._file_type = _file_type;
    }

    public Boolean getM3u8() {
        return IsM3u8;
    }

    public void setM3u8(Boolean m3u8) {
        IsM3u8 = m3u8;
    }
}

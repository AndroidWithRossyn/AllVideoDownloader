package com.video.music.downloader.statusandgallery.model

class VidesoModel {
    var videoName: String? = null
    var videoPath: String? = null
    var videoSize: String? = null
    var videoUri: String? = null
    var selected = false

    constructor() {}
    constructor(
        videoName: String?,
        videoUri: String?,
        videoPath: String?,
        videoSize: String?
    ) {
        this.videoName = videoName
        this.videoPath = videoPath
        this.videoSize = videoSize
        this.videoUri = videoUri
    }
}

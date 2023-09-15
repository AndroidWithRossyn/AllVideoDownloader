package com.video.music.downloader.statusandgallery.model

class videoFolder {
    var path: String? = null
    var folderName: String? = null
    var numberOfvideos = 0
    var firstvideo: String? = null

    fun addvideo() {
        numberOfvideos++
    }

    constructor(path: String?, folderName: String?) {
        var folderName = folderName
        this.path = path
        folderName = folderName
    }
    constructor() {}
}
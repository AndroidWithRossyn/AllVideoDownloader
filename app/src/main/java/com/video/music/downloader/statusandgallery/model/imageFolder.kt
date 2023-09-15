package com.video.music.downloader.statusandgallery.model

class imageFolder {
    var path: String? = null
    var folderName: String? = null
    var numberOfPics = 0
    var firstPic: String? = null
//    var datetaken: String? = null

    constructor() {}
    constructor(path: String?, folderName: String?,datetaken:String?) {
        this.path = path
        this.folderName = folderName
//        this.datetaken = datetaken
    }

    fun addpics() {
        numberOfPics++
    }
}

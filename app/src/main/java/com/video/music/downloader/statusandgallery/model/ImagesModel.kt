package com.video.music.downloader.statusandgallery.model

import android.net.Uri

class ImagesModel {

    var picturName: String? = null
    var picturePath: String? = null
    var pictureSize: String? = null
//    var picturedate: String? = null
    var imageUri: Uri? = null
    var selected = false

    constructor() {}
    constructor(
        picturName: String?,
        imageUri: Uri?,
        picturePath: String?,
        pictureSize: String?,
//        picturedate: String?
    ) {
        this.picturName = picturName
        this.picturePath = picturePath
        this.pictureSize = pictureSize
        this.imageUri = imageUri
//        this.picturedate=picturedate
    }
}
package com.video.music.downloader.statusandgallery.utils

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.video.music.downloader.R

class indicatorHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var image: ImageView
    private val card: CardView
    var positionController: View

    init {
        image = itemView.findViewById(R.id.imageIndicator)
        card = itemView.findViewById(R.id.indicatorCard)
        positionController = itemView.findViewById(R.id.activeImage)
    }
}
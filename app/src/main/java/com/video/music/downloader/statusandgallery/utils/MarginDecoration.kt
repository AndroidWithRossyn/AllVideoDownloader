package com.video.music.downloader.statusandgallery.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.video.music.downloader.R

class MarginDecoration(context: Context) : ItemDecoration() {
    private val margin: Int

    init {
        margin = context.resources.getDimensionPixelSize(R.dimen.item_margin)
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect[margin, margin, margin] = margin
    }
}
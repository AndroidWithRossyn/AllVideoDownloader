package com.video.music.downloader.statusandgallery.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.video.music.downloader.R
import com.video.music.downloader.statusandgallery.model.ImagesModel

class recyclerViewPagerImageIndicator(
    pictureList: ArrayList<ImagesModel>,
    pictureContx: Context,
    imageListerner: imageIndicatorListener
) :
    RecyclerView.Adapter<indicatorHolder>() {
    var pictureList: ArrayList<ImagesModel>
    var pictureContx: Context
    private val imageListerner: imageIndicatorListener

    /**
     *
     * @param pictureList ArrayList of pictureFacer objects
     * @param pictureContx The Activity of fragment context
     * @param imageListerner Interface for communication between adapter and fragment
     */
    init {
        this.pictureList = pictureList
        this.pictureContx = pictureContx
        this.imageListerner = imageListerner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): indicatorHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cell: View = inflater.inflate(R.layout.indicator_holder, parent, false)
        return indicatorHolder(cell)
    }

    override fun onBindViewHolder(
        holder: indicatorHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val pic: ImagesModel = pictureList[position]
        holder.positionController.setBackgroundColor(
            if (pic.selected) Color.parseColor("#00000000") else Color.parseColor(
                "#8c000000"
            )
        )
        Glide.with(pictureContx)
            .load(pic.picturePath)
            .apply(RequestOptions().centerCrop())
            .into(holder.image)
        holder.image.setOnClickListener(View.OnClickListener { //holder.card.setCardElevation(5);
            pic.selected=true
            notifyDataSetChanged()
            imageListerner.onImageIndicatorClicked(position)
        })
    }

    override fun getItemCount(): Int {
        return pictureList.size
    }
}
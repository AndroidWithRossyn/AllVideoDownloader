package com.video.music.downloader.statusandgallery.utils;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.video.music.downloader.R;


public class PicHolder extends RecyclerView.ViewHolder{

    public ImageView picture;
    public CheckBox favorite;

    public PicHolder(@NonNull View itemView) {
        super(itemView);
        picture = itemView.findViewById(R.id.image);
        favorite = itemView.findViewById(R.id.image1);
    }
}

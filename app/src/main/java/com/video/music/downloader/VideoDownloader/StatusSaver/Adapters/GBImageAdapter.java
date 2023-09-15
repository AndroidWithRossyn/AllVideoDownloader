package com.video.music.downloader.VideoDownloader.StatusSaver.Adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.video.music.downloader.VideoDownloader.StatusSaver.Model.ModelStatus;
import com.video.music.downloader.R;
import com.video.music.downloader.VideoDownloader.StatusSaver.activities.GBImageActivity;
import com.video.music.downloader.VideoDownloader.StatusSaver.fragments.GBImageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GBImageAdapter extends RecyclerView.Adapter<GBImageAdapter.ImageViewHolder> {

    public List<ModelStatus> imagesList = new ArrayList<>();
    public List<ModelStatus> selected_imageList = new ArrayList<>();
    Context acontext;
    GBImageFragment imageFragment;
    public final static String POSITION = "Image Position";

    public GBImageAdapter(Context context, List<ModelStatus> imageList, List<ModelStatus> selected_image, GBImageFragment imageFragment){
        this.acontext = context;
        this.imagesList = imageList;
        this.selected_imageList = selected_image;
        this.imageFragment = imageFragment;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(acontext).inflate(R.layout.item_status, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        final ModelStatus statusModel = imagesList.get(position);

        Glide.with(acontext).load(statusModel.getFull_path()).into(holder.ivThumbnailImageView);

        if (selected_imageList.contains(imagesList.get(position))){
            holder.opacity.setBackgroundColor(ContextCompat.getColor(acontext, R.color.image_selected));
            holder.opacity.setVisibility(View.VISIBLE);
        }else {
            holder.opacity.setBackgroundColor(ContextCompat.getColor(acontext, R.color.image_not_selected));
            holder.opacity.setVisibility(View.GONE);
        }

        holder.ivThumbnailImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int pos = holder.getAdapterPosition();
                String stringId = Integer.valueOf(pos).toString();

                Intent intent = new Intent(acontext, GBImageActivity.class);
                intent.putExtra(POSITION, stringId);
                intent.putExtra("image", statusModel.getFull_path());
                intent.putExtra("myPath", statusModel.getPath());
                intent.putExtra("myPackage", "1");
                intent.putExtra("type", "" + statusModel.getType());
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                acontext.startActivity(intent);
                ((Activity) acontext).overridePendingTransition(R.anim.enter, R.anim.pop_enter);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivThumbnail) ImageView ivThumbnailImageView;
        @BindView(R.id.opacity) LinearLayout opacity;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ivThumbnailImageView= itemView.findViewById(R.id.ivThumbnail);
            opacity=itemView.findViewById(R.id.opacity);
        }
    }

}

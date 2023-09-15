package com.video.music.downloader.videoplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.video.music.downloader.R;
import com.video.music.downloader.videoplayer.data.model.youtube.YoutubeVideo;

import java.util.List;


public class Top100PlaylistAdapter extends RecyclerView.Adapter<Top100PlaylistAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    public List<YoutubeVideo> songs;

    private final Callback callback;

    public Top100PlaylistAdapter(Context context, List<YoutubeVideo> songs, Callback callback) {
        this.songs = songs;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_top_100_playlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        YoutubeVideo youtubeVideo = songs.get(position);

        holder.tvTitle.setText(youtubeVideo.getSnippet().getTitle());
        holder.tvArtist.setText(youtubeVideo.getSnippet().getChannelTitle());

        Glide.with(inflater.getContext())
                .load(youtubeVideo.getSnippet().getThumbnails().getMedium().getUrl())
                .apply(RequestOptions.placeholderOf(R.drawable.song_cover).centerInside())
                .into(holder.ivCover);

        holder.itemView.setOnClickListener(v -> {
            if (callback != null) {
                callback.onVideoClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle;
        final TextView tvArtist;
        final ImageView ivCover;

        ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_song_title_top_100);
            tvArtist = view.findViewById(R.id.tv_song_artist_top_100);
            ivCover = view.findViewById(R.id.iv_song_cover_top_100);
        }
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public interface Callback {
        void onVideoClick(int position);
    }
}

package com.video.music.downloader.videoplayer.data.model;

import com.video.music.downloader.databinding.FragmentPlayerBinding;

import java.util.List;


public interface ISongList {
    void fragmentSongList (List<Song> songs, int position);

    void fragmentPlayerNext();

    void fragmentPlayerPause();

    void fragmentPlayerPrevious();

    void fragmentPlayerBinding(FragmentPlayerBinding fragmentPlayerBinding);

    void fragmentPlayerDisplayProgress(boolean displayProgress);

    void fragmentPlayerSetProgress(int progress);
}
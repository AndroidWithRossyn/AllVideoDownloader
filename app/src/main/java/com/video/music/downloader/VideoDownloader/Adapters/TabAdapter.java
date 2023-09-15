package com.video.music.downloader.VideoDownloader.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.video.music.downloader.VideoDownloader.Fragments.VideosFragment;
import com.video.music.downloader.VideoDownloader.Models.file_type;

public class TabAdapter extends FragmentStateAdapter {
    public TabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0)
        {
            return VideosFragment.newInstance(file_type.VIDEO);
        }
        else if (position==1)
        {
            return VideosFragment.newInstance(file_type.AUDIO);
        }
        else
        {
            return VideosFragment.newInstance(file_type.IMAGE);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

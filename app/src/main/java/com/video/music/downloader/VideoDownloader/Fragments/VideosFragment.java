package com.video.music.downloader.VideoDownloader.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.video.music.downloader.VideoDownloader.Adapters.DownloadsAdapter;
import com.video.music.downloader.VideoDownloader.Configs.SettingsManager;
import com.video.music.downloader.VideoDownloader.Models.file_type;
import com.video.music.downloader.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class VideosFragment extends Fragment {


    private View view;
    private RecyclerView downloadsList;
    private DownloadsAdapter adapter;
    file_type File_type;

    public VideosFragment(file_type _file_type) {
        File_type=_file_type;
    }


    public static VideosFragment newInstance(file_type _file_type) {
        VideosFragment fragment = new VideosFragment(_file_type);
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view==null)
        {
            view=inflater.inflate(R.layout.fragment_videos, container, false);
            downloadsList = view.findViewById(R.id.downloadsCompletedList);

            File videoFile = new File(SettingsManager.DOWNLOAD_FOLDER_VIDEO);
            if(File_type==file_type.AUDIO)
            {
                videoFile = new File(SettingsManager.DOWNLOAD_FOLDER_AUDIO);
            }
            if(File_type==file_type.IMAGE)
            {
                videoFile = new File(SettingsManager.DOWNLOAD_FOLDER_IMAGES);
            }
            if(videoFile.exists())
            {
                List<File> nonExistentFiles = new ArrayList<>(Arrays.asList(videoFile.listFiles()));
                if(!nonExistentFiles.isEmpty())
                {
                    adapter=new DownloadsAdapter(nonExistentFiles,getActivity(),File_type);
                    downloadsList.setAdapter(adapter);
                    downloadsList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    downloadsList.setHasFixedSize(true);
                }
            }
        }

        return view;
    }
}
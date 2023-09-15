package com.video.music.downloader.videoplayer.fragment;

import static com.video.music.downloader.videoplayer.data.SongHelper.getLong;
import static com.video.music.downloader.videoplayer.data.SongHelper.getTime;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.video.music.downloader.R;
////import com.video.music.downloader.statusandgallery.AdsUtils.FirebaseADHandlers.AdUtils;
//import com.video.music.downloader.statusandgallery.AdsUtils.Utils.Constants;
import com.video.music.downloader.videoplayer.VideoPlayerActivity;
import com.video.music.downloader.videoplayer.adapter.SongListAdapter;
import com.video.music.downloader.videoplayer.adapter.VideoFilesAdapter;
import com.video.music.downloader.videoplayer.data.VideoData;
import com.video.music.downloader.videoplayer.data.model.ISongList;
import com.video.music.downloader.videoplayer.data.model.Song;
import com.video.music.downloader.videoplayer.utils.PermissionHelper;

import java.util.ArrayList;
import java.util.List;

public class DeviceFragment extends Fragment implements VideoFilesAdapter.VideoFilesHandler {
    private static final int STORAGE_PERMISSION_CODE = 101;

    private static List<Song> songs;
    private static List<VideoData> videoData;

    private VideoPlayerActivity context;
    private RecyclerView rvSongs;

    public DeviceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_device, container, false);
      return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.context = (VideoPlayerActivity) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvSongs = view.findViewById(R.id.rv_song_list);
        if(PermissionHelper.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                STORAGE_PERMISSION_CODE, context)) {
            videoData = getVideos();
//            setSongList(rvSongs, videoData);
            VideoFilesAdapter adapter = new VideoFilesAdapter(getVideos(),getActivity(), this);
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
            rvSongs.setAdapter(adapter);
            rvSongs.setLayoutManager(manager);
        } else if (PermissionHelper.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                STORAGE_PERMISSION_CODE, context)) {
            videoData = getVideos();
//            setSongList(rvSongs, videoData);
            VideoFilesAdapter adapter = new VideoFilesAdapter(getVideos(),getActivity(), this);
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
            rvSongs.setAdapter(adapter);
            rvSongs.setLayoutManager(manager);
        }
//        ((EditText)view.findViewById(R.id.sv_device_songs)).addTextChangedListener(this);
    }

    @SuppressLint("Range")
    private List<VideoData> getVideos() {
        List<VideoData> list = new ArrayList<>();
        Cursor managedQuery = getActivity().managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "_id", "_display_name", "duration"}, null, null , " _id DESC");
        int count = managedQuery.getCount();
        managedQuery.moveToFirst();
        for (int i = 0; i < count; i++) {
            Uri withAppendedPath = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, getLong(managedQuery));
            list.add(new VideoData(managedQuery.getString(managedQuery.getColumnIndexOrThrow("_display_name")), withAppendedPath, managedQuery.getString(managedQuery.getColumnIndex("_data")), getTime(managedQuery, "duration")));
            managedQuery.moveToNext();
        }
        return list;
    }

    private void setSongList(RecyclerView rvSongs, List<Song> songs) {
        SongListAdapter songAdapter = new SongListAdapter(context, songs, position -> {
            try {
                ((ISongList)context).fragmentSongList(songs, position);
            } catch (ClassCastException ignored) {}
        });
        rvSongs.setAdapter(songAdapter);
    }

//    private void searchSong(String query) {
//        if(query.length() == 0) {
//            setSongList(rvSongs, songs);
//        } else {
//            query = query.toLowerCase();
//            List<Song> songList = new ArrayList<>();
//            for (int i = 0; i < songs.size(); i++) {
//                String title = songs.get(i).getTitle().toLowerCase();
//                String artist = songs.get(i).getArtist().toLowerCase();
//                if (title.contains(query) || artist.contains(query)) {
//                    songList.add(songs.get(i));
//                }
//            }
//            setSongList(rvSongs, songList);
//        }
//    }
//
//    @Override
//    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////        searchSong(charSequence.toString());
//    }
//
//    @Override
//    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////        searchSong(charSequence.toString());
//    }
//
//    @Override
//    public void afterTextChanged(Editable editable) {
//        // Unused method
//    }

    @Override
    public void onChoose(int position) {
        Intent intent = new Intent(getActivity(), com.video.music.downloader.statusandgallery.activity.VideoPlayerActivity.class);
        intent.putExtra("PathVideo", getVideos().get(position).videoPath);
        startActivity(intent);
    }
}
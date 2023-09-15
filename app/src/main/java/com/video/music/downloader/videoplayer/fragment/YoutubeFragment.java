package com.video.music.downloader.videoplayer.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.video.music.downloader.BuildConfig;
import com.video.music.downloader.R;
////import com.video.music.downloader.statusandgallery.AdsUtils.FirebaseADHandlers.AdUtils;
////import com.video.music.downloader.statusandgallery.AdsUtils.Interfaces.AppInterfaces;
//import com.video.music.downloader.statusandgallery.AdsUtils.Utils.Constants;
import com.video.music.downloader.videoplayer.VideoPlayerActivity;
import com.video.music.downloader.videoplayer.adapter.Top100PlaylistAdapter;
import com.video.music.downloader.videoplayer.adapter.Top10PlaylistAdapter;
import com.video.music.downloader.videoplayer.api.IYoutubeApi;
import com.video.music.downloader.videoplayer.data.model.youtube.IYoutubeVideo;
import com.video.music.downloader.videoplayer.data.model.youtube.RequestResponse;
import com.video.music.downloader.videoplayer.data.model.youtube.YoutubeVideo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YoutubeFragment extends Fragment implements EditText.OnEditorActionListener {
    private VideoPlayerActivity context;
    private IYoutubeApi iYoutubeApi;

    private RecyclerView rvTop10Playlist;
    private RecyclerView rvTop100Playlist;

    private final static String PLAYLIST_NAME = "Maroon 5";

    public YoutubeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_YOUTUBE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iYoutubeApi = retrofit.create(IYoutubeApi.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_youtube, container, false);
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
        rvTop10Playlist = view.findViewById(R.id.rv_top_10_playlist);
        rvTop100Playlist = view.findViewById(R.id.rv_top_100_playlist);
        getTopPlaylist(PLAYLIST_NAME, 10);
        getTopPlaylist(PLAYLIST_NAME, 100);
        ((EditText)view.findViewById(R.id.sv_youtube_songs)).setOnEditorActionListener(this);
        EditText sv_youtube_songs = ((EditText)view.findViewById(R.id.sv_youtube_songs));

        ImageView searchbtn = ((ImageView)view.findViewById(R.id.search_btn));
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSong(sv_youtube_songs.getText().toString());
            }
        });
    }

    private void getTopPlaylist(String query, int maxResults) {
        Call<RequestResponse> call = iYoutubeApi.searchQuery(
                BuildConfig.YOTUBE_DATA_TOKEN, maxResults, "relevance", "snippet", query, "video"
        );
        call.enqueue(new Callback<RequestResponse>() {
            @Override
            public void onResponse(Call<RequestResponse> call, Response<RequestResponse> response) {
                if (response.code() == 200) {
                    if(response.body() != null) {
                        if(maxResults == 10) {
                            setTopPlaylist(response.body().getItems(), rvTop10Playlist);
                        } else if(maxResults == 50) {
                            setSearchList(response.body().getItems());
                        } else {
                            setTopPlaylist(response.body().getItems(), rvTop100Playlist);
                        }
                    }
                } else {
                    Toast.makeText(context, getString(R.string.error_happened), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RequestResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void searchSong(String query) {
        if(query.length() == 0) {
            setSearchList(null);
        } else {
            query = query.toLowerCase();
            getTopPlaylist(query, 50);
        }
    }

    private void setSearchList(ArrayList<YoutubeVideo> songs) {
        if(songs == null) {
            rvTop10Playlist.setVisibility(View.VISIBLE);
            context.findViewById(R.id.tv_top_10_playlist).setVisibility(View.VISIBLE);
            context.findViewById(R.id.tv_top_100_playlist).setVisibility(View.VISIBLE);
        } else {
            rvTop10Playlist.setVisibility(View.GONE);
            context.findViewById(R.id.tv_top_10_playlist).setVisibility(View.GONE);
            context.findViewById(R.id.tv_top_100_playlist).setVisibility(View.GONE);
            Top100PlaylistAdapter top100PlaylistAdapter = new Top100PlaylistAdapter(context, songs, new Top100PlaylistAdapter.Callback() {
                @Override
                public void onVideoClick(int position) {
                    try {
                        ((IYoutubeVideo) context).startVideo(songs.get(position));
                    } catch (ClassCastException ignored) {
                    }
                }
            });
            rvTop100Playlist.setAdapter(top100PlaylistAdapter);
        }
    }

    private void setTopPlaylist(List<YoutubeVideo> songs, RecyclerView recyclerView) {
        switch (recyclerView.getId()) {
            case (R.id.rv_top_10_playlist):
//                AdUtils.showInterstitialAd(getActivity(), new AppInterfaces.InterStitialADInterface() {
//                    @Override
//                    public void adLoadState(boolean isLoaded) {
                        Top10PlaylistAdapter top10PlaylistAdapter = new Top10PlaylistAdapter(context, songs, position -> {
                            try {
                                ((IYoutubeVideo)context).startVideo(songs.get(position));
                            } catch (ClassCastException ignored) {}
                        });
                        rvTop10Playlist.setAdapter(top10PlaylistAdapter);
//                    }
//                });

                break;
            case (R.id.rv_top_100_playlist):
//                AdUtils.showInterstitialAd(getActivity(), new AppInterfaces.InterStitialADInterface() {
//                    @Override
//                    public void adLoadState(boolean isLoaded) {
                        Top100PlaylistAdapter top100PlaylistAdapter = new Top100PlaylistAdapter(context, songs, position -> {
                            try {
                                ((IYoutubeVideo)context).startVideo(songs.get(position));
                            } catch (ClassCastException ignored) {}
                        });
                        rvTop100Playlist.setAdapter(top100PlaylistAdapter);
//                    }
//                });

                break;
        }

    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
            if (keyEvent == null || !keyEvent.isShiftPressed()) {
                searchSong(textView.getText().toString());
                return true;
            }
        }
        return false;
    }
}
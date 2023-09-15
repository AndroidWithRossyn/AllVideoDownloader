package com.video.music.downloader.MusicPlayer.view;

import android.Manifest;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.video.music.downloader.Activity.DashboardActivity;

import com.video.music.downloader.MusicPlayer.adapter.SongsAdapter;
import com.video.music.downloader.MusicPlayer.model.Song;
import com.video.music.downloader.MusicPlayer.viewmodel.SharedViewModel;
import com.video.music.downloader.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MusicPlayerActivity extends AppCompatActivity {
    //initialize variable
    ActivityResultLauncher<String> storagePermissionLauncher;
    RecyclerView recyclerview;
    SongsAdapter songsAdapter;
    int gridSpanSize = 1;
    ConstraintLayout controlsWrapper;
    TextView playingSongNameView, skipPrevSongBtn, skipNextSongBtn;
    ImageButton playPauseBtn;
    ExoPlayer player;
    ImageView imBack;
    SharedViewModel sharedViewModel;
    FragmentContainerView nav_host_fragment_container;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_music_player);
        recyclerview = findViewById(R.id.recyclerview);
        playingSongNameView = findViewById(R.id.playingSongNameView);
        playingSongNameView.setSelected(true);//for marquee
        player = new ExoPlayer.Builder(this).build();
        player.setRepeatMode(Player.REPEAT_MODE_ALL);
        skipPrevSongBtn = findViewById(R.id.prevBtn);
        skipNextSongBtn = findViewById(R.id.nextBtn);
        playPauseBtn = findViewById(R.id.playPauseBtn);
        controlsWrapper = findViewById(R.id.controlsWrapper);
        imBack = findViewById(R.id.imBack);
        nav_host_fragment_container = findViewById(R.id.nav_host_fragment_container);
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            }
        });

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.getPlayer().setValue(player);

        //set tool bar
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setTitle("Music Player App");

        //assigning storage permission launcher
        storagePermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if (result) {
                //permission was granted
                fetchSongs();
            } else {//responding on user's actions
                respondOnUserPermissionActs();

            }
        });

        //launch the storage permission launcher
        storagePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//player controls
        playerControls();
        controlsWrapper.setVisibility(View.GONE);

    }

    private void playerControls() {
        controlsWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerViewFragment playerViewFragment = new PlayerViewFragment();
                String fragmentTag = playerViewFragment.getClass().getName();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment_container, playerViewFragment)
                        .addToBackStack(fragmentTag)
                        .commit();

            }
        });
        skipNextSongBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player.hasNextMediaItem()) {
                    player.seekToNext();
                }
            }
        });

        skipPrevSongBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player.hasPreviousMediaItem()) {
                    player.seekToPrevious();
                }
            }
        });

        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player.isPlaying()) {
                    player.pause();
                    playPauseBtn.setImageResource(R.drawable.ic_play_circle);
                } else {
                    if (player.getMediaItemCount() > 0) {
                        player.play();
                        playPauseBtn.setImageResource(R.drawable.ic_pause_circle);
                    }
                }
            }
        });

        //player listener
        playerListener();
    }

    private void playerListener() {
        player.addListener(new Player.Listener() {
            @Override
            public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
                assert mediaItem != null;
                playingSongNameView.setText(mediaItem.mediaMetadata.title);
            }

            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == ExoPlayer.STATE_READY) {
                    playingSongNameView.setText(Objects.requireNonNull(player.getCurrentMediaItem()).mediaMetadata.title);
                    playPauseBtn.setImageResource(R.drawable.ic_pause_circle);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player.isPlaying()) {
            player.stop();
        }
        player.release();
    }

    //menu
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }

//    @SuppressLint("NotifyDataSetChanged")
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        if (item.getItemId() ==R.id.listview){
//            if (gridSpanSize ==1){
//                gridSpanSize =2;
//            }else {
//                gridSpanSize =1;
//            }
//        }


//        return true;
//    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void respondOnUserPermissionActs() {
        //user response
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //permission granted
            fetchSongs();
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //show an educational UI to user explaining why we need this permission
            //alert dialog
            new AlertDialog.Builder(this)
                    .setTitle("Requesting Permission")
                    .setMessage("Allow us to fetch & show songs on your device")
                    .setPositiveButton("Allow ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //request permission again
                            storagePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        }
                    })
                    .setNegativeButton("Don't Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(), " You denied to fetch songs", Toast.LENGTH_LONG).show();
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        } else {
            Toast.makeText(this, "You denied to fetch songs", Toast.LENGTH_LONG).show();
        }
    }

    private void fetchSongs() {
        //define list to carry the songs
        List<Song> songs = new ArrayList<>();
        Uri songLibraryUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            songLibraryUri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            songLibraryUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }

        //projection
        String[] projection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.ALBUM_ID,
        };

        //sort order
        String sortOrder = MediaStore.Audio.Media.DATE_ADDED + " DESC";

        //Querying
        try (Cursor cursor = getContentResolver().query(songLibraryUri, projection, null, null, sortOrder)) {

            //cache the cursor indices
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
            int albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);

            //getting the values
            while (cursor.moveToNext()) {
                //get values of columns for a give audio file
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int duration = cursor.getInt(durationColumn);
                int size = cursor.getInt(sizeColumn);
                long albumId = cursor.getLong(albumIdColumn);

                //song uri
                Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);

                //album art uri
                Uri albumartUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);

                //remove .mp3 extension on song's name
                name = name.substring(0, name.lastIndexOf("."));

                //song item
                Song song = new Song(id, uri, name, duration, size, albumId, albumartUri);
                //add song to songs list
                songs.add(song);
            }
            //show songs on rv
            showSongs(songs);
            //Toast.makeText(getApplicationContext(), "Number Songs: "+songs.size(),Toast.LENGTH_SHORT).show();
        }
    }

    private void showSongs(List<Song> songs) {
        //layout manager
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, gridSpanSize);
        recyclerview.setLayoutManager(layoutManager);

        //adapter
        songsAdapter = new SongsAdapter(songs, player, controlsWrapper);
        recyclerview.setAdapter(songsAdapter);

        songsAdapter.notifyDataSetChanged();

    }


/*    public class SongAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<Song> songs;
//        ExoPlayer player;

        public SongAdapter(List<Song> songs) {
            this.songs = songs;
//            this.player = player;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.song_row_item, parent, false);
            return new SongViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Song song = songs.get(position);
            SongViewHolder viewHolder = (SongViewHolder) holder;

            viewHolder.titleHolder.setText(song.getName());
            viewHolder.sizeHolder.setText(getSize(song.getSize()));
            viewHolder.numberHolder.setText(String.valueOf(position + 1));
            viewHolder.durationHolder.setText(getDuration(song.getDuration()));


            //album art
            Uri albumartUri = song.getAlbumartUri();
            if (albumartUri != null) {
                viewHolder.albumartHolder.setImageURI(albumartUri);

                if (viewHolder.albumartHolder.getDrawable() == null) {
                    viewHolder.albumartHolder.setImageResource(R.drawable.default_albumart);
                }
            } else {
                viewHolder.albumartHolder.setImageResource(R.drawable.default_albumart);
            }

            //onclick item
            viewHolder.rowItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    controlsWrapper.setVisibility(View.VISIBLE);
                    //media item
                    MediaItem mediaItem = getMediaItem(song);
                    if (!player.isPlaying()) {
                        player.setMediaItems(getMediaItems(), position, 0);
                    } else {
                        player.pause();
                        player.seekTo(position, 0);
                    }
                    player.prepare();
                    player.play();
//                Toast.makeText(view.getContext(),"Playing: "+song.getName(), Toast.LENGTH_LONG).show();

                    // launch a Player Fragment
//                    goToPlayerViewFragment(view.getContext());
                }
            });

        }

        @Override
        public int getItemCount() {
            return 0;
        }

//        private void goToPlayerViewFragment(Context context) {
////        PlayerViewFragment playerViewFragment = new PlayerViewFragment();
////        String fragmentTag = playerViewFragment.getClass().getName();
////        ((MainActivity)context).getSupportFragmentManager()
////                .beginTransaction()
////                .replace(R.id.nav_host_fragment_container, playerViewFragment)
////                .addToBackStack(fragmentTag)
////                .commit();
//        }

        private List<MediaItem> getMediaItems() {
            List<MediaItem> mediaItems = new ArrayList<>();
            for (Song song : songs) {
                MediaItem mediaItem = new MediaItem.Builder()
                        .setUri(song.getUri())
                        .setMediaMetadata(getMetadata(song))
                        .build();
                mediaItems.add(mediaItem);
            }

            return mediaItems;
        }

        private MediaItem getMediaItem(Song song) {
            return new MediaItem.Builder()
                    .setUri(song.getUri())
                    .setMediaMetadata(getMetadata(song))
                    .build();
        }

        private MediaMetadata getMetadata(Song song) {
            return new MediaMetadata.Builder()
                    .setTitle(song.getName())
                    .setArtworkUri(song.getAlbumartUri())
                    .build();
        }

        public class SongViewHolder extends RecyclerView.ViewHolder {

            //member variables
            ConstraintLayout rowItemLayout;
            ImageView albumartHolder;
            TextView numberHolder, titleHolder, durationHolder, sizeHolder;
            RelativeLayout relativeLayout;
            LinearLayout nativeads;
            CardView cd;

            public SongViewHolder(@NonNull View itemView) {
                super(itemView);
                rowItemLayout = itemView.findViewById(R.id.rowItemLayout);
                albumartHolder = itemView.findViewById(R.id.albumart);
                numberHolder = itemView.findViewById(R.id.number);
                titleHolder = itemView.findViewById(R.id.title);
                sizeHolder = itemView.findViewById(R.id.size);
                durationHolder = itemView.findViewById(R.id.duration);
                relativeLayout = itemView.findViewById(R.id.rl);
                nativeads = itemView.findViewById(R.id.native_ads);
                cd = itemView.findViewById(R.id.cd);

            }
        }

        @SuppressLint("DefaultLocale")
        private String getDuration(int totalDuration) {
            String totalDurationText;
            int hrs = totalDuration / (1000 * 60 * 60);
            int min = (totalDuration % (1000 * 60 * 60)) / (1000 * 60);
            int secs = (((totalDuration % (1000 * 60 * 60)) % (1000 * 60 * 60)) % (1000 * 60)) / 1000;

            if (hrs < 1) {
                totalDurationText = String.format("%02d:%02d", min, secs);
            } else {
                totalDurationText = String.format("%1d:%02d:%02d", hrs, min, secs);
            }
            return totalDurationText;
        }

        private String getSize(long bytes) {
            String hrSize;

            double k = bytes / 1024.0;
            double m = ((bytes / 1024.0) / 1024.0);
            double g = (((bytes / 1024.0) / 1024.0) / 1024.0);
            double t = ((((bytes / 1024.0) / 1024.0) / 1024.0) / 1024.0);

            DecimalFormat dec = new DecimalFormat("0.00");

            if (t > 1) {
                hrSize = dec.format(t).concat(" TB");
            } else if (g > 1) {
                hrSize = dec.format(g).concat(" GB");
            } else if (m > 1) {
                hrSize = dec.format(m).concat(" MB");
            } else if (k > 1) {
                hrSize = dec.format(k).concat(" KB");
            } else {
                hrSize = dec.format((double) bytes).concat(" Bytes");
            }

            return hrSize;
        }
    }*/
}
package com.video.music.downloader.videoplayer.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.video.music.downloader.TimeUtils;
import com.video.music.downloader.videoplayer.data.model.Song;

import java.util.ArrayList;
import java.util.List;

public class SongHelper {
    final private static Uri ALBUM_URI = Uri.parse("content://media/external/video/albumart");

    public static List<Song> getSongList(Context context) {
        ContentResolver songResolver = context.getContentResolver();
        Uri musicUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = songResolver.query(musicUri, null, null, null, null);

        List<Song> songList = new ArrayList<>();

        if (songCursor != null && songCursor.moveToFirst()) {
            int idColumn = songCursor.getColumnIndex(MediaStore.Video.Media._ID);
            int titleColumn = songCursor.getColumnIndex(MediaStore.Video.Media.TITLE);
            int artistColumn = songCursor.getColumnIndex(MediaStore.Video.Media.ARTIST);
            int albumColumn = songCursor.getColumnIndex(MediaStore.Video.Media.ALBUM);
            do {
                long id = songCursor.getLong(idColumn);
                String title = songCursor.getString(titleColumn);
                String artist = songCursor.getString(artistColumn);
                long albumID = songCursor.getLong(albumColumn);
                Uri albumArtUri = ContentUris.withAppendedId(ALBUM_URI, albumID);
                songList.add(new Song(id, title, artist, albumArtUri));
            } while (songCursor.moveToNext());
        }

        if (songCursor != null) {
            songCursor.close();
        }
        return songList;
    }



    public static String getLong(Cursor cursor) {
        String sb = "" +
                cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
        return sb;
    }

    public static String getTime(Cursor cursor, String str) {
        return TimeUtils.toFormattedTime(getInt(cursor, str));
    }

    public static int getInt(Cursor cursor, String str) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(str));
    }
}

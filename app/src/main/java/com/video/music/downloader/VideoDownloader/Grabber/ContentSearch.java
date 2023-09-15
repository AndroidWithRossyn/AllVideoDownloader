/*
 * Copyright (c) 2021.  Hurricane Development Studios
 */

package com.video.music.downloader.VideoDownloader.Grabber;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.util.Log;


import com.video.music.downloader.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;


public abstract class ContentSearch extends Thread {

    private static final String VIDEO = "video";
    private static final String AUDIO = "audio";
    private static final String IMAGE = "image";
    private static final String LENGTH = "content-length";
    private static final String TWITTER = "twitter.com";
    private static final String YOUTUBE = "youtube.com";
    private static final String METACAFE = "metacafe.com";
    private static final String MYSPACE = "myspace.com";
    private Context context;
    private String url;
    private String page;
    private String title;
    private int numLinksInspected;

    public abstract void onStartInspectingURL();

    public abstract void onFinishedInspectingURL(boolean finishedAll);

    public abstract void onVideoFound(String size, String type, String link, String name,
                                      String page, boolean chunked, String website, boolean audio);

    public abstract void onImageFound(String size, String type, String link, String name,
                                      String page, boolean chunked, String website, boolean audio);

    public abstract void onAudioFound(String size, String type, String link, String name,
                                      String page, boolean chunked, String website, boolean audio);

    public ContentSearch(Context context, String url, String page, String title) {
        this.context = context;
        this.url = url;
        this.page = page;
        this.title = title;
        numLinksInspected = 0;
    }

    private String getUrlWithoutParameters(String url) throws URISyntaxException {
        URI uri = new URI(url);
        return new URI(uri.getScheme(),
                uri.getAuthority(),
                uri.getPath(),
                null, // Ignore the query part of the input url
                uri.getFragment()).toString();
    }

    @Override
    public void run() {
        String urlLowerCase = url.toLowerCase();
        String[] video_filters = context.getResources().getStringArray(R.array.videourl_filters);
        String[] image_filters = context.getResources().getStringArray(R.array.imageurl_filters);
        String[] audio_filters = context.getResources().getStringArray(R.array.audiourl_filters);

        boolean urlMightBeVideo = false;
        boolean urlMightBeImage = false;
        boolean urlMightBeAudio = false;

        for (String filter : video_filters) {
            if (urlLowerCase.contains(filter)) {
                try {
                    String compressedUrl=getUrlWithoutParameters(urlLowerCase);
                    if((! compressedUrl.endsWith(".js")) && (! compressedUrl.endsWith(".css")) && (! compressedUrl.endsWith(".svg")) && (! compressedUrl.endsWith(".ts")) )
                    {
                        urlMightBeVideo = true;
                        break;
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }
        if(urlMightBeVideo==true)
        {
            if(URLAddFilter.IsContainsAdURL(context,url))
            {
                urlMightBeVideo=false;
            }
        }

        for (String filter : image_filters) {
            if (urlLowerCase.contains(filter)) {
                urlMightBeImage = true;
                break;
            }
        }

        for (String filter : audio_filters) {
            if (urlLowerCase.contains(filter)) {
                urlMightBeAudio = true;
                break;
            }
        }

        if( (urlMightBeImage)){
            addImageToList(url, page, title, "image");
        }

        if( (urlMightBeAudio)){
            addAudioToList(url, page, title, "audio");
        }

        if(urlMightBeVideo)
        {
            if(url.toLowerCase().contains("googlevideo.com"))
            {
                urlMightBeVideo=false;
            }
        }

        if (urlMightBeVideo) {

            numLinksInspected++;
            onStartInspectingURL();
            Log.d("MPEGURL",url);
            boolean isErrorFound=false;
            URLConnection uCon = null;
            try {
                uCon = new URL(url).openConnection();
                uCon.connect();

            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                isErrorFound=true;
                e.printStackTrace();
            }
            if (uCon != null && isErrorFound==false) {
                String contentType = uCon.getHeaderField("content-type");

                if (contentType != null) {
                    contentType = contentType.toLowerCase();
                    if (contentType.contains(VIDEO) || contentType.contains
                            (AUDIO)) {
                        addVideoToList(uCon, page, title, contentType);
                    }
                    else if (contentType.equals("application/octet-stream")  || contentType.equals("application/mp4") || contentType.equals("video/mp4") ){
                        addVideoToList(uCon, page, title, contentType);
                    }
                    else if (contentType.equals("application/x-mpegurl")   ||
                            contentType.equals("application/vnd.apple.mpegurl") ||
                            contentType.equals("application/x-mpegURL; charset=UTF-8")

                        ) {

                        try {
                            String host = new URL(page).getHost();
                            if (host.contains(TWITTER) || host.contains(METACAFE) || host.contains
                                    (MYSPACE))
                            {
                                addVideosToListFromM3U8(uCon, page, title);
                            }
                            else
                            {
                                addVideosToListFromM3U8_Direct(uCon,page,title);
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (contentType.equals("binary/octet-stream"))
                    {
                        addVideosToListFromM3U8_Direct(uCon,page,title);
                    }
                }
            }

            numLinksInspected--;
            boolean finishedAll = false;
            if (numLinksInspected <= 0) {
                finishedAll = true;
            }
            onFinishedInspectingURL(finishedAll);
        }
    }


    private void addImageToList(String url, String page, String title, String contentType)
    {
        onImageFound(null, IMAGE,url,title , page, false, null, false);
    }

    private void addAudioToList(String url, String page, String title, String contentType)
    {
        onAudioFound(null, title, url, IMAGE, page, false, null, false);
    }

    private void addVideoToList(URLConnection uCon, String page, String title, String contentType) {
        Log.d("urlLowerCase",uCon.getURL().toString());
        try {
            String size = uCon.getHeaderField(LENGTH);
            String link = uCon.getHeaderField("Location");
            if (link == null) {
                link = uCon.getURL().toString();
            }

            String host = new URL(page).getHost();
            String website = null;
            boolean chunked = false;
            String type;
            boolean audio = false;

            // Skip twitter video chunks.
            if (host.contains(TWITTER) && contentType.equals("video/mp2t")) {
                return;
            }

            String name = VIDEO;
            if (title != null) {
                if (contentType.contains(AUDIO)) {
                    name = "[AUDIO ONLY]" + title;
                } else {
                    name = title;
                }
            } else if (contentType.contains(AUDIO)) {
                name = AUDIO;
            }

            if (host.contains(YOUTUBE) || (new URL(link).getHost().contains("googlevideo.com")
            )) {

                int r = link.lastIndexOf("&range");
                if (r > 0) {
                    link = link.substring(0, r);
                }
                URLConnection ytCon;
                ytCon = new URL(link).openConnection();
                ytCon.connect();
                size = ytCon.getHeaderField(LENGTH);

                if (host.contains(YOUTUBE)) {
                    URL embededURL = new URL("http://www.youtube.com/oembed?url=" + page +
                            "&format=json");
                    try {
                        String jSonString;
                        InputStream in = embededURL.openStream();

                        InputStreamReader inReader = new InputStreamReader(in, Charset
                                .defaultCharset());
                        StringBuilder sb = new StringBuilder();
                        char[] buffer = new char[1024];
                        int read;
                        while ((read = inReader.read(buffer)) != -1) {
                            sb.append(buffer, 0, read);
                        }
                        jSonString = sb.toString();

                        name = new JSONObject(jSonString).getString("title");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (contentType.contains(VIDEO)) {
                        name = "[VIDEO ONLY]" + name;
                    } else if (contentType.contains(AUDIO)) {
                        name = "[AUDIO ONLY]" + name;
                    }
                    website = YOUTUBE;
                }
            } else if (host.contains("dailymotion.com")) {
                chunked = true;
                website = "dailymotion.com";
                link = link.replaceAll("(frag\\()+(\\d+)+(\\))", "FRAGMENT");
                size = null;
            } else if (host.contains("vimeo.com") && link.endsWith("m4s")) {
                chunked = true;
                website = "vimeo.com";
                link = link.replaceAll("(segment-)+(\\d+)", "SEGMENT");
                size = null;
            } else if (host.contains("facebook.com") && link.contains("bytestart")) {
                int b = link.lastIndexOf("&bytestart");
                int f = link.indexOf("fbcdn");
                if (b > 0) {
                    link = "https://video.xx." + link.substring(f, b);
                }
                URLConnection fbCon;
                fbCon = new URL(link).openConnection();
                fbCon.connect();
                size = fbCon.getHeaderField(LENGTH);

            }else if (host.contains("instagram.com")) {
                try {
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(link, new HashMap<String, String>());
                    retriever.release();
                    audio = false;
                } catch (RuntimeException ex) {
                    audio = true;
                }
            }

            switch (contentType) {
                case "video/mp4":
                    type = "mp4";
                    break;
                case "video/webm":
                    type = "webm";
                    break;
                case "video/mp2t":
                    type = "ts";
                    break;
                case "audio/webm":
                    type = "webm";
                    break;
                default:
                    type = "mp4";
                    break;
            }

            onVideoFound(size, type, link, name, page, chunked, website, audio);
        } catch (IOException e) {
        }
    }

    private void addVideosToListFromM3U8_Direct(URLConnection uCon, String page, String title){
        Log.d("urlLowerCase",uCon.getURL().toString());
        String link = uCon.getHeaderField("Location");
        if (link == null) {
            link = uCon.getURL().toString();
        }
        onVideoFound(null, "m3u8", link, "Video", page, true, null,false);
    }

    private void addVideosToListFromM3U8(URLConnection uCon, String page, String title) {
        Log.d("urlLowerCase",uCon.getURL().toString());
        try {
            String host;
            Boolean audio = false;
            InputStream in = uCon.getInputStream();
            InputStreamReader inReader = new InputStreamReader(in);
            BufferedReader buffReader = new BufferedReader(inReader);
            String line;
            String prefix = null;
            String type = null;
            String name = VIDEO;
            String website = null;
            host = new URL(page).getHost();
            if (host.contains(TWITTER) || host.contains(METACAFE) || host.contains
                    (MYSPACE)) {


                if (title != null) {
                    name = title;
                }
                if (host.contains(TWITTER)) {
                    prefix = "https://video.twimg.com";
                    type = "ts";
                    website = TWITTER;
                } else if (host.contains(METACAFE)) {
                    String link = uCon.getURL().toString();
                    prefix = link.substring(0, link.lastIndexOf("/") + 1);
                    website = METACAFE;
                    type = "mp4";
                } else if (host.contains(MYSPACE)) {
                    String link = uCon.getURL().toString();
                    website = MYSPACE;
                    type = "ts";

                    onVideoFound(null, type, link, name, page, true, website, audio);

                    return;
                }
                while ((line = buffReader.readLine()) != null) {
                    if (line.endsWith(".m3u8")) {
                        String link = prefix + line;
                        onVideoFound(null, type, link, name, page, true, website, audio);
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

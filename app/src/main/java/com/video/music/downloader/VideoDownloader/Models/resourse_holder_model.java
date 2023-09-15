package com.video.music.downloader.VideoDownloader.Models;

import android.webkit.MimeTypeMap;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class resourse_holder_model {

    private ArrayList<downloadable_resource_model> video_files;
    private ArrayList<downloadable_resource_model> audio_files;
    private ArrayList<downloadable_resource_model> image_files;
    private ArrayList<downloadable_resource_model> document_files;

    private ArrayList<String> video_types=new ArrayList<>();
    private ArrayList<String> image_types=new ArrayList<>();

    private String page_title;

    public resourse_holder_model() {
        this.audio_files=new ArrayList<>();
        this.document_files=new ArrayList<>();
        this.image_files=new ArrayList<>();
        this.video_files=new ArrayList<>();
        this.page_title="";
        init_arraylists();
    }

    private void init_arraylists(){
        video_types.add("mp4");
        video_types.add("wmv");
        video_types.add("avi");

        image_types.add("png");
        image_types.add("jpg");
        image_types.add("gif");
        image_types.add("webp");
    }


    public void add_Video (String size, String type, String link, String name, String page)
    {
        add_video_files(new downloadable_resource_model(name,link,file_type.VIDEO,size));
    }

    public void add_Image (String size, String type, String link, String name, String page)
    {
        add_image_files(new downloadable_resource_model(name,link,file_type.IMAGE,size));
    }

    public void add_Audio (String size, String type, String link, String name, String page)
    {
        add_audio_file(new downloadable_resource_model(name,link,file_type.AUDIO,size));
    }

    public void check_and_add_file(String url){
        try {
            String extension=MimeTypeMap.getFileExtensionFromUrl(url);
            if(video_types.contains(extension)){
                add_video_files(new downloadable_resource_model(this.page_title,url,file_type.VIDEO,null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void add_audio_file(downloadable_resource_model _downloadable_resource_model){
        boolean found=false;
        for (downloadable_resource_model item: this.audio_files) {
            if(item.equals(_downloadable_resource_model)){
                found=true;
            }
        }
        if(found==false){
            this.audio_files.add(_downloadable_resource_model);
        }
    }

    public void add_video_files(downloadable_resource_model _downloadable_resource_model){
        boolean found=false;
        if ( (_downloadable_resource_model.getURL() !=null) &&  ( !_downloadable_resource_model.getURL().startsWith("blob"))) {
            try
            {
                for (downloadable_resource_model item : this.video_files) {
                    if (item.getURL().equals(_downloadable_resource_model.getURL())) {
                        found = true;
                    }
                }
                if (found == false) {
                    this.video_files.add(_downloadable_resource_model);
                }
            }
            catch (Exception ex)
            {}
        }
    }
    public void add_image_files(downloadable_resource_model _downloadable_resource_model){
        boolean found=false;


        try {
            for(Iterator<downloadable_resource_model> it = image_files.iterator(); it.hasNext();) {
                downloadable_resource_model item = it.next();
                if( item.getURL() !=null && item.getURL().equals(_downloadable_resource_model.getURL())){
                    found=true;
                }
            }
        }
        catch (ConcurrentModificationException ex)
        {

        }

        if(found==false){
            this.image_files.add(_downloadable_resource_model);
        }
    }
    public void add_document_files(downloadable_resource_model _downloadable_resource_model){
        this.document_files.add(_downloadable_resource_model);
    }


    public ArrayList<downloadable_resource_model> getVideo_files() {
        for (downloadable_resource_model model:video_files) {
            model.setTitle(this.page_title);
        }
        return video_files;
    }

    public void setVideo_files(ArrayList<downloadable_resource_model> video_files) {
        this.video_files = video_files;
    }

    public ArrayList<downloadable_resource_model> getAudio_files() {
        return audio_files;
    }

    public void setAudio_files(ArrayList<downloadable_resource_model> audio_files) {
        this.audio_files = audio_files;
    }

    public ArrayList<downloadable_resource_model> getImage_files() {
        return image_files;
    }

    public void setImage_files(ArrayList<downloadable_resource_model> image_files) {
        this.image_files = image_files;
    }

    public ArrayList<downloadable_resource_model> getDocument_files() {
        return document_files;
    }

    public void setDocument_files(ArrayList<downloadable_resource_model> document_files) {
        this.document_files = document_files;
    }
    public String getPage_title() {return page_title;}

    public void setPage_title(String page_title) {this.page_title = page_title;}
}

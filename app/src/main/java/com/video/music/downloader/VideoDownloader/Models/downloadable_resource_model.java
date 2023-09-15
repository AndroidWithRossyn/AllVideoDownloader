package com.video.music.downloader.VideoDownloader.Models;

import android.graphics.Bitmap;


import com.video.music.downloader.VideoDownloader.Utils.Utils;

public class downloadable_resource_model {
    private String Title;
    private String URL;
    private file_type file_type;
    private String file_size;
    private Bitmap preview;

    public downloadable_resource_model(String title, String URL, com.video.music.downloader.VideoDownloader.Models.file_type file_type, String file_size) {
        Title = title;
        this.URL = URL;
        this.file_type = file_type;
        this.file_size=file_size;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public com.video.music.downloader.VideoDownloader.Models.file_type getFile_type() {
        return file_type;
    }

    public void setFile_type(com.video.music.downloader.VideoDownloader.Models.file_type file_type) {
        this.file_type = file_type;
    }

    public String getFile_size() {
        if(file_size !=null && !file_size.equals("")  ){
            try{
                return Utils.formatFileSize(Long.parseLong(file_size));
            }
            catch (Exception ex){
                return file_size;
            }
        }else
        {
            return file_size;
        }
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }
    public Bitmap getPreview() {
        return preview;
    }

    public void setPreview(Bitmap preview) {this.preview = preview; }

}

package com.video.music.downloader.VideoDownloader.Statics;

import com.video.music.downloader.VideoDownloader.Models.downloadable_resource_model;
import com.video.music.downloader.VideoDownloader.Models.file_type;
import com.video.music.downloader.VideoDownloader.Models.resourse_holder_model;

import java.util.ArrayList;
import java.util.List;

public class static_variables {
    public static resourse_holder_model resourse_holder;


    public static downloadable_resource_model get_by_type_position(file_type _type,int position){
        List<downloadable_resource_model> list;
        if( _type==file_type.VIDEO ){
            list= resourse_holder.getVideo_files();
        }
       else if( _type==file_type.IMAGE ){
            list= resourse_holder.getImage_files();
        }
        else if( _type==file_type.AUDIO ){
            list= resourse_holder.getAudio_files();
        }
        else
        {
            return null;
        }
        if(list !=null){
            return  list.get(position);
        }
        else
        {
            return null;
        }
    }

    public static ArrayList<downloadable_resource_model> get_downloadable_resource_model_By_Type(file_type _type){
        if( _type==file_type.VIDEO ){
            return resourse_holder.getVideo_files();
        }else if( _type==file_type.IMAGE ){
            return resourse_holder.getImage_files();
        }
        else if( _type==file_type.AUDIO ){
            return resourse_holder.getAudio_files();
        }
        else
        {
            return null;
        }
    }

}

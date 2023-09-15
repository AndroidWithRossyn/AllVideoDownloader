package com.video.music.downloader.VideoDownloader.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.video.music.downloader.VideoDownloader.Models.downloadable_resource_model;
import com.video.music.downloader.VideoDownloader.Models.file_type;
import com.video.music.downloader.R;
import com.video.music.downloader.VideoDownloader.Statics.static_variables;
import com.video.music.downloader.VideoDownloader.popups.rename_dialog;
import com.video.music.downloader.VideoDownloader.popups.video_player;



public class ResultHolderAdapter extends  RecyclerView.Adapter <ResultHolderAdapter.MyViewHolder>  {
    private Context mContext;
    private Activity activity;
    file_type _type;

    public ResultHolderAdapter(
                Context mContext,
                file_type _type,
                Activity mActivity){
        this.mContext=mContext;
        this.activity=mActivity;
        this._type=_type;
    }

    @NonNull
    @Override
    public ResultHolderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.download_result_list, parent, false);
        return new ResultHolderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final downloadable_resource_model result= static_variables.get_by_type_position(this._type,position);

        if( (result.getFile_size() !=null) && (!result.getFile_size().equals("")))
        {
            holder.txtVidSize.setText(result.getFile_size());
        }

        if(result !=null){
            holder.tv_film_name.setText(result.getTitle() +"");
            if(_type==file_type.IMAGE)
            {
                Glide.with(mContext).load(result.getURL()).into(holder.iv_poster);
            }
            else
            {
                if(result.getPreview() !=null)
                {
                    holder.iv_poster.setImageBitmap(result.getPreview());
                }
            }

            holder.iv_poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (result.getFile_type()==file_type.IMAGE)
                    {
                        String[] list={ result.getURL()  };
                        FragmentActivity activity = (FragmentActivity) (mContext);
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        video_player player=new video_player(result);
                        player.show(fragmentManager,"TAG");
//                        new ImageViewer.Builder(mContext, list)
//                                .show();
                    }
                }
            });

            holder.btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();

                    rename_dialog _rename_dialog=new rename_dialog(result);
                    _rename_dialog.show(fragmentManager,"TAG");

                }
            });

            holder.btnPreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(result.getFile_type()==file_type.VIDEO ||   result.getFile_type()==file_type.AUDIO  )
                    {
                        FragmentActivity activity = (FragmentActivity) (mContext);
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        video_player player=new video_player(result);
                        player.show(fragmentManager,"TAG");
                    }
                    else if (result.getFile_type()==file_type.IMAGE)
                    {
                        String[] list={ result.getURL()  };
                        FragmentActivity activity = (FragmentActivity) (mContext);
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        video_player player=new video_player(result);
                        player.show(fragmentManager,"TAG");
//                        new ImageViewer.Builder(mContext, list)
//                                .show();
                    }

                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return static_variables.get_downloadable_resource_model_By_Type(_type).size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView iv_poster;
        public TextView tv_film_name;
        public Button btnDownload,btnPreview;
        public TextView txtVidSize;


        public MyViewHolder(@NonNull View view) {
            super(view);
            iv_poster=view.findViewById(R.id.iv_poster);
            tv_film_name=view.findViewById(R.id.tv_film_name);
            btnDownload=view.findViewById(R.id.btnDownload);
            txtVidSize=view.findViewById(R.id.txtVidSize);
            btnPreview=view.findViewById(R.id.btnPreview);


        }
    }





}

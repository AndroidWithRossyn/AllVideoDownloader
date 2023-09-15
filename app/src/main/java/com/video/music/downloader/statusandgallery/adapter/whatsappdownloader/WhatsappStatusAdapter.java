package com.video.music.downloader.statusandgallery.adapter.whatsappdownloader;

import static com.video.music.downloader.statusandgallery.fragment.WhatsappQImageFragment.isAllSelected;
import static com.video.music.downloader.statusandgallery.utils.Utils.RootDirectoryWhatsappShow;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.video.music.downloader.R;
import com.video.music.downloader.statusandgallery.AppInterface;
import com.video.music.downloader.statusandgallery.activity.VideoPlayerActivity;
import com.video.music.downloader.statusandgallery.interfacee.FileListWhatsappClickInterface;
import com.video.music.downloader.statusandgallery.model.whatsAppdownloader.WhatsappStatusModel;
import com.video.music.downloader.statusandgallery.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class WhatsappStatusAdapter extends RecyclerView.Adapter<WhatsappStatusAdapter.ViewHolder> implements FileListWhatsappClickInterface {
    private Context context;
    private ArrayList<WhatsappStatusModel> fileArrayList;
    private LayoutInflater layoutInflater;
    ProgressDialog dialogProgress;
    String fileName = "";
    public String saveFilePath = RootDirectoryWhatsappShow + File.separator;
    private FileListWhatsappClickInterface fileListClickInterface;

    private AppInterface.WhatsAppAdapterInterface whatsAppAdapterInterface;

    public static final int INTRODUCTION_VIEW_TYPE = 0;
    public static final int IMPLEMENTATION_VIEW_TYPE = 1;
    private int currentViewType;

    public WhatsappStatusAdapter(Context context, ArrayList<WhatsappStatusModel> files, AppInterface.WhatsAppAdapterInterface whatsAppAdapterInterface) {
        this.context = context;
        this.fileArrayList = files;
        initProgress();
        this.whatsAppAdapterInterface = whatsAppAdapterInterface;
    }

    public WhatsappStatusAdapter(Context context, ArrayList<WhatsappStatusModel> files, FileListWhatsappClickInterface fileListClickInterface) {
        this.context = context;
        this.fileArrayList = files;
        this.fileListClickInterface = fileListClickInterface;
        initProgress();
    }

    @Override
    public int getItemViewType(int position) {
        WhatsappStatusModel fileItem = fileArrayList.get(position);
        if (fileItem.getUri().toString().endsWith(".mp4")) {
            return IMPLEMENTATION_VIEW_TYPE;
        } else {
            return INTRODUCTION_VIEW_TYPE;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == INTRODUCTION_VIEW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.items_whatsapp_view, viewGroup, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.items_whatsapp_view1, viewGroup, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        WhatsappStatusModel fileItem = fileArrayList.get(position);

//        Toast.makeText(context, ""+fileArrayList.size(), Toast.LENGTH_SHORT).show();
        if (isAllSelected) {
            viewHolder.checkboxFav.setVisibility(View.VISIBLE);
            viewHolder.checkboxFav.setChecked(true);
//            whatsAppAdapterInterface.onSelectAll(fileArrayList);
//            whatsAppAdapterInterface.onSingleDownload(fileArrayList.get(position));
        } else {
            viewHolder.checkboxFav.setChecked(false);
        }

        if (viewHolder.checkboxFav.isChecked()) {
            whatsAppAdapterInterface.onSelectAll(fileArrayList);
//            whatsAppAdapterInterface.onSingleDownload(fileArrayList.get(position));
        }
        else {

        }


        if (fileItem.getUri().toString().endsWith(".mp4")) {
            viewHolder.iv_play.setVisibility(View.VISIBLE);
            viewHolder.checkboxFav.setVisibility(View.GONE);
        } else {
            viewHolder.iv_play.setVisibility(View.GONE);
            viewHolder.checkboxFav.setVisibility(View.VISIBLE);
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            Glide.with(context).load(fileItem.getUri()).into(viewHolder.pcw);
        } else {
            Glide.with(context).load(fileItem.getPath()).into(viewHolder.pcw);
        }

//        if (isAllSelected) {
//            viewHolder.checkboxFav.setVisibility(View.VISIBLE);
//            viewHolder.rl_main.performLongClick();
//            viewHolder.checkboxFav.performClick();
//        } else {
//
//        }

//        viewHolder.checkboxFav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               whatsAppAdapterInterface.onSelectAll(fileArrayList);
//            }
//        });

        viewHolder.dowload.setOnClickListener(view -> {
            whatsAppAdapterInterface.onSingleDownload(fileItem);

        });


        viewHolder.rl_main.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                whatsAppAdapterInterface.showAllDownload(true);
                return true;
            }
        });
        viewHolder.rl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsAppAdapterInterface.getPosition(position);
            }
        });


        viewHolder.iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("PathVideo", fileItem.getUri().toString());
                context.startActivity(intent);

            }
        });
        // or do whatever else you need to do for implementation view

    }

    @Override
    public int getItemCount() {
        return fileArrayList == null ? 0 : fileArrayList.size();
    }

    @Override
    public void getPosition(int position) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_main;
        ImageView dowload, iv_play, pcw;
        CheckBox checkboxFav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dowload = itemView.findViewById(R.id.dowload);
            rl_main = itemView.findViewById(R.id.rl_main);
            iv_play = itemView.findViewById(R.id.iv_play);
            pcw = itemView.findViewById(R.id.pcw);
            checkboxFav = itemView.findViewById(R.id.checkbox_fav);
        }
//        ItemsWhatsappViewBinding binding;
//
//        public ViewHolder(ItemsWhatsappViewBinding mbinding) {
//            super(mbinding.getRoot());
//            this.binding = mbinding;
//        }
    }

    public void initProgress() {
        dialogProgress = new ProgressDialog(context);
        dialogProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogProgress.setTitle("Saving");
        dialogProgress.setMessage("Saving. Please wait...");
        dialogProgress.setIndeterminate(true);
        dialogProgress.setCanceledOnTouchOutside(false);
    }

    public class DownloadFileTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... furl) {
            try {
                InputStream in = context.getContentResolver().openInputStream(Uri.parse(furl[0]));
                File f = null;
                f = new File(RootDirectoryWhatsappShow + File.separator + fileName);
                f.setWritable(true, false);
                OutputStream outputStream = new FileOutputStream(f);
                byte buffer[] = new byte[1024];
                int length = 0;

                while ((length = in.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.close();
                in.close();
            } catch (IOException e) {
                System.out.println("error in creating a file");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String fileUrl) {
            Utils.setToast(context, context.getResources().getString(R.string.download_complete));
            try {
                if (Build.VERSION.SDK_INT >= 19) {
                    MediaScannerConnection.scanFile(context, new String[]{new File(RootDirectoryWhatsappShow + File.separator + fileName).getAbsolutePath()}, null, (path, uri) -> {
                        //no action
                    });
                } else {
                    context.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(RootDirectoryWhatsappShow + File.separator + fileName))));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
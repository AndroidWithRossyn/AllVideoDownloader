package com.video.music.downloader.statusandgallery;

import com.video.music.downloader.statusandgallery.model.whatsAppdownloader.WhatsappStatusModel;

import java.util.ArrayList;

public class AppInterface {

    public interface WhatsAppAdapterInterface {
        void onSelectAll(ArrayList<WhatsappStatusModel> statusModelArrayList);

        void onSingleDownload(WhatsappStatusModel singleModel);

        void showAllDownload(boolean show);

        void getPosition(int position);

    }
    public interface DownloadFileTask {
        void downloadComplete();
    }


}

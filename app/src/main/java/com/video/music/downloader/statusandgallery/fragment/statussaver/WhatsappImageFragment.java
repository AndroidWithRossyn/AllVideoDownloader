package com.video.music.downloader.statusandgallery.fragment.statussaver;

import static androidx.databinding.DataBindingUtil.inflate;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.fragment.app.Fragment;

import com.video.music.downloader.R;
import com.video.music.downloader.databinding.FragmentWhatsappImageBinding;
import com.video.music.downloader.statusandgallery.adapter.whatsappdownloader.WhatsappStatusAdapter;
import com.video.music.downloader.statusandgallery.interfacee.FileListWhatsappClickInterface;
import com.video.music.downloader.statusandgallery.model.whatsAppdownloader.WhatsappStatusModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class WhatsappImageFragment extends Fragment implements FileListWhatsappClickInterface {
    FragmentWhatsappImageBinding binding;

    private File[] allfiles;
    ArrayList<WhatsappStatusModel> statusModelArrayList;
    private WhatsappStatusAdapter whatsappStatusAdapter;
    private ArrayList<Uri> fileArrayList;
    private boolean isAllSelected = true;
    private FileListWhatsappClickInterface fileListClickInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = inflate(inflater, R.layout.fragment_whatsapp_image, container, false);
        initViews();
        binding.cbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAllSelected = b;
                if(b==true){
                    binding.hello.setVisibility(View.VISIBLE);
                }
                else {
                    binding.hello.setVisibility(View.GONE);
                }
            }
        });
        return binding.getRoot();
    }

    private void initViews() {
        statusModelArrayList = new ArrayList<>();
        fileArrayList = new ArrayList<>();
        getData();
        binding.swiperefresh.setOnRefreshListener(() -> {
            statusModelArrayList = new ArrayList<>();
            getData();
            binding.swiperefresh.setRefreshing(false);
        });

    }

    private void getData() {
        WhatsappStatusModel whatsappStatusModel;
        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses";
        }
        File targetDirector = new File(targetPath);
        allfiles = targetDirector.listFiles();

        String targetPathBusiness = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Business/Media/.Statuses";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            targetPathBusiness = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/.Statuses";
        }
        File targetDirectorBusiness = new File(targetPathBusiness);
        File[] allfilesBusiness = targetDirectorBusiness.listFiles();
        if (allfilesBusiness == null) {
            File targetDirectorBusinessNew = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Business/Media/.Statuses");
            allfilesBusiness = targetDirectorBusinessNew.listFiles();
        }

        try {
            Arrays.sort(allfiles, (Comparator) (o1, o2) -> {
                if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                    return -1;
                } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                    return +1;
                } else {
                    return 0;
                }
            });

            for (int i = 0; i < allfiles.length; i++) {
                File file = allfiles[i];
                if (Uri.fromFile(file).toString().endsWith(".png") || Uri.fromFile(file).toString().endsWith(".jpg")) {
                    fileArrayList.add(Uri.fromFile(file));
                    whatsappStatusModel = new WhatsappStatusModel("WhatsStatus: " + (i + 1), Uri.fromFile(file), allfiles[i].getAbsolutePath(), file.getName());
                    statusModelArrayList.add(whatsappStatusModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Arrays.sort(allfilesBusiness, (Comparator) (o1, o2) -> {
                if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                    return -1;
                } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                    return +1;
                } else {
                    return 0;
                }
            });

            for (int i = 0; i < allfilesBusiness.length; i++) {
                File file = allfilesBusiness[i];
                if (Uri.fromFile(file).toString().endsWith(".png") || Uri.fromFile(file).toString().endsWith(".jpg")) {
                    fileArrayList.add(Uri.fromFile(file));
                    whatsappStatusModel = new WhatsappStatusModel("WhatsStatusB: " + (i + 1), Uri.fromFile(file), allfilesBusiness[i].getAbsolutePath(), file.getName());
                    statusModelArrayList.add(whatsappStatusModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (statusModelArrayList.size() != 0) {
            binding.tvNoResult.setVisibility(View.GONE);
        } else {
            binding.tvNoResult.setVisibility(View.VISIBLE);
        }
        whatsappStatusAdapter = new WhatsappStatusAdapter(getActivity(),statusModelArrayList, WhatsappImageFragment.this);

        binding.rvFileList.setAdapter(whatsappStatusAdapter);


    }

    @Override
    public void getPosition(int position) {


    }




}

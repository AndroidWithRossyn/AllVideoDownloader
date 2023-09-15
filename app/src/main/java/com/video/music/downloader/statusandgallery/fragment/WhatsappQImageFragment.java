package com.video.music.downloader.statusandgallery.fragment;

import static android.view.View.VISIBLE;
import static androidx.databinding.DataBindingUtil.inflate;
import static com.video.music.downloader.statusandgallery.utils.Utils.createFileFolder;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.video.music.downloader.R;
import com.video.music.downloader.VideoDownloader.Configs.SettingsManager;
import com.video.music.downloader.databinding.FragmentWhatsappImageBinding;

import com.video.music.downloader.statusandgallery.AppInterface;
import com.video.music.downloader.statusandgallery.adapter.whatsappdownloader.WhatsappStatusAdapter;
import com.video.music.downloader.statusandgallery.interfacee.FileListWhatsappClickInterface;
import com.video.music.downloader.statusandgallery.model.whatsAppdownloader.WhatsappStatusModel;
import com.video.music.downloader.statusandgallery.utils.Utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class WhatsappQImageFragment extends Fragment implements FileListWhatsappClickInterface {
    FragmentWhatsappImageBinding binding;

    private File[] allfiles;
    private ArrayList<Uri> fileArrayList;
    ArrayList<WhatsappStatusModel> statusModelArrayList;
    ArrayList<WhatsappStatusModel> selectedStatusModelArrayList;
    private WhatsappStatusAdapter whatsappStatusAdapter;
    private FileListWhatsappClickInterface whatsappClickInterface;
    public static boolean isAllSelected = false;
    public ProgressDialog mProgressDialog;
    public static final int progressType = 0;
    String fileName = "";
    Integer downloadingFilePos = 0;
    public String saveFilePath = SettingsManager.DOWNLOAD_FOLDER_IMAGES + File.separator;

    public WhatsappQImageFragment(ArrayList<Uri> fileArrayList) {
        this.fileArrayList = fileArrayList;
    }

    public WhatsappQImageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = inflate(inflater, R.layout.fragment_whatsapp_image, container, false);
        initViews();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.main_color2));
        }
        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initViews() {
        statusModelArrayList = new ArrayList<>();
        getData();
        binding.swiperefresh.setOnRefreshListener(() -> {
            statusModelArrayList = new ArrayList<>();
            getData();
            binding.swiperefresh.setRefreshing(false);
        });


        binding.cbSelectAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isAllSelected = isChecked;
            whatsappStatusAdapter.notifyDataSetChanged();
            if(isChecked==true){
                binding.hello.setVisibility(View.VISIBLE);
            }
            else {
                binding.hello.setVisibility(View.GONE);
            }

        });

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Downloading...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setMax(100);

    }

    private void getData() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            try {
                for (int i = 0; i < fileArrayList.size(); i++) {
                    WhatsappStatusModel whatsappStatusModel;
                    Uri uri = fileArrayList.get(i);
                    if (uri.toString().endsWith(".png") || uri.toString().endsWith(".jpg")) {
                        whatsappStatusModel = new WhatsappStatusModel("WhatsStatus: " + (i + 1), uri, new File(uri.toString()).getAbsolutePath(), new File(uri.toString()).getName());
                        statusModelArrayList.add(whatsappStatusModel);
                    }
                }
                if (statusModelArrayList.size() != 0) {
                    binding.tvNoResult.setVisibility(View.GONE);
                } else {
                    binding.tvNoResult.setVisibility(VISIBLE);
                }

//                whatsappStatusAdapter = new WhatsappStatusAdapter(getActivity(), statusModelArrayList, WhatsappQImageFragment.this);
                whatsappStatusAdapter = new WhatsappStatusAdapter(getActivity(), statusModelArrayList, new AppInterface.WhatsAppAdapterInterface() {
                    @Override
                    public void onSelectAll(ArrayList<WhatsappStatusModel> statusModelArrayList) {
                        //TODO all download start
//                        saveFile(statusModelArrayList);

                        binding.hello.setVisibility(View.VISIBLE);
                        selectedStatusModelArrayList.addAll(statusModelArrayList);
                        //saveFile(statusModelArrayList);

                    }

                    @Override
                    public void onSingleDownload(WhatsappStatusModel singleModel) {
                        if (singleModel != null) {
//                            AdUtils.showInterstitialAd(requireActivity(), new AppInterfaces.InterStitialADInterface() {
//                                @Override
//                                public void adLoadState(boolean isLoaded) {
                                    selectedStatusModelArrayList.add(singleModel);
                                    saveFile(selectedStatusModelArrayList);
//                                }
//                            });


//                            binding.cbSelectAll.setOnClickListener(new View.OnClickListener() {
//                                public void onClick(View v){
//                                    whatsappStatusAdapter.notifyDataSetChanged();
//                                    getData();
//                                }
//                            });

                        }
                    }

                    @Override
                    public void showAllDownload(boolean s) {
                        binding.hello.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void getPosition(int position) {

                    }

                });
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
                binding.rvFileList.setLayoutManager(layoutManager);
                binding.rvFileList.setAdapter(whatsappStatusAdapter);
                selectedStatusModelArrayList = new ArrayList<>();
                binding.downloadall.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
//                        AdUtils.showInterstitialAd(requireActivity(), new AppInterfaces.InterStitialADInterface() {
//                            @Override
//                            public void adLoadState(boolean isLoaded) {
                                saveFile(statusModelArrayList);
//                            }
//                        });

//                                whatsappStatusAdapter.notifyDataSetChanged();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void saveFile(ArrayList<WhatsappStatusModel> selectedStatusModelArrayList) {
        createFileFolder();


//        if (singleFileURI.toString().endsWith(".mp4")) {
//            fileName = "status_" + System.currentTimeMillis() + ".mp4";
//            new DownloadFileTask().execute(singleFileURI.toString());
//        } else {
//            fileName = "status_" + System.currentTimeMillis() + ".png";
//            new DownloadFileTask().execute(singleFileURI.toString());
//        }

//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {


//
            new DownloadFileTask().execute(selectedStatusModelArrayList);
//
//        }
        /*else {
            final String path = singleFilePath;
            String filename = path.substring(path.lastIndexOf("/") + 1);
            final File file = new File(path);
            File destFile = new File(singleFilePath);
            try {
                FileUtils.copyFileToDirectory(file, destFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String fileNameChange = filename.substring(12);
            File newFile = new File(saveFilePath + fileNameChange);
            String contentType = "image/*";
            if (singleFileURI.toString().endsWith(".mp4")) {
                contentType = "video/*";
            } else {
                contentType = "image/*";
            }
            MediaScannerConnection.scanFile(getActivity(), new String[]{newFile.getAbsolutePath()}, new String[]{contentType}, new MediaScannerConnection.MediaScannerConnectionClient() {
                public void onMediaScannerConnected() {
                    //NA
                }

                public void onScanCompleted(String path, Uri uri) {
                    //NA
                }
            });

            File from = new File(saveFilePath, filename);
            File to = new File(saveFilePath, fileNameChange);
            from.renameTo(to);
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.saved_to) + saveFilePath + fileNameChange, Toast.LENGTH_LONG).show();
        }*/


    }


    @Override
    public void getPosition(int position) {
        /*Intent inNext = new Intent(getActivity(), FullViewHomeWAActivity.class);
        inNext.putExtra("ImageDataFile", fileArrayList);
        inNext.putExtra("Position", position);
        getActivity().startActivity(inNext);*/
    }


    public class DownloadFileTask extends AsyncTask<ArrayList<WhatsappStatusModel>, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mProgressDialog.dismiss();
            Utils.setToast(getActivity(), getActivity().getResources().getString(R.string.download_complete));

        }

        @Override
        protected String doInBackground(ArrayList<WhatsappStatusModel>... arrayLists) {
            try {
                for (int i = 0; i < arrayLists[0].size(); i++) {
                    InputStream in = getActivity().getContentResolver().openInputStream(arrayLists[0].get(i).getUri());
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                        try {
                            if (arrayLists[0].get(i).getUri().toString().endsWith(".mp4")) {
                                fileName = "status_" + System.currentTimeMillis() + ".mp4";
//                                new DownloadFileTask(arrayLists[0].get(i).getUri().toString()).execute(selectedStatusModelArrayList);
                            } else {
                                fileName = "status_" + System.currentTimeMillis() + ".png";
//                                new DownloadFileTask(arrayLists[0].get(i).getUri().toString()).execute(selectedStatusModelArrayList);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        final String path = arrayLists[0].get(i).getPath();
                        String filename = path.substring(path.lastIndexOf("/") + 1);
                        final File file = new File(path);
                        File destFile = new File(arrayLists[0].get(i).getPath());
                        try {
                            FileUtils.copyFileToDirectory(file, destFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String fileNameChange = filename.substring(12);
                        File newFile = new File(saveFilePath + fileNameChange);
                        String contentType = "image/*";
                        if (arrayLists[0].get(i).getUri().toString().endsWith(".mp4")) {
                            contentType = "video/*";
                        } else {
                            contentType = "image/*";
                        }
                        MediaScannerConnection.scanFile(getActivity(), new String[]{newFile.getAbsolutePath()}, new String[]{contentType}, new MediaScannerConnection.MediaScannerConnectionClient() {
                            public void onMediaScannerConnected() {
                                //NA
                            }

                            public void onScanCompleted(String path, Uri uri) {
                                //NA
                            }
                        });

                        File from = new File(saveFilePath, filename);
                        File to = new File(saveFilePath, fileNameChange);
                        from.renameTo(to);
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.saved_to) + saveFilePath + fileNameChange, Toast.LENGTH_LONG).show();
                    }
                    File f = null;
                    f = new File(SettingsManager.DOWNLOAD_FOLDER_IMAGES + File.separator + fileName);
                    f.setWritable(true, false);
                    int lenghtOfFile = fileArrayList.size();
                    OutputStream outputStream = new FileOutputStream(f);
                    byte buffer[] = new byte[1024];
                    int length = 0;


                    while ((length = in.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }

                    outputStream.close();
                    in.close();
                }
                selectedStatusModelArrayList.clear();
            } catch (IOException e) {
                System.out.println("error in creating a file");
                e.printStackTrace();
            }


            return "";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            mProgressDialog.setProgress(Integer.parseInt(values[0]));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
        }
    }

    //progress dialog

}

package com.video.music.downloader.VideoDownloader.StatusSaver.activities;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.video.music.downloader.VideoDownloader.StatusSaver.Adapters.ImageAdapter;
import com.video.music.downloader.VideoDownloader.StatusSaver.Model.ModelStatus;
import com.video.music.downloader.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.video.music.downloader.VideoDownloader.StatusSaver.TouchImageView;
import com.video.music.downloader.VideoDownloader.StatusSaver.utils.MyConstants;
import static com.video.music.downloader.VideoDownloader.StatusSaver.fragments.ImageFragment.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ImageViewerActivity extends AppCompatActivity {

    String image_path="", myPackage="", myPath="", types="", type="";
    FloatingActionMenu fabs_menu;
    String myPackages;
    File getPure;
    int image_pos;
    int position = 0;
    FloatingActionButton saveButton, shareButton, repostButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        fabs_menu = (FloatingActionMenu) findViewById(R.id.fabs_menu);
        saveButton = (FloatingActionButton) findViewById(R.id.save);
        shareButton = (FloatingActionButton) findViewById(R.id.share);
        repostButton = (FloatingActionButton) findViewById(R.id.repost);
        deleteButton = (FloatingActionButton) findViewById(R.id.delete);

        Intent intent = getIntent();
        if (intent != null) {

        String message = intent.getStringExtra((ImageAdapter.POSITION));
        image_pos = Integer.parseInt(message);
        myPath = intent.getStringExtra("myPath");

        myPackage = intent.getStringExtra("myPackage");
        type = intent.getStringExtra("type");
    }
           final String path = MyConstants.ADD_DIR;

            myPackage = "com.whatsapp";
            getPure = MyConstants.STATUS_DIRECTORY;
            myPackages = "WhatsApp";

        final File[] files = getPure.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return (name.contains(".jpg")|| name.contains(".png"));
                }
            });
            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return Long.compare(o2.lastModified(), o1.lastModified());
                }
            });

        final ViewPager viewPager = findViewById(R.id.viewager);
            TouchImageAdapter adapter = new TouchImageAdapter(this, data);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(image_pos);

            saveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int currentItem = viewPager.getCurrentItem();
                    copyFileOrDirectory(files[currentItem].getAbsolutePath(), path);
                }
            });
            shareButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int currentItem = viewPager.getCurrentItem();
                    shareNow(files[currentItem].getAbsolutePath());
                }
            });
            repostButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int currentItem = viewPager.getCurrentItem();
                    sendTo("image/*", files[currentItem].getAbsolutePath(), myPackage);
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int currentItem = viewPager.getCurrentItem();
                    startDialog(files[currentItem].getAbsolutePath());
                }
            });

    }

    private class TouchImageAdapter extends PagerAdapter {
        private Context context;
        //File[] files;
        final ArrayList<ModelStatus> data;

        TouchImageAdapter(Context context, ArrayList<ModelStatus> data) {
            this.context = context;
            //this.files = files;
            this.data = data;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            TouchImageView imageView = new TouchImageView(container.getContext());
            Bitmap bitmap = BitmapFactory.decodeFile(data.get(position).getFull_path());
            Glide.with(context)
                    .asBitmap()
                    .load(bitmap)
                    .apply(new RequestOptions().override(1600, 1600))
                    .into(imageView);
            container.addView(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    public void copyFileOrDirectory(String srcDir, String dstDir) {

        try {
            File src = new File(srcDir);
            File dst = new File(dstDir, src.getName());

            if (src.isDirectory()) {

                String files[] = src.list();
                int filesLength = files.length;
                for (int i = 0; i < filesLength; i++) {
                    String src1 = (new File(src, files[i]).getPath());
                    String dst1 = dst.getPath();
                    copyFileOrDirectory(src1, dst1);

                }
            } else {
                copyFile(src, dst);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
            Toast.makeText(getApplicationContext(), "Picture Saved", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(destFile));
            this.sendBroadcast(intent);

        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    public void shareNow(String path){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        File file = new File(path);
        Uri uri = FileProvider.getUriForFile(this, "com.techvinz.wahstatussaver.FileProvider", file);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent, "Share Via"));
    }

    public void sendTo(String type, String path, String myPackage) {

        PackageManager pm = getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(myPackage, PackageManager.GET_META_DATA);
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            Uri uri = Uri.parse(path);
            sharingIntent.setType(type);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
            sharingIntent.setPackage(myPackage);//package name of the app
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, myPackages+" not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    private void startDialog(final String myPath) {
        final AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(ImageViewerActivity.this);
        myAlertDialog.setTitle("Delete Status");
        myAlertDialog.setMessage("You are about to delete this image permanently. This action cannot be undone");

        myAlertDialog.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        File file = new File(myPath);
                        file.delete();
                        Toast.makeText(ImageViewerActivity.this, "Status Deleted", Toast.LENGTH_SHORT).show();
                        finish();
                        }
                });

        myAlertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //
                    }
                });
        myAlertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }
}

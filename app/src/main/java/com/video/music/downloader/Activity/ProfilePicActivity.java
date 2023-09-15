package com.video.music.downloader.Activity;

import static com.video.music.downloader.AdsUtils.Utils.Global.getContentMediaUri;
import static com.video.music.downloader.AdsUtils.Utils.Global.getPath;
import static com.video.music.downloader.AdsUtils.Utils.Global.isLatestVersion;

import static com.video.music.downloader.PermissionHelperActivity.askCompactPermissions;
import static com.video.music.downloader.PermissionHelperActivity.isPermissionsGranted;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.video.music.downloader.Adapter.ProfilepicAdapter;
import com.video.music.downloader.AdsUtils.Utils.Global;
import com.video.music.downloader.PermissionHelperActivity;
import com.video.music.downloader.PermitConstant;
import com.video.music.downloader.R;
import com.video.music.downloader.databinding.ActivityProfilePicBinding;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ProfilePicActivity extends AppCompatActivity implements ProfilepicAdapter.OnClickListener {
    
    ActivityProfilePicBinding binding;
    ProfilepicAdapter animSelectionAdapter;
    String[] images = new String[0];
    ArrayList<String> listImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_pic);

        getAnimData();

        binding.mTxtChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckPermission();
            }
        });

        binding.allowPermissionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DetailActivity.class));
            }
        });

    }

    private void getAnimData() {
        try {
            images = getAssets().list("Anims");
            listImages = new ArrayList<String>(Arrays.asList(images));
            animSelectionAdapter = new ProfilepicAdapter(this, listImages, this);
            binding.profileRV.setLayoutManager(new MyStaggeredGridLayoutManager(ProfilePicActivity.this, 3));
            binding.profileRV.setAdapter(animSelectionAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onclickAnim(String string) {
        InputStream inputstream = null;
        try {
            Global.ProfilePath = "Anims/" + string;
            inputstream = getAssets().open("Anims/" + string);
            Drawable drawable = Drawable.createFromStream(inputstream, null);
            binding.circularimg.setImageBitmap(null);
            binding.circularimg.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mCheckPermission() {
        String[] string;

        if (isLatestVersion()) {
            string = new String[]{PermitConstant.Manifest_READ_EXTERNAL_STORAGE};
        } else {
            string = new String[]{PermitConstant.Manifest_READ_EXTERNAL_STORAGE,
                    PermitConstant.Manifest_WRITE_EXTERNAL_STORAGE};
        }

        if (isPermissionsGranted(this, string)) {
            OpenImageChooser();
        } else {
            askCompactPermissions(string, new PermissionHelperActivity.PermissionResult() {
                @Override
                public void permissionGranted() {
                    OpenImageChooser();
                }

                @Override
                public void permissionDenied() {
                    Toast.makeText(ProfilePicActivity.this, "Permission Denied..!", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void permissionForeverDenied() {
                    Toast.makeText(ProfilePicActivity.this, "Permission Forever Denied..!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }

    public void OpenImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, getContentMediaUri());
        intent.setType("image/*");
        startActivityForResult(intent, 22);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 22) {
                String actualFilepath = getPath(this, data.getData());
                binding.circularimg.setImageDrawable(null);
                Glide.with(this).load(actualFilepath).into(binding.circularimg);
                Global.ProfilePath = actualFilepath;
                if (animSelectionAdapter != null) {
                    animSelectionAdapter.clearSelection();
                }
            } else if (requestCode == 3) {
                Toast.makeText(this, "Trim video success", Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        AdUtils.showInterstitialAd(AnimSelectionActivity.this, new AppInterfaces.InterStitialADInterface() {
//            @Override
//            public void adLoadState(boolean isLoaded) {
//                finish();
//            }
//        });
    }
}
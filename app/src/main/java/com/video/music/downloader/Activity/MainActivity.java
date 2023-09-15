package com.video.music.downloader.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.video.music.downloader.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView allow_permissions_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_main);
        allow_permissions_btn = findViewById(R.id.allow_permissions_btn);

        allow_permissions_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] arrPermissionsEdit;

                if (Build.VERSION.SDK_INT >= 33)
                    arrPermissionsEdit = new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO", "android.permission.POST_NOTIFICATIONS"};
                else if (Build.VERSION.SDK_INT >= 29)
                    arrPermissionsEdit = new String[]{"android.permission.READ_EXTERNAL_STORAGE"};
                else
                    arrPermissionsEdit= new String[]{"android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"};

                    Dexter.withContext(MainActivity.this).withPermissions(arrPermissionsEdit).withListener(new MultiplePermissionsListener() {
                        public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                            if (multiplePermissionsReport.areAllPermissionsGranted()) {
//                            checkClick();
                                startActivity(new Intent(getApplicationContext(), PrivacyPolicy.class));

                            }

                            if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                                DetailsDialog.showDetailsDialog(MainActivity.this);
//                            Toast.makeText(MainActivity.this, "Please Provide Permission", Toast.LENGTH_SHORT).show();
                            }
                        }


                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }

                    }).withErrorListener(dexterError -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show()).onSameThread().check();
            }
        });


    }
}
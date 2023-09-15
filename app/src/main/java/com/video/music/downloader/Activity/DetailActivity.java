package com.video.music.downloader.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;

import com.google.android.material.card.MaterialCardView;
import com.video.music.downloader.AdsUtils.Utils.Global;
import com.video.music.downloader.R;
import com.video.music.downloader.databinding.ActivityDetailBinding;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    Context mContext;
//    MaterialCardView mScVBottom;
//    FirebaseDatabase database;
    int mPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mContext = this;

        binding.maleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.maleImg.setImageDrawable(getResources().getDrawable(R.drawable.anim5_selected));
                binding.femaleImg.setImageDrawable(getResources().getDrawable(R.drawable.anim3));
            }
        });

        binding.femaleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.maleImg.setImageDrawable(getResources().getDrawable(R.drawable.anim5));
                binding.femaleImg.setImageDrawable(getResources().getDrawable(R.drawable.anim3_selected));
            }
        });

        binding.maletext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.maleImg.setImageDrawable(getResources().getDrawable(R.drawable.anim5_selected));
                binding.femaleImg.setImageDrawable(getResources().getDrawable(R.drawable.anim3));
            }
        });

        binding.femaletext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.maleImg.setImageDrawable(getResources().getDrawable(R.drawable.anim5));
                binding.femaleImg.setImageDrawable(getResources().getDrawable(R.drawable.anim3_selected));
            }
        });

        binding.mETFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    binding.mETFullName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.mETUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    binding.mETUserName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.mETAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    binding.mETAge.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.mTxtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mSetInfoLayout();
                mCheckTextData();
                startActivity(new Intent(getApplicationContext(), SocialMediaSelectActivity.class));

            }
        });
    }

//    @OnClick({R.id.mTxtNext, R.id.mTxtGetStarted})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.mTxtNext:
//                mSetInfoLayout();
//                break;
//            case R.id.mTxtGetStarted:
//                mSetDatabseData();
//                break;
//        }
//    }

    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
        }

        return bitmap;
    }

    private void mSetInfoLayout() {
        switch (mPos) {
            case 0:
                mCheckTextData();
                break;
            case 1:
                mCheckAgeTextData();
                break;
            case 2:
//                mCheckAgeTextData();
                startActivity(new Intent(getApplicationContext(), SocialMediaSelectActivity.class));
                break;
            default:
                break;
        }
    }

    private void mCheckTextData() {
        if (Global.isEmptyStr(binding.mETFullName.getText().toString())) {
            binding.mETFullName.setError("Enter Full Name");
        } else {
            binding.mETFullName.setError(null);
        }
        if (Global.isEmptyStr(binding.mETUserName.getText().toString())) {
            binding.mETUserName.setError("Enter User Name");
        } else {
            binding.mETUserName.setError(null);
        }
        if (Global.isEmptyStr(binding.mETAge.getText().toString())) {
            binding.mETAge.setError("Enter Age");
        } else {
            binding.mETAge.setError(null);
        }
        if (!Global.isEmptyStr(binding.mETFullName.getText().toString()) && !Global.isEmptyStr(binding.mETUserName.getText().toString())) {
//            binding.mLLAgeInfo.setVisibility(View.VISIBLE);
//            binding.mLLNameInfo.setVisibility(View.GONE);
            mPos = 1;
        }

        if (Global.isEmptyStr(binding.mETAge.getText().toString())) {
            binding.mETAge.setError("Enter Age");
        } else {
            binding.mETAge.setError(null);
        }
        if (!Global.isEmptyStr(binding.mETAge.getText().toString())) {
//            mScVBottom.setVisibility(View.VISIBLE);
//            binding.mScVTop.setVisibility(View.INVISIBLE);
            mPos = 2;
        }

    }

    private void mCheckAgeTextData() {
        if (Global.isEmptyStr(binding.mETAge.getText().toString())) {
            binding.mETAge.setError("Enter Age");
        } else {
            binding.mETAge.setError(null);
        }
        if (!Global.isEmptyStr(binding.mETAge.getText().toString())) {
//            mScVBottom.setVisibility(View.VISIBLE);
//            binding.mScVTop.setVisibility(View.INVISIBLE);
            mPos = 2;
        }
    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        if (isChecked) {
//            if (buttonView.getId() == R.id.mRbtnMale) {
//                binding.mRbtnFemale.setChecked(false);
//            }
//            if (buttonView.getId() == R.id.mRbtnFemale) {
//                binding.mRbtnMale.setChecked(false);
//            }
//        }
    }

    /*public String saveUriToFile(Uri uri, Context context) {
        InputStream inputStream;
        BufferedInputStream bufferedInputStream;
        BufferedOutputStream bufferedOutputStream;
        Exception e;
        BufferedInputStream bufferedInputStream2 = null;
        BufferedOutputStream bufferedOutputStream2 = null;
        BufferedInputStream bufferedInputStream3 = null;
        Context applicationContext = getApplicationContext();
        File cacheFile = getCacheFile("ProfilePic.png", context);
        BufferedOutputStream bufferedOutputStream3 = null;
        try {
            inputStream = applicationContext.getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                try {
                    bufferedInputStream3 = new BufferedInputStream(inputStream);
                } catch (Exception e2) {
                    e = e2;
                    bufferedOutputStream2 = null;
                } catch (Throwable th) {
                    th = th;
                    bufferedInputStream = null;
                    if (bufferedInputStream != null) {
                    }
                    if (bufferedOutputStream3 != null) {
                    }
                    if (inputStream != null) {
                    }
                    throw th;
                }
            } else {
                bufferedInputStream3 = null;
            }
            try {
                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(cacheFile, false));
            } catch (Exception e3) {
                e = e3;
                bufferedOutputStream = null;
                bufferedInputStream2 = bufferedInputStream3;
                try {
                    e.printStackTrace();
                    if (bufferedInputStream2 != null) {
                    }
                    if (bufferedOutputStream != null) {
                    }
                    if (inputStream != null) {
                    }
                    cacheFile = null;
                    if (cacheFile != null) {
                    }
                } catch (Throwable th2) {
                    bufferedOutputStream3 = bufferedOutputStream;
                    bufferedInputStream = bufferedInputStream2;
                    if (bufferedInputStream != null) {
                    }
                    if (bufferedOutputStream3 != null) {
                    }
                    if (inputStream != null) {
                    }
                    throw th2;
                }
            } catch (Throwable th3) {
                bufferedInputStream = bufferedInputStream3;
                if (bufferedInputStream != null) {
                }
                if (bufferedOutputStream3 != null) {
                }
                if (inputStream != null) {
                }
                throw th3;
            }
            try {
                byte[] bArr = new byte[1024];
                if (bufferedInputStream3 != null) {
                    bufferedInputStream3.read(bArr);
                    do {
                        bufferedOutputStream.write(bArr);
                    } while (bufferedInputStream3.read(bArr) != -1);
                } else {
                    cacheFile = null;
                }
                if (bufferedInputStream3 != null) {
                    try {
                        bufferedInputStream3.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                        cacheFile = null;
                        if (cacheFile != null) {
                        }
                    }
                }
                bufferedOutputStream.close();
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e5) {
                e = e5;
                bufferedInputStream2 = bufferedInputStream3;
                e.printStackTrace();
                if (bufferedInputStream2 != null) {
                }
                if (bufferedOutputStream != null) {
                }
                if (inputStream != null) {
                }
                cacheFile = null;
                if (cacheFile != null) {
                }
            }
        } catch (IOException e6) {
            e = e6;
            inputStream = null;
            bufferedOutputStream2 = null;
            bufferedOutputStream = bufferedOutputStream2;
            e.printStackTrace();
            if (bufferedInputStream2 != null) {
                try {
                    bufferedInputStream2.close();
                    if (bufferedOutputStream != null) {
                        bufferedOutputStream.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            cacheFile = null;
            if (cacheFile != null) {
            }
        } catch (Throwable th4) {
            inputStream = null;
            bufferedInputStream = null;
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                    try {
                        throw th4;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }
            if (bufferedOutputStream3 != null) {
                try {
                    bufferedOutputStream3.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                throw th4;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        if (cacheFile != null) {
            return cacheFile.getPath();
        }
        return null;
    }*/
//}
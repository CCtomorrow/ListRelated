package com.ai.listrelated.sample.img;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ai.listrelated.sample.R;

import java.io.File;

public class ImageSizeActivity extends AppCompatActivity {

    TextView vDrawableSize;
    TextView vSdFileSize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgsize);
        vDrawableSize = findViewById(R.id.drawable_size);
        vSdFileSize = findViewById(R.id.sd_size);
    }

    public void getDrawable(View view) {
        // ﻿(inTargetDensity / inDensity * width +0.5) * (inTargetDensity / inDensity * height +0.5) * 4
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        vDrawableSize.setText("从Drawable获取图片的大小:" + bitmap.getByteCount());
    }

    public void getFile(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "bg.jpg");
        if (!file.exists()) {
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        if (bitmap == null) {
            return;
        }
        vSdFileSize.setText("从File获取图片的大小:" + bitmap.getByteCount());
    }
}

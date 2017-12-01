/*
 *          Copyright (C) 2016 jarlen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.summer.photos;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.summer.photos.enhance.PhotoEnhance;
import com.summer.photos.utils.FileUtils;
import com.summer.photos.utils.PhotoUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 增强
 *
 * @author summer
 */
public class EnhanceActivity extends AppCompatActivity implements
        View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "EnhanceActivity";
    private Toolbar toolbar;
    private ImageView pictureShow;
    private SeekBar saturationSeekBar;
    private SeekBar brightnessSeekBar;
    private SeekBar contrastSeekBar;
    private ImageView back;
    private Button save;
    private Bitmap bitmapSrc;
    private PhotoEnhance pe;
    private int mProgress = 0;
    private Bitmap bit = null;
    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_enhance);
        Intent intent = getIntent();
        imgPath = intent.getStringExtra("imagePath");
        bitmapSrc = BitmapFactory.decodeFile(imgPath);
        initView();
        if (bitmapSrc != null) {
            pictureShow.setImageBitmap(bitmapSrc);
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pictureShow = (ImageView) findViewById(R.id.enhancePicture);

        saturationSeekBar = (SeekBar) findViewById(R.id.saturation);
        saturationSeekBar.setOnSeekBarChangeListener(this);

        brightnessSeekBar = (SeekBar) findViewById(R.id.brightness);
        brightnessSeekBar.setOnSeekBarChangeListener(this);

        contrastSeekBar = (SeekBar) findViewById(R.id.contrast);
        contrastSeekBar.setOnSeekBarChangeListener(this);

        back = (ImageView) findViewById(R.id.img_back);
        back.setOnClickListener(this);
        save = (Button) findViewById(R.id.btn_save);
        save.setOnClickListener(this);

        pe = new PhotoEnhance(bitmapSrc);

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mProgress = progress;
        int type = 0;

        switch (seekBar.getId()) {
            case R.id.saturation:
                pe.setSaturation(mProgress);
                type = pe.Enhance_Saturation;
                break;
            case R.id.brightness:
                pe.setBrightness(mProgress);
                type = pe.Enhance_Brightness;
                break;

            case R.id.contrast:
                pe.setContrast(mProgress);
                type = pe.Enhance_Contrast;
                break;

            default:
                break;
        }

        bit = pe.handleImage(type);
        pictureShow.setImageBitmap(bit);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;

            case R.id.btn_save:
                if (bit == null && bitmapSrc != null) {
                    bit = bitmapSrc;
                }
                String savePath = PhotoUtils.saveBitmap(bit);
                Intent intent = new Intent();
                intent.setClass(EnhanceActivity.this, ShareActivity.class);
                intent.putExtra("savePath", savePath);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}

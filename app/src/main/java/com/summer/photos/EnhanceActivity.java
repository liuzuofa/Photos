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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.summer.photos.enhance.PhotoEnhance;
import com.summer.photos.utils.FileUtils;

import java.io.FileNotFoundException;

/**
 * 增强
 *
 * @author summer
 */
public class EnhanceActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "EnhanceActivity";
    private ImageView pictureShow;
    private SeekBar saturationSeekBar, brightnessSeekBar, contrastSeekBar;
    private Bitmap bitmapSrc;
    private PhotoEnhance pe;
    private int mProgress = 0;
    private Bitmap bit = null;
    private static final int WRITE_PERMISSION = 1;
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

        pictureShow = (ImageView) findViewById(R.id.enhancePicture);

        saturationSeekBar = (SeekBar) findViewById(R.id.saturation);
        saturationSeekBar.setOnSeekBarChangeListener(this);

        brightnessSeekBar = (SeekBar) findViewById(R.id.brightness);
        brightnessSeekBar.setOnSeekBarChangeListener(this);

        contrastSeekBar = (SeekBar) findViewById(R.id.contrast);
        contrastSeekBar.setOnSeekBarChangeListener(this);

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

    /*@Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.btn_ok :

                FileUtils.writeImage(bit, imgPath, 100);
                Intent okData = new Intent();
                okData.putExtra("camera_path", imgPath);
                setResult(RESULT_OK, okData);
                recycle();
                this.finish();
                break;

            case R.id.btn_cancel :

                Intent cancelData = new Intent();
                setResult(RESULT_CANCELED, cancelData);
                recycle();
                this.finish();
                break;

            default :
                break;
        }
    }*/

    private void recycle() {
        if (bitmapSrc != null) {
            bitmapSrc.recycle();
            bitmapSrc = null;
        }

        if (bit != null) {
            bit.recycle();
            bit = null;
        }
    }

    @Override
    protected void onPause() {
        recycle();
        super.onPause();
    }
}

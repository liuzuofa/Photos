package com.summer.photos.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.summer.photos.R;
import com.summer.photos.watermark.WatermarkView;

public class WatermarkActivity extends AppCompatActivity {

    private String mImagePath;
    private Bitmap mBitmap;
    private WatermarkView watermarkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watermark);
        initView();
    }

    private void initView() {
        Intent filterIntent = getIntent();
        mImagePath = filterIntent.getStringExtra("imagePath");
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 1;
        mBitmap = BitmapFactory.decodeFile(mImagePath, option);

        watermarkView = (WatermarkView) findViewById(R.id.content_view);
        watermarkView.setBitmap(mBitmap);

    }
}

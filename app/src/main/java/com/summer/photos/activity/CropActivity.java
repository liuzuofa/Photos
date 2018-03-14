package com.summer.photos.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.summer.photos.R;
import com.summer.photos.crop.CropImageType;
import com.summer.photos.crop.CropImageView;
import com.summer.photos.utils.PhotoUtils;

public class CropActivity extends AppCompatActivity implements View.OnClickListener {

    private String mImagePath;
    private Bitmap mBitmap;

    private ImageView imgBack;
    private Button btnSave;

    private CropImageView cropImage;

    private LinearLayout crop_free;
    private LinearLayout crop_one;
    private LinearLayout crop_three;
    private LinearLayout crop_four;
    private LinearLayout crop_six;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        initView();
    }

    private void initView() {
        Intent filterIntent = getIntent();
        mImagePath = filterIntent.getStringExtra("imagePath");
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 1;
        mBitmap = BitmapFactory.decodeFile(mImagePath, option);

        cropImage = (CropImageView) findViewById(R.id.cropmageView);

        imgBack = (ImageView) findViewById(R.id.crop_img_back);
        imgBack.setOnClickListener(this);
        btnSave = (Button) findViewById(R.id.crop_btn_save);
        btnSave.setOnClickListener(this);

        crop_free = (LinearLayout) findViewById(R.id.crop_free);
        crop_free.setOnClickListener(this);
        crop_one = (LinearLayout) findViewById(R.id.crop_one);
        crop_one.setOnClickListener(this);
        crop_three = (LinearLayout) findViewById(R.id.crop_three);
        crop_three.setOnClickListener(this);
        crop_four = (LinearLayout) findViewById(R.id.crop_four);
        crop_four.setOnClickListener(this);
        crop_six = (LinearLayout) findViewById(R.id.crop_six);
        crop_six.setOnClickListener(this);

        Bitmap cropBitmap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.crop_button);

        cropImage.setCropOverlayCornerBitmap(cropBitmap);
        cropImage.setImageBitmap(mBitmap);

        cropImage.setGuidelines(CropImageType.CROPIMAGE_GRID_ON_TOUCH);// 触摸时显示网格
        cropImage.setFixedAspectRatio(false);// 自由剪切
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.crop_img_back:
                finish();
                break;
            case R.id.crop_btn_save:
                Bitmap cropImageBitmap = cropImage.getCroppedImage();
                String savePath = PhotoUtils.saveBitmap(cropImageBitmap);
                Intent intent = new Intent();
                intent.setClass(CropActivity.this, ShareActivity.class);
                intent.putExtra("savePath", savePath);
                startActivity(intent);
            case R.id.crop_free:
                cropImage.setFixedAspectRatio(false);
                break;
            case R.id.crop_one:
                cropImage.setFixedAspectRatio(true);
                cropImage.setAspectRatio(10, 10);
                break;
            case R.id.crop_three:
                cropImage.setFixedAspectRatio(true);
                cropImage.setAspectRatio(30, 20);
                break;
            case R.id.crop_four:
                cropImage.setFixedAspectRatio(true);
                cropImage.setAspectRatio(40, 30);
                break;
            case R.id.crop_six:
                cropImage.setFixedAspectRatio(true);
                cropImage.setAspectRatio(160, 90);
                break;
        }

    }
}

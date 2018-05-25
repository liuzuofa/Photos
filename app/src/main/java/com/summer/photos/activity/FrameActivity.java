package com.summer.photos.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.summer.photos.R;
import com.summer.photos.frame.PhotoFrame;
import com.summer.photos.utils.PhotoUtils;

public class FrameActivity extends BaseActivity implements View.OnClickListener{

    private PhotoFrame mImageFrame;
    private String mImagePath;
    private Bitmap mBitmap;
    private Bitmap newBitmap;

    private ImageView mImageView;
    private Button btnSave;
    private ImageView imgBack;

    private ImageView mOneImageView;
    private ImageView mTwoImageView;
    private ImageView mThreeImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        mImagePath = intent.getStringExtra("imagePath");
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 1;
        mBitmap = BitmapFactory.decodeFile(mImagePath, option);
        newBitmap = mBitmap;

        imgBack = (ImageView) findViewById(R.id.frame_img_back);
        imgBack.setOnClickListener(this);
        btnSave = (Button) findViewById(R.id.frame_btn_save);
        btnSave.setOnClickListener(this);

        mImageView = (ImageView) findViewById(R.id.frame_image);
        mImageView.setImageBitmap(mBitmap);

        mOneImageView = (ImageView) findViewById(R.id.photoRes_one);
        mOneImageView.setOnClickListener(this);
        mTwoImageView = (ImageView) findViewById(R.id.photoRes_two);
        mTwoImageView.setOnClickListener(this);
        mThreeImageView = (ImageView) findViewById(R.id.photoRes_three);
        mThreeImageView.setOnClickListener(this);

        reset();
        mImageFrame = new PhotoFrame(this,mBitmap);
    }

    /**
     * 重新设置一下图片
     */
    private void reset()
    {
        mImageView.setImageBitmap(newBitmap);
        mImageView.invalidate();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frame_img_back:
                finish();
                break;
            case R.id.frame_btn_save:
                String savePath = PhotoUtils.saveBitmap(newBitmap);
                Intent intent = new Intent();
                intent.setClass(FrameActivity.this, ShareActivity.class);
                intent.putExtra("savePath", savePath);
                startActivity(intent);
                break;
            case R.id.photoRes_one:
                mImageFrame.setFrameType(PhotoFrame.FRAME_SMALL);
                mImageFrame.setFrameResources(
                        R.drawable.frame_around1_left_top,
                        R.drawable.frame_around1_left,
                        R.drawable.frame_around1_left_bottom,
                        R.drawable.frame_around1_bottom,
                        R.drawable.frame_around1_right_bottom,
                        R.drawable.frame_around1_right,
                        R.drawable.frame_around1_right_top,
                        R.drawable.frame_around1_top);
                newBitmap = mImageFrame.combineFrameRes();
                break;
            case R.id.photoRes_two:
                mImageFrame.setFrameType(PhotoFrame.FRAME_SMALL);
                mImageFrame.setFrameResources(
                        R.drawable.frame_around2_left_top,
                        R.drawable.frame_around2_left,
                        R.drawable.frame_around2_left_bottom,
                        R.drawable.frame_around2_bottom,
                        R.drawable.frame_around2_right_bottom,
                        R.drawable.frame_around2_right,
                        R.drawable.frame_around2_right_top,
                        R.drawable.frame_around2_top);
                newBitmap = mImageFrame.combineFrameRes();
                break;
            case R.id.photoRes_three:
                mImageFrame.setFrameType(PhotoFrame.FRAME_BIG);
                mImageFrame.setFrameResources(R.drawable.frame_around3_big);

                newBitmap = mImageFrame.combineFrameRes();
                break;
           /* case R.id.photoRes_four:
                mImageFrame.setFrameType(PhotoFrame.FRAME_BIG);
                mImageFrame.setFrameResources(R.drawable.frame_around4_big);

                newBitmap = mImageFrame.combineFrameRes();
                break;*/

        }
        reset();
    }
}

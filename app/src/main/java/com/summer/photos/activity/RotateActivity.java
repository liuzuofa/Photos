package com.summer.photos.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.summer.photos.R;
import com.summer.photos.utils.PhotoUtils;

public class RotateActivity extends BaseActivity implements View.OnClickListener {

    private String mImagePath;
    private Bitmap mBitmap;
    private Bitmap newBitmap;

    private ImageView imgBack;
    private Button btnSave;
    private ImageView mImageView;
    private Button upDown;
    private Button leftRight;
    private Button reset;
    private Button rotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        mImagePath = intent.getStringExtra("imagePath");
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 1;
        mBitmap = BitmapFactory.decodeFile(mImagePath, option);
        newBitmap = mBitmap;

        imgBack = (ImageView) findViewById(R.id.rotate_img_back);
        imgBack.setOnClickListener(this);
        btnSave = (Button) findViewById(R.id.rotate_btn_save);
        btnSave.setOnClickListener(this);

        mImageView = (ImageView) findViewById(R.id.rotate_image);
        mImageView.setImageBitmap(mBitmap);

        rotate = (Button) findViewById(R.id.btn_rotate);
        rotate.setOnClickListener(this);

        reset = (Button) findViewById(R.id.btn_reset);
        reset.setOnClickListener(this);

        upDown = (Button) findViewById(R.id.btn_updown);
        upDown.setOnClickListener(this);

        leftRight = (Button) findViewById(R.id.btn_leftright);
        leftRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rotate_img_back:
                finish();
                break;
            case R.id.rotate_btn_save:
                String savePath = PhotoUtils.saveBitmap(newBitmap);
                Intent intent = new Intent();
                intent.setClass(RotateActivity.this, ShareActivity.class);
                intent.putExtra("savePath", savePath);
                startActivity(intent);
                break;
            case R.id.btn_rotate:
                newBitmap = rotateImage(newBitmap, 90);
                mImageView.setImageBitmap(newBitmap);
                break;
            case R.id.btn_leftright:
                newBitmap = reverseImage(newBitmap, -1, 1);
                mImageView.setImageBitmap(newBitmap);
                break;
            case R.id.btn_updown:
                newBitmap = reverseImage(newBitmap, 1, -1);
                mImageView.setImageBitmap(newBitmap);
                break;
            case R.id.btn_reset:
                mImageView.setImageBitmap(mBitmap);
                break;
        }
    }

    private Bitmap rotateImage(Bitmap bit, int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        Bitmap tempBitmap = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(),
                bit.getHeight(), matrix, true);
        return tempBitmap;
    }

    private Bitmap reverseImage(Bitmap bit, int x, int y) {
        Matrix matrix = new Matrix();
        matrix.postScale(x, y);
        Bitmap tempBitmap = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(),
                bit.getHeight(), matrix, true);
        return tempBitmap;
    }
}

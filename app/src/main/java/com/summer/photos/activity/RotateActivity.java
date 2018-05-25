package com.summer.photos.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.summer.photos.R;
import com.summer.photos.rotate.RotateView;
import com.summer.photos.utils.PhotoUtils;

public class RotateActivity extends BaseActivity implements View.OnClickListener {

    private String mImagePath;
    private Bitmap mBitmap;
    private Bitmap newBitmap;

    private ImageView imgBack;
    private Button btnSave;
    private RotateView mImageView;
    private LinearLayout rotate;
    private LinearLayout upDown;
    private LinearLayout leftRight;
    private LinearLayout zoom;
    private int rota = 0;
    private static final String TAG = "RotateActivity";


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

        mImageView = (RotateView) findViewById(R.id.rotate_image);
        mImageView.setImageBitmap(mBitmap);

        rotate = (LinearLayout) findViewById(R.id.ll_rotate);
        rotate.setOnClickListener(this);

        upDown = (LinearLayout) findViewById(R.id.ll_updown);
        upDown.setOnClickListener(this);

        leftRight = (LinearLayout) findViewById(R.id.ll_leftright);
        leftRight.setOnClickListener(this);

        zoom = (LinearLayout) findViewById(R.id.ll_zoom);
        zoom.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rotate_img_back:
                finish();
                break;
            case R.id.rotate_btn_save:
                newBitmap = rotateImage(newBitmap, rota);
                String savePath = PhotoUtils.saveBitmap(newBitmap);
                Intent intent = new Intent();
                intent.setClass(RotateActivity.this, ShareActivity.class);
                intent.putExtra("savePath", savePath);
                startActivity(intent);
                break;
            case R.id.ll_rotate:
                rota += 90;
                mImageView.setPivotX(mImageView.getWidth()/2);
                mImageView.setPivotY(mImageView.getHeight()/2);//支点在图片中心
                mImageView.setRotation(rota);
                rota = rota % 360;
                Log.d(TAG, "onClick: " + rota);
                /*newBitmap = rotateImage(newBitmap, 90);
                mImageView.setImageBitmap(newBitmap);*/
                break;
            case R.id.ll_leftright:
                newBitmap = reverseImage(newBitmap, -1, 1);
                mImageView.setImageBitmap(newBitmap);
                break;
            case R.id.ll_updown:
                newBitmap = reverseImage(newBitmap, 1, -1);
                mImageView.setImageBitmap(newBitmap);
                break;
            case R.id.ll_zoom:
                /*newBitmap = zoomImage(newBitmap, 0.8f, 0.8f);
                mImageView.setImageBitmap(newBitmap);*/
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

    private Bitmap zoomImage(Bitmap bit, float x, float y) {
        Matrix matrix = new Matrix();
        matrix.postScale(x, y, bit.getWidth() / 2, bit.getHeight() / 2);
        Bitmap tempBitmap = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(),
                bit.getHeight(), matrix, true);
        return tempBitmap;
    }

    private Bitmap moveImage(Bitmap bit, int x, int y) {
        Matrix matrix = new Matrix();
        matrix.setTranslate(x, y);
        Bitmap tempBitmap = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(),
                bit.getHeight(), matrix, true);
        return tempBitmap;
    }
}

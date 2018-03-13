package com.summer.photos.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.summer.photos.R;
import com.summer.photos.filters.FilterType;
import com.summer.photos.filters.Filters;
import com.summer.photos.utils.PhotoUtils;


public class FilterActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG = "FilterActivity";

    private ImageView imgBack;
    private Button btnSave;

    private ImageView mImageView;
    private String mImagePath = null;
    private Bitmap mBitmap = null;
    private FilterType filterType = new FilterType();

    private LinearLayout filterBefore;
    private LinearLayout filterGray;
    private LinearLayout filterNostalgic;
    private LinearLayout filterNegative;
    private LinearLayout filterSaturation;
    private LinearLayout filterRelief;
    private LinearLayout filterSketch;


    private Bitmap newBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        initView();
    }

    private void initView() {
        Intent filterIntent = getIntent();
        mImagePath = filterIntent.getStringExtra("imagePath");
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 1;
        mBitmap = BitmapFactory.decodeFile(mImagePath, option);

        imgBack = (ImageView) findViewById(R.id.filter_img_back);
        imgBack.setOnClickListener(this);
        btnSave = (Button) findViewById(R.id.filter_btn_save);
        btnSave.setOnClickListener(this);

        mImageView = (ImageView) findViewById(R.id.pictureShow);
        mImageView.setImageBitmap(mBitmap);

        newBitmap = mBitmap;
        initFiltersView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter_img_back:
                finish();
                break;
            case R.id.filter_btn_save:
                String savePath = PhotoUtils.saveBitmap(newBitmap);
                Intent intent = new Intent();
                intent.setClass(FilterActivity.this, ShareActivity.class);
                intent.putExtra("savePath", savePath);
                startActivity(intent);
                break;
            case R.id.filter_before:
                mImageView.setImageBitmap(mBitmap);
                break;
            case R.id.filter_gray:
                newBitmap = Filters.doFilter(mBitmap, filterType.getFilterFloat(FilterType.FILTER_GRAY));
                mImageView.setImageBitmap(newBitmap);
                break;
            case R.id.filter_nostalgic:
                newBitmap = Filters.doFilter(mBitmap, filterType.getFilterFloat(FilterType.FILTER_NOSTALGIC));
                mImageView.setImageBitmap(newBitmap);
                break;
            case R.id.filter_negative:
                newBitmap = Filters.doFilter(mBitmap, filterType.getFilterFloat(FilterType.FILTER_NEGATIVE));
                mImageView.setImageBitmap(newBitmap);
                break;
            case R.id.filter_saturation:
                newBitmap = Filters.doFilter(mBitmap, filterType.getFilterFloat(FilterType.FILTER_SATURATION));
                mImageView.setImageBitmap(newBitmap);
                break;
            case R.id.filter_relief:
                newBitmap = Filters.relief(mBitmap);
                mImageView.setImageBitmap(newBitmap);
                break;
            case R.id.filter_sketch:
                newBitmap = Filters.testGaussBlur(mBitmap,10,10/3);
                mImageView.setImageBitmap(newBitmap);
                break;

            /*case R.id.btn_cancel :
                Intent cancelData = new Intent();
                setResult(RESULT_CANCELED, cancelData);
                recycle();
                this.finish();

                break;
            case R.id.btn_ok :

                FileUtils.writeImage(resultImg, picturePath, 100);
                Intent intent = new Intent();
                intent.putExtra("camera_path", picturePath);
                setResult(Activity.RESULT_OK, intent);
                recycle();
                this.finish();
                break;*/
            default:
                break;
        }
    }

    private void initFiltersView() {
        filterBefore = (LinearLayout) findViewById(R.id.filter_before);
        filterBefore.setOnClickListener(this);

        filterGray = (LinearLayout) findViewById(R.id.filter_gray);
        filterGray.setOnClickListener(this);

        filterNostalgic = (LinearLayout) findViewById(R.id.filter_nostalgic);
        filterNostalgic.setOnClickListener(this);

        filterNegative = (LinearLayout) findViewById(R.id.filter_negative);
        filterNegative.setOnClickListener(this);

        filterSaturation = (LinearLayout) findViewById(R.id.filter_saturation);
        filterSaturation.setOnClickListener(this);

        filterRelief = (LinearLayout) findViewById(R.id.filter_relief);
        filterRelief.setOnClickListener(this);

        filterSketch = (LinearLayout) findViewById(R.id.filter_sketch);
        filterSketch.setOnClickListener(this);

    }

    private void recycle() {
        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }

        if (newBitmap != null) {
            newBitmap.recycle();
            newBitmap = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recycle();
    }
}

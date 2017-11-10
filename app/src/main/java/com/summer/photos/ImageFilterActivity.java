package com.summer.photos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.summer.photos.filters.FilterType;
import com.summer.photos.filters.Filters;


/**
 * 滤镜
 *
 * @author summer
 */
public class ImageFilterActivity extends Activity implements View.OnClickListener {

    private final String TAG = "ImageFilterActivity";

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


    private Bitmap newBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_filter);
        initView();
    }

    private void initView() {
        Intent filterIntent = getIntent();
        mImagePath = filterIntent.getStringExtra("imagePath");
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 1;
        mBitmap = BitmapFactory.decodeFile(mImagePath, option);

        mImageView = (ImageView) findViewById(R.id.pictureShow);
        mImageView.setImageBitmap(mBitmap);

        newBitmap = mBitmap;
        initFiltersView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter_before:
                mImageView.setImageBitmap(mBitmap);
                break;
            case R.id.filter_gray:
                mImageView.setImageBitmap(Filters.doFilter(newBitmap,
                        filterType.getFilterFloat(FilterType.FILTER_GRAY)));
                break;
            case R.id.filter_nostalgic:
                mImageView.setImageBitmap(Filters.doFilter(newBitmap,
                        filterType.getFilterFloat(FilterType.FILTER_NOSTALGIC)));
                break;
            case R.id.filter_negative:
                mImageView.setImageBitmap(Filters.doFilter(newBitmap,
                        filterType.getFilterFloat(FilterType.FILTER_NEGATIVE)));
                break;
            case R.id.filter_saturation:
                mImageView.setImageBitmap(Filters.doFilter(newBitmap,
                        filterType.getFilterFloat(FilterType.FILTER_SATURATION)));
                break;
            case R.id.filter_relief:
                mImageView.setImageBitmap(Filters.doFilter(newBitmap,
                        filterType.getFilterFloat(FilterType.FILTER_RELIEF)));
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

}

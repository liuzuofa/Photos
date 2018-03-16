package com.summer.photos.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.summer.photos.R;
import com.summer.photos.draw.CircleButton;
import com.summer.photos.draw.DrawView;
import com.summer.photos.draw.DrawingView;
import com.summer.photos.utils.PhotoUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.summer.photos.R.id.graffiti_btn_save;

public class GraffitiActivity extends BaseActivity implements View.OnClickListener {


    private LinearLayout colorLayout;
    private LinearLayout sizeLayout;

    private ImageView imgBack;
    private Button btnSave;

    //private DrawView drawView;
    private DrawingView drawView;
    private CircleButton redCircle;
    private CircleButton yellowCircle;
    private CircleButton greenCircle;
    private CircleButton blueCircle;
    private CircleButton blackCircle;
    private CircleButton magentaCircle;
    private CircleButton grayCircle;

    private CircleButton moreSmallCircle;
    private CircleButton smallCircle;
    private CircleButton standardCircle;
    private CircleButton bigCircle;
    private CircleButton moreBigCircle;

    private CircleButton color;
    private CircleButton size;
    private ImageView revoke;

    private String mImagePath;
    private Bitmap mBitmap;
    private Bitmap newBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graffiti);
        initView();
    }

    private void initView() {
        Intent filterIntent = getIntent();
        mImagePath = filterIntent.getStringExtra("imagePath");
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 1;
        mBitmap = BitmapFactory.decodeFile(mImagePath, option);
        newBitmap = mBitmap;

        imgBack = (ImageView) findViewById(R.id.graffiti_img_back);
        imgBack.setOnClickListener(this);
        btnSave = (Button) findViewById(graffiti_btn_save);
        btnSave.setOnClickListener(this);

        colorLayout = (LinearLayout) findViewById(R.id.color_layout);
        sizeLayout = (LinearLayout) findViewById(R.id.size_layout);
        //drawView = (DrawView) findViewById(R.id.draw_view);
        drawView = (DrawingView) findViewById(R.id.draw_view);

        redCircle = (CircleButton) findViewById(R.id.red_circle);
        redCircle.setOnClickListener(this);
        redCircle.setColor(Color.RED);
        redCircle.setCircleSize(50);

        yellowCircle = (CircleButton) findViewById(R.id.yellow_circle);
        yellowCircle.setOnClickListener(this);
        yellowCircle.setColor(Color.YELLOW);

        greenCircle = (CircleButton) findViewById(R.id.green_circle);
        greenCircle.setOnClickListener(this);
        greenCircle.setColor(Color.GREEN);

        blueCircle = (CircleButton) findViewById(R.id.blue_circle);
        blueCircle.setOnClickListener(this);
        blueCircle.setColor(Color.BLUE);

        blackCircle = (CircleButton) findViewById(R.id.black_circle);
        blackCircle.setOnClickListener(this);
        blackCircle.setColor(Color.BLACK);

        magentaCircle = (CircleButton) findViewById(R.id.magenta_circle);
        magentaCircle.setOnClickListener(this);
        magentaCircle.setColor(Color.MAGENTA);

        grayCircle = (CircleButton) findViewById(R.id.gray_circle);
        grayCircle.setOnClickListener(this);
        grayCircle.setColor(Color.GRAY);

        moreSmallCircle = (CircleButton) findViewById(R.id.more_small);
        moreSmallCircle.setOnClickListener(this);

        smallCircle = (CircleButton) findViewById(R.id.small);
        smallCircle.setOnClickListener(this);

        standardCircle = (CircleButton) findViewById(R.id.standard);
        standardCircle.setOnClickListener(this);

        bigCircle = (CircleButton) findViewById(R.id.big);
        bigCircle.setOnClickListener(this);

        moreBigCircle = (CircleButton) findViewById(R.id.more_big);
        moreBigCircle.setOnClickListener(this);

        color = (CircleButton) findViewById(R.id.color);
        color.setColor(Color.RED);
        color.setOnClickListener(this);

        size = (CircleButton) findViewById(R.id.size);
        size.setOnClickListener(this);

        revoke = (ImageView) findViewById(R.id.revoke);
        revoke.setOnClickListener(this);

        //drawView.setBitmap(newBitmap);
        drawView.loadImage(newBitmap);
        //drawView.setBackground(new BitmapDrawable(newBitmap));
        drawView.initializePen();
        drawView.setPenSize(10);
        drawView.setPenColor(Color.RED);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.graffiti_img_back:
                finish();
                break;
            case R.id.graffiti_btn_save:
                //String savePath = PhotoUtils.saveBitmap(drawView.getBitmap());
                String savePath = PhotoUtils.saveBitmap(drawView.getImageBitmap());
                Intent intent = new Intent();
                intent.setClass(GraffitiActivity.this, ShareActivity.class);
                intent.putExtra("savePath", savePath);
                startActivity(intent);
                break;
            case R.id.red_circle:
                color.setColor(Color.RED);
                setDefaultCircleSize();
                redCircle.setCircleSize(50);
                drawView.setPenColor(Color.RED);
                break;
            case R.id.yellow_circle:
                color.setColor(Color.YELLOW);
                setDefaultCircleSize();
                yellowCircle.setCircleSize(50);
                drawView.setPenColor(Color.YELLOW);
                break;
            case R.id.green_circle:
                color.setColor(Color.GREEN);
                setDefaultCircleSize();
                greenCircle.setCircleSize(50);
                drawView.setPenColor(Color.GREEN);
                break;
            case R.id.blue_circle:
                color.setColor(Color.BLUE);
                setDefaultCircleSize();
                blueCircle.setCircleSize(50);
                drawView.setPenColor(Color.BLUE);
                break;
            case R.id.black_circle:
                color.setColor(Color.BLACK);
                setDefaultCircleSize();
                blackCircle.setCircleSize(50);
                drawView.setPenColor(Color.BLACK);
                break;
            case R.id.magenta_circle:
                color.setColor(Color.MAGENTA);
                setDefaultCircleSize();
                magentaCircle.setCircleSize(50);
                drawView.setPenColor(Color.MAGENTA);
                break;
            case R.id.gray_circle:
                color.setColor(Color.GRAY);
                setDefaultCircleSize();
                grayCircle.setCircleSize(50);
                drawView.setPenColor(Color.GRAY);
                break;
            case R.id.more_small:
                setDefaultCircleColor();
                moreSmallCircle.setColor(0xFF00FFFF);
                drawView.setPenSize(10);
                break;
            case R.id.small:
                setDefaultCircleColor();
                smallCircle.setColor(0xFF00FFFF);
                drawView.setPenSize(20);
                break;
            case R.id.standard:
                setDefaultCircleColor();
                standardCircle.setColor(0xFF00FFFF);
                drawView.setPenSize(30);
                break;
            case R.id.big:
                setDefaultCircleColor();
                bigCircle.setColor(0xFF00FFFF);
                drawView.setPenSize(40);
                break;
            case R.id.more_big:
                setDefaultCircleColor();
                moreBigCircle.setColor(0xFF00FFFF);
                drawView.setPenSize(50);
                break;
            case R.id.color:
                size.setColor(0xFFBFBFBF);
                color.setColor(0xFFFF0000);
                colorLayout.setVisibility(View.VISIBLE);
                sizeLayout.setVisibility(View.GONE);
                break;
            case R.id.size:
                color.setColor(0xFFBFBFBF);
                size.setColor(0xFF00FFFF);
                colorLayout.setVisibility(View.GONE);
                sizeLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.revoke:
                drawView.undo();
                break;
        }
    }

    private void setDefaultCircleSize() {
        redCircle.setCircleSize(40);
        yellowCircle.setCircleSize(40);
        greenCircle.setCircleSize(40);
        blueCircle.setCircleSize(40);
        blackCircle.setCircleSize(40);
        magentaCircle.setCircleSize(40);
        grayCircle.setCircleSize(40);
    }

    private void setDefaultCircleColor() {
        moreSmallCircle.setColor(0xFFBFBFBF);
        smallCircle.setColor(0xFFBFBFBF);
        standardCircle.setColor(0xFFBFBFBF);
        bigCircle.setColor(0xFFBFBFBF);
        moreBigCircle.setColor(0xFFBFBFBF);
    }
}

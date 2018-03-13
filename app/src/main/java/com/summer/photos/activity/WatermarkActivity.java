package com.summer.photos.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.summer.photos.R;
import com.summer.photos.watermark.WatermarkView;

public class WatermarkActivity extends AppCompatActivity implements View.OnClickListener {

    private String mImagePath;
    private Bitmap mBitmap;
    private WatermarkView watermarkView;
    private LinearLayout chunvzuo;
    private LinearLayout shenhuifu;
    private LinearLayout qiugouda;
    private LinearLayout guaishushu;
    private LinearLayout haoxingzuo;
    private LinearLayout wanhuaile;
    private LinearLayout xiangsi;
    private LinearLayout xingzuokong;
    private LinearLayout xinnian;
    private LinearLayout zaoan;
    private LinearLayout zuile;
    private LinearLayout jiuyaozuo;
    private LinearLayout zui;
    private int watermark[] = {R.drawable.watermark_chunvzuo, R.drawable.comment,
            R.drawable.gouda, R.drawable.guaishushu, R.drawable.haoxingzuop,
            R.drawable.wanhuaile, R.drawable.xiangsi, R.drawable.xingzuokong,
            R.drawable.xinnian, R.drawable.zaoan, R.drawable.zuile,
            R.drawable.zuo,R.drawable.zui};

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

        chunvzuo = (LinearLayout) findViewById(R.id.chunvzuo);
        shenhuifu = (LinearLayout) findViewById(R.id.shenhuifu);
        qiugouda = (LinearLayout) findViewById(R.id.qiugouda);
        guaishushu = (LinearLayout) findViewById(R.id.guaishushu);
        haoxingzuo = (LinearLayout) findViewById(R.id.haoxingzuo);
        wanhuaile = (LinearLayout) findViewById(R.id.wanhuaile);
        xiangsi = (LinearLayout) findViewById(R.id.xiangsi);
        xingzuokong = (LinearLayout) findViewById(R.id.xingzuokong);
        xinnian = (LinearLayout) findViewById(R.id.xinnian);
        zaoan = (LinearLayout) findViewById(R.id.zaoan);
        zuile = (LinearLayout) findViewById(R.id.zuile);
        jiuyaozuo = (LinearLayout) findViewById(R.id.jiuyaozuo);
        zui = (LinearLayout) findViewById(R.id.zui);
        chunvzuo.setOnClickListener(this);
        shenhuifu.setOnClickListener(this);
        qiugouda.setOnClickListener(this);
        guaishushu.setOnClickListener(this);
        haoxingzuo.setOnClickListener(this);
        wanhuaile.setOnClickListener(this);
        xiangsi.setOnClickListener(this);
        xingzuokong.setOnClickListener(this);
        xinnian.setOnClickListener(this);
        zaoan.setOnClickListener(this);
        zuile.setOnClickListener(this);
        jiuyaozuo.setOnClickListener(this);
        zui.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chunvzuo:
                addpic(watermark[0]);
                break;
            case R.id.shenhuifu:
                addpic(watermark[1]);
                break;
            case R.id.qiugouda:
                addpic(watermark[2]);
                break;
            case R.id.guaishushu:
                addpic(watermark[3]);
                break;
            case R.id.haoxingzuo:
                addpic(watermark[4]);
                break;
            case R.id.wanhuaile:
                addpic(watermark[5]);
                break;
            case R.id.xiangsi:
                addpic(watermark[6]);
                break;
            case R.id.xingzuokong:
                addpic(watermark[7]);
                break;
            case R.id.xinnian:
                addpic(watermark[8]);
                break;
            case R.id.zaoan:
                addpic(watermark[9]);
                break;
            case R.id.zuile:
                addpic(watermark[10]);
                break;
            case R.id.jiuyaozuo:
                addpic(watermark[11]);
                break;
            case R.id.zui:
                addpic(watermark[12]);
                break;
            /*case R.id.btn_ok:
                btnSave();
                break;
            case R.id.btn_cancel:
                finish();
                break;*/
            default:
                break;
        }
    }

    private void addpic(int position)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), position);
        watermarkView.setWatermarkBitmap(bitmap);
    }
}

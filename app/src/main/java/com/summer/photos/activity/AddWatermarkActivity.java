package com.summer.photos.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.summer.photos.R;
import com.summer.photos.utils.Constants;
import com.summer.photos.utils.PhotoUtils;
import com.summer.photos.watermark.ImageObject;
import com.summer.photos.watermark.OperateUtils;
import com.summer.photos.watermark.OperateView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 添加水印
 *
 * @author jarlen
 */
public class AddWatermarkActivity extends Activity implements View.OnClickListener {

    private LinearLayout content_layout;
    private OperateView operateView;
    private String camera_path;
    private String mPath = null;
    OperateUtils operateUtils;
    private Button btn_ok;
    private ImageView btn_cancel;
    private final Handler myHandler = new WorkHandle();
    private LinearLayout chunvzuo, shenhuifu, qiugouda, guaishushu, haoxingzuo,
            wanhuaile, xiangsi, xingzuokong, xinnian, zaoan, zuile, jiuyaozuo, zui;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addwatermark);

        Intent intent = getIntent();
        camera_path = intent.getStringExtra("imagePath");
        operateUtils = new OperateUtils(this);
        initView();
        // 延迟每次延迟10 毫秒 隔1秒执行一次
        timer.schedule(task, 10, 1000);
    }
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            myHandler.sendMessage(message);
        }
    };

    private void initView() {
        content_layout = (LinearLayout) findViewById(R.id.mainLayout);

        btn_ok = (Button) findViewById(R.id.watermark_btn_save);
        btn_cancel = (ImageView) findViewById(R.id.watermark_img_back);
        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

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

    private void fillContent() {
        Bitmap resizeBmp = BitmapFactory.decodeFile(camera_path);
        operateView = new OperateView(AddWatermarkActivity.this, resizeBmp);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                resizeBmp.getWidth(), resizeBmp.getHeight());
        operateView.setLayoutParams(layoutParams);
        content_layout.addView(operateView);
        operateView.setMultiAdd(true); // 设置此参数，可以添加多个图片
    }

    private void btnSave() {
        operateView.save();
        Bitmap bmp = getBitmapByView(operateView);
        if (bmp != null) {
            mPath = PhotoUtils.saveBitmap(bmp);
            Intent intent = new Intent();
            intent.setClass(AddWatermarkActivity.this, ShareActivity.class);
            intent.putExtra("savePath", mPath);
            startActivity(intent);
        }
    }

    // 将模板View的图片转化为Bitmap
    public Bitmap getBitmapByView(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    // 将生成的图片保存到内存中
    public String saveBitmap(Bitmap bitmap, String name) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File dir = new File(Constants.filePath);
            if (!dir.exists())
                dir.mkdir();
            File file = new File(Constants.filePath + name + ".jpg");
            FileOutputStream out;

            try {
                out = new FileOutputStream(file);
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                    out.flush();
                    out.close();
                }
                return file.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private void addpic(int position) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), position);
        // ImageObject imgObject = operateUtils.getImageObject(bmp);
        ImageObject imgObject = operateUtils.getImageObject(bmp, operateView,
                5, 150, 100);
        operateView.addItem(imgObject);
    }

    int watermark[] = {R.drawable.watermark_chunvzuo, R.drawable.comment,
            R.drawable.gouda, R.drawable.guaishushu, R.drawable.haoxingzuop,
            R.drawable.wanhuaile, R.drawable.xiangsi, R.drawable.xingzuokong,
            R.drawable.xinnian, R.drawable.zaoan, R.drawable.zuile,
            R.drawable.zuo, R.drawable.zui};

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.watermark_btn_save:
                btnSave();
                break;
            case R.id.watermark_img_back:
                finish();
                break;
            default:
                break;
        }

    }

    class WorkHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (content_layout.getWidth() != 0) {
                    Log.i("LinearLayoutW", content_layout.getWidth() + "");
                    Log.i("LinearLayoutH", content_layout.getHeight() + "");
                    // 取消定时器
                    timer.cancel();
                    fillContent();
                }
            }
        }
    }
}

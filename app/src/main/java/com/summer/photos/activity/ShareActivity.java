package com.summer.photos.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.summer.photos.MainActivity;
import com.summer.photos.R;
import com.summer.photos.collector.ActivityCollector;

import java.io.File;

public class ShareActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgBack;
    private Button btnComplete;
    private ImageView imageView;
    private ImageView shareWechatMoments;
    private String imagePath;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        initView();
        Intent intent = getIntent();
        imagePath = intent.getStringExtra("savePath");
        if (imagePath != null) {
            bitmap = BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bitmap);
            Toast.makeText(ShareActivity.this,"图片已经保存在"+imagePath,Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(ShareActivity.this,"图片保存失败",Toast.LENGTH_SHORT).show();
        }

    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.share_img_back);
        imgBack.setOnClickListener(this);
        btnComplete = (Button) findViewById(R.id.share_btn_complete);
        btnComplete.setOnClickListener(this);
        imageView = (ImageView) findViewById(R.id.share_img);
        shareWechatMoments = (ImageView) findViewById(R.id.share_wechatmoments);
        shareWechatMoments.setOnClickListener(this);
    }

    private void shareWeixinFriend(String imagePath) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("Kdescription", "");
        File file = new File(imagePath);
        Uri uri = null;
        if (Build.VERSION.SDK_INT > 23) {
            uri = FileProvider.getUriForFile(ShareActivity.this, "com.summer.photo.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        if (uri == null){
            return;
        }
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_wechatmoments:
                shareWeixinFriend(imagePath);
                break;
            case R.id.share_img_back:
                this.finish();
                break;
            case R.id.share_btn_complete:
                startActivity(new Intent(ShareActivity.this,MainActivity.class));
                ActivityCollector.finishAllActivity();
                break;
            default:
                break;
        }
    }
}

package com.summer.photos;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ShareActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button shareButton;
    private String imagePath;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Intent intent = getIntent();
        imagePath = intent.getStringExtra("savePath");
        bitmap = BitmapFactory.decodeFile(imagePath);

        imageView = (ImageView) findViewById(R.id.share_img);
        imageView.setImageBitmap(bitmap);

        shareButton = (Button) findViewById(R.id.share);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareWeixinFriend(imagePath);
            }
        });
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
}

package com.summer.photos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.summer.photos.activity.BaseActivity;
import com.summer.photos.activity.CropActivity;
import com.summer.photos.activity.EnhanceActivity;
import com.summer.photos.activity.FilterActivity;
import com.summer.photos.activity.FrameActivity;
import com.summer.photos.activity.GraffitiActivity;
import com.summer.photos.activity.RotateActivity;
import com.summer.photos.activity.ShareActivity;
import com.summer.photos.activity.WatermarkActivity;

import java.io.File;
import java.io.IOException;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG = "MainActivity";
    private final String[] mPermission = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    public static final int REQUEST_PERMISSION = 1;
    public static final int TAKE_PHOTO = 2;
    public static final int PICK_PHOTO = 3;
    public static final int IMAGE_GRAFFITI = 4;
    public static final int IMAGE_FILTER = 5;
    public static final int IMAGE_CUT = 6;
    public static final int IMAGE_ROTATE = 7;
    public static final int IMAGE_FRAME = 8;
    public static final int IMAGE_COMIC = 9;

    private Toolbar toolbar;
    private ImageView mImageView;
    private CardView mCamera;
    private CardView mAlbum;
    private CardView mGraffiti;
    private CardView mFilter;
    private CardView mCut;
    private CardView mRotate;
    private CardView mFrame;
    private CardView mComic;
    private Uri imgUri;
    private String mImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mImageView = (ImageView) findViewById(R.id.image_view);
        mCamera = (CardView) findViewById(R.id.camera);
        mCamera.setOnClickListener(this);
        mAlbum = (CardView) findViewById(R.id.album);
        mAlbum.setOnClickListener(this);
        mGraffiti = (CardView) findViewById(R.id.graffiti);
        mGraffiti.setOnClickListener(this);
        mFilter = (CardView) findViewById(R.id.filter);
        mFilter.setOnClickListener(this);
        mCut = (CardView) findViewById(R.id.cut);
        mCut.setOnClickListener(this);
        mRotate = (CardView) findViewById(R.id.rotate);
        mRotate.setOnClickListener(this);
        mFrame = (CardView) findViewById(R.id.frame);
        mFrame.setOnClickListener(this);
        mComic = (CardView) findViewById(R.id.comic);
        mComic.setOnClickListener(this);
    }


    private void grantPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(MainActivity.this, Manifest.
                permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(MainActivity.this, Manifest.
                permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, mPermission, REQUEST_PERMISSION);
        }
    }

    @Override
    public void onClick(View view) {
        grantPermission();
        if (view.getId() == R.id.camera) {
            Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
            if (imgUri == null) {
                createFile();
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
            startActivityForResult(cameraIntent, TAKE_PHOTO);
        }else {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            switch (view.getId()) {
                /*case R.id.camera:
                    Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                    if (imgUri == null) {
                        createFile();
                    }
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                    startActivityForResult(cameraIntent, TAKE_PHOTO);
                    break;*/
                case R.id.album:
                    startActivityForResult(intent, PICK_PHOTO);
                    break;
                case R.id.graffiti:
                    startActivityForResult(intent, IMAGE_GRAFFITI);
                    break;
                case R.id.filter:
                    startActivityForResult(intent, IMAGE_FILTER);
                    break;
                case R.id.cut:
                    startActivityForResult(intent, IMAGE_CUT);
                    break;
                case R.id.rotate:
                    startActivityForResult(intent, IMAGE_ROTATE);
                    break;
                case R.id.frame:
                    startActivityForResult(intent, IMAGE_FRAME);
                    break;
                case R.id.comic:
                    startActivityForResult(intent, IMAGE_COMIC);
                    break;
            }
        }
    }

    private void createFile() {
        File file = new File(getExternalCacheDir(), "img.jpg");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            mImagePath = file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT > 23) {
            imgUri = FileProvider.getUriForFile(MainActivity.this, "com.summer.photo.fileprovider", file);
        } else {
            imgUri = Uri.fromFile(file);
        }
    }

    public String getAlbumPhoto(Uri uri){
        String[] filePathColumns = {MediaStore.Images.Media.DATA};
        Cursor c = getContentResolver().query(uri, filePathColumns, null, null, null);
        if (c !=null) {
            c.moveToFirst();
        }
        int columnIndex = c.getColumnIndex(filePathColumns[0]);
        String photoPath = c.getString(columnIndex);
        c.close();
        return photoPath;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_PHOTO) {
                if (mImagePath != null) {
                    Log.e(TAG, "mImagePath = " + mImagePath);
                    Intent intent = new Intent(MainActivity.this, EnhanceActivity.class);
                    intent.putExtra("imagePath", mImagePath);
                    startActivity(intent);
                }
            } else {
                Intent intent = new Intent();
                Uri uri = data.getData();
                String imagePath = getAlbumPhoto(uri);
                //Log.e(TAG, "imagePath = " + imagePath);
                intent.putExtra("imagePath", imagePath);
                switch (requestCode) {
                    case PICK_PHOTO:
                        intent.setClass(MainActivity.this,EnhanceActivity.class);
                        break;
                    case IMAGE_GRAFFITI:
                        intent.setClass(MainActivity.this, GraffitiActivity.class);
                        break;
                    case IMAGE_FILTER:
                        intent.setClass(MainActivity.this, FilterActivity.class);
                        break;
                    case IMAGE_CUT:
                        intent.setClass(MainActivity.this, CropActivity.class);
                        break;
                    case IMAGE_ROTATE:
                        intent.setClass(MainActivity.this, RotateActivity.class);
                        break;
                    case IMAGE_FRAME:
                        intent.setClass(MainActivity.this, FrameActivity.class);
                        break;
                    case IMAGE_COMIC:
                        intent.setClass(MainActivity.this, WatermarkActivity.class);
                        break;
                }
                startActivity(intent);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults.length > 2
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(MainActivity.this, "没有权限程序将出现异常", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}

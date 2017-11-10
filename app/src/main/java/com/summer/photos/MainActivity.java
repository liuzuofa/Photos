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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "MainActivity";
    private final String[] mPermission = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    public static final int REQUEST_PERMISSION = 1;
    public static final int TAKE_PHOTO = 2;
    public static final int PICK_PHOTO = 3;

    private ImageView mImageView;
    private CardView mCamera;
    private CardView mAlbum;
    private CardView mEdit;
    private CardView mFilter;
    private Uri imgUri;
    private String mImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.image_view);
        mCamera = (CardView) findViewById(R.id.camera);
        mCamera.setOnClickListener(this);
        mAlbum = (CardView) findViewById(R.id.album);
        mAlbum.setOnClickListener(this);
        mEdit = (CardView) findViewById(R.id.edit);
        mEdit.setOnClickListener(this);
        mFilter = (CardView) findViewById(R.id.filter);
        mFilter.setOnClickListener(this);
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
        switch (view.getId()) {
            case R.id.camera:
                grantPermission();
                Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                if (imgUri == null) {
                    createFile();
                }
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                startActivityForResult(cameraIntent, TAKE_PHOTO);
                break;
            case R.id.album:
                grantPermission();
                Intent albumIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(albumIntent, PICK_PHOTO);
                break;
            case R.id.edit:

                break;
            case R.id.filter:

                break;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Intent enhanceIntent = null;
            switch (requestCode) {
                case TAKE_PHOTO:
                    if (mImagePath != null) {
                        Log.e(TAG, "mImagePath = " + mImagePath);
                        enhanceIntent = new Intent(MainActivity.this, EnhanceActivity.class);
                        enhanceIntent.putExtra("imagePath", mImagePath);
                        startActivity(enhanceIntent);
                    }
                    break;
                case PICK_PHOTO:
                    Uri pickUri = data.getData();
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(pickUri, filePathColumns, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    String photoPath = c.getString(columnIndex);
                    c.close();
                    Log.e(TAG,"photoPath = " + photoPath);
                    enhanceIntent = new Intent(MainActivity.this, ImageFilterActivity.class);
                    enhanceIntent.putExtra("imagePath", photoPath);
                    startActivity(enhanceIntent);
                    break;
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

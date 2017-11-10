package com.summer.photos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class TestActivity extends AppCompatActivity {

    private ImageView before;
    private ImageView after;
    private float[] array = {
            0.393f, 0.769f, 0.189f, 0, 0,
            0.349f, 0.686f, 0.168f, 0, 0,
            0.272f, 0.534f, 0.131f, 0, 0,
            0.000f, 0.000f, 0.000f, 1, 0};
    private float[] array1 = {
            2, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0};
    private float[] array2 = {
            1.438F, -0.122F, -0.016F, 0F, -0.03F,
            -0.062F, 1.378F, -0.016F, 0F, 0.05F,
            -0.062F, -0.122F, 1.483F, 0F, -0.02F,
            0F, 0F, 0F, 1F, 0F};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        before = (ImageView) findViewById(R.id.before);
        after = (ImageView) findViewById(R.id.after);

        Intent filterIntent = getIntent();
        String picturePath = filterIntent.getStringExtra("imagePath");
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 1;
        Bitmap bitmap = BitmapFactory.decodeFile(picturePath, option);

        before.setImageBitmap(bitmap);
        after.setImageBitmap(doFilter(bitmap, array2));
    }

    public Bitmap doFilter(Bitmap bitmap, float[] array) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap tempBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(tempBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        ColorMatrix colorMatrix = new ColorMatrix(array);
        colorMatrix.reset();
        colorMatrix.set(array);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return tempBitmap;
    }
}

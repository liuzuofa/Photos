package com.summer.photos.filters;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class Filters {

    public static Bitmap doFilter(Bitmap bitmap, float[] array) {
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

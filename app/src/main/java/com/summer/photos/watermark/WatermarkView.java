package com.summer.photos.watermark;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by zuofa.liu on 17-12-9.
 */
public class WatermarkView extends View {

    private static String TAG = "WatermarkView";

    private Bitmap mBackgroundBitmap;
    private Bitmap mDrawBitmap;
    private int mWidth;
    private int mHeight;

    public WatermarkView(Context context) {
        this(context, null);
    }

    public WatermarkView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WatermarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw: start");
        if (mBackgroundBitmap != null) {
            mDrawBitmap = scaleBitmap(mBackgroundBitmap, getWidth(), getHeight());
            canvas.drawBitmap(mDrawBitmap, (getWidth()-mDrawBitmap.getWidth())/2,
                    (getHeight()-mDrawBitmap.getHeight())/2, null);
        }
    }

    /**
     * 设置绘画bitmap
     */
    public void setBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            mBackgroundBitmap = bitmap;
        }
    }

    public Bitmap scaleBitmap(Bitmap bitmap, int width, int height) {
        float scale = 0f;
        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();
        scale = bitmapHeight > bitmapWidth
                ? height / (bitmapHeight * 1f)
                : width / (bitmapWidth * 1f);
        Bitmap resizeBmp;
        if (scale != 0) {
            int bitmapheight = bitmap.getHeight();
            int bitmapwidth = bitmap.getWidth();
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale); // 长和宽放大缩小的比例
            resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmapwidth,
                    bitmapheight, matrix, true);
        } else {
            resizeBmp = bitmap;
        }
        return resizeBmp;
    }

}

package com.summer.photos.watermark;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.summer.photos.draw.DrawView;
import com.summer.photos.utils.PhotoUtils;

/**
 * Created by zuofa.liu on 17-12-9.
 */
public class WatermarkView extends View {

    private static String TAG = "WatermarkView";

    private Bitmap mBackgroundBitmap;
    private Bitmap mWatermarkBitmap;
    private Bitmap mDrawBitmap;
    private boolean isDefaultPoint = true;
    private Point defaultPoint;
    private Point currPoint;
    private Point watermarkPoint;


    public WatermarkView(Context context) {
        this(context, null);
    }

    public WatermarkView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WatermarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        defaultPoint = new Point();
        currPoint = new Point();
        watermarkPoint = new Point();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw: start");
        if (mBackgroundBitmap != null) {
            mDrawBitmap = PhotoUtils.scaleBitmap(mBackgroundBitmap, getWidth(), getHeight());
            if (mDrawBitmap != null) {
                canvas.drawBitmap(mDrawBitmap, //绘制背景图
                        (getWidth() - mDrawBitmap.getWidth()) / 2,
                        (getHeight() - mDrawBitmap.getHeight()) / 2, null);
                Log.d(TAG, "getWidth: " + getWidth());
                Log.d(TAG, "getHeight: " + getHeight());
                Log.d(TAG, "mDrawBitmap: " + mDrawBitmap.getWidth());
                Log.d(TAG, "mDrawBitmap: " + mDrawBitmap.getHeight());
                if (mWatermarkBitmap != null) {//绘制水印
                    Log.d(TAG, "mWatermarkBitmap: " + mWatermarkBitmap.getWidth());
                    Log.d(TAG, "mWatermarkBitmap: " + mWatermarkBitmap.getHeight());
                    if (isDefaultPoint) {
                        defaultPoint.x = (getWidth() - mWatermarkBitmap.getWidth()) / 2;
                        defaultPoint.y = (getHeight() - mWatermarkBitmap.getHeight()) / 2;
                        watermarkPoint.x = defaultPoint.x;
                        watermarkPoint.y = defaultPoint.y;
                        isDefaultPoint = false;
                        canvas.drawBitmap(mWatermarkBitmap, defaultPoint.x, defaultPoint.y, null);
                    } else {
                        canvas.drawBitmap(mWatermarkBitmap, watermarkPoint.x, watermarkPoint.y, null);
                    }
                }
            }
        }
    }

    /**
     * 根据触控点重绘View
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() == 1) {
            handleSingleTouchManipulateEvent(event);
        } else {
            handleMultiTouchManipulateEvent(event);
        }
        invalidate();
        super.onTouchEvent(event);
        return true;
    }

    /**
     * 单点触控
     *
     * @param event
     */
    private void handleSingleTouchManipulateEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "handleSingleTouchManipulateEvent: start");
                currPoint.x = (int) event.getX();
                currPoint.y = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                watermarkPoint.x += (int) (moveX - currPoint.x);
                watermarkPoint.y += (int) (moveY - currPoint.y);
                currPoint.x = (int) moveX;
                currPoint.y = (int) moveY;
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "handleSingleTouchManipulateEvent: end");
                break;
        }
    }

    /**
     * 多点触控
     *
     * @param event
     */
    private void handleMultiTouchManipulateEvent(MotionEvent event) {

    }


    /**
     * 设置背景bitmap
     */
    public void setBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            mBackgroundBitmap = bitmap;
        }
    }

    /**
     * 设置水印bitmap
     */
    public void setWatermarkBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            mWatermarkBitmap = bitmap;
            isDefaultPoint = true;
            invalidate();
        } else {
            mWatermarkBitmap = null;
            invalidate();
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

    /**
     * 获取绘画bitmap
     *
     * @return bitmap
     */
    public Bitmap getBitmap() {
        if (mBackgroundBitmap != null) {
            Bitmap bitmap = Bitmap.createBitmap(mBackgroundBitmap.getWidth(),
                    mBackgroundBitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(mBackgroundBitmap, 0, 0, null);
            /*if (isDefaultPoint) {
                defaultPoint.x = (getWidth() - mWatermarkBitmap.getWidth()) / 2;
                defaultPoint.y = (getHeight() - mWatermarkBitmap.getHeight()) / 2;
                watermarkPoint.x = defaultPoint.x;
                watermarkPoint.y = defaultPoint.y;
                isDefaultPoint = false;
                canvas.drawBitmap(mWatermarkBitmap, defaultPoint.x, defaultPoint.y, null);
            } else {
                canvas.drawBitmap(mWatermarkBitmap, watermarkPoint.x, watermarkPoint.y, null);
            }*/

            canvas.save(Canvas.ALL_SAVE_FLAG);
            return bitmap;
        }
        return null;
    }

}

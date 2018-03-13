package com.summer.photos.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawView extends View {

    private Paint mPaint;
    private Path mPath;
    private int paintSzie = 10;
    private int paintColor = Color.RED;
    private float downX;
    private float downY;
    private float currentX;
    private float currentY;
    private List<DrawPath> mDrawPathList;
    private Bitmap mBackgroundBitmap;
    private Bitmap mDrawBitmap;

    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        initPaint();
        mDrawPathList = new ArrayList<>();
    }

    private void initPaint() {
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(paintSzie);
        mPaint.setColor(paintColor);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBackgroundBitmap != null) {
            //mDrawBitmap = PhotoUtils.scaleBitmap(mBackgroundBitmap, getWidth(), getHeight());
            //if (mDrawBitmap != null) {
                //canvas.drawBitmap(mDrawBitmap, 0, 0, null);
                if (mDrawPathList != null && !mDrawPathList.isEmpty()) {
                    for (DrawPath drawPath : mDrawPathList) {
                        //if (mPath != null && mBackgroundBitmap != null) {
                            canvas.drawPath(drawPath.path, drawPath.paint);
                        //}
                    }
                }
            //}
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                mPath = new Path();
                mPath.moveTo(downX, downY);
                DrawPath drawPath = new DrawPath();
                drawPath.paint = mPaint;
                drawPath.path = mPath;
                mDrawPathList.add(drawPath);
                invalidate();
                currentX = downX;
                currentY = downY;
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                mPath.quadTo(currentX, currentY, moveX, moveY);
                invalidate();
                currentX = moveX;
                currentY = moveY;
                break;
            case MotionEvent.ACTION_UP:
                mPaint = new Paint();
                initPaint();
                break;
        }
        return true;
    }

    /**
     * 撤销功能
     */
    public void revoke() {
        if (mDrawPathList != null && mDrawPathList.size() > 0) {
            mDrawPathList.remove(mDrawPathList.size() - 1);
            invalidate();
        }
    }

    /**
     * 设置画笔颜色
     */
    public void setPainColor(int color) {
        this.paintColor = color;
        initPaint();
    }

    /**
     * 设置画笔大小
     */
    public void setPaintSize(int size) {
        this.paintSzie = size;
        initPaint();
    }

    private class DrawPath {
        Path path;
        Paint paint;
    }

    /**
     * 设置绘画bitmap
     */
    public void setBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            mBackgroundBitmap = bitmap;
        }
    }

    /**
     * 获取绘画bitmap
     * @return bitmap
     */
    public Bitmap getBitmap() {
        if (mBackgroundBitmap != null) {
            Bitmap bitmap = Bitmap.createBitmap(mBackgroundBitmap.getWidth(),
                    mBackgroundBitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(mBackgroundBitmap, 0, 0, null);
            if (mDrawPathList != null && !mDrawPathList.isEmpty()) {
                for (DrawPath drawPath : mDrawPathList) {
                    if (mPath != null && mBackgroundBitmap != null) {
                        canvas.drawPath(drawPath.path, drawPath.paint);
                    }
                }
            }
            canvas.save(Canvas.ALL_SAVE_FLAG);
            return bitmap;
        }
        return null;
    }
}

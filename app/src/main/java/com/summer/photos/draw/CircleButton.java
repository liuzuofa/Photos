package com.summer.photos.draw;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;

import com.summer.photos.R;

/**
 * Created by zuofa.liu on 17-11-17.
 */
public class CircleButton extends Button {

    private Paint mPaint;
    private float mCircleSize = 40;
    private int mCircleColor;

    public CircleButton(Context context) {
        this(context,null);
    }

    public CircleButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleButton);
        mCircleSize = typedArray.getInt(R.styleable.CircleButton_circleSize,40);
        mCircleColor = typedArray.getColor(R.styleable.CircleButton_circleColor,Color.BLACK);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mCircleColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth()/2,getHeight()/2,mCircleSize,mPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setColor(int color){
        mPaint.setColor(color);
        invalidate();
    }

    public void setCircleSize(float size){
        this.mCircleSize = size;
        invalidate();
    }


}

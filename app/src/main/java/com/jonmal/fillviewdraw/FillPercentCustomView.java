package com.jonmal.fillviewdraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class FillPercentCustomView extends View {

    //=================================================
    //                  Fields
    //=================================================

    private Paint mPaint;
    private Rect mRect;
    private Handler mHandler;

    //
    private int mRight;
    private int mTop;

    //=================================================
    //                  Constructor
    //=================================================

    public FillPercentCustomView(Context context) {
        super(context);
        init();
    }

    public FillPercentCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FillPercentCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //=================================================
    //                  Constructor
    //=================================================

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mRect = new Rect(0, mTop, mRight, 100);
        // fill
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.MAGENTA);
        canvas.drawRect(mRect, mPaint);

        // border
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setColor(Color.BLACK);
//        canvas.drawRect(mRect, mPaint);
    }


    //=================================================
    //                  Private Methods
    //=================================================


    private void init() {

        mPaint = new Paint();

        mHandler = new Handler();

        mTop = 100;
        mRight = 100;

        animateIn();
    }

    private void animateIn() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                invalidate();

                mTop -= 1;

                if(mTop >= 50){

                    animateIn();
                }
            }
        }, 100);


    }
}


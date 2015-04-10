package com.jonmal.fillviewdraw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class FillPercentCustomView extends View {


    //=================================================
    //                  Constants
    //=================================================

    private static final int DEFAULT_PERCENT_TO_FILL = 50;
    private static final String DEFAULT_FILL_COLOR = "#CD5C5C";

    private static final int ON_DRAW_DELAY_IN_MILLIS = 50; // 0.05 sec

    //=================================================
    //                  Fields
    //=================================================

    private Paint mPaint;
    private Rect mRect;
    private Handler mHandler;

    //
    private int mRight;
    private int mTop;

    //
    private float mPercentToFill;
    private String mFillColor;
    private int mMaxHeight;
    private int mOnDrawIntervalPX;

    //=================================================
    //                  Constructor
    //=================================================

    public FillPercentCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FillPercentCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    //=================================================
    //                  Constructor
    //=================================================

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mRect = new Rect(0, mTop, mRight, getHeight());

        mOnDrawIntervalPX = getHeight() / 100;

        // fill
        mPaint.setStyle(Paint.Style.FILL);

        if (mFillColor != null) {

            mPaint.setColor(Color.parseColor(mFillColor));

        } else {

            mPaint.setColor(Color.parseColor(DEFAULT_FILL_COLOR));
        }

        canvas.drawRect(mRect, mPaint);

    }


    //=================================================
    //                  Private Methods
    //=================================================


    private void init(AttributeSet attrs) {

        mPaint = new Paint();

        mHandler = new Handler();

        //
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = null;

        try {

            typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FillPercent);

            mPercentToFill = typedArray.getInt(R.styleable.FillPercent_fillPercentValue, DEFAULT_PERCENT_TO_FILL);

            mFillColor = typedArray.getString(R.styleable.FillPercent_fillColor);

        } finally {

            if (typedArray != null) {
                typedArray.recycle();
            }
        }

        //
        animateIn();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mTop = getMeasuredHeight();
        mRight = getMeasuredWidth();

        mMaxHeight = Math.round((1 - (mPercentToFill / 100f)) * getMeasuredHeight());

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void animateIn() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                invalidate();

                mTop -= mOnDrawIntervalPX;

                if (mTop >= mMaxHeight) {

                    animateIn();
                }
            }
        }, ON_DRAW_DELAY_IN_MILLIS);


    }
}


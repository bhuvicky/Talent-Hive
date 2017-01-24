package com.bhuvanesh.talenthive.photography.view;


import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;

import com.bhuvanesh.talenthive.util.THLoggerUtil;

public class AutoFitAppBarLayout  extends AppBarLayout{

    private int mRatioWidth = 0;
    private int mRatioHeight = 0;

    public AutoFitAppBarLayout(Context context) {
        super(context);
    }

    public AutoFitAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAspectRatio(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        mRatioWidth = width;
        mRatioHeight = height;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (0 == mRatioWidth || 0 == mRatioHeight) {
            setMeasuredDimension(width, height);
        } else {
            if (width < height * mRatioWidth / mRatioHeight) {
                setMeasuredDimension(width, width * mRatioHeight / mRatioWidth);
                THLoggerUtil.debug("hh",(width * mRatioHeight / mRatioWidth )+"hei");
            } else {
                //setMeasuredDimension(height * mRatioWidth / mRatioHeight, height);
            }
        }

    }

}

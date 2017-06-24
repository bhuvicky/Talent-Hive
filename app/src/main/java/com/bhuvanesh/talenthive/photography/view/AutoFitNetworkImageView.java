package com.bhuvanesh.talenthive.photography.view;


import android.content.Context;
import android.net.Network;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;

public class AutoFitNetworkImageView extends NetworkImageView {
    private int mRatioWidth;
    private int mRatioHeight;

    public AutoFitNetworkImageView(Context context) {
        super(context);
    }

    public AutoFitNetworkImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoFitNetworkImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        int height = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
//        if (0 == mRatioWidth || 0 == mRatioHeight) {
//            setMeasuredDimension(width, height);
//        } else {
//            if (width < height * mRatioWidth / mRatioHeight) {
//                setMeasuredDimension(width, width * mRatioHeight / mRatioWidth);
//            } else {
//                setMeasuredDimension(height * mRatioWidth / mRatioHeight, height);
//            }
//        }
        setMeasuredDimension(width,height);
    }

}

package com.bhuvanesh.talenthive.photography.view;


import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.AttributeSet;

public class CustomCollapsingToolbar extends CollapsingToolbarLayout {
    AttributeSet attrs;
    int width,height;
    boolean gestureEnabled=false;
    public CustomCollapsingToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs=attrs;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height=heightMeasureSpec;
    }

}

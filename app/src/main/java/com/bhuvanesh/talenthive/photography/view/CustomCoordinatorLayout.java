package com.bhuvanesh.talenthive.photography.view;


import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

public class CustomCoordinatorLayout extends CoordinatorLayout{
    private boolean allowForScrool = false;

    public CustomCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomCoordinatorLayout(Context context) {
        super(context);
    }

    public CustomCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return allowForScrool && super.onStartNestedScroll(child, target, nestedScrollAxes);
    }

    public boolean isAllowForScrool() {
        return allowForScrool;
    }

    public void setAllowForScrool(boolean allowForScrool) {
        this.allowForScrool = allowForScrool;
    }
}

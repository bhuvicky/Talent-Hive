package com.bhuvanesh.talenthive.util;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import com.bhuvanesh.talenthive.R;

public class UIUtils {

    public static int getNmOfColumns(Activity context,float percentage){
        return getNumOfColumns(context,percentage,context.getResources().getDimension(R.dimen.dimen_width_of_gallery_item));
    }
    public static int getNumOfColumns(Activity context, float percentage, float widthOfItem)
    {
        DisplayMetrics metrics=new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float gapBetweenItem =UIUtils.convertDpToPixel(context,5);
        float containerWidth=(metrics.widthPixels*percentage)/100-(2*UIUtils.convertDpToPixel(context,context.getResources().getDimension(R.dimen.dimen_marginLeft)));
        return (int)((containerWidth*gapBetweenItem)/(UIUtils.convertDpToPixel(context,widthOfItem)+gapBetweenItem));
    }
    public static float convertDpToPixel(Context context,float dp)
    {
        DisplayMetrics metrics=context.getResources().getDisplayMetrics();
        float px=dp * (metrics.densityDpi/160f);
        return px;
    }
}

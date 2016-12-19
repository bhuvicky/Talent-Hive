package com.bhuvanesh.talenthive.util;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import com.bhuvanesh.talenthive.R;

public class UIUtils {

    public static int getNumOfColumns(Activity context,float percentage){
        return getNumOfColumns(context,percentage,context.getResources().getDimension(R.dimen.dimen_width_of_gallery_item));
    }
    public static int getNumOfColumns(Activity context, float percentage, float widthOfItem)
    {
        DisplayMetrics metrics=new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float gapBetweenItem =UIUtils.convertDpToPixel(context,context.getResources().getDimension(R.dimen.dimen_gap_between_item));
        float containerWidth=(metrics.widthPixels*percentage)/100;
        THLoggerUtil.debug("hh",containerWidth+""+(UIUtils.convertDpToPixel(context,widthOfItem)+gapBetweenItem)+""+gapBetweenItem);
        THLoggerUtil.debug("hh",(containerWidth*gapBetweenItem)/(UIUtils.convertDpToPixel(context,widthOfItem)+gapBetweenItem)+"");
        return (int)((containerWidth*gapBetweenItem)/(UIUtils.convertDpToPixel(context,widthOfItem)+gapBetweenItem));
    }
    public static int convertDpToPixel(Context context,float dp)
    {
        DisplayMetrics metrics=context.getResources().getDisplayMetrics();
        int px=(int)(dp * (metrics.densityDpi/160));
        return px;
    }
}

package com.bhuvanesh.talenthive.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;

import com.android.volley.toolbox.NetworkImageView;

public class CircularNetworkImageView extends NetworkImageView {
    Context mContext;

    public CircularNetworkImageView(Context context) {
        super(context);
        mContext = context;
    }

    public CircularNetworkImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext = context;
    }

    public CircularNetworkImageView(Context context, AttributeSet attrs,
                                    int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public void setLocalImageResource( int resId) {
        try {

            setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),
                    resId));
        } catch (Exception e) {
            Log.w("ImageView", "Unable to find resource: " + resId, e);
            // Don't try again.

        }

    }

    @SuppressLint("DrawAllocation")
    @Override
    public void setImageBitmap(Bitmap bm) {
        if(bm==null) return;
        setImageDrawable(new BitmapDrawable(mContext.getResources(),
                getCircularBitmap(bm)));
    }

    /**
     * Creates a circular bitmap and uses whichever dimension is smaller to determine the width
     * <br/>Also constrains the circle to the leftmost part of the image
     *
     * @param bitmap
     * @return bitmap
     */
    public Bitmap getCircularBitmap(Bitmap bitmap) {



        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int width = bitmap.getWidth();
        if(bitmap.getWidth()>bitmap.getHeight())
            width = bitmap.getHeight();
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, width);
        final RectF rectF = new RectF(rect);
        final float roundPx = 120 ;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);


        return output;
    }
}

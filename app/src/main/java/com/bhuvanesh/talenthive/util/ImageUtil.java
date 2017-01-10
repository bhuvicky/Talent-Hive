package com.bhuvanesh.talenthive.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;

public class ImageUtil {

    public static Bitmap decodeUri(Context c, Uri uri, final int requiredWidth, final int requiredHeight)
            throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (true) {
            if (width_tmp / 2 < requiredWidth && height_tmp / 2 < requiredHeight)
                break;
            if (width_tmp / 2 < requiredWidth)
                width_tmp /= 2;
            if (height_tmp / 2 < requiredHeight)
                height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
    }

}

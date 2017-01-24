package com.bhuvanesh.talenthive.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Jay Rambhia on 11/2/2015.
 */
public class BitmapUtils {

    public static Bitmap addPadding(Bitmap bmp, int color) {

        if (bmp == null) {
            return null;
        }

        int biggerParam = Math.max(bmp.getWidth(), bmp.getHeight());
        Bitmap bitmap = Bitmap.createBitmap(biggerParam, biggerParam, bmp.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(color);

        int top = bmp.getHeight() > bmp.getWidth() ? 0 : (bmp.getWidth() - bmp.getHeight())/2;
        int left = bmp.getWidth() > bmp.getHeight() ? 0 : (bmp.getHeight() - bmp.getWidth())/2;

        canvas.drawBitmap(bmp, left, top, null);
        return bitmap;
    }
    public static void writeBitmapToFile(Bitmap bm, File file, int quality) throws IOException {

        FileOutputStream fos = new FileOutputStream(file);
        bm.compress(Bitmap.CompressFormat.JPEG, quality, fos);
        fos.flush();
        fos.close();
    }

}

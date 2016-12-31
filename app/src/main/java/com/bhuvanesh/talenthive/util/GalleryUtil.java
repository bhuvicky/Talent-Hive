package com.bhuvanesh.talenthive.util;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import java.io.File;

public class GalleryUtil {
    private static Cursor cursor;
    public static Bitmap getBitmapOfImage(Context context,String imageId)
    {
        cursor=context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,
                "_id="+imageId,null,null);
        cursor.moveToFirst();
        Bitmap myBitMap=null;
        String path="";
        path=cursor.getString(cursor.getColumnIndex("_data"));
        File imgFile=new File(path);
        if(imgFile.exists())
        {
             myBitMap= BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
return myBitMap;
    }
}

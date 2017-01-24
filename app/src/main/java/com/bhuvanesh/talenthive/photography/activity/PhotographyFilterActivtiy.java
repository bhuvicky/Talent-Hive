package com.bhuvanesh.talenthive.photography.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.bhuvanesh.talenthive.BaseActivity;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.photography.fragment.PhotoFilterFragment;

import java.io.File;

public class PhotographyFilterActivtiy extends BaseActivity{
    private static final String IMAGEID="imageId";
    private static final String IMGFILE ="imgFile" ;
    public Bitmap bitmap;
    private File file=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        setTitle("TalentHive");
        Intent intent=getIntent();

       String imageId=intent.getStringExtra(IMAGEID);
       String path=intent.getStringExtra(IMGFILE);
       bitmap=BitmapFactory.decodeFile(path);
       replace(R.id.filter_container, PhotoFilterFragment.newInstance(bitmap,imageId));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}

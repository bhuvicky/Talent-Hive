package com.bhuvanesh.talenthive.dashboard.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.widget.Toolbar;

import com.bhuvanesh.talenthive.BaseActivity;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.THApplication;
import com.bhuvanesh.talenthive.constant.IntentConstant;
import com.bhuvanesh.talenthive.dashboard.fragment.DashboardFragment;
import com.bhuvanesh.talenthive.photography.model.Photo;
import com.bhuvanesh.talenthive.photography.model.UploadPhotoRequest;
import com.bhuvanesh.talenthive.account.model.UserDetails;
import com.bhuvanesh.talenthive.util.THPreference;
import com.google.gson.Gson;

public class DashboardActivity extends BaseActivity {
    private FloatingActionButton floatingActionButton;
    public static String imageId="0";
    private Uri uri;
    private Photo photo;
    public UserDetails userDetails;
    public UploadPhotoRequest uploadPhotoRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        Intent intent=getIntent();
        uri=intent.getParcelableExtra(IntentConstant.URI);
        if(uri!=null){

            photo=new Photo();
            photo.location=intent.getStringExtra(IntentConstant.LOCATION);
            photo.photoURL=uri.toString();
            photo.photoUri=uri;
            photo.titleDescription=intent.getStringExtra(IntentConstant.TITLE);
            uploadPhotoRequest=new UploadPhotoRequest();
            uploadPhotoRequest.photo=photo;
            uploadPhotoRequest.createdTime=intent.getLongExtra(IntentConstant.TIME,0);
            uploadPhotoRequest.lastModifiedTime=uploadPhotoRequest.createdTime;
            uploadPhotoRequest.user= THApplication.getInstance().getUserDetails();

        }

        replace(R.id.flayout_container, DashboardFragment.newInstance());
    }

}

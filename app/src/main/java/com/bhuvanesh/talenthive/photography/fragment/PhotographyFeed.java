package com.bhuvanesh.talenthive.photography.fragment;

import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.firebase.FirebaseStorageAcess;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

public class PhotographyFeed extends BaseFragment {
    private Uri photoUri;
    private int id;
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;

    public static PhotographyFeed newInstance(Uri uri) {
        PhotographyFeed photographyFeed = new PhotographyFeed();
        photographyFeed.photoUri = uri;
        return photographyFeed;
    }
    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_scrolling,container,false);
    }*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseStorageAcess firebaseStorageAcess = new FirebaseStorageAcess(getContext());
        UploadTask uploadTask = firebaseStorageAcess.uploadPhoto(photoUri, System.currentTimeMillis() + "");

        mNotifyManager =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(getActivity());
        mBuilder.setContentTitle("Photo Upload")
                .setContentText("Upload in progress")
                .setSmallIcon(R.mipmap.ic_launcher);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = 100.0 * (taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                int currentprogress = (int) progress;
                mBuilder.setProgress(100, currentprogress, false);
                mNotifyManager.notify(id, mBuilder.build());
            }
        });
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mBuilder.setContentText("Download complete")
                        .setProgress(0, 0, false);
                mNotifyManager.notify(id, mBuilder.build());
            }
        });
        uploadTask.addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
    }
}

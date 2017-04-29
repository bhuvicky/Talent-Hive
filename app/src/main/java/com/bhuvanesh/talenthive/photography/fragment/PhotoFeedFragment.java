package com.bhuvanesh.talenthive.photography.fragment;


import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.database.THDBManager;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.firebase.FirebaseStorageAcess;
import com.bhuvanesh.talenthive.photography.adapter.PhotoFeedAdapter;
import com.bhuvanesh.talenthive.photography.manager.PhotoManager;
import com.bhuvanesh.talenthive.photography.model.Photo;
import com.bhuvanesh.talenthive.photography.model.PhotoFeedResponse;
import com.bhuvanesh.talenthive.photography.model.UploadPhotoRequest;
import com.bhuvanesh.talenthive.util.THPreference;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.util.LinkedList;
import java.util.List;

public class PhotoFeedFragment extends BaseFragment implements PhotoManager.OnGetPhotoFeedListener {
    private RecyclerView.OnScrollListener mOnScrollListener;
    private RecyclerView mRecyclerViewPhotoFeed;
    private SwipeRefreshLayout mRefreshPhotoFeed;
    private boolean isLoading;
    private boolean mIsScrollUp;
    private PhotoFeedAdapter mPhotoFeedAdapter;
    private List<PhotoFeedResponse> mPhotoFeedList=new LinkedList<>();
    private Uri photoUri;
    private int id;
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;
    private Photo photo;
    private UploadTask uploadTask;

    public static PhotoFeedFragment newInstance(Uri uri,Photo photo){
       PhotoFeedFragment photoFeed = new PhotoFeedFragment();
    photoFeed.photoUri = uri;
    photoFeed.photo=photo;
        return photoFeed;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(photoUri!=null)upload();
        View view=inflater.inflate(R.layout.fragment_photography_feed,container,false);
        mRecyclerViewPhotoFeed= (RecyclerView) view.findViewById(R.id.recyclerview_photo_feed);
        mRefreshPhotoFeed= (SwipeRefreshLayout) view.findViewById(R.id.refersh_photo_feed);
        mRefreshPhotoFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPhotoFeedList(true);
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewPhotoFeed.setLayoutManager(layoutManager);
        mRecyclerViewPhotoFeed.addOnScrollListener(mOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int firstVisibleItemPosition, totalVisibleItem, totalItemCount;
                    firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    totalVisibleItem = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();

                    if (firstVisibleItemPosition + totalVisibleItem == totalItemCount)
                        getPhotoFeedList(false);
                }
            }
        });
        mPhotoFeedAdapter = new PhotoFeedAdapter();
        mRecyclerViewPhotoFeed.setAdapter(mPhotoFeedAdapter);


        THDBManager manager = new THDBManager();

        manager.setmOnTHDBMangerListener(new THDBManager.OnTHDBMangerListener() {
            @Override
            public void onTHDBSuccessful(Object object) {
                mPhotoFeedList = (List< PhotoFeedResponse>) object;
                getPhotoFeedList(true);
            }

            @Override
            public void onTHDBError(THException error) {
                getPhotoFeedList(true);
            }
        });
        manager.getPhotoFeedList();
        mPhotoFeedAdapter.setOnStoryFeedItemClickListener(new PhotoFeedAdapter.IOnPhotoFeedItemClickListener() {
            @Override
            public void onPaginationRetryClick() {
                getPhotoFeedList(false);
            }
        });
        return view;
    }

    private void getPhotoFeedList(boolean isLookingForNewData) {
        if (isLoading)
            return;

        isLoading = true;
        mIsScrollUp = isLookingForNewData;
        long time = 0L;

        if (mPhotoFeedAdapter.getItemCount() > 0) {
            if (isLookingForNewData)
                time = mPhotoFeedList.get(0).photo.lastModifiedTime;
            else {
                time = mPhotoFeedList.get(mPhotoFeedList.size() - 1).photo.lastModifiedTime;
                mPhotoFeedAdapter.setFooterVisible(true, false);
            }
        }
        PhotoManager manager = new PhotoManager();
        manager.getPhotoFeedList(isLookingForNewData, time, this);

    }

    @Override
    public void onGetPhotoFeedSuccess(List<PhotoFeedResponse> list) {
        if (isAdded()) {
            afterNetworkResponse(false, false);
            if (mIsScrollUp)
                mPhotoFeedList.addAll( 0,list);
            else
                mPhotoFeedList.addAll(list);

            mPhotoFeedAdapter.setData(mPhotoFeedList);
        }
    }

    @Override
    public void onGetPhotoFeedError(THException exception) {
        if (isAdded()) {
            afterNetworkResponse(false, true);
        }
    }

    @Override
    public void onGetPhotoFeedEmpty() {
        if (isAdded()) {
            afterNetworkResponse(false, false);
            if (!mIsScrollUp)
//                no old data, available for pagination, so no need to look at the last item of list
                mRecyclerViewPhotoFeed.removeOnScrollListener(mOnScrollListener);
        }
    }
    private void afterNetworkResponse(boolean paginationStarts, boolean paginationFailed ) {
        isLoading = false;
        if (mIsScrollUp)
            mRefreshPhotoFeed.setRefreshing(false);
        else
            mPhotoFeedAdapter.setFooterVisible(paginationStarts, paginationFailed);
    }

    private void upload()
    {
        FirebaseStorageAcess firebaseStorageAcess = new FirebaseStorageAcess(getContext());
        uploadTask = firebaseStorageAcess.uploadPhoto(photoUri, System.currentTimeMillis() + "");

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

                UploadPhotoRequest uploadPhoto=new UploadPhotoRequest();
                uploadPhoto.photoURL = taskSnapshot.getDownloadUrl().toString();
                uploadPhoto.lastModifiedTime=taskSnapshot.getMetadata().getCreationTimeMillis();=
                uploadPhoto.titleDescription=photo.titleDescription;
                uploadPhoto.location=photo.location;
                if(THPreference.getInstance().isFBLoggedIn())
                {
                uploadPhoto.profileID=THPreference.getInstance().getProfileId();
                uploadPhoto.loginType=2;
                }
                else if(THPreference.getInstance().isGoolgeLoggedIn())
                {
                    uploadPhoto.profileID=THPreference.getInstance().getGoogleServerAuthCode();
                    uploadPhoto.loginType=1;
                }
                else {
                    uploadPhoto.profileID = THPreference.getInstance().getProfileId();
                    uploadPhoto.loginType=0;
                }
                PhotoManager photoManager=new PhotoManager();
                photoManager.uploadPostToServer(uploadPhoto, new PhotoManager.OnUploadPhotoListener() {
                    @Override
                    public void uploadPhotoSuccess(PhotoFeedResponse response) {
                        mBuilder.setContentText("Upload complete")
                                .setProgress(0, 0, false);
                        mNotifyManager.notify(id, mBuilder.build());
                         mPhotoFeedAdapter.addData(response);

                    }

                    @Override
                    public void uplaodPhotoFailure(THException exception) {

                    }
                });


            }
        });
        uploadTask.addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
    }

}

package com.bhuvanesh.talenthive.photography.manager;


import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.manager.WebServiceManager;
import com.bhuvanesh.talenthive.photography.manager.operation.GetPhotoFeedOperation;
import com.bhuvanesh.talenthive.photography.manager.operation.UploadPostToServerOperation;
import com.bhuvanesh.talenthive.photography.model.PhotoFeedResponse;
import com.bhuvanesh.talenthive.photography.model.UploadPhotoRequest;
import com.google.gson.Gson;

import java.util.List;

public class PhotoManager  extends WebServiceManager{

    private OnUploadPhotoListener uploadPhotoListener;
    public interface OnGetPhotoFeedListener{
        void onGetPhotoFeedSuccess(List<PhotoFeedResponse> list);
        void onGetPhotoFeedError(THException exception);
        void onGetPhotoFeedEmpty();
    }
    public interface OnUploadPhotoListener{
        void uploadPhotoSuccess(PhotoFeedResponse response);
        void uplaodPhotoFailure(THException exception);
    }

    public void uploadPostToServer(UploadPhotoRequest photo, OnUploadPhotoListener listener){
           uploadPhotoListener=listener;
           String json=new Gson().toJson(photo);
        final UploadPostToServerOperation uploadPostToServerOperation=new UploadPostToServerOperation(json,
                getHeaders(),
                new UploadPostToServerOperation.IUploadPhotographyListener() {
                    @Override
                    public void uploadPhotoSuccess(PhotoFeedResponse response) {
                        uploadPhotoListener.uploadPhotoSuccess(response);
                    }

                    @Override
                    public void uplaodPhotoFailure(THException exception) {
                        uploadPhotoListener.uplaodPhotoFailure(exception);
                    }
                }
                );
        uploadPostToServerOperation.addToRequestQueue();

    }

    public void getPhotoFeedList(final boolean isLookingForNewData, long time, final OnGetPhotoFeedListener listener)
    {
        GetPhotoFeedOperation getPhotoFeedOperation=new GetPhotoFeedOperation(isLookingForNewData,time,getHeaders()
        ,new GetPhotoFeedOperation.OnGetPhotoFeedOperation() {
            @Override
            public void onPhotoFeedSucess(List<PhotoFeedResponse> list) {
                if(list.isEmpty()){
                    listener.onGetPhotoFeedEmpty();
                }else
                {
                    listener.onGetPhotoFeedSuccess(list);
                }
            }

            @Override
            public void onPhotoFeedError(THException exception) {
                    listener.onGetPhotoFeedError(exception);
            }
        });
        getPhotoFeedOperation.addToRequestQueue();
    }


}

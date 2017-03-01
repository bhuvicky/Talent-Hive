package com.bhuvanesh.talenthive.photography.manager;


import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.manager.WebServiceManager;
import com.bhuvanesh.talenthive.photography.manager.operation.GetPhotoFeedOperation;
import com.bhuvanesh.talenthive.photography.model.PhotoFeedResponse;

import java.util.List;

public class PhotoManager  extends WebServiceManager{

    public interface OnGetPhotoFeedListener{
        void onGetPhotoFeedSuccess(List<PhotoFeedResponse> list);
        void onGetPhotoFeedError(THException exception);
        void onGetPhotoFeedEmpty();
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

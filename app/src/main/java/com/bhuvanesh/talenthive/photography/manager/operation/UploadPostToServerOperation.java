package com.bhuvanesh.talenthive.photography.manager.operation;

import com.android.volley.Request;
import com.bhuvanesh.talenthive.constant.URLConstant;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.manager.operation.WebServiceOperation;
import com.bhuvanesh.talenthive.photography.model.PhotoFeedResponse;

import java.util.Map;

/**
 * Created by Karthik on 14-Apr-17.
 */

public class UploadPostToServerOperation extends WebServiceOperation {
    public interface IUploadPhotographyListener{
        void uploadPhotoSuccess(PhotoFeedResponse response);
        void uplaodPhotoFailure(THException exception);
    }
    private IUploadPhotographyListener uploadPhotographyListener;
    public UploadPostToServerOperation(String body, Map<String, String> header, IUploadPhotographyListener listener) {
        super(URLConstant.UPLOAD_PHOTOGRAPHY, Request.Method.POST, header, body, PhotoFeedResponse.class, UploadPostToServerOperation.class.getSimpleName());
        uploadPhotographyListener=listener;

    }

    @Override
    public void addToRequestQueue() {
        super.addToRequestQueue();
    }

    @Override
    public void onError(THException exception) {
           uploadPhotographyListener.uplaodPhotoFailure(exception);
    }

    @Override
    public void onSuccess(Object response) {
        uploadPhotographyListener.uploadPhotoSuccess((PhotoFeedResponse)response);
    }
}

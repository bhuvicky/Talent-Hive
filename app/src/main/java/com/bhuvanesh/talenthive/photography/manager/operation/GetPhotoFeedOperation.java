package com.bhuvanesh.talenthive.photography.manager.operation;


import com.android.volley.Request;
import com.bhuvanesh.talenthive.Config;
import com.bhuvanesh.talenthive.constant.URLConstant;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.manager.operation.WebServiceOperation;
import com.bhuvanesh.talenthive.photography.model.PhotoFeedResponse;
import com.bhuvanesh.talenthive.storywriting.manager.operation.GetStoryFeedOperation;
import com.bhuvanesh.talenthive.storywriting.model.StoryFeedResponse;
import com.bhuvanesh.talenthive.util.THLoggerUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class GetPhotoFeedOperation extends WebServiceOperation {
    public interface OnGetPhotoFeedOperation{
        void onPhotoFeedSucess(List<PhotoFeedResponse> list);
        void onPhotoFeedError(THException exception);
    }
    private OnGetPhotoFeedOperation onGetPhotoFeedOperation;

    public GetPhotoFeedOperation(boolean isLookingForNewData, long time, Map<String, String> header, GetPhotoFeedOperation.OnGetPhotoFeedOperation listener) {
        super( URLConstant.GET_PHOTO_FEED+URLConstant.slash+time+URLConstant.slash+isLookingForNewData, Request.Method.GET, header, new TypeToken<List<PhotoFeedResponse>>() {}.getType(),
                GetStoryFeedOperation.class.getSimpleName());
        THLoggerUtil.println(time+"");
        onGetPhotoFeedOperation= listener;
    }

    @Override
    public void onError(THException exception) {
        onGetPhotoFeedOperation.onPhotoFeedError(exception);
    }
    public void addToRequestQueue() {
        if (Config.HARDCODED_ENABLE) {
            THLoggerUtil.debug("hh","ss00");
            onSuccess(getFromAssetsFolder("photofeedresponse.json", new TypeToken<List<PhotoFeedResponse>>() {}.getType()));
        } else {
            super.addToRequestQueue();
        }
    }
    @Override
    public void onSuccess(Object response) {
        onGetPhotoFeedOperation.onPhotoFeedSucess((List<PhotoFeedResponse>) response);
    }

}

package com.bhuvanesh.talenthive.storywriting.manager.operation;


import com.android.volley.Request;
import com.bhuvanesh.talenthive.Config;
import com.bhuvanesh.talenthive.constant.URLConstant;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.manager.operation.WebServiceOperation;
import com.bhuvanesh.talenthive.storywriting.model.StoryFeedResponse;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class GetStoryFeedOperation extends WebServiceOperation {

    public interface OnGetStoryFeedOperation {
        void OnGetStoryFeedSuccess(List<StoryFeedResponse> response);
        void OnGetStoryFeedError(THException exception);
    }

    private OnGetStoryFeedOperation iOnGetStoryFeedOperation;

    public GetStoryFeedOperation(boolean isLookingForNewData, long time, Map<String, String> header, OnGetStoryFeedOperation listener) {
        super(URLConstant.GET_STORY_FEED, Request.Method.GET, header, new TypeToken<List<StoryFeedResponse>>() {}.getType(),
                GetStoryFeedOperation.class.getSimpleName());
        iOnGetStoryFeedOperation = listener;
    }

    public void addToRequestQueue() {
        if (Config.HARDCODED_ENABLE) {
            onSuccess(getFromAssetsFolder("storyfeedresponse.json", new TypeToken<List<StoryFeedResponse>>() {}.getType()));
        } else {
            super.addToRequestQueue();
        }
    }

    @Override
    public void onSuccess(Object response) {
        iOnGetStoryFeedOperation.OnGetStoryFeedSuccess((List<StoryFeedResponse>) response);
    }

    @Override
    public void onError(THException exception) {
        iOnGetStoryFeedOperation.OnGetStoryFeedError(exception);
    }
}

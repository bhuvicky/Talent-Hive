package com.bhuvanesh.talenthive.storywriting.manager.operation;


import com.android.volley.Request;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.operation.WebServiceOperation;
import com.bhuvanesh.talenthive.storywriting.model.StoryFeedResponse;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class GetStoryFeedOperation extends WebServiceOperation {

    public interface OnGetStoryFeedOperation {
        void OnGetStoryFeedSuccess(List<StoryFeedResponse> response);
        void OnGetStoryFeedError(THException exception);
    }

    private OnGetStoryFeedOperation iOnGetStoryFeedOperation;

    protected GetStoryFeedOperation(Map<String, String> header, OnGetStoryFeedOperation listener) {
        super(null, Request.Method.GET, header, new TypeToken<List<StoryFeedResponse>>() {}.getType(),
                GetStoryFeedOperation.class.getSimpleName());
        iOnGetStoryFeedOperation = listener;
    }

    @Override
    public void onSuccess(Object response) {

    }

    @Override
    public void onError(THException exception) {

    }
}

package com.bhuvanesh.talenthive.storywriting.manager;


import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.manager.WebServiceManager;
import com.bhuvanesh.talenthive.storywriting.manager.operation.GetStoryFeedOperation;
import com.bhuvanesh.talenthive.storywriting.model.StoryFeedResponse;

import java.util.List;

public class StoryManager extends WebServiceManager {

    public interface OnGetStoryFeedListener {
        void OnGetStoryFeedSuccess(List<StoryFeedResponse> response);
        void OnGetStoryFeedError(THException exception);
        void OnGetStoryFeedEmpty();
    }

    public void getStoryFeedList(final OnGetStoryFeedListener listener) {
       GetStoryFeedOperation operation = new GetStoryFeedOperation(getHeaders(), new GetStoryFeedOperation.OnGetStoryFeedOperation() {
           @Override
           public void OnGetStoryFeedSuccess(List<StoryFeedResponse> response) {
               if (response.isEmpty()) {
                   listener.OnGetStoryFeedEmpty();
               } else {
                   listener.OnGetStoryFeedSuccess(response);
               }
           }

           @Override
           public void OnGetStoryFeedError(THException exception) {
                listener.OnGetStoryFeedError(exception);
           }
       });
        operation.addToRequestQueue();
    }


}

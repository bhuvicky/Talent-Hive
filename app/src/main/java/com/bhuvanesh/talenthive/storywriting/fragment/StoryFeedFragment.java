package com.bhuvanesh.talenthive.storywriting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.storywriting.manager.StoryManager;
import com.bhuvanesh.talenthive.storywriting.model.StoryFeedResponse;

import java.util.List;

public class StoryFeedFragment extends BaseFragment {

    public static StoryFeedFragment newInstance() {
        return new StoryFeedFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getStoryFeedList();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void getStoryFeedList() {
        StoryManager manager = new StoryManager();
        manager.getStoryFeedList(new StoryManager.OnGetStoryFeedListener() {
            @Override
            public void OnGetStoryFeedSuccess(List<StoryFeedResponse> response) {
                if (isAdded()) {

                }
            }

            @Override
            public void OnGetStoryFeedError(THException exception) {
                if (isAdded()) {

                }
            }

            @Override
            public void OnGetStoryFeedEmpty() {
                if (isAdded()) {

                }
            }
        });
    }
}

package com.bhuvanesh.talenthive.storywriting.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.storywriting.adapter.StoryFeedAdapter;
import com.bhuvanesh.talenthive.storywriting.manager.StoryManager;
import com.bhuvanesh.talenthive.storywriting.model.StoryFeedResponse;

import java.util.List;

public class StoryFeedFragment extends BaseFragment {

    private StoryFeedAdapter mStoryFeedAdapter;
    private List<StoryFeedResponse> mStoryFeedList;

    public static StoryFeedFragment newInstance() {
        return new StoryFeedFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story_feed, container, false);

        RecyclerView recyclerViewStoryFeed = (RecyclerView) view.findViewById(R.id.recyclerview_story_feed_list);
        assert recyclerViewStoryFeed != null;
        recyclerViewStoryFeed.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        if (mStoryFeedAdapter == null) {
            mStoryFeedAdapter = new StoryFeedAdapter();
        }
        recyclerViewStoryFeed.setAdapter(mStoryFeedAdapter);

        if (mStoryFeedList != null) {
            mStoryFeedAdapter.setData(mStoryFeedList);
        } else {
            getStoryFeedList();
        }
        return view;
    }

    private void getStoryFeedList() {
        StoryManager manager = new StoryManager();
        manager.getStoryFeedList(new StoryManager.OnGetStoryFeedListener() {
            @Override
            public void OnGetStoryFeedSuccess(List<StoryFeedResponse> response) {
                if (isAdded()) {
                    mStoryFeedList = response;
                    mStoryFeedAdapter.setData(response);
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

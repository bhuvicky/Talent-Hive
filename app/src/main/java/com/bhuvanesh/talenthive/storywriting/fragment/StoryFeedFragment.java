package com.bhuvanesh.talenthive.storywriting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.database.THDBManager;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.storywriting.adapter.StoryFeedAdapter;
import com.bhuvanesh.talenthive.storywriting.manager.StoryManager;
import com.bhuvanesh.talenthive.storywriting.model.StoryFeedResponse;

import java.util.LinkedList;
import java.util.List;

public class StoryFeedFragment extends BaseFragment implements StoryManager.OnGetStoryFeedListener {

    private StoryFeedAdapter mStoryFeedAdapter;
    private List<StoryFeedResponse> mStoryFeedList = new LinkedList<>();

    private RecyclerView.OnScrollListener mOnScrollListener;
    private RecyclerView mRecyclerViewStoryFeed;
    private SwipeRefreshLayout mRefreshStoryFeed;

    private Button buttonRetry;

    private boolean isLoading, mIsScrollUp;

    public static StoryFeedFragment newInstance() {
        return new StoryFeedFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story_feed, container, false);

        mRefreshStoryFeed = (SwipeRefreshLayout) view.findViewById(R.id.refresh_story_feed);
        mRefreshStoryFeed.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("refresh listener called");
                getStoryFeedList(true);
            }
        });

        mRecyclerViewStoryFeed = (RecyclerView) view.findViewById(R.id.recyclerview_story_feed_list);
        assert mRecyclerViewStoryFeed != null;
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewStoryFeed.setLayoutManager(layoutManager);

        if (mStoryFeedAdapter == null)
            mStoryFeedAdapter = new StoryFeedAdapter();
        mRecyclerViewStoryFeed.setAdapter(mStoryFeedAdapter);

        mRecyclerViewStoryFeed.addOnScrollListener(mOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int firstVisibleItemPosition, totalVisibleItem, totalItemCount;
                    firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    totalVisibleItem = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();

                    if (firstVisibleItemPosition + totalVisibleItem == totalItemCount)
                        getStoryFeedList(false);
                }
            }
        });

        mRefreshStoryFeed.post(new Runnable() {
            @Override
            public void run() {
                mRefreshStoryFeed.setRefreshing(true);
            }
        });

        THDBManager manager = new THDBManager();
        manager.setmOnTHDBMangerListener(new THDBManager.OnTHDBMangerListener() {
            @Override
            public void onTHDBSuccessful(Object object) {
                mStoryFeedList = (List<StoryFeedResponse>) object;
//                getStoryFeedList(true);
            }

            @Override
            public void onTHDBError(THException error) {
//                getStoryFeedList(true);
            }
        });
        manager.getStoryFeedList();

        mStoryFeedAdapter.setOnStoryFeedItemClickListener(new StoryFeedAdapter.OnStoryFeedItemClickListener() {
            @Override
            public void onRetryClick() {
                getStoryFeedList(false);
            }
        });
        return view;
    }

    private void getStoryFeedList(boolean isLookingForNewData) {
        if (isLoading)
            return;

        isLoading = true;
        mIsScrollUp = isLookingForNewData;
        long time = 0L;

        if (mStoryFeedAdapter.getItemCount() > 0) {
            if (isLookingForNewData)
                time = mStoryFeedList.get(0).story.lastModifiedDate;
            else {
                time = mStoryFeedList.get(mStoryFeedList.size() - 1).story.lastModifiedDate;
                mStoryFeedAdapter.setFooterVisible(true, false);
            }
        }
        StoryManager manager = new StoryManager();
        manager.getStoryFeedList(isLookingForNewData, time, this);

    }

    @Override
    public void OnGetStoryFeedSuccess(List<StoryFeedResponse> response) {
        if (isAdded()) {
            afterNetworkResponse(false, false);
            if (mIsScrollUp)
                mStoryFeedList.addAll(0, response);
            else
                mStoryFeedList.addAll(response);

            mStoryFeedAdapter.setData(mStoryFeedList);
        }
    }

    @Override
    public void OnGetStoryFeedError(THException exception) {
        if (isAdded()) {
            afterNetworkResponse(false, true);
        }
    }

    @Override
    public void OnGetStoryFeedEmpty() {
        if (isAdded()) {
            afterNetworkResponse(false, false);
            if (!mIsScrollUp)
//                no old data, available for pagination, so no need to look at the last item of list
                mRecyclerViewStoryFeed.removeOnScrollListener(mOnScrollListener);
        }

    }

    private void afterNetworkResponse(boolean paginationStarts, boolean paginationFailed ) {
        isLoading = false;
        mRefreshStoryFeed.setRefreshing(false);
        if (!mIsScrollUp)
            mStoryFeedAdapter.setFooterVisible(paginationStarts, paginationFailed);
    }
}

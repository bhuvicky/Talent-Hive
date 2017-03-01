package com.bhuvanesh.talenthive.photography.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.photography.adapter.PhotoFeedAdapter;
import com.bhuvanesh.talenthive.photography.manager.PhotoManager;
import com.bhuvanesh.talenthive.photography.model.PhotoFeedResponse;

import java.util.List;

public class PhotoFeedFragment extends BaseFragment implements PhotoManager.OnGetPhotoFeedListener {
    private RecyclerView.OnScrollListener mOnScrollListener;
    private RecyclerView mRecyclerViewPhotoFeed;
    private SwipeRefreshLayout mRefreshPhotoFeed;
    private boolean isLoading;
    private boolean mIsScrollUp;
    private PhotoFeedAdapter mPhotoFeedAdapter;
    private List<PhotoFeedResponse> mPhotoFeedList;

    public static PhotoFeedFragment newInstance()
    {
        return new PhotoFeedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_photography_feed,container,false);
        mRecyclerViewPhotoFeed= (RecyclerView) view.findViewById(R.id.recyclerview_photo_feed);
        mRefreshPhotoFeed= (SwipeRefreshLayout) view.findViewById(R.id.refersh_photo_feed);
        mRefreshPhotoFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPhotoFeedList(true);
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewPhotoFeed.setLayoutManager(layoutManager);
        mRecyclerViewPhotoFeed.addOnScrollListener(mOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int firstVisibleItemPosition, totalVisibleItem, totalItemCount;
                    firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    totalVisibleItem = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();

                    if (firstVisibleItemPosition + totalVisibleItem == totalItemCount)
                        getPhotoFeedList(false);
                }
            }
        });
        if (mPhotoFeedAdapter == null)
            mPhotoFeedAdapter = new PhotoFeedAdapter();
        mRecyclerViewPhotoFeed.setAdapter(mPhotoFeedAdapter);
        return view;
    }

    private void getPhotoFeedList(boolean isLookingForNewData) {
        if (isLoading)
            return;

        isLoading = true;
        mIsScrollUp = isLookingForNewData;
        long time = 0L;

        if (mPhotoFeedAdapter.getItemCount() > 0) {
            if (isLookingForNewData)
                time = mPhotoFeedList.get(0).photo.lastModifiedTime;
            else {
                time = mPhotoFeedList.get(mPhotoFeedList.size() - 1).photo.lastModifiedTime;
                mPhotoFeedAdapter.setFooterVisible(true, false);
            }
        }
        PhotoManager manager = new PhotoManager();
        manager.getPhotoFeedList(isLookingForNewData, time, this);

    }

    @Override
    public void onGetPhotoFeedSuccess(List<PhotoFeedResponse> list) {
        if (isAdded()) {
            afterNetworkResponse(false, false);
            if (mIsScrollUp)
                mPhotoFeedList.addAll(0, list);
            else
                mPhotoFeedList.addAll(list);

            mPhotoFeedAdapter.setData(mPhotoFeedList);
        }
    }

    @Override
    public void onGetPhotoFeedError(THException exception) {
        if (isAdded()) {
            afterNetworkResponse(false, true);
        }
    }

    @Override
    public void onGetPhotoFeedEmpty() {
        if (isAdded()) {
            afterNetworkResponse(false, false);
            if (!mIsScrollUp)
//                no old data, available for pagination, so no need to look at the last item of list
                mRecyclerViewPhotoFeed.removeOnScrollListener(mOnScrollListener);
        }
    }
    private void afterNetworkResponse(boolean paginationStarts, boolean paginationFailed ) {
        isLoading = false;
        if (mIsScrollUp)
            mRefreshPhotoFeed.setRefreshing(false);
        else
            mPhotoFeedAdapter.setFooterVisible(paginationStarts, paginationFailed);
    }

}

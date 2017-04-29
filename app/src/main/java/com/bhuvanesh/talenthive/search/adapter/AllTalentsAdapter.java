package com.bhuvanesh.talenthive.search.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.dance.model.Dance;
import com.bhuvanesh.talenthive.photography.model.PhotoFeedResponse;
import com.bhuvanesh.talenthive.search.model.AllTalents;
import com.bhuvanesh.talenthive.storywriting.model.StoryFeedResponse;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AllTalentsAdapter extends RecyclerView.Adapter {

    public interface OnItemClickListener {
        void onGridItemClick();
    }

    private List<PhotoFeedResponse> mPhotoFeedList = new ArrayList<>();
    private List<StoryFeedResponse> mStoryFeedList = new ArrayList<>();
    private List<Dance> mDanceList = new ArrayList<>();
    private List<Integer> mActualPosition = new LinkedList<>();

    private boolean mIsLayoutTypeList;
    private OnItemClickListener mOnItemClickListener;

    public void setLayoutType(boolean isLayoutTypeList) {
        mIsLayoutTypeList = isLayoutTypeList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setData(AllTalents allTalents) {
        if (allTalents.photoFeedList != null && allTalents.storyFeedList != null && allTalents.danceList != null) {
            mPhotoFeedList = allTalents.photoFeedList;
            mStoryFeedList = allTalents.storyFeedList;
            mDanceList = allTalents.danceList;
        }
        for (int i = 0; i < mPhotoFeedList.size() || i < mStoryFeedList.size() || i < mDanceList.size(); i++) {
            if (i < mPhotoFeedList.size())
                mActualPosition.add(i);
            if (i < mStoryFeedList.size())
                mActualPosition.add(i);
            if (i < mDanceList.size())
                mActualPosition.add(i);
        }
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mIsLayoutTypeList ? new ListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_talents_list, parent, false)) :
                new GridViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_talents_grid, parent, false));


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mActualPosition.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    private class GridViewHolder extends RecyclerView.ViewHolder {

        public GridViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class ListViewHolder extends RecyclerView.ViewHolder {

        public ListViewHolder(View itemView) {
            super(itemView);
        }
    }
}

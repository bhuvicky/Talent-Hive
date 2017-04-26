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

import java.util.List;

public class AllTalentsAdapter extends RecyclerView.Adapter {

    public interface OnItemClickListener {
        void onGridItemClick();
    }

    private List<PhotoFeedResponse> mPhotoFeedList;
    private List<StoryFeedResponse> mStoryFeedList;
    private List<Dance> mDanceList;

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
        return 0;
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

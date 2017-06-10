package com.bhuvanesh.talenthive.storywriting.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.THApplication;
import com.bhuvanesh.talenthive.storywriting.model.StoryFeedResponse;
import com.bhuvanesh.talenthive.util.DateUtil;
import com.bhuvanesh.talenthive.widget.CircularNetworkImageView;

import java.util.ArrayList;
import java.util.List;

public class StoryFeedAdapter extends RecyclerView.Adapter<StoryFeedAdapter.ViewHolder> {

    public interface OnStoryFeedItemClickListener {
        void onPaginationRetryClick();
    }

    private List<StoryFeedResponse> mStoryFeedList = new ArrayList<>();
    private boolean isPaginationStarts, isPaginationFailed;
    private OnStoryFeedItemClickListener mOnStoryFeedItemClickListener;
//    private ImageLoader mImageLoader = THApplication.getInstance();

    public void setOnStoryFeedItemClickListener(OnStoryFeedItemClickListener listener) {
        mOnStoryFeedItemClickListener = listener;
    }

    @Override
    public StoryFeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_feed, parent, false), mOnStoryFeedItemClickListener);
    }

    public void setData(List<StoryFeedResponse> storyList) {
        mStoryFeedList = storyList;
        notifyDataSetChanged();
    }

    public void setFooterVisible(boolean paginationStarts, boolean paginationFailed) {
        isPaginationStarts = paginationStarts;
        isPaginationFailed = paginationFailed;
//        notifyDataSetChanged();
        notifyItemChanged(getItemCount() - 1);
    }

    @Override
    public void onBindViewHolder(StoryFeedAdapter.ViewHolder holder, int position) {
        StoryFeedResponse item = mStoryFeedList.get(position);
        holder.textViewAuthor.setText(item.story.author);
        holder.textViewTimeStamp.setText(DateUtil.getTimeAgo(item.story.lastModifiedDate));
        holder.textViewStoryTitle.setText(item.story.title);
        holder.textViewStoryLang.setText(item.story.language.name);
        holder.textViewStoryCategory.setText(item.story.category.name);
        holder.textViewStoryDesc.setText(item.story.description);

        if (position == getItemCount() - 1) {
            if (!(isPaginationStarts || isPaginationFailed)) {
                holder.progressBar.setVisibility(View.GONE);
                holder.buttonRetry.setVisibility(View.GONE);
            } else {
                if (isPaginationStarts) {
                    holder.progressBar.setVisibility(View.VISIBLE);
                    holder.buttonRetry.setVisibility(View.GONE);
                }
                else {
                    holder.buttonRetry.setVisibility(View.VISIBLE);
                    holder.progressBar.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mStoryFeedList != null ? mStoryFeedList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewAuthor, textViewTimeStamp, textViewStoryTitle, textViewStoryLang,
                textViewStoryCategory, textViewStoryDesc;
        private CircularNetworkImageView imageViewProfileIcon;
        private NetworkImageView networkImageViewStoryCover;
        private ProgressBar progressBar;
        private Button buttonRetry;
        private OnStoryFeedItemClickListener mOnStoryFeedItemClickListener;

        ViewHolder(View itemView, OnStoryFeedItemClickListener listener) {
            super(itemView);
            mOnStoryFeedItemClickListener = listener;
            textViewAuthor = (TextView) itemView.findViewById(R.id.textview_author);
            textViewTimeStamp = (TextView) itemView.findViewById(R.id.textview_timestamp);
            textViewStoryTitle = (TextView) itemView.findViewById(R.id.textview_story_title);
            textViewStoryLang = (TextView) itemView.findViewById(R.id.textview_story_lang);
            textViewStoryCategory = (TextView) itemView.findViewById(R.id.textview_story_category);
            textViewStoryDesc = (TextView) itemView.findViewById(R.id.textview_story_desc);
            imageViewProfileIcon = (CircularNetworkImageView) itemView.findViewById(R.id.circular_network_imageview_profile_icon);
            networkImageViewStoryCover = (NetworkImageView) itemView.findViewById(R.id.network_imageview_story_cover_image);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
            buttonRetry = (Button) itemView.findViewById(R.id.retry);
            buttonRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnStoryFeedItemClickListener != null)
                        mOnStoryFeedItemClickListener.onPaginationRetryClick();
                }
            });
        }
    }

}

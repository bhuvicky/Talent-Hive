package com.bhuvanesh.talenthive.storywriting.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.storywriting.model.StoryFeedResponse;
import com.bhuvanesh.talenthive.widget.CircularNetworkImageView;

import java.util.ArrayList;
import java.util.List;

public class StoryFeedAdapter extends RecyclerView.Adapter<StoryFeedAdapter.ViewHolder> {

    private List<StoryFeedResponse> mStoryFeedList = new ArrayList<>();

    @Override
    public StoryFeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_story_feed, parent, false);
        return new ViewHolder(view);
    }

    public void setData(List<StoryFeedResponse> storyList) {
        mStoryFeedList.clear();
        mStoryFeedList.addAll(storyList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(StoryFeedAdapter.ViewHolder holder, int position) {
        StoryFeedResponse item = mStoryFeedList.get(position);
        holder.textViewAuthor.setText(item.story.author);
        holder.textViewTimeStamp.setText(String.valueOf(item.timeStamp));
        holder.textViewStoryTitle.setText(item.story.title);
        holder.textViewStoryLang.setText(item.story.language.name);
        holder.textViewStoryCategory.setText(item.story.category.name);
        holder.textViewStoryDesc.setText(item.story.description);
    }

    @Override
    public int getItemCount() {
        return mStoryFeedList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewAuthor, textViewTimeStamp, textViewStoryTitle, textViewStoryLang,
                textViewStoryCategory, textViewStoryDesc;
        private CircularNetworkImageView imageViewProfileIcon;
        private NetworkImageView networkImageViewStoryCover;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewAuthor = (TextView) itemView.findViewById(R.id.textview_author);
            textViewTimeStamp = (TextView) itemView.findViewById(R.id.textview_timestamp);
            textViewStoryTitle = (TextView) itemView.findViewById(R.id.textview_story_title);
            textViewStoryLang = (TextView) itemView.findViewById(R.id.textview_story_lang);
            textViewStoryCategory = (TextView) itemView.findViewById(R.id.textview_story_category);
            textViewStoryDesc = (TextView) itemView.findViewById(R.id.textview_story_desc);
            imageViewProfileIcon = (CircularNetworkImageView) itemView.findViewById(R.id.circular_network_imageview_profile_icon);
            networkImageViewStoryCover = (NetworkImageView) itemView.findViewById(R.id.network_imageview_story_cover_image);
        }
    }

}

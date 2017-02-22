package com.bhuvanesh.talenthive.dance.adapter;


import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.dance.model.Dance;

import java.util.ArrayList;
import java.util.List;

public class DanceFeedAdapter extends RecyclerView.Adapter<DanceFeedAdapter.ViewHolder> {

    private List<String> mDanceList = new ArrayList<>();

    public void setData(List<String> danceList) {
        mDanceList = danceList;
        notifyDataSetChanged();
    }

    @Override
    public DanceFeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dance_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(DanceFeedAdapter.ViewHolder holder, int position) {
        String item = mDanceList.get(position);
        holder.textViewTitle.setText("Title " + position);
        holder.videoViewDance.setVideoPath(item);
    }

    @Override
    public int getItemCount() {
        return mDanceList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        VideoView videoViewDance;

        ViewHolder(View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.textview_header);
            videoViewDance = (VideoView) itemView.findViewById(R.id.videoview);
            videoViewDance.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    mp.setLooping(true);
                }
            });
        }
    }
}

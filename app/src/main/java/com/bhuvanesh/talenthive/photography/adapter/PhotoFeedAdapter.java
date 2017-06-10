package com.bhuvanesh.talenthive.photography.adapter;


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
import com.bhuvanesh.talenthive.photography.model.PhotoFeedResponse;
import com.bhuvanesh.talenthive.util.DateUtil;
import com.bhuvanesh.talenthive.widget.CircularNetworkImageView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PhotoFeedAdapter extends RecyclerView.Adapter<PhotoFeedAdapter.ViewHolder>{

   public interface IOnPhotoFeedItemClickListener{
        void onPaginationRetryClick();
   }
   private List<PhotoFeedResponse>  mPhotoFeedList=new LinkedList<>();
   private boolean isPaginationStarts, isPaginationFailed;
   private IOnPhotoFeedItemClickListener onPhotoFeedItemClickListener;
    private ImageLoader imageLoader= THApplication.getInstance().getImageLoader();
    private String location;
    @Override
    public PhotoFeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_feed,parent,false));
    }


    @Override
    public void onBindViewHolder(PhotoFeedAdapter.ViewHolder holder, int position) {
        PhotoFeedResponse item=mPhotoFeedList.get(position);
        holder.dpCNImageView.setImageUrl(item.user.profilePicUrl,imageLoader);
        holder.profileNameTextView.setText(item.user.name);
        holder.postTimeTextView.setText(". "+DateUtil.getTimeAgo(item.lastModifiedTime));
        location=item.photo.location;
        if(location!=null){

//            holder.postLocationTextView.setVisibility(View.VISIBLE);
//            holder.postLocationTextView.setText(item.photo.location);
        }
        holder.titleDescriptionTextView.setText(item.photo.titleDescription);
        holder.photoGraphyNImageView.setImageUrl(item.photo.photoURL,imageLoader);
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
        holder.buttonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPhotoFeedItemClickListener==null)
                    onPhotoFeedItemClickListener.onPaginationRetryClick();
            }
        });
    }
    public void setOnPhotoFeedItemClickListener(IOnPhotoFeedItemClickListener listener) {
        onPhotoFeedItemClickListener = listener;
    }
    public void setData(List<PhotoFeedResponse> photoList) {
        mPhotoFeedList = photoList;
        notifyDataSetChanged();
    }
    public void setFooterVisible(boolean paginationStarts, boolean paginationFailed) {
        isPaginationStarts = paginationStarts;
        isPaginationFailed = paginationFailed;
        notifyItemChanged(getItemCount() - 1);
    }
    public void addData(PhotoFeedResponse response){
        mPhotoFeedList.add(response);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mPhotoFeedList!=null?mPhotoFeedList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircularNetworkImageView dpCNImageView;
        public TextView profileNameTextView,postTimeTextView,postLocationTextView,titleDescriptionTextView;
        public NetworkImageView photoGraphyNImageView;
        public ProgressBar progressBar;
        public Button buttonRetry;
        public ViewHolder(View itemView) {
            super(itemView);
            dpCNImageView= (CircularNetworkImageView) itemView.findViewById(R.id.cniv_profile_icon);
            profileNameTextView= (TextView) itemView.findViewById(R.id.textview_dp_name);
            postTimeTextView = (TextView) itemView.findViewById(R.id.textview_post_time);
            postLocationTextView= (TextView) itemView.findViewById(R.id.textview_location);
            titleDescriptionTextView= (TextView) itemView.findViewById(R.id.textview_title_description);
            photoGraphyNImageView= (NetworkImageView) itemView.findViewById(R.id.imageview_photography);

            progressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
            buttonRetry = (Button) itemView.findViewById(R.id.retry);

        }
    }
}

package com.bhuvanesh.talenthive.photography.adapter;


import android.content.Context;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.util.GPUImageFilterTools;

import jp.co.cyberagent.android.gpuimage.GPUImageView;

public class PhotoFilterAdapter extends RecyclerView.Adapter<PhotoFilterAdapter.ViewHolder> {
    private Context context;
    private String imageId;
    private GPUImageFilterTools.FilterList filterList;
    public PhotoFilterAdapter(Context context,String imageId,GPUImageFilterTools.FilterList filterList)
    {
        this.context=context;
        this.imageId=imageId;
        this.filterList=filterList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_photo_filter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.gpuImageView.setImage(MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(),Long.parseLong(imageId), MediaStore.Images.Thumbnails.MINI_KIND,null));
        holder.filterNameTextView.setText(filterList.names.get(position));
        holder.gpuImageView.setFilter(GPUImageFilterTools.createFilterForType(context,filterList.filters.get(position)));
        //gpuImage.requestRender();
    }

    @Override
    public int getItemCount() {
        return filterList.names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private GPUImageView gpuImageView;
        private TextView filterNameTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            gpuImageView= (GPUImageView) itemView.findViewById(R.id.glsurfaceView_thumbnail_image);
            filterNameTextView=(TextView)itemView.findViewById(R.id.textview_filter_name);
        }
    }



}

package com.bhuvanesh.talenthive.photography.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.util.GPUImageFilterTools;

import jp.co.cyberagent.android.gpuimage.GPUImage;

public class PhotoFilterAdapter extends RecyclerView.Adapter<PhotoFilterAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private String imageId;
    private GPUImageFilterTools.FilterList filterList;
    private Bitmap bitmap;
    private static final String ORIGINAL="Original";
    private int currentPostion=0;
    private int previousPosition=0;
    OnItemClickListener onItemClickListener=null;
    public interface OnItemClickListener{
        void onItemClickListener(int postion);
    }
    public PhotoFilterAdapter(Context context, Bitmap bitmap, GPUImageFilterTools.FilterList filterList, OnItemClickListener onItemClickListener)
    {
        this.context=context;
        this.imageId=imageId;
        this.filterList=filterList;
        //this.filterList.names.add(0,ORIGINAL);
        this.onItemClickListener=onItemClickListener;
        this.bitmap=bitmap;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_photo_filter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(currentPostion==position){
            holder.filterMarkerView.setVisibility(View.VISIBLE);
        }else{
            holder.filterMarkerView.setVisibility(View.GONE);
        }
        holder.filterNameTextView.setText(filterList.names.get(holder.getAdapterPosition()));
        if(position!=0) {
            GPUImage gpuImage = new GPUImage(context);
            gpuImage.setImage(bitmap);
            gpuImage.setFilter(GPUImageFilterTools.createFilterForType(context, filterList.filters.get(holder.getAdapterPosition()-1)));
            holder.gpuImageView.setImageBitmap(gpuImage.getBitmapWithFilterApplied());
        }else{
        holder.gpuImageView.setImageBitmap(bitmap);
        }

        //holder.gpuImageView.setFilter(GPUImageFilterTools.createFilterForType(context,filterList.filters.get(position)));
        holder.filterItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               currentPostion=position;
               notifyItemChanged(currentPostion);
                if(previousPosition!=currentPostion){
                   notifyItemChanged(previousPosition);
                   previousPosition=position;
                    onItemClickListener.onItemClickListener(position);
               }


            }
        });

    }

    @Override
    public int getItemCount() {
        return filterList.names.size();
    }

    @Override
    public void onClick(View view) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView gpuImageView;
        private TextView filterNameTextView;
        private View filterMarkerView;
        private LinearLayout filterItemView;
        public ViewHolder(View itemView) {
            super(itemView);
            gpuImageView= (ImageView) itemView.findViewById(R.id.glsurfaceView_thumbnail_image);
            filterNameTextView=(TextView)itemView.findViewById(R.id.textview_filter_name);
            filterMarkerView=itemView.findViewById(R.id.view_filter_marker);
            filterItemView= (LinearLayout) itemView.findViewById(R.id.thumbnails_container);
        }
    }



}

package com.bhuvanesh.talenthive.photography.adapter;


import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.util.THLoggerUtil;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private int columnIndex;
    private Cursor cursor;
    private Context context;
    private ItemClickListener listener;
    private int previousSelectedPosition=0;
    public PhotoAdapter(Context context, Cursor cursor,ItemClickListener listener)
    {
        this.cursor=cursor;
        this.context=context;
        this.listener=listener;
        columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        cursor.moveToPosition(position);
        int imageId=cursor.getInt(columnIndex);
        holder.galleryImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if(position==previousSelectedPosition)
        {
            holder.frameImageView.setVisibility(View.VISIBLE);
        }
        holder.frameImageView.setVisibility(View.GONE);
        holder.galleryImageView.setImageBitmap(MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(),imageId, MediaStore.Images.Thumbnails.MINI_KIND,null));
        holder.galleryImageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               cursor.moveToPosition(position);
               String imaged=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
               THLoggerUtil.debug("hh",imaged+"");
               listener.onItemClickListener(v,imaged,position,previousSelectedPosition);
               notifyItemChanged(previousSelectedPosition);
               previousSelectedPosition=position;
               holder.frameImageView.setVisibility(View.VISIBLE);

           }
       });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    private ImageView galleryImageView,frameImageView;
    public ViewHolder(View itemView) {
        super(itemView);
        galleryImageView= (ImageView) itemView.findViewById(R.id.imageview_gallery);
        frameImageView= (ImageView) itemView.findViewById(R.id.imageview_frame);
       }
    }
    public interface ItemClickListener{
       void onItemClickListener(View view,String imageId,int position,int previousPostion);
    }
}

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
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.imageView.setImageBitmap(MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(),imageId, MediaStore.Images.Thumbnails.MINI_KIND,null));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                 cursor.moveToPosition(position);
                 String imaged=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                 THLoggerUtil.debug("hh",imaged+"");
                 listener.onItemClickListener(imaged);
                // holder.imageView.setBackground(context.getResources().getDrawable(R.drawable.ic_backgd_red));
           }
       });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    public ViewHolder(View itemView) {
        super(itemView);
        imageView= (ImageView) itemView.findViewById(R.id.imageview_gallery);}
    }
    public interface ItemClickListener{
       void onItemClickListener(String imageId);

    }
}

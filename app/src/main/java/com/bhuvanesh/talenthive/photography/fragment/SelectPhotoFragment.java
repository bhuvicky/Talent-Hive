package com.bhuvanesh.talenthive.photography.fragment;


import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bhuvanesh.talenthive.BaseActivity;
import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.photography.adapter.PhotoAdapter;
import com.bhuvanesh.talenthive.util.UIUtils;

import java.io.File;

public class SelectPhotoFragment extends BaseFragment {
    private RecyclerView galleryRecyclerView;
    private Cursor cursor;
    private ImageView previewImageView;
    public static SelectPhotoFragment newInstance()
    {
        return new SelectPhotoFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery_view,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((BaseActivity)getActivity()).getSupportActionBar().hide();
        galleryRecyclerView= (RecyclerView) view.findViewById(R.id.recyclerview_gallery);
        cursor=getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                MediaStore.Images.Media.DATE_MODIFIED+" DESC"
        );

        PhotoAdapter photoAdapter=new PhotoAdapter(getContext(), cursor, new PhotoAdapter.ItemClickListener() {
            @Override
            public void onItemClickListener(String imageId) {
                String path="";
                cursor=getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,
                        "_id="+imageId,null,null);
                cursor.moveToFirst();
                path=cursor.getString(cursor.getColumnIndex("_data"));
                File imgFile=new File(path);
                if(imgFile.exists())
                {
                    Bitmap myBitMap= BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    previewImageView.setImageBitmap(myBitMap);
                    previewImageView.setAdjustViewBounds(true);
                }
            }
        });
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(), UIUtils.getNmOfColumns(getActivity(),70));
        previewImageView=(ImageView) view.findViewById(R.id.imageview_preview);
        galleryRecyclerView.setLayoutManager(gridLayoutManager);
        galleryRecyclerView.setAdapter(photoAdapter);
    }
   }

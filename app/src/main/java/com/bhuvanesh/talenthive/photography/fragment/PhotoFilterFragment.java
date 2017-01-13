package com.bhuvanesh.talenthive.photography.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.photography.adapter.PhotoFilterAdapter;
import com.bhuvanesh.talenthive.util.GPUImageFilterTools;
import com.bhuvanesh.talenthive.util.GalleryUtil;

import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

public class PhotoFilterFragment extends BaseFragment {
    private GPUImageView photoEditImageView;
    private String selectedImageId;
    private RecyclerView filterRecyclerView;
    public static PhotoFilterFragment newInstance(String selectedImageId)
    {
        PhotoFilterFragment photoFilterFragment=new PhotoFilterFragment();
        photoFilterFragment.selectedImageId=selectedImageId;
        return photoFilterFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_filter,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        filterRecyclerView= (RecyclerView) view.findViewById(R.id.recyclerview_filter_preview);
        PhotoFilterAdapter photoFilterAdapter=new PhotoFilterAdapter(getActivity(),selectedImageId, GPUImageFilterTools.getFilterList());
        filterRecyclerView.setAdapter(photoFilterAdapter);
        filterRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayout.HORIZONTAL,false));
        photoEditImageView=(GPUImageView) view.findViewById(R.id.imageView_edit);
        photoEditImageView.setImage(GalleryUtil.getBitmapOfImage(getActivity(),selectedImageId));
        photoEditImageView.setFilter(new GPUImageSepiaFilter());

    }

}

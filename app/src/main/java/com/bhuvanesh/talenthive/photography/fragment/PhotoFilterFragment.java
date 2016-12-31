package com.bhuvanesh.talenthive.photography.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.util.GalleryUtil;

public class PhotoFilterFragment extends BaseFragment {
    private ImageView photoEditImageView;
    private String selectedImageId;
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
        photoEditImageView=(ImageView)view.findViewById(R.id.imageView_edit);
        photoEditImageView.setImageBitmap(GalleryUtil.getBitmapOfImage(getActivity(),selectedImageId));
        photoEditImageView.setAdjustViewBounds(true);
    }

}

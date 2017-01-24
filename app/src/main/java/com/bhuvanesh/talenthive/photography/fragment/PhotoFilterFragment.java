package com.bhuvanesh.talenthive.photography.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
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

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

public class PhotoFilterFragment extends BaseFragment {
    private GPUImageView photoEditImageView;
    private String selectedImageId;
    private RecyclerView filterRecyclerView;
    private Bitmap imageBitmap;
    public static PhotoFilterFragment newInstance(Bitmap bitmap,String selectedImageId)
    {
        PhotoFilterFragment photoFilterFragment=new PhotoFilterFragment();
        photoFilterFragment.selectedImageId=selectedImageId;
        photoFilterFragment.imageBitmap=bitmap;
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
        filterRecyclerView.setDrawingCacheEnabled(true);
        filterRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL,false));
        photoEditImageView=(GPUImageView) view.findViewById(R.id.imageView_edit);
        setToOringinal();
        final GPUImageFilterTools.FilterList filterList=GPUImageFilterTools.getFilterList();

        PhotoFilterAdapter photoFilterAdapter=new PhotoFilterAdapter(getActivity(), getThumnailBitmap(selectedImageId), filterList, new PhotoFilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int postion) {
                if(postion!=0) {
                    photoEditImageView.setFilter(GPUImageFilterTools.createFilterForType(getContext(), filterList.filters.get(postion-1)));
                }else{
                    setToOringinal();
                }
            }
        });
        filterRecyclerView.setAdapter(photoFilterAdapter);
    }
    private void setToOringinal(){
        photoEditImageView.setFilter(new GPUImageFilter());
        photoEditImageView.setImage(imageBitmap);
    }
    public  Bitmap getThumnailBitmap(String imageId)
    {
        if(imageId!=null) {
            return MediaStore.Images.Thumbnails.getThumbnail(getContext().getContentResolver(), Long.parseLong(imageId), MediaStore.Images.Thumbnails.MINI_KIND, null);
        }
        else
        {
            return Bitmap.createScaledBitmap(imageBitmap,100,100,false);
        }

    }
}

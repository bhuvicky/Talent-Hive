package com.bhuvanesh.talenthive.photography.fragment;


import android.annotation.TargetApi;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.photography.adapter.PhotoAdapter;
import com.bhuvanesh.talenthive.util.GalleryUtil;
import com.bhuvanesh.talenthive.util.THLoggerUtil;
import com.bhuvanesh.talenthive.util.UIUtils;

public class GalleryFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener{
    private RecyclerView galleryRecyclerView;
    private Cursor cursor;
    private ImageView previewImageView;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private int noOfColumns;
    private int selectedPosition=0;
    private PhotoAdapter photoAdapter;
    public static GalleryFragment newInstance()
    {
        return new GalleryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery,container,false);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
     //   ((BaseActivity)getActivity()).getSupportActionBar().hide();
        galleryRecyclerView= (RecyclerView) view.findViewById(R.id.recyclerview_gallery);
        collapsingToolbarLayout= (CollapsingToolbarLayout) view.findViewById(R.id.collpsing_toolbar);
        appBarLayout= (AppBarLayout) view.findViewById(R.id.app_bar_collpse);
        cursor=getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                MediaStore.Images.Media.DATE_MODIFIED+" DESC"
        );
        noOfColumns=UIUtils.getNumOfColumns(getActivity(),100);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),noOfColumns );
        previewImageView=(ImageView) view.findViewById(R.id.imageview_preview);

        galleryRecyclerView.setLayoutManager(gridLayoutManager);


        photoAdapter=new PhotoAdapter(getContext(), cursor, new PhotoAdapter.ItemClickListener() {
            @Override
            public void onItemClickListener(View v,String imageId,int position,int prevposition) {
                 setGallerySelection(imageId);
                selectedPosition=position;
               appBarLayout.setExpanded(true);
               if(position>(2*noOfColumns-1)){
                    if(((prevposition+noOfColumns)/noOfColumns)!=((position+noOfColumns)/noOfColumns))
                    galleryRecyclerView.smoothScrollBy(0,UIUtils.convertDpToPixel(getContext(),getContext().getResources().getDimension(R.dimen.dimen_height_of_gallery_item))/2);

                }else if((2*noOfColumns-1)==position)
                {
                    galleryRecyclerView.smoothScrollBy(0,UIUtils.convertDpToPixel(getContext(),getContext().getResources().getDimension(R.dimen.dimen_height_of_gallery_item))/2);
                }
                else{

                }
            }
        });
        galleryRecyclerView.setAdapter(photoAdapter);
        galleryRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        galleryRecyclerView.setNestedScrollingEnabled(false);

            }
        });

        cursor.moveToFirst();
        setGallerySelection(cursor.getString(0));
    }
    public void setGallerySelection(String imageId){
            Bitmap myBitMap= GalleryUtil.getBitmapOfImage(getActivity(),imageId);
            previewImageView.setImageBitmap(myBitMap);
            previewImageView.setAdjustViewBounds(true);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.select_photo_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        THLoggerUtil.debug("hh","selected");
        if(item.getItemId()==R.id.menu_next){

            THLoggerUtil.debug("hh","selected");
            return true;
            //replace(R.id.layout_container,PhotoFilterFragment.newInstance(photoAdapter.getItemAtPosition(selectedPosition)));
        }
        return super.onOptionsItemSelected(item);
    }


}

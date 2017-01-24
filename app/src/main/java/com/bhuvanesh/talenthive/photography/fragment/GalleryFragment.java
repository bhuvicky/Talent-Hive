package com.bhuvanesh.talenthive.photography.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.photography.activity.PhotographyFilterActivtiy;
import com.bhuvanesh.talenthive.photography.adapter.PhotoAdapter;
import com.bhuvanesh.talenthive.photography.view.AutoFitAppBarLayout;
import com.bhuvanesh.talenthive.photography.view.CropperView;
import com.bhuvanesh.talenthive.photography.view.CustomCoordinatorLayout;
import com.bhuvanesh.talenthive.util.BitmapUtils;
import com.bhuvanesh.talenthive.util.GalleryUtil;
import com.bhuvanesh.talenthive.util.THLoggerUtil;
import com.bhuvanesh.talenthive.util.UIUtils;

import java.io.File;
import java.io.IOException;

import static com.bhuvanesh.talenthive.dashboard.activity.DashboardActivity.imageId;

public class GalleryFragment extends BaseFragment {
    private static final String IMAGEID ="imageId" ;
    private static final String IMGFILE ="imgFile" ;
    private RecyclerView galleryRecyclerView;
    private Cursor cursor;
    private CropperView previewImageView;

    private AutoFitAppBarLayout appBarLayout;
    private int noOfColumns;
    private int selectedPosition=0;
    Toolbar toolbar;
    private PhotoAdapter photoAdapter;
    private Matrix matrix;
    private ScaleGestureDetector scaleGestureDetector;
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

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
     //   ((BaseActivity)getActivity()).getSupportActionBar().hide();
        appBarLayout= (AutoFitAppBarLayout) view.findViewById(R.id.app_bar_collpse);
        appBarLayout.setAspectRatio(9,16);
        galleryRecyclerView= (RecyclerView) view.findViewById(R.id.recyclerview_gallery);
        noOfColumns=UIUtils.getNumOfColumns(getActivity(),100);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),noOfColumns );
        previewImageView= (CropperView) view.findViewById(R.id.imageview_preview);
       // previewImageView.setAspectRatio(16,9);
        galleryRecyclerView.setLayoutManager(gridLayoutManager);
        previewImageView.setDebug(true);
        matrix=new Matrix();
        final CustomCoordinatorLayout coordinatorLayout= (CustomCoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        View view1=view.findViewById(R.id.view_sep);
        view1.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View view, MotionEvent motionEvent) {
                 coordinatorLayout.setAllowForScrool(true);
                return true;
            }
        });
        previewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(coordinatorLayout.isAllowForScrool())
                {
                    coordinatorLayout.setAllowForScrool(false);
                    appBarLayout.setExpanded(true);
                }
            }
        });
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               coordinatorLayout.setAllowForScrool(true);
            }
        });

        cursor=getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                MediaStore.Images.Media.DATE_MODIFIED+" DESC"
        );
       // GestureDetector gestureDetector =new GestureDetector()

//        scaleGestureDetector=new ScaleGestureDetector(getActivity(),new ScaleGestureDetector.SimpleOnScaleGestureListener(){
//            @Override
//            public boolean onScale(ScaleGestureDetector detector) {
//                THLoggerUtil.debug("hh","decteds");
//                float scaleFactor=detector.getScaleFactor();
//                scaleFactor=Math.max(0.1f,Math.min(scaleFactor,5f));
//                matrix.setScale(scaleFactor,scaleFactor);
//                //previewImageView.setImageMatrix(matrix);
//                return true;
//            }
//        });

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

    public void setGallerySelection(String imagId){
            imageId=imagId;
            Bitmap myBitMap= GalleryUtil.getBitmapOfImage(getActivity(),imageId);
            previewImageView.setImageBitmap(myBitMap);
         //   previewImageView.setAdjustViewBounds(true);

    }

    @Override
    public void onPause() {
        super.onPause();
        THLoggerUtil.debug("hh","GalleryPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        THLoggerUtil.debug("hh","GalleryDestory");
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.select_photo_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        THLoggerUtil.debug("h","selected");
        if(item.getItemId()==R.id.menu_next){
            THLoggerUtil.debug("hh","selected");
            Bitmap bitmap=previewImageView.getCroppedBitmap();
            if (bitmap != null) {
                File file=null;
                try {
                    file = new File(Environment.getExternalStorageDirectory() + "/crop_test.jpg");
                    BitmapUtils.writeBitmapToFile(bitmap, file, 90);
                    Intent intent = new Intent(getActivity(), PhotographyFilterActivtiy.class);
                    intent.putExtra(IMAGEID, imageId);
                    intent.putExtra(IMGFILE, file.getAbsolutePath());
                    startActivity(intent);
                } catch (IOException e) {

                }
             }
        }
        return super.onOptionsItemSelected(item);
    }

    public  void carryPhotoToNext()
    {
       // replace(R.id.layout_container,PhotoFilterFragment.newInstance(this.photoAdapter.getItemAtPosition(selectedPosition)));
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem menuItem) {
//        switch(menuItem.getItemId()){
////            case android.R.id.home:
////                pop();
////                break;
//            case R.id.menu_next:
//
//                //pop();
//                //replace(R.id.dashboard_container2, PhotoFilterFragment.newInstance(imageId),true);
//                //selectPhotoFragment.replaces(imageId);
//                break;
//        }
//
//        return true;
//
//    }
}

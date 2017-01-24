package com.bhuvanesh.talenthive.photography.adapter;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.photography.fragment.CameraFragment;
import com.bhuvanesh.talenthive.photography.fragment.GalleryFragment;

public class SelectPhotoPagerAdapter extends FragmentStatePagerAdapter {
    private static final byte TAB_GALLERY = 0;
    private static final byte TAB_CAMERA = TAB_GALLERY + 1;
    private static  String[] TAB_TITLE ;

    public SelectPhotoPagerAdapter(FragmentManager fm, Context context)
    {
        super(fm);
        TAB_TITLE = context.getResources().getStringArray(R.array.lbl_photo_select_tab_title);
    }

    @Override
    public BaseFragment getItem(int position) {
        switch (position)
        {
            case TAB_GALLERY:
                return GalleryFragment.newInstance();
            default:
                return CameraFragment.newInstance();
        }
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLE[position];
    }
    @Override
    public int getCount() {
        return TAB_TITLE.length;
    }
}

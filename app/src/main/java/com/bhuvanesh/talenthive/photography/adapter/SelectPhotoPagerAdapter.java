package com.bhuvanesh.talenthive.photography.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bhuvanesh.talenthive.photography.fragment.CameraFragment;
import com.bhuvanesh.talenthive.photography.fragment.GalleryFragment;

public class SelectPhotoPagerAdapter extends FragmentPagerAdapter {

    public SelectPhotoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position)
        {
            case 0:
                fragment= GalleryFragment.newInstance();
                break;
            case 1:
                fragment= CameraFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}

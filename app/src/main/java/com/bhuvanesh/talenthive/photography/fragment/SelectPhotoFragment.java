package com.bhuvanesh.talenthive.photography.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bhuvanesh.talenthive.BaseActivity;
import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.photography.adapter.SelectPhotoPagerAdapter;
import com.bhuvanesh.talenthive.photography.view.CustomPhotoViewPager;
import com.bhuvanesh.talenthive.util.THLoggerUtil;

public class SelectPhotoFragment extends BaseFragment {
    private TabLayout tabLayout;
    private CustomPhotoViewPager viewPager;
    private SelectPhotoPagerAdapter selectPhotoPagerAdapter;
    private static  String[] TAB_TITLE ;
    public static SelectPhotoFragment newInstance()
    {
        return new SelectPhotoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_photo,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TAB_TITLE = getContext().getResources().getStringArray(R.array.lbl_photo_select_tab_title);
        restoreActionBar(TAB_TITLE[0]);
        selectPhotoPagerAdapter=new SelectPhotoPagerAdapter(getFragmentManager(),getContext());
        ((BaseActivity)getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        tabLayout= (TabLayout) view.findViewById(R.id.tab_layout_select_photo);
        viewPager= (CustomPhotoViewPager) view.findViewById(R.id.view_pager_select_photo);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(selectPhotoPagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
                restoreActionBar(TAB_TITLE[tabLayout.getSelectedTabPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        THLoggerUtil.debug("hh","PhotoOnPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        THLoggerUtil.debug("hh","PhotoOnDestroy");
    }
  public void replaces(String imageId)
  {
      //replace(R.id.dashboard_container2,PhotoFilterFragment.newInstance(imageId));
  }
    public void restoreActionBar(String title) {
        ActionBar actionBar=((BaseActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(title);
    }

}

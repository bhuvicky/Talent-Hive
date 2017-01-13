package com.bhuvanesh.talenthive.photography.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bhuvanesh.talenthive.BaseActivity;
import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.photography.adapter.SelectPhotoPagerAdapter;
import com.bhuvanesh.talenthive.util.THLoggerUtil;

public class SelectPhotoFragment extends BaseFragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SelectPhotoPagerAdapter selectPhotoPagerAdapter;
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
        selectPhotoPagerAdapter=new SelectPhotoPagerAdapter(getFragmentManager());
        ((BaseActivity)getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        tabLayout= (TabLayout) view.findViewById(R.id.tab_layout_select_photo);
        viewPager= (ViewPager) view.findViewById(R.id.view_pager_select_photo);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(selectPhotoPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
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
    public void onDestroy() {
        super.onDestroy();
        THLoggerUtil.debug("hh","ssss");

    }
  public void replaces(String imageId)
  {
      replace(R.id.dashboard_container2,PhotoFilterFragment.newInstance(imageId));
  }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_next) {
            THLoggerUtil.debug("hh", "selected");
//           return true;
        }
        return super.onOptionsItemSelected(item);

    }
}

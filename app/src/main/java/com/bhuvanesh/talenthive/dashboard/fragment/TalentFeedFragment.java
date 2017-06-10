package com.bhuvanesh.talenthive.dashboard.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.dashboard.adapter.THFeedViewPagerAdapter;

public class TalentFeedFragment extends BaseFragment {
    private THFeedViewPagerAdapter mViewPagerAdapter;

    public static TalentFeedFragment newInstance() {
        return new TalentFeedFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_talent_hive_feed, container, false);
        //getActivity().setTitle(R.string.title_story_writing);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        if (mViewPagerAdapter== null)
            mViewPagerAdapter = new THFeedViewPagerAdapter(getActivity(), getChildFragmentManager());
            viewPager.setAdapter(mViewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
             viewPager.setOffscreenPageLimit(1);
        return rootView;
    }
}

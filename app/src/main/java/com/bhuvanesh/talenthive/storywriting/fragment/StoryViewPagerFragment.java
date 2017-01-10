package com.bhuvanesh.talenthive.storywriting.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhuvanesh.talenthive.BaseActivity;
import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.storywriting.adapter.StoryViewPagerAdapter;

public class StoryViewPagerFragment extends BaseFragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private StoryViewPagerAdapter mStoryViewPagerAdapter;

    public static StoryViewPagerFragment newInstance() {
        return new StoryViewPagerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_story_viewpager, container, false);

        getActivity().setTitle(R.string.title_story_writing);

        mTabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        if (mStoryViewPagerAdapter == null)
            mStoryViewPagerAdapter = new StoryViewPagerAdapter(getActivity(), getChildFragmentManager());
        mViewPager.setAdapter(mStoryViewPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        return rootView;
    }
}

package com.bhuvanesh.talenthive.storywriting.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bhuvanesh.talenthive.storywriting.fragment.MyStoriesFragment;
import com.bhuvanesh.talenthive.storywriting.fragment.StoryFeedFragment;

public class StoryViewPagerAdapter extends FragmentStatePagerAdapter{

    private static final byte TAB_FEED = 0;
    private static final byte TAB_MY_TALENT = TAB_FEED + 1;

    private Fragment fragment;

    public StoryViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (fragment == null) {
            switch (position) {
                case TAB_FEED:
                    fragment = StoryFeedFragment.newInstance();
                    break;
                default:
                    fragment = MyStoriesFragment.newInstance();
            }
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 0;
    }
}

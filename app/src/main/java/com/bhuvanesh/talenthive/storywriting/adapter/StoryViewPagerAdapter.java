package com.bhuvanesh.talenthive.storywriting.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.storywriting.fragment.MyStoriesFragment;
import com.bhuvanesh.talenthive.storywriting.fragment.StoryFeedFragment;

public class StoryViewPagerAdapter extends FragmentStatePagerAdapter{

    private static final byte TAB_FEED = 0;
    private static final byte TAB_MY_TALENT = TAB_FEED + 1;

    private static String[] TAB_TITLE;

    public StoryViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        TAB_TITLE = context.getResources().getStringArray(R.array.lbl_story_tab_title);
    }

    @Override
    public BaseFragment getItem(int position) {
        switch (position) {
            case TAB_FEED:
                return StoryFeedFragment.newInstance();
            default:
                return MyStoriesFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return TAB_TITLE.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLE[position];
    }
}

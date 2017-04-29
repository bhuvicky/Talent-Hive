package com.bhuvanesh.talenthive.dashboard.adapter;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.dance.fragment.DanceFeedFragment;
import com.bhuvanesh.talenthive.photography.fragment.PhotoFeedFragment;
import com.bhuvanesh.talenthive.storywriting.fragment.StoryViewPagerFragment;

public class THFeedViewPagerAdapter extends FragmentStatePagerAdapter{

private static final byte TAB_STORY_FEED = 0;
private static final byte TAB_PHOTO_FEED = TAB_STORY_FEED + 1;
private static final byte TAB_DANCE_FEED=TAB_PHOTO_FEED+1;
private static String[] TAB_TITLE;

public THFeedViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        TAB_TITLE = context.getResources().getStringArray(R.array.lbl_feed_tab_title);
        }

@Override
public BaseFragment getItem(int position) {
        switch (position) {
        case TAB_STORY_FEED:
        return StoryViewPagerFragment.newInstance();
        case TAB_PHOTO_FEED:
        return PhotoFeedFragment.newInstance(null,null);
        default:
        return DanceFeedFragment.newInstance();
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


package com.bhuvanesh.talenthive.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bhuvanesh.talenthive.BaseActivity;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.dance.fragment.DanceFeedFragment;
import com.bhuvanesh.talenthive.storywriting.fragment.StoryFeedFragment;
import com.bhuvanesh.talenthive.storywriting.fragment.StoryViewPagerFragment;

public class THActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talent_hive);
        setActionBar(R.id.toolbar_main);
        replace(R.id.flayout_container, StoryViewPagerFragment.newInstance());
    }
}

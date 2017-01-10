package com.bhuvanesh.talenthive.storywriting.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;

public class MyStoriesFragment extends BaseFragment{

    public static MyStoriesFragment newInstance() {
        return new MyStoriesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mystories, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_write_story);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(R.id.flayout_container, EditStoryFragment.newInstance(null));
            }
        });
        return view;
    }
}

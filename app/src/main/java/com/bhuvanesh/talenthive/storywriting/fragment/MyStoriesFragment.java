package com.bhuvanesh.talenthive.storywriting.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.database.THDBManager;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.storywriting.adapter.MyStoriesAdapter;
import com.bhuvanesh.talenthive.storywriting.model.Story;
import com.bhuvanesh.talenthive.util.UIUtils;

import java.util.LinkedList;
import java.util.List;

public class MyStoriesFragment extends BaseFragment {

    private MyStoriesAdapter mMyStoriesAdapter;
    private List<Story> mStoryList = new LinkedList<>();

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

        GridView gridViewMyStories = (GridView) view.findViewById(R.id.gridview_mystories);
        gridViewMyStories.setNumColumns(UIUtils.getNumOfColumns(getActivity(), 70));
        gridViewMyStories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                System.out.println("on item click grid view");
                replace(R.id.flayout_container, EditStoryFragment.newInstance(mStoryList.get(position)));
            }
        });

        gridViewMyStories.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        gridViewMyStories.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                System.out.println("checked state = " + checked);
                mMyStoriesAdapter.getItem(position).isSelected = checked;
                mMyStoriesAdapter.notifyDataSetChanged();

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        if (mMyStoriesAdapter == null)
            mMyStoriesAdapter = new MyStoriesAdapter();
        gridViewMyStories.setAdapter(mMyStoriesAdapter);

        THDBManager manager = new THDBManager();
        manager.setmOnTHDBMangerListener(new THDBManager.OnTHDBMangerListener<List<Story>>() {
            @Override
            public void onTHDBSuccessful(List<Story> response) {
                mStoryList.clear();
                mStoryList.addAll(response);
                mMyStoriesAdapter.setData(mStoryList);
            }

            @Override
            public void onTHDBError(THException error) {
                System.out.println("db fetch error");
            }
        });
        manager.getStoryList();

        return view;
    }
}

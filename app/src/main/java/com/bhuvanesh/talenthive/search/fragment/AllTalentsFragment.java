package com.bhuvanesh.talenthive.search.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.search.adapter.AllTalentsAdapter;
import com.bhuvanesh.talenthive.util.UIUtils;

public class AllTalentsFragment extends BaseFragment {

    private boolean mIsLayoutTypeList;
    private AllTalentsAdapter mAllTalentsAdapter;
    private RecyclerView mRecyclerView;

    public static AllTalentsFragment newInstance() {
        return new AllTalentsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_talents, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_all_talents);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), UIUtils.getNumOfColumns(getActivity(), 100)));

        if (mAllTalentsAdapter == null)
            mAllTalentsAdapter = new AllTalentsAdapter();
        mRecyclerView.setAdapter(mAllTalentsAdapter);

        mAllTalentsAdapter.setOnItemClickListener(new AllTalentsAdapter.OnItemClickListener() {
            @Override
            public void onGridItemClick() {
                mIsLayoutTypeList = !mIsLayoutTypeList;
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setAdapter(mAllTalentsAdapter);
                mAllTalentsAdapter.setLayoutType(mIsLayoutTypeList);
            }
        });

        return view;
    }

    @Override
    protected void onBackPress() {
       if (mIsLayoutTypeList) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), UIUtils.getNumOfColumns(getActivity(), 100)));
           mRecyclerView.setAdapter(mAllTalentsAdapter);
           mIsLayoutTypeList = !mIsLayoutTypeList;
           mAllTalentsAdapter.setLayoutType(mIsLayoutTypeList);
       } else
           super.onBackPress();
    }
}

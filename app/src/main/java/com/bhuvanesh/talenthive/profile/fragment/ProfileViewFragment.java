package com.bhuvanesh.talenthive.profile.fragment;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bhuvanesh.talenthive.BaseActivity;
import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;

public class ProfileViewFragment extends BaseFragment {

    public static ProfileViewFragment newInstance(String profileId) {
        return new ProfileViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_view, container, false);
        setHasOptionsMenu(true);
        ((BaseActivity) getActivity()).hideMainToolbar();

        CollapsingToolbarLayout cToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        cToolbar.setTitle("");
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile_view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getGroupId()) {
            case R.id.menu_edit_profile:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

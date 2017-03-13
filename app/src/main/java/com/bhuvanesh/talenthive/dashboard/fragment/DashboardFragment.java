package com.bhuvanesh.talenthive.dashboard.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.profile.fragment.ProfileViewFragment;

public class DashboardFragment extends BaseFragment {

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_home:
                                break;

                            case R.id.menu_search:
                                break;

                            case R.id.menu_share:
                                TalentListFragment.newInstance().show(getFragmentManager(), "dialog");
                                break;

                            default:
                                replaceChildFragment(R.id.flayout_container, ProfileViewFragment.newInstance());
                        }
                        return true;
                    }
                });
        return view;
    }
}

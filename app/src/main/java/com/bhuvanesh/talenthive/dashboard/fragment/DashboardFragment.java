package com.bhuvanesh.talenthive.dashboard.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.OnSubmitClickListener;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.dance.fragment.DanceFeedFragment;
import com.bhuvanesh.talenthive.photography.fragment.SelectPhotoFragment;
import com.bhuvanesh.talenthive.profile.fragment.ProfileViewFragment;
import com.bhuvanesh.talenthive.storywriting.fragment.StoryViewPagerFragment;

public class DashboardFragment extends BaseFragment {

    public static final int TALENT_TYPE_PHOTO = 0;
    public static final int TALENT_TYPE_STORY = TALENT_TYPE_PHOTO + 1;
    public static final int TALENT_TYPE_DANCE = TALENT_TYPE_STORY;

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
                                replaceTalentListFragment();
                                break;

                            default:
                                replaceChildFragment(R.id.flayout_container, ProfileViewFragment.newInstance());
                        }
                        return true;
                    }
                });
        return view;
    }

    private void replaceTalentListFragment() {
        final TalentListFragment fragment  = TalentListFragment.newInstance();
        fragment.setOnSubmitClickListener(new OnSubmitClickListener() {
            @Override
            public void onSubmit(Object object) {

            }

            @Override
            public void onSubmit(int type) {
                BaseFragment baseFragment = null;
                switch (type) {
                    case TALENT_TYPE_PHOTO:
                        baseFragment = SelectPhotoFragment.newInstance();
                        break;
                    case TALENT_TYPE_STORY:
                        baseFragment = StoryViewPagerFragment.newInstance();
                        break;
                    default:
                        baseFragment = DanceFeedFragment.newInstance();
                        break;
                }
                replaceChildFragment(R.id.flayout_container, baseFragment);
            }

            @Override
            public void onSubmit() {

            }
        });
        fragment.show(getFragmentManager(), "dialog");


    }
}

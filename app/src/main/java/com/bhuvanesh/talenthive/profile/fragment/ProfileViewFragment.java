package com.bhuvanesh.talenthive.profile.fragment;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bhuvanesh.talenthive.BaseActivity;
import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.THApplication;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.model.Profile;
import com.bhuvanesh.talenthive.profile.manager.ProfileManager;
import com.bhuvanesh.talenthive.widget.CircularNetworkImageView;

import java.util.List;

public class ProfileViewFragment extends BaseFragment implements ProfileManager.OnGetProfileManager {

    private Profile mProfile;
    private Profile mProfileForView, mProfileNotView;
    private NetworkImageView mImageViewCover;
    private CircularNetworkImageView mImageViewProfile;
    private TextView mTextViewName, mTextViewUserName, mTextViewFollowersCount, mTextViewFollowingCount;
    private ImageLoader imageLoader;
    public static ProfileViewFragment newInstance(Profile profile) {
        ProfileViewFragment fragment = new ProfileViewFragment();
        fragment.mProfile = profile == null ? new Profile() : profile;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_view, container, false);
        setHasOptionsMenu(true);
       // THLoggerUtil.debug("hh",mProfile.name);
         imageLoader=THApplication.getInstance().getImageLoader();
        ((BaseActivity) getActivity()).hideMainToolbar();
        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);

        final CollapsingToolbarLayout cToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        cToolbar.setTitle(" ");

        AppBarLayout appbarLayout = (AppBarLayout) view.findViewById(R.id.appbar_layout);
        appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == -cToolbar.getHeight() + toolbar.getHeight()) {
                    cToolbar.setTitle(mTextViewName.getText());
                } else {
                    cToolbar.setTitle(" ");
                }
            }
        });

        mImageViewCover = (NetworkImageView) view.findViewById(R.id.imageview_cover);
        mImageViewProfile = (CircularNetworkImageView) view.findViewById(R.id.imageview_profile_icon);
        mTextViewName = (TextView) view.findViewById(R.id.textview_name);
        mTextViewUserName = (TextView) view.findViewById(R.id.textview_user_name);
//        mTextViewBio = (TextView) view.findViewById(R.id.textview_bio);
//        mTextViewEmail = (TextView) view.findViewById(R.id.textview_name);
//        mTextViewMobileNo = (TextView) view.findViewById(R.id.textview_name);
//        mTextViewGender = (TextView) view.findViewById(R.id.textview_name);
//        mTextViewCountry = (TextView) view.findViewById(R.id.textview_name);


        mTextViewFollowersCount = (TextView) view.findViewById(R.id.textview_follower_count);
        mTextViewFollowingCount = (TextView) view.findViewById(R.id.textview_following_count);
       // getProfileList();

        mImageViewProfile.setImageUrl(mProfile.profilePicUrl,imageLoader);
        mImageViewCover.setImageUrl(mProfile.coverImageUrl,THApplication.getInstance().getImageLoader());
        mTextViewName.setText(mProfile.name);
        mTextViewUserName.setText(mProfile.userName);
        mTextViewFollowersCount.setText("0");
        mTextViewFollowingCount.setText("0");

        return view;
    }

    private void getProfileList() {
//        ProfileManager manager = new ProfileManager();
//        if (THPreference.getInstance().getProfileId().equals(mProfileId))
//            manager.getProfileList("", null, this);
//        else
//            manager.getProfileList("selfProfileId", "other profile id", this);
    }

    @Override
    public void onGetProfileListSuccess(List<Profile> response) {
//        if (response.size() == 2)
//            for (Profile profile : response) {
//                if (profile.profileId.equals(mProfileId)) {
//                    mProfileForView = profile;
//                    updateProfile(mProfileForView);
//                }
//                if (THPreference.getInstance().getProfileId().equals(mProfileId))
//                    mProfileNotView = profile;
//            }
//        else {
//            mProfileForView = response.get(0);
//            updateProfile(mProfileForView);
//        }
    }

    @Override
    public void onGetProfileListError(THException exception) {

    }

    private void updateProfile(Profile mProfileForView) {
        mImageViewProfile.setDefaultImageResId(R.drawable.ic_default_avatar);
        mImageViewProfile.setImageUrl(mProfileForView.profilePicUrl, THApplication.getInstance().getImageLoader());
        mTextViewName.setText(mProfileForView.name);
        mTextViewUserName.setText(mProfileForView.userName);
        mTextViewFollowersCount.setText(String.valueOf(mProfileForView.followersList.size()));
        mTextViewFollowingCount.setText(String.valueOf(mProfileForView.followingList.size()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile_view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_discover_people:
                replace(R.id.flayout_container, DiscoverPeopleFragment.newInstance());
                break;

            case R.id.menu_edit_profile:
                replace(R.id.flayout_container, EditProfileFragment.newInstance());
                break;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((BaseActivity) getActivity()).showMainToolbar();
    }
}

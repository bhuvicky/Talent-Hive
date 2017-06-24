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
import com.bhuvanesh.talenthive.account.model.UserDetails;
import com.bhuvanesh.talenthive.dashboard.activity.DashboardActivity;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.model.Profile;
import com.bhuvanesh.talenthive.profile.manager.ProfileManager;
import com.bhuvanesh.talenthive.util.CountUtils;
import com.bhuvanesh.talenthive.widget.CircularNetworkImageView;

import java.util.List;

public class ProfileViewFragment extends BaseFragment  {

    private com.bhuvanesh.talenthive.profile.model.Profile mProfile;
    private UserDetails userDetails;
    private Profile mProfileForView, mProfileNotView;
    private NetworkImageView mImageViewCover;
    private CircularNetworkImageView mImageViewProfile;
    private TextView mTextViewName, mTextViewUserName, mTextViewFollowersCount, mTextViewFollowingCount,mTextViewBio;
    private ImageLoader imageLoader;
    public static ProfileViewFragment newInstance(UserDetails userDetails) {
        ProfileViewFragment fragment = new ProfileViewFragment();
        fragment.userDetails=userDetails;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_view, container, false);
        setHasOptionsMenu(true);
        mProfile=new com.bhuvanesh.talenthive.profile.model.Profile();
        mProfile.user=userDetails;

       // THLoggerUtil.debug("hh",mProfile.name);
         imageLoader=THApplication.getInstance().getImageLoader();




        mImageViewCover = (NetworkImageView) view.findViewById(R.id.imageview_cover);
        mImageViewProfile = (CircularNetworkImageView) view.findViewById(R.id.imageview_profile_icon);
        mTextViewName = (TextView) view.findViewById(R.id.textview_name);
        mTextViewUserName = (TextView) view.findViewById(R.id.textview_user_name);
       mTextViewBio = (TextView) view.findViewById(R.id.textview_bio);
//        mTextViewEmail = (TextView) view.findViewById(R.id.textview_name);
//        mTextViewMobileNo = (TextView) view.findViewById(R.id.textview_name);
//        mTextViewGender = (TextView) view.findViewById(R.id.textview_name);
//        mTextViewCountry = (TextView) view.findViewById(R.id.textview_name);


        mTextViewFollowersCount = (TextView) view.findViewById(R.id.textview_follower_count);
        mTextViewFollowingCount = (TextView) view.findViewById(R.id.textview_following_count);
       // getProfileList();

        mTextViewName.setText(mProfile.user.name);
        mTextViewUserName.setText("@"+mProfile.user.userName);

                mImageViewProfile.setImageUrl(mProfile.user.profilePicUrl,imageLoader);

        getProfile();

        return view;
    }

    private void getProfileList() {
//        ProfileManager manager = new ProfileManager();
//        if (THPreference.getInstance().getProfileId().equals(mProfileId))
//            manager.getProfileList("", null, this);
//        else
//            manager.getProfileList("selfProfileId", "other profile id", this);
    }
    private void getProfile() {
        ProfileManager manager = new ProfileManager();
        manager.getProfile(mProfile.user.userName, new ProfileManager.OnGetProfileManager() {
            @Override
            public void onGetProfileListSuccess(com.bhuvanesh.talenthive.profile.model.Profile response) {
                mProfile=response;
                mImageViewCover.setImageUrl(mProfile.coverImageUrl,THApplication.getInstance().getImageLoader());
                if(mProfile.bio!=null)mTextViewBio.setText(mProfile.bio);
                mTextViewFollowersCount.setText(CountUtils.formatCount(mProfile.followersCount));
                mTextViewFollowingCount.setText(CountUtils.formatCount(mProfile.followingCount));
            }

            @Override
            public void onGetProfileListError(THException exception) {

            }
        });
    }

//    @Override
//    public void onGetProfileListSuccess(List<Profile> response) {
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
//    }

//    @Override
//    public void onGetProfileListError(THException exception) {
//
//    }

    private void updateProfile(Profile mProfileForView) {
        mImageViewProfile.setDefaultImageResId(R.drawable.ic_default_avatar);
        mImageViewProfile.setImageUrl(mProfileForView.user.profilePicUrl, THApplication.getInstance().getImageLoader());
        mTextViewName.setText(mProfileForView.user.name);
        mTextViewUserName.setText(mProfileForView.user.userName);
        mTextViewFollowersCount.setText(String.valueOf(0));
        mTextViewFollowingCount.setText(String.valueOf(0));
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

    }
}

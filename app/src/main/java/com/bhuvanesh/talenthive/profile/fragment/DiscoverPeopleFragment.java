package com.bhuvanesh.talenthive.profile.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.util.PeopleAsyncTask;
import com.bhuvanesh.talenthive.util.THPreference;


public class DiscoverPeopleFragment extends BaseFragment {

    public static DiscoverPeopleFragment newInstance() {
        return new DiscoverPeopleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover_people, container, false);

        TextView textView = (TextView) view.findViewById(R.id.textview_google_friends);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(THPreference.getInstance().getGoogleServerAuthCode())) {
                    System.out.println("log server auth code = " +  THPreference.getInstance().getGoogleServerAuthCode());
                    new PeopleAsyncTask(getActivity()).execute(THPreference.getInstance().getGoogleServerAuthCode());
                }
            }
        });
        return view;
    }
}

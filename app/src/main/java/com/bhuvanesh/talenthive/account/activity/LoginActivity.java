package com.bhuvanesh.talenthive.account.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.bhuvanesh.talenthive.BaseActivity;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.account.fragment.LoginFragment;
import com.bhuvanesh.talenthive.dashboard.activity.DashboardActivity;
import com.bhuvanesh.talenthive.util.THPreference;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Fabric.with(this, new Crashlytics());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);

        boolean isLoggedIn = THPreference.getInstance().isInAppLoggedIn() || THPreference.getInstance().isFBLoggedIn() ||
                THPreference.getInstance().isGoolgeLoggedIn();

        isLoggedIn = true;
        if (isLoggedIn)
            startActivity(new Intent(this, DashboardActivity.class));
        else
            replace(R.id.layout_container, LoginFragment.newInstance());

    }

}

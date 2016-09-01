package com.bhuvanesh.talenthive.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bhuvanesh.talenthive.BaseActivity;
import com.bhuvanesh.talenthive.R;

public class THActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talent_hive);
        setActionBar(R.id.toolbar_main);
    }
}

package com.bhuvanesh.talenthive;


import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bhuvanesh.talenthive.database.DBManager;

public class THApplication extends Application{

    private static THApplication mInstance;
    private RequestQueue mRequestQueue;


    //onCreate will call only one time
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        new DBManager(this).connect();
    }

    public static synchronized THApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }
}

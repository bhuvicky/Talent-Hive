package com.bhuvanesh.talenthive;


import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class THApplication extends Application{

    private static THApplication mInstance;
    private RequestQueue mRequestQueue;

    //onCreate will call only one time
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
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

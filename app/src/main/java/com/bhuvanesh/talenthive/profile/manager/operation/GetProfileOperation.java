package com.bhuvanesh.talenthive.profile.manager.operation;


import com.android.volley.Request;
import com.bhuvanesh.talenthive.Config;
import com.bhuvanesh.talenthive.constant.URLConstant;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.manager.operation.WebServiceOperation;

import com.bhuvanesh.talenthive.profile.model.Profile;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class GetProfileOperation extends WebServiceOperation {

    public interface OnGetProfileOperation {
        void onGetProfileListSuccess(com.bhuvanesh.talenthive.profile.model.Profile response);
        void onGetProfileListError(THException exception);
    }

    private OnGetProfileOperation mOnGetProfileOperation;

    public GetProfileOperation(String userName, Map<String, String> header, OnGetProfileOperation listener) {
        super(URLConstant.GET_PROFILE+URLConstant.slash+userName, Request.Method.GET, header, com.bhuvanesh.talenthive.profile.model.Profile.class, GetProfileOperation.class.getSimpleName());
        mOnGetProfileOperation = listener;
    }

    public void addToRequestQueue() {
        if (Config.HARDCODED_ENABLE)
            onSuccess(getFromAssetsFolder("profile.json", Profile.class));
        else
            super.addToRequestQueue();

    }

    @Override
    public void onError(THException exception) {
        if (mOnGetProfileOperation != null)
            mOnGetProfileOperation.onGetProfileListError(exception);
    }

    @Override
    public void onSuccess(Object response) {
        if (mOnGetProfileOperation != null)
            mOnGetProfileOperation.onGetProfileListSuccess((Profile )response);
    }
}

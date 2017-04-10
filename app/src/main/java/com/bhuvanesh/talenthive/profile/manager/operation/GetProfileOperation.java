package com.bhuvanesh.talenthive.profile.manager.operation;


import com.android.volley.Request;
import com.bhuvanesh.talenthive.Config;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.manager.operation.WebServiceOperation;
import com.bhuvanesh.talenthive.model.Profile;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class GetProfileOperation extends WebServiceOperation {

    public interface OnGetProfileOperation {
        void onGetProfileListSuccess(List<Profile> response);
        void onGetProfileListError(THException exception);
    }

    private OnGetProfileOperation mOnGetProfileOperation;

    public GetProfileOperation(String selfProfileId, String other, Map<String, String> header, OnGetProfileOperation listener) {
        super("", Request.Method.GET, header, new TypeToken<List<Profile>> () {}.getType(), GetProfileOperation.class.getSimpleName());
        mOnGetProfileOperation = listener;
    }

    public void addToRequestQueue() {
        if (Config.HARDCODED_ENABLE)
            onSuccess(getFromAssetsFolder("profile.json", new TypeToken<List<Profile>> () {}.getType()));
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
            mOnGetProfileOperation.onGetProfileListSuccess((List<Profile>) response);
    }
}

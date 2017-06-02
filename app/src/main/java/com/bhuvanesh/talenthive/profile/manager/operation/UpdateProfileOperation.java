package com.bhuvanesh.talenthive.profile.manager.operation;

import com.android.volley.Request;
import com.bhuvanesh.talenthive.BaseResponse;
import com.bhuvanesh.talenthive.Config;
import com.bhuvanesh.talenthive.constant.URLConstant;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.manager.operation.WebServiceOperation;
import com.bhuvanesh.talenthive.model.Profile;
import com.bhuvanesh.talenthive.util.THLoggerUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class UpdateProfileOperation extends WebServiceOperation {

    public interface OnUpdateProfileOperation {
        void onUpdateProfileSuccess(BaseResponse response);

        void onUpdateProfileError(THException exception);
    }

    private OnUpdateProfileOperation mOnUpdateProfileOperation;


    public UpdateProfileOperation(Map<String, String> header, String body, OnUpdateProfileOperation listener) {
        super(URLConstant.UPDATE_PROFILE, Request.Method.POST, header, body, BaseResponse.class, UpdateProfileOperation.class.getSimpleName());
        mOnUpdateProfileOperation = listener;
    }

    public void addToRequestQueue() {
        if (Config.HARDCODED_ENABLE) {
        }
//            onSuccess(getFromAssetsFolder("profile.json", new TypeToken<List<Profile>>() {}.getType()));
        else
            super.addToRequestQueue();

    }

    @Override
    public void onError(THException exception) {
        mOnUpdateProfileOperation.onUpdateProfileError(exception);
    }

    @Override
    public void onSuccess(Object response) {
        mOnUpdateProfileOperation.onUpdateProfileSuccess((BaseResponse) response);
    }
}

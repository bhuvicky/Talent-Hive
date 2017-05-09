package com.bhuvanesh.talenthive.fcm.manager.operation;

import com.android.volley.Request;
import com.bhuvanesh.talenthive.BaseResponse;
import com.bhuvanesh.talenthive.Config;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.manager.operation.WebServiceOperation;

import java.util.Map;

public class FCMServiceOperation extends WebServiceOperation {

    public interface OnFCMKeyServiceOperation {
        void onFCMKeyServiceOperationSuccess(BaseResponse response);

        void onFCMKeyServiceOperationError(THException e);
    }

    private OnFCMKeyServiceOperation mOnFCMKeyServiceOperation;

    public FCMServiceOperation(Map<String, String> header, String body, OnFCMKeyServiceOperation listener) {
        super("", Request.Method.POST, header, body, BaseResponse.class,
                FCMServiceOperation.class.getSimpleName());
        mOnFCMKeyServiceOperation = listener;
    }

    public void addToRequestQueue() {
        if (Config.HARDCODED_ENABLE) {
            BaseResponse fromAssetsFolder = getFromAssetsFolder("basejson.json", BaseResponse.class);
            onSuccess(fromAssetsFolder);
        } else {
            super.addToRequestQueue();
        }
    }

    @Override
    public void onError(THException exception) {
        mOnFCMKeyServiceOperation.onFCMKeyServiceOperationError(exception);
    }

    @Override
    public void onSuccess(Object response) {
        mOnFCMKeyServiceOperation.onFCMKeyServiceOperationSuccess((BaseResponse) response);
    }
}

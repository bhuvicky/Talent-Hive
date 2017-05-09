package com.bhuvanesh.talenthive.fcm.manager;

import com.bhuvanesh.talenthive.BaseResponse;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.fcm.manager.operation.FCMServiceOperation;
import com.bhuvanesh.talenthive.fcm.model.FCMKeyRequest;
import com.bhuvanesh.talenthive.manager.WebServiceManager;
import com.google.gson.Gson;

/**
 * Created by bhuvanesh on 02-05-2017.
 */

public class FCMServiceManager extends WebServiceManager {

    private OnFCMKeyServiceManager mOnFCMKeyServiceManager;

    public interface OnFCMKeyServiceManager {
        void onFCMKeyServiceOperationSuccess(BaseResponse response);

        void onFCMKeyServiceOperationError(THException e);
    }

    public void sendFCMKey(FCMKeyRequest fcmKeyRequest, final OnFCMKeyServiceManager listener) {
        FCMServiceOperation operation = new FCMServiceOperation(getHeaders(), new Gson().toJson(fcmKeyRequest, FCMKeyRequest.class),
                new FCMServiceOperation.OnFCMKeyServiceOperation() {
                    @Override
                    public void onFCMKeyServiceOperationSuccess(BaseResponse response) {
                        if (listener != null)
                            listener.onFCMKeyServiceOperationSuccess(response);
                    }

                    @Override
                    public void onFCMKeyServiceOperationError(THException e) {
                        if (listener != null)
                            listener.onFCMKeyServiceOperationError(e);
                    }
                });
        operation.addToRequestQueue();
    }
}

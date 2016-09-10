package com.bhuvanesh.talenthive.account.manager.operation;

import com.android.volley.Request;
import com.bhuvanesh.talenthive.Config;
import com.bhuvanesh.talenthive.account.model.LoginResponse;
import com.bhuvanesh.talenthive.constant.URLConstant;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.manager.operation.WebServiceOperation;
import com.bhuvanesh.talenthive.util.THLoggerUtil;

import java.util.Map;

public class UserLoginOperation extends WebServiceOperation {

    public interface IUserLoginOperation {
        void onUserLoginSuccess(LoginResponse response);
        void onUserLoginError(THException exception);
    }

    private IUserLoginOperation iUserLoginOperation;

    public UserLoginOperation(String body, Map<String, String> header, IUserLoginOperation listener) {
        super(URLConstant.USER_LOGIN_URI, Request.Method.POST, header, body, LoginResponse.class, UserLoginOperation.class.getSimpleName());
        iUserLoginOperation = listener;
    }

    @Override
    public void addToRequestQueue() {
        if (Config.HARDCODED_ENABLE) {
            onSuccess(getFromAssetsFolder("loginresponse.json", LoginResponse.class));
        } else {
            super.addToRequestQueue();
        }
    }

    @Override
    public void onError(THException exception) {
        iUserLoginOperation.onUserLoginError(exception);
    }

    @Override
    public void onSuccess(Object response) {
        iUserLoginOperation.onUserLoginSuccess((LoginResponse) response);
    }
}

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


    private String TAG = UserLoginOperation.class.getSimpleName();
    private IUserLoginOperation iUserLoginOperation;

    public UserLoginOperation(Map<String, String> header, String body, IUserLoginOperation listener) {
        super(URLConstant.USER_LOGIN_URI, Request.Method.POST, header, body, LoginResponse.class, UserLoginOperation.class.getSimpleName());
        this.iUserLoginOperation = listener;
    }

    @Override
    public void addToRequestQueue() {
        if (Config.HARDCODED_ENABLE) {
            onSuccess(getFromAssetsFolder
                    ("loginresponse.json", LoginResponse.class));
        } else {
            super.addToRequestQueue();
        }
    }

    @Override
    public void onError(THException exception) {
        iUserLoginOperation.onUserLoginError(exception);
        new THLoggerUtil().error("ss","ee");
    }


    @Override
    public void onSuccess(Object response) {
        LoginResponse loginResponse = (LoginResponse) response;
        iUserLoginOperation.onUserLoginSuccess(loginResponse);
        new THLoggerUtil().error("ss","ss");
    }

    public interface IUserLoginOperation {
        void onUserLoginSuccess(LoginResponse response);

        void onUserLoginError(THException exception);
    }
}

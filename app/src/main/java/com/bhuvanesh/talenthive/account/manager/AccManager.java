package com.bhuvanesh.talenthive.account.manager;

import com.bhuvanesh.talenthive.account.manager.operation.UserLoginOperation;
import com.bhuvanesh.talenthive.account.model.LoginRequest;
import com.bhuvanesh.talenthive.account.model.LoginResponse;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.manager.WebServiceManager;
import com.bhuvanesh.talenthive.util.THLoggerUtil;
import com.google.gson.Gson;

public class AccManager extends WebServiceManager implements UserLoginOperation.IUserLoginOperation {
    private IUserLoginManager iUserLoginManager;

    public void doAuthenticate(String username, String password, IUserLoginManager listener) {
        iUserLoginManager = listener;
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.username = username;
        loginRequest.password = password;
        String json = new Gson().toJson(loginRequest).toString();
        new THLoggerUtil().debug(AccManager.class.getSimpleName(), json + "\n" + getHeaders());
        UserLoginOperation loginOperation = new UserLoginOperation(getHeaders(), json, this);
        loginOperation.addToRequestQueue();
    }

    @Override
    public void onUserLoginSuccess(LoginResponse response) {
        iUserLoginManager.OnUserLoginSuccess(response);
    }

    @Override
    public void onUserLoginError(
            THException exception) {
        iUserLoginManager.OnUserLoginError(exception);
    }

    public interface IUserLoginManager {
        void OnUserLoginSuccess(LoginResponse response);

        void OnUserLoginError(THException exception);
    }

}

package com.bhuvanesh.talenthive.account.manager;

import com.bhuvanesh.talenthive.account.manager.operation.UserLoginOperation;
import com.bhuvanesh.talenthive.account.model.LoginRequest;
import com.bhuvanesh.talenthive.account.model.LoginResponse;
import com.bhuvanesh.talenthive.account.model.UserDetails;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.manager.WebServiceManager;
import com.bhuvanesh.talenthive.profile.model.Profile;
import com.bhuvanesh.talenthive.util.THLoggerUtil;
import com.google.gson.Gson;

public class AccountManager extends WebServiceManager {

    public interface IUserLoginManager {
        void OnUserLoginSuccess(LoginResponse response);
        void OnUserLoginError(THException exception);
    }
    public interface IUpdateProfileManager {
        void OnUpdateProfileSuccess(UserDetails userdetails);
        void OnUpdateProfileError(THException exception);
    }

    public void upadateProfile(Profile profile,final IUpdateProfileManager profileManager){
        String body=new Gson().toJson(profile);

    }

    public void doAuthenticate(LoginRequest request, final IUserLoginManager listener) {
        String body = new Gson().toJson(request);
        UserLoginOperation operation = new UserLoginOperation(body, getHeaders(), new UserLoginOperation.IUserLoginOperation() {
            @Override
            public void onUserLoginSuccess(LoginResponse response) {
                listener.OnUserLoginSuccess(response);
            }

            @Override
            public void onUserLoginError(THException exception) {
                listener.OnUserLoginError(exception);
            }
        });
        operation.addToRequestQueue();
    }
}

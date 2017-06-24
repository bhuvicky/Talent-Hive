package com.bhuvanesh.talenthive.account.manager;

import com.bhuvanesh.talenthive.account.manager.operation.GoogleLoginOperation;
import com.bhuvanesh.talenthive.account.manager.operation.SocialAuthOperation;
import com.bhuvanesh.talenthive.account.model.GoogleLoginRequest;
import com.bhuvanesh.talenthive.account.model.GoogleLoginResponse;
import com.bhuvanesh.talenthive.account.model.UserDetails;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.manager.WebServiceManager;
import com.bhuvanesh.talenthive.model.Profile;
import com.bhuvanesh.talenthive.model.SocialFriend;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import java.util.List;

public class SocialAuthManager extends WebServiceManager {

    public interface OnFbLoginManager {
        void onFbLoginSuccess(Profile profile);

        void onFbLoginError(THException exception);
    }

    public interface OnGoogleLoginManager {
        void onGoogleLoginSuccess(GoogleLoginResponse response);

        void onGoogleLoginError(THException exception);
    }

    public interface OnGetAppUserFbFriendsManager {
        void OnGetAppUserFbFriendsSuccess(List<SocialFriend> list);

        void OnGetAppUserFbFriendsError(THException exception);
    }

    public UserDetails googleLogin(String googleTokenId, String userName, final OnGoogleLoginManager listener) {

        GoogleLoginRequest request = new GoogleLoginRequest();
        request.googleTokenId = googleTokenId;
        request.userName = userName;
        GoogleLoginOperation loginOperation = new GoogleLoginOperation(getHeaders(), new Gson().toJson(request), new GoogleLoginOperation.IGoogleLoginListener() {
            @Override
            public void onLoginSuccess(GoogleLoginResponse response) {
                listener.onGoogleLoginSuccess(response);
            }

            @Override
            public void onError(THException exception) {
                listener.onGoogleLoginError(exception);
            }
        });
        loginOperation.addToRequestQueue();


        return null;
    }

    public void initFbLogin(LoginButton loginButton, CallbackManager callbackManager, final OnFbLoginManager listener) {
        new SocialAuthOperation().initFbLoginOperation(loginButton, callbackManager, new SocialAuthOperation.OnFbLoginOperation() {
            @Override
            public void onFbLoginSuccess(Profile profile) {
                listener.onFbLoginSuccess(profile);
            }

            @Override
            public void onFbLoginError(THException exception) {
                listener.onFbLoginError(exception);
            }
        });
    }

    public void getAppUserFbFriendList(final OnGetAppUserFbFriendsManager listener) {
        new SocialAuthOperation().getAppUserFbFriendList(new SocialAuthOperation.OnGetAppUserFbFriendsOperation() {
            @Override
            public void OnGetAppUserFbFriendsSuccess(List<SocialFriend> list) {
                listener.OnGetAppUserFbFriendsSuccess(list);
            }

            @Override
            public void OnGetAppUserFbFriendsError(THException exception) {
                listener.OnGetAppUserFbFriendsError(exception);
            }
        });
    }
}

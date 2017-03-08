package com.bhuvanesh.talenthive.account.manager;

import com.bhuvanesh.talenthive.account.manager.operation.SocialAuthOperation;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.model.Profile;
import com.bhuvanesh.talenthive.model.SocialFriend;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import java.util.List;

public class SocialAuthManager {

    public interface OnFbLoginManager {
        void onFbLoginSuccess(Profile profile);
        void onFbLoginError(THException exception);
    }

    public interface OnGetAppUserFbFriendsManager {
        void OnGetAppUserFbFriendsSuccess(List<SocialFriend> list);
        void OnGetAppUserFbFriendsError(THException exception);
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

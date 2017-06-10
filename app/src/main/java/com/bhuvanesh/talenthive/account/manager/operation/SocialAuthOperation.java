package com.bhuvanesh.talenthive.account.manager.operation;


import android.os.Bundle;

import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.model.Profile;
import com.bhuvanesh.talenthive.model.SocialFriend;
import com.bhuvanesh.talenthive.profile.model.UserDetails;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class SocialAuthOperation {

    public interface OnFbLoginOperation {
        void onFbLoginSuccess(Profile profile);
        void onFbLoginError(THException exception);
    }

    public interface OnGetAppUserFbFriendsOperation {
        void OnGetAppUserFbFriendsSuccess(List<SocialFriend> list);
        void OnGetAppUserFbFriendsError(THException exception);
    }

    public void initFbLoginOperation(LoginButton loginButton, CallbackManager callbackManager, final OnFbLoginOperation listener) {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Profile profile = new Profile();
                        profile.user=new UserDetails();
                        profile.user.accountId = object.optString("id");
                        profile.user.loginType = 2;
                        String name = object.optString("first_name") + " " + object.optString("last_name");
                        /*profile.firstName = object.optString("first_name");
                        profile.lastName = object.optString("last_name");*/
                        profile.user.name = object.optString("name");
                        if (object.has("picture")) {
                            try {
                                profile.user.profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            listener.onFbLoginSuccess(profile);

                        }

                    }
                }
                );
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email,first_name,middle_name,last_name,friends");
                request.setParameters(parameters);
                request.executeAsync();

            }
               @Override
           public void onCancel() {
                listener.onFbLoginError(new THException("unknown"));
            }

            @Override
            public void onError(FacebookException error) {
                listener.onFbLoginError(new THException(error.getMessage()));
            }


    });
    }

    public void getAppUserFbFriendList(final OnGetAppUserFbFriendsOperation listener) {
        GraphRequest request = GraphRequest.newMyFriendsRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONArrayCallback() {
            @Override
            public void onCompleted(JSONArray objects, GraphResponse response) {
                List<SocialFriend> socialFriendList = null;
                try {
                    if (response.getConnection().getResponseCode() == HttpsURLConnection.HTTP_OK) {
                        socialFriendList = new Gson().fromJson(objects.toString(), new TypeToken<List<SocialFriend>>() {}.getType());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (socialFriendList != null)
                    listener.OnGetAppUserFbFriendsSuccess(socialFriendList);
                else
                    listener.OnGetAppUserFbFriendsError(new THException("Error"));
            }
        });
        request.executeAsync();
    }
}

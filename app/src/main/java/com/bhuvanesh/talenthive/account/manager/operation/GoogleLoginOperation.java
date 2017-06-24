package com.bhuvanesh.talenthive.account.manager.operation;

import com.android.volley.Request;
import com.bhuvanesh.talenthive.account.model.GoogleLoginResponse;
import com.bhuvanesh.talenthive.constant.URLConstant;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.manager.operation.WebServiceOperation;



import java.util.Map;

/**
 * Created by Karthikeyan on 20-06-2017.
 */

public class GoogleLoginOperation extends WebServiceOperation {

    public interface IGoogleLoginListener{
        void onLoginSuccess(GoogleLoginResponse response);
        void onError(THException exception);
    }

    private IGoogleLoginListener googleLoginListener;

    public GoogleLoginOperation( Map<String, String> header, String body,IGoogleLoginListener listener) {
        super(URLConstant.GOOGLE_LOGIN_URI, Request.Method.POST, header, body,GoogleLoginResponse.class, GoogleLoginOperation.class.getSimpleName());
        googleLoginListener=listener;
    }

    @Override
    public void addToRequestQueue() {
        super.addToRequestQueue();
    }

    @Override
    public void onError(THException exception) {
          googleLoginListener.onError(exception);
    }

    @Override
    public void onSuccess(Object response) {
         googleLoginListener.onLoginSuccess((GoogleLoginResponse) response);
    }
}

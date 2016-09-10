package com.bhuvanesh.talenthive.manager.operation;


import android.content.res.AssetManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.bhuvanesh.talenthive.BaseResponse;
import com.bhuvanesh.talenthive.THApplication;
import com.bhuvanesh.talenthive.constant.URLConstant;
import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.util.ConnectionUtil;
import com.bhuvanesh.talenthive.util.THLoggerUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.Map;

public abstract class WebServiceOperation implements Response.Listener, Response.ErrorListener {

    public abstract void onSuccess(Object response);

    public abstract void onError(THException exception);

    protected Class mClazz;
    protected Type mClassType;
    protected String mTag;
    protected WebServiceRequest mWebServiceRequest;

    protected final int TIME_OUT_CONNECTION = 5000;

    //Constructors for POST
    protected WebServiceOperation(String uri, int method, Map<String, String> header, String body, Class clazz, String tag) {
        this(uri, method, header, body, clazz, null, tag);
    }

    protected WebServiceOperation(String uri, int method, Map<String, String> header, String body, Type type, String tag) {
        this(uri, method, header, body, null, type, tag);
    }

    //Constructors for GET
    protected WebServiceOperation(String uri, int method, Map<String, String> header, Class clazz, String tag) {
        this(uri, method, header, null, clazz, null, tag);
    }

    protected WebServiceOperation(String uri, int method, Map<String, String> header, Type type, String tag) {
        this(uri, method, header, null, null, type, tag);
    }

    protected WebServiceOperation(String uri, int method, Map<String, String> header, String body, Class clazz, Type type, String tag) {
        mClazz = clazz;
        mClassType = type;
        mTag = tag;
        createRequest(uri, method, header, body);
    }

    private void createRequest(String uri, int method, Map<String, String> headers, String body) {
        mWebServiceRequest = new WebServiceRequest(URLConstant.BASE_SERVER_URL + uri, method, headers, body, this, this);
        RetryPolicy policy = new DefaultRetryPolicy(TIME_OUT_CONNECTION, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mWebServiceRequest.setRetryPolicy(policy);
    }

    public void addToRequestQueue() {
        if (ConnectionUtil.isOnline(THApplication.getInstance())) {
            THLoggerUtil.debug(this, "Network Online");
            mWebServiceRequest.setTag(mTag);
            THApplication.getInstance().getRequestQueue().add(mWebServiceRequest);
        } else {
            onError(new THException("Network is not Available"));
            THLoggerUtil.debug(this, "Network Offline");
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        onError(new THException(error.networkResponse != null ? error.networkResponse.statusCode : 401));
    }

    @Override
    public void onResponse(Object response) {
        Object object;

        try {
            if (mClassType != null) {
                object = new Gson().fromJson((String) response, mClassType);
            } else {
                object = new Gson().fromJson((String) response, mClazz);
            }

            if (object != null) {
                int statusCode = HttpURLConnection.HTTP_OK;
                String statusMessage = "";
                if (object instanceof BaseResponse) {
                    BaseResponse baseResponse = (BaseResponse) object;
                    statusCode = baseResponse.statusCode;
                    statusMessage = baseResponse.message;
                }

                if (statusCode == HttpURLConnection.HTTP_OK || statusCode == HttpURLConnection.HTTP_CREATED) {
                    onSuccess(object);
                } else {
                    onError(new THException(statusCode, statusMessage));
                }
            } else {
                onError(new THException(401));
            }
        } catch (JsonSyntaxException e) {
            THLoggerUtil.error(this, "Json syntax error: " + e.getMessage());
        }

    }

    protected <T> T getFromAssetsFolder(String fileName, Type type) {
        return getFromAssetsFolder(fileName, null, type);
    }

    protected <T> T getFromAssetsFolder(String filename, Class<T> clazz) {
        return getFromAssetsFolder(filename, clazz, null);
    }

    private <T> T getFromAssetsFolder(String filename, Class<T> clazz, Type type) {
        AssetManager manager = THApplication.getInstance().getAssets();
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();
        T object = null;

        try {
            reader = new BufferedReader(new InputStreamReader(manager.open(filename)));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            if (type != null) {
                object = new Gson().fromJson(response.toString(), type);
            } else {
                object = new Gson().fromJson(response.toString(), clazz);
            }
        } catch (IOException | JsonSyntaxException e) {
            THLoggerUtil.error(this, e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return object;
    }
}

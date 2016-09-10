package com.bhuvanesh.talenthive.manager.operation;


import android.content.res.AssetManager;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.bhuvanesh.talenthive.BaseResponse;
import com.bhuvanesh.talenthive.Config;
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

    public static final String TAG = WebServiceOperation.class.getSimpleName();

    protected final int TIME_OUT_CONNECTION = 10000;//

    protected String mTag;
    protected Class mClazz;
    protected Type mClazzType;
    protected WebServiceRequest mWebServiceRequest;

    protected WebServiceOperation(String uri, int method, Map<String, String> header, Class clazz, String tag) {
        this(uri, method, header, null, clazz, null, tag);
    }

    protected WebServiceOperation(String uri, int method, Map<String, String> header, Type type, String tag) {
        this(uri, method, header, null, null, type, tag);
    }

    protected WebServiceOperation(String uri, int method, Map<String, String> header, String body, Class clazz, String tag) {
        this(uri, method, header, body, clazz, null, tag);
    }

    protected WebServiceOperation(String uri, int method, Map<String, String> header, String body, Type type, String tag) {
        this(uri, method, header, body, null, type, tag);
    }

    protected WebServiceOperation(String uri, int method, Map<String, String> header, String body, Class clazz, Type type, String tag) {
        mTag = tag;
        mClazz = clazz;
        mClazzType = type;

        setRequest(uri, method, header, body);
    }

    protected void setRequest(String uri, int method, Map<String, String> header, String body) {
        mWebServiceRequest =
                new WebServiceRequest(Config.LOCAL ? URLConstant.BASE_LOCAL_URL + uri
                        : URLConstant.BASE_SERVER_URL + uri, method, header, body, this, this);
        RetryPolicy policy = new DefaultRetryPolicy(TIME_OUT_CONNECTION, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mWebServiceRequest.setRetryPolicy(policy);
    }

    /**
     * This method will check network connection before adding the request to queue for
     * network operation.
     */
    protected void addToRequestQueue() {
        if (ConnectionUtil.isOnline(THApplication.getInstance().getApplicationContext())){
            new THLoggerUtil().debug(this, "Network Online");
            mWebServiceRequest.setTag(TextUtils.isEmpty(mTag) ? WebServiceOperation.TAG : mTag);
            THApplication.getInstance().getRequestQueue().add(mWebServiceRequest);
        } else {
            onError(new THException("Network is not available"));
            new THLoggerUtil().debug(this, "Network offline");
        }
    }

    protected <T> T getFromAssetsFolder(String filename, Type type) {
        return getFromAssetsFolder(filename, null, type);
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
            reader = new BufferedReader(
                    new InputStreamReader(manager.open(filename)));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                response.append(mLine);
            }
            if (type != null) {
                object = new Gson().fromJson(response.toString(), type);
            } else {
                object = new Gson().fromJson(response.toString(), clazz);
            }
        } catch (IOException e) {
            //log the exception
            new THLoggerUtil().debug(this, e.getMessage());
        } catch (JsonSyntaxException e) {
            //log the exception
            new THLoggerUtil().debug(this, e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return object;
    }

    /**
     * This method will clear the request from queue based on tag.
     */
    public void removeFromRequestQueue() {
        THApplication.getInstance().getRequestQueue().cancelAll(TextUtils.isEmpty(mTag) ? WebServiceOperation.TAG : mTag);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        new THLoggerUtil().debug(this, "Network error:=" + error != null ? "error" : error.getMessage());
        onError(new THException(error.networkResponse != null ? error.networkResponse.statusCode : 401));
    }

    /**
     * Abstract method for handling network or parsing error
     */
    public abstract void onError(THException exception);

    /**
     * This method will parse the json data based on class
     */
    @Override
    public void onResponse(Object response) {
        Object object = null;

        try {
            if (mClazzType != null) {
                object = new Gson().fromJson(((String) response), mClazzType);
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
                    new THLoggerUtil().error("ss","ee1");
                }
            } else {
                onError(new THException(401));
            }
        } catch (JsonSyntaxException e) {
            new THLoggerUtil().debug(this, "Network error:=" + e.getMessage());
            new THLoggerUtil().error("ss","eew");
            onError(new THException(401));
        }
    }

    /**
     * Abstract method for handling success response
     */
    public abstract void onSuccess(Object response);
}

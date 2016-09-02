package com.bhuvanesh.talenthive.manager.operation;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bhuvanesh.talenthive.exception.THException;

import java.lang.reflect.Type;
import java.util.Map;

public abstract class WebServiceOperation implements Response.Listener, Response.ErrorListener {

    public abstract void onSuccess(Object response);
    public abstract void onError(THException exception);

    protected Class mClazz;
    protected Type mClassType;
    protected String mTag;

    //Constructors for POST
    protected WebServiceOperation(String url, int method, Map<String, String> header, String body, Class clazz, String tag) {
        this(url, method, header, body, clazz, null, tag);
    }

    protected WebServiceOperation(String url, int method, Map<String, String> header, String body, Type type, String tag) {
        this(url, method, header, body, null, type, tag);
    }

    //Constructors for GET
    protected WebServiceOperation(String url, int method, Map<String, String> header, Class clazz, String tag) {
        this(url, method, header, null, clazz, null, tag);
    }

    protected WebServiceOperation(String url, int method, Map<String, String> header, Type type, String tag) {
        this(url, method, header, null, null, type, tag);
    }

    protected WebServiceOperation(String url, int method, Map<String, String> header, String body, Class clazz, Type type, String tag) {
        mClazz = clazz;
        mClassType = type;
        mTag = tag;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(Object response) {

    }
}

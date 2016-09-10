package com.bhuvanesh.talenthive.manager.operation;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.bhuvanesh.talenthive.util.THLoggerUtil;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class WebServiceRequest extends Request {

    private Map<String, String> mHeaders;
    private Response.Listener mResponseListener;

    public WebServiceRequest(String url, int method, Map<String, String> headers, String body, Response.Listener responseListener,
                             Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mHeaders = headers;
        mResponseListener = responseListener;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        THLoggerUtil.debug("status code = ", String.valueOf(response.statusCode));
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(json, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(Object response) {
        mResponseListener.onResponse(response);
    }

    @Override
    public int compareTo(Object another) {
        return 0;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        mHeaders.put("Content-Type", "application/json");
        return mHeaders;
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }
}

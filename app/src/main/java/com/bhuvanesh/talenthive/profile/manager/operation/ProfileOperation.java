package com.bhuvanesh.talenthive.profile.manager.operation;


import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.manager.operation.WebServiceOperation;

import java.util.Map;

public class ProfileOperation extends WebServiceOperation {

    protected ProfileOperation(String uri, int method, Map<String, String> header, String body, Class clazz, String tag) {
        super(uri, method, header, body, clazz, tag);
    }

    @Override
    public void onError(THException exception) {

    }

    @Override
    public void onSuccess(Object response) {

    }
}

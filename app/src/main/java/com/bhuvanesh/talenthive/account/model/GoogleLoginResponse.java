package com.bhuvanesh.talenthive.account.model;

import java.io.Serializable;

/**
 * Created by Karthikeyan on 20-06-2017.
 */

public class GoogleLoginResponse implements Serializable {

    public boolean isSuccess;
    public UserDetails userDetails;
}

package com.bhuvanesh.talenthive.account.model;

//import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("emailId")
    public String username;
    public String password;
}

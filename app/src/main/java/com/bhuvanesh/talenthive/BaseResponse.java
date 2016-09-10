package com.bhuvanesh.talenthive;


import com.google.gson.annotations.SerializedName;

public class BaseResponse {



    @SerializedName("statusMessage")
    public int statusCode;
    public String message;
}

package com.bhuvanesh.talenthive.model;


public class Profile {

    public static final int LOGIN_TYPE_FB = 0;
    public static final int LOGIN_TYPE_GOOGLE = LOGIN_TYPE_FB + 1;
    public static final int LOGIN_TYPE_TWITTER = LOGIN_TYPE_GOOGLE + 1;
    public static final int LOGIN_TYPE_THEMAIL = LOGIN_TYPE_TWITTER + 1;

    public String profileId;
    public int loginType;
    public String accountId;

    public String fbTokenId;
    public String googleTokenId;

    public String firstName;
    public String lastName;
    public String name;
    public String email;
    public String mobileNo;
    public String profilePicUrl;
    public String encodedProfilePic;



}

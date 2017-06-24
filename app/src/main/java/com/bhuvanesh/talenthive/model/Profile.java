package com.bhuvanesh.talenthive.model;


import com.bhuvanesh.talenthive.account.model.UserDetails;

public class Profile {

    public static final int LOGIN_TYPE_FB = 0;
    public static final int LOGIN_TYPE_GOOGLE = LOGIN_TYPE_FB + 1;
    public static final int LOGIN_TYPE_TWITTER = LOGIN_TYPE_GOOGLE + 1;
    public static final int LOGIN_TYPE_IN_APP = LOGIN_TYPE_TWITTER + 1;

    public UserDetails user;
    public String encodedProfilePic;
    public String coverImageUrl;
    public String encodedCoverImage;
    public boolean isFollowedByMe;
    public String bio;
    public String email;
    public String mobileNo;
    public String gender;
}

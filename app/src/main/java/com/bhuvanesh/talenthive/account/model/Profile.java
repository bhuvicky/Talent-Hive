package com.bhuvanesh.talenthive.account.model;

import java.io.Serializable;

/**
 * Created by Karthik on 10-Apr-17.
 */

public class Profile implements Serializable{
    public  String  profileId;
    public int loginType;
    public String accountId;
    public String fbTokenId;
    public String googleTokenId;
    public String profilePicUrl;
    public String encodedProfilePic;
    public String coverImageUrl;
    public String encodedCoverImage;
    public boolean isFollowedByMe;
    public String name;
    public String userName;
    public String bio;
    public String email;
    public String mobileNo;
    public String gender;

}

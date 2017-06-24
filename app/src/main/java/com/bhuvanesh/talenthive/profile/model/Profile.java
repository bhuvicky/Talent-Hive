package com.bhuvanesh.talenthive.profile.model;

import com.bhuvanesh.talenthive.account.model.UserDetails;

import java.io.Serializable;

/**
 * Created by Karthik on 10-Apr-17.
 */

public class Profile implements Serializable{
    public UserDetails user;
    public String encodedProfilePic;
    public String coverImageUrl;
    public String encodedCoverImage;
    public boolean isFollowedByMe;
    public String bio;
    public String email;
    public String mobileNo;
    public String gender;
    public long followersCount;
    public long followingCount;
}

package com.bhuvanesh.talenthive.photography.model;

import com.bhuvanesh.talenthive.account.model.UserDetails;

public class PhotoFeedResponse {
    public String photoID;
    public Photo photo;
    public long lastModifiedTime;
    public long createdTime;
    public UserDetails user;
    public long likeCount;
    public long commentCount;
}

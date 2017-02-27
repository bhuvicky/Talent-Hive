package com.bhuvanesh.talenthive.photography.model;

import java.util.List;

public class PhotoFeedResponse {
    public String profileImageUrl;
    public String name;
    public transient int likeByMe;
    public List<String> likedPeopleList;
    public long noOfComments;
    public Photo photo;


}

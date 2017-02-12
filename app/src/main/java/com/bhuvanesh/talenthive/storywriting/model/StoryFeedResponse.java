package com.bhuvanesh.talenthive.storywriting.model;


import java.util.List;

public class StoryFeedResponse {

    public String profileImageUrl;
    public String name;
    public transient int likeByMe;
    public List<String> likedPeopleList;
    public long noOfComments;

    public Story story;
}

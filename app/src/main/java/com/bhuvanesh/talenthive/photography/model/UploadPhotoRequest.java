package com.bhuvanesh.talenthive.photography.model;

import com.bhuvanesh.talenthive.account.model.UserDetails;

import java.util.List;

/**
 * Created by Karthik on 20-Apr-17.
 */

public class UploadPhotoRequest {
    public long createdTime;
    public long lastModifiedTime;
    public Photo photo;
    public UserDetails user;
    public List<String> likedList;
    public List<Comment> commentList;
    public long likeCount;
    public long commentCount;

}

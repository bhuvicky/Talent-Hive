package com.bhuvanesh.talenthive.photography.model;

import java.util.List;

/**
 * Created by Karthik on 20-Apr-17.
 */

public class UploadPhotoRequest extends Photo{

    public List<String> likedPeopleList;
    public List<Comment> commentsList;
    public String profileID;
    public int loginType;
}

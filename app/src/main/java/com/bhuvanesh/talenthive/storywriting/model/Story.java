package com.bhuvanesh.talenthive.storywriting.model;


import com.bhuvanesh.talenthive.model.Language;

import java.util.List;

public class Story {

    public String storyId;
    public String storyAuthorId;    //user profile id

    public String wrapperImageData;
    public String wrapperImageUrl;

    public Language language;
    public StoryCategory category;
    public String title;
    public String description;
    public String author;
    public List<Chapter> chapterList;
    public long lastModifiedDate;
}

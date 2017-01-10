package com.bhuvanesh.talenthive.storywriting.model;


import com.google.gson.Gson;

public class Chapter {

    public String chapterTitle;
    public String chapterDescription;
    public long lastModifiedDate;

    public transient boolean isDeleted;

    @Override
    public int hashCode() {
        return new Gson().toJson(this).hashCode();
    }
}

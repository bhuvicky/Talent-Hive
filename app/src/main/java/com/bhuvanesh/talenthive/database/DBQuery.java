package com.bhuvanesh.talenthive.database;

/**
 * Created by Bhuvanesh on 12/5/2016.
 */

public interface DBQuery {

    String PROFILE_TABLE_NAME = "profileRecords";
    String TABLE_NAME_STORY = "Story";
    String TABLE_NAME_STORY_FEED = "StoryFeed";

    String CREATE_TABLE_PROFILE_DETAILS = "CREATE TABLE "+ PROFILE_TABLE_NAME +"(_id integer PRIMARY KEY, "+
            "Name varchar, Email varchar, Password varchar, MobileNo varchar, StateName varchar, DistrictName varchar);";
    String GET_PROFILE = "SELECT * FROM " + PROFILE_TABLE_NAME + " WHERE Email = \'%s\' AND Password = \'%s\'";

//    for my story
    String CREATE_TABLE_STORY = "CREATE TABLE "+ TABLE_NAME_STORY + "(_id varchar PRIMARY KEY, WrapperImageUrl varchar, "+
            "Title varchar, Description varchar, LangId long, CategoryId integer, ChapterList varchar, LastModifiedTime long);";
    String GET_STORY_LIST = "SELECT * FROM " + TABLE_NAME_STORY + " ORDER BY LastModifiedTime DESC";

//    for story feed
    String CREATE_TABLE_STORY_FEED = "CREATE TABLE " + TABLE_NAME_STORY_FEED + "(StoryId varchar PRIMARY KEY, ProfileImageUrl varchar, " +
        "Author varchar, LastModifiedTime long, WrapperImageUrl varchar, Title varchar, Description varchar, LangId long, CategoryId integer, " +
        "LikedPeopleList varchar, NoOfComments long);";
    String GET_STORY_FEED = "SELECT * FROM " + TABLE_NAME_STORY_FEED + " ORDER BY LastModifiedTime DESC";
    String DELETE_ALL_STORY_FEED_RECORD = "DELETE FROM " + TABLE_NAME_STORY_FEED;
    String DELETE_OLD_STORY_FEED_RECORDS_WITH_LIMIT = "DELETE FROM " + TABLE_NAME_STORY_FEED + " WHERE rowid IN " +
            "(SELECT rowid FROM " + TABLE_NAME_STORY_FEED + " ORDER BY LastModifiedTime ASC LIMIT \'%d\');";

}

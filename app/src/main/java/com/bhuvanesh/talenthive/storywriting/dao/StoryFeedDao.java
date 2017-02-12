package com.bhuvanesh.talenthive.storywriting.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Message;

import com.bhuvanesh.talenthive.THApplication;
import com.bhuvanesh.talenthive.database.CUDModel;
import com.bhuvanesh.talenthive.database.DBManager;
import com.bhuvanesh.talenthive.database.DBQuery;
import com.bhuvanesh.talenthive.database.Dao;
import com.bhuvanesh.talenthive.model.Language;
import com.bhuvanesh.talenthive.storywriting.model.Story;
import com.bhuvanesh.talenthive.storywriting.model.StoryCategory;
import com.bhuvanesh.talenthive.storywriting.model.StoryFeedResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhuvanesh on 11-02-2017.
 */

public class StoryFeedDao extends Dao {

    private static final String STORY_ID = "StoryId";
    private static final String PROFILE_IMAGE_URL = "ProfileImageUrl";
    private static final String AUTHOR = "Author";
    private static final String WRAPPER_IMAGE_URL = "WrapperImageUrl";
    private static final String TITLE = "Title";
    private static final String DESCRIPTION = "Description";
    private static final String LANG_ID = "LangId";
    private static final String CATEGORY_ID = "CategoryId";
    private static final String LIKED_PEOPLE_LIST = "LikedPeopleList";
    private static final String NO_OF_COMMENTS = "NoOfComments";

    @Override
    public void insert(CUDModel model) {

    }

    @Override
    public void update(CUDModel model) {
        List<StoryFeedResponse> storyFeedList = (List<StoryFeedResponse>) model.object;
        List<ContentValues> valuesList = new ArrayList<>();
        for (StoryFeedResponse item : storyFeedList) {
            ContentValues values = new ContentValues();

            values.put(STORY_ID, item.story.storyId);
            values.put(PROFILE_IMAGE_URL, item.profileImageUrl);
            values.put(AUTHOR, item.story.author);
            values.put(WRAPPER_IMAGE_URL, item.story.wrapperImageUrl);
            values.put(TITLE, item.story.title);
            values.put(DESCRIPTION, item.story.description);
            values.put(LANG_ID, item.story.language.id);
            values.put(CATEGORY_ID, item.story.category.id);
            values.put(LIKED_PEOPLE_LIST, new Gson().toJson(item.likedPeopleList));
            values.put(NO_OF_COMMENTS, item.noOfComments);

            valuesList.add(values);
        }
        DBManager dbManager = new DBManager(THApplication.getInstance());

//        Delete all data before update
        dbManager.executeSQLQuery(DBQuery.DELETE_ALL_STORY_FEED_RECORD);

        long rowId = dbManager.replaceBulk(DBQuery.TABLE_NAME_STORY_FEED, valuesList);

        Message msg = new Message();
        if (rowId > 0) {
            msg.what = HANDLER_MESSAGE_SUCCESS;
        } else {
            msg.what = HANDLER_MESSAGE_ERROR;
        }
        mHandler.handleMessage(msg);
    }

    @Override
    public void delete(CUDModel model) {

    }

    @Override
    public void query(CUDModel model) {
        List<StoryFeedResponse> storyFeedList = null;
        Cursor cursor = new DBManager(THApplication.getInstance()).select(DBQuery.GET_STORY_FEED);

        try {
            while (cursor.moveToNext()) {
                storyFeedList = new ArrayList<>();

                StoryFeedResponse storyFeedItem = new StoryFeedResponse();
                storyFeedItem.story = new Story();
                storyFeedItem.story.storyId = cursor.getString(cursor.getColumnIndex(STORY_ID));
                storyFeedItem.story.author = cursor.getString(cursor.getColumnIndex(AUTHOR));
                storyFeedItem.story.wrapperImageUrl = cursor.getString(cursor.getColumnIndex(WRAPPER_IMAGE_URL));
                storyFeedItem.story.title = cursor.getString(cursor.getColumnIndex(TITLE));
                storyFeedItem.story.language = new Language();
                storyFeedItem.story.language.id = cursor.getLong(cursor.getColumnIndex(LANG_ID));
                storyFeedItem.story.category = new StoryCategory();
                storyFeedItem.story.category.id = cursor.getInt(cursor.getColumnIndex(CATEGORY_ID));
                storyFeedItem.story.description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));

                storyFeedItem.profileImageUrl = cursor.getString(cursor.getColumnIndex(PROFILE_IMAGE_URL));
                storyFeedItem.likedPeopleList = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(LIKED_PEOPLE_LIST)),
                        new TypeToken<List<String>> () {}.getType());
                storyFeedItem.noOfComments = cursor.getLong(cursor.getColumnIndex(NO_OF_COMMENTS));

                storyFeedList.add(storyFeedItem);
            }
        } finally {
            cursor.close();
        }

        Message msg = new Message();
        if (storyFeedList != null) {
            msg.what = HANDLER_MESSAGE_SUCCESS;
            msg.obj = storyFeedList;
        } else {
            msg.what = HANDLER_MESSAGE_ERROR;
        }
        mHandler.handleMessage(msg);
    }
}

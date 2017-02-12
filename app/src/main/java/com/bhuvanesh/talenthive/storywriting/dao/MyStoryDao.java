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
import com.bhuvanesh.talenthive.storywriting.model.Chapter;
import com.bhuvanesh.talenthive.storywriting.model.Story;
import com.bhuvanesh.talenthive.storywriting.model.StoryCategory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedList;
import java.util.List;

public class MyStoryDao extends Dao {

    private static final String STORY_ID = "_id";
    private static final String WRAPPER_IMAGE_URL = "WrapperImageUrl";
    private static final String TITLE = "Title";
    private static final String DESCRIPTION = "Description";
    private static final String LANG_ID = "LangId";
    private static final String CATEGORY_ID = "CategoryId";
    private static final String CHAPTER_LIST = "ChapterList";
    private static final String LAST_MODIFIED_TIME = "LastModifiedTime";

    @Override
    public void insert(CUDModel model) {

    }

    @Override
    public void update(CUDModel model) {
        Story story = (Story) model.object;
        ContentValues values = new ContentValues();
        values.put(STORY_ID, story.storyId);
        values.put(WRAPPER_IMAGE_URL, story.wrapperImageUrl);
        values.put(TITLE, story.title);
        values.put(DESCRIPTION, story.description);
        values.put(LANG_ID, story.language.id);
        values.put(CATEGORY_ID, story.category.id);
        values.put(CHAPTER_LIST, new Gson().toJson(story.chapterList, new TypeToken<List<Chapter>> () {}.getType()));
        values.put(LAST_MODIFIED_TIME, story.lastModifiedDate);

        long rowId = new DBManager(THApplication.getInstance()).replace(DBQuery.TABLE_NAME_STORY, values);
        System.out.println("row id = " + rowId);

        Message msg = new Message();
        if (rowId > 0) {
            msg.what = HANDLER_MESSAGE_SUCCESS;
            msg.obj = rowId;
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
        Cursor cursor = new DBManager(THApplication.getInstance()).select(DBQuery.GET_STORY_LIST);
        List<Story> storyList = new LinkedList<>();
        System.out.println("after fetch count = " + cursor.getCount());
        //Nothing inside throw any exception. just for naming conv try finally used
        try {
            while (cursor.moveToNext()) {
                Story story = new Story();
                story.storyId = cursor.getString(cursor.getColumnIndex(STORY_ID));
                story.wrapperImageUrl = cursor.getString(cursor.getColumnIndex(WRAPPER_IMAGE_URL));
                story.title = cursor.getString(cursor.getColumnIndex(TITLE));
                story.description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                story.language = new Language();
                story.language.id = cursor.getLong(cursor.getColumnIndex(LANG_ID));
                story.category = new StoryCategory();
                story.category.id = cursor.getInt(cursor.getColumnIndex(CATEGORY_ID));
                story.chapterList = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(CHAPTER_LIST)),
                        new TypeToken<List<Chapter>>() {}.getType());
                story.lastModifiedDate = cursor.getLong(cursor.getColumnIndex(LAST_MODIFIED_TIME));
                storyList.add(story);
            }
        } finally {
            cursor.close();
            cursor = null;
        }

        Message msg = new Message();
        if (storyList.size() > 0) {
            msg.what = HANDLER_MESSAGE_SUCCESS;
            msg.obj = storyList;
        } else {
            msg.what = HANDLER_MESSAGE_ERROR;
        }
        mHandler.handleMessage(msg);
    }
}

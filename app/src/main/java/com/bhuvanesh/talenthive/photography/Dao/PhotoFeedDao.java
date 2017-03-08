package com.bhuvanesh.talenthive.photography.Dao;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Message;

import com.bhuvanesh.talenthive.THApplication;
import com.bhuvanesh.talenthive.database.CUDModel;
import com.bhuvanesh.talenthive.database.DBManager;
import com.bhuvanesh.talenthive.database.DBQuery;
import com.bhuvanesh.talenthive.database.Dao;
import com.bhuvanesh.talenthive.photography.model.Photo;
import com.bhuvanesh.talenthive.photography.model.PhotoFeedResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PhotoFeedDao extends Dao {

    private static final String PHOTO_ID = "PhotoId";
    private static final String PROFILE_IMAGE_URL = "ProfileImageUrl";
    private static final String PHOTO_IMAGE_URL = "PhotoImageUrl";
    private static final String DESCRIPTION = "Description";
    private static final String LIKED_PEOPLE_LIST = "LikedPeopleList";
    private static final String NO_OF_COMMENTS = "NoOfComments";
    private static final int MAX_RECORD_TO_UPDATE = 10;

    @Override
    public void insert(CUDModel model) {

    }

    @Override
    public void update(CUDModel model) {
        List<PhotoFeedResponse> photoFeedList = (List<PhotoFeedResponse>) model.object;
        List<ContentValues> valuesList = new ArrayList<>();
        int listSize = photoFeedList.size() >= MAX_RECORD_TO_UPDATE ? MAX_RECORD_TO_UPDATE : photoFeedList.size();
        for (int i = 0; i < listSize; i++) {
            PhotoFeedResponse item = photoFeedList.get(i);
            ContentValues values = new ContentValues();

            values.put(PHOTO_ID, item.photo.photoID);
            values.put(PROFILE_IMAGE_URL, item.profileImageUrl);
            values.put(PHOTO_IMAGE_URL, item.photo.photoURL);
            values.put(DESCRIPTION, item.photo.titleDescription);
            values.put(LIKED_PEOPLE_LIST, new Gson().toJson(item.likedPeopleList));
            values.put(NO_OF_COMMENTS, item.noOfComments);
            valuesList.add(values);
        }
        DBManager dbManager = new DBManager(THApplication.getInstance());
        long rowId = 0L;

//        Delete all data before update
        if (photoFeedList.size() >= MAX_RECORD_TO_UPDATE) {
            dbManager.executeSQLQuery(DBQuery.DELETE_ALL_PHOTO_FEED_RECORD);
            rowId = dbManager.replaceBulk(DBQuery.TABLE_NAME_PHOTO_FEED, valuesList);
        } else {
            long tableRowCount = dbManager.getTableRowCount(DBQuery.TABLE_NAME_PHOTO_FEED);
            if (tableRowCount + photoFeedList.size() <= MAX_RECORD_TO_UPDATE)
                rowId = dbManager.replaceBulk(DBQuery.TABLE_NAME_PHOTO_FEED, valuesList);
            else {
                int limitSize = (int) (tableRowCount + photoFeedList.size() - MAX_RECORD_TO_UPDATE);
                dbManager.executeSQLQuery(String.format(Locale.getDefault(), DBQuery.DELETE_OLD_PHOTO_FEED_RECORDS_WITH_LIMIT, limitSize));
                rowId = dbManager.replaceBulk(DBQuery.TABLE_NAME_PHOTO_FEED, valuesList);
            }
        }

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
        List<PhotoFeedResponse> photoFeedList = null;
        Cursor cursor = new DBManager(THApplication.getInstance()).select(DBQuery.GET_PHOTO_FEED);


        try {
            while (cursor.moveToNext()) {
                photoFeedList = new ArrayList<>();

                PhotoFeedResponse PhotoFeedItem = new PhotoFeedResponse();
                PhotoFeedItem.photo = new Photo();
                PhotoFeedItem.photo.photoID = cursor.getString(cursor.getColumnIndex(PHOTO_ID));
                PhotoFeedItem.photo.photoURL = cursor.getString(cursor.getColumnIndex(PHOTO_IMAGE_URL));
                PhotoFeedItem.photo.titleDescription = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                PhotoFeedItem.profileImageUrl = cursor.getString(cursor.getColumnIndex(PROFILE_IMAGE_URL));
                PhotoFeedItem.likedPeopleList = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(LIKED_PEOPLE_LIST)),
                        new TypeToken<List<String>>() {}.getType());
                PhotoFeedItem.noOfComments = cursor.getLong(cursor.getColumnIndex(NO_OF_COMMENTS));
                photoFeedList.add(PhotoFeedItem);
            }
        } finally {
            cursor.close();
        }

        Message msg = new Message();
        if (photoFeedList != null) {
            msg.what = HANDLER_MESSAGE_SUCCESS;
            msg.obj = photoFeedList;
        } else {
            msg.what = HANDLER_MESSAGE_ERROR;
        }
        mHandler.handleMessage(msg);
    }



}

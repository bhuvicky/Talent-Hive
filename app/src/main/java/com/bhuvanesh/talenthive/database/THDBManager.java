package com.bhuvanesh.talenthive.database;

import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.photography.Dao.PhotoFeedDao;
import com.bhuvanesh.talenthive.photography.model.PhotoFeedResponse;
import com.bhuvanesh.talenthive.storywriting.dao.MyStoryDao;
import com.bhuvanesh.talenthive.storywriting.dao.StoryFeedDao;
import com.bhuvanesh.talenthive.storywriting.model.Story;
import com.bhuvanesh.talenthive.storywriting.model.StoryFeedResponse;

import java.util.List;


public class THDBManager {

    private OnTHDBMangerListener mOnTHDBMangerListener;

    public interface OnTHDBMangerListener<T> {
        void onTHDBSuccessful(T object);
        void onTHDBError(THException error);
    }

    public void setmOnTHDBMangerListener(OnTHDBMangerListener listener) {
        mOnTHDBMangerListener = listener;
    }

    public void updateStory(Story story) {
        Dao dao = new MyStoryDao();
        dao.setOnDaoOperationListener(new Dao.OnDaoOperationListener() {
            @Override
            public void onSuccessfulOperation(Object obj) {
                System.out.println("db manager op success");
            }

            @Override
            public void onErrorOperation(THException exception) {
                System.out.println("db manager op fail");
            }
        });
        CUDModel model = new CUDModel();
        model.object = story;
        dao.execute(Dao.CUDOperationType.UPDATION, model);
    }

    public void getStoryList() {
        Dao dao = new MyStoryDao();
        dao.setOnDaoOperationListener(new Dao.OnDaoOperationListener<List<Story>>() {

            @Override
            public void onSuccessfulOperation(List<Story> obj) {
                mOnTHDBMangerListener.onTHDBSuccessful(obj);
            }

            @Override
            public void onErrorOperation(THException exception) {
                mOnTHDBMangerListener.onTHDBError(exception);
            }
        });
        dao.execute(Dao.CUDOperationType.QUERY, new CUDModel());
    }

    public void updateStoryFeed(List<StoryFeedResponse> storyFeedList) {
        Dao dao = new StoryFeedDao();
        dao.setOnDaoOperationListener(new Dao.OnDaoOperationListener() {
            @Override
            public void onSuccessfulOperation(Object obj) {

            }

            @Override
            public void onErrorOperation(THException exception) {

            }
        });
        CUDModel model = new CUDModel();
        model.object = storyFeedList;
        dao.execute(Dao.CUDOperationType.UPDATION, model);
    }

    public void getStoryFeedList() {
        Dao dao = new StoryFeedDao();
        dao.setOnDaoOperationListener(new Dao.OnDaoOperationListener() {
            @Override
            public void onSuccessfulOperation(Object obj) {
                mOnTHDBMangerListener.onTHDBSuccessful(obj);
            }

            @Override
            public void onErrorOperation(THException exception) {
                mOnTHDBMangerListener.onTHDBError(exception);

            }
        });
        dao.execute(Dao.CUDOperationType.QUERY, new CUDModel());
    }
    public void updatePhotoFeed(List<PhotoFeedResponse> photoFeedList) {
        Dao dao = new PhotoFeedDao();
        dao.setOnDaoOperationListener(new Dao.OnDaoOperationListener() {
            @Override
            public void onSuccessfulOperation(Object obj) {

            }

            @Override
            public void onErrorOperation(THException exception) {

            }
        });
        CUDModel model = new CUDModel();
        model.object = photoFeedList;
        dao.execute(Dao.CUDOperationType.UPDATION, model);
    }

    public void getPhotoFeedList() {
        Dao dao = new PhotoFeedDao();

        dao.setOnDaoOperationListener(new Dao.OnDaoOperationListener() {
            @Override
            public void onSuccessfulOperation(Object obj) {
                mOnTHDBMangerListener.onTHDBSuccessful(obj);
            }

            @Override
            public void onErrorOperation(THException exception) {
                mOnTHDBMangerListener.onTHDBError(exception);

            }
        });

        dao.execute(Dao.CUDOperationType.QUERY, new CUDModel());
    }

}

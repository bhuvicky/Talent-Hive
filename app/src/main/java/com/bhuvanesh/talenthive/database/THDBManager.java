package com.bhuvanesh.talenthive.database;

import com.bhuvanesh.talenthive.exception.THException;
import com.bhuvanesh.talenthive.storywriting.dao.StoryDao;
import com.bhuvanesh.talenthive.storywriting.model.Story;

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
        Dao dao = new StoryDao();
        dao.setOnDaoOperation(new Dao.OnDaoOperationListener() {
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
        Dao dao = new StoryDao();
        dao.setOnDaoOperation(new Dao.OnDaoOperationListener<List<Story>>() {

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
}

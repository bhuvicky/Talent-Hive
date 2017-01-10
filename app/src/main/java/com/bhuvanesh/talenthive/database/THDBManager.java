package com.bhuvanesh.talenthive.database;

import com.bhuvanesh.talenthive.exception.THException;


public class THDBManager {

    private OnTHDBMangerListener mOnTHDBMangerListener;

    public interface OnTHDBMangerListener<T> {
        void onTHDBSuccessful(T object);
        void onTHDBError(THException error);
    }

    public void setmOnTHDBMangerListener(OnTHDBMangerListener listener) {
        mOnTHDBMangerListener = listener;
    }
}

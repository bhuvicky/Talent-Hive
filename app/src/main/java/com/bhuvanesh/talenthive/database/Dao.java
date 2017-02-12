package com.bhuvanesh.talenthive.database;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.bhuvanesh.talenthive.exception.THException;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class Dao {

    protected enum CUDOperationType {
        INSERTION, UPDATION, DELETION, QUERY
    }

    public interface OnDaoOperationListener<T> {
        void onSuccessfulOperation(T obj);
        void onErrorOperation(THException exception);
    }

    private OnDaoOperationListener mOnDaoOperation;
    protected static final int HANDLER_MESSAGE_SUCCESS = 1;
    protected static final int HANDLER_MESSAGE_ERROR = 0;
    private final ThreadPoolExecutor mThreadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    public void setOnDaoOperationListener(OnDaoOperationListener listener) {
        mOnDaoOperation = listener;
    }

    protected Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(final Message msg) {
            post(new Runnable() {
                @Override
                public void run() {
                    if (msg.what == HANDLER_MESSAGE_SUCCESS)
                        mOnDaoOperation.onSuccessfulOperation(msg.obj);
                    else
                        mOnDaoOperation.onErrorOperation(new THException(404, "Error in DB"));
                }
            });
        }
    };

    public void execute(final CUDOperationType type, final CUDModel model) {
        mThreadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                switch (type) {
                    case INSERTION: insert(model);  break;
                    case UPDATION:  update(model);  break;
                    case DELETION:  delete(model);  break;
                    case QUERY:     query(model);   break;
                }
            }
        });
    }

    public abstract void insert(CUDModel model);
    public abstract void update(CUDModel model);
    public abstract void delete(CUDModel model);
    public abstract void query(CUDModel model);
}

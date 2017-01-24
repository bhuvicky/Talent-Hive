package com.bhuvanesh.talenthive;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bhuvanesh.talenthive.util.THLoggerUtil;

public class BaseFragment extends DialogFragment {

    protected OnSubmitClickListener mOnSubmitClickListener;
    private BaseActivity mActivity;
    private boolean mVisibleToUser;

    private android.support.v7.app.ActionBar mActionBar;

    private int mTitleResourceId;

    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (BaseActivity) getActivity();
        mActionBar = mActivity.getSupportActionBar();

        setCancelable(false);

        if (mActionBar != null) {
            mActionBar.setTitle(getResources().getString(mTitleResourceId));
        }

        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void showToastMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }


    public void setOnSubmitClickListener(OnSubmitClickListener listener) {
        mOnSubmitClickListener = listener;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mVisibleToUser = isVisibleToUser;
    }

    protected boolean isVisibleToUser() {
        return mVisibleToUser;
    }

    protected void setTitle(@StringRes int resId) {
        mTitleResourceId = resId;
        setTitle(getResources().getString(mTitleResourceId));
    }

    protected void setTitle(CharSequence title) {
        if (mActionBar != null) {
            mActionBar.setTitle(title);
        }
    }


    protected void showProgressDialog(int resMessage) {
        showProgressDialog("", this.getResources().getString(resMessage));
    }

    protected void showProgressDialog(String message) {
        showProgressDialog("", message);
    }

    protected void showProgressDialog(String title, String message) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showProgressDialog(title, message);
        }

    }

    protected void dismissProgressDialog() {
        ((BaseActivity) getActivity()).dismissProgressDialog();
    }

    public void onResume() {
        super.onResume();
//        UIUtils.closeKeyBoard(getContext(), getView());
    }

    public void replace(int containerId, BaseFragment fragment) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).replace(containerId, fragment);
        } else {
            THLoggerUtil.debug(fragment.getTag(), "Activity is null!!!!!");
        }
    }
    public void replace(int containerId, BaseFragment fragment,String dir) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).replace(containerId, fragment);
        } else {
            THLoggerUtil.debug(fragment.getTag(), "Activity is null!!!!!");
        }
    }

    public void replace(int containerId, DialogFragment fragment) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).replace(containerId, fragment);
        } else {
            THLoggerUtil.debug(fragment.getTag(), "Activity is null!!!!!");

        }
    }

    public void pop() {
        if (getActivity() != null) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            THLoggerUtil.debug(BaseFragment.class.getSimpleName(), "BackStackEntryCount: " + fm.getBackStackEntryCount());
            fm.popBackStack();
            THLoggerUtil.debug(BaseFragment.class.getSimpleName(), "BackStackEntryCount: " + fm.getBackStackEntryCount());
        }
    }


    public void popAllFragmentsUpto(int index) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).popAllFragmentsUpto(index);
        }
    }

    public void replaceChildFragment(int containerId, BaseFragment fragment) {
        if (getActivity() != null) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(containerId, fragment);
            transaction.commit();
        } else {
            THLoggerUtil.debug(fragment.getTag(), "Activity is null!!!!!");
        }
    }

    public void popChildFragment(BaseFragment fragment) {
        if (fragment != null) {
            if (getActivity() != null) {
                THLoggerUtil.debug("hh","ssdd");
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
            } else {
                THLoggerUtil.debug(fragment.getTag(), "Activity is null!!!!!");
            }
        }
    }

    protected void onHomeButtonPress() {
    }

    protected void onBackPress() {
        pop();
    }

    public boolean isDialog() {
        if (getDialog() != null) return true;
        else return false;
    }
}

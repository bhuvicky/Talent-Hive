package com.bhuvanesh.talenthive.dashboard.fragment;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import com.bhuvanesh.talenthive.R;

public class TalentListFragment extends BottomSheetDialogFragment {

    private Dialog mDialog;
    private int mPeekHeight = 1000;

    public static TalentListFragment newInstance() {
        return new TalentListFragment();
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View view = View.inflate(getActivity(), R.layout.fragment_talent_list, null);
        dialog.setContentView(view);
        mDialog = dialog;

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(bottomSheetCallback);
            ((BottomSheetBehavior) behavior).setPeekHeight(mPeekHeight);
        }
    }

    BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                if (mDialog != null) {
                    mDialog.dismiss();
                }
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };
}

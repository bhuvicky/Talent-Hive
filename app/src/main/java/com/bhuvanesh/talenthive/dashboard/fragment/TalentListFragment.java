package com.bhuvanesh.talenthive.dashboard.fragment;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.TextView;

import com.bhuvanesh.talenthive.OnSubmitClickListener;
import com.bhuvanesh.talenthive.R;

public class TalentListFragment extends BottomSheetDialogFragment {

    private Dialog mDialog;
    private OnSubmitClickListener mOnSubmitClickListener;
    private int mPeekHeight = 1000;

    public static TalentListFragment newInstance() {
        return new TalentListFragment();
    }

    public void setOnSubmitClickListener(OnSubmitClickListener listener) {
        mOnSubmitClickListener = listener;
    }

    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View view = View.inflate(getActivity(), R.layout.fragment_talent_list, null);
        TextView textViewPhoto = (TextView) view.findViewById(R.id.textview_photo);
        TextView textViewStory = (TextView) view.findViewById(R.id.textview_story);
        TextView textViewDance = (TextView) view.findViewById(R.id.textview_dance);

        textViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (mOnSubmitClickListener != null)
                    mOnSubmitClickListener.onSubmit(DashboardFragment.TALENT_TYPE_PHOTO);
            }
        });

        textViewStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (mOnSubmitClickListener != null)
                    mOnSubmitClickListener.onSubmit(DashboardFragment.TALENT_TYPE_STORY);
            }
        });

        textViewDance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (mOnSubmitClickListener != null)
                    mOnSubmitClickListener.onSubmit(DashboardFragment.TALENT_TYPE_DANCE);
            }
        });

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

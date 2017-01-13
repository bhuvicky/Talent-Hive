package com.bhuvanesh.talenthive.storywriting.fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bhuvanesh.talenthive.BaseActivity;
import com.bhuvanesh.talenthive.BaseFragment;
import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.storywriting.model.Chapter;
import com.bhuvanesh.talenthive.util.EditTextUndoRedoUtil;

public class EditStoryChapterFragment extends BaseFragment {

    public interface ChapterEventListener {
        void onDeleteEvent(Chapter delChapter);

        void onSaveEvent(Chapter saveChapter);
    }

    private EditText mEditTextChapterTitle, mEditTextChapterContent;
    private EditTextUndoRedoUtil mEditTextUndoRedoContent;
    private TextWatcher mTextWatcher;
    private RelativeLayout mRelativeLayout;

    private ChapterEventListener mChapterEventListener;
    private Chapter mChapter;

    private int mPrevHashCode;
    private String mPrevTitle, mPrevDescription;

    public void setChapterEventListener(ChapterEventListener listener) {
        mChapterEventListener = listener;
    }

    public static EditStoryChapterFragment newInstance(Chapter chapter) {
        EditStoryChapterFragment fragment = new EditStoryChapterFragment();
        fragment.mChapter = chapter != null ? chapter : new Chapter();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.edit_story);
        View view = inflater./*cloneInContext(contextThemeWrapper).*/inflate(R.layout.fragment_edit_story_chapter, container, false);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.title_edit_chapter);

        mRelativeLayout = (RelativeLayout) view.findViewById(R.id.rlayout_container);
        final ImageView imageViewUndo = (ImageView) view.findViewById(R.id.imageview_undo);
        final ImageView imageViewRedo = (ImageView) view.findViewById(R.id.imageview_redo);

        mEditTextChapterTitle = (EditText) view.findViewById(R.id.edittext_chapter_title);

        mEditTextChapterContent = (EditText) view.findViewById(R.id.edittext_chapter_content);
        mEditTextUndoRedoContent = new EditTextUndoRedoUtil(mEditTextChapterContent);
        mEditTextChapterContent.addTextChangedListener(mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEditTextUndoRedoContent.getCanUndo())
                    imageViewUndo.setImageResource(R.drawable.ic_undo_orange_24dp);
            }
        });

        imageViewUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditTextUndoRedoContent.getCanUndo()) {
                    mEditTextUndoRedoContent.undo();
                    if (!mEditTextUndoRedoContent.getCanUndo())
                        imageViewUndo.setImageResource(R.drawable.ic_undo_grey_24dp);
                } else {
                    imageViewUndo.setImageResource(R.drawable.ic_undo_grey_24dp);
                }
                if (mEditTextUndoRedoContent.getCanRedo())
                    imageViewRedo.setImageResource(R.drawable.ic_redo_orange_24dp);
            }
        });

        imageViewRedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditTextUndoRedoContent.getCanRedo()) {
                    mEditTextUndoRedoContent.redo();
                    if (!mEditTextUndoRedoContent.getCanRedo())
                        imageViewRedo.setImageResource(R.drawable.ic_redo_grey_24dp);
                } else {
                    imageViewRedo.setImageResource(R.drawable.ic_redo_grey_24dp);
                }
                if (mEditTextUndoRedoContent.getCanUndo())
                    imageViewUndo.setImageResource(R.drawable.ic_undo_orange_24dp);
            }
        });
        setChapterDetails();
        return view;
    }

    private void setChapterDetails() {
        mEditTextChapterContent.removeTextChangedListener(mTextWatcher);
        mEditTextChapterTitle.setText(!TextUtils.isEmpty(mChapter.chapterTitle) ? mChapter.chapterTitle : "");
        mEditTextChapterContent.setText(!TextUtils.isEmpty(mChapter.chapterDescription) ? mChapter.chapterDescription : "");

        updateChapterContent();
        mPrevHashCode = mChapter.hashCode();

        mPrevTitle = mEditTextChapterTitle.getText().toString();
        mPrevDescription = mEditTextChapterContent.getText().toString();
        mEditTextChapterContent.addTextChangedListener(mTextWatcher);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_story, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_delete) {
            updateChapterContent();
            mChapterEventListener.onDeleteEvent(mChapter);
            pop();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onBackPress() {
        System.out.println("fragment overridden on backpress");
        if (isValid()) {
            updateChapterContent();
            if (mPrevHashCode != mChapter.hashCode()) {
                ((BaseActivity) (getActivity())).showAlertDialog(getString(R.string.header_save_changes), getString(R.string.msg_save_chapter),
                        getString(R.string.lbl_yes), getString(R.string.lbl_no), new BaseActivity.OnHSAlertDialog() {
                            @Override
                            public void onPositiveButtonClick() {
                                mChapterEventListener.onSaveEvent(mChapter);
                                mEditTextUndoRedoContent.clearHistory();
                                EditStoryChapterFragment.super.onBackPress();
                            }

                            @Override
                            public void onNegativeButtonClick() {
                                mChapter.chapterTitle = mPrevTitle;
                                mChapter.chapterDescription = mPrevDescription;
                                mEditTextUndoRedoContent.clearHistory();
                                EditStoryChapterFragment.super.onBackPress();
                            }
                        });
            } else
                super.onBackPress();
        }

    }

    private void updateChapterContent() {
        mChapter.chapterTitle = mEditTextChapterTitle.getText().toString();
        mChapter.chapterDescription = mEditTextChapterContent.getText().toString();
    }

    private boolean isValid() {
        boolean isValid = true;
        if (TextUtils.isEmpty(mEditTextChapterTitle.getText().toString())) {
            isValid = false;
            showSnackBar(R.string.msg_title_empty);
        } else if (TextUtils.isEmpty(mEditTextChapterContent.getText().toString())) {
            isValid = false;
            showSnackBar(R.string.msg_desc_empty);
        }
        return isValid;
    }

    private void showSnackBar(int msgResId) {
        Snackbar.make(mRelativeLayout, getString(msgResId), Snackbar.LENGTH_LONG).show();
    }
}

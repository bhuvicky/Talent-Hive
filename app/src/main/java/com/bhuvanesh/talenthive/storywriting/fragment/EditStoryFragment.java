package com.bhuvanesh.talenthive.storywriting.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.bhuvanesh.talenthive.R;
import com.bhuvanesh.talenthive.RunTimePermissionFragment;
import com.bhuvanesh.talenthive.THApplication;
import com.bhuvanesh.talenthive.account.activity.LoginActivity;
import com.bhuvanesh.talenthive.database.THDBManager;
import com.bhuvanesh.talenthive.model.Language;
import com.bhuvanesh.talenthive.storywriting.adapter.StoryChapterAdapter;
import com.bhuvanesh.talenthive.storywriting.model.Chapter;
import com.bhuvanesh.talenthive.storywriting.model.Story;
import com.bhuvanesh.talenthive.storywriting.model.StoryCategory;
import com.bhuvanesh.talenthive.util.FileUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


public class EditStoryFragment extends RunTimePermissionFragment {

    // TODO: 13-01-2017 when click undo, deleted items should come back
    // TODO: 13-01-2017  validation check for description

    private static final int PICK_GALLERY_IMAGE_REQUEST_CODE = 1;
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_CODE = 100;

    private ImageView mImageViewStoryWrapper;
    private EditText mEditTextTitle, mEditTextDescription;
    private TextInputLayout mTextinputTitle;
    private Spinner mSpinnerLang, mSpinnerCategory;
    private CoordinatorLayout mCoordinatorLayout;

    private Story mStory;
    private StoryChapterAdapter mStoryChapterAdapter;

    private String mImagePath;
    private SoftReference<Bitmap> mStoryWrapperBitmap;

    private List<Chapter> mChapterList = new LinkedList<>();
    private List<Language> mLangList;
    private List<StoryCategory> mCategoryList;

    public static EditStoryFragment newInstance(Story story) {
        EditStoryFragment fragment = new EditStoryFragment();
        fragment.mStory = story != null ? story : new Story();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View headerView = inflater.inflate(R.layout.header_story_wrapper, null, false);
        View view = inflater.inflate(R.layout.fragment_edit_story, container, false);

        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.title_edit_story);

        mCoordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorlayout);
        ListView listViewChapter = (ListView) view.findViewById(R.id.listview_chapter);
        listViewChapter.addHeaderView(headerView, null, false);

        if (mStoryChapterAdapter == null) {
            mStoryChapterAdapter = new StoryChapterAdapter();
        }
        listViewChapter.setAdapter(mStoryChapterAdapter);

        listViewChapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // position -1 becos in 0 th position header view is added
                replaceStoryChapterFragment(mStoryChapterAdapter.getItem(position - 1));
            }
        });

        listViewChapter.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listViewChapter.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                System.out.println("edit story checked state = " + checked);
                mStoryChapterAdapter.getItem(position - 1).isDeleted = checked;
                mStoryChapterAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                getActivity().getMenuInflater().inflate(R.menu.menu_delete, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_delete:
                        Iterator<Chapter> chapterIterator = mChapterList.iterator();
                        if (chapterIterator.hasNext()) {
                            Chapter chapter = chapterIterator.next();
                            if (chapter.isDeleted)
                                chapterIterator.remove();
                        }
                        mStoryChapterAdapter.notifyDataSetChanged();
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                showSnackBar();
            }
        });

        mImageViewStoryWrapper = (ImageView) headerView.findViewById(R.id.imageview_story_wrapper);
        mImageViewStoryWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(16)
            public void onClick(View v) {
                if (hasPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}))
                    loadFromGallery();
                else
                    requestAppPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION_CODE);
            }
        });

        mEditTextTitle = (EditText) headerView.findViewById(R.id.edittext_story_title);
        mTextinputTitle = (TextInputLayout) headerView.findViewById(R.id.textinput_story_title);
        mEditTextDescription = (EditText) headerView.findViewById(R.id.edittext_story_description);

        List<String> languageNameList = new ArrayList<>();
        mLangList = FileUtil.getFromAssetsFolder("language.json", null,
                new TypeToken<List<Language>>() {
                }.getType());
        for (Language lang : mLangList) {
            languageNameList.add(lang.name);
        }
        mSpinnerLang = (Spinner) headerView.findViewById(R.id.spinner_story_lang);
        ArrayAdapter langAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, languageNameList);
        mSpinnerLang.setAdapter(langAdapter);

        List<String> categoryNameList = new ArrayList<>();
        mCategoryList = FileUtil.getFromAssetsFolder("story_category.json", null,
                new TypeToken<List<StoryCategory>>() {
                }.getType());
        for (StoryCategory category : mCategoryList) {
            categoryNameList.add(category.name);
        }
        mSpinnerCategory = (Spinner) headerView.findViewById(R.id.spinner_story_category);
        ArrayAdapter categoryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, categoryNameList);
        mSpinnerCategory.setAdapter(categoryAdapter);

        setStoryDetails();
        return view;
    }

    private void setStoryDetails() {
        mEditTextTitle.setText(mStory.title);
        mEditTextDescription.setText(mStory.description);
        if (mStory.wrapperImageUrl != null)
            mImageViewStoryWrapper.setImageBitmap(BitmapFactory.decodeFile(mImagePath = mStory.wrapperImageUrl));
        else
            mImageViewStoryWrapper.setImageResource(R.drawable.ic_like_2);

        int index = 0;
        if (mStory.language != null)
            for (int i = 0; i < mLangList.size(); i++) {
                Language lang = mLangList.get(i);
                if (lang.id == mStory.language.id) {
                    index = i;
                    break;
                }
            }
        mSpinnerLang.setSelection(index);

        if (mStory.category != null)
            for (int j = 0; j < mCategoryList.size(); j++) {
                StoryCategory category = mCategoryList.get(j);
                if (category.id == mStory.category.id) {
                    index = j;
                    break;
                }
            }
        mSpinnerCategory.setSelection(index);

        if (mStory.chapterList != null && mStory.chapterList.size() > 0) {
            mChapterList = mStory.chapterList;
            mStoryChapterAdapter.addItem(mChapterList);
        }

    }

    private void saveIntoDB() {
        goToNextScreen();
        if (mStory.storyId == null) {
            /*long id = THPreference.getInstance().getTempStoryId();
            mStory.storyId = String.valueOf(++id);
            THPreference.getInstance().setTempStoryId(id);*/
            mStory.storyId = String.valueOf(UUID.randomUUID());
        }
        mStory.chapterList = mChapterList.size() > 0 ? mChapterList : null;
        mStory.lastModifiedDate = System.currentTimeMillis();
        new THDBManager().updateStory(mStory);
        pop();
        showToastMsg(THApplication.getInstance(), getString(R.string.msg_saved));
    }

    private void goToNextScreen() {
        mStory.title = mEditTextTitle.getText().toString();
        mStory.description = mEditTextDescription.getText().toString();
        mStory.wrapperImageUrl = mImagePath;

        if (mStory.language == null)
            mStory.language = new Language();
        mStory.language.id = mLangList.get(mSpinnerLang.getSelectedItemPosition()).id;

        if (mStory.category == null)
            mStory.category = new StoryCategory();
        mStory.category.id = (int) (mCategoryList.get(mSpinnerCategory.getSelectedItemPosition()).id);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_story, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                if (isValid()) {
                    saveIntoDB();
                }
                break;
            case R.id.menu_add_chapter:
                goToNextScreen();
                replaceStoryChapterFragment(null);
                break;
            default:
//                LoginManager.getInstance().logOut();
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void replaceStoryChapterFragment(Chapter chapter) {
        EditStoryChapterFragment fragment = EditStoryChapterFragment.newInstance(chapter);
        fragment.setChapterEventListener(new EditStoryChapterFragment.ChapterEventListener() {
            @Override
            public void onDeleteEvent(Chapter delChapter) {
                if (mChapterList.contains(delChapter)) {
                    mChapterList.remove(delChapter);
                    mStoryChapterAdapter.notifyDataSetChanged();
                    showSnackBar();
                }
            }

            @Override
            public void onSaveEvent(Chapter saveChapter) {
                if (mChapterList.contains(saveChapter))
                    mChapterList.set(mChapterList.indexOf(saveChapter), saveChapter);
                else
                    mChapterList.add(saveChapter);
                mStoryChapterAdapter.addItem(mChapterList);
            }
        });
        replace(R.id.flayout_container, fragment);
    }


    private void loadFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_GALLERY_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_GALLERY_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            Bitmap bitmap = null;
            /*try {
                bitmap = ImageUtil.decodeUri(getActivity(), mSelectedImageUri, 100, 150);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            mImagePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            cursor.close();
//            mStoryWrapperBitmap = new SoftReference<>(bitmap);
            mImageViewStoryWrapper.setImageBitmap(BitmapFactory.decodeFile(mImagePath));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPermissionsGranted(int requestCode) {
        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_CODE)
            loadFromGallery();
    }

    private void showSnackBar() {
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, getString(R.string.msg_deleted), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.desc_undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        snackbar.show();
    }

    private boolean isValid() {
        boolean isValid = true;
        if (TextUtils.isEmpty(mEditTextTitle.getText().toString())) {
            isValid = false;
            mTextinputTitle.setErrorEnabled(true);
        } else {
            isValid = true;
            mTextinputTitle.setErrorEnabled(false);
        }
        if (mSpinnerLang.getSelectedItemPosition() == 0) {
            isValid = false;
            showToastMsg(EditStoryFragment.this.getContext(), getString(R.string.msg_select_lang));
        } else if (mSpinnerCategory.getSelectedItemPosition() == 0) {
            isValid = false;
            showToastMsg(EditStoryFragment.this.getContext(), getString(R.string.msg_select_category));
        }
        return isValid;
    }
}

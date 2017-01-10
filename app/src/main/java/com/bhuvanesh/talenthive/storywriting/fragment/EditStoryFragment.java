package com.bhuvanesh.talenthive.storywriting.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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
import com.bhuvanesh.talenthive.model.Language;
import com.bhuvanesh.talenthive.storywriting.adapter.StoryChapterAdapter;
import com.bhuvanesh.talenthive.storywriting.model.Chapter;
import com.bhuvanesh.talenthive.storywriting.model.Story;
import com.bhuvanesh.talenthive.storywriting.model.StoryCategory;
import com.bhuvanesh.talenthive.util.FileUtil;
import com.bhuvanesh.talenthive.util.ImageUtil;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class EditStoryFragment extends RunTimePermissionFragment {

    private static final int PICK_GALLERY_IMAGE_REQUEST_CODE = 1;
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_CODE = 100;

    private SoftReference<Bitmap> mStoryWrapperBitmap;
    private ImageView mImageViewStoryWrapper;
    private EditText mEditTextTitle, mEditTextDescription;
    private Spinner mSpinnerLang, mSpinnerCategory;
    private CoordinatorLayout mCoordinatorLayout;

    private Story mStory;
    private StoryChapterAdapter mStoryChapterAdapter;
    private List<Chapter> mChapterList = new LinkedList<>();

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
                mStoryChapterAdapter.getItem(position - 1).isDeleted = checked;
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
        mEditTextDescription = (EditText) headerView.findViewById(R.id.edittext_story_description);

        List<String> languageNameList = new ArrayList<>();
        List<Language> langList = FileUtil.getFromAssetsFolder("language.json", null,
                new TypeToken<List<Language>>() {
                }.getType());
        languageNameList.add("Select Language");
        for (Language lang : langList) {
            languageNameList.add(lang.name);
        }
        mSpinnerLang = (Spinner) headerView.findViewById(R.id.spinner_story_lang);
        ArrayAdapter langAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, languageNameList);
        mSpinnerLang.setAdapter(langAdapter);

        List<String> categoryNameList = new ArrayList<>();
        List<StoryCategory> categoryList = FileUtil.getFromAssetsFolder("story_category.json", null,
                new TypeToken<List<StoryCategory>>() {
                }.getType());
        categoryNameList.add("Select Category");
        for (StoryCategory category : categoryList) {
            categoryNameList.add(category.name);
        }
        mSpinnerCategory = (Spinner) headerView.findViewById(R.id.spinner_story_category);
        ArrayAdapter categoryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, categoryNameList);
        mSpinnerCategory.setAdapter(categoryAdapter);

        setStoryDetails();
        return view;
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
                break;
            case R.id.menu_add_chapter:
                replaceStoryChapterFragment(null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setStoryDetails() {

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
            try {
                bitmap = ImageUtil.decodeUri(getActivity(), selectedImageUri, 100, 150);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            mStoryWrapperBitmap = new SoftReference<>(bitmap);
            mImageViewStoryWrapper.setImageBitmap(bitmap);
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
        return isValid;
    }
}

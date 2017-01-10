package com.bhuvanesh.talenthive.util;


import android.content.Context;
import android.content.SharedPreferences;

import com.bhuvanesh.talenthive.THApplication;
import com.bhuvanesh.talenthive.model.Language;
import com.bhuvanesh.talenthive.storywriting.model.StoryCategory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public final class THPreference {

    public static final String PREFERENCE_FILE_NAME = "TALENT_HIVE";

    public static final String PREFERENCE_KEY_LANGUAGE = "PREFERENCE_KEY_LANGUAGE";
    public static final String PREFERENCE_KEY_STORY_CATEGORY = "PREFERENCE_KEY_STORY_CATEGORY";

    private static THPreference mInstance;
    private static SharedPreferences mSharedPreferences;

    private THPreference() {}

    public static synchronized THPreference getInstance() {
        if (mInstance == null) {
            mInstance = new THPreference();
            mSharedPreferences = THApplication.getInstance().getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        }
        return mInstance;
    }

    public void setLanguageList(String languageJson) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PREFERENCE_KEY_LANGUAGE, languageJson);
        editor.apply();
    }

    public List<Language> getLanguageList() {
        List<Language> languageList = new Gson().fromJson(mSharedPreferences.getString(PREFERENCE_KEY_LANGUAGE, ""),
                new TypeToken<List<Language>>() {}.getType());
        return languageList != null ? languageList : new ArrayList<Language>();
    }

    public void setCategoryList(String categoryJson) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PREFERENCE_KEY_STORY_CATEGORY, categoryJson);
        editor.apply();
    }

    public List<StoryCategory> getStoryCategoryList() {
        List<StoryCategory> categoryList = new Gson().fromJson(mSharedPreferences.getString(PREFERENCE_KEY_STORY_CATEGORY, ""),
                new TypeToken<List<StoryCategory>>() {}.getType());
        return categoryList != null ? categoryList : new ArrayList<StoryCategory>();
    }
}

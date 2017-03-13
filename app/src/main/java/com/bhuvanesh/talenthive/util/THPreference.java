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

    private static final String PREFERENCE_FILE_NAME = "TALENT_HIVE";

    private static final String PREFERENCE_KEY_LANGUAGE = "PREFERENCE_KEY_LANGUAGE";
    private static final String PREFERENCE_KEY_STORY_CATEGORY = "PREFERENCE_KEY_STORY_CATEGORY";
    private static final String PREFERENCE_KEY_TEMP_STORY_ID = "PREFERENCE_KEY_TEMP_STORY_ID";
    private static final String PREFERENCE_KEY_IN_APP_LOGIN = "PREFERENCE_KEY_IN_APP_LOGIN";
    private static final String PREFERENCE_KEY_FB_LOGIN = "PREFERENCE_KEY_FB_LOGIN";
    private static final String PREFERENCE_KEY_GOOGLE_LOGIN = "PREFERENCE_KEY_GOOGLE_LOGIN";
    private static final String PREFERENCE_KEY_GOOGLE_SERVER_AUTH_CODE = "PREFERENCE_KEY_GOOGLE_SERVER_AUTH_CODE";


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

    public void setInAppLogin(boolean loginStatus) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean("PREFERENCE_KEY_IN_APP_LOGIN", loginStatus);
        editor.apply();
    }

    public boolean isInAppLoggedIn() {
        return mSharedPreferences.getBoolean("PREFERENCE_KEY_IN_APP_LOGIN", false);
    }

    public void setFBLogin(boolean loginStatus) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean("PREFERENCE_KEY_FB_LOGIN", loginStatus);
        editor.apply();
    }

    public boolean isFBLoggedIn() {
        return mSharedPreferences.getBoolean("PREFERENCE_KEY_FB_LOGIN", false);
    }

    public void setGoogleLogin(boolean loginStatus) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean("PREFERENCE_KEY_GOOGLE_LOGIN", loginStatus);
        editor.apply();
    }

    public boolean isGoolgeLoggedIn() {
        return mSharedPreferences.getBoolean("PREFERENCE_KEY_GOOGLE_LOGIN", false);
    }

    public void setLanguageList(String languageJson) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PREFERENCE_KEY_LANGUAGE, languageJson);
        editor.apply();
    }

    public void setGoogleServerAuthCode(String serverAuthCode) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PREFERENCE_KEY_GOOGLE_SERVER_AUTH_CODE, serverAuthCode);
        editor.apply();
    }

    public String getGoogleServerAuthCode() {
        return mSharedPreferences.getString(PREFERENCE_KEY_GOOGLE_SERVER_AUTH_CODE, "");
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

    public void setTempStoryId(long id) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(PREFERENCE_KEY_TEMP_STORY_ID, id);
        editor.apply();
    }

    public long getTempStoryId() {
        return mSharedPreferences.getLong(PREFERENCE_KEY_TEMP_STORY_ID, 0);
    }
}

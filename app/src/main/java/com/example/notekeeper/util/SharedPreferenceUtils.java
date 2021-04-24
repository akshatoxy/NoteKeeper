package com.example.notekeeper.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SharedPreferenceUtils {

    private static final String SORT_BY_KEY = "sort_by";
    private static final String INTRO_SCREEN_KEY = "intro_screen";

    private static SharedPreferenceUtils instance;
    private SharedPreferences preferences;

    private SharedPreferenceUtils(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferenceUtils getInstance(Context context){
        synchronized (SharedPreferenceUtils.class){
            if (instance == null){
                instance = new SharedPreferenceUtils(context);
            }
        }
        return instance;
    }

    public String getSortBy(){
        return preferences.getString(SORT_BY_KEY, "id");
    }

    public void updateSortBy(String sortBy){
        preferences.edit().putString(SORT_BY_KEY, sortBy).apply();
    }

    public Boolean getIsIntroDisplayed(){
        return preferences.getBoolean(INTRO_SCREEN_KEY, false);
    }

    public void updateIsIntroDisplayed(boolean isIntroDisplayed){
        preferences.edit().putBoolean(INTRO_SCREEN_KEY, isIntroDisplayed).apply();
    }

}

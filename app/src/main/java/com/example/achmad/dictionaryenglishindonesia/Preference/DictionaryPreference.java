package com.example.achmad.dictionaryenglishindonesia.Preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * created by Achmad
 * 16 august 2018
 */

public class DictionaryPreference {
    private SharedPreferences preferences;

    public DictionaryPreference(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setFirstRun(Boolean input) {
        SharedPreferences.Editor editor = preferences.edit();
        String key = "dictionary_pref";
        editor.putBoolean(key, input);
        editor.apply();
    }

    public Boolean getFirstRun() {
        String key = "dictionary_pref";
        return preferences.getBoolean(key, true);
    }
}

package com.jordan.android.weatherapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import java.util.List;

/**
 *  The SettingsFragment will display the user's preferences
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);

        PreferenceScreen prefScreen = getPreferenceScreen();
        SharedPreferences sharedPrefs = getPreferenceScreen().getSharedPreferences();

        for (int i = 0; i < prefScreen.getPreferenceCount(); i++) {
            Preference pref = prefScreen.getPreference(i);
            if (!(pref instanceof CheckBoxPreference)) {
                String val = sharedPrefs.getString(pref.getKey(), "");
                setPrefSummary(pref, val);
            }
        }
    }

    private  void setPrefSummary(Preference pref, Object value) {
        String stringVal = value.toString();
        String key = pref.getKey();

        if (pref instanceof ListPreference) {
            ListPreference listPref = (ListPreference) pref;
            int prefIndex = listPref.findIndexOfValue(stringVal);
            if (prefIndex >= 0) {
                pref.setSummary(listPref.getEntries()[prefIndex]);
            }
        } else if (pref instanceof EditTextPreference) {
            pref.setSummary(stringVal);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference pref = findPreference(key);
        if (pref != null) {
            if (!(pref instanceof CheckBoxPreference)) {
                setPrefSummary(pref, sharedPreferences.getString(key, ""));
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}

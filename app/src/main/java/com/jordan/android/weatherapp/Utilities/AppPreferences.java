package com.jordan.android.weatherapp.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.jordan.android.weatherapp.R;

/**
 *  Constants and methods for accessing preferences
 */

public class AppPreferences {

    public static String getPreferredWeatherLocation(Context context) {
        String locationKey = context.getString(R.string.pref_location_key);
        String locationValue = context.getString(R.string.pref_location);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        return prefs.getString(locationKey, locationValue);
    }
}

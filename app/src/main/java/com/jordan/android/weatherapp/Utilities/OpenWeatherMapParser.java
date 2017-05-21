package com.jordan.android.weatherapp.Utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *  Methods to parse JSON data received from OpenWeatherMap
 */

public class OpenWeatherMapParser {

    private static final long MILLISECONDS_IN_DAY = 86400000;

    public static String[] parseWeather(Context context, String JSONReponse) throws JSONException {

        long now = System.currentTimeMillis();

        JSONObject jsonObject = new JSONObject(JSONReponse);

        JSONArray dayArray = jsonObject.getJSONArray("list");

        String[] parsedWeather = new String[dayArray.length()];

        for (int i = 0; i < dayArray.length(); i++) {
            JSONObject day = dayArray.getJSONObject(i);

            JSONObject temperatureInfo = day.getJSONObject("temp");

            int min = (int) Math.round(temperatureInfo.getDouble("min"));
            int max = (int) Math.round(temperatureInfo.getDouble("max"));

            String description = day.getJSONArray("weather").getJSONObject(0).getString("main");

            DateFormat formatter = new SimpleDateFormat("EEEE MMM d");

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(now);

            Date dateOfThisDay = new Date(calendar.getTimeInMillis() + MILLISECONDS_IN_DAY * i);

            String date = formatter.format(dateOfThisDay);

            parsedWeather[i] = date + " - " + description + " - " + "Min: " + min + " / " + "Max: " + max;
        }

        return parsedWeather;
    }
}

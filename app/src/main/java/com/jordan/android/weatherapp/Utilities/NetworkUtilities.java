package com.jordan.android.weatherapp.Utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 *  Various methods to facilitate networking
 */

public class NetworkUtilities {

    private static final String OPEN_WEATHER_URL = "https://api.openweathermap.org/data/2.5/forecast/daily";
    private static final String TEMP_URL = "https://andfun-weather.udacity.com/staticweather";

    private static final String format = "json";
    private static final String units = "metric";
    private static final String numDays = "14";

    private static final String QUERY_PARAM = "q";
    private static final String FORMAT_PARAM = "mode";
    private static final String UNITS_PARAM = "units";
    private static final String NUM_DAYS_PARAM = "cnt";

    /**
     * Builds URL to be used to communicate with OpenWeatherData server
     *
     *  @param location     The location to be queried
     *  @return             The url for the OpenWeatherData API
     */
    public static URL buildURL(String location) {

        Uri uri = Uri.parse(TEMP_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, location)
                .appendQueryParameter(FORMAT_PARAM, format)
                .appendQueryParameter(UNITS_PARAM, units)
                .appendQueryParameter(NUM_DAYS_PARAM, numDays).build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println(url.toString());
        return url;
    }

    /**
     * Returns the HTTP response from the URL
     *
     * @param url   The URL to get the HTTP response from
     * @return      Everything from HTTP response
     * @throws IOException
     */
    public static String getHttpResponse(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream is = connection.getInputStream();

            Scanner scanner = new Scanner(is);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            connection.disconnect();
        }
    }
}

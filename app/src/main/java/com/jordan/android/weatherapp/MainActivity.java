package com.jordan.android.weatherapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jordan.android.weatherapp.Utilities.AppPreferences;
import com.jordan.android.weatherapp.Utilities.NetworkUtilities;
import com.jordan.android.weatherapp.Utilities.OpenWeatherMapParser;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements WeatherClickHandler, LoaderManager.LoaderCallbacks<String[]>, SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView recyclerView;

    private WeatherAdapter weatherAdapter;

    private ProgressBar loadingCircle;

    private static final int WEATHER_LOADER_ID = 0;

    private static boolean PREFS_CHANGED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.weather_rv);

        loadingCircle = (ProgressBar) findViewById(R.id.loading_circle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        weatherAdapter = new WeatherAdapter(this);

        recyclerView.setAdapter(weatherAdapter);

        LoaderManager.LoaderCallbacks<String[]> callback = this;

        getSupportLoaderManager().initLoader(WEATHER_LOADER_ID, null, callback);

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (PREFS_CHANGED) {
            getSupportLoaderManager().restartLoader(WEATHER_LOADER_ID, null, this);
            PREFS_CHANGED = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onClick(String weatherForTheDayClicked) {
        Intent intentToOpenDayDetailActivity = new Intent(this, DayDetailActivity.class);
        intentToOpenDayDetailActivity.putExtra(Intent.EXTRA_TEXT, weatherForTheDayClicked);
        startActivity(intentToOpenDayDetailActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {  // REMOVE EVENTUALLY
            invalidateData();
            getSupportLoaderManager().restartLoader(0, null, this);
            return true;
        }

        if (id == R.id.action_map) {
            String locationString = AppPreferences.getPreferredWeatherLocation(this);
            Uri encodedLocation = Uri.parse("geo:0,0?q=" + locationString);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(encodedLocation);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
                return true;
            } else {
                Toast.makeText(this, "No appropriate map app installed!", Toast.LENGTH_SHORT);
                return false;
            }
        }

        if (id == R.id.action_settings) {
            Intent intentToStartSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(intentToStartSettingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void invalidateData() {       // REMOVE EVENTUALLY
        weatherAdapter.setWeatherData(null);
    }

    @Override
    public Loader<String[]> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<String[]>(this) {

            String[] weatherData = null;

            @Override
            protected void onStartLoading() {
                if (weatherData != null) {
                    deliverResult(weatherData);
                } else {
                    loadingCircle.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public String[] loadInBackground() {

                URL weatherRequestURL = NetworkUtilities.buildURL("Vancouver");

                try {
                    String response = NetworkUtilities.getHttpResponse(weatherRequestURL);
                    String[] parsedWeather = OpenWeatherMapParser.parseWeather(MainActivity.this, response);
                    return parsedWeather;
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("IOException thrown!");
                    return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("JSONException thrown!");
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String[]> loader, String[] data) {
        loadingCircle.setVisibility(View.INVISIBLE);
        weatherAdapter.setWeatherData(data);

    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {
        // Not used
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        PREFS_CHANGED = true;
    }
}

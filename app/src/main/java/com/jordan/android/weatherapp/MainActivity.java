package com.jordan.android.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.jordan.android.weatherapp.Utilities.NetworkUtilities;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private WeatherAdapter weatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.weather_rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        weatherAdapter = new WeatherAdapter();

        recyclerView.setAdapter(weatherAdapter);

        fetchWeatherData();
    }

    private void fetchWeatherData() {
        new FetchWeatherTask().execute("Vancouver");
    }


    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String location = params[0];
            URL weatherRequestURL = NetworkUtilities.buildURL(location);

            try {
                String response = NetworkUtilities.getHttpResponse(weatherRequestURL);
                return new String[5];  // FIIIIIIIIIIIIIIIIIIIIIIIIIIIX
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Null!");
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
            weatherAdapter.setWeatherData(weatherData);
        }
    }

}

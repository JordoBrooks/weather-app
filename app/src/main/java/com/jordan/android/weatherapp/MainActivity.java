package com.jordan.android.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jordan.android.weatherapp.Utilities.NetworkUtilities;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView forecastTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        forecastTextView = (TextView) findViewById(R.id.weather_information);

        fetchWeatherData();
    }

    private void fetchWeatherData() {
        new FetchWeatherTask().execute("Vancouver");
    }


    public class FetchWeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String location = params[0];
            URL weatherRequestURL = NetworkUtilities.buildURL(location);

            try {
                String response = NetworkUtilities.getHttpResponse(weatherRequestURL);
                return response;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Null!");
                return null;
            }
        }

        @Override
        protected void onPostExecute(String weatherData) {
            if (weatherData != null) {
                forecastTextView.setText(weatherData);
            }
        }
    }

}

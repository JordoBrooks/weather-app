package com.jordan.android.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.jordan.android.weatherapp.Utilities.NetworkUtilities;
import com.jordan.android.weatherapp.Utilities.OpenWeatherMapParser;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements WeatherAdapter.WeatherClickHandler {

    private RecyclerView recyclerView;

    private WeatherAdapter weatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.weather_rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        weatherAdapter = new WeatherAdapter(this);

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

        @Override
        protected void onPostExecute(String[] weatherData) {
            weatherAdapter.setWeatherData(weatherData);
        }
    }

    @Override
    public void onClick() {
        Toast.makeText(this, "Item clicked!", Toast.LENGTH_SHORT).show();
    }
}

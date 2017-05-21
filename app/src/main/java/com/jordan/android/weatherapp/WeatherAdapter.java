package com.jordan.android.weatherapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jordan on 20/05/17.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherAdapterViewHolder> {

    private String[] weatherData;

    public class WeatherAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView weatherTextView;

        public WeatherAdapterViewHolder(View view) {
            super(view);
            weatherTextView = (TextView) view.findViewById(R.id.weather_item_tv);
        }

    }

    @Override
    public WeatherAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.weather_list_item, parent, false);
        WeatherAdapterViewHolder wavh = new WeatherAdapterViewHolder(view);
        return wavh;
    }

    @Override
    public void onBindViewHolder(WeatherAdapterViewHolder holder, int position) {
        holder.weatherTextView.setText(weatherData[position]);
    }

    @Override
    public int getItemCount() {
        return weatherData.length;
    }

    public void setWeatherData(String[] weatherData) {
        this.weatherData = weatherData;
        notifyDataSetChanged();
    }
}

package com.jordan.android.weatherapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 *   Adapter class for RecyclerView
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherAdapterViewHolder> {

    private String[] weatherData;

    private WeatherClickHandler clickHandler;

    /**
     *  Constructor for WeatherAdaptor
     *
     * @param clickHandler  On click handler for this adaptor
     */

    public WeatherAdapter(WeatherClickHandler clickHandler) {
        this.clickHandler = clickHandler;
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
        if (weatherData == null) {
            return 0;
        }
        return weatherData.length;
    }

    /**
     *  Sets the weatherData for this adaptor, then notifies any registered observers of the change
     *  forcing LayoutManager to re-bind and display visible views
     *
     * @param weatherData   The weather data to be displayed
     */

    public void setWeatherData(String[] weatherData) {
        this.weatherData = weatherData;
        notifyDataSetChanged();
    }

    /**
     *  The interface to handles on click messages sent from WeatherAdapterViewHolder
     */

    public interface WeatherClickHandler {
        void onClick();
    }

    public class WeatherAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView weatherTextView;

        public WeatherAdapterViewHolder(View view) {
            super(view);
            weatherTextView = (TextView) view.findViewById(R.id.weather_item_tv);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickHandler.onClick();
        }
    }
}

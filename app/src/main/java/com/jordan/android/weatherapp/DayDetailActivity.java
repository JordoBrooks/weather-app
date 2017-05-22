package com.jordan.android.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by jordan on 21/05/17.
 */

public class DayDetailActivity extends AppCompatActivity {

    TextView dayDetailTextView;

    String weatherForThisDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_detail);

        dayDetailTextView = (TextView) findViewById(R.id.day_detail_tv);

        Intent parentIntent = getIntent();

        if (parentIntent.hasExtra(Intent.EXTRA_TEXT)) {
            weatherForThisDay = parentIntent.getStringExtra(Intent.EXTRA_TEXT);
            dayDetailTextView.setText(weatherForThisDay);
        }
    }
}

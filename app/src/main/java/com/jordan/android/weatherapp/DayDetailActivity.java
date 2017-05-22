package com.jordan.android.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 *  Activity for particular day showing more weather detail
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.day_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            Intent intentToShare = ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setText(weatherForThisDay)
                    .getIntent();

            startActivity(intentToShare);
            return true;
        }

        if (id == R.id.action_settings) {
            Intent intentToStartSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(intentToStartSettingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

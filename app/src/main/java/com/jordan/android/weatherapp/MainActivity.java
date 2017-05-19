package com.jordan.android.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jordan.android.weatherapp.Utilities.NetworkUtilities;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkUtilities.buildURL("Vancouver");
    }
}

package com.cyprian.worldtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class IndividualTime extends AppCompatActivity {
    private TextView currentTime;
    private TextView currentLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_time);

        currentTime = findViewById(R.id.ind_time);
        currentLoc = findViewById(R.id.loc);

        Intent myIntent = getIntent();
        String loc = myIntent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String time = myIntent.getStringExtra(MainActivity.TIME_EXTRA);

        String[] l = loc.split("/");
        currentLoc.setText(l[l.length-1]);
        //getTime();
        currentTime.setText(time);
    }

    public void selectZone(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
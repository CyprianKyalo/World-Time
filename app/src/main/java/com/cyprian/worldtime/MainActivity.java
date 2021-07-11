package com.cyprian.worldtime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    public static String EXTRA_MESSAGE = "MY KEY";
    public static String TIME_EXTRA = "CURR TIME";
    Calendar calendar;
    long milliseconds;
    private TextView currTime;
    Date resDate;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = findViewById(R.id.myToolBar);
//        setSupportActionBar(toolbar);

        String[] worldArray = TimeZone.getAvailableIDs();

        adapter = new ArrayAdapter(this, R.layout.activity_listview, worldArray);

        final ListView listView = findViewById(R.id.world_zones);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), IndividualTime.class);
                //Get the location
                String loc = parent.getItemAtPosition(position).toString();

                //Get the time
                getTime();
                String ID = (String) parent.getItemAtPosition(position);
                TimeZone timeZone = TimeZone.getTimeZone(ID);

                int timeZoneOffset = timeZone.getRawOffset() / (60 * 1000);
                int hrs = timeZoneOffset / 60;
                int mins = timeZoneOffset % 60;

                milliseconds = milliseconds + timeZone.getRawOffset();
                resDate = new Date(milliseconds);

                SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
                System.out.println("This is the time: "+df.format(resDate));


                String time = df.format(resDate).toString();


                intent.putExtra(TIME_EXTRA, time);
                intent.putExtra(EXTRA_MESSAGE, loc);

                startActivity(intent);
            }
        });
    }

    public void getTime() {
        calendar = Calendar.getInstance();

        milliseconds = calendar.getTimeInMillis();
        TimeZone TzCurr = calendar.getTimeZone();
        int offset = TzCurr.getRawOffset();

        if (TzCurr.inDaylightTime(new Date())) {
            offset = offset + TzCurr.getDSTSavings();
        }

        milliseconds = milliseconds - offset;

        resDate = new Date(milliseconds);
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
        System.out.println(sd.format(resDate));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }
}
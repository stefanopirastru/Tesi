package com.example.mirco.civichacking;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;
import java.util.GregorianCalendar;

public class InfoActivity extends AppCompatActivity {

    private TextView nameEventsTextView;
    private TextView placeEventsTextView;
    private TextView cityEventsTextView;
    private TextView dateEventsTextView;
    private TextView timeEventsTextView;
    private TextView categoryEventsTextView;
    private TextView descriptionEventsTextView;
    private ImageButton favButton;
    private ImageButton mapButton;
    EventsListDB db;
    Long id;
    String name, place, city, datein, datefi,  time, category, description;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        db= new EventsListDB(this);

        nameEventsTextView = (TextView) findViewById(R.id.nomeevento);
        placeEventsTextView = (TextView) findViewById(R.id.luogoevento);
        cityEventsTextView = (TextView) findViewById(R.id.cittaevento);
        dateEventsTextView = (TextView) findViewById(R.id.dataevento);
        timeEventsTextView = (TextView) findViewById(R.id.oraevento);
        categoryEventsTextView = (TextView) findViewById(R.id.categoriaevento);
        descriptionEventsTextView=(TextView) findViewById(R.id.descrizioneevento);
        favButton = (ImageButton) findViewById(R.id.favButton);
        mapButton = (ImageButton) findViewById(R.id.mapButton);


        Intent i = getIntent();
        id = i.getLongExtra("id", -1);
        name = i.getStringExtra("name");
        place = i.getStringExtra("place");
        city = i.getStringExtra("city");
        datein = i.getStringExtra("datein");
        datefi = i.getStringExtra("datefi");
        time = i.getStringExtra("time");
        category = i.getStringExtra("category");
        description=i.getStringExtra("description");

        nameEventsTextView.setText(name);
        placeEventsTextView.setText(place);
        cityEventsTextView.setText(city);
        timeEventsTextView.setText(time);
        categoryEventsTextView.setText(category);
        descriptionEventsTextView.setText(description);

        if(datein.equals(datefi)) {
            dateEventsTextView.setText(datein);
        }
        else{
            dateEventsTextView.setText("Dal " + datein + " al " + datefi);
        }

        addFavorites();
        viewMap();

    }


    public void viewMap(){
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String events_place = place;
                String events_city = city;

                Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination= "+ events_place + " "+ events_city);
                Intent mapIntent = new Intent (Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }

    public void addFavorites(){
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Events events = new Events(id, name, place, city, datein, datefi, time, category, description);
                long isInserted = db.insertEvents(events);

                if (isInserted > 0) {
                    notification();
                    Toast.makeText(InfoActivity.this, "Preferito Inserito", Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(InfoActivity.this, "Preferito Già Inserito", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    public void notification(){
        Calendar today = Calendar.getInstance();

        String[] splitdate =datein.split("/");
        int day = Integer.parseInt(splitdate[0]);
        int month = Integer.parseInt(splitdate[1]);
        int year = Integer.parseInt(splitdate[2]);

        String [] splittime = time.split(":");
        int hour = Integer.parseInt(splittime[0]);
        int minute = Integer.parseInt(splittime[1]);

        Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DAY_OF_MONTH, day);
        thatDay.set(Calendar.MONTH, month-1);
        thatDay.set(Calendar.YEAR, year);
        thatDay.set(Calendar.HOUR_OF_DAY, hour-3);
        thatDay.set(Calendar.MINUTE, minute );

        long difference =thatDay.getTimeInMillis() - today.getTimeInMillis();

        long timer = new GregorianCalendar().getTimeInMillis()+difference;

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this, Receiver.class);
        intent.putExtra("name", name);
        intent.putExtra("place", place);
        intent.putExtra("city", city);
        intent.putExtra("datein", datein);
        intent.putExtra("datefi", datefi);
        intent.putExtra("time", time);
        intent.putExtra("category", category);
        intent.putExtra("description", description);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timer, pendingIntent);
    }
}

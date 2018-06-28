package com.example.mirco.civichacking;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

public class InfoFavorites extends AppCompatActivity  {

    private TextView nameEventsTextView;
    private TextView placeEventsTextView;
    private TextView cityEventsTextView;
    private TextView dateEventsTextView;
    private TextView timeEventsTextView;
    private TextView categoryEventsTextView;
    private TextView descriptionEventsTextView;
    private ImageButton deleteButton;
    private ImageButton mapButton;
    private ImageButton calButton;
    EventsListDB db;
    long id;
    Intent i;
    String name, place, city, datein, datefi, time, category, description;


    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, MainActivity.class );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity( i );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_favorites);

        db= new EventsListDB(this);

        nameEventsTextView = (TextView) findViewById(R.id.nomeevento);
        placeEventsTextView = (TextView) findViewById(R.id.luogoevento);
        cityEventsTextView = (TextView) findViewById(R.id.cittaevento);
        dateEventsTextView = (TextView) findViewById(R.id.dataevento);
        timeEventsTextView = (TextView) findViewById(R.id.oraevento);
        categoryEventsTextView = (TextView) findViewById(R.id.categoriaevento);
        descriptionEventsTextView = (TextView) findViewById(R.id.descrizioneevento);
        mapButton = (ImageButton) findViewById(R.id.mapButton);
        calButton = (ImageButton) findViewById(R.id.calButton);
        deleteButton = (ImageButton) findViewById(R.id.delButton);



        i = getIntent();
        id = i.getLongExtra("id", -1);
        name = i.getStringExtra("name");
        place = i.getStringExtra("place");
        city = i.getStringExtra("city");
        datein = i.getStringExtra("datein");
        datefi = i.getStringExtra("datefi");
        time = i.getStringExtra("time");
        category = i.getStringExtra("category");
        description = i.getStringExtra("description");


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

        viewMap();
        insertCalendar();
        deleteFavorites();
    }

    public void viewMap(){
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String events_place = place;
                String events_city = city;

                Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination= "+ events_place +" "+ events_city);
                Intent mapIntent = new Intent (Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }

    public void insertCalendar(){

        calButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] splitdatein = datein.split("/");
                int day = Integer.parseInt(splitdatein[0]);
                int month = Integer.parseInt(splitdatein[1]);
                int year = Integer.parseInt(splitdatein[2]);

                String[] splitdatefi =  datefi.split("/");
                int day2 = Integer.parseInt(splitdatefi[0]);
                int month2 = Integer.parseInt(splitdatefi[1]);
                int year2 = Integer.parseInt(splitdatefi[2]);

                String [] splittime = time.split(":");
                int hour = Integer.parseInt(splittime[0]);
                int minute = Integer.parseInt(splittime[1]);

                Calendar begin = Calendar.getInstance();
                begin.set(year, month-1, day, hour, minute);

                Calendar end = Calendar.getInstance();
                end.set(year2, month2-1, day2, hour, minute);

                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE, name)
                        .putExtra(CalendarContract.Events.DESCRIPTION, description)
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, place + "  " + city);
                startActivity(intent);

            }
        });

    }

    public void deleteFavorites(){
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int isDelete = db.deleteEvents(id);
                View row=(View) v.getParent();
                ViewGroup container = ((ViewGroup)row.getParent());
                container.removeView(row);
                container.invalidate();
                if(isDelete >0) {
                    Toast.makeText(InfoFavorites.this, "Preferito rimosso", Toast.LENGTH_LONG).show();
                    finish();



                }
                else {
                    Toast.makeText(InfoFavorites.this, "Preferito già rimosso", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}


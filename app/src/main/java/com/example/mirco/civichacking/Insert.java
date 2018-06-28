package com.example.mirco.civichacking;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


public class Insert extends AppCompatActivity {

    private EditText nameEventsEditText;
    private EditText placeEditText;
    private EditText cityEditText;
    private TextView dateinTextView;
    private TextView datefiTextView;
    private TextView timeTextView;
    private EditText descriptionEditText;
    private Button insertButton;
    private Button dateinButton;
    private Button datefiButton;
    private Button timeButton;
    private Spinner spinner;
    String server_url = "http://abouttoday.altervista.org/insert.php";
    String description = "";
    String category="";
    private DatePickerDialog.OnDateSetListener dateSetListener1;
    private DatePickerDialog.OnDateSetListener dateSetListener2;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    long difference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);


        nameEventsEditText = (EditText) findViewById(R.id.nameLabeleditText);
        placeEditText = (EditText) findViewById(R.id.placeLabeleditText);
        cityEditText = (EditText) findViewById(R.id.cityLabeleditText);
        dateinTextView = (TextView) findViewById(R.id.dateintext);
        datefiTextView = (TextView) findViewById(R.id.datefitext);
        dateinButton = (Button) findViewById(R.id.dateinbutton);
        datefiButton = (Button) findViewById(R.id.datefibutton);
        timeTextView = (TextView) findViewById(R.id.timetext);
        timeButton = (Button) findViewById(R.id.timebutton);
        descriptionEditText = (EditText) findViewById(R.id.descriptionLabeleditText);
        spinner = (Spinner) findViewById(R.id.spinner);
        insertButton = (Button) findViewById(R.id.insertButton);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.split_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        dateinButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Insert.this,
                        dateSetListener1, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        datefiButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Insert.this,
                        dateSetListener2, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        dateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c1 = new GregorianCalendar(year, month, dayOfMonth);
                Calendar c2 = new GregorianCalendar();
                c2.add(Calendar.DAY_OF_MONTH, -1);
                if(c1.before(c2)){
                    Toast.makeText(Insert.this, "Data Errata", Toast.LENGTH_LONG).show();
                }
                else {
                    month = month + 1;
                    String data = dayOfMonth + "/" + month + "/" + year;
                    dateinTextView.setText(data);
                }
            }
        };

        dateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c1 = new GregorianCalendar(year, month, dayOfMonth);
                Calendar c2 = new GregorianCalendar();
                c2.add(Calendar.DAY_OF_MONTH, -1);
                if(c1.before(c2)){
                    Toast.makeText(Insert.this, "Data Errata", Toast.LENGTH_LONG).show();
                }

                else {
                    month = month + 1;
                    String data = dayOfMonth + "/" + month + "/" + year;
                    datefiTextView.setText(data);
                }
            }
        };


        timeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        Insert.this,
                        timeSetListener, hour, minute, true);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();

            }
        });

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calend = Calendar.getInstance();
                   calend.set(Calendar.HOUR_OF_DAY, hourOfDay);
                   calend.set(Calendar.MINUTE, minute);

                   String time = hourOfDay + ":" + minute;
                   timeTextView.setText(time);
            }
        };




        insertButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameEventsEditText.getText().toString();
                final String place = placeEditText.getText().toString();
                final String city = cityEditText.getText().toString();
                final String datain = dateinTextView.getText().toString();
                final String datafi = datefiTextView.getText().toString();
                final String time = timeTextView.getText().toString();
                description = descriptionEditText.getText().toString();

                int splitPosition = spinner.getSelectedItemPosition();
                int split = splitPosition + 1;


                switch (split) {

                    case 2:
                        category = "Fiera";
                        break;


                    case 3:
                        category = "Manifestazione";
                        break;

                    case 4:
                        category = "Spettacolo";
                        break;

                    case 5:
                        category = "Sport";
                        break;


                    default:
                        break;
                }


                if(dateinTextView.length() != 0 && datefiTextView.length() != 0) {
                    String[] splitdatein = datain.split("/");
                    int day_in = Integer.parseInt(splitdatein[0]);
                    int month_in = Integer.parseInt(splitdatein[1]);
                    int year_in = Integer.parseInt(splitdatein[2]);

                    String[] splitdatefi = datafi.split("/");
                    int day_fi = Integer.parseInt(splitdatefi[0]);
                    int month_fi = Integer.parseInt(splitdatefi[1]);
                    int year_fi = Integer.parseInt(splitdatefi[2]);

                    Calendar dayIn = Calendar.getInstance();
                    dayIn.set(Calendar.DAY_OF_MONTH, day_in);
                    dayIn.set(Calendar.MONTH, month_in - 1);
                    dayIn.set(Calendar.YEAR, year_in);

                    Calendar dayFi = Calendar.getInstance();
                    dayFi.set(Calendar.DAY_OF_MONTH, day_fi);
                    dayFi.set(Calendar.MONTH, month_fi - 1);
                    dayFi.set(Calendar.YEAR, year_fi);

                    difference = dayFi.getTimeInMillis() - dayIn.getTimeInMillis();
                }

                if (name.length() == 0 ) {
                    Toast.makeText(Insert.this, "Inserisci il nome dell'evento", Toast.LENGTH_LONG).show();

                }

                else if(place.length() == 0 ){
                    Toast.makeText(Insert.this, "Inserisci il luogo", Toast.LENGTH_LONG).show();
                }

                else if(city.length() == 0 ){
                    Toast.makeText(Insert.this, "Inserisci la città", Toast.LENGTH_LONG).show();
                }

                else if(dateinTextView.length() == 0){
                    Toast.makeText(Insert.this, "Inserisci la data di inizio", Toast.LENGTH_LONG).show();
                }

                else if(datefiTextView.length() == 0){
                    Toast.makeText(Insert.this, "Inserisci la data di fine", Toast.LENGTH_LONG).show();
                }


                else if(timeTextView.length() == 0){
                    Toast.makeText(Insert.this, "Inserisci l'ora", Toast.LENGTH_LONG).show();
                }

                else if(split == 1){
                    Toast.makeText(Insert.this, "Inserisci la categoria", Toast.LENGTH_LONG).show();
                }

                else if(difference<0){
                    Toast.makeText(Insert.this, "Data di fine errata", Toast.LENGTH_LONG).show();
                    datefiTextView.setText("");
                }

                else {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(Insert.this, "Evento inserito", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Insert.this, MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                            }
                        }

                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Insert.this, "Error", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        String[] splitdatein = datain.split("/");
                        final String datein = splitdatein[2] + "/" + splitdatein[1] + "/" + splitdatein[0];

                        String[] splitdatefi = datafi.split("/");
                        final String datefi = splitdatefi[2] + "/" + splitdatefi[1] + "/" + splitdatefi[0];

                        if (description.length() == 0) {
                            description = "Descrizione non disponibile";
                        }
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name", name);
                        params.put("place", place);
                        params.put("city", city);
                        params.put("datein", datein);
                        params.put("datefi", datefi);
                        params.put("time", time);
                        params.put("category", category);
                        params.put("description", description);
                        return params;
                    }
                };

                MySingleton.getInstance(Insert.this).addTorequestque(stringRequest);
            }
            }
        });
    }

}
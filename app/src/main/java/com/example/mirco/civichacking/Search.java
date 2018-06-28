package com.example.mirco.civichacking;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Search extends AppCompatActivity {

    SearchView mSearchView;
    LinearLayout mChartLayout;
    TableLayout mTableLayout;
    FrameLayout mFrameLayout;
    TextView eventTextView;
    TextView textView;
    TableRow row;
    TableRow row1;
    Intent intent;
    String url = "http://abouttoday.altervista.org/selectall.php";
    String search;
    ImageView imageView;


    private void loadImageFromUrl(String url_image){
        Picasso.with( this ).load( url_image ).placeholder( R.mipmap.ic_launcher )
                .error( R.mipmap.ic_launcher)
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                } );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        mSearchView = (SearchView) findViewById(R.id.search);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(Search.this, Search.class);
                intent.putExtra("search", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        Intent i = getIntent();
        search = i.getStringExtra("search");

        textView = (TextView) findViewById(R.id.text);

        mChartLayout = (LinearLayout) findViewById(R.id.chart_layout);
        mChartLayout.setBackgroundColor(Color.parseColor("#2d7cbe"));

        mTableLayout = new TableLayout(this);
        mTableLayout.setBackgroundColor(Color.parseColor("#2d7cbe"));
        mTableLayout.setColumnShrinkable(0, true);
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,5,10,0);
        mTableLayout.setLayoutParams(params);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject events = response.getJSONObject(i);
                                final Long events_id = events.getLong("id");
                                final String nome = events.getString("name");
                                final String luogo = events.getString("place");
                                final String citta = events.getString("city");
                                final String datain = events.getString("datein");
                                final String datafi = events.getString("datefi");
                                final String ora = events.getString("time");
                                final String categoria = events.getString("category");
                                final String descrizione = events.getString("description");

                                    if(Pattern.compile(Pattern.quote(search), Pattern.CASE_INSENSITIVE).matcher( nome ).find() ||
                                            Pattern.compile(Pattern.quote(search), Pattern.CASE_INSENSITIVE).matcher( citta ).find() ||
                                            Pattern.compile(Pattern.quote(search), Pattern.CASE_INSENSITIVE).matcher( categoria ).find()){
                                    textView.setVisibility(View.GONE);

                                    final String events_name = nome;
                                    final String events_place = luogo;
                                    final String events_city = citta;
                                    final String events_datein = datain;
                                    final String events_datefi = datafi;
                                    final String events_time = ora;
                                    final String events_category = categoria;
                                    final String events_description = descrizione;


                                    TableLayout.LayoutParams params1 = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                                    row = new TableRow(Search.this);
                                    row.setBackgroundColor(Color.WHITE);
                                    row.setLayoutParams(params1);


                                    row1 = new TableRow(Search.this);
                                    row1.setBackgroundColor(Color.WHITE);

                                    mFrameLayout = new FrameLayout(Search.this);
                                    TableRow.LayoutParams paramFrame = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                                    mFrameLayout.setLayoutParams(paramFrame);

                                    String[] splitdatein = events_datein.split("-");
                                    String day = splitdatein[2];
                                    String month = splitdatein[1];
                                    String year = splitdatein[0];

                                    final String dataeventoin = day + "/" + month + "/" + year;

                                    String[] splitdatefi = events_datefi.split("-");
                                    String day2 = splitdatefi[2];
                                    String month2 = splitdatefi[1];
                                    String year2 = splitdatefi[0];

                                    final String dataeventofi = day2 + "/" + month2 + "/" + year2;

                                    String [] splittime = events_time.split(":");
                                    String hour = splittime[0];
                                    String minute = splittime[1];
                                    String second = splittime[2];

                                    final String orario = hour + ":" + minute;


                                    row.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            intent = new Intent(Search.this, InfoActivity.class);
                                            intent.putExtra("id", events_id);
                                            intent.putExtra("name", events_name);
                                            intent.putExtra("place", events_place);
                                            intent.putExtra("city", events_city);
                                            intent.putExtra("datein", dataeventoin);
                                            intent.putExtra("datefi", dataeventofi);
                                            intent.putExtra("time", orario);
                                            intent.putExtra("category", events_category);
                                            intent.putExtra("description", events_description);
                                            startActivity(intent);
                                        }
                                    });


                                    SpannableStringBuilder evento = new SpannableStringBuilder();

                                    String place = "Luogo: ";
                                    SpannableString placeSpannable = new SpannableString(place);
                                    placeSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, place.length(), 0);
                                    placeSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, place.length(), 0);
                                    evento.append(placeSpannable);

                                    String placeEvents = events_place + "   ";
                                    SpannableString placeEventsSpannable = new SpannableString(placeEvents);
                                    placeEventsSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, placeEvents.length(), 0);
                                    evento.append(placeEventsSpannable);

                                    String city = "Città: ";
                                    SpannableString citySpannable = new SpannableString(city);
                                    citySpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, city.length(), 0);
                                    citySpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, city.length(), 0);
                                    evento.append(citySpannable);

                                    String cityEvents = events_city + "\n";
                                    SpannableString cityEventsSpannable = new SpannableString(cityEvents);
                                    cityEventsSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, cityEvents.length(), 0);
                                    evento.append(cityEventsSpannable);

                                    String date = "Data: ";
                                    SpannableString dateSpannable = new SpannableString(date);
                                    dateSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, date.length(), 0);
                                    dateSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, date.length(), 0);
                                    evento.append(dateSpannable);


                                    if(dataeventoin.equals(dataeventofi)) {
                                        String dateEvents = dataeventoin + "   ";
                                        SpannableString dateEventsSpannable = new SpannableString(dateEvents);
                                        dateEventsSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, dateEvents.length(), 0);
                                        evento.append(dateEventsSpannable);
                                    }

                                    else{
                                        String dateEvents = "Dal " +dataeventoin + " al "+ dataeventofi + "   ";
                                        SpannableString dateEventsSpannable = new SpannableString(dateEvents);
                                        dateEventsSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, dateEvents.length(), 0);
                                        evento.append(dateEventsSpannable);
                                    }

                                    String time = "Ora: ";
                                    SpannableString timeSpannable = new SpannableString(time);
                                    timeSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, time.length(), 0);
                                    timeSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, time.length(), 0);
                                    evento.append(timeSpannable);

                                    String timeEvents = orario + "\n";
                                    SpannableString timeEventsSpannable = new SpannableString(timeEvents);
                                    timeEventsSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, timeEvents.length(), 0);
                                    evento.append(timeEventsSpannable);

                                    String category = "Categoria: ";
                                    SpannableString categorySpannable = new SpannableString(category);
                                    categorySpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, category.length(), 0);
                                    categorySpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, category.length(), 0);
                                    evento.append(categorySpannable);

                                    String categoryEvents = events_category + "\n";
                                    SpannableString categoryEventsSpannable = new SpannableString(categoryEvents);
                                    categoryEventsSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, categoryEvents.length(), 0);
                                    evento.append(categoryEventsSpannable);

                                    TextView valueTV = new TextView(Search.this);
                                    TableRow.LayoutParams paramTV = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                                    paramTV.setMargins(5, 0, 0, 0);
                                    valueTV.setText(evento, TextView.BufferType.SPANNABLE);
                                    valueTV.setTextSize(20);
                                    valueTV.setLayoutParams(paramTV);

                                        imageView = new ImageView(getApplicationContext());
                                        String url_image = "http://abouttoday.altervista.org/immagini/"+events_id+".jpg";
                                        loadImageFromUrl( url_image );



                                        TableRow.LayoutParams size = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 172);
                                    imageView.setLayoutParams(size);

                                    View view = new View(Search.this);
                                    TableLayout.LayoutParams paramView = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 5);
                                    paramView.setMargins(0, 0, 0, 25);
                                    view.setBackgroundColor(Color.BLACK);
                                    view.getBackground().setAlpha(50);
                                    view.setLayoutParams(paramView);

                                    eventTextView = new TextView(Search.this);
                                    TableRow.LayoutParams paramText = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                                    eventTextView.setText(events_name);
                                    eventTextView.setTextSize(30);
                                    eventTextView.setTextColor(Color.WHITE);
                                    eventTextView.setBackgroundColor(Color.BLACK);
                                    eventTextView.getBackground().setAlpha(50);
                                    eventTextView.setPadding(10, 10, 10, 10);
                                    eventTextView.setLayoutParams(paramText);


                                    mFrameLayout.addView(imageView);
                                    mFrameLayout.addView(eventTextView);

                                    row1.addView(mFrameLayout);

                                    row.addView(valueTV);

                                    mTableLayout.addView(row1);
                                    mTableLayout.addView(row);
                                    mTableLayout.addView(view);
                                }


                            }


                                mChartLayout.addView(mTableLayout);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }

                    });

        MySingleton.getInstance(Search.this).addTorequestque(jsonArrayRequest);



                }
    }


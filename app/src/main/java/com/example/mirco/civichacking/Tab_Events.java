package com.example.mirco.civichacking;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.TextView.BufferType;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Tab_Events extends Fragment {

    LinearLayout mChartLayout;
    RelativeLayout mRelativeLayout;
    TableLayout mTableLayout;
    FrameLayout mFrameLayout;
    TextView eventTextView;
    TableRow row;
    TableRow row1;
    Intent intent;
    FloatingActionButton add;
    FloatingActionButton rec;
    String url = "http://abouttoday.altervista.org/selectall.php";
    JsonArrayRequest jsonArrayRequest;
    public static int TYPE_WIFI = 1;
    private static int TYPE_MOBILE = 2;
    private static  int TYPE_NOT_CONNECTED = 0;
    ImageView imageView;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void loadImageFromUrl(String url_image){
        Picasso.with( getActivity() ).load( url_image ).placeholder( R.mipmap.ic_launcher )
        .error( R.drawable.sfondo)
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                } );

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = getConnectivityStatusString(context);
            if(status.equals("No")) {
                mChartLayout.setVisibility(View.GONE);
                add.setVisibility(View.GONE);
                rec.setVisibility(View.GONE);
                mRelativeLayout.setVisibility(View.VISIBLE);

                Toast.makeText(getActivity(), "Connessione assente", Toast.LENGTH_LONG).show();
            }
            else {
                mChartLayout.setVisibility(View.VISIBLE);
                add.setVisibility(View.VISIBLE);
                rec.setVisibility(View.VISIBLE);
                mRelativeLayout.setVisibility(View.GONE);

            }
        }
    };

    public static String getConnectivityStatusString(Context context){
        int conn = getConnectivityStatus(context);
        String status = null;
        if(conn == TYPE_WIFI){
            status = "Wifi";
        }
        else if (conn == TYPE_MOBILE){
            status = "Mobile";
        }
        else if (conn == TYPE_NOT_CONNECTED){
            status = "No";
        }
        return status;
    }

    public static int getConnectivityStatus(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_events, container, false);

        final IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        getActivity().registerReceiver(broadcastReceiver, filter);

        mChartLayout = (LinearLayout) rootView.findViewById(R.id.chart_layout);
        mChartLayout.setBackgroundColor(Color.parseColor("#2d7cbe"));

        mRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.relativeLayout);

        mTableLayout = new TableLayout(getActivity());
        mTableLayout.setBackgroundColor(Color.parseColor("#2d7cbe"));
        mTableLayout.setColumnShrinkable(0, true);
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,5,10,5);
        mTableLayout.setLayoutParams(params);

        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject events = response.getJSONObject(i);
                                final Long events_id = events.getLong("id");
                                final String events_name = events.getString("name");
                                final String events_place = events.getString("place");
                                final String events_city = events.getString("city");
                                final String events_datein = events.getString("datein");
                                final String events_datefi = events.getString("datefi");
                                final String events_time = events.getString("time");
                                final String events_category = events.getString("category");
                                final String events_description = events.getString("description");


                                TableLayout.LayoutParams param = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                                row = new TableRow(getActivity());
                                row.setBackgroundColor(Color.WHITE);
                                row.setLayoutParams(param);


                                row1 = new TableRow(getActivity());
                                row1.setBackgroundColor(Color.parseColor( "#2d7cbe" ));

                                mFrameLayout = new FrameLayout(getActivity());
                                TableRow.LayoutParams paramFrame = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                                mFrameLayout.setLayoutParams(paramFrame);


                                String[] splitdatein = events_datein.split("-");
                                String day = splitdatein[2];
                                String month = splitdatein[1];
                                String year = splitdatein[0];

                                final String datain = day + "/" + month + "/" + year;

                                String[] splitdatefi = events_datefi.split("-");
                                String day2 = splitdatefi[2];
                                String month2 = splitdatefi[1];
                                String year2 = splitdatefi[0];

                                final String datafi = day2 + "/" + month2 + "/" + year2;

                                String [] splittime = events_time.split(":");
                                String hour = splittime[0];
                                String minute = splittime[1];
                                String second = splittime[2];

                                final String ora = hour + ":" + minute;


                                row.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        intent = new Intent(getActivity(), InfoActivity.class);
                                        intent.putExtra("id", events_id);
                                        intent.putExtra("name", events_name);
                                        intent.putExtra("place", events_place);
                                        intent.putExtra("city", events_city);
                                        intent.putExtra("datein", datain);
                                        intent.putExtra("datefi", datafi );
                                        intent.putExtra("time", ora);
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

                                String cityEvents = events_city + "\n" ;
                                SpannableString cityEventsSpannable = new SpannableString(cityEvents);
                                cityEventsSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, cityEvents.length(), 0);
                                evento.append(cityEventsSpannable);

                                String date = "Data: ";
                                SpannableString dateSpannable = new SpannableString(date);
                                dateSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, date.length(), 0);
                                dateSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, date.length(), 0);
                                evento.append(dateSpannable);

                                if(datain.equals(datafi)) {
                                    String dateEvents = datain + "   ";
                                    SpannableString dateEventsSpannable = new SpannableString(dateEvents);
                                    dateEventsSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, dateEvents.length(), 0);
                                    evento.append(dateEventsSpannable);
                                }
                                else{
                                    String dateEvents = "Dal " + datain + " al " + datafi + "   ";
                                    SpannableString dateEventsSpannable = new SpannableString(dateEvents);
                                    dateEventsSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, dateEvents.length(), 0);
                                    evento.append(dateEventsSpannable);
                                }

                                String time = "Ora: ";
                                SpannableString timeSpannable = new SpannableString(time);
                                timeSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, time.length(), 0);
                                timeSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, time.length(), 0);
                                evento.append(timeSpannable);

                                String timeEvents = ora + "\n";
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

                                TextView valueTV = new TextView(getActivity());
                                TableRow.LayoutParams paramTV = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                                paramTV.setMargins(5, 0, 0, 0);
                                valueTV.setText(evento, BufferType.SPANNABLE);
                                valueTV.setTextSize(20);
                                valueTV.setLayoutParams(paramTV);

                                imageView = new ImageView(getActivity());
                                String url_image = "http://abouttoday.altervista.org/immagini/"+events_id+".jpg";
                                loadImageFromUrl( url_image );

                                /*switch (events_category) {
                                    case "Fiera":
                                        imageView.setImageResource(R.drawable.rsz_expo);
                                        break;

                                    case "Manifestazione":
                                        imageView.setImageResource(R.drawable.rsz_frecce);
                                        break;

                                    case "Spettacolo":
                                        imageView.setImageResource(R.drawable.rsz_1sipario);
                                        break;

                                    case "Sport":
                                        imageView.setImageResource(R.drawable.sport);
                                        break;


                                }*/


                                TableRow.LayoutParams size = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 172 );
                                imageView.setLayoutParams(size);

                                View view = new View(getActivity());
                                TableLayout.LayoutParams paramView = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 5);
                                paramView.setMargins(0, 0, 0, 25);
                                view.setBackgroundColor(Color.BLACK);
                                view.getBackground().setAlpha(50);
                                view.setLayoutParams(paramView);

                                eventTextView = new TextView(getActivity());
                                TableRow.LayoutParams paramText = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                                eventTextView.setText(events_name);
                                eventTextView.setTextSize(30);
                                eventTextView.setTextColor(Color.WHITE);
                                eventTextView.setBackgroundColor(Color.BLACK);
                                eventTextView.getBackground().setAlpha(75);
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

        MySingleton.getInstance(getActivity()).addTorequestque(jsonArrayRequest);




        rec = (FloatingActionButton) rootView.findViewById(R.id.recyclButton);
        rec.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTask myTask = new MyTask(getActivity(), Tab_Events.this);
                myTask.execute();

            }
        });


        add = (FloatingActionButton) rootView.findViewById(R.id.adderButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Insert.class);
                Tab_Events.this.startActivity(i);
            }
        });

        return rootView;

    }

    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(broadcastReceiver);

    }
}


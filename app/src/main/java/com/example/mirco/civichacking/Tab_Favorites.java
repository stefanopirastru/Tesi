package com.example.mirco.civichacking;

/**
 * Created by mirco on 29/03/2018.
 */
import android.content.Intent;
import android.graphics.Color;

import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
import android.widget.TextView.BufferType;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Tab_Favorites extends Fragment {

    RelativeLayout relativeLayout;
    LinearLayout mChartLayout;
    TableLayout mTableLayout;
    FrameLayout mFrameLayout;
    TextView eventTextView;
    TableRow row, row1;
    EventsListDB db;
    Intent i;
    FloatingActionButton rec;
    ImageView imageView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new EventsListDB(getActivity());

    }

    private void loadImageFromUrl(String url_image){
        Picasso.with( getActivity() ).load( url_image ).placeholder( R.mipmap.ic_launcher )
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_favorites, container, false);

        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relatLayout);
        mChartLayout = (LinearLayout) rootView.findViewById(R.id.chart_layout);

        List<Events> events = db.getEvents();

        if(events.isEmpty()){
            TextView textView = new TextView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(5,15,0,0);
            textView.setLayoutParams(params);
            textView.setText("Nessun preferito ancora inserito");
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(30);
            relativeLayout.setBackgroundColor(Color.WHITE);
            mChartLayout.addView(textView);

        }

        else {

        relativeLayout.setBackgroundColor(Color.parseColor("#2d7cbe"));
        mChartLayout.setBackgroundColor(Color.parseColor("#2d7cbe"));

        mTableLayout = new TableLayout(getActivity());
        mTableLayout.setBackgroundColor(Color.parseColor("#2d7cbe"));
        mTableLayout.setColumnShrinkable(0, true);
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,5,10,5);
        mTableLayout.setLayoutParams(params);


            //List<Events> events = db.getEvents();
            for (final Events e : events) {


                TableLayout.LayoutParams param = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                row = new TableRow(getActivity());
                row.setBackgroundColor(Color.WHITE);
                row.setLayoutParams(param);


                row1 = new TableRow(getActivity());
                row1.setBackgroundColor(Color.WHITE);

                mFrameLayout = new FrameLayout(getActivity());
                TableRow.LayoutParams paramFrame = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                mFrameLayout.setLayoutParams(paramFrame);

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        i = new Intent(getActivity(), InfoFavorites.class);
                        i.putExtra("id", e.getEventsId());
                        i.putExtra("name", e.getName());
                        i.putExtra("place", e.getPlace());
                        i.putExtra("city", e.getCity());
                        i.putExtra("datein", e.getDatein());
                        i.putExtra("datefi", e.getDatefi());
                        i.putExtra("time", e.getTime());
                        i.putExtra("category", e.getCategory());
                        i.putExtra("description", e.getDescription());
                        startActivity(i);
                    }
                });


                SpannableStringBuilder evento = new SpannableStringBuilder();


                String place = "Luogo: ";
                SpannableString placeSpannable = new SpannableString(place);
                placeSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, place.length(), 0);
                placeSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, place.length(), 0);
                evento.append(placeSpannable);

                String placeEvents = e.getPlace() + "   ";
                SpannableString placeEventsSpannable = new SpannableString(placeEvents);
                placeEventsSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, placeEvents.length(), 0);
                evento.append(placeEventsSpannable);

                String city = "Città: ";
                SpannableString citySpannable = new SpannableString(city);
                citySpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, city.length(), 0);
                citySpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, city.length(), 0);
                evento.append(citySpannable);

                String cityEvents = e.getCity() + "\n";
                SpannableString cityEventsSpannable = new SpannableString(cityEvents);
                cityEventsSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, cityEvents.length(), 0);
                evento.append(cityEventsSpannable);

                String date = "Data: ";
                SpannableString dateSpannable = new SpannableString(date);
                dateSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, date.length(), 0);
                dateSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, date.length(), 0);
                evento.append(dateSpannable);

                if (e.getDatein().equals(e.getDatefi())) {
                    String dateEvents = e.getDatein() + "   ";
                    SpannableString dateEventsSpannable = new SpannableString(dateEvents);
                    dateEventsSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, dateEvents.length(), 0);
                    evento.append(dateEventsSpannable);
                } else {
                    String dateEvents = "Dal " + e.getDatein() + " al " + e.getDatefi() + "   ";
                    SpannableString dateEventsSpannable = new SpannableString(dateEvents);
                    dateEventsSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, dateEvents.length(), 0);
                    evento.append(dateEventsSpannable);
                }

                String time = "Ora: ";
                SpannableString timeSpannable = new SpannableString(time);
                timeSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, time.length(), 0);
                timeSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, time.length(), 0);
                evento.append(timeSpannable);

                String timeEvents = e.getTime() + "\n";
                SpannableString timeEventsSpannable = new SpannableString(timeEvents);
                timeEventsSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, timeEvents.length(), 0);
                evento.append(timeEventsSpannable);

                String category = "Categoria: ";
                SpannableString categorySpannable = new SpannableString(category);
                categorySpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, category.length(), 0);
                categorySpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, category.length(), 0);
                evento.append(categorySpannable);

                String categoryEvents = e.getCategory() + "\n";
                SpannableString categoryEventsSpannable = new SpannableString(categoryEvents);
                categoryEventsSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, categoryEvents.length(), 0);
                evento.append(categoryEventsSpannable);

                TextView valueTV = new TextView(getActivity());
                valueTV.setText(evento, BufferType.SPANNABLE);
                valueTV.setTextSize(20);

                valueTV.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                imageView = new ImageView(getActivity());
                String url_image = "http://abouttoday.altervista.org/immagini/"+e.getEventsId()+".jpg";
                loadImageFromUrl( url_image );



                TableRow.LayoutParams size = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 172);
                imageView.setLayoutParams(size);

                final View view = new View(getActivity());
                TableLayout.LayoutParams paramView = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 5);
                paramView.setMargins(0, 0, 0, 25);
                view.setBackgroundColor(Color.BLACK);
                view.getBackground().setAlpha(50);
                view.setLayoutParams(paramView);

                eventTextView = new TextView(getActivity());
                TableRow.LayoutParams paramText = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                eventTextView.setText(e.getName());
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
        }

        rec = (FloatingActionButton) rootView.findViewById(R.id.recyclButton);
        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTask myTask = new MyTask(getActivity(), Tab_Favorites.this);
                myTask.execute();

            }
        });

        return rootView;

    }

}

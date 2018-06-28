package com.example.mirco.civichacking;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EventsListDB {

    // database constants
    public static final String DB_NAME = "events.db";
    public static final int    DB_VERSION = 1;

    // task table constants
    public static final String EVENTS_TABLE = "events";

    public static final String EVENTS_ID = "_id";
    public static final int    EVENTS_ID_COL = 0;

    public static final String EVENTS_NAME = "events_name";
    public static final int    EVENTS_NAME_COL = 1;

    public static final String EVENTS_PLACE = "events_place";
    public static final int    EVENTS_PLACE_COL = 2;

    public static final String EVENTS_CITY = "events_city";
    public static final int    EVENTS_CITY_COL = 3;

    public static final String EVENTS_DATEIN = "events_datein";
    public static final int    EVENTS_DATEIN_COL = 4;

    public static final String EVENTS_DATEFI = "events_datefi";
    public static final int    EVENTS_DATEFI_COL = 5;

    public static final String EVENTS_TIME = "events_time";
    public static final int    EVENTS_TIME_COL = 6;

    public static final String EVENTS_CATEGORY= "events_category";
    public static final int    EVENTS_CATEGORY_COL = 7;

    public static final String EVENTS_DESCRIPTION= "events_description";
    public static final int    EVENTS_DESCRIPTION_COL = 8;

    // CREATE and DROP TABLE statements

    public static final String CREATE_EVENTS_TABLE =
            "CREATE TABLE " + EVENTS_TABLE + " (" +
                    EVENTS_ID         + " INTEGER PRIMARY KEY, " +
                    EVENTS_NAME       + " TEXT NOT NULL, " +
                    EVENTS_PLACE      + " TEXT NOT NULL, " +
                    EVENTS_CITY       + " TEXT NOT NULL, " +
                    EVENTS_DATEIN       + " TEXT NOT NULL, " +
                    EVENTS_DATEFI       + " TEXT NOT NULL, " +
                    EVENTS_TIME       + " TEXT NOT NULL, " +
                    EVENTS_CATEGORY     + " TEXT NOT NULL, " +
                    EVENTS_DESCRIPTION   + " TEXT);";

    public static final String DROP_EVENTS_TABLE =
            "DROP TABLE IF EXISTS " + EVENTS_TABLE;

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name,
                        CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create tables
            db.execSQL(CREATE_EVENTS_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {

            Log.d("Events list", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(EventsListDB.DROP_EVENTS_TABLE);
            onCreate(db);
        }
    }

    // database and database helper objects
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // constructor
    public EventsListDB(Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    // private methods
    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null)
            db.close();
    }

    private void closeCursor(Cursor cursor) {
        if (cursor != null)
            cursor.close();
    }

    // public methods


    public List<Events> getEvents() {
        List<Events> eventsList = new ArrayList<>();

        this.openWriteableDB();
        Cursor cursor = db.query(EVENTS_TABLE, null, null, null, null, null, EVENTS_DATEIN);

        if(cursor.moveToFirst()){
            do{
                Events events = new Events();
                events.setEventsId(cursor.getLong(0));
                events.setName(cursor.getString(1));
                events.setPlace(cursor.getString(2));
                events.setCity(cursor.getString(3));
                events.setDatein(cursor.getString(4));
                events.setDatefi(cursor.getString(5));
                events.setTime(cursor.getString(6));
                events.setCategory(cursor.getString(7));
                events.setDescription(cursor.getString(8));

                eventsList.add(events);
            }
            while (cursor.moveToNext());
        }

        return eventsList;
    }


    private static Events getEventsFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                Events events = new Events(
                        cursor.getLong(EVENTS_ID_COL),
                        cursor.getString(EVENTS_NAME_COL),
                        cursor.getString(EVENTS_PLACE_COL),
                        cursor.getString(EVENTS_CITY_COL),
                        cursor.getString(EVENTS_DATEIN_COL),
                        cursor.getString(EVENTS_DATEFI_COL),
                        cursor.getString(EVENTS_TIME_COL),
                        cursor.getString(EVENTS_CATEGORY_COL),
                        cursor.getString(EVENTS_DESCRIPTION_COL));

                return events;
            }
            catch(Exception e) {
                return null;
            }
        }
    }



    public long insertEvents(Events events) {
        ContentValues cv = new ContentValues();
        cv.put(EVENTS_ID, events.getEventsId());
        cv.put(EVENTS_NAME, events.getName());
        cv.put(EVENTS_PLACE, events.getPlace());
        cv.put(EVENTS_CITY, events.getCity());
        cv.put(EVENTS_DATEIN, events.getDatein());
        cv.put(EVENTS_DATEFI, events.getDatefi());
        cv.put(EVENTS_TIME, events.getTime());
        cv.put(EVENTS_CATEGORY, events.getCategory());
        cv.put(EVENTS_DESCRIPTION, events.getDescription());

        this.openWriteableDB();
        long rowID = db.insert(EVENTS_TABLE, null, cv);
        this.closeDB();

        return rowID;
    }


    public int deleteEvents(long id) {
        String where = EVENTS_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWriteableDB();
        int rowCount = db.delete(EVENTS_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }
}
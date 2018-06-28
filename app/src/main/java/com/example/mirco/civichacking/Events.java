package com.example.mirco.civichacking;

public class Events {

    private long eventsId;
    private String name;
    private String place;
    private String city;
    private String datein;
    private String datefi;
    private String time;
    private String category;
    private String description;


    public Events() {}

    public Events(String name, String place, String city, String datein, String datefi, String time, String category, String description) {
        this.name = name;
        this.place = place;
        this.city = city;
        this.datein = datein;
        this.datefi = datefi;
        this.time = time;
        this.category = category;
        this.description = description;
    }


    public Events(long eventsId, String name, String place, String city, String datein, String datefi, String time,  String category, String description) {
        this.eventsId = eventsId;
        this.name = name;
        this.place = place;
        this.city = city;
        this.datein = datein;
        this.datefi = datefi;
        this.time = time;
        this.category = category;
        this.description = description;

    }

    public long getEventsId() {
        return eventsId;
    }

    public void setEventsId(long eventsId) {
        this.eventsId = eventsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) { this.city = city; }

    public String getDatein() {
        return datein;
    }

    public void setDatein(String datein) {
        this.datein = datein;
    }

    public String getDatefi() {
        return datefi;
    }

    public void setDatefi(String datefi) {
        this.datefi = datefi;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
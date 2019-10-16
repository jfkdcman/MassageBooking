package com.example.massagebooker;

import java.text.DateFormat;
import java.util.ArrayList;

public class BookingDetails {

    private String massageType;
    private int massageTypeValue;
    private String date;
    private String time;
    private String location;

    public BookingDetails(String massageType, int massageTypeValue, String date, String time, String location) {
        this.massageType = massageType;
        this.massageTypeValue = massageTypeValue;
        this.date = date;
        this.time = time;
        this.location = location;
    }


    public String getMassageType() {
        return massageType;
    }

    public void setMassageType(String massageType) {
        this.massageType = massageType;
    }

    public int getMassageTypeValue() {
        return massageTypeValue;
    }

    public void setMassageTypeValue(int massageTypeValue) {
        this.massageTypeValue = massageTypeValue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}

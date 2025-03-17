package com.example.tothedestination;

import android.graphics.Bitmap;

import java.util.Date;

public class vacation {

    private int hoursFlight;
    private String country;
    private String ageOfChild;
    private String season;
    private long fromDate;
    private long toDate;
    private String keyUser;

    public vacation(String country,int hoursFlight, String ageOfChild, String season, long fromDate, long toDate, String keyUser) {
        this.hoursFlight = hoursFlight;
        this.ageOfChild = ageOfChild;
        this.season = season;
        this.country=country;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.keyUser = keyUser;
    }

    public String getCountry() {
        return country;
    }

    public int getHoursFlight() {
        return hoursFlight;
    }

    public String getAgeOfChild() {
        return ageOfChild;
    }

    public String getSeason() {
        return season;
    }
    public String getKeyUser() {
        return keyUser;
    }

    public long getFromDate() {
        return fromDate;
    }

    public long getToDate() {
        return toDate;
    }

    public void setHoursFlight(int hoursFlight) {
        this.hoursFlight = hoursFlight;
    }

    public void setAgeOfChild(String ageOfChild) {
        this.ageOfChild = ageOfChild;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public void setFromDate(long fromDate) {
        this.fromDate = fromDate;
    }

    public void setKeyUser(String keyUser) {
        this.keyUser = keyUser;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public void setToDate(long toDate) {
        this.toDate = toDate;
    }
}

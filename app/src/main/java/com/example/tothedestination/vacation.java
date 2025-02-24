package com.example.tothedestination;

import android.graphics.Bitmap;

import java.util.Date;

public class vacation {

    private int hoursFlight;
    private String attraction;
    private String ageOfChild;
    private String season;
    private Date fromDate;
    private Date toDate;
    private String keyUser;

    public vacation(int hoursFlight, String attraction, String ageOfChild, String season, Date fromDate, Date toDate, String keyUser) {
        this.hoursFlight = hoursFlight;
        this.attraction = attraction;
        this.ageOfChild = ageOfChild;
        this.season = season;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.keyUser = keyUser;
    }

    public String getAttraction() {
        return attraction;
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

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setHoursFlight(int hoursFlight) {
        this.hoursFlight = hoursFlight;
    }

    public void setAttraction(String attraction) {
        this.attraction = attraction;
    }

    public void setAgeOfChild(String ageOfChild) {
        this.ageOfChild = ageOfChild;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public void setKeyUser(String keyUser) {
        this.keyUser = keyUser;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}

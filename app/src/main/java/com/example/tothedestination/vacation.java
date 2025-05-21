package com.example.tothedestination;

import android.graphics.Bitmap;

import java.util.Date;

public class vacation {

    private int hoursFlight;
    private String airport;
    private double coordinatesX;
    private double coordinatesY;
    private String country;
    private String ageOfChild;
    private String season;
    private long fromDate;
    private long toDate;
    private String keyUser;
    private Bitmap bitmap;
    private String attraction;
    private String key;

    public vacation(String country, String attraction,int hoursFlight, String ageOfChild, String season, long fromDate, long toDate, String keyUser, String airport, double coordinatesX, double coordinatesY, Bitmap bitmap) {
        this.hoursFlight = hoursFlight;
        this.attraction = attraction;
        this.ageOfChild = ageOfChild;
        this.season = season;
        this.country=country;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.keyUser = keyUser;
        this.airport=airport;
        this.coordinatesX=coordinatesX;
        this.coordinatesY=coordinatesY;
        this.bitmap=bitmap;

    }

    public vacation(String country, String attraction,int hoursFlight, String ageOfChild, String season, long fromDate, long toDate, String keyUser, String airport, double coordinatesX, double coordinatesY) {
        this.hoursFlight = hoursFlight;
        this.attraction = attraction;
        this.ageOfChild = ageOfChild;
        this.season = season;
        this.country=country;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.keyUser = keyUser;
        this.airport=airport;
        this.coordinatesX=coordinatesX;
        this.coordinatesY=coordinatesY;

    }
    public vacation()
    {

    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey()
    {
        return this.key;
    }

    public String getAttraction() {
        return attraction;
    }

    public void setAttraction(String attracion) {
        this.attraction = attracion;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getCountry() {
        return country;
    }

    public String getAirport() {
        return airport;
    }
    public double getCoordinatesX() {
        return coordinatesX;
    }
    public double getCoordinatesY() {
        return coordinatesY;
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
    public void setAirport(String airport) {
        this.airport = airport;
    }

    public void setCoordinatesX(double coordinatesX) {
        this.coordinatesX = coordinatesX;
    }
    public void setCoordinatesY(double coordinatesY) {
        this.coordinatesY = coordinatesY;
    }

    public void setToDate(long toDate) {
        this.toDate = toDate;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}

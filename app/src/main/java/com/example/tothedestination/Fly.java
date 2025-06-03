package com.example.tothedestination;

import android.graphics.Bitmap;

public class Fly {

    private int hoursFlight;
    private String Airport;
    private double CoordinatesX;
    private double CoordinatesY;
    private String attraction;
    private String ageOfChild;
    private String season;
    private String country;
    private boolean isChecked;
    private Bitmap bitmap;
    private String key;

    public Fly(int hoursFlight, String attraction, String country, String ageOfChild, String season, Bitmap bitmap,String key, String airport, double coordinatesX, double coordinatesY)
    {
        this.hoursFlight=hoursFlight;
        this.attraction=attraction;
        this.country=country;
        this.ageOfChild=ageOfChild;
        this.season=season;
        this.bitmap=bitmap;
        this.key= key;
        this.isChecked=false;
        this.Airport =airport;
        this.CoordinatesX =coordinatesX;
        this.CoordinatesY =coordinatesY;
    }

    public Fly(int hoursFlight, String attraction, String country, String ageOfChild, String season,String key, String airport, double coordinatesX, double coordinatesY)
    {
        this.hoursFlight=hoursFlight;
        this.attraction=attraction;
        this.country=country;
        this.ageOfChild=ageOfChild;
        this.season=season;
        this.bitmap=null;
        this.key= key;
        this.isChecked=false;
        this.Airport =airport;
        this.CoordinatesX =coordinatesX;
        this.CoordinatesY =coordinatesY;
    }

    public Fly()
    {

    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getAirport() {
        return Airport;
    }

    public double getCoordinatesX() {
        return CoordinatesX;
    }
    public double getCoordinatesY() {
        return CoordinatesY;
    }

    public String getKey() {return this.key;}

    public void setKey(String key) {
        this.key = key;
    }

    public int getHoursFlight() {
        return hoursFlight;
    }

    public String getAttraction() {
        return attraction;
    }

    public String getCountry() {
        return country;
    }
    public String getAgeOfChild() {
        return ageOfChild;
    }
    public String getSeason() {
        return season;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setHoursFlight(int hoursFlight) {
        this.hoursFlight = hoursFlight;
    }

    public void setAttraction(String attraction) {
        this.attraction = attraction;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public void setAgeOfChild(String ageOfChild) {
        this.ageOfChild = ageOfChild;
    }
    public void setSeason(String season) {
        this.season = season;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public void setAirport(String airport) {
        this.Airport = airport;
    }

    public void setCoordinatesX(double coordinatesX) {
        this.CoordinatesX = coordinatesX;
    }
    public void setCoordinatesY(double coordinatesY) {
        this.CoordinatesY = coordinatesY;
    }
}

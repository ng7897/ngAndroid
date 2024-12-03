package com.example.tothedestination;

import android.graphics.Bitmap;

public class Fly {

    private int hoursFlight;
    private String attraction;
    private String ageOfChild;
    private String season;
    private String country;
    private Bitmap bitmap;

    public Fly(int hoursFlight, String attraction, String country, String ageOfChild, String season, Bitmap bitmap)
    {
        this.hoursFlight=hoursFlight;
        this.attraction=attraction;
        this.country=country;
        this.ageOfChild=ageOfChild;
        this.season=season;
        this.bitmap=bitmap;
    }

    public Fly(int hoursFlight, String attraction, String country, String ageOfChild, String season)
    {
        this.hoursFlight=hoursFlight;
        this.attraction=attraction;
        this.country=country;
        this.ageOfChild=ageOfChild;
        this.season=season;
        this.bitmap=null;
    }

    public Fly()
    {

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
}

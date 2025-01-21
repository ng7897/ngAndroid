package com.example.tothedestination;

import android.graphics.Bitmap;

public class Fly {

    private int hoursFlight;
    private String attraction;
    private String ageOfChild;
    private String season;
    private String country;
    private Bitmap bitmap;
    private String key;

    public Fly(int hoursFlight, String attraction, String country, String ageOfChild, String season, Bitmap bitmap,String key)
    {
        this.hoursFlight=hoursFlight;
        this.attraction=attraction;
        this.country=country;
        this.ageOfChild=ageOfChild;
        this.season=season;
        this.bitmap=bitmap;
        this.key= key;
    }

    public Fly(int hoursFlight, String attraction, String country, String ageOfChild, String season,String key)
    {
        this.hoursFlight=hoursFlight;
        this.attraction=attraction;
        this.country=country;
        this.ageOfChild=ageOfChild;
        this.season=season;
        this.bitmap=null;
        this.key= key;
    }

    public Fly()
    {

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
}

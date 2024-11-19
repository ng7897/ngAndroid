package com.example.tothedestination;

import android.graphics.Bitmap;

public class Fly {

    private int hoursFlight;
    private String attraction;
    private String country;
    private Bitmap bitmap;

    public Fly(int hoursFlight, String attraction, String country, Bitmap bitmap)
    {
        this.hoursFlight=hoursFlight;
        this.attraction=attraction;
        this.country=country;
        this.bitmap=bitmap;
    }

    public Fly(int hoursFlight, String attraction, String country)
    {
        this.hoursFlight=hoursFlight;
        this.attraction=attraction;
        this.country=country;
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

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}

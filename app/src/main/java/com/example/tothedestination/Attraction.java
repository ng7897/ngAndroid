package com.example.tothedestination;

import java.io.Serializable;

public class Attraction implements Serializable {

    //הגדרת תכונות המחלקה attraction
    private String attName;
    private Double CoordinatesX;
    private Double CoordinatesY;
    private String explain;
    private String image;
    private boolean isChecked;
    private String key;

    //יצירת פעולה בונה והכנסת הנתונים שהיא מקבלת לתכונותיה עם תמונה
    public Attraction(String attName, Double CoordinatesX, Double CoordinatesY, String explain, String image, String key) {
        this.attName = attName;
        this.CoordinatesX = CoordinatesX;
        this.CoordinatesY = CoordinatesY;
        this.explain = explain;
        this.image = image;
        this.isChecked = false;
        this.key=key;
    }
    //יצירת פעולה בונה והכנסת הנתונים שהיא מקבלת לתכונותיה
    public Attraction(String attName, Double CoordinatesX, Double CoordinatesY, String explain, String key) {
        this.attName = attName;
        this.CoordinatesX = CoordinatesX;
        this.CoordinatesY = CoordinatesY;
        this.explain = explain;
        this.isChecked = false;
        this.key=key;

    }
    //עולה בונה ריקה כדי שנוכל להתשמש בfirebase
    public Attraction()
    {

    }

    //פעולות get set
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isChecked() {
        return isChecked;
    }
    public String getImage() {
        return image;
    }
    public String getAttName() {
        return attName;
    }

    public void setAttName(String attName) {
        this.attName = attName;
    }

    public Double getCoordinatesX() {
        return CoordinatesX;
    }

    public void setCoordinatesX(Double CoordinatesX) {
        this.CoordinatesX = CoordinatesX;
    }

    public Double getCoordinatesY() {
        return CoordinatesY;
    }

    public void setCoordinatesY(Double CoordinatesY) {
        this.CoordinatesY = CoordinatesY;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
    public void setBitmap(String image) {
        this.image = image;
    }
    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}

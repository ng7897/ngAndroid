package com.example.tothedestination;

import android.graphics.Bitmap;

public class attraction {

    private String attName;
    private Double CoordinatesX;
    private Double CoordinatesY;
    private String explain;
    private Bitmap bitmap;

    public attraction(String attName, Double CoordinatesX, Double CoordinatesY, String explain, Bitmap bitmap) {
        this.attName = attName;
        this.CoordinatesX = CoordinatesX;
        this.CoordinatesY = CoordinatesY;
        this.explain = explain;
        this.bitmap = bitmap;

    }

    public attraction(String attName, Double CoordinatesX, Double CoordinatesY, String explain) {
        this.attName = attName;
        this.CoordinatesX = CoordinatesX;
        this.CoordinatesY = CoordinatesY;
        this.explain = explain;

    }
    public attraction()
    {

    }

    public Bitmap getBitmap() {
        return bitmap;
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
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}

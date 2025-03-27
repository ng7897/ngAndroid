package com.example.tothedestination;

import android.graphics.Bitmap;

public class Attraction {

    private String nameAtt;
    private Double coordinatesX;
    private Double coordinatesY;
    private String explain;
    private Bitmap bitmap;

    public Attraction(String nameAtt, Double coordinatesX, Double coordinatesY, String explain, Bitmap bitmap) {
        this.nameAtt = nameAtt;
        this.coordinatesX = coordinatesX;
        this.coordinatesY = coordinatesY;
        this.explain = explain;
        this.bitmap = bitmap;

    }

    public Attraction(String nameAtt, Double coordinatesX, Double coordinatesY, String explain) {
        this.nameAtt = nameAtt;
        this.coordinatesX = coordinatesX;
        this.coordinatesY = coordinatesY;
        this.explain = explain;

    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public String getNameAtt() {
        return nameAtt;
    }

    public void setNameAtt(String nameAtt) {
        this.nameAtt = nameAtt;
    }

    public Double getCoordinatesX() {
        return coordinatesX;
    }

    public void setCoordinatesX(Double coordinatesX) {
        this.coordinatesX = coordinatesX;
    }

    public Double getCoordinatesY() {
        return coordinatesY;
    }

    public void setCoordinatesY(Double coordinatesY) {
        this.coordinatesY = coordinatesY;
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

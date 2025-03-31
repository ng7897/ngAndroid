package com.example.tothedestination;

import java.io.Serializable;

public class Attraction implements Serializable {

    private String attName;
    private Double CoordinatesX;
    private Double CoordinatesY;
    private String explain;
    private String image;
    private boolean isChecked;

    public Attraction(String attName, Double CoordinatesX, Double CoordinatesY, String explain, String image) {
        this.attName = attName;
        this.CoordinatesX = CoordinatesX;
        this.CoordinatesY = CoordinatesY;
        this.explain = explain;
        this.image = image;
        this.isChecked = false;
    }

    public Attraction(String attName, Double CoordinatesX, Double CoordinatesY, String explain) {
        this.attName = attName;
        this.CoordinatesX = CoordinatesX;
        this.CoordinatesY = CoordinatesY;
        this.explain = explain;
        this.isChecked = false;

    }
    public Attraction()
    {

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

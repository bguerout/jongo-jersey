package com.xebia.bigdata.data;

public class Coordinates {

    private double[] coordinates;

    //jackson
    private Coordinates() {
    }

    public double getLongitude() {
        if (coordinates != null) {
            return coordinates[0];
        }
        return 0;
    }

    public double getLatitude() {
        if (coordinates != null) {
            return coordinates[1];
        }
        return 0;
    }
}

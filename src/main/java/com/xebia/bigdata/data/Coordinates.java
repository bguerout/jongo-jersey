package com.xebia.bigdata.data;

public class Coordinates {

    private double[] coordinates;

    //used by jackson
    private Coordinates() {
    }

    public Coordinates(double[] coordinates) {
        this.coordinates = coordinates;
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

    public String toString() {
        return "[" + getLatitude() + "," + getLongitude() + "]";
    }
}

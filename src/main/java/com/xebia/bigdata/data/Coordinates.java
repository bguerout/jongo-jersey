package com.xebia.bigdata.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinates {

    private double[] coordinates;

    @JsonCreator
    public Coordinates(@JsonProperty("coordinates") double[] coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates(String latlng) {
        String[] split = latlng.split(",");
        double lng = Double.parseDouble(split[0]);
        double lat = Double.parseDouble(split[1]);
        coordinates = new double[]{lng, lat};
    }

    public double getLong() {
        if (coordinates != null) {
            return coordinates[0];
        }
        return 0;
    }

    public double getLat() {
        if (coordinates != null) {
            return coordinates[1];
        }
        return 0;
    }
}

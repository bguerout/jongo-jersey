package com.xebia.bigdata.data;

import java.util.ArrayList;
import java.util.List;

public class Heatmap {

    private final List<String> coords;

    public Heatmap(List<String> coords) {
        this.coords = coords;

    }

    public List<Coordinates> getCoordinates() {
        List<Coordinates> coordinates = new ArrayList<Coordinates>();
        for (String coord : coords) {
            String[] split = coord.split(",");
            double lng = Double.parseDouble(split[0]);
            double lat = Double.parseDouble(split[1]);
            coordinates.add(new Coordinates(new double[]{lng, lat}));
        }
        return coordinates;
    }
}

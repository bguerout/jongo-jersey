package com.xebia.bigdata.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Tweet {

    public Coordinates coordinates;
    public Date date;

    @JsonCreator
    public Tweet(@JsonProperty("coordinates") Coordinates coordinates, @JsonProperty("date") Date date) {
        this.coordinates = coordinates;
        this.date = date;
    }
}

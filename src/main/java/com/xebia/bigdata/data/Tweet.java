package com.xebia.bigdata.data;

import java.util.Date;

public class Tweet {

    public Coordinates coordinates;
    public Date date;

    public Tweet(Coordinates coordinates, Date date) {
        this.coordinates = coordinates;
        this.date = date;
    }

    //used by jackson
    public Tweet() {
    }

}

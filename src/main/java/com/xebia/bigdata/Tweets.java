package com.xebia.bigdata;

import com.xebia.bigdata.data.Coordinates;
import com.xebia.bigdata.data.GeoNearResults;
import com.xebia.bigdata.data.Stat;
import com.xebia.bigdata.data.Tweet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tweets {

    public static final int MAX_TWEETS = 1000;
    public static final double RADIUS = (double) 100 / 6371;
    public static final String MONGO_URI = "mongo-1.aws.xebiatechevent.info";


    public static GeoNearResults findNearest(double lat, double lng, long limit) {
        return new GeoNearResults();
    }

    public static List<Tweet> findInArea(List<Coordinates> coordinates) {
        return new ArrayList<Tweet>();
    }

    public static List<Tweet> get(double lat, double lng, Date start, Date end) {
        return new ArrayList<Tweet>();
    }

    public static List<Stat> tagsPerLang() {
        return new ArrayList<Stat>();
    }
}

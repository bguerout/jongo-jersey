package com.xebia.bigdata.data;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Tweets {

    private static Jongo jongo;

    static {
        try {
            Mongo mongo = new MongoClient("mongo-1.aws.xebiatechevent.info");
            DB db = mongo.getDB("tweets");
            jongo = new Jongo(db);
        } catch (UnknownHostException e) {
            throw new RuntimeException("Unable to reach mongo database", e);
        }
    }

    public static List<Tweet> get(double lat, double lng, Date start, Date end) {
        MongoCollection collection = jongo.getCollection("tweets");
        double radius = (double)100/6371;
        Iterable<Tweet> tweets = collection
                .find("{ coordinates : { $geoWithin :{ $centerSphere: [ [ #,# ], #] } } }", lng, lat, radius)
                .projection("{coordinates:1,date:1}")
                .limit(5000)
                .as(Tweet.class);
        return newArrayList(tweets);
    }

    public static Heatmap getWorldwide(Date start, Date end) {
        MongoCollection collection = jongo.getCollection("tweets");
        List<String> coords = collection
                .distinct("rcoords")
                .query("{date:{$gte:#,$lte:#}}", start, end)
                .as(String.class);
        return new Heatmap(coords);
    }

}

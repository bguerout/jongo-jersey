package com.xebia.bigdata.data;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import java.net.UnknownHostException;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Tweets {

    private static Jongo jongo;

    static {
        try {
            Mongo mongo = new MongoClient();
            DB db = mongo.getDB("xebia");
            jongo = new Jongo(db);
        } catch (UnknownHostException e) {
            throw new RuntimeException("Unable to reach mongo database", e);
        }
    }

    public static List<Tweet> get(int limit, int skip) {
        MongoCollection collection = jongo.getCollection("tweets");
        Iterable<Tweet> tweets = collection
                .find()
                //.projection("{coordinates:1,date:1}")
                .limit(limit)
                .skip(skip)
                .as(Tweet.class);
        return newArrayList(tweets);
    }

    public static Heatmap distinct() {
        MongoCollection collection = jongo.getCollection("tweets");
        List<String> coords = collection.distinct("rcoords").as(String.class);
        return new Heatmap(coords);
    }

}

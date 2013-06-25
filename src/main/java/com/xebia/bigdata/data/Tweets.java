package com.xebia.bigdata.data;

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
            jongo = new Jongo(mongo.getDB("xebia"));
        } catch (UnknownHostException e) {
            throw new RuntimeException("Unable to reach mongo database", e);
        }
    }

    public static List<Tweet> get(int limit) {
        MongoCollection collection = jongo.getCollection("tweets");
        Iterable<Tweet> tweets = collection
                .find()
                .projection("{coordinates:1,date:1,'entities.hashtags.text':1}")
                .limit(limit)
                .as(Tweet.class);
        return newArrayList(tweets);
    }

}

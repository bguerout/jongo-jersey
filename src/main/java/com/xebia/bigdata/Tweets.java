package com.xebia.bigdata;

import com.google.common.base.Joiner;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.xebia.bigdata.data.Coordinates;
import com.xebia.bigdata.data.GeoNearResults;
import com.xebia.bigdata.data.Stat;
import com.xebia.bigdata.data.Tweet;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Tweets {

    public static final int MAX_TWEETS = 1000;
    private static Jongo jongo;

    public static final double RADIUS = (double) 100 / 6371;

    static {
        try {
            Mongo mongo = new MongoClient("mongo-1.aws.xebiatechevent.info");
            DB db = mongo.getDB("tweets");
            jongo = new Jongo(db);
        } catch (UnknownHostException e) {
            throw new RuntimeException("Unable to reach mongo database", e);
        }
    }

    public static List<Tweet> heatmap(double lat, double lng, Date start, Date end) {
        MongoCollection collection = jongo.getCollection("tweets");
        Iterable<Tweet> tweets = collection
                .find("{ date:{$gte:#,$lte:#}, coordinates : { $geoWithin :{ $centerSphere: [ [ #,# ], #] } } }", start, end, lng, lat, RADIUS)
                .projection("{coordinates:1,date:1}")
                .limit(40000)
                .as(Tweet.class);
        return newArrayList(tweets);
    }

    public static GeoNearResults findNearest(double lat, double lng, long limit) {
        //db.runCommand({geoNear : "tweets", near : {type:"Point",coordinates : [2.1840906,48.8399779]},spherical : true,limit : 5})
        GeoNearResults result = jongo.runCommand("{geoNear : 'tweets', near : {type:'Point',coordinates : [#,#]},spherical : true,limit : #}", lng, lat, limit)
                .as(GeoNearResults.class);

        return result;
    }

    public static List<Tweet> findInArea(List<Coordinates> coordinates) {
        //db.tweets.find( {"coordinates" :{ $geoWithin : { $geometry :{type : "Polygon" , coordinates : [[[25.0974512743628,86.8965517241379],[-81.2293853073463,86.8965517241379],[-81.2293853073463,15.1124437781109],[25.0974512743628,15.1124437781109],[25.0974512743628,86.8965517241379]]] } } } })
        Joiner joiner = Joiner.on(",");
        String req = joiner.join(coordinates);

        Iterable<Tweet> tweets = jongo.getCollection("tweets")
                .find("{'coordinates' :{ $geoWithin : { $geometry :{type : 'Polygon' , coordinates : [[" + req + "]] } } } }")
                .limit(MAX_TWEETS)
                .as(Tweet.class);

        return newArrayList(tweets);
    }

    public static List<Stat> tagsPerLang() {
        MongoCollection collection = jongo.getCollection("tweets");
        return collection
                .aggregate("{$match:{'entities.hashtags':{$ne:[]}}}")
                .and("{ $project : {lang : 1,  tags:'$entities.hashtags.text'} }")
                .and("{ $unwind : '$tags' }")
                .and("{ $group : {_id : '$lang',nbTags : { $sum : 1 }} }")
                .as(Stat.class);
    }
}

package org.jongo.jersey.resource;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.jongo.Jongo;

import java.net.UnknownHostException;

public class JongoHolder {

    public static Jongo getInstance(String dbname) {
        Mongo mongo = LocalMongo.instance;
        return new Jongo(mongo.getDB(dbname));
    }

    private static class LocalMongo {

        private static Mongo instance = getLocalInstance();

        private static Mongo getLocalInstance() {
            try {
                return new MongoClient();
            } catch (UnknownHostException e) {
                throw new RuntimeException("Unable to reach mongo database test instance", e);
            }
        }
    }
}

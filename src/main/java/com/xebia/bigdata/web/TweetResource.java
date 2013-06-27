package com.xebia.bigdata.web;

import com.xebia.bigdata.Tweets;
import com.xebia.bigdata.data.Coordinates;
import com.xebia.bigdata.data.GeoNearResults;
import com.xebia.bigdata.data.Stat;
import com.xebia.bigdata.data.Tweet;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("tweets")
public class TweetResource {

    @GET
    @Path("nearest")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findNearest(@QueryParam("lat") double lat,
                                @QueryParam("lng") double lng,
                                @QueryParam("limit") @DefaultValue("10") long limit) {


        GeoNearResults nearest = Tweets.findNearest(lat, lng, limit);

        return Response.ok(new GenericEntity<GeoNearResults>(nearest) {
        }).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("area")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findInArea(MultivaluedMap<String, String> formParams) {

        List<Coordinates> coordinates = new ArrayList<Coordinates>();
        List<String> points = formParams.get("points[]");
        for (String point : points) {
            Coordinates c = new Coordinates(new double[]{Double.parseDouble(point.split(",")[0]), Double.parseDouble(point.split(",")[1])});
            coordinates.add(c);
        }

        List<Tweet> tweets = Tweets.findInArea(coordinates);

        return sendResponse(tweets);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("lat") double lat,
                        @QueryParam("lng") double lng,
                        @QueryParam("start") long start,
                        @QueryParam("end") long end) {

        Date startAt = new Date(start);
        Date endAt = new Date(end);

        List<Tweet> tweets = Tweets.get(lat, lng, startAt, endAt);

        return sendResponse(tweets);
    }

    @GET
    @Path("stats")
    @Produces(MediaType.APPLICATION_JSON)
    public Response stats() {

        List<Stat> stats = Tweets.tagsPerLang();

        return sendResponse(stats);
    }

    private <T> Response sendResponse(List<T> docs) {
        GenericEntity<List<T>> entity = new GenericEntity<List<T>>(docs) {
        };
        return Response.ok(entity).build();
    }
}

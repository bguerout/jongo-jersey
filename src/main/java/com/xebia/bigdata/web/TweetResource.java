package com.xebia.bigdata.web;

import com.google.common.collect.Lists;
import com.xebia.bigdata.Tweets;
import com.xebia.bigdata.data.*;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Path("tweets")
public class TweetResource {

    /**
     * ex http://localhost:8080/tweets?limit=13000&skip=130000
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("lat") double lat,
                        @QueryParam("lng") double lng,
                        @QueryParam("start") long start,
                        @QueryParam("end") long end,
                        @QueryParam("search") String search) {

        Date startAt = new Date(start);
        Date endAt = new Date(end);

        List<Tweet> tweets = Tweets.get(lat, lng, startAt, endAt);

        return sendResponse(tweets);
    }

    /**
     * ex http://localhost:8080/tweets/heatmap
     */
    @GET
    @Path("stats")
    @Produces(MediaType.APPLICATION_JSON)
    public Response stats() {

        List<Stat> stats = Lists.newArrayList(new Stat("bg", 619));

        return sendResponse(Tweets.tagsPerLang());
    }

    /**
     * ex http://localhost:8080/tweets/heatmap
     */
    @GET
    @Path("worldwide")
    @Produces(MediaType.APPLICATION_JSON)
    public Response worldwide(@QueryParam("start") long start,
                              @QueryParam("end") long end) {

        Date startAt = new Date(start);
        Date endAt = new Date(end);

        List<Coordinates> coordinates = Tweets.getWorldwide(startAt, endAt).getCoordinates();

        return sendResponse(coordinates);
    }

    /**
     * ex http://localhost:8080/tweets/nearest?lat=2&lng=48.5&limit=15
     */
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
    public Response findInArea(MultivaluedMap<String, String> formParams){
        RequestPolygon requestPolygon = new RequestPolygon();
        List<String> points = formParams.get("points[]");
        for (String point : points) {
            Coordinates c = new Coordinates(new double[]{Double.parseDouble(point.split(",")[0]),Double.parseDouble(point.split(",")[1])});
            requestPolygon.coordinatesList.add(c);
        }

        List<Tweet> tweets = Tweets.findInArea(requestPolygon);

        return sendResponse(tweets);
    }

    private <T> Response sendResponse(List<T> docs) {
        GenericEntity<List<T>> entity = new GenericEntity<List<T>>(docs) {
        };
        return Response.ok(entity).build();
    }
}

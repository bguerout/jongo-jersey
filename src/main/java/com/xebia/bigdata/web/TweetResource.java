package com.xebia.bigdata.web;

import com.xebia.bigdata.data.Coordinates;
import com.xebia.bigdata.data.Tweet;
import com.xebia.bigdata.data.Tweets;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
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
    public Response get(@QueryParam("lat") String lat,
                        @QueryParam("lng") String lng,
                        @QueryParam("start") long start,
                        @QueryParam("end") long end) {

        Date startAt = new Date(start);
        Date endAt = new Date(end);

        List<Tweet> tweets = Tweets.get(200, 0);

        return sendResponse(tweets);
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

    private <T> Response sendResponse(List<T> docs) {
        GenericEntity<List<T>> entity = new GenericEntity<List<T>>(docs) {
        };
        return Response.ok(entity).build();
    }
}

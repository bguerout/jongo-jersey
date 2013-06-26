package com.xebia.bigdata.web;

import com.google.common.collect.Lists;
import com.xebia.bigdata.data.Coordinates;
import com.xebia.bigdata.data.Tweet;

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
    public Response get(@QueryParam("limit") int limit, @QueryParam("skip") int skip) {

        Tweet tweet = new Tweet(new Coordinates(new double[]{2.351074, 48.842125}), new Date());
        List<Tweet> tweets = Lists.newArrayList(tweet);
        return sendResponse(tweets);
    }

    /**
     * ex http://localhost:8080/tweets/heatmap
     */
    @GET
    @Path("heatmap")
    @Produces(MediaType.APPLICATION_JSON)
    public Response heatmap() {

        List<Coordinates> coords = Lists.newArrayList(new Coordinates("2,48"), new Coordinates("3,48"));
        return sendResponse(coords);
    }

    private <T> Response sendResponse(List<T> docs) {
        GenericEntity<List<T>> entity = new GenericEntity<List<T>>(docs) {
        };
        return Response.ok(entity).build();
    }

}

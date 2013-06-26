package com.xebia.bigdata.web;

import com.xebia.bigdata.data.Tweet;
import com.xebia.bigdata.data.Tweets;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("tweets")
public class TweetResource {

    /**
     * ex http://localhost:8080/tweets?limit=13000&skip=130000
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("lat") String lat, @QueryParam("lng") String lng) {
        List<Tweet> tweets = Tweets.get(200, 0);
        return sendResponse(tweets);
    }

    /**
     * ex http://localhost:8080/tweets/heatmap
     */
    @GET
    @Path("heatmap")
    @Produces(MediaType.APPLICATION_JSON)
    public Response heatmap() {
        return sendResponse(Tweets.distinct().getCoordinates());
    }

    private <T> Response sendResponse(List<T> docs) {
        GenericEntity<List<T>> entity = new GenericEntity<List<T>>(docs) {
        };
        return Response.ok(entity).build();
    }

}

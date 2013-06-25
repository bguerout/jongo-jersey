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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("limit") int limit) {

        List<Tweet> tweets = Tweets.get(limit);

        GenericEntity<List<Tweet>> entity = new GenericEntity<List<Tweet>>(tweets) {
        };
        return Response.ok(entity).build();
    }

}

package org.jongo.jersey.data;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.jersey.representation.ServerProduct;
import org.jongo.jersey.resource.JongoHolder;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Products {

    public static MongoCollection products() {
        return JongoHolder.getInstance("xebia").getCollection("products");
    }

    public static List<ServerProduct> get() {
        return newArrayList(products().find().as(ServerProduct.class));
    }

    public static ServerProduct get(String id) {
        if (ObjectId.isValid(id))
            return products().findOne(new ObjectId(id)).as(ServerProduct.class);
        else
            return null;
    }

    public static ServerProduct put(ServerProduct product) {
        products().save(product);
        return product;
    }

    public static void delete(String id) {
        products().remove(new ObjectId(id));
    }
}

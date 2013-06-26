Xebia Bigdata Mongo
===================

Import tweets into mongo:

```sh
mongorestore -d xebia -c tweets --drop dump/xebia/tweets.bson
```

Commands to prepare collection (already done in previous dump)
```sh
 db.tweets.remove({ coordinates : null })
 db.tweets.ensureIndex({ coordinates : "2dsphere" })
 db.tweets.find({}).forEach(function(doc){ doc.date = new Date(doc.created_at); db.tweets.save(doc);})
```


Maven stuff

Declare jetty into settings.xml
```xml
<pluginGroups>
    <pluginGroup>org.mortbay.jetty</pluginGroup>
</pluginGroups>
```

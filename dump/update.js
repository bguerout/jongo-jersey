//Clean heartbeat Twitter API documents
db.tweets.remove({limit: {$ne: null}});
db.tweets.remove({coordinates: null});

db.tweets.find({$or:[{rcoords:null},{date:null}]}).forEach(function (doc) {
        //Convert String to real Date to ease querying
        doc.date = new Date(doc.created_at);
        if (doc.coordinates) {
            //Stringify coords for distinct queries
            doc.rcoords = Math.round(doc.coordinates.coordinates[0]) + "," + Math.round(doc.coordinates.coordinates[1]);
        }
        db.tweets.save(doc);
});

//Indexes
db.tweets.ensureIndex({ coordinates: "2dsphere" });
db.tweets.ensureIndex({ rcoords: 1 });
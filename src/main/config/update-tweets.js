/**
 * decimal places     degrees      distance
 *       0            1            111   km
 *       1            0.1          11.1  km
 *       2            0.01         1.11  km
 *       3            0.001        111   m
 *       4            0.0001       11.1  m
 *       5            0.00001      1.11  m
 *       6            0.000001     0.111 m
 *       7            0.0000001    1.11  cm
 *       8            0.00000001   1.11  mm
 */

//Add field with rounded coords, eg. 106.85724283,-6.22355957 to 106,-6
db.tweets.find({rcoords: null}).forEach(function (doc) {
    doc.rcoords = Math.round(doc.coordinates.coordinates[0]) + "," + Math.round(doc.coordinates.coordinates[1]);
    db.tweets.save(doc);
});
db.tweets.ensureIndex({ rcoords: 1 });

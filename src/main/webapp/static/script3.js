var map,
    markers = [],
    me,
    coords = [],
    currentArea;

$(function () {
    $('.reset').on('click', function () {
        clearArea();
        coords = [];
        for (i = 0; i < markers.length; i++) {
            markers[i].setMap(null);
        }
        markers = [];
    });

    $('.search').on('click', function () {
        searchTweets();
    });

    navigator.geolocation.getCurrentPosition(function (position) {
        me = position.coords;
        initialize(me.latitude, me.longitude);
    });


});

function initialize(lat, lng) {
    var mapOptions = {
        zoom: 3,
        center: new google.maps.LatLng(48.5, 2),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById('map-canvas'),
        mapOptions);

    google.maps.event.addListener(map, 'click', function (event) {
        coords.push(event.latLng);
        if (coords.length > 2) {
            displayArea();
        }
    });

};


function displayArea() {
    clearArea();

    var areaCoords = [];
    for (i = 0; i < coords.length; i++) {
        areaCoords.push(coords[i]);
    }
    areaCoords.push(coords[0]);

    currentArea = new google.maps.Polygon({
        paths: areaCoords,
        strokeColor: "#FF0000",
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: "#FF0000",
        fillOpacity: 0.35
    });

    currentArea.setMap(map);

}

function searchTweets() {
    var data = []
    for (i = 0; i < coords.length; i++) {
        data.push(coords[i].lat() + "," + coords[i].lng());
    }
    data.push(coords[0].lat() + "," + coords[0].lng());
    console.log(data);


    $.post('/tweets/area', {points: data}, function (response) {
        $.each(response, function (index, tweet) {
            console.log(tweet.text);
            var marker = new google.maps.Marker({
                position: new google.maps.LatLng(tweet.coordinates.latitude, tweet.coordinates.longitude),
                map: map,
                title: tweet.text});
            markers.push(marker);
        });
    }, 'json');


}

function clearArea() {
    if (currentArea != null) {
        currentArea.setMap(null);
        currentArea = null;
    }
}

function cleanMarkers() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

function changeZoom(maxDistance) {
    if (maxDistance < 5000)map.setZoom(14);
    else if (maxDistance < 10000)map.setZoom(12);
    else if (maxDistance < 20000)map.setZoom(10);
    else if (maxDistance < 100000) map.setZoom(8);
    else if (maxDistance < 1000000) map.setZoom(6);
    else if (maxDistance < 5000000) map.setZoom(4);
    else map.setZoom(3);

}
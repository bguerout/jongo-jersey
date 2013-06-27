var map,
    markers = [],
    me;

$(function () {


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

        $.getJSON('/tweets/nearest?limit=10&lat=' + event.latLng.lat() + "&lng=" + event.latLng.lng(),
            function (data) {
                cleanMarkers();
                var maxDistance = 0;
                $.each(data.results, function (index, pos) {

                    var marker = new google.maps.Marker({
                        position: new google.maps.LatLng(pos.obj.coordinates.latitude, pos.obj.coordinates.longitude),
                        map: map,
                        title: pos.obj.text});
                    markers.push(marker);

                    if (maxDistance < pos.dis) {
                        maxDistance = pos.dis;
                    }
                });
                console.log("Max distance = " + maxDistance);
                changeZoom(maxDistance);
                map.setCenter(event.latLng);
            }
        );
    });

};

function cleanMarkers() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

function changeZoom(maxDistance) {
    if(maxDistance < 5000)map.setZoom(14);
    else if(maxDistance < 10000)map.setZoom(12);
    else if(maxDistance < 20000)map.setZoom(10);
    else if(maxDistance < 100000) map.setZoom(8);
    else if(maxDistance < 1000000) map.setZoom(6);
    else if(maxDistance < 5000000) map.setZoom(4);
    else map.setZoom(3);

}
var map,
    pointarray,
    heatmap,
    taxiData = [],
    now = moment("20130625 11", "YYYYMMDD h a").minutes(0).seconds(0).milliseconds(0),
    moments = [],
    search,
    me;

$(function () {
    $('.next').on('click',function () {
        now.add(1, 'hour');
        displayDots();
    }).on('dblclick', function () {
            setInterval(function () {
                now.add(1, 'hour');
                displayDots();
            }, 1000);
        });

    $('.previous').on('click', function () {
        now.subtract(1, 'hour');
        displayDots();
    });

    $('.clear').on('click', function () {
        heatmap.setData([]);
    });

    $('.search input').bindWithDelay('keyup', function () {
        displayDots($(this).val());
    }, 1000);

    navigator.geolocation.getCurrentPosition(function (position) {
        me = position.coords;
        initialize(me.latitude, me.longitude);
        displayDots();
    });
});

function displayNow() {
    $('.date').text(now.format('MMMM Do, ha'));
};

function displayDots(term) {
    if (!_.isUndefined(term)) {
        search = term;
    }
    console.log('Get tweets for position: lat=' + me.latitude + '/lng=' + me.longitude + '/start=' + now);
    $.getJSON('/tweets?lat=' + me.latitude +
        '&lng=' + me.longitude +
        '&search=' + search +
        '&start=' + now +
        '&end=' + now.clone().add(1, 'hour'),
        function (data) {
            if (!_.contains(moments, +now)) {
                moments.push(+now);

                $.each(data, function (index, pos) {
                    var coords = pos.coordinates
                    if (coords.latitude) {
                        var dot = new google.maps.LatLng(coords.latitude, coords.longitude);
                        taxiData.push(dot);
                    }
                });
                heatmap.setData(taxiData);
                $('.tweet b').text(taxiData.length);
            }

            displayNow();
        });
};

function initialize(lat, lng) {
    heatmap = new google.maps.visualization.HeatmapLayer();
    heatmap.setMap(new google.maps.Map(document.getElementById('map-canvas'), {
        zoom: 3,
        center: new google.maps.LatLng(lat, lng),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    }));
};
// Adding 500 Data Points
var map, pointarray, heatmap;

var taxiData = [];
var now = moment();
var moments = [];

$(function() {
    $('.next').on('click', function() {
        now.add(1, 'hour');
        displayDots();
    }).on('dblclick', function() {
        setInterval(function() {
            now.add(1, 'hour');
            displayDots();
        }, 1000);
    });
    $('.previous').on('click', function() {
        now.subtract(1, 'hour');
        displayDots();
    });

    displayDots();
    initialize();
});

function displayNow() {
  $('.date').text(now.format('MMMM Do, ha'));
}

function displayDots() {
  $.getJSON('/dots?'+now, function(data) {
      if(!_.contains(moments, +now)) {
          moments.push(+now);
          
          $.each(data, function(index, pos) {
              var dot = new google.maps.LatLng(pos.lat, pos.lng);
              taxiData.push(dot);
          });
          heatmap.setData(taxiData);
          $('.tweet b').text(taxiData.length);
      }  
          
      displayNow();
  });
}

function initialize() {
  var mapOptions = {
    zoom: 13,
    center: new google.maps.LatLng(37.774546, -122.433523),
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };

  map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

  var pointArray = new google.maps.MVCArray(taxiData);

  heatmap = new google.maps.visualization.HeatmapLayer();
  heatmap.setMap(map);
}
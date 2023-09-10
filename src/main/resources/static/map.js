function initMap() {
    var place = document.getElementById('#pref').value;
    alert(place);

    new google.maps.Map(document.getElementById('map'), {
    zoom: 10,
    center: { lat: 35.68194580055263, lng: 139.76654773147183 }
    });
}
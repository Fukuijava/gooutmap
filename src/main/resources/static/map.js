function initMap() {
    //golistの要素を取得
    var gotable = document.getElementById("go");
        var pref = gotable.rows[1].cells[1].innerHTML;
        var city = gotable.rows[2].cells[1].innerHTML;
        var genre = gotable.rows[3].cells[1].innerHTML;
        var move_means = gotable.rows[4].cells[1].innerHTML;
        console.log(pref,city,genre,move_means);
    //my_homeの要素を取得
    var gotable = document.getElementById("home");
        var latitude = gotable.rows[1].cells[1].innerHTML;
        var longitude = gotable.rows[2].cells[1].innerHTML;
        console.log(latitude,longitude);

    //my_homeの要素をジオコード
    var geocoder = new google.maps.Geocoder();
    geocoder.geocode({ address: pref + city },
        function( results, status ){
            if( status == google.maps.GeocoderStatus.OK ){
                var address = results[ 0 ].geometry.location
                alert(address);
                var la_lo = new google.maps.LatLng(address);
                //マップ表示
                var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 11,
                center: la_lo
                });
                new google.maps.Marker({
                    position: la_lo,
                    map,
                    title: "Hello World!",
                });
                window.initMap = initMap;
            }
            else{
                alert( 'Faild：' + status );
            }
        }
    );

}
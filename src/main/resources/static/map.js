let map; //地図
let bounds; //地図の表示領域
let infoWindow; //打たれたピンのウィンドウ
let currentInfoWindow; //現在のウィンドウ
let service; //サービス
let infoPane; //パネルの情報


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
//        var home_la_lo = {
//            lat: latitude,
//            lng: longitude
//        };
        var home_la_lo = latitude +","+ longitude
        console.log(home_la_lo);
        var home = new google.maps.LatLng(home_la_lo);
        console.log(home);

    //my_homeの要素をジオコード
    var geocoder = new google.maps.Geocoder();

    bounds = new google.maps.LatLngBounds(); //マップの表示領域を生成
    infoWindow = new google.maps.InfoWindow; //打たれたピンのウィンドウを生成
    currentInfoWindow = infoWindow; //currentInfoWindowにウィンドウ生成の変数を入れる
    infoPane = document.getElementById('panel'); //infoPaneにID="panel"の情報を入れる


    geocoder.geocode({ address: pref + city },
        function( results, status ){
            if( status == google.maps.GeocoderStatus.OK ){
                var pref_city = results[ 0 ].geometry.location
                var pcAddress = new google.maps.LatLng(pref_city);
                //マップ表示
                map = new google.maps.Map(document.getElementById('map'), {
                    zoom: 11,
                    center: pcAddress
                });
                bounds.extend(pcAddress); //地図の表示領域を家にする

                infoWindow.setPosition(home); //ウィンドウを家に置く
                infoWindow.setContent('my_home'); //ウィンドウにコメントを入れる
                infoWindow.open(map);//ウィンドウを地図に表示
                map.setCenter(pcAddress);//地図の中心位置はposの座標 part2















//                new google.maps.Marker({
//                    position: la_lo,
//                    map,
//                    title: "Hello World!",
//                });
//                window.initMap = initMap;

            }
            else{
                alert( 'Faild：' + status );
            }
        }
    );

}
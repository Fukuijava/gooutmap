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
    var hometable = document.getElementById("home");
        var latitude_Str = hometable.rows[1].cells[1].innerHTML;
        var longitude_Str = hometable.rows[2].cells[1].innerHTML;
        var latitude_Flo = parseFloat(latitude_Str);
        var longitude_Flo = parseFloat(longitude_Str);
        var home = {
            lat: latitude_Flo,
            lng: longitude_Flo
        };
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
                getNearbyPlaces(pcAddress);//getNearbyPlacesメソッドに都道府県と市区町村の座標を送る

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

// 近くの場所の検索リクエストを実行する
//getNearbyPlacesにリクエストする項目
//渡された現在位置
//「google.maps.places.RankBy.DISTANCE」を指定しました。これにより、場所検索に最も近い順に結果が並べ替えられます。
//radius :中心から半径5000m
//rankByとradiusはどっちか一つしか指定できない
//キーワード’sushi’がどこかしらに入ってるやつを探す
//その場所のタイプで探す
//keywordとtypesはどっちか一つしか指定できない
function getNearbyPlaces(pcAddress) {
    let request = {
        location: pcAddress,
        rankBy: google.maps.places.RankBy.DISTANCE,
        //radius: 5000,
        //keyword: 'sushi'
        types: ['cafe']
    };
    //変数serviceを＃mapのPlaces_apiを使える変数にして、そこでnearbySearchを使ってる
    service = new google.maps.places.PlacesService(map);
    service.nearbySearch(request, nearbyCallback);
}
// 近隣検索の結果 (最大 20) を処理します
//結果を返すやつ
//最大20回検索に引っかかったやつにマーカーを立ててる
function nearbyCallback(results, status) {
    if (status == google.maps.places.PlacesServiceStatus.OK) {
        createMarkers(results);//メソッド呼び出し
    }
}

// 各場所の結果の位置にマーカーを設定します
function createMarkers(places) {
    places.forEach(place => {
        let marker = new google.maps.Marker({
        position: place.geometry.location,
        map: map,
        title: place.name
    });

    /* クリックリスナーをマーカーに追加する */
    // 各マーカーにクリックリスナーを追加する
    //マーカーをクリックした時のイベント処理？
    google.maps.event.addListener(marker, 'click', () => {
        let request = {
            placeId: place.place_id,
            fields: ['name', 'formatted_address', 'geometry', 'rating','website', 'photos']
        };

        /* ユーザーがマーカーをクリックしたときにのみ、場所の詳細を取得します。
        * 結果が得られ次第、すべての場所の詳細を取得する場合
        * 検索応答によっては、API レート制限に達します。 */
        service.getDetails(request, (placeResult, status) => {
            showDetails(placeResult, marker, status)
        });
    });

    // このマーカーの位置を含むように地図の境界を調整します
    bounds.extend(place.geometry.location);
    });
    /* すべてのマーカーを配置したら、マップの境界を次のように調整します。
    * 表示領域内のすべてのマーカーを表示します。 */
    map.fitBounds(bounds);
}

/* 情報ウィンドウに場所の詳細を表示する */
// マーカーの上に詳細を表示する情報ウィンドウを構築します。
// if (placeResult.rating)     rating = placeResult.rating;は評価があればratingに評価を入れる
//評価がなければlet rating = "None";のままになる
function showDetails(placeResult, marker, status) {
    if (status == google.maps.places.PlacesServiceStatus.OK) {
        let placeInfowindow = new google.maps.InfoWindow();
        let rating = "None";
        if (placeResult.rating)     rating = placeResult.rating;
        placeInfowindow.setContent
        ('<div><strong>' + placeResult.name +'</strong><br>' + 'Rating: ' + rating + '</div>');
        placeInfowindow.open(marker.map, marker);
        currentInfoWindow.close();
        currentInfoWindow = placeInfowindow;
        showPanel(placeResult);
    } else {
        console.log('showDetails failed: ' + status);
    }
}
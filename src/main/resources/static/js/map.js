var map; //地図
var bounds; //地図の表示領域
var infoWindow; //打たれたピンのウィンドウ
var currentInfoWindow; //現在のウィンドウ
var service; //サービス
var infoPane; //パネルの情報
var infoRoute;//ルートの情報
var genre;//ジャンル
var home;//家
var move_means;//移動手段
var route;
var directionsRenderer;
var directionsService;

function initMap() {
    //golistの要素を取得
    var gotable = document.getElementById("go");
    var pref = gotable.rows[1].cells[1].innerHTML;
    var city = gotable.rows[2].cells[1].innerHTML;
    genre = gotable.rows[3].cells[1].innerHTML;
    move_means = gotable.rows[4].cells[1].innerHTML;
        console.log(pref,city,genre,move_means);

    //my_homeの要素を取得
    //家の座標を入れていたらtrue、入れてなかったらfalseの処理
    if(document.getElementById('coordinate') != null){
        var hometable = document.getElementById("home");
        var coordinate = hometable.rows[1].cells[1].innerHTML;
        var coordinate_Str = coordinate.split(',');
        var lat_Flo = parseFloat(coordinate_Str[0]);
        var lng_Flo = parseFloat(coordinate_Str[1]);
        home = {
            lat: lat_Flo,
            lng: lng_Flo
        };
            console.log(home);
    }else{
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(position => {
                home = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                };
                console.log(home);
            }, () => {
                handleLocationError(true, infoWindow);
            });
        } else {
            handleLocationError(false, infoWindow);
        }
    }

    //my_homeの要素をジオコード
    var geocoder = new google.maps.Geocoder();

    bounds = new google.maps.LatLngBounds(); //マップの表示領域を生成
    infoWindow = new google.maps.InfoWindow; //打たれたピンのウィンドウを生成
    currentInfoWindow = infoWindow; //currentInfoWindowにウィンドウ生成の変数を入れる
    infoPane = document.getElementById('panel'); //infoPaneにID="panel"の情報を入れる
    infoRoute = document.getElementById('sidebar');//infoRouteにID="sidebar"の情報を入れる
    directionsRenderer = new google.maps.DirectionsRenderer();
    directionsService = new google.maps.DirectionsService();

    geocoder.geocode({ address: pref + city },function( results, status ){
        if( status == google.maps.GeocoderStatus.OK ){
            var pref_city = results[ 0 ].geometry.location
            var pcAddress = new google.maps.LatLng(pref_city);
            //マップ表示
            map = new google.maps.Map(document.getElementById('map'), {
                zoom: 11,
                center: pcAddress,
                disableDefaultUI: true　//デフォルトのコントローラー（人形とか＋。ーボタン）を非表示にする。
            });

            bounds.extend(pcAddress); //地図の表示領域を指定した県・市にする
            infoWindow.setPosition(home); //ウィンドウを家に置く
            infoWindow.setContent('MY_HOME'); //ウィンドウにコメントを入れる
            infoWindow.open(map);//ウィンドウを地図に表示
            getNearbyPlaces(pcAddress);//getNearbyPlacesメソッドに都道府県と市区町村の座標を送る

            new google.maps.Marker({ position: home, map });
        }
        else{
            alert( 'Faild：' + status );
        }
    });
}
function handleLocationError(browserHasGeolocation, infoWindow) {
    home = {lat: 35.68164765431072, lng: 139.7670550726925};
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
//        radius: 5000,
        keyword: genre
//        types: ['cafe']
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
        bounds.extend(home);
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
        if (placeResult.rating)
            rating = placeResult.rating;
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

/* サイドバーに場所の詳細をロードする */
// サイドバーに場所の詳細を表示します
function showPanel(placeResult) {
    // 以前の詳細をクリアします
    while (infoPane.lastChild) {
        infoPane.removeChild(infoPane.lastChild);
    }
    /* 場所の写真と場所の詳細を表示する */
    // メインの写真がある場合は追加します
    if (placeResult.photos) {
        let firstPhoto = placeResult.photos[0];
        let photo = document.createElement('img');
        photo.classList.add('hero');
        photo.src = firstPhoto.getUrl();
        infoPane.appendChild(photo);
    }

    //テキスト形式を使用して場所の名前、評価、住所、公式URLを追加する
    let name = document.createElement('h1');
    name.classList.add('place');
    name.textContent = placeResult.name;
    infoPane.appendChild(name);
    if (placeResult.rating) {
        let rating = document.createElement('p');
        rating.classList.add('details');
        rating.textContent = `Rating: ${placeResult.rating} \u272e`;
        infoPane.appendChild(rating);
    }
    let address = document.createElement('p');
    address.classList.add('details');
    address.textContent = placeResult.formatted_address;
    infoPane.appendChild(address);
    if (placeResult.website) {
        let websitePara = document.createElement('p');
        let websiteLink = document.createElement('a');
        let websiteUrl = document.createTextNode(placeResult.website);
        websiteLink.appendChild(websiteUrl);
        websiteLink.title = placeResult.website;
        websiteLink.href = placeResult.website;
        websitePara.appendChild(websiteLink);
        infoPane.appendChild(websitePara);
    }
    // infoPaneを開く
    document.getElementById("map").style.width = "70%";
    document.getElementById("panel").addEventListener("change", showRoutes(placeResult));//showRoutesメソッドへ
}

function showRoutes(placeResult){
    // 以前の詳細をクリアします
    while (infoRoute.lastChild) {
        infoRoute.removeChild(infoRoute.lastChild);
    }

    if(move_means == '車'){
        move_means = google.maps.DirectionsTravelMode.DRIVING;
    }else if(move_means != google.maps.DirectionsTravelMode.DRIVING){
        move_means = google.maps.DirectionsTravelMode.WALKING;
    }
    directionsRenderer.setMap(map);
    directionsRenderer.setPanel(document.getElementById("sidebar"));

    calculateAndDisplayRoute(directionsService, directionsRenderer);//calculateAndDisplayRouteメソッド呼び出し

    function calculateAndDisplayRoute(directionsService, directionsRenderer) {
        directionsService
        .route({
            origin: new google.maps.LatLng(home), // スタート地点
            destination: new google.maps.LatLng(placeResult.geometry.location), // ゴール地点
            travelMode: move_means// 移動手段
        })
        .then((response) => {
            directionsRenderer.setDirections(response);
        })
        .catch((e) => window.alert("Directions request failed due to " + status));
    }
    window.initMap = initMap;
}


$(function() {
    $('Button').on('click', function() {
        document.getElementById("map").style.width = "100%";
        document.getElementById("sidebar").style.width = "0%";
    });
});


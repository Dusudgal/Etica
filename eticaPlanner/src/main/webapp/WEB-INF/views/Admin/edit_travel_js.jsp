<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=${map_key}&libraries=services"></script>
<script>
    // 지도 초기화 스크립트
    function initMap() {
        var mapContainer = document.getElementById('map'), // 지도를 표시할 div
            mapOption = {
                center: new kakao.maps.LatLng(37.5665, 126.9780), // 초기 지도 중심 좌표 (데이터베이스)
                level: 3 // 초기 지도 줌 레벨
            };

        // 지도 생성
        var map = new kakao.maps.Map(mapContainer, mapOption);

        // 좌표 -> 주소 변환 객체
        var geocoder = new kakao.maps.services.Geocoder();

        // 주소를 기준으로 지도에 표시
        var address = "${travel.addr}";

        geocoder.addressSearch(address, function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

        // 마커를 지도에 표시
        var marker = new kakao.maps.Marker({
            map: map,
            position: coords
        });

        // 지도 중심을 결과값으로 받은 위치로 이동
        map.setCenter(coords);

        // HTML 폼에 좌표 입력
        document.getElementById('travel_X_marker').value = result[0].y;
        document.getElementById('travel_Y_marker').value = result[0].x;



        // 지도 클릭 이벤트를 등록
        kakao.maps.event.addListener(map, 'click', function(mouseEvent){
            //클릭한 위치를 마커 위치로 설정
            var latlng = mouseEvent.latLng;

            //위도와 경도를 정수로 변환
            var lat = latlng.getLat().toFixed(5);
            var lng = latlng.getLng().toFixed(5);

            marker.setPosition(latlng);

            //HTML 폼에 좌표 입력
            document.getElementById('travel_X_marker').value = lat;
            document.getElementById('travel_Y_marker').value = lng;

            //좌표를 주소로 변환
             geocoder.coord2Address(lng, lat, function(result, status) {
                if(status === kakao.maps.services.Status.OK) {
                    var detailAddr = result[0].road_address ? result[0].road_address.address_name : result[0].address.address_name;
                    document.getElementById('travel_context').value = detailAddr;
                }
           });
        });
    } else {
        console.error('fail');
    }
    });
    }

        // DOMContentLoaded 이벤트 발생 시 initMap 함수 실행
        document.addEventListener('DOMContentLoaded', initMap);

        // 폼 제출 함수 예시
        function submitTravelForm() {
            document.getElementById("travelform").submit();
        }
</script>
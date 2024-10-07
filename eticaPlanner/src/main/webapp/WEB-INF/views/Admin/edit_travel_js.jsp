<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=${map_key}"></script>
<script>
    // 지도 초기화 스크립트
    function initMap() {
        var mapX = ${travel.travelXcd};
        var mapY = ${travel.travelYcd};

        var mapContainer = document.getElementById('map'), // 지도를 표시할 div
            mapOption = {
                center: new kakao.maps.LatLng(mapX, mapY), // 초기 지도 중심 좌표 (데이터베이스)
                level: 3 // 초기 지도 줌 레벨
            };

        // 지도 생성
        var map = new kakao.maps.Map(mapContainer, mapOption);

        // 마커를 지도에 표시
        var marker = new kakao.maps.Marker({
            position: mapOption.center,
        });

        marker.setMap(map);

        // 지도 클릭 이벤트를 등록
        kakao.maps.event.addListener(map, 'click', function(mouseEvent){
            //클릭한 위치를 마커 위치로 설정
            var latlng = mouseEvent.latLng;

            marker.setPosition(latlng);

            //위도와 경도를 정수로 변환
            var lat = latlng.getLat().toFixed(5);
            var lng = latlng.getLng().toFixed(5);

            //HTML 폼에 좌표 입력
            document.getElementById('travel_X_marker').value = lat;
            document.getElementById('travel_Y_marker').value = lng;
        });
    }

    // DOMContentLoaded 이벤트 발생 시 initMap 함수 실행
    document.addEventListener('DOMContentLoaded', initMap);

    // 폼 제출 함수 예시
    function submitTravelForm() {
        document.getElementById("travelform").submit();
    }

</script>
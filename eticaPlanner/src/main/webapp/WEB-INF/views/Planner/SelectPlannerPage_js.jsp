<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=${map_key}"></script>

<script type="text/javascript">
    // 메모하기 위한 관광지 Ul 태그
    //메모 관광지 ul태그를 감싸는 div 태그
    const tourMemoListDiv = document.querySelector('.row:nth-child(2)');
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');
    const tour_title = document.getElementById('TourTitle');
    let days = "";
    let tourList = null;
    let tourMapList = {};
    let memoMapList = {};
    var positions = [];
    let markers = [];
    // 카카오 map을 띄우는 곳
    let mapcontainer = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
    let options = { //지도를 생성할 때 필요한 기본 옵션
    	center: new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
    	level: 3 //지도의 레벨(확대, 축소 정도)
    };

    let map = new kakao.maps.Map(mapcontainer, options); //지도 생성 및 객체 리턴

    function createtag( li , h4Text , imgsrc , pText ){
        const title = document.createElement('H4');
        const img = document.createElement('img');
        const newP = document.createElement('p');
        newP.textContent = pText;
        title.textContent = h4Text;
        img.src = imgsrc;
        img.onerror = () => {img.style.display = 'none';};
        li.appendChild(img);
        li.appendChild(title);
        li.appendChild(newP);
    }

    function moveMap( mapx , mapy ){
         let moveLatLon = new kakao.maps.LatLng(mapx, mapy);
         map.setCenter(moveLatLon);
    }
    function setMarker(tourMaptitle , tourData){
        let markerPosition  = new kakao.maps.LatLng( tourMaptitle.mapy , tourMaptitle.mapx);  // 마커가 표시될 위치
        let marker = new kakao.maps.Marker({  // 마커를 생성한다
            position: markerPosition ,
            ul : tourList.getAttribute('data-date')
        });

        marker.setMap(map);
        markers.push({ marker : marker , title : tourData.titleText , ul : tourList.getAttribute('data-date') });
    }
    function setMarkersView(date , map){
        markers.forEach(markerdata => {
            if(markerdata.ul === date){
                 markerdata.marker.setMap(map);
            }
        });
    }

    // 여행 일자를 클릭시에 버튼을 생성해서 일자별로 볼수있게 만들어주는 함수
    document.addEventListener("DOMContentLoaded", function() {
        const durationSpan = document.getElementById('duration');
            const dayButtonsContainer = document.getElementById('dayButtons');
            const tourMemoContainer = document.querySelector('.row:nth-child(2) > .tourMemo');

            function updateDuration() {
                const startDateValue = startDateInput.value;
                const endDateValue = endDateInput.value;

                if (startDateValue && endDateValue) {
                    const startDate = new Date(startDateValue);
                    const endDate = new Date(endDateValue);
                    const timeDifference = endDate - startDate;
                    const dayDifference = Math.ceil(timeDifference / (1000 * 60 * 60 * 24)) + 1;

                    if (durationSpan) durationSpan.textContent = dayDifference;

                    if (dayButtonsContainer) updateDayButtons(dayDifference);
                    if (tourMemoContainer) updateTourMemoList(dayDifference);
                    if (dayDifference > 0) {
                        showDayList(1); // 첫째 날 표시
                    }
                } else {
                    if (durationSpan) durationSpan.textContent = '0';
                    clearDayButtons();
                    clearTourMemoLists();
                }
            }

            function updateDayButtons(days) {
                if (dayButtonsContainer) {
                    dayButtonsContainer.innerHTML = ''; // 기존 버튼 제거

                    for (let i = 1; i <= days; i++) {
                        const button = document.createElement('button');
                        button.textContent = `\${i}일차`;
                        button.addEventListener('click', () => showDayList(i));
                        dayButtonsContainer.appendChild(button);
                    }
                }
            }

            function updateTourMemoList(days) {
                if (tourMemoContainer) {
                    tourMemoContainer.innerHTML = ''; // 기존 ul 태그 제거

                    for (let i = 1; i <= days; i++) {
                        const newUl = document.createElement('ul');
                        newUl.classList.add('touristSpotMemo');
                        newUl.setAttribute('data-date', i); // 각 ul에 날짜 설정
                        newUl.style.display = 'none'; // 초기에는 숨김

                        // 해당 날짜의 메모 데이터를 가져옴
                        if(planmemo){

                            const dayData = planmemo.filter(item => item.date === i);
                            dayData.forEach(data => {
                                const li = document.createElement('li');
                                createtag(li, data.title, data.imgSrc, data.addr); // createtag 함수 사용
                                const input = document.createElement('input');
                                input.type = 'text';
                                input.value = data.inputValue; // inputValue 설정
                                li.appendChild(input);

                                newUl.appendChild(li); // li를 newUl에 추가

                                if (!memoMapList[newUl.getAttribute('data-date')]) {
                                    memoMapList[newUl.getAttribute('data-date')] = {}; // 빈 객체로 초기화
                                }
                                if(data.mapx != null){
                                    memoMapList[i][data.title] = {
                                        mapx: data.mapx ,
                                        mapy: data.mapy
                                    };

                                    let markerPosition  = new kakao.maps.LatLng( data.mapy , data.mapx );  // 마커가 표시될 위치
                                    let marker = new kakao.maps.Marker({  // 마커를 생성한다
                                        position: markerPosition ,
                                        ul : newUl.getAttribute('data-date')
                                    });

                                    marker.setMap(map);
                                    markers.push({ marker : marker , title : data.title , ul : newUl.getAttribute('data-date') });
                                }

                            });
                        }

                        tourMemoContainer.appendChild(newUl);
                    }
                }
            }

            function clearDayButtons() {
                if (dayButtonsContainer) {
                    dayButtonsContainer.innerHTML = '';
                }
            }

            function clearTourMemoLists() {
                if (tourMemoContainer) {
                    tourMemoContainer.innerHTML = '';
                }
            }

            function showDayList(day) {
                const allLists = document.querySelectorAll('.row:nth-child(2) .touristSpotMemo');
                if(tourList){
                    setMarkersView(tourList.getAttribute('data-date') , null);
                }
                allLists.forEach(ul => {
                    if (parseInt(ul.getAttribute('data-date')) === day) {
                        ul.style.display = 'block'; // 해당 날짜의 ul 태그 보이기
                        tourList = ul;
                    } else {
                        ul.style.display = 'none'; // 다른 날짜의 ul 태그 숨기기
                    }
                });

                if(tourList){
                    setMarkersView(tourList.getAttribute('data-date') , map);
                }
            }

            startDateInput.addEventListener('change', updateDuration);
            endDateInput.addEventListener('change', updateDuration);

            let planData = JSON.parse('${planData}');
            let planmemo = planData.tourMemoData;
            const planTitle = planData.tourTitleData;
            moveMap( planmemo[0].mapy , planmemo[0].mapx )
            init(planTitle);

            function init(planTitle) {
                tour_title.value = planTitle.tour_title;
                startDateInput.value = planTitle.startDate;
                endDateInput.value = planTitle.endDate;
                updateDuration();
                planmemo = null;
            }
    });

</script>